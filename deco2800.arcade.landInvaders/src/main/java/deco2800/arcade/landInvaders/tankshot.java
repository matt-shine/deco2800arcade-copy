package deco2800.arcade.landInvaders;

import java.awt.Color;
import java.awt.Graphics;

public class tankshot {

	private int p_x;
	private int p_y;
	private int width;
	private int height;

	/**
	 * @param x
	 * @param y
	 */
	public tankshot(int x, int y) {
		p_x = x +20;
		p_y = y;
		width = 5;
		height = 10;

	}

	/**
	 * @param g
	 */
	public void drawshot(Graphics g) {

		g.setColor(Color.green);

		g.fillRect(p_x, p_y, width, height);
	}

	/**
	 * 
	 */
	public void Update() {
		p_y -= 7;
	}

	/**
	 * @return
	 */
	public int positionX() {
		return p_x;
	}

	/**
	 * @return
	 */
	public int positionY() {
		return p_y;
	}

	/**
	 * @return
	 */
	public int width() {

		return width;
	}

	/**
	 * @return
	 */
	public int height() {

		return height;
	}

}
