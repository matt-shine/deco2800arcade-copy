package deco2800.arcade.minigolf;

import com.badlogic.gdx.math.Polygon; 
import com.badlogic.gdx.math.Vector2; 


/**
 * The class responsible for the majority of the properties of a ball. There 
 * is usually only one instance of this class used throughout the code. 
 * Behaves very similar to a Block1 class object.
 * 
 * Most important variables are its size and bounds (used for collision
 * detection).
 *
 *
 */
public class Ball {
	
	static final float SIZE = 8f; 
	// Boolean variables used for the bouncing effect of the ball.
	// When one of them is true that axis is reversed.
	public Boolean bounceX = false;
	public Boolean bounceY = false;
	public Boolean bounceDiagX = false;
	public Boolean bounceDiagY = false;
	
	// Boolean variables used for determining if the ball is in
	// the hole or the water.
	public Boolean inHole = false;
	public Boolean inWater = false;
	
	Vector2 position = new Vector2();  
	Vector2 velocity = new Vector2(); 
	Polygon bounds;  
	
	public Ball(Vector2 position) { 
		
		this.position = position; 
		this.bounds = new Polygon(new float[]{SIZE/3,SIZE/3,SIZE/3,2*(SIZE/3),2*(SIZE/3),
				2*(SIZE/3),2*(SIZE/3),SIZE/3});
		this.bounds.setPosition(position.x, position.y);
		
	}
	/* update the balls position and new bounds */
	public void update(float delta){
		position.add(velocity.cpy().mul(delta));
		this.bounds.setPosition(position.x, position.y);
	}
	public Vector2 getVelocity() {
		return this.velocity;
	}	
	
	public Polygon getBounds(){ 
		return this.bounds; 
	}	
	public Vector2 getPosition(){
		this.position.x = this.bounds.getX();
		this.position.y = this.bounds.getY();
		return this.position; 
	}	
	public void setPosition(float x, float y){
		this.position = new Vector2(x,y);
		this.bounds.setPosition(x, y);
	}

}
