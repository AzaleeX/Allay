package cn.allay.api.item.interfaces;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemElement109Stack extends ItemStack {
    ItemType<ItemElement109Stack> ELEMENT_109_TYPE = ItemTypeBuilder
            .builder(ItemElement109Stack.class)
            .vanillaItem(VanillaItemId.ELEMENT_109)
            .build();
}