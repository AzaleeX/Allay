package org.allaymc.server.block.initializer;

import org.allaymc.api.block.interfaces.BlockMelonStemBehavior;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.block.type.BlockTypes;
import org.allaymc.api.data.VanillaBlockId;
import org.allaymc.api.data.VanillaBlockPropertyTypes;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface BlockMelonStemBehaviorInitializer {
  static void init() {
    BlockTypes.MELON_STEM_TYPE = BlockTypeBuilder
            .builder(BlockMelonStemBehavior.class)
            .vanillaBlock(VanillaBlockId.MELON_STEM)
            .setProperties(VanillaBlockPropertyTypes.FACING_DIRECTION, VanillaBlockPropertyTypes.GROWTH)
            .build();
  }
}