package cn.allay.block.impl;

import cn.allay.block.Block;
import cn.allay.block.data.VanillaBlockId;
import cn.allay.block.property.vanilla.VanillaBlockPropertyTypes;
import cn.allay.block.type.BlockType;
import cn.allay.block.type.BlockTypeBuilder;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockMudBrickSlab extends Block {
    BlockType<BlockMudBrickSlab> TYPE = BlockTypeBuilder
            .builder(BlockMudBrickSlab.class)
            .vanillaBlock(VanillaBlockId.MUD_BRICK_SLAB)
            .property(VanillaBlockPropertyTypes.TOP_SLOT_BIT)
            .build();
}