package deco2800.arcade.towerdefence;

import java.util.UUID;

/**
 * Grid object to store attributes of a generated Isometric Grid.
 * @author hadronn
 * 
 */
public class Grid {
	//Fields
	//The unique id of the Grid.
	private final UUID id;
	//The grid's name, for making human recognisable variations if necessary.
	private String name;
	//The grid's max width.
	private int width = 0;
	//The grid's max depth.
	private int depth = 0;
	
	//Constructor
	/**
	 * Instantiates a grid with a unique UUID, length, width and name.
	 * @param width
	 * @param depth
	 * @param name
	 */
	public Grid(int width, int depth, String name){
		id = UUID.randomUUID();
		this.width = width;
		this.depth = depth;
		this.name = name;
	}
	
	//Getters
	/**
	 * Returns the max width of the grid
	 * @return int
	 */
	public int width(){
		return this.width;
	}
	
	/**
	 * Returns the max depth of the grid
	 * @return int
	 */
	public int depth(){
		return this.depth;
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
		this.width = a;
	}
	
	/**
	 * Sets the depth of the grid to be drawn as integer a.
	 * @param a
	 */
	public void depth(int a){
		this.depth = a;
	}
	
	/**
	 * Renames the grid to newName. Not for use as a unique identifier use the UUID id.
	 * @param newName
	 */
	public void name(String newName){
		this.name = newName;
	}
}
