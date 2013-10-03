package deco2800.arcade.mixmaze.domain;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static deco2800.arcade.mixmaze.domain.Direction.*;

/**
 * TileModel represents a tile on the game board.
 */
public class TileModel {
	
	final Logger logger = LoggerFactory.getLogger(TileModel.class);

	/** Column number */
	private int x;
	
	/** Row number */
	private int y;
	
	/** Whether the box is built or not */
	private boolean isBoxBuilt;
	
	/** Walls adjacent to this tile */
	private WallModel[] walls = null;
	
	/** Tiles adjacent to this tile */
	private TileModel[] adjTiles;
	
	/** Player who built the box or <code>null</code> if not built */
	private PlayerModel boxer = null;
	
	/** Observers to this tile */
	private ArrayList<TileModelObserver> observers;

	/**
	 * Constructs a new <code>TileModel</code> at 
	 * (<code>x</code>, <code>y</code>) with <code>adjWalls</code>
	 * surrounding this <code>TileModel</>.
	 *
	 * @param x		the column number (origin at top left)
	 * @param y		the row number (origin at top left)
	 * @param adjTiles	the tiles adjacent to this tile
	 */
	public TileModel(int x, int y, TileModel[] adjTiles) {
		this.x = x;
		this.y = y;
		this.adjTiles = adjTiles;
		observers = new ArrayList<TileModelObserver>();
		isBoxBuilt = false;
		initWalls();
	}
	
	/**
	 * Initialises the walls adjacent to this tile.
	 */
	private void initWalls() {
		TileModel tile;
		int polarDir;
		
		walls = new WallModel[4];
		for (int direction = 0; direction < 4; ++direction) {
			tile = adjTiles[direction];
			polarDir = Direction.getPolarDirection(direction);
			if (tile != null) {
				walls[direction] = tile.getWall(polarDir);
				tile.addAdjacent(this, polarDir);
			} else {
				walls[direction] = new WallModel();
			}
			walls[direction].addTile(this);
		}
	}

	/**
	 * Records an adjacent tile to this tile.
	 * 
	 * @param tile		the adjacent tile
	 * @param direction	the direction of the adjacent tile to this tile
	 */
	private void addAdjacent(TileModel tile, int direction) {
		TileModel cTile = adjTiles[direction];
		
		if (cTile != null && cTile != tile) {
			throw new IllegalStateException("tile adjacency cannot be changed once set.");
		}
		adjTiles[direction] = tile;
	}

	/**
	 * Adds an observer to this tile.
	 * 
	 * @param observer	the observer
	 */
	public void addObserver(TileModelObserver observer) {
		observers.add(observer);
	}

	/**
	 * Returns the column number of this tile on game board.
	 * Origin is at the top left corner.
	 *
	 * @return the column number
	 */
	int getX() {
		return x;
	}

	/**
	 * Returns the row number of this tile on game board.
	 * Origin is at the top left corner.
	 *
	 * @return the row number
	 */
	int getY() {
		return y;
	}

	/**
	 * Checks if the wall on the specified <code>direction</code> is built.
	 *
	 * @param direction	the direction
	 * @return <code>true</code> if the wall is built, <code>false</code>
	 * otherwise.
	 */
	boolean isWallBuilt(int direction) {
		return getWall(direction).isBuilt();
	}

	/**
	 * Returns the adjacent wall specified by <code>direction</code>.
	 *
	 * @param direction 	the direction of the requested wall
	 * @return the adjacent wall in the specified <code>direction</code>
	 * @throws IllegalArgumentException If <code>direction</code> is not one
	 * of <code>WEST</code>, <code>NORTH</code>, <code>EAST</code>,
	 * or <code>SOUTH</code>.
	 */
	WallModel getWall(int direction) {
		if (!isDirection(direction))
			throw NOT_A_DIRECTION;

		return walls[direction];
	}

