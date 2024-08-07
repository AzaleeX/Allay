package org.allaymc.api.item.enchantment.type;

import org.allaymc.api.item.enchantment.AbstractEnchantmentType;
import org.allaymc.api.item.enchantment.Rarity;
import org.allaymc.api.utils.Identifier;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public class EnchantmentUnbreakingType extends AbstractEnchantmentType {
    public EnchantmentUnbreakingType() {
        super(new Identifier("minecraft:unbreaking"), 17, 3, Rarity.UNCOMMON);
    }
}
