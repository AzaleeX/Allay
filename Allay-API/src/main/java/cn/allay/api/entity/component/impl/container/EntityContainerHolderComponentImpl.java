package cn.allay.api.entity.component.impl.container;

import cn.allay.api.component.annotation.Impl;
import cn.allay.api.container.Container;
import cn.allay.api.container.FullContainerType;
import cn.allay.api.entity.component.EntityComponentImpl;
import cn.allay.api.identifier.Identifier;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Allay Project 2023/7/15
 *
 * @author daoge_cmd
 */
public class EntityContainerHolderComponentImpl implements EntityContainerHolderComponent, EntityComponentImpl {

    protected static final Identifier IDENTIFIER = new Identifier("minecraft:entity_inventory_holder_component");
    protected final Map<FullContainerType, Container> containers = new HashMap<>();

    public EntityContainerHolderComponentImpl() {
    }

    public EntityContainerHolderComponentImpl(Container... containers) {
        for (Container container : containers) {
            this.addContainer(container);
        }
    }

    @Override
    @Impl
    public @UnmodifiableView Map<FullContainerType, Container> getContainers() {
        return Collections.unmodifiableMap(containers);
    }

    @Override
    @Nullable
    @Impl
    public Container getContainer(FullContainerType type) {
        return containers.get(type);
    }

    @Override
    @Impl
    public void addContainer(Container container) {
        if (containers.containsKey(container.getClass()))
            throw new IllegalArgumentException("Inventory " + container.getClass().getSimpleName() + "already exists");
        containers.put(container.getContainerType(), container);
    }

    @Override
    public Identifier getIdentifier() {
        return IDENTIFIER;
    }
}