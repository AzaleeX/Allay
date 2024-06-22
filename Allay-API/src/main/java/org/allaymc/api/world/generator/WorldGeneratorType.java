package org.allaymc.api.world.generator;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Allay Project 2023/6/3
 *
 * @author daoge_cmd
 */
@Getter
@AllArgsConstructor
public enum WorldGeneratorType {
    LIMIT(0),
    INFINITE(1),
    FLAT(2),
    NETHER(3),
    THE_END(4);

    /**
     * The generator id
     */
    private final int id;

    public static WorldGeneratorType of(int id) {
        Preconditions.checkArgument(id >= 0 && id <= 4);
        return values()[id];
    }
}
