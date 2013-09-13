package deco2800.arcade.burningskies.entities;

import deco2800.arcade.burningskies.*;
import deco2800.arcade.burningskies.entities.bullets.BulletPattern;
import deco2800.arcade.burningskies.entities.bullets.PlayerPattern;
import deco2800.arcade.burningskies.screen.PlayScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PlayerShip extends Ship {
	
	private BulletPattern playerBullets;
	private final float MAXVELOCITY = 600;
	private PlayScreen screen;

	/**
	 * Construct a playable ship for the user(s).
	 * @ensure health && hitbox1 && hitbox2 > 0
	 */
	public PlayerShip(int health, Texture image, Vector2 position, PlayScreen screen) {
		super(health, image, position);
		this.screen = screen;
		hitboxScale = 0.25f; // lets the player 'just miss' bullets
	}
	
	/**
	 * Keeps the player within the screen bounds.(hopefully)
	 */
	public void onRender(float delta) {
		// reset
		velocity.set(0, 0);
    	if(Gdx.input.isKeyPressed(Keys.UP)) {
    		velocity.add(0, MAXVELOCITY);
    	}
    	if(Gdx.input.isKeyPressed(Keys.DOWN)) {
    		velocity.add(0, -MAXVELOCITY);
    	}
    	if(Gdx.input.isKeyPressed(Keys.LEFT)) {
    		velocity.add(-MAXVELOCITY, 0);
    	}
    	if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
    		velocity.add(MAXVELOCITY, 0);
    	}
    	position.add( velocity.x * delta, velocity.y * delta );
		if (position.x + getWidth() > BurningSkies.SCREENWIDTH) {
			position.x = BurningSkies.SCREENWIDTH - getImageWidth();
		}
    	if (position.x < 0) position.x = 0;
    	
		if (position.y + getHeight() > BurningSkies.SCREENHEIGHT) {
			position.y = BurningSkies.SCREENHEIGHT - getImageHeight();
		}
    	if (position.y < 0) position.y = 0;
		setX(position.x);
		setY(position.y);
		this.setZIndex(getStage().getActors().size); // this is silly, but no better way
    	shoot(delta);
	}
	
	/**
	 * Fire a shot.
	 */
	public void shoot(float delta) {
		if(playerBullets == null) {
			playerBullets = new PlayerPattern(this,screen);
			getStage().addActor(playerBullets);
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			if(!playerBullets.isFiring()) {
				playerBullets.start();
			}
		} else {
			if(playerBullets.isFiring()) playerBullets.stop();
		}
	}
}