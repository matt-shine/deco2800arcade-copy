package deco2800.arcade.pacman;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.lwjgl.util.Point;
import static java.lang.Math.*; 

import deco2800.arcade.model.Player;
import deco2800.arcade.pacman.PacChar.PacState;


public class Ghost extends Mover {
	
	public enum GhostState {
		CHASE, SCATTER, FRIGHT, DEAD
	}
	
	public enum GhostName {
		BLINKY, PINKY, INKY, CLYDE
	}
	private GhostName ghost;
	private GhostState currentState;
	
	private static int widthVal = 26;
	private static int heightVal = 26;
	
	// the distance ghost moves each frame
	private PacChar player;
	private float moveDist;
	private Tile targetTile;
	private Animation walkAnimation;
	private int spritePos;
	
	
	public Ghost(GameMap gameMap, GhostName ghost, PacChar player) {
		super(gameMap);
		this.player = player;
		this.ghost = ghost;
		currentTile = gameMap.getFruitLeft(); // CHANGE TO appropriate ghost start
		//set up pacman to be drawn in the right place- this is defintely right
		drawX = gameMap.getTileCoords(currentTile).getX() + 4;
		drawY = gameMap.getTileCoords(currentTile).getY() - 4;
		
		String file = "";
		switch (ghost) {
		case BLINKY : file = "redghostmove.png"; break;
		case PINKY : file = "pinkghostmove.png"; break;
		case INKY : file = "tealghostmove.png"; break;
		case CLYDE : file = "orangeghostmove.png"; break;
		}
		
		
		// initialise some variables
		currentState = GhostState.CHASE;
		facing = Dir.LEFT;
		width = widthVal;
		height = heightVal;
		updatePosition();
		moveDist = 1; //made it so he can't move at the moment, because he
						// currently goes through walls and crashes it.
		currentTile.addMover(this);
		//System.out.println(this);
//		animation not necessary unless Pacman moving		
//		walkAnimation = new Animation(0.025f, pacmanFrames);
//		stateTime = 0f;	
	}
	
	/**
	 * Prepares to draw a Ghost
	 */
	public void prepareDraw() {		
		spritePos = 3;
		if (facing == Dir.RIGHT) {
			spritePos = 1;
		} else if (facing == Dir.UP) {
			spritePos = 5;
		} else if (facing == Dir.DOWN){ 
			spritePos = 7;
		} else {
			facing = Dir.LEFT;
		}
		// checks if pacman is moving, and if so keeps him moving in that direction
		if (currentState == GhostState.CHASE) {
			if (facing == Dir.LEFT){
    			drawX -= moveDist;
    		} else if (facing == Dir.RIGHT) {
    			drawX += moveDist;
    		} else if (facing == Dir.UP) {
    			drawY += moveDist;
    		} else if (facing == Dir.DOWN){ 
    			drawY -= moveDist;
    		} else {
    			currentState = GhostState.SCATTER;
    			facing = Dir.LEFT;
    		}			
			updatePosition();			
    	} 		
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
	
	public String toString() {
		return "Ghost at (" + midX + ", " + midY + ") drawn at {" + drawX + 
				", " + drawY + "}, " + currentState + " in " + currentTile;
	}
		
	/**returns the target tile of the ghost.
	 * so far only does blinky and pinky
	 * 
	 * @return
	 */
	public Tile getTargetTile() {
		if (ghost == GhostName.BLINKY) {
			return player.getTile();
		}
		else if (ghost == GhostName.PINKY) {
			return player.nextTile(player.getTile(), 4);
		}
		else {
			return player.getTile();
		}
	}
	
	/**
	 * calculates the straight line distance between the current (start) tile and 
	 * the target tile.
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
		int distx = startx - targetx;
		int disty = starty - targety;
		dist = sqrt((distx*distx + disty*disty));
		return dist;
	}

	public void setTargetTile(Tile targetTile) {
		this.targetTile = targetTile;
	}
	
	/**
	 * returns a list of testTiles that can be walked into.
	 * returns them in the order of left, down, up, right
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
		
		if (checkNoWallCollision(upTile)) {
			testTiles.add(upTile);
		}
		if (checkNoWallCollision(leftTile)) {
			testTiles.add(leftTile);
		}
		if (checkNoWallCollision(downTile)) {
			testTiles.add(downTile);
		}
		if (checkNoWallCollision(rightTile)) {
			testTiles.add(rightTile);
		}
		
		
		return testTiles;
	}
	/**
	 * returns a list of distances in the same order of the testTiles
	 * @param testTiles
	 * @param current
	 * @return
	 */
	public List<Double> getDists (List<Tile> testTiles, Tile current) {
		
		Tile temp;
		double tempDist;
		List<Double> dists = new ArrayList<Double>();
		for (int i = 0; i < testTiles.size(); i++) {
			temp = testTiles.get(0);
			tempDist = calcDist(current, temp);
			dists.add(tempDist);
			
			
		}
		
		return dists;
	}
	
	/**
	 * Returns the direction that the nextTile is in, in relation to the current tile
	 * @param current
	 * @param nextTile
	 * @return
	 */
	public Dir getDirection(Tile current, Tile nextTile) {
		
		Point currentPoint = gameMap.getTilePos(current);
		Point nextPoint = gameMap.getTilePos(nextTile);
		
		int currentX = currentPoint.getX();
		int currentY = currentPoint.getY();
		int nextX = nextPoint.getX();
		int nextY = nextPoint.getY();
		
		if (nextX > currentX) {
			return Dir.RIGHT;
		}
		if (nextX < currentX) {
			return Dir.LEFT;
		}
		if (nextY > currentY) {
			return Dir.UP;
		}
		return Dir.DOWN;
	}
	

}
