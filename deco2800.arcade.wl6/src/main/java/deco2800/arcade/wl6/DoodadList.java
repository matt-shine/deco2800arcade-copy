package deco2800.arcade.wl6;

import deco2800.arcade.wl6.enemy.EnemyType;
import deco2800.arcade.wl6.WL6Meta.DIRS;

public class DoodadList {

    public DoodadList() {
    }

    public DoodadInfo[] getDoodads() {
        return doodads.clone();
    }

    private static DoodadInfo[] doodads =
            new DoodadInfo[] {
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
                    DoodadInfo.nonsolidScenery("blank"),

                    //24: oil drum
                    DoodadInfo.solidScenery("plant"),

                    //25: table and chairs
                    DoodadInfo.solidScenery("plant"),

                    //26: lamp
                    DoodadInfo.nonsolidScenery("manfried"),

                    //27: chandelier
                    DoodadInfo.nonsolidScenery("manfried"),

                    //28: hanging skeleton
                    DoodadInfo.solidScenery("skeleton"),

                    //29: dog food
                    DoodadInfo.healthPickup("health", 5, 0),

                    //30: white column
                    DoodadInfo.solidScenery("column"),

                    //31: green plant
                    DoodadInfo.solidScenery("plant"),

                    //32: skeleton
                    DoodadInfo.nonsolidScenery("skeleton"),

                    //33: sink
                    DoodadInfo.solidScenery("sink"),

                    //34: brown plant
                    DoodadInfo.solidScenery("plant"),

                    //35: vase
                    DoodadInfo.solidScenery("vase"),

                    //36: table
                    DoodadInfo.solidScenery("table"),

                    //37: ceiling light (green)
                    DoodadInfo.nonsolidScenery("manfried"),

                    //38: utensils brown
                    DoodadInfo.solidScenery("utensils"),

                    //39: armor
                    DoodadInfo.solidScenery("armour"),

                    //40: empty cage
                    DoodadInfo.solidScenery("cage"),

                    //41: cage / skeleton
                    DoodadInfo.solidScenery("cage"),

                    //42: bones 1
                    DoodadInfo.solidScenery("headstone"),

                    //43: gold key
                    new DoodadInfo().specialCase(),

                    //44: silver key
                    new DoodadInfo().specialCase(),

                    //45: bed
                    DoodadInfo.solidScenery("bed"),

                    //46: basket
                    DoodadInfo.solidScenery("dogbed"),

                    //47: food
                    DoodadInfo.healthPickup("health", 10, 0),

                    //48: medkit
                    DoodadInfo.healthPickup("clownnurse", 25, 0),

                    //49: ammo pickup
                    DoodadInfo.ammoPickup("ammo", 5, 0),

                    //50: "small gun"
                    //don't know what the difference between this and machine gun is
                    DoodadInfo.gunPickup("machinegun", 1, 0),

                    //51: machine gun
                    DoodadInfo.gunPickup("machinegun", 1, 0),

                    //52: cross
                    DoodadInfo.treasurePickup("worm", 100, 1),

                    //53: chalice
                    DoodadInfo.treasurePickup("worm", 500, 1),

                    //54: treasure chest
                    DoodadInfo.treasurePickup("worm", 1000, 1),

                    //55: crown
                    DoodadInfo.treasurePickup("worm", 5000, 1),

                    //56: extra life
                    DoodadInfo.treasurePickup("fullheal", 10000, 150),

                    //57: bones / blood
                    DoodadInfo.nonsolidScenery("headstone"),

                    //58: barrel
                    DoodadInfo.solidScenery("barrel"),

                    //59: well / water
                    DoodadInfo.solidScenery("well"),

                    //60: well
                    DoodadInfo.solidScenery("well"),

                    //61: pool of blood
                    DoodadInfo.nonsolidScenery("headstone"),

                    //62: flag
                    DoodadInfo.solidScenery("flag"),

                    //63: nothing
                    new DoodadInfo(),

                    //64: bones 2
                    DoodadInfo.nonsolidScenery("headstone"),

                    //65: bones 3
                    DoodadInfo.nonsolidScenery("headstone"),

                    //66: bones 4
                    DoodadInfo.nonsolidScenery("headstone"),

                    //67: utensils blue
                    DoodadInfo.solidScenery("utensils"),

                    //68: stove
                    DoodadInfo.solidScenery("stove"),

                    //69: rack
                    DoodadInfo.solidScenery("rack"),

                    //70: vines
                    DoodadInfo.nonsolidScenery("vines"),

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

                    //90: Turning point (right)
                    DoodadInfo.wayPoint(DIRS.RIGHT),

                    //91: Turning point (upright)
                    DoodadInfo.wayPoint(DIRS.UPRIGHT),

                    //92: Turning point (up)
                    DoodadInfo.wayPoint(DIRS.UP),

                    //93: Turning point (upleft)
                    DoodadInfo.wayPoint(DIRS.UPLEFT),

                    //94: Turning point (left)
                    DoodadInfo.wayPoint(DIRS.LEFT),

                    //95: Turning point (downleft)
                    DoodadInfo.wayPoint(DIRS.DOWNLEFT),

                    //96: Turning point (down)
                    DoodadInfo.wayPoint(DIRS.DOWN),

                    //97: Turning point (downright)
                    DoodadInfo.wayPoint(DIRS.DOWNRIGHT),

                    //98: Secret door
                    new DoodadInfo().specialCase(),

                    //99: Endgame trigger
                    new DoodadInfo().specialCase(),

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
                    new DoodadInfo(true, "snowman", 100, 0, 0, 1, EnemyType.GUARD, 0, DIRS.RIGHT, false),  // FIXME Not complete

                    //109: Guard 1 (Standing)
                    new DoodadInfo(true, "snowman", 100, 0, 0, 1, EnemyType.GUARD, 0, DIRS.UP, false),  // FIXME Not complete

                    //110: Guard 1 (Standing)
                    new DoodadInfo(true, "snowman", 100, 0, 0, 1, EnemyType.GUARD, 0, DIRS.LEFT, false),  // FIXME Not complete

                    //111: Guard 1 (Standing)
                    new DoodadInfo(true, "snowman", 100, 0, 0, 1, EnemyType.GUARD, 0, DIRS.DOWN, false),  // FIXME Not complete

                    //112: Guard 1 (Moving)
                    new DoodadInfo(true, "snowman", 100, 0, 0, 1, EnemyType.GUARD, 0, DIRS.RIGHT, true),  // FIXME Not complete

                    //113: Guard 1 (Moving)
                    new DoodadInfo(true, "snowman", 100, 0, 0, 1, EnemyType.GUARD, 0, DIRS.UP, true),  // FIXME Not complete

                    //114: Guard 1 (Moving)
                    new DoodadInfo(true, "snowman", 100, 0, 0, 1, EnemyType.GUARD, 0, DIRS.LEFT, true),  // FIXME Not complete

                    //115: Guard 1 (Moving)
                    new DoodadInfo(true, "snowman", 100, 0, 0, 1, EnemyType.GUARD, 0, DIRS.DOWN, true),  // FIXME Not complete

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
                    DoodadInfo.nonsolidScenery("headstone"),

                    //125: nothing
                    new DoodadInfo(),

                    //126: SS 1 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 1, EnemyType.SS, 2, DIRS.RIGHT, false),  // FIXME Not complete

                    //127: SS 1 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 1, EnemyType.SS, 2, DIRS.UP, false),  // FIXME Not complete

                    //128: SS 1 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 1, EnemyType.SS, 0, DIRS.LEFT, false),  // FIXME Not complete

                    //129: SS 1 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 1, EnemyType.SS, 0, DIRS.DOWN, false),  // FIXME Not complete

                    //130: SS 1 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 1, EnemyType.SS, 0, DIRS.RIGHT, true),  // FIXME Not complete

                    //131: SS 1 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 1, EnemyType.SS, 0, DIRS.UP, true),  // FIXME Not complete

                    //132: SS 1 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 1, EnemyType.SS, 0, DIRS.LEFT, true),  // FIXME Not complete

                    //133: SS 1 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 1, EnemyType.SS, 0, DIRS.DOWN, true),  // FIXME Not complete

                    //134: nothing
                    new DoodadInfo(),

                    //135: nothing
                    new DoodadInfo(),

                    //136: nothing
                    new DoodadInfo(),

                    //137: nothing
                    new DoodadInfo(),

                    //138: Dog 1 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 1, EnemyType.DOG, 0, DIRS.RIGHT, true),  // FIXME Not complete

                    //139: Dog 1 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 1, EnemyType.DOG, 0, DIRS.UP, true),  // FIXME Not complete

                    //140: Dog 1 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 1, EnemyType.DOG, 0, DIRS.LEFT, true),  // FIXME Not complete

                    //141: Dog 1 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 1, EnemyType.DOG, 0, DIRS.DOWN, true),  // FIXME Not complete

                    //142: nothing
                    new DoodadInfo(),

                    //143: nothing
                    new DoodadInfo(),

                    //144: Guard 3 (Standing)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 3, EnemyType.GUARD, 0, DIRS.RIGHT, false),  // FIXME Not complete

                    //145: Guard 3 (Standing)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 3, EnemyType.GUARD, 0, DIRS.UP, false),  // FIXME Not complete

                    //146: Guard 3 (Standing)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 3, EnemyType.GUARD, 0, DIRS.LEFT, false),  // FIXME Not complete

                    //147: Guard 3 (Standing)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 3, EnemyType.GUARD, 0, DIRS.DOWN, false),  // FIXME Not complete

                    //148: Guard 3 (Moving)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 3, EnemyType.GUARD, 0, DIRS.RIGHT, true),  // FIXME Not complete

                    //149: Guard 3 (Moving)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 3, EnemyType.GUARD, 0, DIRS.UP, true),  // FIXME Not complete

                    //150: Guard 3 (Moving)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 3, EnemyType.GUARD, 0, DIRS.LEFT, true),  // FIXME Not complete

                    //151: Guard 3 (Moving)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 3, EnemyType.GUARD, 0, DIRS.DOWN, true),  // FIXME Not complete

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
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 3, EnemyType.SS, 0, DIRS.RIGHT, false),  // FIXME Not complete

