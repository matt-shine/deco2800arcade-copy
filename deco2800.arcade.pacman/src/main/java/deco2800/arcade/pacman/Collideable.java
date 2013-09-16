package deco2800.arcade.pacman;

import java.util.List;
/**
 * An abstract class for objects that can be collided with
 *
 */
public abstract class Collideable {

	//the coordinates of the bottom left corner of the collideable object
	protected float x; 
	protected float y;
	protected int height; 
	protected int width;
	
	public Collideable(List<Object> colList) {
		colList.add(this);
	}

	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	/**
	 * Overrides toString() so that trying to print the list won't crash the program
	 */
	@Override
	public String toString() {
		return new String("Object of " + this.getClass() + " at (" + x + "," + y + "), width=" + width + ", height=" + height);
	}
}
