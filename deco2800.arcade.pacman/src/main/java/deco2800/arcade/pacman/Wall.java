package deco2800.arcade.pacman;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Wall extends Collideable{

	//private Texture wallTexture;
	//private Sprite wallSprite;
	private int wallType; //1:|, 2: -
	private float length; // The length of the wall
	
	//colour for wall
	private static final float renderColourRed = 0f;;
	private static final float renderColourGreen = 0f;
	private static final float renderColourBlue = 1f;
	private static final float renderColourAlpha = 1f;
	
	public Wall(List<Collideable> colList, int wallType, int xpos, int ypos, float length) {
		super(colList);
		this.wallType = wallType;
		x = xpos;
		y = ypos;
		if (wallType == 1) {
			width = 1;
			height = (int) length;
		} else {
			width = (int) length;
			height = 1;
		}
		// for wall drawing method
		this.length = length;
	}
	

	
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
	    /**if (wallType == 2 || wallType == 4) {
	    	// calculate location of end x and y coords based on rearranged pythagoras
	    	float diagDist = ((Double)(Math.sqrt(Math.pow(length, 2)/2))).floatValue();	    	
	    	if (wallType == 2) {
	    		shapeRenderer.line(x, y, x + diagDist, y + diagDist);
	    	} else {
	    		shapeRenderer.line(x, y, x + diagDist, y - diagDist);
	    	} **/
	    // if it's a horizontal or vertical wall
	    //} else {
	    if (wallType == 1) {
	    	shapeRenderer.line(x, y, x, y + length);
	    } else if (wallType == 2) {
	    	shapeRenderer.line(x, y, x + length, y);
	    }
	    
	}	
}

