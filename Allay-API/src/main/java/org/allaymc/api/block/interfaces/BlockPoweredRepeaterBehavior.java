package org.allaymc.api.block.interfaces;

import org.allaymc.api.block.BlockBehavior;
import org.allaymc.api.block.type.BlockType;
import org.allaymc.api.block.type.BlockTypeBuilder;
import org.allaymc.api.data.VanillaBlockId;
import org.allaymc.api.data.VanillaBlockPropertyTypes;

/**
 * @author daoge_cmd | Cool_Loong <br>
 * Allay Project <br>
 */
public interface BlockPoweredRepeaterBehavior extends BlockBehavior {
  BlockType<BlockPoweredRepeaterBehavior> POWERED_REPEATER_TYPE = BlockTypeBuilder
          .builder(BlockPoweredRepeaterBehavior.class)
          .vanillaBlock(VanillaBlockId.POWERED_REPEATER)
          .setProperties(VanillaBlockPropertyTypes.MINECRAFT_CARDINAL_DIRECTION, VanillaBlockPropertyTypes.REPEATER_DELAY)
          .build();
}