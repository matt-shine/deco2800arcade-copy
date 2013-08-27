package deco2800.arcade.hunter.model;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.platformergame.model.Entity;

public class Player extends Entity {
	private static final int JUMP_VELOCITY = 20;
	//Is the player standing on something
	private boolean grounded;
	private float jumpVelocity;
	
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
	
	public void setY(float y) {
		this.setPosition(new Vector2(this.getX(), y));
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
		setJumpVelocity(getJumpVelocity() - 1);
		if (jumpVelocity > 0 || !grounded) {
			//JUMP UP
			setY((float) (getY() + getJumpVelocity() * delta * 9.81)); //9.81 is gravity.
		}
		
		//Need to check for the player moving past the edge of a tile in the physics step above TODO
		
		//Update the player state
		//Pretending the DEAD state doesn't exist for now... TODO
		if (grounded) {
			this.state = State.RUNNING;
		} else if (jumpVelocity > 0) {
			this.state = State.JUMPING;
		} else if (jumpVelocity <= 0) {
			this.state = State.FALLING;
		}
	}
}
