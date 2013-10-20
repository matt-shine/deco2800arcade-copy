package deco2800.arcade.pacman;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import deco2800.arcade.pacman.Ghost.GhostName;
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
	private int lives = 3;
	private static final int dotScore = 10;
	private static final int energizerScore = 50;
	private int ghostScore = 400;
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
			if (this.getClass() == Ghost.class){
				((Ghost)this).setPreviousTile(currentTile);
			}
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
	 * Returns the next tile in the given direction
	 * @param offset- the amount of tiles to offset from the currentTile
	 */
	protected Tile tileInDir(int offset, Dir dir){
		int x = gameMap.getTilePos(currentTile).getX();
		int y = gameMap.getTilePos(currentTile).getY();
		switch(dir) {
		case LEFT: x -= offset; break;
		case RIGHT: x += offset; break;
		case UP: y += offset; break;
		case DOWN: y -= offset; break;
		}
		return gameMap.getGrid()[x][y];
	}
	
	/**
	 * On movement, check if the Mover has 'eaten' a dot and update score accordingly.
	 * Later this can be modified to handle interactions with other tiles such as
	 * teleporters and fruit.
	 * @param tile
	 */
	public void checkTile(Tile tile){
		
		// Get dead ghosts back into the pen!
		if (this.getClass() == Ghost.class){	
			if (tileInDir(1, Dir.DOWN) == gameMap.getGhostDoors().get(0) &&
					((Ghost)this).getCurrentState() == GhostState.DEAD){
				GhostName name = ((Ghost)this).getGhostName();
				int num = 0;
				switch (name){
				case PINKY: num = 1; break;
				case INKY: num = 2; break;
				case CLYDE: num = 3; break;
				default: num = 0; break;
				}		
				this.currentTile = gameMap.getGhostStarts()[num];
				this.drawX = gameMap.getTileCoords(currentTile).getX();
				this.drawY = gameMap.getTileCoords(currentTile).getY();
				this.updatePosition();
				((Ghost)this).setCurrentState(GhostState.PENNED);
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
					// Set the scatter timer for seven seconds
					Timer.schedule(new Task() {
						public void run() {
							gameMap.setEnergized(false);
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

	public int getGhostScore() {
		return ghostScore;
	}

	public void setGhostScore(int ghostScore) {
		this.ghostScore = ghostScore;
	}
	
}
