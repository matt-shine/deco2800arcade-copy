package deco2800.arcade.pacman;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Point;

import deco2800.arcade.pacman.Mover.Dir;

public final class Ghost extends Mover {

	public enum GhostState {
		CHASE, SCATTER, FRIGHT, DEAD
	}

	public enum GhostName {
		BLINKY, PINKY, INKY, CLYDE
	}

	private GhostName ghostName;
	private GhostState currentState;

	private static int widthVal = 26;
	private static int heightVal = 26;

	// the distance ghost moves each frame
	private PacChar player;
	private Tile targetTile;
	private Tile previousTile;
	// private Animation walkAnimation; this should move to PacView

	public Ghost(GameMap gameMap, GhostName ghostName, PacChar player) {
		super(gameMap);
		this.player = player;
		this.ghostName = ghostName;
		int num;
		switch (ghostName) {
		case PINKY: num = 1; break;
		case INKY: num = 2; break;
		case CLYDE: num = 3; break;
		default: num = 0; break;
		}

		facing = Dir.LEFT;
//		currentTile = gameMap.getGhostStarts()[num];  //This is the actual starting positon in pen
		currentTile = gameMap.getFruitRight(); // For testing purposes
		// makes the previous tile the one to right, since he's facing left
		Point current = gameMap.getTilePos(currentTile);
		previousTile = gameMap.getGrid()[current.getX() + 1][current.getY()];
		drawX = gameMap.getTileCoords(currentTile).getX(); 
		drawY = gameMap.getTileCoords(currentTile).getY(); 
		// DEBUGGING PRINT
//		System.out.println("drawX % 16 is: " + (drawX % 16)
//				+ ", drawY % 16 is: " + (drawY % 16));		
		currentState = GhostState.CHASE;
		width = widthVal;
		height = heightVal;
		currentTile.addMover(this);
		updatePosition();
		moveDist = 1;
		// System.out.println(this);
		// animation not necessary unless Pacman moving
		// walkAnimation = new Animation(0.025f, pacmanFrames);
		// stateTime = 0f;
	}

	/**
	 * Prepares to draw a Ghost
	 */
	public void prepareDraw() {		
		updateTargetTile();
		facing = getNextTileDirection();
		spritePos = 3;
		if (facing == Dir.RIGHT) {
			spritePos = 1;
		} else if (facing == Dir.UP) {
			spritePos = 5;
		} else if (facing == Dir.DOWN) {
			spritePos = 7;
		} 
		
		// Check ghost wall collision
		if (!checkNoWallCollision()){
			facing = Dir.LEFT; // why this?
		}
		
		// Check whether energised
		if (gameMap.isEnergized() && currentState == GhostState.CHASE){
			System.out.println("Ghosts scatter!");
			currentState = GhostState.SCATTER;
			// TODO: more stuff
		} else if (!gameMap.isEnergized() && currentState == GhostState.SCATTER) {
			System.out.println("Ghosts back to the chase!");
			currentState = GhostState.CHASE;
		}
		
		
		// checks if ghost is moving, and if so keeps him moving in that
		// direction
		if (currentState == GhostState.CHASE) {
			if (facing == Dir.LEFT) {
				drawX -= moveDist;
			} else if (facing == Dir.RIGHT) {
				drawX += moveDist;
			} else if (facing == Dir.UP) {
				drawY += moveDist;
			} else if (facing == Dir.DOWN) {
				drawY -= moveDist;
			} 
			updatePosition();
		}
	}

	/** Updates the target tile for the ghost. So far only does Blinky 
	 * and Pinky's targeting schemes */
	private void updateTargetTile() {
		if (ghostName == GhostName.BLINKY) {
			targetTile = player.getCurTile();
		} else if (ghostName == GhostName.PINKY) {
			targetTile = player.nextTile(player.getCurTile(), 4);
		} else {
			targetTile = player.getCurTile();
		}
	}

	/**
	 * Returns the direction that the nextTile is in, in relation to the current
	 * tile
	 */
	private Dir getNextTileDirection() {
		Point currentPoint = gameMap.getTilePos(currentTile);
		Point nextPoint = gameMap.getTilePos(getNextTile());
		int currentX = currentPoint.getX();
		int currentY = currentPoint.getY();
		int nextX = nextPoint.getX();
		int nextY = nextPoint.getY();
		
		if (nextX > currentX) {
			return Dir.RIGHT;
		} else if (nextX < currentX) {
			return Dir.LEFT;
		} else if (nextY > currentY) {
			return Dir.UP;
		} else {
			return Dir.DOWN;
		}
	}
	
	/** Returns the tile adjacent to the ghost which is closest to the target tile */
	private Tile getNextTile() {
		List<Tile> testTiles = getTestTiles();
		double dist = 9999;
		double temp;
		int tileNum = 0; //number of nextTile
		for (int i = 0; i < testTiles.size(); i++) {
			temp = calcDist(testTiles.get(i));
			if (temp < dist) {
				dist = temp;
				tileNum = i;
			}
		}
		return testTiles.get(tileNum);
	}
	
	/** Returns a list of testTiles that can be walked into. returns them in the
	 * order left, right, up, down (same as enum) */
	private List<Tile> getTestTiles() {
		List<Tile> testTiles = new ArrayList<Tile>();		
		for (Dir dir: Dir.values()) {
			Dir opposite = dir; //won't ever stay like this but needed for initialisation
			switch(dir) {
			case UP: opposite = Dir.DOWN; break;
			case DOWN: opposite = Dir.UP; break;
			case LEFT: opposite = Dir.RIGHT; break;
			case RIGHT: opposite = Dir.LEFT; break;
			}
			Tile next = nextTile(1, dir);
			if (next.getClass() != WallTile.class && facing != opposite) {
				testTiles.add(next);
			}
		}
		return testTiles;
	}	
	
	/**
	 * Returns the next tile in the given direction
	 * @param offset- the amount of tiles to offset from the currentTile
	 */
	private Tile nextTile(int offset, Dir dir){
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
	 * Calculates the Euclidean distance between a tile
	 * and the target tile.
	 */
	public double calcDist(Tile start) {
		Point startPoint = gameMap.getTilePos(start);
		Point targetPoint = gameMap.getTilePos(targetTile);
		int distx = targetPoint.getX() - startPoint.getX();
		int disty = targetPoint.getY() - startPoint.getY();
		return Math.sqrt(Math.pow(distx, 2) + Math.pow(disty, 2));
	}	

	public GhostState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(GhostState currentState) {
		this.currentState = currentState;
	}
	
	public String toString() {
		return ghostName + " at (" + midX + ", " + midY + ") drawn at {"
				+ drawX + ", " + drawY + "}, " + currentState + " in "
				+ currentTile;
	}

}
