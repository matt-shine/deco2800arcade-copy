package deco2800.arcade.towerdefence.model.creationclasses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.towerdefence.controller.TowerDefence;
import deco2800.arcade.towerdefence.model.Grid;
import deco2800.arcade.towerdefence.model.GridObject;
import deco2800.arcade.towerdefence.model.Mortal;
import deco2800.arcade.towerdefence.model.Team;
import deco2800.arcade.towerdefence.model.effects.Effect;
import deco2800.arcade.towerdefence.view.GameScreen;

/**
 * The class for GridObjects that can fly on any angle from a source GridObject
 * towards its target.
 * 
 * @author hadronn
 * 
 */
public class Projectile extends GridObject {
	// Fields
	// The amount of damage this projectile does if it collides.
	private int damage;
	// The amount of armour this projectile ignores.
	private int penetration;
	// The projectile's speed and direction, in pixels per second.
	private Vector2 speed;
	// The projectile's range
	private float range;
	// The projectile's explosion radius, the number of Grid squares outward
	// around the colliding GridObject that are damaged.
	// 0 means no area of effect, just the one square.
	private int explosionRadius;

	// Constructor
	/**
	 * The Projectile constructor.
	 * 
	 * @param x
	 *            The starting x position of the object
	 * @param y
	 *            The starting y position of the object
	 * @param grid
	 *            The grid the object belongs to
	 * @param speed
	 *            The speed and direction of the object in pixels per second,
	 *            represented as a vector
	 * @param range
	 *            The maximum distance the projectile can travel.
	 * @param team
	 *            The team the projectile belongs to.
	 * @param fileStanding
	 *            The file used by the projectile.
	 * @param damage
	 *            The damage the projectile deals on impact.
	 * @param penetration
	 *            The penetration of the projectile against armoured targets.
	 * @param explosionRadius
	 *            The explosion radius of the projectile.
	 */
	public Projectile(int x, int y, Grid grid, Vector2 speed, float range,
			Team team, List<String> fileStanding, int damage, int penetration,
			int explosionRadius) {
		super(x, y, grid, team, fileStanding);
		this.speed = speed;
		this.range = range;
		this.damage = damage;
		this.penetration = penetration;
		this.explosionRadius = explosionRadius;
	}

	/**
	 * Constructor used by towers when shooting.
	 * 
	 * @param other
	 *            The Projectile given by the tower to copy.
	 * @param speed
	 *            The speed and direction of this projectile.
	 */
	public Projectile(Projectile other, Vector2 speed, Vector2 position) {
		super((int) position.x, (int) position.y, other.grid(), other.team(),
				other.fileStanding());
		this.speed = speed;
		this.range = other.range;
		this.damage = other.damage;
		this.penetration = other.penetration;
		this.explosionRadius = other.explosionRadius;
	}

	// Getters
	/**
	 * Returns the damage the projectile inflicts on collision.
	 * 
	 * @return The damage dealt by the projectile.
	 */
	public int damage() {
		return damage;
	}

	/**
	 * Returns the penetration amount on the projectile. The amount of armour
	 * this projectile ignores.
	 * 
	 * @return The armour penetration of the projectile.
	 */
	public int penetration() {
		return penetration;
	}

	/**
	 * Returns the number of squares around the colliding GridObject that also
	 * take damage.
	 */
	public int explosionRadius() {
		return explosionRadius;
	}

	/**
	 * Return a copy of this projectile's speed/direction vector.
	 * 
	 * @return A vector representing the speed and direction of this projectile.
	 */
	public Vector2 speed() {
		return speed.cpy();
	}

	// Setters
	/**
	 * Set the damage this projectile inflicts on collision.
	 * 
	 * @param damage
	 *            The new damage dealt by the projectile.
	 */
	public void damage(int damage) {
		this.damage = damage;
	}

	/**
	 * Set the amount of penetration on the projectile.
	 * 
	 * @param penetration
	 *            The new penetration of the projectile.
	 */
	public void penetration(int penetration) {
		this.penetration = penetration;
	}

	/**
	 * Set the number of squares around the colliding GridObject that also take
	 * damage.
	 */
	public void explosionRadius(int amount) {
		this.explosionRadius = amount;
	}

	// Methods
	/**
	 * Start the AI and animations.
	 */
	public void start() {
	// Remember to adjust the rotation before building the sprite if necessary
	this.rotation(0);
	
	// Build the idle sprite list
	List<Sprite> sprList = (GameScreen.spriteBuild(this, fileStanding()));
	
	// Add the list of sprites to the currently animating model
	TowerDefence.toRender.add(sprList);
	}
	
	/**
	 * Continually move in the given vector until a collision, or the maximum
	 * range is reached.
	 */
	public void move() {
		// Move at speed in the object's direction until it collides with
		// something
		float moved = 0;
		long t0, t1;
		Vector2 addVector = speed.cpy();
		addVector.mul((float) 1 / 30);
		while (moved < range) {
			// Check for collisions
			for (int i = 0; i < getCurrentGrid().size(); i++) {
				if (getCurrentGrid().get(i).team() != this.team) {
					// Collision occurred
					collide(getCurrentGrid().get(i));
					return;
				}
			}

			t0 = System.currentTimeMillis();
			t1 = t0;
			// Move
			position.add(addVector);
			moved += addVector.len();
			// Wait 1/30th of a second before moving again
			while (t1 - t0 < 33) {
				t1 = System.currentTimeMillis();
			}
		}

		// do nothing for now TODO
	}

	/**
	 * Models collision, effect application and damage calculations when the
	 * projectile strikes a GridObject.
	 */
	private void collide(GridObject collidingObject) {
		// create a hit list
		List<GridObject> hitList = new ArrayList<GridObject>();
		// determine targets hit with explosionRadius
		int r = explosionRadius;
		// find the tile the colldingObject is sitting on.
		Vector2 centreTile = collidingObject.positionInTiles();
		// find the top left square of the area to hit.
		Vector2 startingTile = new Vector2(centreTile.x - r, centreTile.y + r);
		// iterate through squares r away from centre tile in a square
		for (int i = 0; i <= r * 2; i++) {
			for (int j = 0; j <= r * 2; j++) {
				// make an iterator for the contents of each square
				Iterator<GridObject> contents = grid.getGridContents(
						(int) (startingTile.x + i), (int) (startingTile.y - j))
						.iterator();
				// iterate through
				while (contents.hasNext()) {
					GridObject element = contents.next();
					// check for friendly fire
					if (element.team() != this.team()
							&& element instanceof Mortal) {
						// not friendly fire, can bleed so add to hit list.
						hitList.add(element);
					}
				}
			}
		}
		// hit list created
		// if has projectile has no effects just apply damage
		if (!this.canApplyStatusEffects()) {
			// iterate through hitList
			for (int i = 0; i < hitList.size(); i++) {
				((Mortal) hitList.get(i)).takeDamage(damage);
			}
		} else {
			// Get the list of effects
			List<Effect> effectList = this.effects();
			// iterate through effectList
			for (int i = 0; i < effectList.size(); i++) {
				// Get the effect
				Effect effect = effectList.get(i);
				// iterate through hitList
				for (int j = 0; j < hitList.size(); i++) {
					// get the GridObject
					GridObject victim = hitList.get(j);
					// apply the effect to the victim
					effect.function(victim);
					// and then apply damage
					((Mortal) hitList.get(i)).takeDamage(damage);
				}
			}
		}
	}
}
