package deco2800.arcade.hunter.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.platformergame.model.Entity;

public class Enemy extends Entity {

	private boolean hunted;
	
	private boolean moving;
	
	private int moveSpeed;
	
	private State state = State.IDLE;
	
	private Animation currAnim;
	
	
	public Enemy(Vector2 pos, float width, float height, boolean hunted, String filepath) {
		super(pos, width, height);
		setX(pos.x);
		setY(pos.y);
		moving = false;
		this.hunted = hunted;
		setAnimation(loadAnimations(filepath));
	}

	private Animation loadAnimations(String filepath) {
		Texture text = new Texture("textures/Animals/"+filepath+"IDLE");
		TextureRegion[][] tmp = TextureRegion.split(text, text.getWidth()/2, text.getHeight());
		TextureRegion[] animFrames = new TextureRegion[2];
		int index = 0;
		for(int j = 0; j<2; j++){
			animFrames[index++] = tmp[0][j];
		}
		return new Animation(0.5f,animFrames);
	}

	private enum State{
		MOVING,
		DEAD,
		IDLE
	}
	
	/*
	 * Changes the state of the entity to moving
	 */
	public void changeMove(){
		if (moving){
			state = State.IDLE;
			moving = false;
			moveSpeed = 0;
		}else{
			state = State.MOVING;
			moving = true;
			moveSpeed = -5;
		}
	}
	
	
	
	public void update(float delta){
		//Checks whether the enemy is on the screen
		if(getX() <= 0){
			setX(200);
		}
		
		if(moveSpeed != 0){
			//updates position of enemy
			setX(getX() + moveSpeed);
		}
		
		//checking collision with the ground and eventually with the tile map TODO
		if(getY() <= 0){
			setY(1);
		}
		
	}
	
	private void setAnimation(Animation anim){
		currAnim = anim;
	}
	
	public Animation getAnim(){
		return currAnim;
	}
}
