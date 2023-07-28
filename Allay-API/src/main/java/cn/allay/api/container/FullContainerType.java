package cn.allay.api.container;

import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType;

import java.util.EnumMap;
import java.util.Map;

/**
 * Allay Project 2023/7/27
 *
 * @author daoge_cmd
 *
 */
public record FullContainerType(int id, boolean canBeOpenedAlone, ContainerSlotType... slotTypes) {
    public FullContainerType(int id, boolean canBeOpenedAlone, ContainerSlotType... slotTypes) {
        this.id = id;
        this.canBeOpenedAlone = canBeOpenedAlone;
        this.slotTypes = slotTypes;
        for (ContainerSlotType slotType : slotTypes) {
            var mapped = SLOT_TYPE_TO_TYPE_MAP.get(slotType);
            if (mapped == null) {
                SLOT_TYPE_TO_TYPE_MAP.put(slotType, this);
                continue;
            }
            if (!mapped.equals(this))
                throw new IllegalArgumentException("Slot type " + slotType + " is already mapped to " + SLOT_TYPE_TO_TYPE_MAP.get(slotType));
        }
    }

    public static FullContainerType fromSlotType(ContainerSlotType type) {
        return SLOT_TYPE_TO_TYPE_MAP.get(type);
    }

    FullContainerType(ContainerType type) {
        this(type.getId(), true);
    }

    FullContainerType(ContainerType type, ContainerSlotType... slotTypes) {
        this(type.getId(), true, slotTypes);
    }

    FullContainerType(int id) {
        this(id, false);
    }

    FullContainerType(int id, ContainerSlotType... slotTypes) {
        this(id, false, slotTypes);
    }

    public ContainerType toNetworkType() {
        return ContainerType.from(id);
    }

    public static final Map<ContainerSlotType, FullContainerType> SLOT_TYPE_TO_TYPE_MAP = new EnumMap<>(ContainerSlotType.class);

    public static final int UNKNOWN_NETWORK_ID = -1;

    public static final FullContainerType CURSOR = new FullContainerType(
            119,
            ContainerSlotType.CURSOR);
    public static final FullContainerType ARMOR = new FullContainerType(
            ContainerType.ARMOR,
            ContainerSlotType.ARMOR);
    public static final FullContainerType OFFHAND = new FullContainerType(
            120,
            ContainerSlotType.OFFHAND);
    public static final FullContainerType PLAYER_INVENTORY = new FullContainerType(
            ContainerType.INVENTORY,
            ContainerSlotType.INVENTORY, ContainerSlotType.HOTBAR, ContainerSlotType.HOTBAR_AND_INVENTORY);
    public static final FullContainerType CREATED_OUTPUT = new FullContainerType(
            UNKNOWN_NETWORK_ID,
            ContainerSlotType.CREATED_OUTPUT
    );
}