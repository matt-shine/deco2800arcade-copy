package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.screen.PlayScreen;

//TODO abstract?
public class Enemy extends Ship {
	
	private PlayerShip player;

	private float speed = 200;
	
	private Vector2 currentDirVel = new Vector2();
	
	// Adjust the variable below to change the max speed
	private Vector2 maxDirVel = new Vector2(speed, speed);
	
	private Vector2 dirAccel = new Vector2();
	
	private float accelIntensity;
	
	private boolean homing;

	private long points;

//	private Vector2 playerDir = new Vector2();
	
	public Enemy(int health, Texture image, Vector2 pos, Vector2 dir, PlayScreen screen, PlayerShip player, long points) {
		super(health, image, pos);
		this.player = player;
		position = pos;
		currentDirVel = dir;
		this.points = points;
		
		dirAccel.x = 0;
		dirAccel.y = 0;
		homing = true;
		accelIntensity = (float) 0.95;
		
		setWidth(getWidth());
		setHeight(getHeight());
	}
	
	public void onRender(float delta) {
		super.onRender(delta);
		move(delta);
	}
	
	public long getPoints() {
		return points;
	}
	
	private void move(float delta) {
		
//		playerDir.set(player.getCenterX() - this.getCenterX(), player.getCenterY() - this.getCenterY());
//		playerDir.nor();
//		playerDir.mul(5);
//		if((this.getRotation() + 90 - playerDir.angle()) > 0) {
//			playerDir.rotate(-45);
//		} else {
//			playerDir.rotate(45);
//		}
//		velocity.add(playerDir);
		//normalise our velocity
//    	velocity.nor();
//    	velocity.mul(speed);
//    	position.add( velocity.x * delta, velocity.y * delta );
		
		//home in to the player
		if(homing) {
//			System.out.println("Player x : " + player.getX() + ", y: " + player.getY());
			dirAccel.x = (player.getCenterX() - position.x)/accelIntensity;
			dirAccel.y = (player.getCenterY() - position.y)/accelIntensity;
//			System.out.println("accel x: " + dirAccel.x + ", accel y: " + dirAccel.y);
			currentDirVel.x += dirAccel.x * delta;
			currentDirVel.y += dirAccel.y * delta;
			
			if(Math.abs(currentDirVel.x) > maxDirVel.x) {
				if (currentDirVel.x > 0){
					currentDirVel.x = maxDirVel.x;
				}
				else {
					currentDirVel.x = (-1) * maxDirVel.x;
				}
			}
			if(Math.abs(currentDirVel.y) > maxDirVel.y) {
				if(currentDirVel.y > 0) {
					currentDirVel.y = maxDirVel.y;
				}
				else {
					currentDirVel.y =  (-1) * maxDirVel.y;
				}
				
			}
			
		}
		
		position.x += currentDirVel.x * delta;
		position.y += currentDirVel.y * delta;
		
    	setX(position.x);
		setY(position.y);
		setRotation(currentDirVel.angle() - 90);
	}
}
