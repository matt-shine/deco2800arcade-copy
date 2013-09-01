package deco2800.arcade.hunter.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.platformergame.model.Entity;

public class Player extends Entity {
	private static final int JUMP_VELOCITY = 5;
	//Is the player standing on something
	private boolean grounded;
	private float jumpVelocity;
	
	
	private Animation currAnim;
	//States used to determine how to draw the player
	private enum State {
		RUNNING,
		JUMPING,
		FALLING,
		DEAD
	};
	
	private State state = State.RUNNING;

	public Player(Vector2 pos, float width, float height) {
		super(pos, width, height);
		setX(128); //starting X offset
	}
	
	public boolean isGrounded() {
		return grounded;
	}
	
	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}

	public float getJumpVelocity() {
		return jumpVelocity;
	}
	
	public void jump() {
		setJumpVelocity(JUMP_VELOCITY);
	}

	public void setJumpVelocity(float jumpVelocity) {
		this.jumpVelocity = jumpVelocity;
	}
	
	public State getState() {
		return state;
	}
	
	public void update(float delta) {
		//Everything depends on everything else here, may have to rearrange, or even double up on checks
		//Check if player is grounded, this should be changed to check if you are standing on a map tile TODO
		if (getY() <= 0) {
			setY(0);
			grounded = true;
		} else {
			grounded = false;
		}
		
		//Move the player vertically, horizontally controlled by map
		setJumpVelocity((float) (getJumpVelocity() - delta * 9.81));
		if (jumpVelocity > 0 || !grounded) {
			//JUMP UP
			setY(getY() + getJumpVelocity()); //9.81 is gravity.
		}
		
		//Need to check for the player moving past the edge of a tile in the physics step above TODO
		
		//Update the player state
		//Pretending the DEAD state doesn't exist for now... TODO
		if (grounded) {
			this.state = State.RUNNING;
			currAnim = runAnimation();
		} else if (jumpVelocity > 0) {
			this.state = State.JUMPING;
			currAnim = jumpAnimation();
		} else if (jumpVelocity <= 0) {
			this.state = State.FALLING;
			currAnim = fallAnimation();
		}
	}
	
	public Animation getAnimation(){
		return currAnim;
	}
	
	public Animation hurtAnimation(){
		return null;
	}
	
	public Animation deathAnimation(){
		return null;
	}
	
	public Animation attackAnimation(){
		return null;
	}
	
	public Animation jumpAnimation(){
		Texture jumpSheet = new Texture("textures/jumpSheet.png");
		TextureRegion[][] jumpRegion = TextureRegion.split(jumpSheet,jumpSheet.getWidth()/2, jumpSheet.getHeight());
		TextureRegion jumpFrame = jumpRegion[0][1];
		Animation jumpAnim = new Animation(1f,jumpFrame);
		return jumpAnim;
	}
	
	public Animation fallAnimation(){
		Texture fallSheet = new Texture("textures/jumpSheet.png");
		TextureRegion[][] fallRegion = TextureRegion.split(fallSheet,fallSheet.getWidth()/2, fallSheet.getHeight());
		TextureRegion fallFrame = fallRegion[0][0];
		Animation fallAnim = new Animation(1f,fallFrame);
		return fallAnim;
	}
	
	public Animation runAnimation(){
		Animation runAnim;
		Texture runSheet;
		TextureRegion[] runFrames;
		
		int RUN_FRAMES = 3;
		runSheet = new Texture("textures/runSheet.png");
		TextureRegion[][] tmp = TextureRegion.split(runSheet, runSheet.getWidth()/RUN_FRAMES, runSheet.getHeight());
		runFrames = new TextureRegion[RUN_FRAMES];
		int i = 0;
		int index = 0;
		for (int j = 0; j < RUN_FRAMES; j++){
			runFrames[index++] = tmp[i][j];
		}
		runAnim = new Animation(0.1f, runFrames);
		
		return runAnim;
	}
	
}
