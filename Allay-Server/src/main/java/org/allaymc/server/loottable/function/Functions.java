package org.allaymc.server.loottable.function;

import org.allaymc.api.item.ItemStack;

import java.util.List;

/**
 * Allay Project 2024/7/15
 *
 * @author daoge_cmd
 */
public record Functions(List<Function> functions) {
    public void apply(ItemStack itemStack) {
        functions.forEach(function -> function.apply(itemStack));
    }
}
