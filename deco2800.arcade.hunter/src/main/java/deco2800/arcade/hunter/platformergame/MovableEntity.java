package deco2800.arcade.hunter.platformergame;

import com.badlogic.gdx.math.Vector2;

public abstract class MovableEntity extends Entity {

    protected Vector2 velocity;
    protected float speed;
    protected float rotation;

    public MovableEntity(float speed, float rotation, Vector2 pos, float width, float height) {
        super(pos, width, height);
        this.speed = speed;
        this.rotation = rotation;
        this.velocity = new Vector2(0, 0);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void update() {

    }
}
