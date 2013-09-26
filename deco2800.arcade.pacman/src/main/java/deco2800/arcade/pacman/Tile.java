package deco2800.arcade.pacman;

/** A square in the grid of pacman. Can be either a dot, energiser, fruit, wall, or door to ghost pen
 * 
 */
public abstract class Tile {

	private int sideLength; //length of side of square
	private Tile north;
	private Tile east;
	private Tile south;
	private Tile west;
	
	public Tile(int sideLength) {
		this.sideLength = sideLength;
	}
	
	//returns the type of tile
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
	
}