	/*
	 * TODO: doc
	 */
	private int getDirection(WallModel w) {
		for (int i = 0; i < 4; i++)
			if (w == walls[i])
				return i;
		return -1;
	}

	/**
	 * Validates the status of the box on this tile, and modifies the
	 * <code>boxer</code> based on any change.
	 *
	 * @param player	the player who used an action against this tile
	 */
	void validateBox(PlayerModel player) {
		if (!isBoxBuilt && isBox()) {
			isBoxBuilt = true;
			boxer = player;
			boxer.incrementScore();
			updateBoxer(player.getId());
		} else if (isBoxBuilt && !isBox()) {
			isBoxBuilt = false;
			boxer.decrementScore();
			boxer = null;
			updateBoxer(0);
		}
	}

	/**
	 * Updates all observers on the wall status.
	 * 
	 * @param wall
	 * @param isBuilt
	 */
	void updateWall(WallModel wall, boolean isBuilt) {
		for (TileModelObserver o : observers)
			o.updateWall(getDirection(wall), isBuilt);
	}

	/**
	 * Updates all observers on the item status.
	 * 
	 * @param type	the item type
	 */
	void updateType(ItemModel.Type type) {
		for (TileModelObserver o : observers)
			o.updateType(type);
	}

	/**
	 * Updates all observers on the boxer on this tile.
	 * 
	 * @param id	the id of the boxer
	 */
	private void updateBoxer(int id) {
		for (TileModelObserver o : observers)
			o.updateBoxer(id);
	}

	/*
	 * TODO: doc
	 */
	public List<TileModel> findPath(List<TileModel> path)
	{
		for(int direction = 0; direction < 4; ++direction) {
			WallModel wall = walls[direction];
			if(wall.isBuilt()) {
				if(Direction.isXDirection(direction)) {
					TileModel northTile = adjTiles[Direction.NORTH];
					if(northTile.getWall(Direction.SOUTH).isBuilt() || northTile.getWall(direction).isBuilt()) {

					}

					TileModel adjTile = adjTiles[direction];
					if(adjTile.getWall(Direction.NORTH).isBuilt() || adjTile.getWall(Direction.SOUTH).isBuilt()) {

					}
				} else {

				}

				switch(direction) {
				case Direction.WEST:
					break;
				case Direction.NORTH:
					break;
				case Direction.EAST:
					break;
				case Direction.SOUTH:
					break;
				}
			}
		}
		return path;
	}

	/*
	 * TODO
	 */
	private boolean checkWalls(int wallDirection, int tileDirection) {
		if(Direction.isXDirection(wallDirection)) {

		} else {

		}
		return false;
	}

	/**
	 * Checks if this tile is a complete box.
	 *
	 * @return <code>true</code> if this tile is a complete box, 
	 * <code>false</code> otherwise.
	 */
	boolean isBox() {
		return getWall(WEST).isBuilt()
				&& getWall(NORTH).isBuilt()
				&& getWall(EAST).isBuilt()
				&& getWall(SOUTH).isBuilt();
	}

	/**
	 * Returns the id of the boxer.
	 *
	 * @return 0 if the box is not built, otherwise the boxer id.
	 */
	int getBoxerId() {
		return (boxer == null) ? 0 : boxer.getId();
	}

	/**
	 * Returns the boxer of this tile.
	 *
	 * @return the <code>player</code> if there is a complete box, 
	 * <code>null</code> otherwise
	 */
	PlayerModel getBoxer() {
		return boxer;
	}

	/**
	 * Sets the boxer of this tile.
	 *
	 * @param p	the builder
	 */
	void setBoxer(PlayerModel p) {
		boxer = p;
	}
	
	private TileModel getAdjacent(int direction) {
		/*
		 * FIXME: this method is never used
		 */
		if (!Direction.isDirection(direction)) {
			throw Direction.NOT_A_DIRECTION;
		}
		return adjTiles[direction];
	}
	
	@Override
	public String toString() {
		return "<TileModel: " + x + ", " + y + ">";
	}
}
