package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.math.Vector2;

public class Bullet {
	
	private boolean affinity; // enemy == True friendly == False?
	private int damage;
	private Vector2 velocity = new Vector2();
	private Vector2 position = new Vector2();
	
	/**
	 * Creates a new bullet
	 * @param affinity
	 * @param type
	 * @param initialSpeed
	 */
	public Bullet(boolean affinity, int damage, Vector2 initialSpeed, Vector2 initialPosition) {
		this.affinity = affinity;
		this.damage = damage;
		velocity = initialSpeed;
		position = initialPosition;
	}
}
