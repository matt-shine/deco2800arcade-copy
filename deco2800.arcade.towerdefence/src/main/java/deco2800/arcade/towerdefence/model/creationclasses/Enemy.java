package deco2800.arcade.towerdefence.model.creationclasses;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

import deco2800.arcade.towerdefence.model.Grid;
import deco2800.arcade.towerdefence.model.GridObject;
import deco2800.arcade.towerdefence.model.Melee;
import deco2800.arcade.towerdefence.model.Mobile;
import deco2800.arcade.towerdefence.model.Team;

/**
 * The class for enemies invading the ship. Every enemy is able to use Melee,
 * but only some will be able to use Ranged. Those that are Ranged should extend
 * Enemy and implement Ranged.
 * 
 * @author hadronn
 * 
 */
public class Enemy extends Mobile implements Melee {
	// Fields
	// The number of attacks per second the enemy can do.
	private double attackRate;
	// The amount of damage the enemy can inflict with an attack.
	private int damage;
	// The amount of armour the enemy's attack ignores.
	private int penetration;
	// The current target.
	private GridObject target;
	// The sprites for the melee attacking animation.
	private List<Sprite> sprAttacking;
	// The enemies anger statistic, for how likely it is to stop pathing and
	// attack a different team object randomly.
	private double anger;

	// Constructor
	/**
	 * The Enemy constructor.
	 * 
	 * @param maxHealth
	 *            The maximum health
	 * @param armour
	 *            The armour
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @param speed
	 *            The movement speed in pixels per second
	 * @param grid
	 *            The grid it occupies
	 * @param team
	 *            The team it's on
	 * @param attackRate
	 *            The number of attacks per second
	 * @param damage
	 *            The damage per attack
	 * @param penetration
	 *            The amount of armour the attack ignores
	 * @param sprStanding
	 *            The in order list of Standing Sprites
	 * @param sprMoving
	 *            The in order list of Movement Sprites
	 * @param sprDying
	 *            The in order list of Dying Sprites
	 * @param sprDeath
	 *            The in order list of Death Sprites
	 * @param sprAttacking
	 *            The in order list of Attacking Sprites
	 * @param anger
	 *            How likely the enemy is to stop pathing and attack nearest
	 *            other-team object.
	 */
	public Enemy(int maxHealth, int armour, int x, int y, double speed,
			Grid grid, Team team, double attackRate, int damage,
			int penetration, double anger, List<Sprite> sprStanding,
			List<Sprite> sprMoving, List<Sprite> sprDying,
			List<Sprite> sprDeath, List<Sprite> sprAttacking) {
		super(maxHealth, armour, x, y, speed, grid, team, sprStanding,
				sprMoving, sprDying, sprDeath);
		this.sprAttacking = sprAttacking;
		this.attackRate = attackRate;
		this.damage = damage;
		this.penetration = penetration;
		this.anger = anger;
	}

	// Getters
	/**
	 * Returns the attacks per second the enemy can make.
	 */
	public double attackRate() {
		return attackRate;
	}

	/**
	 * Returns the amount of damage per attack the enemy can do.
	 */
	public int damage() {
		return damage;
	}

	/**
	 * Returns the amount of penetration each enemy's attack has.
	 */
	public int penetration() {
		return penetration;
	}

	/**
	 * Returns the amount of anger the enemy has.
	 */
	public double anger() {
		return anger;
	}

	/**
	 * Returns the current target of the enemy.
	 */
	public GridObject target() {
		return target;
	}

	/**
	 * Return the sprites for melee attacking animation.
	 */
	public List<Sprite> sprAttacking() {
		return sprAttacking;
	}

	// Setters
	/**
	 * Sets the attacks per second the enemy can make.
	 * 
	 * @param ar
	 *            The new attack rate, in attacks per second
	 */
	public void attackRate(double ar) {
		this.attackRate = ar;
	}

	/**
	 * Sets the amount of damage per attack the enemy can do.
	 * 
	 * @param dmg
	 *            The new amount of damage dealt by each attack
	 */
	public void damage(int dmg) {
		this.damage = dmg;
	}

	/**
	 * Sets the amount of penetration each enemy's attack has.
	 * 
	 * @param pen
	 *            The armour penetration of each attack
	 */
	public void penetration(int pen) {
		this.penetration = pen;
	}

	/**
	 * Sets the amount of anger the enemy has.
	 */
	public void anger(double anger) {
		this.anger = anger;
	}

	/**
	 * Sets the current target of the enemy.
	 * 
	 * @param target
	 *            The new object to target
	 */
	public void target(GridObject target) {
		this.target = target;
	}

	/**
	 * Sets the sprites for melee attacking animation.
	 */
	public void sprAttacking(List<Sprite> sprites) {
		this.sprAttacking = sprites;
	}

	// Methods
}
