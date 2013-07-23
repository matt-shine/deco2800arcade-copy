package deco2800.arcade.pong;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ball {

	public static final float WIDTH = 20f; //How big is the ball (its a square)
	public static final float INITIALSPEED = 200; // How fast is the ball going at the start of a point
	public static final float BOUNCEINCREMENT = 1.1f; // How much does the ball speed up each time it gets hit
	
	Rectangle bounds = new Rectangle();
	Vector2 velocity = new Vector2();
	
	public Ball() {
//		this.position.x = position.x;
//		this.position.y = position.y;
		bounds.x = Pong.SCREENWIDTH/2 - Ball.WIDTH/2;
		bounds.y = Pong.SCREENHEIGHT/2 - Ball.WIDTH/2;
		bounds.height = WIDTH;
		bounds.width = WIDTH;
	}

	public void setVelocity(Vector2 newVelocity) {
		this.velocity.x = newVelocity.x;
		this.velocity.y = newVelocity.y;
	}
	
	public void setPosition(Vector2 newPosition) {
//		this.position.x = newPosition.x;
//		this.position.y = newPosition.y;
		bounds.x = newPosition.x;
		bounds.y = newPosition.y;
	}
	
	public void bounceX() {
		//This is naive
		velocity.x *= -1;
		velocity.mul(BOUNCEINCREMENT);
	}

	public void move(float time) {
//		position.x += time*velocity.x;
//		position.y += time*velocity.y;
		bounds.x += time*velocity.x;
		bounds.y += time*velocity.y;
	}

	public void bounceY() {
		velocity.y *= -1;
	}
	
	public void reset() {
		velocity.x = 0;
		velocity.y = 0;
		
	}
	
	public void randomizeSpeed() {
		int xFactor = (int) (100f + Math.random()*90f);
		int yFactor = (int) Math.sqrt((200*200) - (xFactor*xFactor));
		System.out.println("Velocity: " + xFactor + ", " + yFactor);
		velocity.x = xFactor;
		velocity.y = yFactor;
	}
}
