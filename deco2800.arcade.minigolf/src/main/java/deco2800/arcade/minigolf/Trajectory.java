package deco2800.arcade.minigolf;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/*
 * This class draws the direction trajectory from the ball, which the ball will travel in 
 * This class overrides some stage/actor functions
 */

public class Trajectory extends Actor {
	
	public Ball ball; 
	public World world;
	private DirectionValues controller; 
	private Sprite trajectorySprite;
	
	public Vector2 startVelocity = new Vector2();  
	
	private int trajectoryPoints = 5; //number of images
	private float timeSeparation = 0.5f; //the size of separation of images
	
	public Trajectory(DirectionValues control, Sprite trajectorySprite, World world){
		this.controller = control; 
		this.trajectorySprite = trajectorySprite;
		this.world = world;
	}
	
	@Override //update
	public void act(float delta){
		super.act(delta); 
		startVelocity.set(controller.getPower(), 0f); 
		startVelocity.rotate(controller.getAngle());
	}
	
	@Override //render the trajectory direction
	public void draw(SpriteBatch batch, float parentAlpha) {
		float t = 0f; 
		//width and height of the trajectory image
		float width = 8; 
		float height = 8; 		
		float timeSep = this.timeSeparation;
		//loop through and draw the trajectory images
		for(int i = 0; i < trajectoryPoints; i++){
			//get the x and y positions to draw the trajectory
			float x = (width) + this.getX(t);
			float y = (height) + this.getY(t);
			
			//don't draw the first image (under ball)
			if(i != 0) {
				batch.draw(trajectorySprite, (x-8), (y-8), width, height);
			}			
			t += timeSep;
		}
	}
	//get the ball's current position
	public float getX(float a){
		ball = world.getBall();
		return startVelocity.x * a + ball.getPosition().x ; 
	}
	public float getY(float a){
		ball = world.getBall();
		return startVelocity.y * a + ball.getPosition().y; 
	}

}
