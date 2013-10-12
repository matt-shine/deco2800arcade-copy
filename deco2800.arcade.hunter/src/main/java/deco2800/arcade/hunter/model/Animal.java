package deco2800.arcade.hunter.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.platformergame.Entity;
import deco2800.arcade.hunter.platformergame.EntityCollection;
import deco2800.arcade.hunter.platformergame.EntityCollision;
import deco2800.arcade.hunter.platformergame.EntityCollision.CollisionType;

public class Animal extends Entity {


	/**
	 * The speed at which the Animal moves
	 */
	private int moveSpeed;

	/**
	 * State of the Animal
	 */
	private State state = State.IDLE;

	/**
	 * The current animation of the animal
	 */
	private Animation currAnim;

	/**
	 * The type of animal
	 */
	private Type hunt;

	private String classType = "Animal";


	private boolean animLoop;

	private long deathTimer;
	
	private EntityCollection entities;

	public Animal(Vector2 pos, float width, float height, boolean hunted,
			String filepath) {
		super(pos, width, height);
		setX(pos.x);
		setY(pos.y);
		currAnim = loadAnimation(filepath);
		if (filepath =="zebra") {
			hunt = Type.PREY;
			moveSpeed = 4;
		} else {
			hunt = Type.PREDATOR;
			moveSpeed = -2;
		}
	}

	/**
	 * Takes a file path and loads all the animations in that file path
	 */
	private Animation loadAnimation(String animal) {
		Texture text = new Texture("textures/Animals/"+ animal + ".png");
		TextureRegion[][] tmp = TextureRegion.split(text, text.getWidth() / 2,
				text.getHeight());
		TextureRegion[] animFrames = new TextureRegion[2];
		int index = 0;
		for (int i = 0; i < 2; i++) {
			animFrames[index++] = tmp[0][i];
		}
		return new Animation(0.5f,animFrames);
	}

	private enum State {
		MOVING, DEAD, IDLE
	}

	private enum Type {
		PREDATOR, PREY
	}

	public void update(float delta) {
		if (moveSpeed != 0) {
			// updates position of enemy
			setX(getX() + moveSpeed);
		}

		setY(getY() - Hunter.Config.gravity);
		
		if(this.state == State.DEAD){
			if (deathTimer + Hunter.Config.PLAYER_BLINK_TIMEOUT <= System.currentTimeMillis()){
				entities.remove(this);
			}
		}
	}


	/**
	 * Returns the current animation of the animal
	 * 
	 * @return Animation Current Animation
	 */
	public Animation getAnim() {
		return currAnim;
	}

	/**
	 * Returns the type of animal
	 * 
	 * @return Type - Predator or Prey
	 */
	public Type getHunt() {
		return hunt;
	}

	public void dead(EntityCollection entities){
		//draw a dead animal sprite
		//play sound
		moveSpeed = 0;
		this.state = State.DEAD;
		deathTimer = System.currentTimeMillis();
		this.entities = entities;
	}
	
	/**
	 * Checks for collisions
	 */
	@Override
	public ArrayList<EntityCollision> getCollisions(EntityCollection entities) {
		ArrayList<EntityCollision> collisions = new ArrayList<EntityCollision>();
		for (Entity e : entities) {
			if (this.getX() <= 0)
				collisions.add(new EntityCollision(this, null,
						CollisionType.PREDATOR_C_LEFT_EDGE));
		}
		return collisions;
	}

	/**
	 * Handles Collisions
	 */
	@Override
	public void handleCollision(Entity e, EntityCollection entities) {
		if (e == null)
			entities.remove(this);
	}

	@Override
	public String getType() {
		return classType;
	}

	@Override
	public void draw(SpriteBatch batch, float stateTime) {
		if (state == State.DEAD) {
			animLoop = false;
		} else {
			animLoop = true;
		}
		TextureRegion currFrame = currAnim.getKeyFrame(stateTime, animLoop);
		batch.draw(currFrame, getX(), getY(), getWidth(), getHeight());
	}
}
