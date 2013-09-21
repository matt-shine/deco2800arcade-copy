package deco2800.arcade.wl6;

/**
 * Describes a thing. A thing can be an enemy, a powerup, a gun, or many at the same time
 * @author Simon
 *
 */
public class DoodadInfo {
	
	
	public DoodadInfo() {
	}
	

	public static DoodadInfo treasurePickup(String texture, int value) {
		return new DoodadInfo(false, texture, value, 0, 0,
				0, EnemyType.NOT_AN_ENEMY, 0);
	}
	
	public static DoodadInfo healthPickup(String texture, int value, int difficulty) {
		return new DoodadInfo(false, texture, 0, 0, value,
				difficulty, EnemyType.NOT_AN_ENEMY, 0);
	}
	
	public static DoodadInfo ammoPickup(String texture, int value, int difficulty) {
		return new DoodadInfo(false, texture, 0, value, 0,
				difficulty, EnemyType.NOT_AN_ENEMY, 0);
	}
	
	public static DoodadInfo gunPickup(String texture, int what, int difficulty) {
		return new DoodadInfo(false, texture, 0, 0, 0,
				difficulty, EnemyType.NOT_AN_ENEMY, what);
	}
	
	public static DoodadInfo emeny(String texture, EnemyType what, int difficulty,
			int dropPoints, int dropAmmo, int dropHealth) {
		return new DoodadInfo(false, texture, 0, 0, 0,
				difficulty, what, 0);
	}
	
	public static DoodadInfo solidScenery(String texture) {
		return new DoodadInfo(true, texture, 0, 0, 0,
				0, EnemyType.NOT_AN_ENEMY, 0);
	}
	
	public static DoodadInfo nonsolidScenery(String texture) {
		return new DoodadInfo(false, texture, 0, 0, 0,
				0, EnemyType.NOT_AN_ENEMY, 0);
	}
	
	
	public DoodadInfo(boolean solid, String texture, int points, int ammo,
			int health, int difficulty, EnemyType enemytype, int gun) {
		super();
		this.solid = solid;
		this.texture = texture;
		this.points = points;
		this.ammo = ammo;
		this.health = health;
		this.difficulty = difficulty;
		this.enemytype = enemytype;
		this.gun = gun;
	}


	@Override
	public DoodadInfo clone() {
		return new DoodadInfo(
				solid, texture,
				points, ammo,
				health, difficulty,
				enemytype, gun);
	}
	
	//whether you can walk through this thing
	public boolean solid;
	
	//texture name
	public String texture;
	
	//number of points to give on collection. If nonzero,
	//this item is a treasure item. If this is an enemy, this defines the
	//amount of treasure it will drop
	public int points;
	
	//amount of ammo to give. If nonzero, this is an ammo
	//pickup or an enemy. If it is an enemy, this defines the
	//amount of ammo it will drop
	public int ammo;
	
	//the amount of health to give. if nonzero, this is a 
	//health pickup. If this is an enemy, this defines the
	//amount of health it will drop
	public int health;
	
	//The difficulty below which this item will not spawn
	public int difficulty;
	
	//The type of enemy
	public EnemyType enemytype;
	
	//The type of gun. If nonzero, this is a gun pickup.
	//If this is an enemy, this defines the gun it will drop
	public int gun;
	
}
