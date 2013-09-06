package deco2800.arcade.pacman;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.FilledRectangle;
import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PacChar extends Actor{
	
	

	// Describes the current state of pacman
	private enum State {
		IDLE, WALKING, DEAD
	}
	
	// Static variables for handling pacman's animation
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 4;
	@SuppressWarnings("unused")
	private int facing = 1; // 1: Right, 3: Left
							// 3: Up, 4: Down

	// Sprite stuff??? TODO: comment properly later
	Sprite sprite;
	private Stage stage;
	@SuppressWarnings("unused")
	private Texture texture;
	private TextureRegion region;
	Animation walkAnimation;
	Texture walkSheet;
	TextureRegion[] walkFrames;
	SpriteBatch spriteBatch;
	TextureRegion currentFrame;

	float stateTime;
	
	/*
	 * create() is called when PacChar is instantiated.
	 * Handles loading of stages, actors etc.
	 */
	public void create() {
		
		
		// Create a stage, create Pacman actor. 
//		stage = new Stage(800, 800, false);
//        Gdx.input.setInputProcessor(stage);
//        //stage.addActor(actor);
       
        // Create textures
        texture = new Texture(Gdx.files.internal("pacmove.png"));
        region = new TextureRegion(texture, 0, 0, 16, 16);    
        
       
		
		// TODO: Need to modify this to work with our current sprites
		sprite = new Sprite(new Texture(Gdx.files.internal("pacmove.png")));

		walkSheet = new Texture(Gdx.files.internal("pacmove.png"));
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
		
		
		walkAnimation = new Animation(0.025f, walkFrames);
		spriteBatch = new SpriteBatch();
		stateTime = 0f;
	}
	
	/*
	 * Essentially an infinite for loop. Called continuously until
	 * 'game over'.
	 */
//	 public void render(SpriteBatch batch) {
//	        batch.draw(texture, 0f, 0);
//	    }
	
	public void draw(SpriteBatch batch, float parentAlpha) {
		//batch.setColor(0f, 1f, 1f, 1f);
		batch.draw(region, getX(), getY(), 128, 128);
	}
	
//	public void render() {
//		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
//		stateTime += Gdx.graphics.getDeltaTime();
//		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
//		spriteBatch.begin();
//		spriteBatch.draw(currentFrame, 50, 50);
//		spriteBatch.end();
//		stage.draw();
//	}

	public TextureRegion[] getWalkFrames() {
		return walkFrames;
	}

}
