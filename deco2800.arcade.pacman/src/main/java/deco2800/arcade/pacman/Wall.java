package deco2800.arcade.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Wall {

	//private Texture wallTexture;
	//private Sprite wallSprite;
	private int wallType; //1:|, 2:/, 3:-, 4:\
	private float length; // The length of the wall
	//the coordinates of the bottom left corner of the wall
	private float x; 
	private float y;
	
	//colour for wall
	private static final float renderColourRed = 0f;;
	private static final float renderColourGreen = 0f;
	private static final float renderColourBlue = 1f;
	private static final float renderColourAlpha = 1f;
	
	public Wall(int wallType, float xpos, float ypos, float length) {
		this.wallType = wallType;
		x = xpos;
		y = ypos;
		this.length = length;
	}
	
//	public void create() {
//		wallTexture = new Texture(Gdx.files.internal("testwall.png"));
//		wallSprite = new Sprite(wallTexture);
//	}
	
	// This is no longer necessary, since we're not using sprites anymore
	// or at least I'm not
//	public void render(SpriteBatch batch) {
//		//batch.draw(wallTexture,100,100);
//	}
	
	
	/**
	 * Render the wall
	 */
	public void render(ShapeRenderer shapeRenderer)
	{
		// set colour to blue
	    shapeRenderer.setColor(renderColourRed,
	                           renderColourGreen,
	                           renderColourBlue,
	                           renderColourAlpha);
	    // draw wall based on type. Arguments for shapeRenderer.line are 
	    // initial coords, end coords, and the line is drawn between. Currently 
	    // not sure how to do width of line, might need different approach like a Rectangle
	    // if it's a diagonal wall type
	    if (wallType == 2 || wallType == 4) {
	    	// calculate location of end x and y coords based on rearranged pythagoras
	    	float diagDist = ((Double)(Math.sqrt(Math.pow(length, 2)/2))).floatValue();	    	
	    	if (wallType == 2) {
	    		shapeRenderer.line(x, y, x + diagDist, y + diagDist);
	    	} else {
	    		shapeRenderer.line(x, y, x + diagDist, y - diagDist);
	    	}
	    // if it's a horizontal or vertical wall
	    } else {
	    	if (wallType == 1) {
	    		shapeRenderer.line(x, y, x, y + length);
	    	} else {
	    		shapeRenderer.line(x, y, x + length, y);
	    	}
	    }
	}	
}

