package deco2800.arcade.hunter.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.platformergame.model.Entity;
import deco2800.arcade.platformergame.model.EntityCollection;
import deco2800.arcade.platformergame.model.EntityCollision;
import deco2800.arcade.platformergame.model.EntityCollision.CollisionType;

public class Animal extends Entity {
	
	private boolean moving;
	
	private int moveSpeed;
	
	private State state = State.IDLE;
	
	private Animation currAnim;
	
	private Type type;
	
	
	public Animal(Vector2 pos, float width, float height, boolean hunted, String filepath) {
		super(pos, width, height);
		setX(pos.x);
		setY(pos.y);
		moving = false;
		if(hunted){
			type = Type.PREY;
		}else{
			type = Type.PREDATOR;
		}
		setAnimation(loadAnimations(filepath));
	}

	private Animation loadAnimations(String filepath) {
		Texture text = new Texture("textures/Animals/"+filepath+"IDLE.png");
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
	
	private enum Type{
		PREDATOR,
		PREY
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
	
	public Type getType(){
		return type;
	}
	
	@Override
	public ArrayList<EntityCollision> getCollisions(EntityCollection entities) {
		ArrayList<EntityCollision> collisions = new ArrayList<EntityCollision>();
		for (Entity e: entities){
			if (this.getX() <= 0)collisions.add(new EntityCollision(e, null, CollisionType.PREDATOR_C_LEFT_EDGE));
		}
		return collisions;
	}
	
	@Override
	public void handleCollision(Entity e){
		if (e == null)
			killAnimal();
	}

	private void killAnimal() {
		System.out.println("DESTROY");
	}
}
