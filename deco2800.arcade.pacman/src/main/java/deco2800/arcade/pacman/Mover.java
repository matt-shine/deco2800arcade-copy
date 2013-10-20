package deco2800.arcade.pacman;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import deco2800.arcade.pacman.Ghost.GhostState;

/**
 * An abstract class for objects that can move and collide *
 */
public abstract class Mover {

	//describes a direction
	public enum Dir {
		LEFT, RIGHT, UP, DOWN
	}
	
	protected Dir facing;
	protected Dir drawFacing;
	
	// the coordinates of the bottom left corner of the pacman/ghost (for
	// drawing)
	protected int drawX;
	protected int drawY;
	protected int midX; // where the middle pixel of the pacman/ghost is
	protected int midY;
	protected int height;
	protected int width;
	private int score;
	private int lives;
	private static final int dotScore = 10;
	private static final int energizerScore = 50;
	protected Tile currentTile; // current tile of the pacman/ghost
	protected GameMap gameMap;
	protected float moveDist; //the distance moved each frame
	protected int spritePos; //location of current sprite in sprite array

	public Mover(GameMap gameMap) {
		this.gameMap = gameMap;
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
		if (!currentTile.equals(newTile)) {
			currentTile.removeMover(this);
			currentTile = newTile;			
			currentTile.addMover(this);
			checkTile(currentTile);
		}
	}

	/**
	 * Checks if the proposed movement will make the mover hit a wall
	 * Returns true if it can move and false if it can't
	 */
	public boolean checkNoWallCollision() {
		return nextTile(currentTile, 1).getClass() != WallTile.class;
	}
	
	/**
	 * Returns the next tile in the direction Mover is facing.
	 * @param tile, offset
	 * @return Tile
	 */
	public Tile nextTile(Tile tile, int offset){
		int x = gameMap.getTilePos(tile).getX();
		int y = gameMap.getTilePos(tile).getY();
		Tile[][] grid = gameMap.getGrid();
		if (this.getClass() == PacChar.class){
			switch(drawFacing) {
			case LEFT: x -= offset; break;
			case RIGHT: x += offset; break;
			case UP: y += offset; break;
			case DOWN: y -= offset; break;
			}
		} else {
			switch(facing) {
			case LEFT: x -= offset; break;
			case RIGHT: x += offset; break;
			case UP: y += offset; break;
			case DOWN: y -= offset; break;
			}
		}
		return grid[x][y];
	}
	
	/**
	 * Checks whether the mover can turn
	 */
	public boolean canTurn(){
		int x = gameMap.getTilePos(currentTile).getX();
		int y = gameMap.getTilePos(currentTile).getY();
		Tile[][] grid = gameMap.getGrid();
		switch(facing) {
		case LEFT: x -= 1; break;
		case RIGHT: x += 1; break;
		case UP: y += 1; break;
		case DOWN: y -= 1; break;
		}
		return !(grid[x][y].getClass() == WallTile.class);
	}
	
	/**
	 * On movement, check if the Mover has 'eaten' a dot and update score accordingly.
	 * Later this can be modified to handle interactions with other tiles such as
	 * teleporters and fruit.
	 * @param tile
	 */
	public void checkTile(Tile tile){
		
		if (this.getClass() == Ghost.class){
			if (tile == gameMap.getGhostDoors().get(0) &&
					((Ghost)this).getCurrentState() == GhostState.DEAD){
				// Move ghost and revive!
				((Ghost)this).setCurrentState(GhostState.CHASE);
				currentTile = gameMap.getGhostStarts()[0];
			}
		}
		
		// handle teleporters
		for (Tile t: gameMap.getAfterTeleports()) {
			if (nextTile(currentTile, 1).equals(t)) {
				drawX = ((TeleportTile) currentTile).getTargetX();
				drawY = ((TeleportTile) currentTile).getTargetY();
			}
		}		
		// Only Pac man can eat dots!
		if (this.getClass() != PacChar.class) {return;} 
		if (tile.getClass() == DotTile.class) {
			if (!((DotTile) tile).isEaten()) {
				((DotTile) tile).dotEaten();
				gameMap.setDotsEaten(gameMap.getDotsEaten() + 1);
				if (((DotTile) tile).isEnergiser()) {
					this.setScore(this.getScore() + energizerScore);
					gameMap.setEnergized(true);
					// Set the scatter timer
					System.out.println("Timer start!");
					Timer.schedule(new Task() {
						public void run() {
							gameMap.setEnergized(false);
							System.out.println("Time's up!");
						}
					}, 7);
				} else {
					this.setScore(this.getScore() + dotScore);
				}
			}
		} 
	}

	/**
	 * Checks if the proposed movement will make the mover hit a wall
	 * Returns true if it can move and false if it can't
	 */
	public boolean checkNoWallCollision(Tile pTile) {
		return !(nextTile(pTile, 1).getClass() == WallTile.class);
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
	
	public float getMoveDist() {
		return moveDist;
	}

	public void setMoveDist(float moveDist) {
		this.moveDist = moveDist;
	}

	public int getSpritePos() {
		return spritePos;
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
	
	public Dir getDrawFacing() {
		return drawFacing;
	}
	
	public Tile getCurTile() {
		return currentTile;
	}


	public int getLives() {
		return lives;
	}


	public void setLives(int lives) {
		this.lives = lives;
	}
	
}
