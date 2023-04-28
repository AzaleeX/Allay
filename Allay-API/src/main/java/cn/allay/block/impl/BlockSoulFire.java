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
public interface BlockSoulFire extends Block {
    BlockType<BlockSoulFire> TYPE = BlockTypeBuilder
            .builder(BlockSoulFire.class)
            .vanillaBlock(VanillaBlockId.SOUL_FIRE)
            .property(VanillaBlockPropertyTypes.AGE)
            .build();
}