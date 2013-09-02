package deco2800.arcade.towerdefence;

import java.util.UUID;

/**
 * A unique identifier for a generated Isometric Grid.
 * @author hadronn
 *
 */
public class Grid {
	//fields
	private final UUID id;
	private String name;
	private int x = 0;
	private int y = 0;
	
	//constructor
	/**
	 * Instantiates a grid with a unique UUID, length, width and name.
	 * @param x
	 * @param y
	 * @param name
	 */
	public Grid(int x, int y, String name){
		id = UUID.randomUUID();
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	//Getters
	/**
	 * Returns the max x of the grid
	 * @return int
	 */
	public int width(){
		return this.x;
	}
	
	/**
	 * Returns the max y of the grid
	 * @return int
	 */
	public int depth(){
		return this.y;
	}
	
	/**
	 * Returns the name of the grid
	 * @return String
	 */
	public String name(){
		return this.name;
	}
	/**
	 * Returns the unique id of the grid.
	 * @return UUID
	 */
	public UUID id(){
		return this.id;
	}
	
	//Setters
	/**
	 * Sets the width of the grid to be drawn as integer a.
	 * @param a
	 */
	public void width(int a){
		this.x = a;
	}
	
	/**
	 * Sets the depth of the grid to be drawn as integer a.
	 * @param a
	 */
	public void depth(int a){
		this.y = a;
	}
	
	/**
	 * Renames the grid to newName. Not for use as a unique identifier use the UUID id.
	 * @param newName
	 */
	public void name(String newName){
		this.name = newName;
	}
}
