package deco2800.arcade.wl6;

import deco2800.arcade.wl6.WL6Meta.DIRS;
import deco2800.arcade.wl6.enemy.EnemyType;

/**
 * Describes a thing. A thing can be an enemy, a powerup, a gun, or many at the same time
 * @author Simon
 *
 */
public class DoodadInfo {


    public DoodadInfo() {
    }


    public static DoodadInfo treasurePickup(String texture, int value, int health) {
        return new DoodadInfo(false, texture, value, 0, health,
                0, EnemyType.NOT_AN_ENEMY, 0, null, false);
    }

    public static DoodadInfo healthPickup(String texture, int value, int difficulty) {
        return new DoodadInfo(false, texture, 0, 0, value,
                difficulty, EnemyType.NOT_AN_ENEMY, 0, null, false);
    }

    public static DoodadInfo ammoPickup(String texture, int value, int difficulty) {
        return new DoodadInfo(false, texture, 0, value, 0,
                difficulty, EnemyType.NOT_AN_ENEMY, 0, null, false);
    }

    public static DoodadInfo gunPickup(String texture, int what, int difficulty) {
        return new DoodadInfo(false, texture, 0, 0, 0,
                difficulty, EnemyType.NOT_AN_ENEMY, what, null, false);
    }

    public static DoodadInfo solidScenery(String texture) {
        return new DoodadInfo(true, texture, 0, 0, 0,
                0, EnemyType.NOT_AN_ENEMY, 0, null, false);
    }

    public static DoodadInfo nonsolidScenery(String texture) {
        return new DoodadInfo(false, texture, 0, 0, 0,
                0, EnemyType.NOT_AN_ENEMY, 0, null, false);
    }

    public static DoodadInfo wayPoint(DIRS d) {
        return new DoodadInfo(false, null, 0, 0, 0,
                0, EnemyType.NOT_AN_ENEMY, 0, d, false);
    }

    public DoodadInfo(boolean solid, String texture, int points, int ammo,
            int health, int difficulty, EnemyType enemytype, int gun, DIRS direction, boolean pathing) {
        super();
        this.solid = solid;
        this.texture = texture;
        this.points = points;
        this.ammo = ammo;
        this.health = health;
        this.difficulty = difficulty;
        this.enemytype = enemytype;
        this.gun = gun;
        this.direction = direction;
        this.pathing = pathing;
        this.special = false;
    }


    @Override
    public DoodadInfo clone() {
        DoodadInfo d = new DoodadInfo(
                solid, texture,
                points, ammo,
                health, difficulty,
                enemytype, gun, direction, pathing);
        d.special = this.special;
        return d;
    }

    public DoodadInfo specialCase() {
        this.special = true;
        return this;
    }

    /**
     * Whether you can walk through this thing
     */
    public boolean solid;

    /**
     * Texture name
     */
    public String texture;

    /**
     * Number of points to give on collection. If nonzero,
     * this item is a treasure item. If this is an enemy, this defines the
     * amount of treasure it will drop
     */
    public int points;

    /**
     * Amount of ammo to give. If nonzero, this is an ammo
     * pickup or an enemy. If it is an enemy, this defines the
     * amount of ammo it will drop
     */
    public int ammo;

    /**
     * The amount of health to give. if nonzero, this is a
     * health pickup. If this is an enemy, this defines the
     * amount of health it will drop
     */
    public int health;

    /**
     * The difficulty below which this item will not spawn
     */
    public int difficulty;

    /**
     * The type of enemy
     */
    public EnemyType enemytype;

    /**
     * The type of gun. If nonzero, this is a gun pickup.
     * If this is an enemy, this defines the gun it will drop
     */
    public int gun;

    /**
     * The direction this item is facing
     */
    public DIRS direction;

    /**
     * The direction this item is travelling
     * Null if the item is stationary
     */
    public boolean pathing;

    /**
     * Indicates that this object should not be handled automatically
     */
    public boolean special;

}
