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
				new BlockInfo(true, "wood"),

				//13: entranceelavator
				new BlockInfo(true, "entranceelavator"),

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
				
				//23: nothing
				DoodadInfo.nonsolidScenery("doodad"),
				
				//24: oil drum
				DoodadInfo.solidScenery("doodad"),
				
				//25: table and chairs
				DoodadInfo.solidScenery("doodad"),
				
				//26: lamp
				DoodadInfo.nonsolidScenery("doodad"),
				
				//26: chandelier
				DoodadInfo.nonsolidScenery("doodad"),
				
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
