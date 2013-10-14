package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.screen.PlayScreen;

//TODO abstract?
public class Enemy extends Ship {
	
	private PlayerShip player;

	private float speed = 200;

	private Vector2 playerDir = new Vector2();
	
	public Enemy(int health, Texture image, Vector2 pos, PlayScreen screen, PlayerShip player) {
		super(health, image, pos);
		
		this.player = player;
		position = pos;
		setWidth(getWidth()/3);
		setHeight(getHeight()/3);
	}
	
	public void onRender(float delta) {
		super.onRender(delta);
		move(delta);
	}
	
	private void move(float delta) {
		//home in to the player
		playerDir.set(player.getCenterX() - this.getCenterX(), player.getCenterY() - this.getCenterY());
		playerDir.nor();
		playerDir.mul(5);
		if((this.getRotation() + 90 - playerDir.angle()) > 0) {
			playerDir.rotate(-45);
		} else {
			playerDir.rotate(45);
		}
		velocity.add(playerDir);
		//normalise our velocity
    	velocity.nor();
    	velocity.mul(speed);
    	position.add( velocity.x * delta, velocity.y * delta );
    	setX(position.x);
		setY(position.y);
		setRotation(velocity.angle() - 90);
	}
}
