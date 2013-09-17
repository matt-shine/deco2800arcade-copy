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
		    	switch (map.getDoodadAt(i, j)) {
		    	
		    	//facing up
		    	//TODO implement spawn facing direction
		    	case WL6Meta.SPAWN_POINT:
		    		model.setSpawnPoint(i + 0.5f, j + 0.5f);
		    		break;
		    	
		    	//facing right
		    	case WL6Meta.SPAWN_POINT + 1:
		    		model.setSpawnPoint(i + 0.5f, j + 0.5f);
		    		break;
		    	
		    	//facing down
		    	case WL6Meta.SPAWN_POINT + 2:
		    		model.setSpawnPoint(i + 0.5f, j + 0.5f);
		    		break;
		    		
		    	//facing right
		    	case WL6Meta.SPAWN_POINT + 3:
		    		model.setSpawnPoint(i + 0.5f, j + 0.5f);
		    		break;
		    		
		    	//TODO doors, hidden passages etc
		    		
		    		
		    		
		    		
		    		
		    	default:
		    		spawnDoodadFromInfo(
		    				model,
		    				WL6Meta.doodad(map.getDoodadAt(i, j)),
		    				map.getDoodadAt(i, j),
		    				i, j
		    		);
		    	
		    	}
		    	
		    	
		    }
		}
		
		
		
		
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
		
		if (d.texture == null) {
			//this doodad is invisible so it must be a waypoint or something
			//we don't need to do anything
			
		} else if (d.enemytype != EnemyType.NOT_AN_ENEMY) {
			
			
			//TODO spawn an enemy
			
			
		} else if (d.health != 0 || d.gun != 0 || d.ammo != 0 || d.points != 0) {
			
			
			//TODO spawn a pickup
			
			
		} else if (d.solid) {
			
			
			//TODO spawn a solid static doodad
			
			
		} else {
			
			
			dd = new Doodad();
			dd.setTextureName(d.texture);
			System.out.println();
			
		}
		
		
		if (dd != null) {
			dd.setPos(new Vector2(x + 0.5f, y + 0.5f));
			model.addDoodad(dd);
		}
		
	}
	
	
	
	
}
