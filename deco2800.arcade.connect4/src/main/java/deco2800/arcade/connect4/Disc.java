package deco2800.arcade.connect4;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Disc {

	public static final float WIDTH = 20f; //How big is the ball (its a square)
	public static final float INITIALSPEED = 200; // How fast is the ball going at the start of a point
	public static final float BOUNCEINCREMENT = 1.1f; // How much does the ball speed up each time it gets hit
	
	Rectangle bounds = new Rectangle(); //The position (x,y) and dimensions (width,height) of the ball
	Vector2 velocity = new Vector2(); // The current velocity of the ball as x,y
	
	public int currentPos = 0;
	
	public boolean isSetPlayer1 = false;
	public boolean isSetPlayer2 = false;
	
	public static final int EMPTY = 0;
	public static final int PLAYER1 = 1;
	public static final int PLAYER2 = 2;
	
	private int state = Disc.EMPTY;
	
	private float renderColourRed;
    private float renderColourGreen;
    private float renderColourBlue;
    private float renderColourAlpha;
	
	/**
	 * Basic constructor for Ball. Set position and dimensions to the default
	 */
	public Disc() {
		bounds.x = 10;
		bounds.y = 10;
		//bounds.x = Connect4.SCREENWIDTH/2 - Disc.WIDTH/2;
		//bounds.y = Connect4.SCREENHEIGHT/2 - Disc.WIDTH/2;
		bounds.height = WIDTH;
		bounds.width = WIDTH;
	}
	
	public void setState( int state ) {
		this.state = state;
		switch ( this.state ) {
			case Disc.EMPTY: 
				setColor(0,0,0,1);
				break;
			case Disc.PLAYER1: 
				setColor(1,0,0,1);
				break;
			case Disc.PLAYER2: 
				setColor(0,0,1,1);
				break;
		}
	}
	
	public int getState() {
		return this.state;
	}
	
	/**
	 * Modify the position of the ball
	 * @param newPosition the new position of the ball as x,y
	 */
	public void setPosition(float newx, float newy) {
		bounds.x = newx;
		bounds.y = newy;
	}

	/**
	 * Move the ball according to its current velocity over the given time period.
	 * @param time the time elapsed in seconds
	 */
	public void moveLeft(float time) {
		if (currentPos > 0) {
			bounds.x -= bounds.width*2 + 5;
			currentPos -= 1;
		} else {
			//cannot move left - at position 0 (leftmost position)
		}
	}
	
	public void moveRight(float time){
		if (currentPos < (Table.TABLECOLS - 1)) {
			bounds.x += bounds.width*2 + 5;
			currentPos += 1;
		} else {
			//cannot move right - at max position (rightmost position)
		}
	}
	
	/**
	 * Reset the ball to its initial position and velocity
	 */
	public void reset() {
		bounds.x = 20;
		bounds.y = 20;
	}
	
	/**
     * Set the colour of the rendered ball.
     * @param r Red (0-1)
     * @param g Green (0-1)
     * @param b Blue (0-1)
     * @param a Alpha (0-1)
     */
    public void setColor(float r, float g, float b, float a)
    {
        renderColourRed = r;
        renderColourGreen = g;
        renderColourBlue = b;
        renderColourAlpha = a;
    }
    
    /**
     * Render the ball.
     * @param shapeRenderer The current {@link ShapeRenderer} instance.
     */
    public void render(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.setColor(renderColourRed,
                               renderColourGreen,
                               renderColourBlue,
                               renderColourAlpha);
        shapeRenderer.filledCircle(this.bounds.x, this.bounds.y, this.bounds.width);
    }
	
}
