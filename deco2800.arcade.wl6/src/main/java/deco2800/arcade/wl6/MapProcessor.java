package deco2800.arcade.wl6;

import com.badlogic.gdx.math.Vector2;

public class MapProcessor {


	
	/**
	 * Takes the game and generates all the items on the map. Most items are
	 * handled automatically as described in WL6Meta, but for the few special cases,
	 * This is where they are handled.
	 * @param model
	 */
	public static void processEverything(GameModel model) {
		
		Level map = model.getMap();
		
		for (int i = 0; i < WL6.MAP_DIM; i++) {
		    for (int j = 0; j < WL6.MAP_DIM; j++) {
		    	
		    	//now generate all the items
		    	
		    	int id = map.getDoodadAt(i, j);
		    	DoodadInfo dInfo = WL6Meta.doodad(id);
		    	
		    	//spawn points
		    	if (id >= WL6Meta.SPAWN_POINT && id < WL6Meta.SPAWN_POINT + 4) {
		    		
		    		//TODO the angle doesn't work quite right -- don't know why
		    		model.setSpawnPoint(i + 0.5f, j + 0.5f, WL6Meta.dirToAngle(dInfo.direction));
		    		
		    	} else {
		    		
		    		//everything else
		    		spawnDoodadFromInfo(model, dInfo, id, i, j);
		    		
		    	}
		    	
		    	
		    }
		}
		
		
		
		
	}
	
	
	
	/**
	 * Unique ids for doodads. TODO make this better
	 * @param x
	 * @param y
	 * @return
	 */
	public static int doodadID() {
		return (int) Math.floor(Math.random() * Integer.MAX_VALUE);
	}
	
	
	
	/**
	 * Takes a DoodadInfo and turns it into a real doodad.
	 * @param model The game
	 * @param d The DoodadInfo object
	 * @param id The tile id of the doodad
	 * @param x The x position of the doodad
	 * @param y The y position of the doodad
	 */
	public static void spawnDoodadFromInfo(GameModel model, DoodadInfo d, int id, int x, int y) {
		Doodad dd = null;
		
		if (d.special) {
			System.err.println("Tried to automatically generate a special case doodad: " + id + " (" + x + ", " + y + ")");
			return;
		}
		
		if (d.texture == null) {
			//this doodad is invisible so it must be a waypoint or something
			//we don't need to do anything
			
		} else if (d.enemytype != EnemyType.NOT_AN_ENEMY) {
			
			
			//TODO spawn an enemy
			
			
		} else if (d.health != 0 || d.gun != 0 || d.ammo != 0 || d.points != 0) {
			
			
			//TODO spawn a pickup
			
			
		} else if (d.solid) {
			
			//TODO make these solid
			dd = new Doodad(doodadID());
			dd.setTextureName(d.texture);
			
		} else {
			
			//spawn a static nonsolid doodad
			dd = new Doodad(doodadID());
			dd.setTextureName(d.texture);
			
		}
		
		
		if (dd != null) {
			dd.setPos(new Vector2(x + 0.5f, y + 0.5f));
			model.addDoodad(dd);
		}
		
	}
	
	
	
	
}
