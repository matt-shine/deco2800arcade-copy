package deco2800.arcade.wl6;

public class WL6Meta {
	
	
	
	private static BlockInfo[] blocks;
	private static DoodadInfo[] doodads;
	
	static {
		blocks = new BlockInfo[]{
				
				//0: nothing
				new BlockInfo(),
				
				//1: wall
				new BlockInfo(true, "unknown"),
				
				//2: nothing
				new BlockInfo(),
				
				//3: wall
				new BlockInfo(true, "unknown"),
				
		};
		doodads = new DoodadInfo[]{
				
				//0: nothing
				new DoodadInfo(),
				
				//1: pickup
				DoodadInfo.treasurePickup("unknown", 5000),
				
		};
	}
	
	public static BlockInfo block(int id) {
		if (id >= blocks.length) {
			return new BlockInfo();
		}
		return blocks[id].clone();
	}
	
	
	public static DoodadInfo doodad(int id) {
		if (id >= doodads.length) {
			return new DoodadInfo();
		}
		return doodads[id].clone();
	}
	
}
