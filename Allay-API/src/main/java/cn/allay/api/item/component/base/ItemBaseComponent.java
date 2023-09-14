package cn.allay.api.item.component.base;

import cn.allay.api.block.data.BlockFace;
import cn.allay.api.block.type.BlockState;
import cn.allay.api.component.annotation.Inject;
import cn.allay.api.entity.interfaces.EntityPlayer;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.UseItemOn;
import cn.allay.api.item.component.ItemComponent;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.world.World;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;
import org.joml.Vector3ic;

/**
 * Allay Project 2023/5/19
 *
 * @author daoge_cmd
 */
public interface ItemBaseComponent extends UseItemOn, ItemComponent {

    @Inject
    ItemType<? extends ItemStack> getItemType();

    @Inject
    int getCount();

    @Inject
    void setCount(int count);

    @Inject
    int getDamage();

    @Inject
    void setDamage(int damage);

    @Nullable
    @Inject
    BlockState toBlockState();

    @Inject
    void setBlockStateStyle(@Nullable BlockState blockState);

    @Nullable
    @Inject
    NbtMap getNbt();

    @Inject
    void setNbt(@Nullable NbtMap nbt);

    @Inject
    ItemData toNetworkItemData();

    @Nullable
    @Inject
    Integer getStackNetworkId();

    @Inject
    void setStackNetworkId(int newStackNetworkId);

    @Inject
    ItemStack copy();

    @Inject
    ItemStack copy(boolean newStackNetworkId);

    @Override
    @Inject
    boolean useItemOn(
            @Nullable EntityPlayer player, ItemStack itemStack,
            World world, Vector3ic targetBlockPos, Vector3ic placeBlockPos, Vector3fc clickPos,
            BlockFace blockFace);
}
