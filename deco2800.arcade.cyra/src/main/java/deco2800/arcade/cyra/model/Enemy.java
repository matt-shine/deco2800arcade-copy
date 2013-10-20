package deco2800.arcade.cyra.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import deco2800.arcade.cyra.model.Player.State;

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
	
	public boolean isJumping(){
			return false;
	}
	
	
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
	
	public void handleDamage(boolean fromRight) {
		isDead = true;
	}
	
	public boolean isDead() {
		return isDead;
	}
	public void toggleDeathCount() {
		deathCounted = true;
	}
	public boolean getDeathCounted() {
		return deathCounted;
	}
	
	public boolean startingNextScene() {
		if (startingNextScene) {
			startingNextScene = false;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean displayHealth() {
		return false;
	}
	
	public float getHealthPercentage() {
		return 0f;
	}
	
	public String getHealthName() {
		return healthName;
	}
	
	public int getScore() {
		return score;
	}
	
	public Rectangle getVulnerableBounds() {
		return getBounds();
	}
	
	public Array<Rectangle> getPlayerDamageBounds() {
		Array<Rectangle> playerDamageRectangle = new Array<Rectangle>();
		playerDamageRectangle.add(getBounds());
		return playerDamageRectangle;
	}
	
	public abstract Array<Enemy> advance(float delta, Player ship, float rank, OrthographicCamera cam);
	
	public boolean advanceDuringScenes() {
		return advanceDuringScenes;
	}
	
	public boolean isInvincible() {
		if (invincibleTime > 0 ) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean toggleFlash() {
		flash= !flash;
		return flash;
	}

}
