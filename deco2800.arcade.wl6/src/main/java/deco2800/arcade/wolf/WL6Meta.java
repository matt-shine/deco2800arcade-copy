package deco2800.arcade.wolf;

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
	public static final int ELEVATOR = 21;
    public static final int DOOR = 90;
    public static final int DOOR_GOLDKEY = 92;
    public static final int DOOR_SILVERKEY = 94;
    public static final int DOOR_ELEVATOR = 100;
    public static final int GOLDKEY = 43;
    public static final int SILVERKEY = 44;
    public static final int SECRET_DOOR = 98;
    public static final int SECRET_ELEVATOR = 107;
    public static final int ENDGAME = 99;


    public static enum DIRS {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        UPRIGHT,
        UPLEFT,
        DOWNRIGHT,
        DOWNLEFT,
    }

    public static enum KEY_TYPE {
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
            case UPRIGHT:
                return 315;
            case UPLEFT:
                return 45;
            case DOWNRIGHT:
                return 225;
            case DOWNLEFT:
                return 135;
            default:
                return 0;
        }
    }
    
    
    
	public static final String[] levels = new String[]{
		"e1l1",
		"e1l2",
		"e1l3",
		"e1l4",
		"e1l5",
		"e1l6",
		"e1l7",
		"e1l8",
		"e1boss",
		"e1secret",
		"e2l1",
		"e2l2",
		"e2l3",
		"e2l4",
		"e2l5",
		"e2l6",
		"e2l7",
		"e2l8",
		"e2boss",
		"e2secret",
		"e3l1",
		"e3l2",
		"e3l3",
		"e3l4",
		"e3l5",
		"e3l6",
		"e3l7",
		"e3l8",
		"e3boss",
		"e3secret",
		"e4l1",
		"e4l2",
		"e4l3",
		"e4l4",
		"e4l5",
		"e4l6",
		"e4l7",
		"e4l8",
		"e4boss",
		"e4secret",
		"e5l1",
		"e5l2",
		"e5l3",
		"e5l4",
		"e5l5",
		"e5l6",
		"e5l7",
		"e5l8",
		"e5boss",
		"e5secret",
		"e6l1",
		"e6l2",
		"e6l3",
		"e6l4",
		"e6l5",
		"e6l6",
		"e6l7",
		"e6l8",
		"e6boss",
		"e6secret",
	};
	
	
	
	
	
    /**
     * Check to see if there is a door at the specified map coordinates
     * @param x x map coordinate
     * @param y y map coordinate
     * @param map map to check
     * @return true if there is a door, false otherwise
     */
    public static boolean hasDoorAt(int x, int y, Level map) {
        int id = map.getTerrainAt(x, y);
        return id >= WL6Meta.DOOR && id < WL6Meta.DOOR + 2 ||
                id >= WL6Meta.DOOR_GOLDKEY && id < WL6Meta.DOOR_GOLDKEY + 2 ||
                id >= WL6Meta.DOOR_SILVERKEY && id < WL6Meta.DOOR_SILVERKEY + 2 ||
                id >= WL6Meta.DOOR_ELEVATOR && id < WL6Meta.DOOR_ELEVATOR + 2;
    }

    public static boolean hasSecretDoorAt(int x, int y, Level map) {
        int id = map.getDoodadAt(x, y);
        return id >= WL6Meta.SECRET_DOOR;
    }

    /**
     * Check to see if there is a solid (non-transparent) block at the specified map coordinates.
     * This counts normal doors, but not secret doors.
     * @param x x map coordinate
     * @param y y map coordinate
     * @param map map to check
     * @return
     */
    public static boolean hasObscuringBlockAt(int x, int y, Level map) {
        return (WL6Meta.block(map.getTerrainAt(x, y)).texture != null && map.getDoodadAt(x, y) != WL6Meta.SECRET_DOOR)
                || hasDoorAt(x, y, map);
    }

    /**
     *
     * @param x
     * @param y
     * @param map
     * @return
     */
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
        return surrounded == 4;
    }

    /**
     *
     * @param x
     * @param y
     * @param map
     * @return
     */
    public static boolean hasSolidBlockAt(int x, int y, Level map) {
        if (x >= 64 || x <= 0 || y >= 64 || y <=0) {
            return true;
        }
        return WL6Meta.block(map.getTerrainAt(x, y)).solid;
    }

    private static BlockList blockList = new BlockList();
    private static DoodadList doodadList = new DoodadList();

    private static BlockInfo[] blocks = blockList.getBlocks();
    private static DoodadInfo[] doodads = doodadList.getDoodads();

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
