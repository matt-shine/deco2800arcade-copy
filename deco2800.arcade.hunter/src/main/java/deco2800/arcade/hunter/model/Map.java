package deco2800.arcade.hunter.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class Map {
    protected final Vector2 offset = new Vector2();
    protected float speedModifier;

    protected Map(float speedModifier) {
        this.speedModifier = speedModifier;
    }

    public abstract void update(float delta, Vector3 cameraPos);

    public abstract void draw(SpriteBatch batch);

    /**
     * @return xOffset
     */
    public float getXOffset() {
        return offset.x;
    }

    /**
     * Returns yOffset
     *
     * @return float yOffset
     */
    public float getYOffset() {
        return offset.y;
    }

    /**
     * Setter for speedModifier
     *
     * @param speedModifier value to assign to speedModifier
     */
    public void setSpeedModifier(float speedModifier) {
        this.speedModifier = speedModifier;
    }
}
