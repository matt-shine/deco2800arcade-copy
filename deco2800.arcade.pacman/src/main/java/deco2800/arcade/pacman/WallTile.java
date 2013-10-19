package deco2800.arcade.pacman;

import java.util.ArrayList;

public final class WallTile extends Tile {

	//Game map should be 28x31 tiles
	/** A char representing the type of wallTile. Same char used in file  
	 */
	private final char type;
	
	public WallTile(GameMap gameMap, char type) {
		super(gameMap);
		this.type = type;
		this.setMovers(new ArrayList<Mover>());
//		if (type == 'Q') {0.
		
		
//			this.setMovers(new ArrayList<Mover>());
//		} else {
//			this.setMovers(null);
//		}
	}	
		
	public String toString() {
		return "Wall" + super.toString();
	}

	public char getType() {
		return type;
	}
}
