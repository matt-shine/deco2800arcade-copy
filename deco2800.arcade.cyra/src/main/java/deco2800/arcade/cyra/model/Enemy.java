package deco2800.arcade.cyra.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import deco2800.arcade.cyra.model.Player.State;

/**
 * Abstract class that all enemy objects call from. Many of these functions should actually be put into MovableEntity
 * to avoid classes such as Explosion inheriting from this.
 * It contains the basic functionality to allow World to easily update every Enemy in play
 * @author GAME OVER
 *
 */
public abstract class Enemy extends MovableEntity{
	
	protected float stateTime = 0;
	protected boolean isDead;
	protected boolean deathCounted = false;
	protected boolean startingNextScene;
	protected int score;
	protected String healthName;
	protected Boolean advanceDuringScenes;
	protected boolean flash;
	protected float invincibleTime;
	
	/**
	 * abstract Enemy Constructor
	 * @param speed should not be used
	 * @param rotation the rotation from the base of 0
	 * @param pos the initial position to place the enemy
	 * @param width the width of the enemy's default bounding box
	 * @param height the height of the enemy's default bounding box
	 */
	public Enemy(float speed, float rotation, Vector2 pos, float width, float height) {
		super(speed, rotation, pos, width, height);
		isDead = false;
		startingNextScene = false;
		score = 0;
		advanceDuringScenes = false;
		healthName = "";
		invincibleTime = 0;
		flash = false;
	}
	
	/**
	 * 
	 * @return true if enemy should play jumping animation
	 */
	public boolean isJumping(){
			return false;
	}
	
	/**
	 * 
	 * @return the count of how long the enemy will be in a certain state
	 */
	public float getStateTime() {
		return stateTime;
	}
	
	
	@Override
	public void handleTopOfMovingPlatform(MovablePlatform movablePlatform) {
		//System.out.println("enemy had top of moving platform collision");
		getPosition().y = movablePlatform.getPosition().y + 
				movablePlatform.getCollisionRectangle().height+1/32f;
		
	}
	
	@Override
	public void handleXCollision(Rectangle tile) {
		//System.out.println("enemy had x collision");
		if (velocity.x > 0.01f) {
			position.x = tile.getX() - getWidth() -0.001f;
				
		} else if (velocity.x < -0.01f){
			position.x = tile.getX() + tile.getWidth() + 0.001f;
		}
		
		
		
	}
	
	@Override
	public void handleYCollision(Rectangle tile, boolean onMovablePlatform,
			MovablePlatform movablePlatform) {
		//System.out.println("enemy had y collision");
		if (velocity.y < 0 ) {
			if (onMovablePlatform) {
				velocity.y = -movablePlatform.getSpeed();
			}
			if (!onMovablePlatform) {
				position.y = tile.y + tile.height + 0.001f;
				velocity.y = 0;
			}
				
			
		} else if (velocity.y > 0) {
			if (onMovablePlatform) {
				velocity.y = -movablePlatform.getSpeed();
			} else {
				velocity.y = 0;
			}
			position.y = tile.y - getHeight() - 0.001f;
		}
		
	}
	
	/**
	 * The function called when the player's attack and this enemy has a collision
	 * @param fromRight true if the player's attack is to the right of this Enemy
	 */
	public void handleDamage(boolean fromRight) {
		isDead = true;
	}
	
	/**
	 * 
	 * @return true if the enemy should be removed from the World
	 */
	public boolean isDead() {
		return isDead;
	}
	
	public void toggleDeathCount() {
		deathCounted = true;
	}
	public boolean getDeathCounted() {
		return deathCounted;
	}
	
	/**
	 * 
	 * @return true if Enemy is made to call a new scene in the LevelScenes
	 */
	public boolean startingNextScene() {
		if (startingNextScene) {
			startingNextScene = false;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @return true if WorldRenderer should display this enemy's health
	 */
	public boolean displayHealth() {
		return false;
	}
	
	/**
	 * 
	 * @return the percentage of health bar should be filled in WorldRenderer
	 */
	public float getHealthPercentage() {
		return 0f;
	}
	
	/**
	 * 
	 * @return the name of the Enemy to display, if displayHealth()==true
	 */
	public String getHealthName() {
		return healthName;
	}
	
	/**
	 * 
	 * @return the score to add in World when this Enemy is removed from game
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * 
	 * @return the Rectangle where this Enemy can be hurt. Defaults to the width and height of Enemy
	 */
	public Rectangle getVulnerableBounds() {
		return getBounds();
	}
	
	/**
	 * 
	 * @return Array of Rectangles where this Enemy can hurt the player. Defeaults to width/height of Enemy
	 */
	public Array<Rectangle> getPlayerDamageBounds() {
		Array<Rectangle> playerDamageRectangle = new Array<Rectangle>();
		playerDamageRectangle.add(getBounds());
		return playerDamageRectangle;
	}
	
	/**
	 * The function to be called every update in World
	 * @param delta value returned by Gdx.graphics.getDeltaTime()
	 * @param ship the current Player
	 * @param rank the difficulty exclusively between 0 and 1
	 * @param cam the camera being used by the game
	 * @return an Array of Enemies to spawn into the World
	 */
	public abstract Array<Enemy> advance(float delta, Player ship, float rank, OrthographicCamera cam);
	
	/**
	 * 
	 * @return true if advance() should be called even when LevelScenes are playing
	 */
	public boolean advanceDuringScenes() {
		return advanceDuringScenes;
	}
	
	/**
	 * 
	 * @return true if Enemy is invincible at this time. Used by WorldRenderer
	 */
	public boolean isInvincible() {
		if (invincibleTime > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Toggle whether the Enemy should be displayed this frame or not by WorldRenderer
	 * @return true if Enemy should be shown
	 */
	public boolean toggleFlash() {
		flash= !flash;
		return flash;
	}

}
