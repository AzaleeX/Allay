package org.allaymc.server.item.initializer.boots;

import org.allaymc.api.data.VanillaItemId;
import org.allaymc.api.item.interfaces.boots.ItemGoldenBootsStack;
import org.allaymc.api.item.type.ItemTypeBuilder;
import org.allaymc.api.item.type.ItemTypes;
import org.allaymc.server.item.component.armor.ItemArmorBaseComponentImpl;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemGoldenBootsStackInitializer {
  static void init() {
    ItemTypes.GOLDEN_BOOTS_TYPE = ItemTypeBuilder
            .builder(ItemGoldenBootsStack.class)
            .vanillaItem(VanillaItemId.GOLDEN_BOOTS)
            .addComponent(ItemArmorBaseComponentImpl::new, ItemArmorBaseComponentImpl.class)
            .build();
  }
}