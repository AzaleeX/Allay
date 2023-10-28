package cn.allay.api.entity.effect.type;

import cn.allay.api.entity.effect.AbstractEffectType;
import cn.allay.api.identifier.Identifier;

import java.awt.*;

/**
 * Allay Project 2023/10/27
 *
 * @author daoge_cmd
 */
public class EffectSlowFallingType extends AbstractEffectType {
    public static final EffectSlowFallingType SLOW_FALLING_TYPE = new EffectSlowFallingType();
    private EffectSlowFallingType() {
        super(27, new Identifier("minecraft:slow_falling"), new Color(206, 255, 255));
    }
}