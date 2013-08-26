package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Bullet extends Image {
	
	private boolean affinity; // enemy == True friendly == False?
	private int damage;
	private Vector2 velocity;
	private Vector2 position;
	private Player player;
	
	/**
	 * Creates a new bullet
	 * @param affinity
	 * @param type
	 * @param initialSpeed
	 */
	public Bullet(boolean affinity, int damage, Vector2 initialPosition, Player player) {
		this.affinity = affinity;
		this.damage = damage;
		this.player = player; // in case of homing
		velocity = new Vector2();
		position = initialPosition;
	}
	
	@Override
    public void act(float delta) {
        super.act(delta);
        moveBullet(delta);
    }
	
	public int getDamage() {
		return damage;
	}
	
	abstract void moveBullet(float delta);

}
