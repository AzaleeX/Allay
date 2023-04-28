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
public interface BlockSoulLantern extends Block {
    BlockType<BlockSoulLantern> TYPE = BlockTypeBuilder
            .builder(BlockSoulLantern.class)
            .vanillaBlock(VanillaBlockId.SOUL_LANTERN)
            .property(VanillaBlockPropertyTypes.HANGING)
            .build();
}