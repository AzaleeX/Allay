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
public interface BlockCrimsonButton extends Block {
    BlockType<BlockCrimsonButton> TYPE = BlockTypeBuilder
            .builder(BlockCrimsonButton.class)
            .vanillaBlock(VanillaBlockId.CRIMSON_BUTTON)
            .property(VanillaBlockPropertyTypes.BUTTON_PRESSED_BIT,
                    VanillaBlockPropertyTypes.FACING_DIRECTION)
            .build();
}