package deco2800.arcade.landInvaders;

import java.awt.Color;
import java.awt.Graphics;

public class enemyShot {
	
	private int p_x;
	private int p_y;
	private int width;
	private int height;
	private boolean enemyShotState = false;

	/**
	 * @param x x-coordinate of enemy shot sprite
	 * @param y y-coordinate of enemy shot sprite
	 */
	public enemyShot(int x, int y) {
		p_x = x +15;
		p_y = y+30;
		width = 5;
		height = 10;
		enemyShotState = true;

	}
	
	public boolean getEShotState()
	{
		return enemyShotState;
	}

	/**
	 * @param g the graphic of shot sprite
	 */
	public void drawshot(Graphics g) {

		g.setColor(Color.red);

		g.fillRect(p_x, p_y, width, height);
	}

	/**
	 * Increase y-coordinate of player sprite
	 */
	public void Update() {
		p_y += 6;
	}

	/**
	 * @return y-coordinate of enemy shot sprite
	 */
	public int positionX() {
		return p_x;
	}

	/**
	 * @return y-coordinate of enemy shot sprite
	 */
	public int positionY() {
		return p_y;
	}

	/**
	 * @return width of enemy shot sprite
	 */
	public int width() {

		return width;
	}

	/**
	 * @return height of enemy shot sprite
	 */
	public int height() {

		return height;
	}
}



