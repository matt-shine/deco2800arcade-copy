package deco2800.arcade.hunter.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.platformergame.model.Entity;
import deco2800.arcade.platformergame.model.EntityCollection;
import deco2800.arcade.platformergame.model.EntityCollision;
import deco2800.arcade.platformergame.model.EntityCollision.CollisionType;

public class Player extends Entity {
	/**
	 * Constant of JUMP VELOCITY
	 */
	private static final int JUMP_VELOCITY = 8;
	/**
	 * Velocity of the player
	 */
	
	private Vector2 velocity = new Vector2(1, 0);

	/**
	 * The current animation for the player
	 */
	private Animation currAnim;
	/**
	 * Lives counter for the player
	 */
	private int lives;
	/**
	 * A hashmap list of all the players
	 */
	private HashMap<String, Animation> animationList = new HashMap<String, Animation>();
	private String Weapon;
	private boolean animLoop;
    private long attackTime = 0;
    private long damageTime = 0; //Time when last hit by a monster.
	
	// States used to determine how to draw the player
	private int score = 0;
	
	private String classType = "Player";
	
	private int multiplier;
    private boolean blink = false;

    //States used to determine how to draw the player
	private enum State {
		RUNNING, JUMPING, ATTACK, FALLING, DEAD
	};

	private State state = State.RUNNING;
	private boolean invulnerable;


	private Sound pickup = Gdx.audio.newSound(Gdx.files.internal("powerup.wav"));
	private Sound death = Gdx.audio.newSound(Gdx.files.internal("death.wav"));
	private Sound hurt = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
	
	public Player(Vector2 pos, float width, float height) {
		super(pos, width, height);
		loadAnims();
		lives = 3;
		Weapon = "Spear";
		currAnim = runAnimation();
		multiplier = 1;
		
	}

	
	private void loadAnims(){
		try{
			File fXmlfile = Gdx.files.internal("../deco2800.arcade.hunter/src/main/resources/player.xml").file();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlfile);
			
			NodeList nList = doc.getElementsByTagName("animation");
			
			for (int temp = 0; temp < nList.getLength(); temp++){
				
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					animationList.put(eElement.getAttribute("id")+"running", createAnimation(3,new Texture(eElement.getElementsByTagName("running").item(0).getTextContent()),0.1f ));
					animationList.put(eElement.getAttribute("id")+"attack", createAnimation(2,new Texture(eElement.getElementsByTagName("attack").item(0).getTextContent()),0.3f ));
					animationList.put(eElement.getAttribute("id")+"jump", createAnimation(2,new Texture(eElement.getElementsByTagName("jumping").item(0).getTextContent()),2f ));
					animationList.put(eElement.getAttribute("id")+"damage", createAnimation(1,new Texture(eElement.getElementsByTagName("damage").item(0).getTextContent()),1f ));
					animationList.put(eElement.getAttribute("id")+"death", createAnimation(1,new Texture(eElement.getElementsByTagName("death").item(0).getTextContent()),10f ));
				}
			}
			System.out.println("Player Animations Loaded");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	/**
	 * Creates an animation
	 * 
	 * @param frames
	 *            Determines how many frames there are in the texture
	 * @param text
	 *            The texture to be created into an animation
	 * @return Animation of the Texture
	 */
	private Animation createAnimation(int frames, Texture text, float speed) {
		TextureRegion[][] tmp = TextureRegion.split(text, text.getWidth()
				/ frames, text.getHeight());
		TextureRegion[] animFrames = new TextureRegion[frames];
		for (int j = 0; j < frames; j++) {
			animFrames[j] = tmp[0][j];
		}
		return new Animation(speed, animFrames);
	}

	/**
	 * Returns whether the player is grounded
	 * 
	 * @return grounded
	 */
	public boolean isGrounded() {
		return collider.bottom;
	}

	/**
	 * Sets whether the player is grounded
	 * 
	 * @param grounded
	 *            Boolean value of whether its grounded
	 */
	public void setGrounded(boolean grounded) {
		this.collider.bottom = grounded;
	}

	/**
	 * Gets the jump velocity
	 * 
	 * @return float value of the JumpVelocity
	 */
	public float getJumpVelocity() {
		return this.velocity.y;
	}

	/**
	 * Makes the player jump
	 */
	public void jump() {
		setJumpVelocity(JUMP_VELOCITY);
		this.collider.bottom = false;
	}

	/**
	 * Sets the jump velocity
	 * 
	 * @param jumpVelocity
	 *            Float value of what the jump velocity will be set to
	 */
	public void setJumpVelocity(float jumpVelocity) {
		this.velocity.y = jumpVelocity;
	}

	/**
	 * Returns the Player State
	 * 
	 * @return State
	 */
	public State getState() {
		return state;
	}

	public void setWeapon(String s){
		Weapon = s;
	}
	
