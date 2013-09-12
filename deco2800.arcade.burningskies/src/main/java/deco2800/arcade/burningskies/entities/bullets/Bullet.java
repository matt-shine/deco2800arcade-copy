package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;

public abstract class Bullet extends Image {
	
	public enum Affinity {
		PLAYER,
		ENEMY
	}
	protected Affinity affinity;
	protected int damage;
	protected Vector2 velocity;
	protected Vector2 position;
	protected Vector2 acceleration;
	protected float direction;
	protected PlayerShip player;
	protected Ship parent;
	
	/**
	 * Creates a new bullet
	 * @param affinity
	 * @param damage
	 * @param initialPosition
	 * @param parent
	 * @param player
	 */
	public Bullet(Affinity affinity, int damage, Ship parent, PlayerShip player, Vector2 initialPosition, float initialDirection, Texture image) {
		super(image);
		this.affinity = affinity;
		this.damage = damage;
		this.player = player; // in case of homing
		this.parent = parent; // in case of bullets sticking close
		velocity = new Vector2(0,0);
		acceleration = new Vector2(0,0);
		position = initialPosition;
		direction = initialDirection;
	}
	
	@Override
    public void act(float delta) {
		float left, right;
		moveBullet(delta);
        super.act(delta);
        // Check we're in bounds, if not goodbye
		left = getX() + getWidth();
		right = getY() + getHeight();
		// 10 pixels in case they're flying off the sides
		if(left < -10 || right < -10 || getX() > getStage().getWidth() + 10 || getY() > getStage().getHeight() + 10) {
			remove();
		}
    }
	
	/**
	 * @return The damage this bullet will inflict
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Checks whether the bullet is friend or foe.
	 * 
	 * @return true if an enemy bullet, false if a friendly bullet
	 */
	public Affinity getAffinity(){
		return affinity;
	}
	
	/**
	 * Move the bullet as the scene is rendered.
	 * Override this if the bullet direction changes over time.
	 * @param delta the change of time since last render
	 */
	void moveBullet(float delta) {
		velocity.add( acceleration.x * delta, acceleration.y * delta );
		velocity.setAngle(direction);
		position.add( velocity.x * delta, velocity.y * delta );
		setX(position.x);
		setY(position.y);
		setRotation(direction);
	}

}
