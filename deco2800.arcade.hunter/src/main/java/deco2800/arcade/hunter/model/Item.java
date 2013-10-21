package deco2800.arcade.hunter.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.hunter.platformerGame.Entity;
import deco2800.arcade.hunter.platformerGame.EntityCollection;
import deco2800.arcade.hunter.platformerGame.EntityCollision;
import deco2800.arcade.hunter.platformerGame.EntityCollision.CollisionType;

import java.util.ArrayList;
import java.util.List;

public class Item extends Entity {

    /**
     * The Texture of the item
     */
    private final Texture texture;

    /**
     * Name of the item
     */
    private final String item;

    //Item type
    public enum Type {
        WEAPON, POWERUP
    }

    /**
     * Type of item: Weapon or Power up
     */
    private final Type type;

    public Item(Vector2 pos, float width, float height, String item, Texture text) {
        super(pos, width, height);
        this.item = item;
        this.texture = text;
        //Checks the item type
        if (item.equals("DoublePoints") || item.equals("ExtraLife") || item.equals("Invulnerability") || item.equals("Coin")) {
            this.type = Type.POWERUP;
        } else {
            this.type = Type.WEAPON;
        }


    }

    /**
     * Checks collisions with other entities in the given collection of entities
     * @param entities collection of entities to search for collisions within
     */
    @Override
    public List<EntityCollision> getCollisions(EntityCollection entities) {
        List<EntityCollision> collisions = new ArrayList<EntityCollision>();
        //Check if this entity collides with the left edge of the screen
        if (this.getX() <= 0) {
            collisions.add(new EntityCollision(this, null, CollisionType.ITEM_C_LEFT_EDGE));
        }
        return collisions;
    }

    /**
     * Handles collisions with other entities
     * @param e the entity this item collided with.
     * @param entities the collection of entities which contains both this
     *                 item and the entity it collided with.
     *
     */
    @Override
    public void handleCollision(Entity e, EntityCollection entities) {
        if (e == null) {
            entities.remove(this);
        }
    }

    /**
     * Draw the item in it's current position
     * @param batch SpriteBatch to draw the item to
     * @param stateTime currently unused by this entity type
     */
    @Override
    public void draw(SpriteBatch batch, float stateTime) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    /**
     * @return the class type of this entity
     */
    @Override
    public String getType() {
        return "Items";
    }

    /**
     * @return whether this is a weapon or a powerup
     */
    public Type getItemType() {
        return type;
    }

    /**
     * @return the name of this item
     */
    public String getItem() {
        return item;
    }

    /**
     * To be called any time an instance of this class is destroyed.
     */
    public void dispose() {
        texture.dispose();
    }
}
