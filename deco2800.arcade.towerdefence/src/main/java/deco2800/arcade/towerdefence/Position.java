package deco2800.arcade.towerdefence;

/**
 * An x,y,Grid entry tuple for determining location within the game system.
 * @author hadronn
 *
 */
public class Position {
	//fields
	private int x;
	private int y;
	/**
	 * The grid the position belongs to, is unique.
	 */
	private final Grid grid;

	//constructor
	/**
	 * Instantiates a position with an x, y and Grid.
	 * @param x
	 * @param y
	 * @param grid
	 */
	public Position(int x, int y, Grid grid){
		this.x = x;
		this.y = y;
		this.grid = grid;
	}
	
	//Getters
	/**
	 * With no parameters returns the x coordinate. With an integer parameter sets the x coordinate.
	 * @return int
	 */
	public int x(){
		return x;
	}
	
	/**
	 * With no parameters returns the y coordinate. With an integer parameter sets the y coordinate.
	 * @return int
	 */
	public int y(){
		return y;
	}
	
	/**
	 * Returns the unique id of the grid this position is on. Important for multiplayer.
	 * @return String
	 */
	public Grid grid(){
		return this.grid;
	}
	
	//Setters
	/**
	 * With no parameters returns the x coordinate. With an integer parameter sets the x coordinate.
	 * @param a
	 */
	public void x(int a){
		this.x = a;
	}
	
	/**
	 * With no parameters returns the y coordinate. With an integer parameter sets the y coordinate.
	 * @param a
	 */
	public void y(int a){
		this.y = a;
	}
	
	/**
	 * Sets the x and y values to integers a and b respectively.
	 * @param a
	 * @param b
	 */
	public void xy(int a, int b){
		this.x = a;
		this.y = b;
	}
}
