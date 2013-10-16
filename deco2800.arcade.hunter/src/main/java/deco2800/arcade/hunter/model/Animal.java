package deco2800.arcade.hunter.model;

import java.util.ArrayList;
import java.util.Iterator;

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
import deco2800.arcade.hunter.screens.GameScreen;

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
	
	private GameScreen gameScreen;

	public Animal(Vector2 pos, float width, float height, boolean hunted,
			String animalType, Animation anim, GameScreen game) {
		super(pos, width, height);
		setX(pos.x);
		setY(pos.y);
		currAnim = anim;
		if (animalType =="zebra") {
			hunt = Type.PREY;
			moveSpeed = 4;
		} else {
			hunt = Type.PREDATOR;
			moveSpeed = -2;
		}
		this.gameScreen = game;
		entities = gameScreen.getEntites();
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

		setY(getY() - Hunter.State.gravity);
		
		if(this.state == State.DEAD){
			if (deathTimer + Hunter.Config.PLAYER_BLINK_TIMEOUT <= System.currentTimeMillis()){
				Iterator it = entities.iterator();
				while (it.hasNext()){
						if(it.next().equals(this))
							it.remove();
				}
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

	public boolean isDead(){
		if (state == State.DEAD){
			return true;
		}else{
			return false;
		}
	}
	public void dead(){
		//draw a dead animal sprite
		//play sound
		moveSpeed = 0;
		this.state = State.DEAD;
		deathTimer = System.currentTimeMillis();
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
			if (this.getBounds().overlaps(e.getBounds())){
				if (e.getType() == "MapEntity"){
					collisions.add(new EntityCollision(this, e, CollisionType.MAP_ENTITY_C_ANIMAL));
				}
			}
		}
		return collisions;
	}

	/**
	 * Handles Collisions
	 */
	@Override
	public void handleCollision(Entity e, EntityCollection entities) {
		if (e == null){
			entities.remove(this);
		}else if (e.getType() == "MapEntity" && ((MapEntity)e).getEntityType() == "arrow"){
			this.dead();
			entities.remove(e);
		}
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
