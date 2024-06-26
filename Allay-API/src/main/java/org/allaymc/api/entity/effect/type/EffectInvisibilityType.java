package org.allaymc.api.entity.effect.type;

import org.allaymc.api.entity.effect.AbstractEffectType;
import org.allaymc.api.utils.Identifier;

import java.awt.*;

/**
 * Allay Project 2023/10/27
 *
 * @author daoge_cmd
 */
public class EffectInvisibilityType extends AbstractEffectType {
    public static final EffectInvisibilityType INVISIBILITY_TYPE = new EffectInvisibilityType();

    private EffectInvisibilityType() {
        super(14, new Identifier("minecraft:invisibility"), new Color(246, 246, 246));
    }
}
