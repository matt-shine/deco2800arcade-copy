package deco2800.arcade.pacman;

/**
 * An abstract class for objects that can move and collide *
 */
public abstract class Mover {

	// the coordinates of the bottom left corner of the pacman/ghost (for
	// drawing)
	protected int drawX;
	protected int drawY;
	protected int midX; // where the middle pixel of the pacman/ghost is
	protected int midY;
	protected int height;
	protected int width;
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

	public Tile getTile() {
		return currentTile;
	}

	/**
	 * Updates the middle coordinate of the mover and its tile. Also updates the
	 * tile's list of movers
	 */
	protected void updatePosition() {
		midX = drawX + width / 2;
		midY = drawY + width / 2;
		// remove mover from tile and add it to new one if it's changed
		Tile newTile = gameMap.findMoverTile(this);
		// Pacman has moved!
		if (!currentTile.equals(newTile)) {
			System.out.println("Current is " + currentTile + ", new is "
					+ newTile);
			currentTile.removeMover(this);
			currentTile = newTile;
			currentTile.addMover(this);
			// System.out.println(currentTile.getMovers());
			
			// Eat the dot! Will need to mvoe this to a different class alter.
			if (currentTile.getClass() == DotTile.class){
				((DotTile) currentTile).eaten();
			}
		}
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
}
