package org.allaymc.server.entity.initializer;

import org.allaymc.api.data.VanillaEntityId;
import org.allaymc.api.entity.interfaces.EntityEvocationIllager;
import org.allaymc.api.entity.type.EntityTypeBuilder;
import org.allaymc.api.entity.type.EntityTypes;

/**
 * @author daoge_cmd <br>
 * Allay Project <br>
 */
public interface EntityEvocationIllagerInitializer {
  static void init() {
    EntityTypes.EVOCATION_ILLAGER_TYPE = EntityTypeBuilder
            .builder(EntityEvocationIllager.class)
            .vanillaEntity(VanillaEntityId.EVOCATION_ILLAGER)
            .build();
  }
}