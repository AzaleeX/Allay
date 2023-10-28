package cn.allay.api.item;

import cn.allay.api.identifier.Identifier;
import cn.allay.api.item.init.SimpleItemStackInitInfo;
import cn.allay.api.item.registry.ItemTypeRegistry;
import org.cloudburstmc.nbt.NbtMap;

import java.util.Objects;

/**
 * Allay Project 2023/9/23
 *
 * @author daoge_cmd
 */
public final class ItemHelper {
    public static ItemStack fromNBT(NbtMap nbt) {
        int count = nbt.getByte("Count");
        int meta = nbt.getShort("Damage");
        var name = nbt.getString("Name");
        var itemType = Objects.requireNonNull(ItemTypeRegistry.getRegistry().get(new Identifier(name)), "Unknown item type " + name + "while loading container items!");
        return itemType.createItemStack(
                SimpleItemStackInitInfo
                        .builder()
                        .count(count)
                        .meta(meta)
                        .extraTag(nbt.getCompound("tag"))
                        .build());
    }
}
