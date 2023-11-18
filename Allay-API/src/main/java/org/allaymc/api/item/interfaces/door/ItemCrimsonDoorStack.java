package org.allaymc.api.item.interfaces.door;

import org.allaymc.api.data.VanillaItemId;
import org.allaymc.api.item.ItemStack;
import org.allaymc.api.item.type.ItemType;
import org.allaymc.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemCrimsonDoorStack extends ItemStack {
  ItemType<ItemCrimsonDoorStack> CRIMSON_DOOR_TYPE = ItemTypeBuilder
          .builder(ItemCrimsonDoorStack.class)
          .vanillaItem(VanillaItemId.CRIMSON_DOOR)
          .build();
}