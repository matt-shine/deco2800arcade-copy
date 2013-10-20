package deco2800.arcade.hunter.model;

import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.platformergame.Entity;

public class BackgroundSprite extends Entity {
    //Speed to move relative to the player
    public float speedModifier;

    /**
     *
     * @param pos starting position of the background sprite
     * @param width width of the entity in pixels
     * @param height height of the entity in pixels
     * @param speedModifier speed to move relative to the player
     */
    public BackgroundSprite(Vector2 pos, float width, float height, float speedModifier) {
        super(pos, width, height);
        this.speedModifier = speedModifier;
    }

    /**
     * Move the background sprite relative to the player
     * @param delta delta time since last update
     */
    @Override
    public void update(float delta) {
        setX(getX() - Hunter.State.playerVelocity.x * 0.1f * delta * speedModifier);
    }
}
