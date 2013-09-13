package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PowerUp extends Entity {

	// Set up constant variable for the number of frame horizontal & vertical 
	private static final int FRAME_COLS = 10;
	private static final int FRAME_ROWS = 7;
	
	// Variable to make animation
	private Animation walkAnimation;
	private Texture walkSheet;
	private TextureRegion[] walkFrames;
	private TextureRegion currentFrame = new TextureRegion();
	
	float stateTime;
	
	private int xPos = 500;
	private int yPos = 100;

	// Recommend to implement animation only to one specific class  
	public PowerUp() {
		// Current static implementation may chage later
		walkSheet = new Texture(Gdx.files.internal("items/test_ani.png"));
		// Using variables to divide up the sprite sheet for this case 10 x 7
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);
		// Initialise size of walkFrame array
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		
		int index = 0;
		
		// use tmp to input divided up image into one long frames
		for (int i=0; i< FRAME_ROWS; ++i)
		{
			for (int j=0; j<FRAME_COLS; ++j)
			{
				walkFrames[index++] = tmp[i][j];
			}
		}
		
		// the first parameter determines the speed off the animation
		walkAnimation = new Animation(0.15f, walkFrames);
		stateTime = 0f;
	}
	
	@Override
	public void act(float delta) {
		// increment delta time
		stateTime += delta;
		// change the current image to the next frame
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		
		// TODO implement key input here 
	}
	
	public void draw(SpriteBatch batch, float alpa)
	{
		// draw into the game at coordinate(xPos, yPos)
		batch.draw(currentFrame, xPos, yPos);
	}

}
