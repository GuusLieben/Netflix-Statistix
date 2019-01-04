package com.netflix.entities.abstracts;

import com.netflix.commons.*;

import java.util.*;

public abstract class Entity {

  public static Set<Entity> entities = new HashSet<>();
  public int entityId;

  public int databaseId;

  public Entity() {
    entityId = getEntityCount() + 1;
    entities.add(this);
    Commons.logger.info(
        String.format("New entity, Id assigned : #%d ::%d", getEntityId(), entities.size()));
  }

  // Get total amount of entities
  public static int getEntityCount() {
    return entities.size();
  }

  // Get entity by the local id
  public static Entity getEntityById(int id) {
    return entities.stream().filter(entity -> entity.entityId == id).findFirst().orElse(null);
  }

  // Get id for current entity
  public int getEntityId() {
    return entityId;
  }
}
