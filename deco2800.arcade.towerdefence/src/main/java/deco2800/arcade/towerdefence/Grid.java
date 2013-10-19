package deco2800.arcade.towerdefence;

import deco2800.arcade.towerdefence.pathfinding.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.math.Vector2;

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
	private int tileSize;
	// The dimensions of the grid in grid squares
	private int gridWidth, gridDepth;
	// The contents of the grid. 0 means clear, 1 means tower, 2 means wall, 3
	// means wall with tower, and 4 means blocked.
	private List<List<List<GridObject>>> gridContents;
	// The ship that is using this grid
	private Ship ship;
	// The pathfinder used by objects in the grid
	public AStarPathFinder pathfinder;
	// The target of pathfinding movement (portal)
	private Vector2 targetPosition;

	// Constructor
	/**
	 * Instantiates a grid with a unique UUID, length, width and name.
	 * 
	 * @param width
	 *            The width of the grid, in pixels
	 * @param depth
	 *            The depth of the grid, in pixels
	 * @param name
	 *            The name for this grid, for extensibility
	 * @param tileSize
	 *            The size of each grid tile, in pixels
	 * @param ship
	 *            The ship which this grid belongs to
	 * @param targetPosition
	 *            A vector representing the target (portal)
	 */
	public Grid(int width, int depth, String name, int tileSize, Ship ship, Vector2 targetPosition) {
		id = UUID.randomUUID();
		this.width = width;
		this.depth = depth;
		this.name = name;
		this.tileSize = tileSize;
		this.ship = ship;
		gridWidth = width / tileSize;
		gridDepth = depth / tileSize;
		gridContents = new ArrayList<List<List<GridObject>>>(gridWidth);
		// initialize the contents of GridContents
		for (int i = 0; i < gridWidth; i++) {
			gridContents.add(new ArrayList<List<GridObject>>());
			for (int j = 0; j < gridDepth; j++) {
				gridContents.get(i).add(new ArrayList<GridObject>());
			}
		}
		this.targetPosition = targetPosition;
	}

	// Getters
	/**
	 * Returns the max width of the grid
	 * 
	 * @return int The width of the grid
	 */
	public int width() {
		return this.width;
	}

	/**
	 * Returns the max depth of the grid
	 * 
	 * @return int The depth of the grid
	 */
	public int depth() {
		return this.depth;
	}

	/**
	 * Returns the name of the grid
	 * 
	 * @return String The name of the grid
	 */
	public String name() {
		return this.name;
	}

	/**
	 * Returns the unique id of the grid.
	 * 
	 * @return UUID The ID of the grid
	 */
	public UUID id() {
		return this.id;
	}

	/**
	 * Get the width of the grid, in tiles.
	 * 
	 * @return The width of the grid in tiles.
	 */
	public int getWidthInTiles() {
		return gridWidth;
	}

	/**
	 * Get the height of the grid, in tiles.
	 * 
	 * @return The height of the grid in tiles.
	 */
	public int getHeightInTiles() {
		return gridDepth;
	}

	// Setters
	/**
	 * Sets the width of the grid to be drawn as integer a.
	 * 
	 * @param a
	 *            The new width
	 */
	public void width(int a) {
		this.width = a;
		this.gridWidth = a / tileSize;
	}

	/**
	 * Sets the depth of the grid to be drawn as integer a.
	 * 
	 * @param a
	 *            The new depth
	 */
	public void depth(int a) {
		this.depth = a;
		this.gridDepth = a / tileSize;
	}

	/**
	 * Renames the grid to newName. Not for use as a unique identifier use the
	 * UUID id.
	 * 
	 * @param newName
	 *            The new name
	 */
	public void name(String newName) {
		this.name = newName;
	}

	/**
	 * Sets the size of the tiles to the given value.
	 * 
	 * @param tileSize
	 *            The new tile size.
	 */
	public void tileSize(int tileSize) {
		this.tileSize = tileSize;
		this.gridWidth = width / tileSize;
		this.gridDepth = depth / tileSize;
	}

	/**
	 * Get the ship which this grid belongs to.
	 * 
	 * @return The ship which this grid belongs to
	 */
	public Ship ship() {
		return ship;
	}

	// Methods
	public void pathFinderVisited(int x, int y) {
		// do nothing - not needed

	}

	public boolean blocked(Mobile mover, int x, int y) {
		Iterator<GridObject> thisGridObjects = getGridContents(x, y).iterator();
		while (thisGridObjects.hasNext()) {
			GridObject current = thisGridObjects.next();
			if (current.getClass() != Enemy.class) {
				return true;
			}
		}
		return false;
	}

	public float getCost(Mobile mover, int sx, int sy, int tx, int ty) {
		// Currently no special tiles to consider - always return 1
		return 1;
	}

	/**
	 * Try and build an object at the specified grid. If it is blocked, return
	 * false, otherwise return true and modify the gridContents appropriately.
	 * 
	 * @param object
	 *            The grid object to place. Its coordinates are determined via
	 *            the position of the object.
	 */
	public boolean buildObject(GridObject object) {
		int x, y;
		x = (int) object.positionInTiles().x;
		y = (int) object.positionInTiles().y;
		Iterator<GridObject> thisGridObjects = getGridContents(x, y).iterator();
		while (thisGridObjects.hasNext()) {
			GridObject current = thisGridObjects.next();
			// Check for block wall
			// Check for towers
			// Check for aliens
			if (current.getClass() == Enemy.class) {
				return false;
			}
		}

		// Start the object AI and add it to the grid
		object.start();
		getGridContents(x, y).add(object);

		return true;
	}

	/**
	 * Get the contents of the specified grid location
	 * 
	 * @param x
	 *            The x-coordinate, in tiles
	 * @param y
	 *            The y-coordinate, in tiles
	 * @return A list of objects at that coordinate
	 */
	public List<GridObject> getGridContents(int x, int y) {
		return gridContents.get(x).get(y);
	}

	/**
	 * Get the size of a single tile
	 * 
	 * @return an int representing the size of a single tile
	 */
	public int getTileSize() {
		return tileSize;
	}

	/**
	 * Move the given object from its previous position to the new position.
	 * 
	 * @param object
	 *            The object to be moved
	 * @param position
	 *            The object's current position
	 * @param newPosition
	 *            The position to move the object to
	 */
	public void moveObject(GridObject object, Vector2 newPosition) {
		// Remove it from the old position
		gridContents.get((int) object.positionInTiles().x).get((int) object.positionInTiles().y).remove(object);
		
		// Add it to the new one
		gridContents.get((int) newPosition.x).get((int) newPosition.y)
				.add(object);
	}

}
