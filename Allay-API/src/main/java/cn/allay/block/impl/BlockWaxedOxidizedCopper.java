package cn.allay.block.impl;

import cn.allay.block.Block;
import cn.allay.block.data.VanillaBlockId;
import cn.allay.block.type.BlockType;
import cn.allay.block.type.BlockTypeBuilder;

/**
 * Author: daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockWaxedOxidizedCopper extends Block {
    BlockType<BlockWaxedOxidizedCopper> TYPE = BlockTypeBuilder
            .builder(BlockWaxedOxidizedCopper.class)
            .vanillaBlock(VanillaBlockId.WAXED_OXIDIZED_COPPER)
            .build();
}