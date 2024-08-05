package org.allaymc.api.eventbus.event.world;

import org.allaymc.api.eventbus.event.CancellableEvent;
import org.allaymc.api.eventbus.event.Event;
import org.allaymc.api.world.Dimension;
import org.allaymc.api.world.chunk.Chunk;

import lombok.Getter;

/**
 * Allay Project 2024/08/03
 *
 * @author Dhaiven
 */
@Getter
public class ChunkUnloadEvent extends ChunkEvent implements CancellableEvent {
    public ChunkUnloadEvent(Dimension dimension, Chunk chunk) {
        super(dimension, chunk);
    }
}
