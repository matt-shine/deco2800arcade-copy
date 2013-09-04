package deco2800.arcade.snakeLadder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GamePlayer {
	public static final float WIDTH = 20f; //How big is the player (its a square)
	public static final float INITIALSPEED = 60; // How fast is the player going at the start of a point
	public static final float SPEEDINCREMENT = 60; // How much is the player's speed each time throw the dice
	//private Texture player;
	
	
	Rectangle bounds = new Rectangle(); //The position (x,y) and dimensions (width,height) of the player
	Vector2 velocity = new Vector2(); // The current velocity of the player as x,y
	
	
	
	/**
	 * Basic constructor for player. Set position and dimensions to the default
	 */
	public GamePlayer() {
		bounds.x = 0;
		bounds.y = 0;
		bounds.height = WIDTH;
		bounds.width = WIDTH;
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
	  bounds.x += time*velocity.x;
	  bounds.y += time*velocity.y;
	}
	
	public void moveUp() {
		//if player reacher left/right edge, it moves up one row
		velocity.x *= -1;
		bounds.y = bounds.y + 60;		
	}
	
	/**
	 * Reset the player to its initial position and velocity
	 */
	public void reset() {
		velocity.x = 0;
		velocity.y = 0;
	}
	
	
    
    /**
     * Render the player.
     * @param 
     */
    public void render(ShapeRenderer shapeRenderer)
    {
    	//loading player icon
    	//player =new Texture(Gdx.files.classpath("assets/player.png"));
    	shapeRenderer.filledRect(this.bounds.x,
                this.bounds.y,
                this.bounds.width,
                this.bounds.height);
    	
    }
    
    public void initializeVelocity() {
		velocity.x = 60;
		velocity.y=0;
	}
}
