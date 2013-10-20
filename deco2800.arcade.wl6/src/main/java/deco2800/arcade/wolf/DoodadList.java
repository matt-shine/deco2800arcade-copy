package deco2800.arcade.wolf;

import deco2800.arcade.wolf.WL6Meta.DIRS;
import deco2800.arcade.wolf.enemy.EnemyType;

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
                    DoodadInfo.solidScenery("plant"),

                    //29: dog food
                    DoodadInfo.healthPickup("health", 5, 0),

                    //30: white column
                    DoodadInfo.solidScenery("plant"),

                    //31: green plant
                    DoodadInfo.solidScenery("plant"),

                    //32: skeleton
                    DoodadInfo.nonsolidScenery("headstone"),

                    //33: sink
                    DoodadInfo.solidScenery("plant"),

                    //34: brown plant
                    DoodadInfo.solidScenery("plant"),

                    //35: vase
                    DoodadInfo.solidScenery("plant"),

                    //36: table
                    DoodadInfo.solidScenery("plant"),

                    //37: ceiling light (green)
                    DoodadInfo.nonsolidScenery("manfried"),

                    //38: utensils brown
                    DoodadInfo.solidScenery("plant"),

                    //39: armor
                    DoodadInfo.solidScenery("armour"),

                    //40: empty cage
                    DoodadInfo.solidScenery("plant"),

                    //41: cage / skeleton
                    DoodadInfo.solidScenery("plant"),

                    //42: bones 1
                    DoodadInfo.nonsolidScenery("headstone"),

                    //43: gold key
                    new DoodadInfo().specialCase(),

                    //44: silver key
                    new DoodadInfo().specialCase(),

                    //45: bed
                    DoodadInfo.solidScenery("plant"),

                    //46: basket
                    DoodadInfo.solidScenery("plant"),

                    //47: food
                    DoodadInfo.healthPickup("health", 10, 0),

                    //48: medkit
                    DoodadInfo.healthPickup("clownnurse", 25, 0),

                    //49: ammo pickup
                    DoodadInfo.ammoPickup("ammo", 8, 0),

                    //50: "small gun"
                    DoodadInfo.gunPickup("machinegun", 2, 0),

                    //51: chain gun
                    DoodadInfo.gunPickup("chaingun", 3, 0),

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
                    DoodadInfo.solidScenery("plant"),

                    //59: well / water
                    DoodadInfo.solidScenery("plant"),

                    //60: well
                    DoodadInfo.solidScenery("plant"),

                    //61: pool of blood
                    DoodadInfo.nonsolidScenery("headstone"),

                    //62: flag
                    DoodadInfo.solidScenery("plant"),

                    //63: Aardwolf sign
                    new DoodadInfo(),

                    //64: bones 2
                    DoodadInfo.nonsolidScenery("headstone"),

                    //65: bones 3
                    DoodadInfo.nonsolidScenery("headstone"),

                    //66: bones 4
                    DoodadInfo.nonsolidScenery("headstone"),

                    //67: utensils blue
                    DoodadInfo.solidScenery("plant"),

                    //68: stove
                    DoodadInfo.solidScenery("plant"),

                    //69: rack
                    DoodadInfo.solidScenery("plant"),

                    //70: vines
                    DoodadInfo.nonsolidScenery("manfried"),

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
                    new DoodadInfo(true, "snowman", 0, 6, 0, 1, EnemyType.GUARD, 0, DIRS.RIGHT, false),

                    //109: Guard 1 (Standing)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 1, EnemyType.GUARD, 0, DIRS.UP, false),

                    //110: Guard 1 (Standing)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 1, EnemyType.GUARD, 0, DIRS.LEFT, false),

                    //111: Guard 1 (Standing)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 1, EnemyType.GUARD, 0, DIRS.DOWN, false),

                    //112: Guard 1 (Moving)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 1, EnemyType.GUARD, 0, DIRS.RIGHT, true),

                    //113: Guard 1 (Moving)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 1, EnemyType.GUARD, 0, DIRS.UP, true),

                    //114: Guard 1 (Moving)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 1, EnemyType.GUARD, 0, DIRS.LEFT, true),

                    //115: Guard 1 (Moving)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 1, EnemyType.GUARD, 0, DIRS.DOWN, true),

                    //116: Officer 1 (Standing)
                    new DoodadInfo(true, "officer", 0, 6, 0, 1, EnemyType.OFFICER, 0, DIRS.RIGHT, false),

                    //117: Officer 1 (Standing)
                    new DoodadInfo(true, "officer", 0, 6, 0, 1, EnemyType.OFFICER, 0, DIRS.UP, false),

                    //118: Officer 1 (Standing)
                    new DoodadInfo(true, "officer", 0, 6, 0, 1, EnemyType.OFFICER, 0, DIRS.LEFT, false),

                    //119: Officer 1 (Standing)
                    new DoodadInfo(true, "officer", 0, 6, 0, 1, EnemyType.OFFICER, 0, DIRS.DOWN, false),

                    //120: Officer 1 (Moving)
                    new DoodadInfo(true, "officer", 0, 6, 0, 1, EnemyType.OFFICER, 0, DIRS.RIGHT, true),

                    //121: Officer 1 (Moving)
                    new DoodadInfo(true, "officer", 0, 6, 0, 1, EnemyType.OFFICER, 0, DIRS.UP, true),

                    //122: Officer 1 (Moving)
                    new DoodadInfo(true, "officer", 0, 6, 0, 1, EnemyType.OFFICER, 0, DIRS.LEFT, true),

                    //123: Officer 1 (Moving)
                    new DoodadInfo(true, "officer", 0, 6, 0, 1, EnemyType.OFFICER, 0, DIRS.DOWN, true),

                    //124: dead guard
                    DoodadInfo.nonsolidScenery("headstone"),

                    //125: nothing
                    new DoodadInfo(),

                    //126: SS 1 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 1, EnemyType.SS, 2, DIRS.RIGHT, false),

                    //127: SS 1 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 1, EnemyType.SS, 2, DIRS.UP, false),

                    //128: SS 1 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 1, EnemyType.SS, 2, DIRS.LEFT, false),

                    //129: SS 1 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 1, EnemyType.SS, 2, DIRS.DOWN, false),

                    //130: SS 1 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 1, EnemyType.SS, 2, DIRS.RIGHT, true),

                    //131: SS 1 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 1, EnemyType.SS, 2, DIRS.UP, true),

                    //132: SS 1 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 1, EnemyType.SS, 2, DIRS.LEFT, true),

                    //133: SS 1 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 1, EnemyType.SS, 2, DIRS.DOWN, true),

                    //134: nothing
                    new DoodadInfo(),

                    //135: nothing
                    new DoodadInfo(),

                    //136: nothing
                    new DoodadInfo(),

                    //137: nothing
                    new DoodadInfo(),

                    //138: Dog 1 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 1, EnemyType.DOG, 0, DIRS.RIGHT, true),

                    //139: Dog 1 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 1, EnemyType.DOG, 0, DIRS.UP, true),

                    //140: Dog 1 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 1, EnemyType.DOG, 0, DIRS.LEFT, true),

                    //141: Dog 1 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 1, EnemyType.DOG, 0, DIRS.DOWN, true),

                    //142: nothing
                    new DoodadInfo(),

                    //143: nothing
                    new DoodadInfo(),

                    //144: Guard 3 (Standing)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 3, EnemyType.GUARD, 0, DIRS.RIGHT, false),

                    //145: Guard 3 (Standing)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 3, EnemyType.GUARD, 0, DIRS.UP, false),

                    //146: Guard 3 (Standing)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 3, EnemyType.GUARD, 0, DIRS.LEFT, false),

                    //147: Guard 3 (Standing)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 3, EnemyType.GUARD, 0, DIRS.DOWN, false),

                    //148: Guard 3 (Moving)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 3, EnemyType.GUARD, 0, DIRS.RIGHT, true),

                    //149: Guard 3 (Moving)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 3, EnemyType.GUARD, 0, DIRS.UP, true),

                    //150: Guard 3 (Moving)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 3, EnemyType.GUARD, 0, DIRS.LEFT, true),

                    //151: Guard 3 (Moving)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 3, EnemyType.GUARD, 0, DIRS.DOWN, true),

                    //152: Officer 3 (Standing)
                    new DoodadInfo(true, "officer", 0, 6, 0, 3, EnemyType.OFFICER, 0, DIRS.RIGHT, false),

                    //153: Officer 3 (Standing)
                    new DoodadInfo(true, "officer", 0, 6, 0, 3, EnemyType.OFFICER, 0, DIRS.UP, false),

                    //154: Officer 3 (Standing)
                    new DoodadInfo(true, "officer", 0, 6, 0, 3, EnemyType.OFFICER, 0, DIRS.LEFT, false),

                    //155: Officer 3 (Standing)
                    new DoodadInfo(true, "officer", 0, 6, 0, 3, EnemyType.OFFICER, 0, DIRS.DOWN, false),

                    //156: Officer 3 (Moving)
                    new DoodadInfo(true, "officer", 0, 6, 0, 3, EnemyType.OFFICER, 0, DIRS.RIGHT, true),

                    //157: Officer 3 (Moving)
                    new DoodadInfo(true, "officer", 0, 6, 0, 3, EnemyType.OFFICER, 0, DIRS.UP, true),

                    //158: Officer 3 (Moving)
                    new DoodadInfo(true, "officer", 0, 6, 0, 3, EnemyType.OFFICER, 0, DIRS.LEFT, true),

                    //159: Officer 3 (Moving)
                    new DoodadInfo(true, "officer", 0, 6, 0, 3, EnemyType.OFFICER, 0, DIRS.DOWN, true),

                    //160: Fake Hitler
                    new DoodadInfo(true, "iceking_clone", 0, 0, 0, 0, EnemyType.FAKE_HITLER, 0, DIRS.RIGHT, false),

                    //161: nothing
                    new DoodadInfo(),

                    //162: SS 3 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 3, EnemyType.SS, 2, DIRS.RIGHT, false),

                    //163: SS 3 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 3, EnemyType.SS, 2, DIRS.UP, false),

                    //164: SS 3 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 3, EnemyType.SS, 2, DIRS.LEFT, false),

                    //165: SS 3 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 3, EnemyType.SS, 2, DIRS.DOWN, false),

                    //166: SS 3 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 3, EnemyType.SS, 2, DIRS.RIGHT, true),

                    //167: SS 3 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 3, EnemyType.SS, 2, DIRS.UP, true),

                    //168: SS 3 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 3, EnemyType.SS, 2, DIRS.LEFT, true),

                    //169: SS 3 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 3, EnemyType.SS, 2, DIRS.DOWN, true),

                    //170: nothing
                    new DoodadInfo(),

                    //171: nothing
                    new DoodadInfo(),

                    //172: nothing
                    new DoodadInfo(),

                    //173: nothing
                    new DoodadInfo(),

                    //174: Dog 3 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 3, EnemyType.DOG, 0, DIRS.RIGHT, true),

                    //175: Dog 3 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 3, EnemyType.DOG, 0, DIRS.UP, true),

                    //176: Dog 3 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 3, EnemyType.DOG, 0, DIRS.LEFT, true),

                    //177: Dog 3 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 3, EnemyType.DOG, 0, DIRS.DOWN, true),

                    //178: Adolf Hitler
                    new DoodadInfo(true, "iceking", 0, 0, 0, 0, EnemyType.HITLER, 0, DIRS.RIGHT, false),

                    //179: General Fettgesicht
                    new DoodadInfo(true, "iceking", 0, 0, 0, 0, EnemyType.FETTGESICHT, 0, DIRS.RIGHT, false),

                    //180: Guard 4 (Standing)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 4, EnemyType.GUARD, 0, DIRS.RIGHT, false),

                    //181: Guard 4 (Standing)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 4, EnemyType.GUARD, 0, DIRS.UP, false),

                    //182: Guard 4 (Standing)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 4, EnemyType.GUARD, 0, DIRS.LEFT, false),

                    //183: Guard 4 (Standing)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 4, EnemyType.GUARD, 0, DIRS.DOWN, false),

                    //184: Guard 4 (Moving)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 4, EnemyType.GUARD, 0, DIRS.RIGHT, true),

                    //185: Guard 4 (Moving)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 4, EnemyType.GUARD, 0, DIRS.UP, true),

                    //186: Guard 4 (Moving)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 4, EnemyType.GUARD, 0, DIRS.LEFT, true),

                    //187: Guard 4 (Moving)
                    new DoodadInfo(true, "snowman", 0, 6, 0, 4, EnemyType.GUARD, 0, DIRS.DOWN, true),

                    //188: Officer 4 (Standing)
                    new DoodadInfo(true, "officer", 0, 6, 0, 4, EnemyType.OFFICER, 0, DIRS.RIGHT, false),

                    //189: Officer 4 (Standing)
                    new DoodadInfo(true, "officer", 0, 6, 0, 4, EnemyType.OFFICER, 0, DIRS.UP, false),

                    //190: Officer 4 (Standing)
                    new DoodadInfo(true, "officer", 0, 6, 0, 4, EnemyType.OFFICER, 0, DIRS.LEFT, false),

                    //191: Officer 4 (Standing)
                    new DoodadInfo(true, "officer", 0, 6, 0, 4, EnemyType.OFFICER, 0, DIRS.DOWN, false),

                    //192: Officer 4 (Moving)
                    new DoodadInfo(true, "officer", 0, 6, 0, 4, EnemyType.OFFICER, 0, DIRS.RIGHT, true),

                    //193: Officer 4 (Moving)
                    new DoodadInfo(true, "officer", 0, 6, 0, 4, EnemyType.OFFICER, 0, DIRS.UP, true),

                    //194: Officer 4 (Moving)
                    new DoodadInfo(true, "officer", 0, 6, 0, 4, EnemyType.OFFICER, 0, DIRS.LEFT, true),

                    //195: Officer 4 (Moving)
                    new DoodadInfo(true, "officer", 0, 6, 0, 4, EnemyType.OFFICER, 0, DIRS.DOWN, true),

                    //196: Dr. Schabbs
                    new DoodadInfo(true, "iceking", 0, 0, 0, 0, EnemyType.SCHABBS, 0, DIRS.RIGHT, false),

                    //197: Gretel Grosse
                    new DoodadInfo(true, "iceking", 0, 0, 0, 0, EnemyType.GRETEL, 0, DIRS.RIGHT, false),

                    //198: SS 4 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 4, EnemyType.SS, 2, DIRS.RIGHT, false),

                    //199: SS 4 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 4, EnemyType.SS, 2, DIRS.UP, false),

                    //200: SS 4 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 4, EnemyType.SS, 2, DIRS.LEFT, false),

                    //201: SS 4 (Standing)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 4, EnemyType.SS, 2, DIRS.DOWN, false),

                    //202: SS 4 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 4, EnemyType.SS, 2, DIRS.RIGHT, true),

                    //203: SS 4 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 4, EnemyType.SS, 2, DIRS.UP, true),

                    //204: SS 4 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 4, EnemyType.SS, 2, DIRS.LEFT, true),

                    //205: SS 4 (Moving)
                    new DoodadInfo(true, "lemongrab", 0, 6, 0, 4, EnemyType.SS, 2, DIRS.DOWN, true),

                    //206: nothing
                    new DoodadInfo(),

                    //207: nothing
                    new DoodadInfo(),

                    //208: nothing
                    new DoodadInfo(),

                    //209: nothing
                    new DoodadInfo(),

                    //210: Dog 4 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 4, EnemyType.DOG, 0, DIRS.RIGHT, true),  // FIXME Not complete

                    //211: Dog 4 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 4, EnemyType.DOG, 0, DIRS.UP, true),  // FIXME Not complete

                    //212: Dog 4 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 4, EnemyType.DOG, 0, DIRS.LEFT, true),  // FIXME Not complete

                    //213: Dog 4 (Moving)
                    new DoodadInfo(true, "penguin", 0, 0, 0, 4, EnemyType.DOG, 0, DIRS.DOWN, true),  // FIXME Not complete

                    //214: Hans Grosse
                    new DoodadInfo(true, "iceking", 0, 0, 0, 0, EnemyType.HANS, 0, DIRS.RIGHT, false),
                    
                    //215: Otto Giftmacher
                    new DoodadInfo(true, "iceking", 0, 0, 0, 0, EnemyType.GUARD, 0, DIRS.RIGHT, false),

                    //216: Mutant 1 (Standing)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 1, EnemyType.MUTANT, 0, DIRS.RIGHT, false),  // FIXME Not complete

                    //217: Mutant 1 (Standing)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 1, EnemyType.MUTANT, 0, DIRS.UP, false),  // FIXME Not complete

                    //218: Mutant 1 (Standing)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 1, EnemyType.MUTANT, 0, DIRS.LEFT, false),  // FIXME Not complete

                    //219: Mutant 1 (Standing)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 1, EnemyType.MUTANT, 0, DIRS.DOWN, false),  // FIXME Not complete

                    //220: Mutant 1 (Moving)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 1, EnemyType.MUTANT, 0, DIRS.RIGHT, true),  // FIXME Not complete

                    //221: Mutant 1 (Moving)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 1, EnemyType.MUTANT, 0, DIRS.UP, true),  // FIXME Not complete

                    //222: Mutant 1 (Moving)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 1, EnemyType.MUTANT, 0, DIRS.LEFT, true),  // FIXME Not complete

                    //223: Mutant 1 (Moving)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 1, EnemyType.MUTANT, 0, DIRS.DOWN, true),  // FIXME Not complete

                    //224: Red PacMan
                    new DoodadInfo(),

                    //225: Yellow PacMan
                    new DoodadInfo(),

                    //226: Rose PacMan
                    new DoodadInfo(),

                    //227: Blue PacMan
                    new DoodadInfo(),

                    //228: Nothing
                    new DoodadInfo(),

                    //229: Nothing
                    new DoodadInfo(),

                    //230: Nothing
                    new DoodadInfo(),

                    //231: Nothing
                    new DoodadInfo(),

                    //232: Nothing
                    new DoodadInfo(),

                    //233: Nothing
                    new DoodadInfo(),

                    //234: Mutant 3 (Standing)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 3, EnemyType.MUTANT, 0, DIRS.RIGHT, false),  // FIXME Not complete

                    //235: Mutant 3 (Standing)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 3, EnemyType.MUTANT, 0, DIRS.UP, false),  // FIXME Not complete

                    //236: Mutant 3 (Standing)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 3, EnemyType.MUTANT, 0, DIRS.LEFT, false),  // FIXME Not complete

                    //237: Mutant 3 (Standing)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 3, EnemyType.MUTANT, 0, DIRS.DOWN, false),  // FIXME Not complete

                    //238: Mutant 3 (Moving)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 3, EnemyType.MUTANT, 0, DIRS.RIGHT, true),  // FIXME Not complete

                    //239: Mutant 3 (Moving)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 3, EnemyType.MUTANT, 0, DIRS.UP, true),  // FIXME Not complete

                    //240: Mutant 3 (Moving)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 3, EnemyType.MUTANT, 0, DIRS.LEFT, true),  // FIXME Not complete

                    //241: Mutant 3 (Moving)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 3, EnemyType.MUTANT, 0, DIRS.DOWN, true),  // FIXME Not complete

                    //242: Nothing
                    new DoodadInfo(),

                    //243: Nothing
                    new DoodadInfo(),

                    //244: Nothing
                    new DoodadInfo(),

                    //245: Nothing
                    new DoodadInfo(),

                    //246: Nothing
                    new DoodadInfo(),

                    //247: Nothing
                    new DoodadInfo(),

                    //248: Nothing
                    new DoodadInfo(),

                    //249: Nothing
                    new DoodadInfo(),

                    //250: Nothing
                    new DoodadInfo(),

                    //251: Nothing
                    new DoodadInfo(),

                    //252: Mutant 4 (Standing)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 4, EnemyType.MUTANT, 0, DIRS.RIGHT, false),  // FIXME Not complete

                    //253: Mutant 4 (Standing)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 4, EnemyType.MUTANT, 0, DIRS.UP, false),  // FIXME Not complete

                    //254: Mutant 4 (Standing)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 4, EnemyType.MUTANT, 0, DIRS.LEFT, false),  // FIXME Not complete

                    //255: Mutant 4 (Standing)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 4, EnemyType.MUTANT, 0, DIRS.DOWN, false),  // FIXME Not complete

                    //256: Mutant 4 (Moving)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 4, EnemyType.MUTANT, 0, DIRS.RIGHT, true),  // FIXME Not complete

                    //257: Mutant 4 (Moving)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 4, EnemyType.MUTANT, 0, DIRS.UP, true),  // FIXME Not complete

                    //258: Mutant 4 (Moving)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 4, EnemyType.MUTANT, 0, DIRS.LEFT, true),  // FIXME Not complete

                    //259: Mutant 4 (Moving)
                    new DoodadInfo(true, "icekingboss", 0, 6, 0, 4, EnemyType.MUTANT, 0, DIRS.DOWN, true),  // FIXME Not complete

            };
}
