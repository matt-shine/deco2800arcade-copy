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

@SuppressWarnings("unused")
public class WorldController{
		
	private World world;
	private GameScreen level;
	private Ball ball;
	private WorldRenderer wRend;
	private DirectionLogic directionLogic; 
	
	boolean leftButtonClick = false; //left mouse click
	private float deceleration = 1f;
	private boolean notZero = true; //speed 
	private float newDirX, newDirY; 
	
	public int hole;
	boolean inHole;
	private int shotNum; //shot counter
	private int holeShots;
	private ArrayList<Integer> scoreCardCopy;
	
	public WorldController(World world, int level, ArrayList<Integer> scoreCard) {
		this.world = world; 
		this.ball = world.getBall();
		this.hole = level;
		this.inHole = false;
		this.shotNum = 0; //shot counter
		this.holeShots = 0;
		this.scoreCardCopy = scoreCard;
	}
	
	//left mouse button clicked
	public void leftKeyPressed() { 
	
	}
	//left mouse button released
	public void leftKeyReleased() {
		//buttons.get(buttons.put(Buttons.LEFT, false));
		leftButtonClick = true;
	}
	
	public void setHole(int level){
		this.hole = level;
	}
	public int getHole(){
		return this.hole;
	}
	public int getNumShots(){
		return this.shotNum;
	}
	public int getHoleShots(){
		return this.holeShots;
	}
	
	//get input and update ball gets power and dir from direcLogic class
	public void update(float delta, float power, Vector2 dir) {
		processInput(delta, power, dir); 
		if (ball.inHole == true){
			ball.getVelocity().x = 0;
			ball.getVelocity().y = 0;
			shotNum++;
			holeShots = shotNum;
			scoreCardCopy.add(holeShots);
			System.out.println(scoreCardCopy.toString());
			
			if(this.inHole == false){
				this.hole += 1; 
				this.inHole = true;
			}
		}		
		ball.update(delta);
	}
	
	//updates the speed and position of the ball when the mouse is clicked
	private void processInput(float delta, float power, Vector2 dir) {
		//if(power < 110) power = 110;
		if(leftButtonClick == false){
			ball.getVelocity().x = 0; 
			ball.getVelocity().y = 0;
			ball.hillX = 0f;
			ball.hillY = 0f;
		}
		//if left mouse clicked (and released)
		if(leftButtonClick == true){
			if(this.notZero){ //if speed doesn't equal zero
				power -= (5.0f * deceleration); //apply deceleration
				deceleration += 0.25;
				if(power <= 0 || ball.inWater){
					power = 0; 
					ball.hillX = 0f;
					ball.hillY = 0f;
					if (ball.inWater) shotNum++;
					ball.inWater = false;
					this.notZero = false; //speed is now zero, stop decelerating
				}
				
				//if(power > 150) power = 150; //cap speed (if not already)
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
				
				//apply velocity directional changes on wall/object contact
				if(ball.bounceX) ball.getVelocity().x = ((-(dir.x)) * power * delta ) - ball.getHillX(); 
				else ball.getVelocity().x = ((dir.x) * power * delta) + ball.getHillX(); 
				if(ball.bounceY) ball.getVelocity().y = ((-(dir.y)) * power * delta ) - ball.getHillY(); 
				else ball.getVelocity().y = ((dir.y) * power * delta) + ball.getHillY();
				
			} else { //ball has stopped and waiting for input
				//so reset everything for next move
				ball.bounceX = false; 
				ball.bounceY = false;  
				ball.hillX = 0f;
				ball.hillY = 0f;
				this.notZero = true;
				deceleration = 1;
				leftButtonClick = false;
				shotNum++; //increase shot counter
			}
		}
	}
}
