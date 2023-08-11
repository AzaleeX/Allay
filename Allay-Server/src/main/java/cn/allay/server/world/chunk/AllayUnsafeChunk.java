package cn.allay.server.world.chunk;

import cn.allay.api.block.data.BlockPos;
import cn.allay.api.block.data.BlockStateWithPos;
import cn.allay.api.block.type.BlockState;
import cn.allay.api.data.VanillaBlockTypes;
import cn.allay.api.world.DimensionInfo;
import cn.allay.api.world.World;
import cn.allay.api.world.biome.BiomeType;
import cn.allay.api.world.chunk.ChunkLoader;
import cn.allay.api.world.chunk.ChunkSection;
import cn.allay.api.world.chunk.UnsafeChunk;
import cn.allay.api.world.heightmap.HeightMap;
import cn.allay.api.world.heightmap.HeightMapType;
import lombok.Getter;
import lombok.Setter;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.UpdateBlockPacket;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnmodifiableView;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Getter
@NotThreadSafe
public class AllayUnsafeChunk implements UnsafeChunk {
    @Setter
    protected volatile int chunkX;
    @Getter
    @Setter
    protected volatile int chunkZ;
    @Getter
    protected final World world;
    @Getter
    protected final DimensionInfo dimensionInfo;
    protected final ChunkSection[] sections;
    protected final HeightMap[] heightMap;
    protected final Vector<ChunkLoader> chunkLoaders;
    protected final Queue<BedrockPacket> chunkPacketQueue;

    public AllayUnsafeChunk(World world, int chunkX, int chunkZ, DimensionInfo dimensionInfo) {
        this(world, chunkX, chunkZ, dimensionInfo, NbtMap.EMPTY);
    }

