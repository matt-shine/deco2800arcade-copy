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
	
	
	
	private static BlockInfo[] blocks;
	private static DoodadInfo[] doodads;
	
	static {
		blocks = new BlockInfo[]{
				
				//0: nothing
				new BlockInfo(),
				
				//1: grey brick wall
				new BlockInfo(true, "unknown"),
				
				//2: grey brick wall 2
				new BlockInfo(true, "unknown"),
				
				//3: grey brick wall with nazi flag
				new BlockInfo(true, "unknown"),
				
				//4: grey brick wall with hitler portrait
				new BlockInfo(true, "unknown"),
				
				//5: prison cell (blue brick)
				new BlockInfo(true, "unknown"),
				
				//6: grey brick with nazi eagle
				new BlockInfo(true, "unknown"),
				
				//7: prison cell with skeleton (blue brick)
				new BlockInfo(true, "unknown"),
				
				//8: blue brick
				new BlockInfo(true, "unknown"),
				
				//9: blue brick 2
				new BlockInfo(true, "unknown"),
				
				//10: wood wall with nazi eagle
				new BlockInfo(true, "unknown"),
				
				//11: wood wall with portrait of hitler
				new BlockInfo(true, "unknown"),
				
				//12: wood wall
				new BlockInfo(true, "unknown"),
				
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
				
				//19: spawn point up - do nothing, this is handled in the MapProcessor
				new DoodadInfo(),
				
				//20: spawn point right - do nothing, this is handled in the MapProcessor
				new DoodadInfo(),
				
				//21: spawn point down - do nothing, this is handled in the MapProcessor
				new DoodadInfo(),
				
				//22: spawn point left- do nothing, this is handled in the MapProcessor
				new DoodadInfo(),
				
				//23: nothing
				new DoodadInfo(),
				
				//24: oil drum
				DoodadInfo.solidScenery("unknown"),
				
				//25: table and chairs
				DoodadInfo.solidScenery("unknown"),
				
				//26: floor light
				DoodadInfo.nonsolidScenery("unknown"),
				
				//26: chandelier
				DoodadInfo.nonsolidScenery("unknown"),
				
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
