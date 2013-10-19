package deco2800.arcade.connect4;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Disc {

	public static final float WIDTH = 20f;
	
	Rectangle bounds = new Rectangle(); //The position (x,y) and dimensions (width,height) of the disc
	
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
	 * Setup the disc.
	 * 
	 * Set position and dimensions to the default.
	 */
	public Disc() {
		bounds.x = 10;
		bounds.y = 10;
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
	
	public void setPosition(float newx, float newy) {
		bounds.x = newx;
		bounds.y = newy;
	}
	
	public Integer moveLeft() {
		if (currentPos > 0) {
			currentPos -= 1;
			return currentPos;
		} else {
			return -1;
			//cannot move left - at position 0 (leftmost position)
		}
	}
	
	public Integer moveRight(){
		if (currentPos < (Table.TABLECOLS - 1)) {
			currentPos += 1;
			return currentPos;
		} else {
			return -1;
			//cannot move right - at max position (rightmost position)
		}
	}
	
	/**
	 * Reset the disc to the inital position.
	 */
	public void reset() {
		bounds.x = 20;
		bounds.y = 20;
	}
	
    public void setColor(float r, float g, float b, float a)
    {
        renderColourRed = r;
        renderColourGreen = g;
        renderColourBlue = b;
        renderColourAlpha = a;
    }

    public void render(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.setColor(renderColourRed,
                               renderColourGreen,
                               renderColourBlue,
                               renderColourAlpha);
        shapeRenderer.filledCircle(this.bounds.x, this.bounds.y, this.bounds.width);
    }
	
}
