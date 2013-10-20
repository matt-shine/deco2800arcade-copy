package deco2800.arcade.towerdefence.model.creationclasses;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.towerdefence.model.Grid;
import deco2800.arcade.towerdefence.model.GridObject;
import deco2800.arcade.towerdefence.model.Mortal;
import deco2800.arcade.towerdefence.model.Ranged;
import deco2800.arcade.towerdefence.model.Team;
import deco2800.arcade.towerdefence.model.TowerType;

/**
 * The class for all Towers available throughout the game to the player. Do
 * implement the interface Ranged or, if created, AreaEffect alongside Tower.
 * 
 * @author hadronn
 * 
 */
public class Tower extends Mortal implements Ranged {
	// Fields
	// The maximum health the tower can have.
	private int maxHealth;
	// The current health the tower has.
	private int health;
	// The armour the tower has.
	private int armour;
	// The death (explosion) sprites this tower uses.
	private List<Sprite> sprDeath;
	// The type of the tower
	private TowerType type;
	// The attackRate of the tower.
	private double attackRate;
	// The range of the tower.
	private double range;
	// The projectile the tower fires.
	private Projectile projectile;
	// The cost to build the base tower.
	private int baseCost;
	// The cost to upgrade each level of the tower.
	private List<Integer> upgradeCost;
	// The cost to upgrade the
	// The current target of the tower.
	private GridObject target = null;
	// The shooting sprites of the tower.
	private List<Sprite> sprShooting;

	/**
	 * The Tower Constructor.
	 * 
	 * @param maxHealth
	 *            The maximum health
	 * @param armour
	 *            The armour
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @param grid
	 *            The grid it occupies
	 * @param team
	 *            The team it's on
	 * @param type
	 *            The tower type
	 * @param attackRate
	 *            The number of attacks per second
	 * @param range
	 *            The range at which it can acquire a target, in tiles
	 * @param projectile
	 *            The projectile it shoots. NOTE: Speed in this case should be
	 *            simply a vector with a length based on the speed of the
	 *            projectile.
	 * @param baseCost
	 *            The cost to build the base tower
	 * @param upgradeCost
	 *            A list of upgrade costs for each level of the tower
	 * @param sprStanding
	 *            The in order list of Standing sprites
	 * @param sprShooting
	 *            The in order list of Shooting sprites
	 * @param sprDeath
	 *            The in order list of Death sprites
	 */
	public Tower(int maxHealth, int armour, int x, int y, Grid grid, Team team,
			TowerType type, double attackRate, double range,
			Projectile projectile, int baseCost, List<Integer> upgradeCost,
			List<Sprite> sprStanding, List<Sprite> sprShooting,
			List<Sprite> sprDeath) {
		super(maxHealth, armour, x, y, grid, team, sprStanding, sprDeath);
		this.health = maxHealth;
		this.type = type;
		this.attackRate = attackRate;
		this.range = range;
		this.projectile = projectile;
		this.sprShooting = sprShooting;
		this.baseCost = baseCost;
		this.upgradeCost = upgradeCost;
	}

	// Getters
	/**
	 * The maximum health of the tower.
	 */
	public int maxHealth() {
		return maxHealth;
	}

	/**
	 * The current health of the tower.
	 */
	public int health() {
		return health;
	}

	/**
	 * The armour of the tower.
	 */
	public int armour() {
		return armour;
	}

	/**
	 * The Death sprites of the tower.
	 */
	public List<Sprite> sprDeath() {
		return sprDeath;
	}

	/**
	 * The Tower Type of the tower.
	 */
	public TowerType type() {
		return type;
	}

	/**
	 * The number of attacks per second the tower can make.
	 */
	public double attackRate() {
		return attackRate;
	}

	/**
	 * The range at which a tower can acquire a target.
	 */
	public double range() {
		return range;
	}

	/**
	 * The projectile the tower uses
	 */
	public Projectile projectile() {
		return projectile;
	}

	/**
	 * The base cost of the tower
	 */
	public int baseCost() {
		return baseCost;
	}

	/**
	 * The upgrade cost of the tower Index as level.
	 */
	public List<Integer> upgradeCost() {
		return upgradeCost;
	}

	/**
	 * The target the tower currently has.
	 */
	public GridObject target() {
		return target;
	}

	/**
	 * The list of sprites for the shooting animation.
	 */
	public List<Sprite> shootingSprites() {
		return sprShooting;
	}

	// Setters
	/**
	 * Sets the maximum health of the tower.
	 */
	public void maxHealth(int health) {
		this.maxHealth = health;
	}

	/**
	 * Sets the current health of the tower.
	 */
	public void health(int health) {
		this.health = health;
	}

	/**
	 * Sets the armour of the tower.
	 */
	public void armour(int armour) {
		this.armour = armour;
	}

	/**
	 * Sets the list of Death sprites for the tower.
	 */
	public void sprDeath(List<Sprite> sprites) {
		this.sprDeath = sprites;
	}

	/**
	 * Sets the Tower Type of the tower.
	 */
	public void type(TowerType type) {
		this.type = type;
	}

	/**
	 * Sets the number of attacks per second the tower can make.
	 */
	public void attackRate(double rate) {
		this.attackRate = rate;
	}

	/**
	 * Sets the range at which a tower can acquire a target, in tiles.
	 */
	public void range(double range) {
		this.range = range;
	}

	/**
	 * Sets the projectile the tower shoots.
	 */
	public void projectile(Projectile projectile) {
		this.projectile = projectile;
	}

	/**
	 * Sets the base cost of the tower
	 */
	public void baseCost(int cost) {
		this.baseCost = cost;
	}

	/**
	 * Sets the upgrade cost of the tower Index as level.
	 */
	public void upgradeCost(List<Integer> costList) {
		this.upgradeCost = costList;
	}

	/**
	 * Sets the current target of the tower
	 */
	public void target(GridObject target) {
		this.target = target;
	}

	/**
	 * Sets the list of sprites for the shooting animation
	 */
	public void shootingSprites(List<Sprite> sprites) {
		this.sprShooting = sprites;
	}

	// Methods

	/**
	 * The method for firing a single projectile at a target.
	 */
	public void shoot() {
		// Get the angle between this and the target
		float angle = target.position().cpy().sub(position()).angle();
		Vector2 projVector = projectile.speed().cpy();
		projVector.setAngle(angle);
		// Fire
		Projectile shot = new Projectile(projectile, projVector, position);
		shot.move();
	}

	public void acquireTarget() {
		Vector2 checkVector;
		Iterator<GridObject> iterator;
		GridObject current;
		// Start the search from in and search outwards to the maximum range (in
		// a square)
		for (int i = 1; i <= range; i++) {
			// For each range
			for (int j = -i; j <= i; j++) {
				// Search left to right
				for (int k = -i; k <= i; k++) {
					checkVector = positionInTiles().add(j, k);
					// Check that grid for a valid target
					iterator = grid.getGridContents((int) checkVector.x,
							(int) checkVector.y).iterator();
					while (iterator.hasNext()) {
						current = iterator.next();
						if (current instanceof Mortal
								&& current.team() != this.team) {
							target = current;
							return;
						}
					}
				}
			}
		}
	}
}
