package deco2800.arcade.snakeLadder;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * A basic implementation of the player class, responsible for the behaviour of the player
 * including movement,behaviors and positioning
 * @author wang qi
 * @author s43146884
 */
public class Player {

	public static final float INITIALSPEED = 0; // How fast is the player going at the start of a point
	public static final float SPEEDINCREMENT = 60; // How much is the player's speed each time throw the dice
	
	Rectangle bounds = new Rectangle(); //The position (x,y) and dimensions (width,height) of the player
	Vector2 velocity = new Vector2(); // The current velocity of the player as x,y
	
	
	
	/**
	 * Basic constructor for player. Set position and dimensions to the default
	 */
	public Player() {
		bounds.x = 0;
		bounds.y = 600;
		bounds.height = 50;
		bounds.width = 35;
	}

	/**
	 * Modify the velocity of the player
	 * @param newVelocity the new velocity of player as x,y
	 */
	public void setVelocity(Vector2 newVelocity) {
		this.velocity.x = newVelocity.x;
		this.velocity.y = newVelocity.y;
	}
	
	/**
	 * Modify the position of the player
	 * @param newPosition the new position of the player as x,y
	 */
	public void setPosition(Vector2 newPosition) {
		bounds.x = newPosition.x;
		bounds.y = newPosition.y;
	}
	

	
	/**
	 * Move the player according to its current velocity over the given time period.
	 * @param time the time elapsed in seconds
	 * seconds of time = numbers of dice
	 */	
	public void move(float time) {
	  //every time throw a dice, player get a speed of 60
	  velocity.x = 60;
	  
	  //while player not reaches the end point
	  while((bounds.x == 0 && bounds.y == 0) == false){
		  //horizontally move the player to position according to time(dice number)
		  bounds.x += time*velocity.x;
		
		  //if player reaches left or right bound, it goes up one row and changes X direction
		  if ((bounds.x == 0 && bounds.y != 600) || (bounds.x == 600))
		  {
			  bounds.y = bounds.y - 60; 
			  velocity.x *= -1;		
		  }
	  }
	  
	  //reset player's velocity when it reaches the position
	  velocity.x = 0;
	  velocity.y = 0;
	}
	
	/**
	 * Reset the player to its initial position and velocity
	 */
	public void reset() {
		velocity.x = 0;
		velocity.y = 0;
		bounds.x = 0;
		bounds.y = 600;
	}
	
	
    
    /**
     * Render the player.
     * @param shapeRenderer The current {@link ShapeRenderer} instance.
     */
    public void render(ShapeRenderer shapeRenderer)
    {
       
        shapeRenderer.filledRect(this.bounds.x,
                                 this.bounds.y,
                                 this.bounds.width,
                                 this.bounds.height);
    }
	
	
}
