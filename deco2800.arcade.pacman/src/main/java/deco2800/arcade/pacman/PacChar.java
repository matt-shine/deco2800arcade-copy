
package deco2800.arcade.pacman;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PacChar extends Mover{
	
	// Describes the current state of pacman- starts IDLE
	public enum PacState {
		IDLE, MOVING, DEAD
	}
			
	private PacState currentState;
	private static int widthVal = 26;
	private static int heightVal = 26;
	
	// the distance pacman moves each frame
	private float moveDist;
	private int spritePos;


	private Animation walkAnimation;
	
	public PacChar(GameMap gameMap) {
		super(gameMap);
		currentTile = gameMap.getPacStart();
		//set up pacman to be drawn in the right place- this is defintely right
		drawX = gameMap.getTileCoords(currentTile).getX() + 4;
		drawY = gameMap.getTileCoords(currentTile).getY() - 4;
		// initialise some variables
		currentState = PacState.IDLE;
		facing = Dir.LEFT;
		drawFacing = Dir.LEFT;
		width = widthVal;
		height = heightVal;
		updatePosition();
		moveDist = 1;
		currentTile.addMover(this);
		//System.out.println(this);
//		animation not necessary unless Pacman moving		
//		walkAnimation = new Animation(0.025f, pacmanFrames);
//		stateTime = 0f;	
	}
	
	/**
	 * Prepare to draw pacman
	 */
	public void prepareDraw() {
		
		// Update pacman's facing dir
		spritePos = 3;
		if (drawFacing == Dir.RIGHT) {
			spritePos = 1;
		} else if (drawFacing == Dir.UP) {
			spritePos = 5;
		} else if (drawFacing == Dir.DOWN){ 
			spritePos = 7;
		} else {
			drawFacing = Dir.LEFT;
		}
		
		
//		if (this.nextTile(this.getTile(), 1).getClass() == TeleportTile.class){
//			
//		}
//	
		// If pacman is able to turn, update drawFacing
		if (canTurn(this.getTile())) {
			drawFacing = facing;
			this.setCurrentState(PacState.MOVING);
		}
		
		if (!this.checkNoWallCollision(this.getTile())){
			this.setCurrentState(PacState.IDLE);
		}
		
		
		// checks if pacman is moving, and if so keeps him moving in that direction
		if (currentState == PacState.MOVING) {
			int corr = 2; // a correction factor to ensure pacman is properly centred.
			if (drawFacing == Dir.LEFT){
    			drawX -= moveDist;
    			drawY = gameMap.getTileCoords(currentTile).getY() - corr;
    		} else if (drawFacing == Dir.RIGHT) {
    			drawX += moveDist;
    			drawY = gameMap.getTileCoords(currentTile).getY() - corr;
    		} else if (drawFacing == Dir.UP) {
    			drawY += moveDist;
    			drawX = gameMap.getTileCoords(currentTile).getX() - corr;
    		} else if (drawFacing == Dir.DOWN){ 
    			drawY -= moveDist;
    			drawX = gameMap.getTileCoords(currentTile).getX() - corr;
    		} else {
    			currentState = PacState.IDLE;
    			facing = Dir.LEFT;
    		}
//			checkTile(this.nextTile(currentTile, 1));
			updatePosition();
    	} 
	}
	
	
	private void checkGhostCollision(Tile pTile) {	
		List<Mover> colList = pTile.getMovers();
		if (colList.size() > 1) {
			for (int i=0; i < colList.size(); i++) {
				if (colList.get(i).getClass() == Ghost.class) {
					System.out.println("Pacman hit a ghost!");
					//TODO some death thing
					this.setCurrentState(PacState.DEAD);
				}
			}
		}
		
	}
	
	public void setFacing(Dir facing) {
		this.facing = facing;
	}
	
	public void setdrawFacing(Dir facing) {
		this.drawFacing = facing;
	}
	
	public PacState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(PacState currentState) {
		this.currentState = currentState;
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
	
	public String toString() {
		return "Pacman at (" + midX + ", " + midY + ") drawn at {" + drawX + 
				", " + drawY + "}, " + currentState + " in " + currentTile;
	}
		
}

