package deco2800.arcade.minigolf;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Mouse;
 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.*;
import com.badlogic.gdx.math.Vector2;

/** 
 * WorldController handles the ball velocity and values to be placed into score-card 
 * This class updates the balls direction and velocity as well as keeps track of the 
 * current hole and triggers the end of hole event within GameScreen.java
 */

@SuppressWarnings("unused")
public class WorldController{
		
	private World world;
	private GameScreen level;
	private Ball ball;
	private WorldRenderer wRend;
	private DirectionLogic directionLogic; 
	
	private boolean leftButtonClick = false; //left mouse click
	private float deceleration = 1f;
	private boolean notZero = true; //for power
	private float newDirX, newDirY; 
	
	private int hole;
	private boolean inHole;
	private int shotNum; //shot counter
	private int holeShots; //shots for score card
	private ArrayList<Integer> scoreCardCopy = new ArrayList<Integer>();
	
	
	public WorldController(World world, int level, ArrayList<Integer> scoreCard) {
		this.world = world; 
		this.ball = world.getBall();
		this.hole = level;
		this.inHole = false;
		this.shotNum = 0; //shot counter
		this.holeShots = 0;
		this.scoreCardCopy = scoreCard;
	}
	
	/* True if the left mouse button has been pressed */
	public void leftKeyReleased() {
		leftButtonClick = true;
	}
	/* set the current hole that's being used */
	public void setHole(int level){
		this.hole = level;
	}
	/* get the current hole */
	public int getHole(){
		return this.hole;
	}
	/* get the number of shots that have been used */
	public int getNumShots(){
		return this.shotNum;
	}
	/* get number of shots used in score-card */
	public int getHoleShots(){
		return this.holeShots;
	}
	
	/* gets power and direction the ball is to move in from GameScreen.java 
	 * Updates ball position
	 */
	public void update(float delta, float power, Vector2 dir) {
		processInput(delta, power, dir); 
		if (ball.inHole){
			ball.getVelocity().x = 0;
			ball.getVelocity().y = 0;
			shotNum++;
			holeShots = shotNum;
			//add all scores to score-card up to hole 18
			if(this.hole != 19){
			scoreCardCopy.add(holeShots);
			}			
			if(!this.inHole){
				this.hole += 1; 
				this.inHole = true;
			}
		}		
		ball.update(delta);
	}
	
	/* Using the power and direction, update the balls velocity
	 * and reverse in case of collision.
	 */
	private void processInput(float delta, float power, Vector2 dir) {
		float newPower = power;
		//if player left clicks
		if(leftButtonClick == true){
			if(this.notZero){ //if speed doesn't equal zero
				newPower -= (5.0f * deceleration); //apply deceleration
				deceleration += 0.25;
				//if power is less than 0 make it zero so ball doesn't move backwards
				if(newPower <= 0 || ball.inWater){ 
					//speed is now zero, stop decelerating
					newPower = 0; 
					if (ball.inWater){ //increment shot counter by 1 if contacts water
						shotNum++; 
					}
					ball.inWater = false;
					this.notZero = false; 
				}
				//diagonal block direction changes
				diagFixes(dir);
				
				//apply velocity and directional changes to the ball
				if(ball.bounceX){
					ball.getVelocity().x = ((-(dir.x)) * newPower * delta ); 
				} else {
					ball.getVelocity().x = ((dir.x) * newPower * delta); 
				}
				if(ball.bounceY){
					ball.getVelocity().y = ((-(dir.y)) * newPower * delta ) ; 
				}else {
					ball.getVelocity().y = ((dir.y) * newPower * delta);
				}
				
				//ball has stopped moving so reset everything for next mouse click.
			} else { 
				ball.bounceX = false; 
				ball.bounceY = false;  
				this.notZero = true;
				deceleration = 1;
				ball.getVelocity().x = 0; 
				ball.getVelocity().y = 0;
				leftButtonClick = false;
				shotNum++; //increase shot counter
			}
		}
	}
	
	/* Check if ball is in contact with diagonal blocks, apply direction changes */
	private void diagFixes(Vector2 dir){
		if(ball.bounceDiagX){
		     newDirX = dir.x;
		     newDirY = dir.y;     
		     dir.x = newDirY;
		     dir.y = newDirX;
		     ball.bounceDiagX = false;
		    }
		else if(ball.bounceDiagY){
		     newDirX = dir.x;
		     newDirY = dir.y;     
		     dir.x = newDirY*(-1);
		     dir.y = newDirX*(-1);
		     ball.bounceDiagY = false;
		}
	}
}
