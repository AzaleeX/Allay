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
public interface BlockStandingBanner extends Block {
    BlockType<BlockStandingBanner> TYPE = BlockTypeBuilder
            .builder(BlockStandingBanner.class)
            .vanillaBlock(VanillaBlockId.STANDING_BANNER)
            .property(VanillaBlockPropertyTypes.GROUND_SIGN_DIRECTION)
            .build();
}