package deco2800.arcade.hunter.platformerGame;

public class EntityCollision implements Comparable<Object> {
    public enum CollisionType {
        PLAYER_C_LEFT_EDGE,
        PREY_C_LEFT_EDGE,
        PREDATOR_C_LEFT_EDGE,
        ITEM_C_LEFT_EDGE,
        ITEM_C_PLAYER,
        PLAYER_PROJECTILE_C_ANIMAL,
        WORLD_PROJECTILE_C_PLAYER,
        MAP_ENTITY_C_ANIMAL,
        MAP_ENTITY_C_WORLD_PROJECTILE,
        MAP_ENTITY_C_PLAYER_PROJECTILE,
        MAP_ENTITY_C_PLAYER,
        MAP_ENTITY_C_LEFT_EDGE
    }

    private final Entity entityOne;
    private final Entity entityTwo;
    private final CollisionType type;

    public EntityCollision(Entity entityOne, Entity entityTwo, CollisionType type) {
        this.entityOne = entityOne;
        this.entityTwo = entityTwo;
        this.type = type;
    }

    public Entity getEntityOne() {
        return entityOne;
    }

    public Entity getEntityTwo() {
        return entityTwo;
    }

    public CollisionType getType() {
        return type;
    }

    @Override
    public int compareTo(Object arg0) {
        this.type.compareTo(((EntityCollision) arg0).getType());
        return 0;
    }
}
