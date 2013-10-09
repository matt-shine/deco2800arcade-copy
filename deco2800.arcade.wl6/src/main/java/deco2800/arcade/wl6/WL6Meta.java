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

    // Is there any way to make this neater/smaller? -abbjohn
    static {
        //TODO the rest of the blocks
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
        };

        //TODO the rest of the items
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

                //28: hanging skeleton
                new DoodadInfo(),

                //29: dog food
                DoodadInfo.healthPickup("dogfood", 5, 0),

                //30: white column
                new DoodadInfo(),

                //31: green plant
                new DoodadInfo(),

                //32: skeleton
                new DoodadInfo(),

                //33: sink
                new DoodadInfo(),

                //34: brown plant
                new DoodadInfo(),

                //35: vase
                new DoodadInfo(),

                //36: table
                new DoodadInfo(),

                //37: ceiling light (green)
                new DoodadInfo(),

                //38: utensils brown
                new DoodadInfo(),

                //39: armor
                new DoodadInfo(),

                //40: empty cage
                new DoodadInfo(),

                //41: cage / skeleton
                new DoodadInfo(),

                //42: bones 1
                new DoodadInfo(),

                //43: gold key
                new DoodadInfo(),

                //44: silver key
                new DoodadInfo(),

                //45: bed
                new DoodadInfo(),

                //46: basket
                new DoodadInfo(),

                //47: food
                DoodadInfo.healthPickup("food", 10, 0),

                //48: medkit
                DoodadInfo.healthPickup("medkit", 25, 0),

                //49: ammo pickup
                DoodadInfo.ammoPickup("ammo", 5, 0),

                //50: "small gun"
                //don't know what the difference between this and machine gun is
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

                //56: extra life
                new DoodadInfo(),

                //57: bones / blood
                new DoodadInfo(),

                //58: barrel
                new DoodadInfo(),

                //59: well / water
                new DoodadInfo(),

                //60: well
                new DoodadInfo(),

                //61: pool of blood
                new DoodadInfo(),

                //62: flag
                new DoodadInfo(),

                //63: nothing
                new DoodadInfo(),

                //64: bones 2
                new DoodadInfo(),

                //65: bones 3
                new DoodadInfo(),

                //66: bones 4
                new DoodadInfo(),

                //67: utensils blue
                new DoodadInfo(),

                //68: stove
                new DoodadInfo(),

                //69: rack
                new DoodadInfo(),

                //70: vines
                new DoodadInfo(),

                //71: nothing
                new DoodadInfo(),

                //72: nothing
                new DoodadInfo(),

                //73: nothing
                new DoodadInfo(),

                //74: nothing
                new DoodadInfo(),

                //75: nothing
                new DoodadInfo(),

                //76: nothing
                new DoodadInfo(),

                //77: nothing
                new DoodadInfo(),

                //78: nothing
                new DoodadInfo(),

                //79: nothing
                new DoodadInfo(),

                //80: nothing
                new DoodadInfo(),

                //81: nothing
                new DoodadInfo(),

                //82: nothing
                new DoodadInfo(),

                //83: nothing
                new DoodadInfo(),

                //84: nothing
                new DoodadInfo(),

                //85: nothing
                new DoodadInfo(),

                //86: nothing
                new DoodadInfo(),

                //87: nothing
                new DoodadInfo(),

                //88: nothing
                new DoodadInfo(),

                //89: nothing
                new DoodadInfo(),

                //90: Turning point
                new DoodadInfo(),

                //91: Turning point
                new DoodadInfo(),

                //92: Turning point
                new DoodadInfo(),

                //93: Turning point
                new DoodadInfo(),

                //94: Turning point
                new DoodadInfo(),

                //95: Turning point
                new DoodadInfo(),

                //96: Turning point
                new DoodadInfo(),

                //97: Turning point
                new DoodadInfo(),

                //98: Secret door
                new DoodadInfo(),

                //99: Endgame trigger
                new DoodadInfo(),

                //100: nothing
                new DoodadInfo(),

                //101: nothing
                new DoodadInfo(),

                //102: nothing
                new DoodadInfo(),

                //103: nothing
                new DoodadInfo(),

                //104: nothing
                new DoodadInfo(),

                //105: nothing
                new DoodadInfo(),

                //106: nothing
                new DoodadInfo(),

                //107: nothing
                new DoodadInfo(),

                //108: Guard 1 (Standing)
                new DoodadInfo(true, "doodad", 0, 0, 0, 0, EnemyType.GUARD, 0, DIRS.UP),

                //109: Guard 1 (Standing)
                new DoodadInfo(),

                //110: Guard 1 (Standing)
                new DoodadInfo(),

                //111: Guard 1 (Standing)
                new DoodadInfo(),

                //112: Guard 1 (Moving)
                new DoodadInfo(),

                //113: Guard 1 (Moving)
                new DoodadInfo(),

                //114: Guard 1 (Moving)
                new DoodadInfo(),

                //115: Guard 1 (Moving)
                new DoodadInfo(),

                //116: nothing
                new DoodadInfo(),

                //117: nothing
                new DoodadInfo(),

                //118: nothing
                new DoodadInfo(),

                //119: nothing
                new DoodadInfo(),

                //120: nothing
                new DoodadInfo(),

                //121: nothing
                new DoodadInfo(),

                //122: nothing
                new DoodadInfo(),

                //123: nothing
                new DoodadInfo(),

                //124: dead guard
                new DoodadInfo(),

                //125: nothing
                new DoodadInfo(),

                //126: SS 1 (Standing)
                new DoodadInfo(),

                //127: SS 1 (Standing)
                new DoodadInfo(),

                //128: SS 1 (Standing)
                new DoodadInfo(),

                //129: SS 1 (Standing)
                new DoodadInfo(),

                //130: SS 1 (Moving)
                new DoodadInfo(),

                //131: SS 1 (Moving)
                new DoodadInfo(),

                //132: SS 1 (Moving)
                new DoodadInfo(),

                //133: SS 1 (Moving)
                new DoodadInfo(),

                //134: nothing
                new DoodadInfo(),

                //135: nothing
                new DoodadInfo(),

                //136: nothing
                new DoodadInfo(),

                //137: nothing
                new DoodadInfo(),

                //138: Dog 1 (Moving)
                new DoodadInfo(),

                //139: Dog 1 (Moving)
                new DoodadInfo(),

                //140: Dog 1 (Moving)
                new DoodadInfo(),

                //141: Dog 1 (Moving)
                new DoodadInfo(),

                //142: nothing
                new DoodadInfo(),

                //143: nothing
                new DoodadInfo(),

                //144: Guard 3 (Standing)
                new DoodadInfo(),

                //145: Guard 3 (Standing)
                new DoodadInfo(),

                //146: Guard 3 (Standing)
                new DoodadInfo(),

                //147: Guard 3 (Standing)
                new DoodadInfo(),

                //148: Guard 3 (Moving)
                new DoodadInfo(),

                //149: Guard 3 (Moving)
                new DoodadInfo(),

                //150: Guard 3 (Moving)
                new DoodadInfo(),

                //151: Guard 3 (Moving)
                new DoodadInfo(),

                //152: nothing
                new DoodadInfo(),

                //153: nothing
                new DoodadInfo(),

                //154: nothing
                new DoodadInfo(),

                //155: nothing
                new DoodadInfo(),

                //156: nothing
                new DoodadInfo(),

                //157: nothing
                new DoodadInfo(),

                //158: nothing
                new DoodadInfo(),

                //159: nothing
                new DoodadInfo(),

                //160: nothing
                new DoodadInfo(),

                //161: nothing
                new DoodadInfo(),

                //162: SS 3 (Standing)
                new DoodadInfo(),

                //163: SS 3 (Standing)
                new DoodadInfo(),

                //164: SS 3 (Standing)
                new DoodadInfo(),

                //165: SS 3 (Standing)
                new DoodadInfo(),

                //166: SS 3 (Moving)
                new DoodadInfo(),

                //167: SS 3 (Moving)
                new DoodadInfo(),

                //168: SS 3 (Moving)
                new DoodadInfo(),

                //169: SS 3 (Moving)
                new DoodadInfo(),

                //170: nothing
                new DoodadInfo(),

                //171: nothing
                new DoodadInfo(),

                //172: nothing
                new DoodadInfo(),

                //173: nothing
                new DoodadInfo(),

                //174: Dog 3 (Moving)
                new DoodadInfo(),

                //175: Dog 3 (Moving)
                new DoodadInfo(),

                //176: Dog 3 (Moving)
                new DoodadInfo(),

                //177: Dog 3 (Moving)
                new DoodadInfo(),

                //178: nothing
                new DoodadInfo(),

                //179: nothing
                new DoodadInfo(),

                //180: Guard 4 (Standing)
                new DoodadInfo(),

                //181: Guard 4 (Standing)
                new DoodadInfo(),

                //182: Guard 4 (Standing)
                new DoodadInfo(),

                //183: Guard 4 (Standing)
                new DoodadInfo(),

                //184: Guard 4 (Moving)
                new DoodadInfo(),

                //185: Guard 4 (Moving)
                new DoodadInfo(),

                //186: Guard 4 (Moving)
                new DoodadInfo(),

                //187: Guard 4 (Moving)
                new DoodadInfo(),

                //188: nothing
                new DoodadInfo(),

                //189: nothing
                new DoodadInfo(),

                //190: nothing
                new DoodadInfo(),

                //191: nothing
                new DoodadInfo(),

                //192: nothing
                new DoodadInfo(),

                //193: nothing
                new DoodadInfo(),

                //194: nothing
                new DoodadInfo(),

                //195: nothing
                new DoodadInfo(),

                //196: nothing
                new DoodadInfo(),

                //197: nothing
                new DoodadInfo(),

                //198: SS 4 (Standing)
                new DoodadInfo(),

                //199: SS 4 (Standing)
                new DoodadInfo(),

                //200: SS 4 (Standing)
                new DoodadInfo(),

                //201: SS 4 (Standing)
                new DoodadInfo(),

                //202: SS 4 (Moving)
                new DoodadInfo(),

                //203: SS 4 (Moving)
                new DoodadInfo(),

                //204: SS 4 (Moving)
                new DoodadInfo(),

                //205: SS 4 (Moving)
                new DoodadInfo(),

                //206: nothing
                new DoodadInfo(),

                //207: nothing
                new DoodadInfo(),

                //208: nothing
                new DoodadInfo(),

                //209: nothing
                new DoodadInfo(),

                //210: Dog 4 (Moving)
                new DoodadInfo(),

                //211: Dog 4 (Moving)
                new DoodadInfo(),

                //212: Dog 4 (Moving)
                new DoodadInfo(),

                //213: Dog 4 (Moving)
                new DoodadInfo(),

                //214: Hans Grosse
                new DoodadInfo(),
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