                    //163: SS 3 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 3, EnemyType.SS, 0, DIRS.UP, false),  // FIXME Not complete

                    //164: SS 3 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 3, EnemyType.SS, 0, DIRS.LEFT, false),  // FIXME Not complete

                    //165: SS 3 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 3, EnemyType.SS, 0, DIRS.DOWN, false),  // FIXME Not complete

                    //166: SS 3 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 3, EnemyType.SS, 0, DIRS.RIGHT, true),  // FIXME Not complete

                    //167: SS 3 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 3, EnemyType.SS, 0, DIRS.UP, true),  // FIXME Not complete

                    //168: SS 3 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 3, EnemyType.SS, 0, DIRS.LEFT, true),  // FIXME Not complete

                    //169: SS 3 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 3, EnemyType.SS, 0, DIRS.DOWN, true),  // FIXME Not complete

                    //170: nothing
                    new DoodadInfo(),

                    //171: nothing
                    new DoodadInfo(),

                    //172: nothing
                    new DoodadInfo(),

                    //173: nothing
                    new DoodadInfo(),

                    //174: Dog 3 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 3, EnemyType.DOG, 0, DIRS.RIGHT, true),  // FIXME Not complete

                    //175: Dog 3 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 3, EnemyType.DOG, 0, DIRS.UP, true),  // FIXME Not complete

