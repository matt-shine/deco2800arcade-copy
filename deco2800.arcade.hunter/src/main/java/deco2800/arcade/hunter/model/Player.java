package deco2800.arcade.hunter.model;

import java.util.ArrayList;
import java.util.HashMap;

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
	 * Jump Velocity variable
	 */
	private float jumpVelocity;
	/**
	 * Velocity of the player
	 */
	private Vector2 velocity = new Vector2(1, 0);

	private TextureRegion img = new TextureRegion(new Texture(
			"textures/playerAnim/GensijinRun kf.png"));
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

	// States used to determine how to draw the player
	private enum State {
		RUNNING, JUMPING, ATTACK, FALLING, DEAD
	};

	private State state = State.RUNNING;

	public Player(Vector2 pos, float width, float height) {
		super(pos, width, height);
		loadAnimations();
		lives = 3;
	}

	/**
	 * Loads all the animations and puts it into the HashMap of Animations
	 */
	private void loadAnimations() {
		Texture jumpSheet = new Texture("textures/jumpSheet.png");
		TextureRegion[][] jumpRegion = TextureRegion.split(jumpSheet,
				jumpSheet.getWidth() / 2, jumpSheet.getHeight());
		TextureRegion jumpFrame = jumpRegion[0][1];
		jumpFrame.flip(true, false);
		animationList.put("Jump", new Animation(1f, jumpFrame));

		Texture fallSheet = new Texture("textures/jumpSheet.png");
		TextureRegion[][] fallRegion = TextureRegion.split(fallSheet,
				fallSheet.getWidth() / 2, fallSheet.getHeight());
		TextureRegion fallFrame = fallRegion[0][0];
		fallFrame.flip(true, false);
		animationList.put("Fall", new Animation(1f, fallFrame));

		animationList.put("Run",
				createAnimation(3, new Texture("textures/runSheet.png")));
		animationList.put(
				"RunSpear",
				createAnimation(3, new Texture(
						"textures/playerAnim/GensijinRun spear.png")));
		animationList.put(
				"RunKnF",
				createAnimation(3, new Texture(
						"textures/playerAnim/GensijinRun kf.png")));
		animationList.put(
				"RunTrident",
				createAnimation(3, new Texture(
						"textures/playerAnim/GensijinRun df.png")));
		animationList.put(
				"AttackKnF",
				createAnimation(2, new Texture(
						"textures/playerAnim/Gensijinattack kf.png")));
		animationList.put(
				"AttackTrident",
				createAnimation(2, new Texture(
						"textures/playerAnim/Gensijinattack df.png")));
		animationList.put(
				"AttackSpear",
				createAnimation(2, new Texture(
						"textures/playerAnim/Gensijinattack spear.png")));
		animationList.put(
				"DamageSpear",
				createAnimation(1, new Texture(
						"textures/playerAnim/GensijinDamage spear.png")));
		animationList.put(
				"DamageKnF",
				createAnimation(1, new Texture(
						"textures/playerAnim/GensijinDamage kf.png")));
		animationList.put(
				"DamageTrident",
				createAnimation(1, new Texture(
						"textures/playerAnim/GensijinDamage df.png")));
		animationList.put(
				"GameOverSpear",
				createAnimation(1, new Texture(
						"textures/playerAnim/GensijinGameOverspear.png")));
		animationList.put(
				"GameOverKnF",
				createAnimation(1, new Texture(
						"textures/playerAnim/GensijinGameOver kf.png")));
		animationList.put(
				"GameOverTrident",
				createAnimation(1, new Texture(
						"textures/playerAnim/GensijinGameOver df.png")));

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
	private Animation createAnimation(int frames, Texture text) {
		TextureRegion[][] tmp = TextureRegion.split(text, text.getWidth()
				/ frames, text.getHeight());
		TextureRegion[] animFrames = new TextureRegion[frames];
		int index = 0;
		for (int j = 0; j < frames; j++) {
			animFrames[index++] = tmp[0][j];
		}
		return new Animation(0.1f, animFrames);
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

	@Override
	public void update(float delta) {

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

		// HERE TODO

		// Everything depends on everything else here, may have to rearrange, or
		// even double up on checks
		// Check if player is grounded, this should be changed to check if you
		// are standing on a map tile TODO
		setX(getX() + delta * velocity.x);

		setJumpVelocity(getJumpVelocity() - delta * 9.81f);
		setY(getY() + getJumpVelocity());

		// Need to check for the player moving past the edge of a tile in the
		// physics step above TODO

		// Update the player state
		// Pretending the DEAD state doesn't exist for now... TODO
		if (isGrounded()) {
			jumpVelocity = 0;
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
		return null;
	}

	/**
	 * Returns the death Animation
	 * 
	 * @return Animation - Death animation
	 */
	public Animation deathAnimation() {
		return null;
	}

	/**
	 * Returns the attack animation
	 * 
	 * @return Animation - Attack Animation
	 */
	public Animation attackAnimation() {
		return null;
	}

	/**
	 * Returns the jump animation
	 * 
	 * @return Animation - Jump Animation
	 */
	public Animation jumpAnimation() {
		return animationList.get("Jump");
	}

	/**
	 * Returns the fall animation
	 * 
	 * @return Animation - Fall animation
	 */
	public Animation fallAnimation() {
		return animationList.get("Fall");
	}

	/**
	 * Returns the run animation
	 * 
	 * @return Animation - Run animation
	 */
	public Animation runAnimation() {
		return animationList.get("RunKnF");
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(img, getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * Checks for entity collisions
	 */
	@Override
	public ArrayList<EntityCollision> getCollisions(EntityCollection entities) {
		ArrayList<EntityCollision> collisions = new ArrayList<EntityCollision>();
		Player player = this;
		for (Entity e : entities) {
			if (player.getX() <= 0) { // change 0 to
										// forgeoundlayer.getXoffset();
				collisions.add(new EntityCollision(player, null,
						CollisionType.PLAYER_C_LEFT_EDGE));
			}
			if (player.getBounds().overlaps(e.getBounds())) {
				if (e.getClass().equals(Animal.class)) {
					if (player.state == State.ATTACK)
						collisions.add(new EntityCollision(player, e,
								CollisionType.PLAYER_PROJECTILE_C_ANIMAL));
					else
						collisions.add(new EntityCollision(player, e,
								CollisionType.WORLD_PROJECTILE_C_PLAYER));

				}
				if (e.getClass() == Items.class) {
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
	public void handleCollision(Entity e) {
		if (e == null) {
			gameOver();
		}
		if (e.getClass() == Items.class) {
			System.out.println("Item pickup!");
		}
		if (e.getClass() == Animal.class) {
			System.out.println("Animal Collision");
			loseLife();
		}
		if (e.getClass() == Animal.class && this.state == State.ATTACK) {
			killAnimal();
		}
	}

	private void killAnimal() {
		System.out.println("Yay you killed an animal!");
	}

	public void loseLife() {
		lives -= 1;
	}

	public void addLife() {
		lives += 1;
	}

	public int getLives() {
		return lives;
	}

	private void gameOver() {
		System.out.println("Game Over!");
	}
}