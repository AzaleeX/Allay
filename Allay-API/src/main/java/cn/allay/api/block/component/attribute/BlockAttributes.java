package cn.allay.api.block.component.attribute;

import cn.allay.api.math.voxelshape.VoxelShape;
import cn.allay.api.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.cloudburstmc.nbt.NbtMap;
import org.joml.Vector3fc;
import org.joml.primitives.AABBf;

import java.awt.*;

/**
 * Allay Project 2023/5/1
 *
 * @author daoge_cmd
 */
@Getter
@Accessors(fluent = true)
@Builder(toBuilder = true)
@EqualsAndHashCode
public class BlockAttributes {
    public static final float DEFAULT_FRICTION = 0.6f;
    public static BlockAttributes DEFAULT = BlockAttributes.builder().build();
    protected static Gson SERIALIZER = new GsonBuilder()
            .registerTypeAdapter(VoxelShape.class, (JsonDeserializer<Object>) (json, typeOfT, context) ->
                    VoxelShape.builder().solid(parseAABBStr(json.getAsString())).build())
            .registerTypeAdapter(Color.class, (JsonDeserializer<Object>) (json, typeOfT, context) -> {
                var r = json.getAsJsonObject().get("r").getAsInt();
                var g = json.getAsJsonObject().get("g").getAsInt();
                var b = json.getAsJsonObject().get("b").getAsInt();
                var a = json.getAsJsonObject().get("a").getAsInt();
                return new Color(r, g, b, a);
            })
            .create();

    protected static AABBf parseAABBStr(String str) {
        var numbers = StringUtils.fastSplit(str, ",").stream().map(Float::valueOf).toList();
        return new AABBf(numbers.get(0), numbers.get(1), numbers.get(2), numbers.get(3), numbers.get(4), numbers.get(5));
    }

    @Builder.Default
    @SerializedName("aabbCollision")
    protected VoxelShape voxelShape = VoxelShape.builder().solid(0, 0, 0, 1, 1 , 1).build();

    public VoxelShape computeOffsetVoxelShape(float x, float y, float z) {
        return voxelShape.translate(x, y, z);
    }

    public VoxelShape computeOffsetVoxelShape(Vector3fc vector) {
        return computeOffsetVoxelShape(vector.x(), vector.y(), vector.z());
    }

    @Builder.Default
    protected boolean hasCollision = true;
    @Builder.Default
    protected boolean blocksPrecipitation = true;
    @Builder.Default
    protected boolean canBeMovingBlock = true;
    @Builder.Default
    protected boolean breaksFallingBlocks = false;
    @Builder.Default
    protected int burnAbility = 0;
    @Builder.Default
    protected int burnChance = 0;
    @Builder.Default
    protected boolean canBeBrokenFromFalling = false;
    @Builder.Default
    protected boolean canContainLiquid = false;
    @Builder.Default
    protected Color color = Color.BLACK;
    @Builder.Default
    protected float explosionResistance = 15;
    @Builder.Default
    protected float friction = DEFAULT_FRICTION;
    @Builder.Default
    protected float hardness = 1;
    @Builder.Default
    protected boolean hasBlockEntity = false;
    @Builder.Default
    protected boolean hasComparatorSignal = false;
    @Builder.Default
    protected boolean isAlwaysDestroyable = true;
    @Builder.Default
    protected boolean isContainerBlock = false;
    @Builder.Default
    protected boolean isLiquid = false;
    @Builder.Default
    protected boolean isMotionBlockingBlock = false;
    @Builder.Default
    protected boolean isPowerSource = false;
    @Builder.Default
    protected boolean isSolid = false;
    @Builder.Default
    protected boolean isSolidBlocking = false;
    @Builder.Default
    protected boolean isUnbreakable = false;
    @Builder.Default
    protected boolean isWaterBlocking = true;
    @Builder.Default
    protected boolean flammable = true;
    @Builder.Default
    protected int light = 0;
    @Builder.Default
    protected int lightEmission = 0;
    @Builder.Default
    protected boolean pushesUpFallingBlocks = false;
    @Builder.Default
    protected boolean superHot = false;
    @Builder.Default
    protected float thickness = 0;
    @Builder.Default
    protected float translucency = 0;
    @Builder.Default
    protected boolean waterSpreadCausesSpawn = false;

    public static BlockAttributes fromJson(String json) {
        return SERIALIZER.fromJson(json, BlockAttributes.class);
    }

    //TODO: test
    public static BlockAttributes fromNBT(NbtMap nbt) {
        var colorObj = nbt.getCompound("color");
        var color = new Color(
                colorObj.getInt("r"),
                colorObj.getInt("g"),
                colorObj.getInt("b"),
                colorObj.getInt("a")
        );
        return BlockAttributes
                .builder()
                .voxelShape(VoxelShape.builder().solid(parseAABBStr(nbt.getString("aabbCollision"))).build())
                .hasCollision(nbt.getBoolean("hasCollision"))
                .blocksPrecipitation(nbt.getBoolean("blocksPrecipitation"))
                .canBeMovingBlock(nbt.getBoolean("canBeMovingBlock"))
                .breaksFallingBlocks(nbt.getBoolean("breaksFallingBlocks"))
                .burnAbility(nbt.getInt("burnAbility"))
                .burnChance(nbt.getInt("burnChance"))
                .canBeBrokenFromFalling(nbt.getBoolean("canBeBrokenFromFalling"))
                .canContainLiquid(nbt.getBoolean("canContainLiquid"))
                .color(color)
                .explosionResistance(nbt.getFloat("explosionResistance"))
                .friction(nbt.getFloat("friction"))
                .hardness(nbt.getFloat("hardness"))
                .hasBlockEntity(nbt.getBoolean("hasBlockEntity"))
                .hasComparatorSignal(nbt.getBoolean("hasComparatorSignal"))
                .isAlwaysDestroyable(nbt.getBoolean("isAlwaysDestroyable"))
                .isContainerBlock(nbt.getBoolean("isContainerBlock"))
                .isLiquid(nbt.getBoolean("isLiquid"))
                .isMotionBlockingBlock(nbt.getBoolean("isMotionBlockingBlock"))
                .isPowerSource(nbt.getBoolean("isPowerSource"))
                .isSolid(nbt.getBoolean("isSolid"))
                .isSolidBlocking(nbt.getBoolean("isSolidBlocking"))
                .isUnbreakable(nbt.getBoolean("isUnbreakable"))
                .isWaterBlocking(nbt.getBoolean("isWaterBlocking"))
                .flammable(nbt.getBoolean("flammable"))
                .light(nbt.getInt("light"))
                .lightEmission(nbt.getInt("lightEmission"))
                .pushesUpFallingBlocks(nbt.getBoolean("pushesUpFallingBlocks"))
                .superHot(nbt.getBoolean("superHot"))
                .thickness(nbt.getFloat("thickness"))
                .translucency(nbt.getFloat("translucency"))
                .waterSpreadCausesSpawn(nbt.getBoolean("waterSpreadCausesSpawn"))
                .build();
    }
}