	@Override
	public void update(float delta) {
        // Everything depends on everything else here, may have to rearrange, or
        // even double up on checks

		if (collider.top && velocity.y > 0) {
			setJumpVelocity(0);
		}

		if (collider.bottom && velocity.y < 0) {
			setJumpVelocity(0);
		}

		if (collider.right) {
			velocity.x = 0;
		} else if (velocity.x < Config.gameSpeed) {
			velocity.x++;
		}

		setX(getX() + delta * velocity.x);

		setJumpVelocity(getJumpVelocity() - delta * Config.gravity);
		setY(getY() + getJumpVelocity());

        if (attackTime + Config.PLAYER_ATTACK_TIMEOUT < System.currentTimeMillis()) {
            this.state = State.RUNNING;
        }

        if (damageTime + Config.PLAYER_BLINK_TIMEOUT < System.currentTimeMillis()) {
            this.blink = false;
        }

		// Update the player state
		// Pretending the DEAD state doesn't exist for now... TODO
		if (isGrounded() && this.state != State.ATTACK) {
			this.velocity.y = 0;
			this.state = State.RUNNING;
			currAnim = runAnimation();
		} else if (this.velocity.y > 0) {
			this.state = State.JUMPING;
			currAnim = jumpAnimation();
		} else if (this.velocity.y <= 0) {
			this.state = State.FALLING;
		}
	}

	/**
	 * Returns the current animation
	 * 
	 * @return Animation - Current Animation
	 */
	public Animation getAnimation() {
		return currAnim;
	}

	/**
	 * Return the hurt Animation
	 * 
	 * @return Animation - Hurt Animation
	 */
	public Animation hurtAnimation() {
		return animationList.get(Weapon + "damage");
	}

	/**
	 * Returns the death Animation
	 * 
	 * @return Animation - Death animation
	 */
	public Animation deathAnimation() {
		return animationList.get(Weapon + "death");
	}

	/**
	 * Returns the attack animation
	 * 
	 * @return Animation - Attack Animation
	 */
	public Animation attackAnimation() {
		return animationList.get(Weapon + "attack");
	}

	/**
	 * Returns the jump animation
	 * 
	 * @return Animation - Jump Animation
	 */
	public Animation jumpAnimation() {
		return animationList.get(Weapon + "jump");
	}

	/**
	 * Returns the run animation
	 * 
	 * @return Animation - Run animation
	 */
	public Animation runAnimation() {
		return animationList.get(Weapon + "running");
	}

	@Override
	public void draw(SpriteBatch batch, float stateTime) {
		if (invulnerable){
			//draw invincibility bubble
		}
		if(state == State.RUNNING){
			animLoop = true;
		}else{
			animLoop = false;
		}
		TextureRegion currFrame = currAnim.getKeyFrame(stateTime, animLoop);
		batch.draw(currFrame, getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * Checks for entity collisions
	 */
	@Override
	public ArrayList<EntityCollision> getCollisions(EntityCollection entities) {
		ArrayList<EntityCollision> collisions = new ArrayList<EntityCollision>();
		Player player = this;
		for (Entity e : entities) {
			if (player.getBounds().overlaps(e.getBounds())) {
				if (e.getType() == "Animal") {
					if (player.state == State.ATTACK)
						collisions.add(new EntityCollision(player, e,
								CollisionType.PLAYER_PROJECTILE_C_ANIMAL));
							
					else
						collisions.add(new EntityCollision(player, e,
								CollisionType.WORLD_PROJECTILE_C_PLAYER));

				}
				if (e.getType() == "Items") {
					System.out.println("Item Collision");
					collisions.add(new EntityCollision(player, e,
							CollisionType.ITEM_C_PLAYER));
				}
			}

		}
		return collisions;
	}

	/**
	 * Handles entity collisions
	 */
	@Override
	public void handleCollision(Entity e, EntityCollection entities) {
		if (e == null) {
			gameOver();
		}else if (e.getType() == "Items") {
			System.out.println("Item pickup!");
			System.out.println(((Items) e).getItem());
			entities.remove(e);
			pickup.play(1.0f);
			if (((Items)e).getItemType() == Items.Type.WEAPON){
				setWeapon(((Items)e).getItem());
			}else{
				applyPlayerBuff(((Items)e).getItem());
			}
		}else if (e.getType() == "Animal") {
			if (getState() == State.ATTACK){
				System.out.println("Attack Animal");
				entities.remove(e);
				killAnimal();
				
			}else{
				System.out.println("Animal Collision");
				if (!invulnerable && !blink){
                    hurt.play(1.0f);
                    this.blink = true;
                    damageTime = System.currentTimeMillis();
					loseLife();
					checkLives();
				}
			}
		}
	}

	private void checkLives() {
		if(lives <= 0){
			death.play(1.0f);
		}
	}


	private void applyPlayerBuff(String item) {
		if (item == "DoublePoints"){
			multiplier = multiplier * 2;
		}
			
		if (item == "ExtraLife"){
			addLife();
		}
		if (item == "Invulnerability"){
			invulnerable = true;
		}
		if (item == "AttackX2"){
			
		}
	}


	private void killAnimal() {
		System.out.println("Yay you killed an animal!");
		score = score + 20*multiplier;
	}

	public void loseLife() {
		lives -= 1;
	}

	public void addLife() {
		lives += 1;
	}

	public void setInvulnerability(boolean inv){
		invulnerable = inv;
	}
	
	public boolean isInvulnerable(){
		return invulnerable;
	}
	
	public void setMultiplier(int multi){
		multiplier = multi;
	}
	
	public int getMultiplier(){
		return multiplier;
	}
	
	public int getLives() {
		return lives;
	}
	
	public int getCurrentScore() {
		return score;
	}
	
	public float getCurrentDistance() {
		return getX() / Config.TILE_SIZE;
	}
	
	private void gameOver() {
		System.out.println("Game Over!");
	}

	public void attack() {
        if (this.state != State.ATTACK) {
            state = State.ATTACK;
            currAnim = attackAnimation();
            attackTime = System.currentTimeMillis();
        }
	}
	
	@Override
	public String getType(){
		return classType;
	}
	
}