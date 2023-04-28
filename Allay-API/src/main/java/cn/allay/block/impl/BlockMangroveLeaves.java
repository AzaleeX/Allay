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
public interface BlockMangroveLeaves extends Block {
    BlockType<BlockMangroveLeaves> TYPE = BlockTypeBuilder
            .builder(BlockMangroveLeaves.class)
            .vanillaBlock(VanillaBlockId.MANGROVE_LEAVES)
            .property(VanillaBlockPropertyTypes.PERSISTENT_BIT,
                    VanillaBlockPropertyTypes.UPDATE_BIT)
            .build();
}