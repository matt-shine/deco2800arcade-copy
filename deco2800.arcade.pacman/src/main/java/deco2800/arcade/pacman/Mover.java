package deco2800.arcade.pacman;

/**
 * An abstract class for objects that can move and collide *
 */
public abstract class Mover {

	//describes a direction- test will be removed later
	public enum Dir {
		LEFT, RIGHT, UP, DOWN, TEST
	}
		
	protected Dir facing; // 1: Right, 2: Left
						// 3: Up, 4: Down
	
	// the coordinates of the bottom left corner of the pacman/ghost (for
	// drawing)
	protected int drawX;
	protected int drawY;
	protected int midX; // where the middle pixel of the pacman/ghost is
	protected int midY;
	protected int height;
	protected int width;
	private String test = "";
	private int score;
	protected Tile currentTile; // current tile of the pacman/ghost
	protected GameMap gameMap;

	public Mover(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	public int getDrawX() {
		return drawX;
	}

	public void setDrawX(int x) {
		drawX = x;
	}

	public int getDrawY() {
		return drawY;
	}

	public void setDrawY(int y) {
		drawY = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Dir getFacing() {
		return facing;
	}
	
	public Tile getTile() {
		return currentTile;
	}
	
	/**
	 * Updates the middle coordinate of the mover and its tile. Also updates the
	 * tile's list of movers
	 */
	protected void updatePosition() {
		midX = drawX + width / 2;
		midY = drawY + height / 2;
		// remove mover from tile and add it to new one if it's changed
		Tile newTile = gameMap.findMoverTile(this);
		// Pacman has moved!
		if (!currentTile.equals(newTile)) {
			System.out.println("Current is " + currentTile + ", new is "
					+ newTile);
			currentTile.removeMover(this);
			currentTile = newTile;
			currentTile.addMover(this);
//			checkTile(currentTile);
		}
	}

	/**
	 * Returns the next tile in the direction pacman is facing.
	 * @param tile
	 * @return
	 */
	public Tile nextTile(Tile tile, int offset){
		int x = gameMap.getTilePos(tile).getX();
		int y = gameMap.getTilePos(tile).getY();
		Tile[][] grid = gameMap.getGrid();
		//System.out.println(x + ", " + y);
		switch(this.getFacing()) {
		case LEFT: x -= offset; break;
		case RIGHT: x += offset; break;
		case UP: y += offset; break;
		case DOWN: y -= offset; break;
		case TEST: break;
		}
		return grid[x][y];
	}
	
	
	/**
	 * On movement, check if the Mover has 'eaten' a dot and update score accordingly.
	 * Later this can be modified to handle interactions with other tiles such as
	 * teleporters and fruit.
	 * @param tile
	 */
	public void checkTile(Tile tile){
		if (tile.getClass() == DotTile.class) {
			if (!((DotTile) tile).isEaten()) {
				((DotTile) tile).eaten();
				if (((DotTile) tile).isEnergiser()) {
					this.setScore(this.getScore() + 50);
				} else {
					this.setScore(this.getScore() + 10);
				}
			}
		} 
//		System.out.println("score: " + this.getScore());
//		displayScore(this); // This is broken at the moment
	}

	/**
	 * Checks if the proposed movement will make pacman hit a wall
	 * Returns true if he can move and false if he can't
	 */
	public boolean checkNoWallCollision(Tile pTile) {
		int x = gameMap.getTilePos(pTile).getX();
		int y = gameMap.getTilePos(pTile).getY();
		Tile[][] grid = gameMap.getGrid();
		//System.out.println(x + ", " + y);
		switch(this.getFacing()) {
		case LEFT: x -= 1; break;
		case RIGHT: x += 1; break;
		case UP: y += 1; break;
		case DOWN: y -= 1; break;
		case TEST: break;
		}		
		if (!test.equals(this + " wants to move to " + grid[x][y] + 
				" allowed=" + (grid[x][y].getClass() != WallTile.class))) {
			test = this + " wants to move to " + grid[x][y] + 
					" allowed=" + (grid[x][y].getClass() != WallTile.class);
			System.out.println(test);
		}
		return grid[x][y].getClass() != WallTile.class;
	}
	/**
	 * Overrides toString() so that trying to print the list won't crash the
	 * program
	 */
	@Override
	public String toString() {
		return new String("Object of " + this.getClass() + " at (" + drawX
				+ "," + drawY + ")," + " in tile: " + currentTile + ", width="
				+ width + ", height=" + height);
	}

	public int getMidX() {
		return midX;
	}

	public int getMidY() {
		return midY;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
