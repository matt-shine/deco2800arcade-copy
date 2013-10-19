package deco2800.arcade.hunter.model;

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

import java.util.ArrayList;
import java.util.Iterator;

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
	
	/**
	 * The Class type of the entity
	 */
	private String classType = "Animal";
	
	/**
	 * Boolean of whether the animal loops
	 */
	private boolean animLoop;
	//Timer for death
	private long deathTimer;
	//Entities list which the Animal is under
	private EntityCollection entities;
	//GameScreen in which the Animal is located
	private GameScreen gameScreen;
	//String of type of Animal
	private String animal;
	
	public Animal(Vector2 pos, float width, float height, boolean hunted,
			String animalType, Animation anim, GameScreen game) {
		super(pos, width, height);
		setX(pos.x);
		setY(pos.y);
		this.currAnim = anim;
		this.gameScreen = game;
		this.animal = animalType;
		this.entities = gameScreen.getEntites();
		//Determines whether it is a predator or a prey
		if (animalType.equals("zebra")) {
			hunt = Type.PREY;
			moveSpeed = 4;
		} else {
			hunt = Type.PREDATOR;
			moveSpeed = -2;
		}
	}
	
	
	private enum State {
		MOVING, DEAD, IDLE
	}

	private enum Type {
		PREDATOR, PREY
	}

	public void update(float delta) {
		
		// updates x position of the animal
		setX(getX() + moveSpeed);
		
		//updates y position of the animal
		setY(getY() - Hunter.State.gravity);
		
		//Removes the entity after a certain period, determined by deathTimer and timeout.
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

	/**
	 * Returns boolean of a dead state.
	 * @return Boolean - Whether the animal is in a dead state
	 */
	public boolean isDead(){
		return state == State.DEAD;
	}
	
	public void dead(){
		//draw a dead animal sprite
		//play sound
		moveSpeed = 0;
		this.state = State.DEAD;
		deathTimer = System.currentTimeMillis();
		currAnim = gameScreen.entityHandler.getAnimalAnimation(animal + "DEAD");
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
				if (e.getType().equals("MapEntity")){
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
		} else if (e.getType().equals("MapEntity") && ((MapEntity) e).getEntityType().equals("arrow") && this.state != State.DEAD){
			this.dead();
			gameScreen.addScore(200);
			gameScreen.addAnimalKilled();
			entities.remove(e);
		}
	}

	@Override
	public String getType() {
		return classType;
	}

	@Override
	public void draw(SpriteBatch batch, float stateTime) {
        animLoop = state != State.DEAD;
		TextureRegion currFrame = currAnim.getKeyFrame(stateTime, animLoop);
		batch.draw(currFrame, getX(), getY(), getWidth(), getHeight());
	}
}
