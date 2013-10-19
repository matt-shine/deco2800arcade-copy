package deco2800.arcade.towerdefence.model.creationobjects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

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
	
	
	public Tower(int maxHealth, int health, int armour, int x, int y, Grid grid, Team team, List<Sprite> sprStanding, List<Sprite> sprDying, List<Sprite> sprDeath) {
		super(maxHealth, armour, x, y, grid, team, sprStanding, sprDying, sprDeath);
		this.health = health;
		this.sprDeath = sprDeath;
		// TODO Auto-generated constructor stub
	}

	// Returns the category type of the tower.
	public TowerType type() {
		return null;
	}

	@Override
	public float attackRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void shoot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float range() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Projectile projectile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GridObject target() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sprite> shootingSprites() {
		// TODO Auto-generated method stub
		return null;
	}
}
