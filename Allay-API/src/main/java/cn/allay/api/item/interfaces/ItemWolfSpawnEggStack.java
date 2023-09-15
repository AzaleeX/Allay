package cn.allay.api.item.interfaces;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemWolfSpawnEggStack extends ItemStack {
    ItemType<ItemWolfSpawnEggStack> WOLF_SPAWN_EGG_TYPE = ItemTypeBuilder
            .builder(ItemWolfSpawnEggStack.class)
            .vanillaItem(VanillaItemId.WOLF_SPAWN_EGG)
            .build();
}