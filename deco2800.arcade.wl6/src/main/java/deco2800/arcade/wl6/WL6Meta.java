package deco2800.arcade.wl6;

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
                id >= WL6Meta.DOOR_SOLVERKEY && id < WL6Meta.DOOR_SOLVERKEY + 2 ||
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
        // TODO Change this so to check actually spawned doodads rather than all potential ones
        if (WL6Meta.block(map.getTerrainAt(x, y)).solid ||
                WL6Meta.doodad(map.getDoodadAt(x, y)).solid) {
            return true;
        }
        return false;
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
