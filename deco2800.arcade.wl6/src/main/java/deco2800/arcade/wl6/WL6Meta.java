package deco2800.arcade.wl6;

import deco2800.arcade.wl6.enemy.EnemyType;
import deco2800.arcade.wl6.enemy.Guard;

/**
 * Defines what each tile in the game files does. For example, tile
 * 101 in the terrain layer is an elevator door. Tile 43 in the doodads
 * layer is a gold key.
 * @author Simon
 *
 */
public class WL6Meta {

    /**
     * The ID of the first spawn point. There are 3 others, they
     * are consecutive.
     */
    public static final int SPAWN_POINT = 19;
    public static final int DOOR = 90;
    public static final int DOOR_GOLDKEY = 92;
    public static final int DOOR_SOLVERKEY = 94;
    public static final int DOOR_ELEVATOR = 100;
    public static final int SECRET_DOOR = 98;


    public static enum DIRS {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

    public static enum KEY_TYPE {
        NONE,
        GOLD,
        SILVER,
    }

    public static float dirToAngle(DIRS d) {
        switch (d) {
        case UP:
            return 0;
        case LEFT:
            return 90;
        case DOWN:
            return 180;
        case RIGHT:
            return 270;
        default:
            return 0;
        }
    }



    public static boolean hasDoorAt(int x, int y, Level map) {
        int id = map.getTerrainAt(x, y);
        if (id >= WL6Meta.DOOR && id < WL6Meta.DOOR + 2 ||
                id >= WL6Meta.DOOR_GOLDKEY && id < WL6Meta.DOOR_GOLDKEY + 2 ||
                id >= WL6Meta.DOOR_SOLVERKEY && id < WL6Meta.DOOR_SOLVERKEY + 2 ||
                id >= WL6Meta.DOOR_ELEVATOR && id < WL6Meta.DOOR_ELEVATOR + 2) {
            return true;
        }
        return false;
    }



    public static boolean hasObscuringBlockAt(int x, int y, Level map) {
        if (WL6Meta.block(map.getTerrainAt(x, y)).texture != null &&
                map.getDoodadAt(x, y) != WL6Meta.SECRET_DOOR) {
            return true;
        }
        if (hasDoorAt(x, y, map)) {
            return true;
        }
        return false;
    }



    public static boolean isSurrounded(int x, int y, Level map) {
        int surrounded = 0;
        if (x == 0 || hasObscuringBlockAt(x - 1, y, map)) {
            surrounded++;
        }
        if (x == 63 || hasObscuringBlockAt(x + 1, y, map)) {
            surrounded++;
        }
        if (y == 0 || hasObscuringBlockAt(x, y - 1, map)) {
            surrounded++;
        }
        if (y == 63 || hasObscuringBlockAt(x, y + 1, map)) {
            surrounded++;
        }
        if (surrounded == 4) {
            return true;
        }

        return false;

    }


    private static BlockInfo[] blocks;
    private static DoodadInfo[] doodads;

