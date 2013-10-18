package deco2800.arcade.minigolf;

import com.badlogic.gdx.math.Polygon; 
import com.badlogic.gdx.math.Vector2; 

/* Holds the position, size, speed and bounds of the ball object */

public class Ball {
	
	static final float SIZE = 8f;  
	public Boolean bounceX = false;
	public Boolean bounceY = false;
	public Boolean bounceDiagX = false;
	public Boolean bounceDiagY = false;
	
	public Float hillX;
	public Float hillY;
	public Boolean inHole = false;
	public Boolean inWater = false;
	
	
	Vector2 position = new Vector2();  
	Vector2 velocity = new Vector2(); 
	Polygon bounds;  
	
	public Ball(Vector2 position) { 
		this.hillX = 0f;
		this.hillY = 0f;
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
	public Float getHillX() {
		return this.hillX;		
	}
	
	public Float getHillY() {
		return this.hillY;
	}
	
	public void setHillX(Float accel) {
		this.hillX = accel;		
	}
	
	public void setHillY(Float accel) {
		this.hillY = accel;
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
