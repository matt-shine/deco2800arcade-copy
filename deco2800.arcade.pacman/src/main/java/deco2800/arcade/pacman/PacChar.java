package deco2800.arcade.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PacChar {
	
	// Describes the current state of pacman- starts IDLE
	private enum PacState {
		IDLE, WALKING, DEAD
	}
	private PacState currentState = PacState.IDLE;
	// Static variables for pulling sprites from sprite sheet
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 4;
	private int facing = 2; // 1: Right, 2: Left
							// 3: Up, 4: Down

	private Animation walkAnimation;
	private Texture walkSheet;
	private TextureRegion[] walkFrames;
	private TextureRegion currentFrame;
	// amount of time spent in this state of animation?
	float stateTime;
	
	public PacChar() {
		super();
		initialisePacman();		
	}
	
	/**
	 * Sorts out initial stuff for the pacman, including getting the sprites
	 * arranged correctly.
	 */
	private void initialisePacman() {		
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
//		animation not necessary unless Pacman moving		
//		walkAnimation = new Animation(0.025f, walkFrames);
//		stateTime = 0f;
	}
	
	/**
	 * Called everytime the main render method happens.
	 * Draws the Pacman
	 */
	 public void render(SpriteBatch batch) {		 
		 batch.draw(walkFrames[1], 100, 100);
	 }
		
}
