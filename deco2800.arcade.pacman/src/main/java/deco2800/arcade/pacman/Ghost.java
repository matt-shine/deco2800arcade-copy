package deco2800.arcade.pacman;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.Point;
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
	private float moveDist;
	private Tile targetTile;
	private Tile previousTile;
	// private Animation walkAnimation; this should move to PacView
	private int spritePos;

	public Ghost(GameMap gameMap, GhostName ghost, PacChar player) {
		super(gameMap);
		this.player = player;
		this.ghostName = ghost;
		currentTile = gameMap.getFruitLeft(); // CHANGE TO appropriate ghost
												// start
		// makes the previous tile the one to right, since he's facing left
		Point current = gameMap.getTilePos(currentTile);
		previousTile = gameMap.getGrid()[current.getX() + 1][current.getY()];
		// set up ghost to be drawn in the right place- this is defintely right
		drawX = gameMap.getTileCoords(currentTile).getX() + 8; // drawX % 16 was
																// 6, so make it
																// 8
		drawY = gameMap.getTileCoords(currentTile).getY() + 8; // was 0, so make
																// it 8

		facing = Dir.LEFT;
		// DEBUGGING PRINT
		System.out.println("drawX % 16 is: " + (drawX % 16)
				+ ", drawY % 16 is: " + (drawY % 16));

		// this section should be deleted once I check it works with the view,
		// or merged into it or something.
		String file = "";
		switch (ghost) {
		case BLINKY:
			file = "redghostmove.png";
			break;
		case PINKY:
			file = "pinkghostmove.png";
			break;
		case INKY:
			file = "tealghostmove.png";
			break;
		case CLYDE:
			file = "orangeghostmove.png";
			break;
		}

		// initialise some variables
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
		// if ((drawX % 16 == 6) && (drawY % 16 == 0)) {
		// facing = getDirection(currentTile);
		// targetTile = getTargetTile();
		// } else{
		// //STOP MOVEMENT
		// }
		if (facing == Dir.RIGHT) {
			spritePos = 1;
		} else if (facing == Dir.UP) {
			spritePos = 5;
		} else if (facing == Dir.DOWN) {
			spritePos = 7;
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

	// private void ghost_move() {
	// if ((drawX % 16 == 8) && (drawY % 16 == 8)) {
	// facing = getDirection();
	// updateTargetTile();
	// } else {
	// // Don't move!!
	// }
	// }

	public String toString() {
		return ghostName + " at (" + midX + ", " + midY + ") drawn at {"
				+ drawX + ", " + drawY + "}, " + currentState + " in "
				+ currentTile;
	}

	/**
	 * Updates the target tile for the ghost so far only does blinky and pinky
	 */

	public void updateTargetTile() {
		if (ghostName == GhostName.BLINKY) {
			targetTile = player.getTile();
			System.out.println("player: " + player);
			System.out.println("<!> " + this.ghostName + " Target is: " + player.getTile());
		}
		// else if (ghostName == GhostName.PINKY) {
		// //need to check this tile exists, otherwise crashes
		// targetTile = player.nextTile(player.getTile(), 4);
		// }
		else {
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
//		int distx = startx - targetx;
//		int disty = starty - targety;
		int distx = targetx - startx;
		int disty = targety - starty;
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
		System.out.println("Get me some test tiles STAT!");
		Point currentPoint = gameMap.getTilePos(current);
		List<Tile> testTiles = new ArrayList<Tile>();
		int currentX = currentPoint.getX();
		int currentY = currentPoint.getY();
		
		System.out.println("In getTestTiles, about to check wall coll");
		int upY = currentY - 1;
		int leftX = currentX - 1;
		int downY = currentY + 1;
		int rightX = currentX + 1;

		Tile upTile = gameMap.getGrid()[currentX][upY];
		Tile leftTile = gameMap.getGrid()[leftX][currentY];
		Tile downTile = gameMap.getGrid()[currentX][downY];
		Tile rightTile = gameMap.getGrid()[rightX][currentY];

//		if ((checkNoWallCollision(upTile) && 
//				!upTile.equals(previousTile))) {
//				testTiles.add(upTile);
//		} else if (checkNoWallCollision(leftTile) && 
//				!leftTile.equals(previousTile)) {
//				testTiles.add(leftTile);
//		} else if (checkNoWallCollision(downTile) && 
//				!downTile.equals(previousTile)) {
//				testTiles.add(downTile);
//		} else if (checkNoWallCollision(rightTile)) {
//			if (!rightTile.equals(previousTile)) {
//				testTiles.add(rightTile);
//			}
//		} else {
//			//Something's not right here, no where to move...
//			System.out.println("<<GetTestTiles>> no valid directions!");
//			this.setCurrentState(GhostState.DEAD);
//		}
		
		if (this.nextTile(this.currentTile, 1, Dir.UP).getClass() != WallTile.class &&
				!this.nextTile(this.currentTile, 1, Dir.UP).equals(previousTile)){
			testTiles.add(upTile);
		} else if (this.nextTile(this.currentTile, 1, Dir.DOWN).getClass() != WallTile.class &&
				!this.nextTile(this.currentTile, 1, Dir.DOWN).equals(previousTile)){
			testTiles.add(downTile);
		} else if (this.nextTile(this.currentTile, 1, Dir.LEFT).getClass() != WallTile.class &&
				!this.nextTile(this.currentTile, 1, Dir.LEFT).equals(previousTile)){
			testTiles.add(leftTile);
		} else if (this.nextTile(this.currentTile, 1, Dir.RIGHT).getClass() != WallTile.class &&
				!this.nextTile(this.currentTile, 1, Dir.RIGHT).equals(previousTile)){
			testTiles.add(rightTile);
		}
		
		if (testTiles.size() == 0) {
			System.out.println("BAD! testTiles is empty!");
		} else {
			System.out.println("All is well! testTiles not empty!");
		}
		return testTiles;
	}

	/**
	 * returns a list of distances in the same order of the testTiles
	 * 
	 * @param testTiles
	 * @param current
	 * @return
	 */
	public List<Double> getDists(List<Tile> testTiles, Tile current) {
		double tempDist;
		List<Double> dists = new ArrayList<Double>();

		for (Tile temp : testTiles) {
			tempDist = calcDist(current, temp);
			dists.add(tempDist);
		}

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
			System.out.println("\t next tile is RIGHT");
			return Dir.RIGHT;
		} else if (nextX < currentX) {
			System.out.println("\t next tile is LEFT");
			return Dir.LEFT;
		} else if (nextY > currentY) {
			System.out.println("\t next tile is UP");
			return Dir.UP;
		} else {
			System.out.println("\t next tile is DOWN");
			return Dir.DOWN;
		}
	}

	/**
	 * Returns the next tile. uses other methods to determine the tile.
	 */

	public Tile getNextTile() {
		List<Tile> testTiles = getTestTiles(currentTile);
		List<Double> dists = getDists(testTiles, currentTile);
		// int Tilenum = -1;
		int tileNum = 0;
		double dist = 9999;
		double temp;

		if (testTiles.size() == 0) {
			System.out.println("SHIT: testTiles is empty");
		} else {
			System.out.println("OKAY: testTiles NOT empty...");
		}

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
		System.out.println(this + " drawX % 16: " + drawX % 16
				+ "drawY % 16: " + drawY % 16);
		if ((drawX % 16 == 8) && (drawY % 16 == 8)) {
			facing = getDirection();
			updateTargetTile();
		} else {
//			System.out.println("Well I'm a ghost and I'm floaatingg freeeee");
//			facing = getDirection();
			// // targetTile = getTargetTile();
			// updateTargetTile();
			// System.out.println("OH NO! drawX % 16: " + drawX % 16 +
			// "drawY % 16: " + drawY % 16);
			// // Don't move!!
		}
		//
	}

	public int getSpritePos() {
		return spritePos;
	}

	public Dir getFacing() {
		return facing;
	}

	public void setFacing(Dir facing) {
		this.facing = facing;
	}

	public GhostState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(GhostState currentState) {
		this.currentState = currentState;
	}

	public float getMoveDist() {
		return moveDist;
	}

	public void setMoveDist(float moveDist) {
		this.moveDist = moveDist;
	}

}
