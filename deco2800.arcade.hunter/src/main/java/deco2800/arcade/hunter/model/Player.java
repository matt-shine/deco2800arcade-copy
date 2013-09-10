package deco2800.arcade.hunter.model;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.platformergame.model.Entity;

public class Player extends Entity {
	private static final int JUMP_VELOCITY = 8;
	//Is the player standing on something
	private boolean grounded;
	private float jumpVelocity;
	
	
	private Animation currAnim;
	
	private HashMap<String, Animation> animationList = new HashMap<String,Animation>();
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
		loadAnimations();
	}
	
	private void loadAnimations() {
		Texture jumpSheet = new Texture("textures/jumpSheet.png");
		TextureRegion[][] jumpRegion = TextureRegion.split(jumpSheet,jumpSheet.getWidth()/2, jumpSheet.getHeight());
		TextureRegion jumpFrame = jumpRegion[0][1];
		jumpFrame.flip(true, false);
		animationList.put("Jump", new Animation(1f,jumpFrame));
		
		Texture fallSheet = new Texture("textures/jumpSheet.png");
		TextureRegion[][] fallRegion = TextureRegion.split(fallSheet,fallSheet.getWidth()/2, fallSheet.getHeight());
		TextureRegion fallFrame = fallRegion[0][0];
		fallFrame.flip(true, false);
		animationList.put("Fall", new Animation(1f,fallFrame));
		
		animationList.put("Run", createAnimation(3,new Texture("textures/runSheet.png")));
		animationList.put("RunSpear", createAnimation(3,new Texture("textures/playerAnim/GensijinRun spear.png")));
		animationList.put("RunKnF", createAnimation(3,new Texture("textures/playerAnim/GensijinRun kf.png")));
		animationList.put("RunTrident", createAnimation(3,new Texture("textures/playerAnim/GensijinRun df.png")));
		animationList.put("AttackKnF", createAnimation(2,new Texture("textures/playerAnim/Gensijinattack kf.png")));
		animationList.put("AttackTrident", createAnimation(2,new Texture("textures/playerAnim/Gensijinattack df.png")));
		animationList.put("AttackSpear", createAnimation(2,new Texture("textures/playerAnim/Gensijinattack spear.png")));
		animationList.put("DamageSpear", createAnimation(1,new Texture("textures/playerAnim/GensijinDamage spear.png")));
		animationList.put("DamageKnF", createAnimation(1, new Texture("textures/playerAnim/GensijinDamage kf.png")));
		animationList.put("DamageTrident", createAnimation(1,new Texture("textures/playerAnim/GensijinDamage df.png")));
		animationList.put("GameOverSpear", createAnimation(1,new Texture("textures/playerAnim/GensijinGameOverspear.png")));
		animationList.put("GameOverKnF", createAnimation(1,new Texture("textures/playerAnim/GensijinGameOver kf.png")));
		animationList.put("GameOverTrident", createAnimation(1,new Texture("textures/playerAnim/GensijinGameOver df.png")));
		
		
	}

	private Animation createAnimation(int frames, Texture text){
		TextureRegion[][] tmp = TextureRegion.split(text, text.getWidth()/frames, text.getHeight());
		TextureRegion[] animFrames = new TextureRegion[frames];
		int index = 0;
		for (int j = 0; j<frames; j++){
			animFrames[index++] = tmp[0][j];
		}
		return new Animation(0.1f,animFrames);
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
	
	@Override
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
		return animationList.get("Jump");
	}
	
	public Animation fallAnimation(){
		return animationList.get("Fall");
	}
	
	public Animation runAnimation(){
		return animationList.get("RunKnF");
	}
	
}
