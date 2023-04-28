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
public interface BlockGlowLichen extends Block {
    BlockType<BlockGlowLichen> TYPE = BlockTypeBuilder
            .builder(BlockGlowLichen.class)
            .vanillaBlock(VanillaBlockId.GLOW_LICHEN)
            .property(VanillaBlockPropertyTypes.MULTI_FACE_DIRECTION_BITS)
            .build();
}