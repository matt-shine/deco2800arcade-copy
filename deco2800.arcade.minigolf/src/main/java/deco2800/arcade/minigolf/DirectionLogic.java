package deco2800.arcade.minigolf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/* 
 * This class controls the logic for the direction trajectory
 * Essentially it provides the speed for the ball and the direction/length
 * to draw the trajectory
 */

public class DirectionLogic {
	
	private DirectionValues directController;
	final Vector2 ballPos = new Vector2(); //holds ball position
	final Vector2 currentPos = new Vector2(); //holds current position
	public final Vector2 temp = new Vector2(); //holds updated trajectory
	
	public DirectionLogic(DirectionValues control, Vector2 ballPos){
		this.directController = control; 
		this.ballPos.set(ballPos);
	}
	/* update the line position based upon mouse position */
	public void update(){
		//get mouse x & y input
		float mouseX = Gdx.input.getX(); 
		float mouseY = Gdx.input.getY();
	
		currentPos.set(mouseX, Gdx.graphics.getHeight() - mouseY);
		//update the current position based on ball position and mouse
		temp.set(currentPos).sub(ballPos);
		//System.out.println("x: "+ temp.x);
		//System.out.println("y: "+ temp.y);
		//cap the speed the ball can travel at
		if(temp.x > 200)  temp.x = 200.0f;
		if(temp.x < -200) temp.x = -200.0f;
		if(temp.y > 200)  temp.y = 200.0f;
		if(temp.y < -200) temp.y = -200.0f;
		
		temp.mul(-1f); //set point of origin for trajectory (so it's on ball not mouse)
		
		//update the angle and power based on the mouse position
		directController.angle = temp.angle();
		
		//cap trajectory length 
		if(temp.len() >= 160){
			directController.power = 160f;
		} else{
			directController.power = temp.len();
		}		
	}
	
	public Vector2 getDirection(){
		return temp;
	}
	public float getPower(){
		return directController.power;
	}

}
