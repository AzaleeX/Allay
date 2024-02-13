package org.allaymc.api.block.interfaces.stainedglass;

import org.allaymc.api.block.BlockBehavior;
import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.data.VanillaBlockId;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockHardRedStainedGlassBehavior extends BlockBehavior {
  BlockType<BlockHardRedStainedGlassBehavior> HARD_RED_STAINED_GLASS_TYPE = BlockTypeBuilder
          .builder(BlockHardRedStainedGlassBehavior.class)
          .vanillaBlock(VanillaBlockId.HARD_RED_STAINED_GLASS)
          .build();
}