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
public interface BlockChorusFlower extends Block {
    BlockType<BlockChorusFlower> TYPE = BlockTypeBuilder
            .builder(BlockChorusFlower.class)
            .vanillaBlock(VanillaBlockId.CHORUS_FLOWER)
            .property(VanillaBlockPropertyTypes.AGE)
            .build();
}