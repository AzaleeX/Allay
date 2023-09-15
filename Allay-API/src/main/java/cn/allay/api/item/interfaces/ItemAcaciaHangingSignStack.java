package cn.allay.api.item.interfaces;

import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemAcaciaHangingSignStack extends ItemStack {
    ItemType<ItemAcaciaHangingSignStack> ACACIA_HANGING_SIGN_TYPE = ItemTypeBuilder
            .builder(ItemAcaciaHangingSignStack.class)
            .vanillaItem(VanillaItemId.ACACIA_HANGING_SIGN)
            .build();
}