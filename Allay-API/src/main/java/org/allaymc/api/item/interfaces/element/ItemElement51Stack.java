package org.allaymc.api.item.interfaces.element;

import org.allaymc.api.data.VanillaItemId;
import org.allaymc.api.item.ItemStack;
import org.allaymc.api.item.type.ItemType;
import org.allaymc.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemElement51Stack extends ItemStack {
  ItemType<ItemElement51Stack> ELEMENT_51_TYPE = ItemTypeBuilder
          .builder(ItemElement51Stack.class)
          .vanillaItem(VanillaItemId.ELEMENT_51)
          .build();
}