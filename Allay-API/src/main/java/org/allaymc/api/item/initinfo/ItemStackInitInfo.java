package org.allaymc.api.item.initinfo;

import org.allaymc.api.component.interfaces.ComponentInitInfo;
import org.allaymc.api.item.type.ItemType;
import org.cloudburstmc.nbt.NbtMap;

/**
 * @author daoge_cmd
 */
public interface ItemStackInitInfo extends ComponentInitInfo {

    int count();

    int meta();

    NbtMap extraTag();

    int stackNetworkId();

    boolean autoAssignStackNetworkId();

    ItemType<?> getItemType();

    void setItemType(ItemType<?> itemType);
}