    static {
        blocks = new BlockInfo[]{

                //0: nothing
                new BlockInfo(),

                //1: grey brick wall
                new BlockInfo(true, "greybrick"),

                //2: grey brick wall 2
                new BlockInfo(true, "greybrick"),

                //3: grey brick wall with nazi flag
                new BlockInfo(true, "greybrickflag"),

                //4: grey brick wall with hitler portrait
                new BlockInfo(true, "greybrickportrait"),

                //5: prison cell (blue brick)
                new BlockInfo(true, "bluebrickprison"),

                //6: grey brick with nazi eagle
                new BlockInfo(true, "greybrickeagle"),

                //7: prison cell with skeleton (blue brick)
                new BlockInfo(true, "bluebrickprisonskeleton"),

                //8: blue brick
                new BlockInfo(true, "bluebrick"),

                //9: blue brick 2
                new BlockInfo(true, "bluebrick"),

                //10: wood wall with nazi eagle
                new BlockInfo(true, "woodeagle"),

                //11: wood wall with portrait of hitler
                new BlockInfo(true, "woodportrait"),

                //12: wood wall
                new BlockInfo(true, "woodwall"),

                //13: entrance elevator
                new BlockInfo(true, "entranceelevator"),

                //14: steel wall with sign
                new BlockInfo(true, "steelsign"),

                //15: steel wall
                new BlockInfo(true, "steel"),

                //16: starysky
                new BlockInfo(true, "starysky"),

                //17: red brick
                new BlockInfo(true, "redbrick"),

                //TODO the rest of the blocks


        };


        doodads = new DoodadInfo[]{

                //0: nothing
                new DoodadInfo(),

                //1: nothing
                new DoodadInfo(),

                //2: nothing
                new DoodadInfo(),

                //3: nothing
                new DoodadInfo(),

                //4: nothing
                new DoodadInfo(),

                //5: nothing
                new DoodadInfo(),

                //6: nothing
                new DoodadInfo(),

                //7: nothing
                new DoodadInfo(),

                //8: nothing
                new DoodadInfo(),

                //9: nothing
                new DoodadInfo(),

                //10: nothing
                new DoodadInfo(),

                //11: nothing
                new DoodadInfo(),

                //12: nothing
                new DoodadInfo(),

                //13: nothing
                new DoodadInfo(),

                //14: nothing
                new DoodadInfo(),

                //15: nothing
                new DoodadInfo(),

                //16: nothing
                new DoodadInfo(),

                //17: nothing
                new DoodadInfo(),

                //18: nothing
                new DoodadInfo(),

                //19: spawn point - special case of waypoint handled by MapProcessor
                DoodadInfo.wayPoint(DIRS.UP).specialCase(),

                //20: spawn point - special case of waypoint handled by MapProcessor
                DoodadInfo.wayPoint(DIRS.RIGHT).specialCase(),

                //21: spawn point - special case of waypoint handled by MapProcessor
                DoodadInfo.wayPoint(DIRS.DOWN).specialCase(),

                //22: spawn point - special case of waypoint handled by MapProcessor
                DoodadInfo.wayPoint(DIRS.LEFT).specialCase(),

                //23: puddle of water
                DoodadInfo.nonsolidScenery("doodad"),

                //24: oil drum
                DoodadInfo.solidScenery("doodad"),

                //25: table and chairs
                DoodadInfo.solidScenery("doodad"),

                //26: lamp
                DoodadInfo.nonsolidScenery("doodad"),

                //27: chandelier
                DoodadInfo.nonsolidScenery("doodad"),

                //28: temp - nothing
                new DoodadInfo(),

                //29: dog food
                DoodadInfo.healthPickup("dogfood", 5, 0),

                //30: temp - nothing
                new DoodadInfo(),

                //31: temp - nothing
                new DoodadInfo(),

                //32: temp - nothing
                new DoodadInfo(),

                //33: temp - nothing
                new DoodadInfo(),

                //34: temp - nothing
                new DoodadInfo(),

                //35: temp - nothing
                new DoodadInfo(),

                //36: temp - nothing
                new DoodadInfo(),

                //37: temp - nothing
                new DoodadInfo(),

                //38: temp - nothing
                new DoodadInfo(),

                //39: temp - nothing
                new DoodadInfo(),

                //40: temp - nothing
                new DoodadInfo(),

                //41: temp - nothing
                new DoodadInfo(),

                //42: temp - nothing
                new DoodadInfo(),

                //43: temp - nothing
                new DoodadInfo(),

                //44: temp - nothing
                new DoodadInfo(),

                //45: temp - nothing
                new DoodadInfo(),

                //46: temp - nothing
                new DoodadInfo(),

                //47: food
                DoodadInfo.healthPickup("food", 10, 0),

                //48: medkit
                DoodadInfo.healthPickup("medkit", 25, 0),

                //49: ammo pickup
                DoodadInfo.ammoPickup("ammo", 5, 0),

                //50: "small gun" - don't know what the difference between this and machine gun is
                DoodadInfo.gunPickup("machinegun", 1, 0),

                //51: machine gun
                DoodadInfo.gunPickup("machinegun", 1, 0),

                //52: cross
                DoodadInfo.treasurePickup("crossrelic", 100),

                //53: chalice
                DoodadInfo.treasurePickup("chalice", 500),

                //54: treasure chest
                DoodadInfo.treasurePickup("treasurechest", 1000),

                //55: crown
                DoodadInfo.treasurePickup("crown", 5000),

                // TODO 56-107

                // 108: guard 1 (standing)
                new DoodadInfo(true, "doodad", 0, 0, 0, 0, EnemyType.GUARD, 0, DIRS.UP),

                // 109: guard 1 (standing)
                new DoodadInfo(),

                // 110: guard 1 (standing)
                new DoodadInfo(),

                // 111: guard 1 (standing)
                new DoodadInfo(),

                // 112: guard 1 (moving)
                new DoodadInfo(),

                // 113: guard 1 (moving)
                new DoodadInfo(),

                // 114: guard 1 (moving)
                new DoodadInfo(),

                // 115: guard 1 (moving)
                new DoodadInfo(),

                // 124: dead guard
                new DoodadInfo(),

                //TODO the rest of the items
        };
    }


    /**
     * Get information about a tile ID
     * @param id
     * @return
     */
    public static BlockInfo block(int id) {
        if (id >= blocks.length) {
            return new BlockInfo();
        }
        return blocks[id].clone();
    }

    /**
     * Get information about a doodad ID
     * @param id
     * @return
     */
    public static DoodadInfo doodad(int id) {
        if (id >= doodads.length) {
            return new DoodadInfo();
        }
        return doodads[id].clone();
    }

}
