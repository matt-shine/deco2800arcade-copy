package deco2800.arcade.snakeLadderModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

//import deco2800.arcade.snakeLadderModel.Dice;



public class GamePlayer {
	public static final float WIDTH = 20f; //How big is the playerTexture (its a square)
	public static final float INITIALSPEED = 60; // How fast is the playerTexture going at the start of a point
	public static final float SPEEDINCREMENT = 60; // How much is the playerTexture's speed each time throw the dice
	private Texture playerTexture;
	private String playerName;
	private int coordinate=-1;
	//private int[] scores = new int[2];
	private int score;
	private Rectangle bounds = new Rectangle(); //The position (x,y) and dimensions (width,height) of the playerTexture
	private Vector2 velocity = new Vector2(); // The current velocity of the playerTexture as x,y
	
	
	public GamePlayer()
	{
	}
	/**
	 * Basic constructor for playerTexture. Set position and dimensions to the default
	 */
	public GamePlayer(String playerName) {
		getBounds().x = 0;
		getBounds().y = 0;
		getBounds().height = WIDTH;
		getBounds().width = WIDTH;
		score = 0; //initial score of the playerTexture
		this.playerName = playerName;
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
	
	
	public void moveUp() {
		//if playerTexture reacher left/right edge, it moves up one row
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
     * @param 
     */
    public void renderPlayer(SpriteBatch batch)
    {
    	batch.draw(this.getPlayerTexture(),getBounds().x,getBounds().y);   	
    }
    
    public void initializeVelocity() {
    	
//    	if(getBounds().y==0 || getBounds().y==120 || getBounds().y==240 || getBounds().y==360 || getBounds().y==480){
//			velocity.x = 60;
//		}
//		else if(getBounds().y==60 || getBounds().y==180 || getBounds().y==300 || getBounds().y==420 || getBounds().y==540)
//		{
//			velocity.x = -60;
//		}
    	
		// if it is even row
		if (getBounds().y % 120 == 0)
		{
			getVelocity().x = 60;
		}
		// if it is odd row
		else 
		{
			getVelocity().x = -60;
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

}