    public AllayUnsafeChunk(World world, int chunkX, int chunkZ, DimensionInfo dimensionInfo, NbtMap data) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.sections = new ChunkSection[dimensionInfo.chunkSectionSize()];
        this.heightMap = new HeightMap[]{new HeightMap()};
        this.dimensionInfo = dimensionInfo;
        this.chunkLoaders = new Vector<>();
        this.chunkPacketQueue = new ConcurrentLinkedQueue<>();
    }

    @ApiStatus.Internal
    @Nullable
    public ChunkSection getSection(int y) {
        return sections[y];
    }

    @ApiStatus.Internal
    @NotNull
    public ChunkSection createAndGetSection(int y) {
        for (int i = 0; i <= y; i++) {
            if (sections[i] == null) {
                sections[i] = new ChunkSection(i);
            }
        }
        return sections[y];
    }

    public int getHeight(HeightMapType type, @Range(from = 0, to = 15) int x, @Range(from = 0, to = 15) int z) {
        return this.heightMap[type.ordinal()].get(x, z);
    }

    public void setHeight(HeightMapType type, @Range(from = 0, to = 15) int x, @Range(from = 0, to = 15) int z, int height) {
        this.heightMap[type.ordinal()].set(x, z, height);
    }

    public BlockState getBlockState(@Range(from = 0, to = 15) int x, @Range(from = -512, to = 511) int y, @Range(from = 0, to = 15) int z, boolean layer) {
        ChunkSection section = this.getSection(normalY(y) >>> 4);
        BlockState blockState;
        if (section == null) {
            blockState = VanillaBlockTypes.AIR_TYPE.getDefaultState();
        } else {
            blockState = section.getBlock(x, y & 0xf, z, layer);
        }
        return blockState;
    }

    public void setBlockState(@Range(from = 0, to = 15) int x, @Range(from = -512, to = 511) int y, @Range(from = 0, to = 15) int z, BlockState blockState, boolean layer, boolean send, boolean update) {
        int sectionY = normalY(y) >>> 4;
        ChunkSection section = this.getSection(sectionY);
        if (section == null) {
            section = this.createAndGetSection(sectionY);
        }
        var blockPos = new BlockPos(world, x, y & 0xf, z, layer);
        var oldBlockState = section.getBlock(x, y & 0xf, z, layer);
        oldBlockState.getBehavior().onReplace(new BlockStateWithPos(oldBlockState, blockPos), blockState);
        blockState.getBehavior().onPlace(new BlockStateWithPos(oldBlockState, blockPos), blockState);
        section.setBlock(x, y & 0xf, z, layer, blockState);
        if (update) {
            neighborUpdateAround(x, y, z);
        }
        if (send) {
            var updateBlockPacket = new UpdateBlockPacket();
            updateBlockPacket.setBlockPosition(Vector3i.from((chunkX << 4) + x, y, (chunkZ << 4) + z));
            updateBlockPacket.setDefinition(blockState.toNetworkBlockDefinitionRuntime());
            updateBlockPacket.setDataLayer(layer ? 1 : 0);
            updateBlockPacket.getFlags().addAll( UpdateBlockPacket.FLAG_ALL_PRIORITY );
            sendChunkPacket(updateBlockPacket);
        }
    }

    public @Range(from = 0, to = 15) int getBlockLight(@Range(from = 0, to = 15) int x, @Range(from = -512, to = 511) int y, @Range(from = 0, to = 15) int z) {
        ChunkSection section = this.getSection(normalY(y) >>> 4);
        return section == null ? 0 : section.getBlockLight(x, y & 0xf, z);
    }

    public @Range(from = 0, to = 15) int getSkyLight(@Range(from = 0, to = 15) int x, @Range(from = -512, to = 511) int y, @Range(from = 0, to = 15) int z) {
        ChunkSection section = this.getSection(normalY(y) >>> 4);
        return section == null ? 0 : section.getSkyLight(x, y & 0xf, z);
    }

    public void setBlockLight(@Range(from = 0, to = 15) int x, @Range(from = -512, to = 511) int y, @Range(from = 0, to = 15) int z, int light) {
        this.createAndGetSection(normalY(y) >>> 4).setBlockLight(x, y & 0xf, z, (byte) light);
    }

    public void setSkyLight(@Range(from = 0, to = 15) int x, @Range(from = -512, to = 511) int y, @Range(from = 0, to = 15) int z, int light) {
        this.createAndGetSection(normalY(y) >>> 4).setSkyLight(x, y & 0xf, z, (byte) light);
    }

    protected int normalY(int y) {
        return y - getDimensionInfo().minHeight();
    }

    @Override
    public void setBiome(@Range(from = 0, to = 15) int x, @Range(from = -512, to = 511) int y, @Range(from = 0, to = 15) int z, BiomeType biomeType) {
        this.createAndGetSection(normalY(y) >>> 4).setBiomeType(x, y & 0xf, z, biomeType);
    }

    @Override
    public BiomeType getBiome(@Range(from = 0, to = 15) int x, @Range(from = -512, to = 511) int y, @Range(from = 0, to = 15) int z) {
        return this.createAndGetSection(normalY(y) >>> 4).getBiomeType(x, y & 0xf, z);
    }

    @Override
    @UnmodifiableView
    public Set<ChunkLoader> getChunkLoaders() {
        return chunkLoaders.stream().collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void addChunkLoader(ChunkLoader chunkLoader) {
        chunkLoaders.add(chunkLoader);
    }

    @Override
    public void removeChunkLoader(ChunkLoader chunkLoader) {
        chunkLoaders.remove(chunkLoader);
    }

    @Override
    public int getChunkLoaderCount() {
        return chunkLoaders.size();
    }

    @Override
    public void addChunkPacket(BedrockPacket packet) {
        chunkPacketQueue.add(packet);
    }

    @Override
    public void sendChunkPacket(BedrockPacket packet) {
        for (ChunkLoader chunkLoader : chunkLoaders) {
            chunkLoader.sendPacket(packet);
        }
    }

    @Override
    public void sendChunkPackets() {
        if (chunkPacketQueue.isEmpty()) return;
        BedrockPacket packet;
        while ((packet = chunkPacketQueue.poll()) != null) {
            sendChunkPacket(packet);
        }
    }
}
