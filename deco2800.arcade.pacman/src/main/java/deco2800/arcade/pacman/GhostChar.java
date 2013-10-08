package deco2800.arcade.pacman;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import deco2800.arcade.pacman.PacChar.PacState;

public class GhostChar extends Collideable{

	

	private Tile startTile;
	private int ghostnum;
	
	public enum GhostState {
		IDLE, CHASE, SCATTER, FRIGHT, DEAD
	}
	private GhostState currentState;
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 4;
	private int facing; // 1: Right, 2: Left
							// 3: Up, 4: Down

	// the distance ghost moves each frame
	private float moveDist;
	private Animation walkAnimation;
	private Texture walkSheet;
	private TextureRegion[] walkFrames;
	private TextureRegion currentFrame;
	// amount of time spent in this state of animation?
	float stateTime;
	
	public GhostChar(List<Collideable> colList) {
		super(colList);
		walkSheet = new Texture(Gdx.files.internal("redghostmove.png"));
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
		currentState = GhostState.IDLE;
		facing = 2;
		//set initial position to be (x,y)
		x = 500;
		y = 500;
		width = walkFrames[1].getRegionWidth();
		height = walkFrames[1].getRegionHeight();
		moveDist = 1;
//		animation not necessary unless Ghost moving		
//		walkAnimation = new Animation(0.025f, walkFrames);
//		stateTime = 0f;	
	}
	
	public void render(SpriteBatch batch) {
		// checks if ghost is chasing, and if so keeps him moving in that direction
		if (currentState.equals(GhostState.CHASE)) {
			// need algorithm to establish direction
    		move();
    	}
		//draw ghost facing the appropriate direction
		batch.draw(walkFrames[facing * 2 - 1], x, y);
	}
	
	private void move() {
		if (facing == 1) {
			x += moveDist;
		} else if (facing == 2){
			x -= moveDist;
		} else if (facing == 3) {
			y += moveDist;
		} else if (facing == 4){ 
			y -= moveDist;
		} else {
			currentState = GhostState.IDLE;
			x = 500;
			y = 500;
			facing =1;
		}
	}
	 
	public int getFacing() {
			return facing;
		}
	
	public void setFacing(int facing) {
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
