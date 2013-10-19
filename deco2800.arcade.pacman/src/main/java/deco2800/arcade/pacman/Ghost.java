package deco2800.arcade.pacman;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Point;

import deco2800.arcade.pacman.PacChar.PacState;
import static java.lang.Math.*;

public class Ghost extends Mover {

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

	public Ghost(GameMap gameMap, GhostName ghost, PacChar player) {
		super(gameMap);
		this.player = player;
		this.ghostName = ghost;
		int num;
		switch (ghost) {
		case PINKY: num = 1; break;
		case INKY: num = 2; break;
		case CLYDE: num = 3; break;
		default: num = 0; break;
		}
//		currentTile = gameMap.getGhostStarts()[num];  //This is the actual starting positon in pen
		currentTile = gameMap.getFruitRight(); // For testing purposes
		// makes the previous tile the one to right, since he's facing left
		Point current = gameMap.getTilePos(currentTile);
		previousTile = gameMap.getGrid()[current.getX() + 1][current.getY()];
		drawX = gameMap.getTileCoords(currentTile).getX(); 
		drawY = gameMap.getTileCoords(currentTile).getY(); 

		facing = Dir.LEFT;
		// DEBUGGING PRINT
//		System.out.println("drawX % 16 is: " + (drawX % 16)
//				+ ", drawY % 16 is: " + (drawY % 16));

		
		currentState = GhostState.CHASE;

		width = widthVal;
		height = heightVal;
		updatePosition();
		moveDist = 1;
		currentTile.addMover(this);
		// System.out.println(this);
		// animation not necessary unless Pacman moving
		// walkAnimation = new Animation(0.025f, pacmanFrames);
		// stateTime = 0f;
	}

	/**
	 * Prepares to draw a Ghost
	 */
	public void prepareDraw() {
		spritePos = 3;
		ghost_move();
		if (facing == Dir.RIGHT) {
			spritePos = 1;
		} else if (facing == Dir.UP) {
			spritePos = 5;
		} else if (facing == Dir.DOWN) {
			spritePos = 7;
		} 
		
		// Check ghost wall collision
		if (!this.checkNoWallCollision(this.getTile())){
//			currentState = GhostState.SCATTER;
			facing = Dir.LEFT;
		}
		
		// checks if ghost is moving, and if so keeps him moving in that
		// direction
		int corr = 0;
		if (currentState == GhostState.CHASE) {
			if (facing == Dir.LEFT) {
				drawX -= moveDist;
//				drawY = gameMap.getTileCoords(currentTile).getY() - corr;
			} else if (facing == Dir.RIGHT) {
				drawX += moveDist;
//				drawY = gameMap.getTileCoords(currentTile).getY() - corr;
			} else if (facing == Dir.UP) {
				drawY += moveDist;
//				drawY = gameMap.getTileCoords(currentTile).getX() - corr;
			} else if (facing == Dir.DOWN) {
				drawY -= moveDist;
//				drawY = gameMap.getTileCoords(currentTile).getX() - corr;
			} else {
				currentState = GhostState.SCATTER;
				facing = Dir.LEFT;
			}
			updatePosition();
		}
	}

	public String toString() {
		return ghostName + " at (" + midX + ", " + midY + ") drawn at {"
				+ drawX + ", " + drawY + "}, " + currentState + " in "
				+ currentTile;
	}

	/**
	 * Updates the target tile for the ghost so far only does blinky and pinky
	 */

	public void updateTargetTile() {
		System.out.println("<!> " + this.ghostName + " Target is: "
				+ player.getTile());
		if (ghostName == GhostName.BLINKY) {
			targetTile = player.getTile();
		} else if (ghostName == GhostName.PINKY) {
			try {
				targetTile = player.nextTile(player.getTile(), 4);
			} catch (ArrayIndexOutOfBoundsException e) {
				// Trying to finda tile outside the map.
				targetTile = player.nextTile(player.getTile(), 1);
			}

		} else {
			targetTile = player.getTile();
		}
	}

	/**
	 * calculates the straight line distance between the current (start) tile
	 * and the target tile.
	 * 
	 * @param start
	 * @param target
	 * @return
	 */
	public double calcDist(Tile start, Tile target) {
		Point startPoint = gameMap.getTilePos(start);
		Point targetPoint = gameMap.getTilePos(target);
		int startx = startPoint.getX();
		int starty = startPoint.getY();
		int targetx = targetPoint.getX();
		int targety = targetPoint.getY();
		double dist;
		int distx = targetx - startx;
		int disty = targety - starty;
		System.out.println("distx: " + distx + " disty: " + disty);
		dist = sqrt((distx * distx + disty * disty));
		return dist;
	}