                    //176: Dog 3 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 3, EnemyType.DOG, 0, DIRS.LEFT, true),  // FIXME Not complete

                    //177: Dog 3 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 3, EnemyType.DOG, 0, DIRS.DOWN, true),  // FIXME Not complete

                    //178: nothing
                    new DoodadInfo(),

                    //179: nothing
                    new DoodadInfo(),

                    //180: Guard 4 (Standing)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 4, EnemyType.GUARD, 0, DIRS.RIGHT, false),  // FIXME Not complete

                    //181: Guard 4 (Standing)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 4, EnemyType.GUARD, 0, DIRS.UP, false),  // FIXME Not complete

                    //182: Guard 4 (Standing)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 4, EnemyType.GUARD, 0, DIRS.LEFT, false),  // FIXME Not complete

                    //183: Guard 4 (Standing)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 4, EnemyType.GUARD, 0, DIRS.DOWN, false),  // FIXME Not complete

                    //184: Guard 4 (Moving)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 4, EnemyType.GUARD, 0, DIRS.RIGHT, true),  // FIXME Not complete

                    //185: Guard 4 (Moving)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 4, EnemyType.GUARD, 0, DIRS.UP, true),  // FIXME Not complete

                    //186: Guard 4 (Moving)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 4, EnemyType.GUARD, 0, DIRS.LEFT, true),  // FIXME Not complete

                    //187: Guard 4 (Moving)
                    new DoodadInfo(true, "snowman", 0, 0, 0, 4, EnemyType.GUARD, 0, DIRS.DOWN, true),  // FIXME Not complete

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
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 4, EnemyType.SS, 0, DIRS.RIGHT, false),  // FIXME Not complete

                    //199: SS 4 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 4, EnemyType.SS, 0, DIRS.UP, false),  // FIXME Not complete

                    //200: SS 4 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 4, EnemyType.SS, 0, DIRS.LEFT, false),  // FIXME Not complete

                    //201: SS 4 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 4, EnemyType.SS, 0, DIRS.DOWN, false),  // FIXME Not complete

                    //202: SS 4 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 4, EnemyType.SS, 0, DIRS.RIGHT, true),  // FIXME Not complete

                    //203: SS 4 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 4, EnemyType.SS, 0, DIRS.UP, true),  // FIXME Not complete

                    //204: SS 4 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 4, EnemyType.SS, 0, DIRS.LEFT, true),  // FIXME Not complete

                    //205: SS 4 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 0, 0, 4, EnemyType.SS, 0, DIRS.DOWN, true),  // FIXME Not complete

                    //206: nothing
                    new DoodadInfo(),

                    //207: nothing
                    new DoodadInfo(),

                    //208: nothing
                    new DoodadInfo(),

                    //209: nothing
                    new DoodadInfo(),

                    //210: Dog 4 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 4, EnemyType.GUARD, 0, DIRS.RIGHT, true),  // FIXME Not complete

                    //211: Dog 4 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 4, EnemyType.GUARD, 0, DIRS.UP, true),  // FIXME Not complete

                    //212: Dog 4 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 4, EnemyType.GUARD, 0, DIRS.LEFT, true),  // FIXME Not complete

                    //213: Dog 4 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 4, EnemyType.GUARD, 0, DIRS.DOWN, true),  // FIXME Not complete

                    //214: Hans Grosse
                    new DoodadInfo(true, "hans", 0, 0, 0, 4, EnemyType.HANS, 0, DIRS.RIGHT, false),
            };
}
