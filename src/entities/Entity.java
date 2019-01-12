package com.netflix.entities;

import com.netflix.commons.Commons;

import java.util.HashSet;
import java.util.Set;

public abstract class Entity {

  public static final Set<Entity> entities = new HashSet<>();
  private final int entityId;
  public int databaseId;

  public Entity() {
    entityId = entities.size() + 1;
    entities.add(this);
    Commons.logger.info(
        String.format("New entity, Id assigned : #%d ::%d", entityId, entities.size()));
  }

  public int getEntityId() {
    return entityId;
  }
}
