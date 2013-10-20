package deco2800.arcade.minigolf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/* 
 * This class controls the logic for the direction trajectory
 * Essentially it calculates the speed for the ball based on the length of the trajectory
 * and the direction the ball will travel based on the angle of the trajectory
 */

public class DirectionLogic {
	
	private DirectionValues directController;
	final Vector2 ballPos = new Vector2(); //holds ball position
	Vector2 currentPos = new Vector2(); //holds current position
	public Vector2 temp = new Vector2(); //holds updated trajectory
	
	public DirectionLogic(DirectionValues control, Vector2 ballPos){
		this.directController = control; 
		this.ballPos.set(ballPos);
	}
	/* update the line position based upon mouse position */
	public void update(){
		
		//get mouse x & y input
		float mouseX = Gdx.input.getX(); 
		float mouseY = Gdx.input.getY();
	
		//update the current position based on ball position and mouse
		currentPos.set(mouseX, Gdx.graphics.getHeight() - mouseY);
		temp.set(currentPos).sub(ballPos);
		
		//cap the speed the ball can travel at
		if(temp.x > 200)  temp.x = 200.0f;
		if(temp.x < -200) temp.x = -200.0f;
		if(temp.y > 200)  temp.y = 200.0f;
		if(temp.y < -200) temp.y = -200.0f;
		
		temp.mul(-1f); //set point of origin for trajectory (so it's on ball not mouse)
		
		//update the angle and power based on the mouse position
		directController.setAngle(temp.angle());
		
		//cap trajectory length 
		if(temp.len() >= 160){
			directController.setPower(160f);
		} else{
			directController.setPower(temp.len());
		}		
	}
	
	/* functions to get the direction and power implied by the trajectory */
	public Vector2 getDirection(){
		return temp;
	}
	public float getPower(){
		return directController.getPower();
	}

}
