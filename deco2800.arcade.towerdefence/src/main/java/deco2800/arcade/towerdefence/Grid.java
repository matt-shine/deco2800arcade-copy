package deco2800.arcade.towerdefence;

import java.util.UUID;

/**
 * A unique identifier for a generated Isometric Grid.
 * @author hadronn
 *
 */
public class Grid {
	//fields
	private UUID id;
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
}
