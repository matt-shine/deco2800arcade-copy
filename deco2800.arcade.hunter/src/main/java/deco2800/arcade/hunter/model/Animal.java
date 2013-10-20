package deco2800.arcade.hunter.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.platformergame.Entity;
import deco2800.arcade.hunter.platformergame.EntityCollection;
import deco2800.arcade.hunter.platformergame.EntityCollision;
import deco2800.arcade.hunter.platformergame.EntityCollision.CollisionType;
import deco2800.arcade.hunter.screens.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;

public class Animal extends Entity {
    /**
     * The speed at which the Animal moves
     */
    private int moveSpeed;

    /**
     * State of the Animal
     */
    private State state = State.IDLE;

    /**
     * The current animation of the animal
     */
    private Animation currentAnimation;

    private long timeOfDeath;
    //GameScreen in which the Animal is located
    private final GameScreen gameScreen;
    private final String animalType;

    public Animal(Vector2 pos, float width, float height, String animalType,
                  Animation currentAnimation, GameScreen game) {
        super(pos, width, height);
        setPosition(pos);
        this.currentAnimation = currentAnimation;
        this.gameScreen = game;
        this.animalType = animalType;

        //Set the speed based upon whether the animal is predator or prey
        moveSpeed = (animalType.equals("zebra")) ? 4 : -2;
    }


    private enum State {
        DEAD, IDLE
    }

    /**
     * Update the state of the animalType
     * @param delta delta time since the last call to update
     */
    public void update(float delta) {
        //Move the animalType
        setX(getX() + moveSpeed);
        setY(getY() - Hunter.State.gravity);

        //Removes the entity after a certain period, determined by timeOfDeath and timeout.
        if (this.state == State.DEAD) {
            if (timeOfDeath + Hunter.Config.PLAYER_BLINK_TIMEOUT <= System.currentTimeMillis()) {
                Iterator it = gameScreen.getEntities().iterator();
                while (it.hasNext()) {
                    if (it.next().equals(this))
                        it.remove();
                }
            }
        }
    }

    /**
     * @return whether the animal is dead.
     */
    public boolean isDead() {
        return state == State.DEAD;
    }

    /**
     * Handle the animal dying
     */
    public void dead() {
        //draw a dead animalType sprite
        moveSpeed = 0;
        this.state = State.DEAD;
        timeOfDeath = System.currentTimeMillis();
        currentAnimation = gameScreen.entityHandler.getAnimalAnimation(animalType + "DEAD");
    }

    /**
     * Checks for collisions against all other entities in the given collection
     * of entities.
     *
     * @param entities a collection of all entities which you want to check
     *                 for collisions against.
     */
    @Override
    public ArrayList<EntityCollision> getCollisions(EntityCollection entities) {
        ArrayList<EntityCollision> collisions = new ArrayList<EntityCollision>();
        for (Entity e : entities) {
            if (this.getX() <= 0)
                collisions.add(new EntityCollision(this, null,
                        CollisionType.PREDATOR_C_LEFT_EDGE));
            if (this.getBounds().overlaps(e.getBounds())) {
                if (e.getType().equals("MapEntity")) {
                    collisions.add(new EntityCollision(this, e, CollisionType.MAP_ENTITY_C_ANIMAL));
                }
            }
        }
        return collisions;
    }

    /**
     * Handle a collision with an entity
     * @param e the entity this animal collided with
     * @param entities the collection of all entities, which contains both this
     *                 entity and the collided entity.
     */
    @Override
    public void handleCollision(Entity e, EntityCollection entities) {
        if (e == null) {
            entities.remove(this);
        } else if (e.getType().equals("MapEntity") && this.state != State.DEAD) {
            if (((MapEntity) e).getEntityType().equals("arrow")) {
                this.dead();
                gameScreen.addScore(200);
                gameScreen.addAnimalKilled();
                entities.remove(e);
            } else if (((MapEntity) e).getEntityType().equals("bomb")) {
                ((MapEntity)e).explode();
            } else if (((MapEntity) e).getEntityType().equals("explosion")) {
                this.dead();
            }
        }
    }

    /**
     * @return the class type of the entity
     */
    @Override
    public String getType() {
        return "Animal";
    }

    /**
     * Draw the animal in its current state and animation frame.
     * @param batch batch to draw to
     * @param stateTime animation state time
     */
    @Override
    public void draw(SpriteBatch batch, float stateTime) {
        boolean loopAnimation = state != State.DEAD;

        TextureRegion currFrame = currentAnimation.getKeyFrame(stateTime, loopAnimation);
        batch.draw(currFrame, getX(), getY(), getWidth(), getHeight());
    }
}
