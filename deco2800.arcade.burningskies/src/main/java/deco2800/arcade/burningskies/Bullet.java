package deco2800.arcade.burningskies;

import com.badlogic.gdx.math.Vector2;

public class Bullet {
	
	public enum Type { TYPE1, TYPE2 }
	
	private boolean affinity; // friend or foe?
	private int damage;
	private Type type;
	private Vector2 velocity = new Vector2();
	private Vector2 position = new Vector2();
	
	/**
	 * Creates a new bullet
	 * @param affinity
	 * @param type
	 * @param initialSpeed
	 */
	public Bullet(boolean affinity, int damage, Type type, Vector2 initialSpeed, Vector2 initialPosition) {
		this.affinity = affinity;
		this.damage = damage;
		this.type = type;
		velocity = initialSpeed;
		position = initialPosition;
	}
}
