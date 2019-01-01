/*
 * Copyright © 2018. Guus Lieben.
 * All rights reserved.
 */

package com.netflix.entities.abstracts;

import java.util.*;

public abstract class Entity {

    public static Set<Entity> entities = new HashSet<>();
    public int entityId;

    public int databaseId;

    public Entity() {
        entityId = getEntityCount() + 1;
        entities.add(this);
        System.out.println("New entity, Id assigned : #" + getEntityId());
    }

    public static int getEntityCount() {
        return entities.size();
    }

    public static Entity getEntityById(int id) {
        return entities.stream().filter(entity -> entity.entityId == id).findFirst().orElse(null);
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public String toString() {
        return "Entity{" + "entityId=" + entityId + '}';
    }
}
