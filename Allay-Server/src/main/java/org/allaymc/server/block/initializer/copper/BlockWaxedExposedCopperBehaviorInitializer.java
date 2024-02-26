package org.allaymc.server.block.initializer.copper;

import org.allaymc.api.block.interfaces.copper.BlockWaxedExposedCopperBehavior;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.block.type.BlockTypes;
import org.allaymc.api.data.VanillaBlockId;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockWaxedExposedCopperBehaviorInitializer {
  static void init() {
    BlockTypes.WAXED_EXPOSED_COPPER_TYPE = BlockTypeBuilder
            .builder(BlockWaxedExposedCopperBehavior.class)
            .vanillaBlock(VanillaBlockId.WAXED_EXPOSED_COPPER)
            .build();
  }
}