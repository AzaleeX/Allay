package org.allaymc.server.block.initializer;

import org.allaymc.api.block.interfaces.BlockBlueIceBehavior;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.block.type.BlockTypes;
import org.allaymc.api.data.VanillaBlockId;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockBlueIceBehaviorInitializer {
  static void init() {
    BlockTypes.BLUE_ICE_TYPE = BlockTypeBuilder
            .builder(BlockBlueIceBehavior.class)
            .vanillaBlock(VanillaBlockId.BLUE_ICE)
            .build();
  }
}