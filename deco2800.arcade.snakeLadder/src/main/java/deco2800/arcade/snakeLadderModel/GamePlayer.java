package deco2800.arcade.snakeLadderModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;




public class GamePlayer {
	public static final float WIDTH = 20f; //How big is the playerTexture (its a square)
	public static final float INITIALSPEED = 60; // How fast is the playerTexture going at the start of a point
	public static final float SPEEDINCREMENT = 60; // How much is the playerTexture's speed each time throw the dice
	private Texture playerTexture;
	private String playerName;
	//private int coordinate=-1;
	private int positionIndex=0;
	//private int[] scores = new dint[2];
	private int score;
	private Rectangle bounds = new Rectangle(); //The position (x,y) and dimensions (width,height) of the playerTexture
	private Vector2 velocity = new Vector2(); // The current velocity of the playerTexture as x,y
	private boolean isAI;
	private int stopForNumOfRound = 0;
	
	/**
	 * Basic constructor for playerTexture. Set position and dimensions to the default
	 * @param isAI TODO
	 */
	public GamePlayer(String playerName, boolean isAI) {
		getBounds().x = 0;
		getBounds().y = 0;
		getBounds().height = WIDTH;
		getBounds().width = WIDTH;
		score = 0; //initial score of the playerTexture
		this.playerName = playerName;
		this.isAI = isAI;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	/**
	 * Modify the velocity of the playerTexture
	 * @param newVelocity the new velocity of playerTexture as x,y
	 */
	public void setVelocity(Vector2 newVelocity) {
		this.velocity.x = newVelocity.x;
		this.velocity.y = newVelocity.y;
	}
	
	/**
	 * Modify the position of the playerTexture
	 * @param newPosition the new position of the playerTexture as x,y
	 */
	public void setPosition(Vector2 newPosition) {
		getBounds().x = newPosition.x;
		getBounds().y = newPosition.y;
	}
		
	/**
	 * Move the playerTexture according to its current velocity over the given time period.
	 * @param time the time elapsed in seconds
	 * seconds of time = numbers of dice
	 */	
	public void move(float time) {
	  getBounds().x += time*getVelocity().x;
	  getBounds().y += time*getVelocity().y;
	 
//      setCoordinate(diceNumber);
	}
	
	/**
	 * If playerTexture reacher left/right edge, it moves up one row
	 */
	public void moveUp() {
		getVelocity().x *= -1;
		getBounds().y = getBounds().y + 60f;		
	}
	
	/**
	 * Reset the playerTexture to its initial position and velocity
	 */
	public void reset() {
		getVelocity().x = 0;
		getVelocity().y = 0;
	}
	
    /**
     * Render the playerTexture.
     *  
     */
    public void renderPlayer(SpriteBatch batch)
    {
    	batch.draw(this.getPlayerTexture(),getBounds().x,getBounds().y);   	
    }
    
    public void initializeVelocity() {  	
		// if it is even row
		if (((this.positionIndex)/10)%2 == 0)
		{
			// the vector of x-velocity goes to right direction 
			getVelocity().x = 60;
		}
		// if it is odd row
		else 
		{
			// the vector of x-velocity goes to left direction
			getVelocity().x = -60;
		}
	}
    
   public int getDnumber(int diceNumber){
    	positionIndex+=diceNumber;
    	if(positionIndex>=99)
    	{
    		positionIndex=99;
    	}
    	return positionIndex;
    }
   
   public int newposition()
   {
	   return positionIndex;
   }
   public int setNewPosition(int newpoint)
   {
	   positionIndex=newpoint;
	   return positionIndex;
   }
   
   
   /**
    * Calling the score of this playerTexture
    * @return the score of the playerTexture
    */
   public int getScore(){
	   return this.score;
   }
   
   /**
    * Updating the score of this playerTexture based on the tile command
    * @param score the score gain after each move
    */
   public void setScore(int score){
	   this.score += score;
   }

	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public Texture getPlayerTexture() {
		return playerTexture;
	}
	public void setPlayerTexture(String playerTexture) {
		//loading playerTexture icon
		this.playerTexture = new Texture(Gdx.files.classpath("images/"+playerTexture));
	}
	public Vector2 getVelocity() {
		return velocity;
	}

	public boolean isAI() {
		return isAI;
	}

	public void setAI(boolean isAI) {
		this.isAI = isAI;
	}

	public int getStopForNumOfRound() {
		return stopForNumOfRound;
	}

	public void setStopForNumOfRound(int stopForNumOfRound) {
		this.stopForNumOfRound = stopForNumOfRound;
	}

}
