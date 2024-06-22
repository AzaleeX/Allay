package org.allaymc.server.block.component.shulkerbox;

import org.allaymc.api.block.BlockBehavior;
import org.allaymc.api.block.component.common.BlockEntityHolderComponent;
import org.allaymc.api.block.data.BlockStateWithPos;
import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.blockentity.interfaces.BlockEntityShulkerBox;
import org.allaymc.api.component.annotation.Dependency;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.item.ItemStack;
import org.allaymc.api.item.component.common.ItemItemStorableComponent;
import org.allaymc.server.block.component.common.BlockBaseComponentImpl;
import org.cloudburstmc.protocol.bedrock.data.GameType;

/**
 * Allay Project 2024/6/20
 *
 * @author daoge_cmd
 */
public class BlockShulkerBoxBaseComponentImpl extends BlockBaseComponentImpl {

    @Dependency
    BlockEntityHolderComponent<BlockEntityShulkerBox> blockEntityHolderComponent;

    public BlockShulkerBoxBaseComponentImpl(BlockType<? extends BlockBehavior> blockType) {
        super(blockType);
    }

    @Override
    public ItemStack[] getDrops(BlockStateWithPos blockState, ItemStack usedItem) {
        return new ItemStack[]{createShulkerBoxDrop(blockState)};
    }

    @Override
    public ItemStack getSilkTouchDrop(BlockStateWithPos blockState) {
        return createShulkerBoxDrop(blockState);
    }

    protected ItemStack createShulkerBoxDrop(BlockStateWithPos blockState) {
        var blockEntity = blockEntityHolderComponent.getBlockEntity(blockState.pos());
        var container = blockEntity.getContainer();
        var containerItems = container.saveNBT();
        var drop = blockState.blockState().toItemStack();
        ((ItemItemStorableComponent) drop).setStoredItems(containerItems);
        return drop;
    }

    @Override
    public boolean isDroppable(BlockStateWithPos blockState, ItemStack usedItem, EntityPlayer player) {
        // 2024/6/21 NOTICE
        // The data exported by BDS is not quite correct. In theory, shulker boxes can be mined with bare hands, but the exported data shows they cannot.
        // Considering the special drop logic of shulker boxes (if they contain items, they will still drop when mined in creative mode), it is suspected to be an internal bug of BDS. Therefore, isDroppable() is overridden here.
        // The data exported by BDS is not used.
        var blockEntity = blockEntityHolderComponent.getBlockEntity(blockState.pos());
        var container = blockEntity.getContainer();
        if (!container.isEmpty()) return true;
        return player.getGameType() != GameType.CREATIVE;
    }
}
