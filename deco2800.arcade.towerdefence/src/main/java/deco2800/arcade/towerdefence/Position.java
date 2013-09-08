package deco2800.arcade.towerdefence;

/**
 * An x,y entry pair for determining location within the game system.
 * @author hadronn
 *
 */
public class Position {
	//Fields
	private int x;
	private int y;

	//Constructor
	/**
	 * Instantiates a position with an x, y.
	 * @param x
	 * @param y
	 */
	public Position(int x, int y){
		this.x = x;
		this.y = y;
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
