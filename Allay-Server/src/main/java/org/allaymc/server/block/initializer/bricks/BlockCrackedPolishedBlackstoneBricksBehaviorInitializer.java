package org.allaymc.server.block.initializer.bricks;

import org.allaymc.api.block.interfaces.bricks.BlockCrackedPolishedBlackstoneBricksBehavior;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.block.type.BlockTypes;
import org.allaymc.api.data.VanillaBlockId;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockCrackedPolishedBlackstoneBricksBehaviorInitializer {
  static void init() {
    BlockTypes.CRACKED_POLISHED_BLACKSTONE_BRICKS_TYPE = BlockTypeBuilder
            .builder(BlockCrackedPolishedBlackstoneBricksBehavior.class)
            .vanillaBlock(VanillaBlockId.CRACKED_POLISHED_BLACKSTONE_BRICKS)
            .build();
  }
}