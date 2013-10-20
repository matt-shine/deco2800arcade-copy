package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.Entity;
import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;

public abstract class Bullet extends Entity {

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
	 * @param initialPosition the unrotated initial position - this will be adjusted for rotation automatically
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
		position.sub(parent.getCenterX(), parent.getCenterY());
		position.rotate(parent.getRotation());
		position.add(parent.getCenterX(), parent.getCenterY());
		position.sub(getWidth()/2, getHeight()/2); // bullet has dimensions too
		direction = initialDirection;
		setPosition(position.x, position.y);
		setRotation(direction);
	}
	
	@Override
    public void act(float delta) {
		moveBullet(delta);
        super.act(delta);
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
