package org.allaymc.api.eventbus.event.entity;

import org.allaymc.api.entity.Entity;

/**
 * @author daoge_cmd
 */
public class EntityDieEvent extends EntityEvent {
    public EntityDieEvent(Entity entity) {
        super(entity);
    }
}
