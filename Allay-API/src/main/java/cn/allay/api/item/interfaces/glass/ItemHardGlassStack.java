package cn.allay.api.item.interfaces.glass;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemHardGlassStack extends ItemStack {
  ItemType<ItemHardGlassStack> HARD_GLASS_TYPE = ItemTypeBuilder
          .builder(ItemHardGlassStack.class)
          .vanillaItem(VanillaItemId.HARD_GLASS)
          .build();
}