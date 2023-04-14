package cn.allay.block.component.base;

import cn.allay.block.Block;
import cn.allay.block.type.BlockType;

/**
 * Author: daoge_cmd <br>
 * Date: 2023/4/8 <br>
 * Allay Project <br>
 */
public interface BlockBaseComponent {
    BlockType<? extends Block> getBlockType();
}