package deco2800.arcade.raiden;
import java.awt.Graphics;


public abstract class Bullet {
	
	public int x;     
	public int y;
	public int width;
	public int heigth;
	
	/**
	 * Constructor for bullet.
	 * @param x
	 * @param y
	 * @param width
	 * @param heigth
	 */
	public Bullet(int x, int y, int width, int heigth) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.heigth = heigth;
	}
	public abstract void bulletMove();
	public abstract void drawMe(Graphics g);
	
}
