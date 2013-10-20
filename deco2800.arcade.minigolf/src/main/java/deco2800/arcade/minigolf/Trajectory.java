package deco2800.arcade.minigolf;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * This class draws the direction trajectory which the ball will travel in 
 * This class overrides some stage/actor functions to draw
 */

public class Trajectory extends Actor {
	
	public Ball ball; 
	public World world;
	private DirectionValues controller; 
	private Sprite trajectorySprite;
	
	public Vector2 startVelocity = new Vector2();  
	
	private int trajectoryPoints = 5; //number of trajectory images (circles) to appear
	private float timeSeparation = 0.5f; //the size of separation of the trajectory images
	
	public Trajectory(DirectionValues control, Sprite trajectorySprite, World world){
		this.controller = control; 
		this.trajectorySprite = trajectorySprite;
		this.world = world;
	}
	
	@Override //update the trajectory position that will appear on screen
	public void act(float delta){
		super.act(delta); 
		startVelocity.set(controller.getPower(), 0f); 
		startVelocity.rotate(controller.getAngle());
	}
	
	@Override //draw the trajectory to the screen
	public void draw(SpriteBatch batch, float parentAlpha) {
		float imgSpace = 0f; 
		//width and height of the trajectory image
		float width = 8; 
		float height = 8; 		
		float timeSep = this.timeSeparation;
		//loop through and draw the trajectory circles to screen
		for(int i = 0; i < trajectoryPoints; i++){
			//get the x and y ball position to draw the trajectory at
			float x = (width) + this.getX(imgSpace);
			float y = (height) + this.getY(imgSpace);
			
			//don't draw the first circle image (it's on top of the ball)
			if(i != 0) {
				batch.draw(trajectorySprite, (x-8), (y-8), width, height);
			}			
			imgSpace += timeSep;
		}
	}
	/* get the balls x and y position */
	public float getX(float space){
		ball = world.getBall();
		return startVelocity.x * space + ball.getPosition().x ; 
	}
	public float getY(float space){
		ball = world.getBall();
		return startVelocity.y * space + ball.getPosition().y; 
	}

}
