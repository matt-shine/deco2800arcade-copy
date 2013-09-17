package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class EntityAnimated extends Entity {
	
	// Variable to make animation
	private Animation walkAnimation;
	private TextureRegionDrawable currentFrame = new TextureRegionDrawable();
	
	float stateTime;

	// Recommend to implement animation only to one specific class  
	public EntityAnimated(Texture walkSheet, int cols, int rows) {
		super();
		// Using variables to divide up the sprite sheet for this case 10 x 7
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,walkSheet.getWidth() / cols, walkSheet.getHeight() / rows);
		// Initialise size of walkFrame array
		TextureRegion[] walkFrames = new TextureRegion[cols * rows];
		
		int index = 0;
		
		// use tmp to input divided up image into one long frames
		for (int i=0; i< rows; ++i)
		{
			for (int j=0; j<cols; ++j)
			{
				walkFrames[index++] = tmp[i][j];
			}
		}
		
		// the first parameter determines the speed of the animation
		walkAnimation = new Animation(0.15f, walkFrames);
		stateTime = 0f;
		animate(0f);
		this.setDrawable(currentFrame);
		this.setWidth(currentFrame.getMinWidth());
		this.setHeight(currentFrame.getMinHeight());
	}
	
	public void animate(float delta) {
		// increment delta time
		stateTime += delta;
		// change the current image to the next frame
		currentFrame.setRegion(walkAnimation.getKeyFrame(stateTime, true));
	}
	
	@Override
	public void act(float delta) {
		animate(delta);
		move(delta);
	}
	
	public abstract void move(float delta);
}
