package deco2800.arcade.snakeLadderModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//import deco2800.arcade.snakeLadderModel.Dice;



public class GamePlayer {
	public static final float WIDTH = 20f; //How big is the player (its a square)
	public static final float INITIALSPEED = 60; // How fast is the player going at the start of a point
	public static final float SPEEDINCREMENT = 60; // How much is the player's speed each time throw the dice
	protected Texture player;
	private int coordinate=-1;
	//private int[] scores = new int[2];
	private int score;
	
	private Rectangle bounds = new Rectangle(); //The position (x,y) and dimensions (width,height) of the player
	Vector2 velocity = new Vector2(); // The current velocity of the player as x,y
	
	
	
	/**
	 * Basic constructor for player. Set position and dimensions to the default
	 */
	public GamePlayer() {
    	//loading player icon
		this.player =new Texture(Gdx.files.classpath("images/player.png"));
		getBounds().x = 0;
		getBounds().y = 0;
		getBounds().height = WIDTH;
		getBounds().width = WIDTH;
		score = 0; //initial score of the player
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
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
		getBounds().x = newPosition.x;
		getBounds().y = newPosition.y;
	}
	

	
	/**
	 * Move the player according to its current velocity over the given time period.
	 * @param time the time elapsed in seconds
	 * seconds of time = numbers of dice
	 */	
	public void move(float time) {
	  getBounds().x += time*velocity.x;
	  getBounds().y += time*velocity.y;
	 
//      setCoordinate(diceNumber);
	}
	
	
	public void moveUp() {
		//if player reacher left/right edge, it moves up one row
		velocity.x *= -1;
		getBounds().y = getBounds().y + 60f;		
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
    public void renderPlayer(SpriteBatch batch)
    {
    	batch.draw(this.player,getBounds().x,getBounds().y);   	
    }
    
    public void initializeVelocity() {
    	
    	if(getBounds().y==0 || getBounds().y==120 || getBounds().y==240 || getBounds().y==360 || getBounds().y==480){
			velocity.x = 60;
		}
		else if(getBounds().y==60 || getBounds().y==180 || getBounds().y==300 || getBounds().y==420 || getBounds().y==540)
		{
			velocity.x = -60;
		}
    	
	}
    
   public int getDnumber(int diceNumber){
    	coordinate+=diceNumber;
    	return coordinate;
    }
   
   public int newposition()
   {
	   return coordinate;
   }
   
   /*
   public void score(int winner){
		scores[winner]++;
	}

	public void initialScore(){
		scores[0] = 0;
		scores[1] = 0;
	}*/
   
   /**
    * Calling the score of this player
    * @return the score of the player
    */
   public int getScore(){
	   return this.score;
   }
   
   /**
    * Updating the score of this player based on the tile command
    * @param score the score gain after each move
    */
   public void setScore(int score){
	   this.score += score;
   }


}
