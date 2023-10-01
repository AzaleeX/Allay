package cn.allay.api.item.interfaces.egg;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemStraySpawnEggStack extends ItemStack {
  ItemType<ItemStraySpawnEggStack> STRAY_SPAWN_EGG_TYPE = ItemTypeBuilder
          .builder(ItemStraySpawnEggStack.class)
          .vanillaItem(VanillaItemId.STRAY_SPAWN_EGG)
          .build();
}