	public void setTargetTile(Tile targetTile) {
		this.targetTile = targetTile;
	}

	/**
	 * returns a list of testTiles that can be walked into. returns them in the
	 * order of left, down, up, right
	 * 
	 * @param current
	 * @return
	 */
	public List<Tile> getTestTiles(Tile current) {
		Point currentPoint = gameMap.getTilePos(current);
		List<Tile> testTiles = new ArrayList<Tile>();
		int currentX = currentPoint.getX();
		int currentY = currentPoint.getY();
		
		int upY = currentY + 1;
		int leftX = currentX - 1;
		int downY = currentY - 1;
		int rightX = currentX + 1;

		Tile upTile = gameMap.getGrid()[currentX][upY];
		Tile leftTile = gameMap.getGrid()[leftX][currentY];
		Tile downTile = gameMap.getGrid()[currentX][downY];
		Tile rightTile = gameMap.getGrid()[rightX][currentY];
		
		if (this.nextTile(this.currentTile, 1, Dir.UP).getClass() != WallTile.class &&
				!this.nextTile(this.currentTile, 1, Dir.UP).equals(previousTile)){
			testTiles.add(upTile);
		} if (this.nextTile(this.currentTile, 1, Dir.DOWN).getClass() != WallTile.class &&
				!this.nextTile(this.currentTile, 1, Dir.DOWN).equals(previousTile)){
			testTiles.add(downTile);
		} if (this.nextTile(this.currentTile, 1, Dir.LEFT).getClass() != WallTile.class &&
				!this.nextTile(this.currentTile, 1, Dir.LEFT).equals(previousTile)){
			testTiles.add(leftTile);
		} if (this.nextTile(this.currentTile, 1, Dir.RIGHT).getClass() != WallTile.class &&
				!this.nextTile(this.currentTile, 1, Dir.RIGHT).equals(previousTile)){
			testTiles.add(rightTile);
		}
		
		System.out.println("List of test tiles: " + testTiles);
		return testTiles;
	}

	/**
	 * returns a list of distances in the same order of the testTiles
	 * 
	 * @param testTiles
	 * @param current
	 * @return
	 */
	public List<Double> getDists(List<Tile> testTiles, Tile target) {
		double tempDist;
		List<Double> dists = new ArrayList<Double>();

		for (Tile tTile : testTiles) {
			System.out.println("target: " + target + "tTile: " + tTile );
			tempDist = calcDist(target, tTile);
			dists.add(tempDist);
		}
		System.out.println(dists);
		return dists;
	}

	/**
	 * Returns the direction that the nextTile is in, in relation to the current
	 * tile
	 * 
	 * @param current
	 * @param nextTile
	 * @return
	 */
	public Dir getDirection() {
		Point currentPoint = gameMap.getTilePos(currentTile);
		Point nextPoint = gameMap.getTilePos(getNextTile());
		int currentX = currentPoint.getX();
		int currentY = currentPoint.getY();
		int nextX = nextPoint.getX();
		int nextY = nextPoint.getY();
		
		System.out.println("<<getDirection>> current: [" + currentX +
				 "," + currentY + "]  next: [" + nextX + "," + nextY + "]");
		if (nextX > currentX) {
			System.out.println("    next tile is RIGHT");
			return Dir.RIGHT;
		} else if (nextX < currentX) {
			System.out.println("    next tile is LEFT");
			return Dir.LEFT;
		} else if (nextY > currentY) {
			System.out.println("    next tile is UP");
			return Dir.UP;
		} else {
			System.out.println("    next tile is DOWN");
			return Dir.DOWN;
		}
	}

	/**
	 * Returns the next immediate tile for the Ghost to move to.
	 */

	public Tile getNextTile() {
		List<Tile> testTiles = getTestTiles(currentTile);
		List<Double> dists = getDists(testTiles, this.targetTile);
		System.out.println(dists);
		int tileNum = 0;
		double dist = 9999;
		double temp;

		for (int i = 0; i < dists.size(); i++) {
			temp = dists.get(i);
			if (temp < dist) {
				dist = temp;
				tileNum = i;
			}
		}
		return testTiles.get(tileNum);
	}

	private void ghost_move() {
		updateTargetTile();
		facing = getDirection();
	}

	public GhostState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(GhostState currentState) {
		this.currentState = currentState;
	}

}
