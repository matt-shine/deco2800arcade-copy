package deco2800.arcade.towerdefence;

import deco2800.arcade.towerdefence.pathfinding.*;
import java.util.UUID;

/**
 * Grid object to store attributes of a generated Grid.
 * 
 * @author hadronn
 * 
 */
public class Grid implements TileBasedMap {
	// Fields
	// The unique id of the Grid.
	private final UUID id;
	// The grid's name, for making human recognisable variations if necessary.
	private String name;
	// The grid's max width.
	private int width, depth;
	// The grid's max depth.
	// The size of each grid square
	private int gridSize;
	// The dimensions of the grid in grid squares
	private int gridWidth, gridDepth;
	// The contents of the grid. 0 means clear, 1 means tower, 2 means wall, 3
	// means wall with tower, and 4 means blocked.
	private int gridContents[][];

	// Constructor
	/**
	 * Instantiates a grid with a unique UUID, length, width and name.
	 * 
	 * @param width
	 * @param depth
	 * @param name
	 */
	public Grid(int width, int depth, String name, int gridSize) {
		id = UUID.randomUUID();
		this.width = width;
		this.depth = depth;
		this.name = name;
		this.gridSize = gridSize;
		gridWidth = width / gridSize;
		gridDepth = depth / gridSize;
		gridContents = new int[gridWidth][gridDepth];
	}

	// Getters
	/**
	 * Returns the max width of the grid
	 * 
	 * @return int
	 */
	public int width() {
		return this.width;
	}

	/**
	 * Returns the max depth of the grid
	 * 
	 * @return int
	 */
	public int depth() {
		return this.depth;
	}

	/**
	 * Returns the name of the grid
	 * 
	 * @return String
	 */
	public String name() {
		return this.name;
	}

	/**
	 * Returns the unique id of the grid.
	 * 
	 * @return UUID
	 */
	public UUID id() {
		return this.id;
	}

	// Setters
	/**
	 * Sets the width of the grid to be drawn as integer a.
	 * 
	 * @param a
	 */
	public void width(int a) {
		this.width = a;
	}

	/**
	 * Sets the depth of the grid to be drawn as integer a.
	 * 
	 * @param a
	 */
	public void depth(int a) {
		this.depth = a;
	}

	/**
	 * Renames the grid to newName. Not for use as a unique identifier use the
	 * UUID id.
	 * 
	 * @param newName
	 */
	public void name(String newName) {
		this.name = newName;
	}

	public int getWidthInTiles() {
		return gridWidth;
	}

	public int getHeightInTiles() {
		return gridDepth;
	}

	public void pathFinderVisited(int x, int y) {
		// do nothing - not needed

	}

	public boolean blocked(Mobile mover, int x, int y) {
		if(gridContents[x][y] == 0){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public float getCost(Mobile mover, int sx, int sy, int tx, int ty) {
		//Currently no special tiles to consider - always return 1
		return 1;
	}
}
