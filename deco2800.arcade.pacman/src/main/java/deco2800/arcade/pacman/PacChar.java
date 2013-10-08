
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
	//describes a direction- test will be removed later
	public enum Dir {
		LEFT, RIGHT, UP, DOWN, TEST
	}
		
	private PacState currentState;
	// Static variables for pulling sprites from sprite sheet
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 4;
	private Dir facing; // 1: Right, 2: Left
							// 3: Up, 4: Down
	
	// the distance pacman moves each frame
	private float moveDist;

	private Animation walkAnimation;
	private Texture walkSheet;
	private TextureRegion[] walkFrames;
	private TextureRegion currentFrame;
	
	public PacChar(GameMap gameMap) {
		super(gameMap);
		currentTile = gameMap.getPacStart();
		//set up pacman to be drawn in the right place- this is defintely right
		drawX = gameMap.getTileCoords(currentTile).getX() + 4;
		drawY = gameMap.getTileCoords(currentTile).getY() - 4;
		//grabs file- should be pacMove2.png, pacTest marks the edges and middle pixel in red
		walkSheet = new Texture(Gdx.files.internal("pacTest.png"));
		// splits into columns and rows then puts them into one array in order
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
		walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
							/ FRAME_ROWS);
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		// initialise some variables
		currentState = PacState.IDLE;
		facing = Dir.LEFT;
		width = walkFrames[1].getRegionWidth() * 2;
		height = walkFrames[1].getRegionHeight() * 2;
		updatePosition();
		moveDist = 1;
		currentTile.addMover(this);
		//System.out.println(this);
//		animation not necessary unless Pacman moving		
//		walkAnimation = new Animation(0.025f, walkFrames);
//		stateTime = 0f;	
	}
	
	/**
	 * Called everytime the main render method happens.
	 * Draws the Pacman
	 */
	public void render(SpriteBatch batch) {
		
		int spritePos = 3;
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
		if (currentState == PacState.MOVING) {
			if (facing == Dir.LEFT){
    			drawX -= moveDist;
    		} else if (facing == Dir.RIGHT) {
    			drawX += moveDist;
    		} else if (facing == Dir.UP) {
    			drawY += moveDist;
    		} else if (facing == Dir.DOWN){ 
    			drawY -= moveDist;
    		} else {
    			currentState = PacState.IDLE;
    			facing = Dir.LEFT;
    		}			
			updatePosition();			
    	} 
		//draw pacman facing the appropriate direction
		batch.draw(walkFrames[spritePos], drawX, drawY, width, height);
	}
	 
	public Dir getFacing() {
			return facing;
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
	
	public String toString() {
		return "Pacman at (" + midX + ", " + midY + ") drawn at {" + drawX + 
				", " + drawY + "}, " + currentState + " in " + currentTile;
	}
		
}

