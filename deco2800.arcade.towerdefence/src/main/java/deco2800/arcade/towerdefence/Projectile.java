package deco2800.arcade.towerdefence;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * The interface for GridObjects that can fly on any angle from a source
 * GridObject towards its target.
 * 
 * @author hadronn
 * 
 */
public class Projectile extends Mobile implements Divine {
	// Fields
	// The amount of damage this projectile does if it collides.
	private int damage;
	// The amount of armour this projectile ignores.
	private int penetration;

	// Constructor

	// Getters
	public int damage() {
		return 0;
	}

	// Setters

	// Methods
	public void die() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGameState() {
		// TODO Auto-generated method stub

	}
}
