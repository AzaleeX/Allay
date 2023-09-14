package cn.allay.api.item.interfaces;

import cn.allay.api.component.interfaces.ComponentProvider;
import cn.allay.api.data.VanillaItemId;
import cn.allay.api.item.ItemStack;
import cn.allay.api.item.component.base.ItemBaseComponentImpl;
import cn.allay.api.item.type.ItemStackInitInfo;
import cn.allay.api.item.type.ItemType;
import cn.allay.api.item.type.ItemTypeBuilder;


/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface ItemBlackstoneStairsStack extends ItemStack {
    ItemType<ItemBlackstoneStairsStack> BLACKSTONE_STAIRS_TYPE = ItemTypeBuilder
            .builder(ItemBlackstoneStairsStack.class)
            .vanillaItem(VanillaItemId.BLACKSTONE_STAIRS)
            .addComponent(ComponentProvider.of(initInfo -> new ItemBaseComponentImpl<>((ItemStackInitInfo<?>) initInfo), ItemBaseComponentImpl.class))
            .build();
}
