package deco2800.arcade.towerdefence.model.creationclasses;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

import deco2800.arcade.towerdefence.controller.TowerDefence;
import deco2800.arcade.towerdefence.model.Grid;
import deco2800.arcade.towerdefence.model.GridObject;
import deco2800.arcade.towerdefence.model.Melee;
import deco2800.arcade.towerdefence.model.Mobile;
import deco2800.arcade.towerdefence.model.Team;
import deco2800.arcade.towerdefence.view.GameScreen;

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
	// The files for the melee attacking animation.
	private List<String> fileAttacking;
	// The enemies anger statistic, for how likely it is to stop pathing and
	// attack a different team object randomly.
	private double anger;
	// The bounty collected for killing the enemy
	private int bounty;

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
	 * @param fileStanding
	 *            The in order list of Standing files
	 * @param fileMoving
	 *            The in order list of Movement files
	 * @param fileDying
	 *            The in order list of Dying files
	 * @param fileDeath
	 *            The in order list of Death files
	 * @param fileAttacking
	 *            The in order list of Attacking files
	 * @param anger
	 *            How likely the enemy is to stop pathing and attack nearest
	 *            other-team object.
	 * @param bounty
	 *            The bounty collected for killing the enemy
	 */
	public Enemy(int maxHealth, int armour, int x, int y, double speed,
			Grid grid, Team team, double attackRate, int damage,
			int penetration, double anger, int bounty,
			List<String> fileStanding, List<String> fileMoving,
			List<String> fileDying, List<String> fileDeath,
			List<String> fileAttacking) {
		super(maxHealth, armour, x, y, speed, grid, team, fileStanding,
				fileMoving, fileDying, fileDeath);
		this.fileAttacking = fileAttacking;
		this.attackRate = attackRate;
		this.damage = damage;
		this.penetration = penetration;
		this.anger = anger;
		this.bounty = bounty;
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
	 * Returns the bounty collection on the enemy.
	 */
	public int bounty() {
		return bounty;
	}

	/**
	 * Returns the current target of the enemy.
	 */
	public GridObject target() {
		return target;
	}

	/**
	 * Return the files for melee attacking animation.
	 */
	public List<String> fileAttacking() {
		return fileAttacking;
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
	 * Sets the bounty collected on this enemy.
	 */
	public void bounty(int bounty) {
		this.bounty = bounty;
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
	 * Sets the files for melee attacking animation.
	 */
	public void fileAttacking(List<String> files) {
		this.fileAttacking = files;
	}

	// Methods
	/**
	 * Start the AI and animations.
	 */
	public void start() {
		// Remember to adjust the rotation before building the sprite if
		// necessary
		this.rotation(0);

		if (fileStanding() != null) {
			// Build the idle sprite list
			List<Sprite> sprList = (GameScreen
					.spriteBuild(this, fileStanding()));

			// Add the list of sprites to the currently animating model
			TowerDefence.toRender.add(sprList);
		}

	}

}
