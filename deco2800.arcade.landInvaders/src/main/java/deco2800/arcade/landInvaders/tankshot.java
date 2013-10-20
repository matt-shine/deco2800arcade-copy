package deco2800.arcade.landInvaders;

import java.awt.Color;
import java.awt.Graphics;

public class tankshot {

	private int p_x;
	private int p_y;
	private int width;
	private int height;
	private boolean tankShotState = false;

	/**
	 * @param x x-coordinate of player sprite
	 * @param y y-coordinate of player sprite
	 */
	public tankshot(int x, int y) {
		p_x = x +20;
		p_y = y;
		width = 5;
		height = 10;
		tankShotState = true;
	}
	
	/**
	 * @return true if tankshot is create successfully, false otherwise
	 */
	public boolean getTankShotState()
	{
		return tankShotState;
	}

	/**
	 * @param g the graphic of the tank shot sprite
	 */
	public void drawshot(Graphics g) {

		g.setColor(Color.green);

		g.fillRect(p_x, p_y, width, height);
	}

	/**
	 * Reduces the y-coordinate of player by 7
	 */
	public void Update() {
		p_y -= 7;
	}

	/**
	 * @return x-coordinate of player sprite
	 */
	public int positionX() {
		return p_x;
	}

	/**
	 * @return y-coordinate of player sprite
	 */
	public int positionY() {
		return p_y;
	}

	/**
	 * @return width of shot sprite
	 */
	public int width() {

		return width;
	}

	/**
	 * @return height of shot sprite
	 */
	public int height() {

		return height;
	}

}
