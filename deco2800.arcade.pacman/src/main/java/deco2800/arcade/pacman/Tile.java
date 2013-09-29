package deco2800.arcade.pacman;

/** A square in the grid of pacman. Can be either a dot, energiser, fruit, wall, 
 * or door to ghost pen
 * 
 */
public abstract class Tile {

	private static int sideLength = 8; //length of side of square- should be same for all tiles
	private Tile north;
	private Tile east;
	private Tile south;
	private Tile west;
	
	public Tile() {
	}
	
	//returns the type of tile
	// may change this implementation so each tile just knows its grid location, 
	//but these methods would still exist
	protected Class<? extends Tile> getNorthType() {
		return north.getClass(); 
	}
	
	protected Class<? extends Tile> getEastType() {
		return east.getClass(); 
	}
	
	protected Class<? extends Tile> getSouthType() {
		return south.getClass(); 
	}
	
	protected Class<? extends Tile> getWestType() {
		return west.getClass(); 
	}

	public static int getSideLength() {
		return sideLength;
	}	
}
