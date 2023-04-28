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
public interface BlockPurpleGlazedTerracotta extends Block {
    BlockType<BlockPurpleGlazedTerracotta> TYPE = BlockTypeBuilder
            .builder(BlockPurpleGlazedTerracotta.class)
            .vanillaBlock(VanillaBlockId.PURPLE_GLAZED_TERRACOTTA)
            .property(VanillaBlockPropertyTypes.FACING_DIRECTION)
            .build();
}