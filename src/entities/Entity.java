package com.netflix.entities;

import com.netflix.commons.Commons;

import java.util.*;

public abstract class Entity {

  public static final Set<Entity> entities = new HashSet<>();
  private final int entityId;
  public int databaseId;
  private static Set<Class> instances = new HashSet<>();
  private String childName;
  private Object primaryIdentifier;
  private Object secondaryIdentifier;

  public Object getPrimaryIdentifier() {
    return primaryIdentifier;
  }

  public Object getSecondaryIdentifier() {
    return secondaryIdentifier;
  }

  public String getChildName() {
    return childName;
  }

  public Entity(Object primaryId, Object secondaryId) {
    entityId = entities.size() + 1;
    entities.add(this);

    childName = this.getClass().getSimpleName();
    primaryIdentifier = primaryId;
    secondaryIdentifier = secondaryId;

    Commons.logger.info(
        String.format(
            "New entity with instance '%s' \t(%d)\t#1 : %s\t#2 : %s",
            this.getClass().getSimpleName(), entityId, primaryId, secondaryId));
    try {
      instances.add(Class.forName(this.getClass().getName()));
    } catch (ClassNotFoundException ex) {
      Commons.exception(ex);
    }
  }

  public int getEntityId() {
    return entityId;
  }
}
