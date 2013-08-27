package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Bullet extends Image {
	
	private boolean affinity; // enemy == True friendly == False?
	private int damage;
	private Vector2 velocity;
	private Vector2 position;
	private Player player;
	private Ship parent;
	
	/**
	 * Creates a new bullet
	 * @param affinity
	 * @param damage
	 * @param initialPosition
	 * @param parent
	 * @param player
	 */
	public Bullet(boolean affinity, int damage, Vector2 initialPosition, Ship parent, Player player) {
		this.affinity = affinity;
		this.damage = damage;
		this.player = player; // in case of homing
		this.parent = parent; // in case of bullets sticking close
		velocity = new Vector2();
		position = initialPosition;
	}
	
	@Override
    public void act(float delta) {
        super.act(delta);
        moveBullet(delta);
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
	public boolean getAffinity(){
		return affinity;
	}
	
	/**
	 * Move the bullet as the scene is rendered.
	 * Cannot be catch all in case bullet direction changes over time.
	 * @param delta the change of time since last render
	 */
	abstract void moveBullet(float delta);

}
