package cn.allay.api.item.enchantment.type;

import cn.allay.api.identifier.Identifier;
import cn.allay.api.item.enchantment.Rarity;
import cn.allay.api.item.enchantment.AbstractEnchantmentType;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public class EnchantmentSilkTouchType extends AbstractEnchantmentType {
    public static final EnchantmentSilkTouchType SILK_TOUCH_TYPE = new EnchantmentSilkTouchType();
  private EnchantmentSilkTouchType() {
    super(new Identifier("minecraft:silk_touch"), 16, 1, Rarity.VERY_RARE);
  }
}