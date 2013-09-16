package deco2800.arcade.pacman;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PacChar extends Collideable{
	
	// Describes the current state of pacman- starts IDLE
	public enum PacState {
		IDLE, MOVING, DEAD
	}
	private PacState currentState;
	// Static variables for pulling sprites from sprite sheet
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 4;
	private int facing; // 1: Right, 2: Left
							// 3: Up, 4: Down

	// the distance pacman moves each frame
	private float moveDist;

	private Animation walkAnimation;
	private Texture walkSheet;
	private TextureRegion[] walkFrames;
	private TextureRegion currentFrame;
	// amount of time spent in this state of animation?
	float stateTime;
	
	public PacChar(List<Object> colList) {
		super(colList);
		//grabs file
		walkSheet = new Texture(Gdx.files.internal("pacmove.png"));
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
		facing = 2;
		//set initial position to be (x,y)
		x = 300;
		y = 300;
		width = walkFrames[1].getRegionWidth();
		height = walkFrames[1].getRegionHeight();
		moveDist = 1;
//		animation not necessary unless Pacman moving		
//		walkAnimation = new Animation(0.025f, walkFrames);
//		stateTime = 0f;	
	}
	
	/**
	 * Called everytime the main render method happens.
	 * Draws the Pacman
	 */
	public void render(SpriteBatch batch) {
		// checks if pacman is moving, and if so keeps him moving in that direction
		if (currentState.equals(PacState.MOVING)) {
    		if (facing == 1) {
    			x += moveDist;
    		} else if (facing == 2){
    			x -= moveDist;
    		} else if (facing == 3) {
    			y += moveDist;
    		} else if (facing == 4){ 
    			y -= moveDist;
    		} else {
    			currentState = PacState.IDLE;
    			x = 300;
    			y = 300;
    			facing =1;
    		}
    	}
		//draw pacman facing the appropriate direction
		batch.draw(walkFrames[facing * 2 - 1], x, y);
	}
	 
	public int getFacing() {
			return facing;
		}
	
	public void setFacing(int facing) {
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
		
}
