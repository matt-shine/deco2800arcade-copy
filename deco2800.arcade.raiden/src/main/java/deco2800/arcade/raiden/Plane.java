package deco2800.arcade.raiden;
import java.awt.Graphics;


abstract public class Plane {

	public int x, y;
	public int w, h;
	
	public Plane(){
		
	}
	/**
	 * The constructor of plane.
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Plane(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	/**
	 * Draw the image.
	 * @param g
	 */
	public abstract void drawMe(Graphics g);
}
