
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
		
		
//		if (this.nextTile(this.getTile(), 1).getClass() == TeleportTile.class){
//			
//		}
//	
		// check collision
		if (checkNoWallCollision(this.getTile())) {
			this.setCurrentState(PacState.MOVING);
		} else {
			this.setCurrentState(PacState.IDLE);
			// stops pacman changing facing if he can't move in that direction
			this.setFacing(facing);
		}
		
		// checks if pacman is moving, and if so keeps him moving in that direction
		if (currentState == PacState.MOVING) {
			if (facing == Dir.LEFT){
    			drawX -= moveDist;
    			drawY = gameMap.getTileCoords(currentTile).getY() - 2;
    		} else if (facing == Dir.RIGHT) {
    			drawX += moveDist;
    			drawY = gameMap.getTileCoords(currentTile).getY() - 2;
    		} else if (facing == Dir.UP) {
    			drawY += moveDist;
    			drawX = gameMap.getTileCoords(currentTile).getX() - 2;
    		} else if (facing == Dir.DOWN){ 
    			drawY -= moveDist;
    			drawX = gameMap.getTileCoords(currentTile).getX() - 2;
    		} else {
    			currentState = PacState.IDLE;
    			facing = Dir.LEFT;
    		}
//			checkTile(this.nextTile(currentTile, 1));
			updatePosition();
    	} 
	}
	 
	
	public void setFacing(Dir facing) {
		this.facing = facing;
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

