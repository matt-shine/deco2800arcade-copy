package Invaders;

import java.awt.Color;
import java.awt.Graphics;

public class tankshot {

	private int p_x;
	private int p_y;
	private int width;
	private int height;

	public tankshot(int x, int y) {
		p_x = x +20;
		p_y = y;
		width = 5;
		height = 10;

	}

	public void drawshot(Graphics g) {

		g.setColor(Color.green);

		g.fillRect(p_x, p_y, width, height);
	}

	public void Update() {
		p_y -= 7;
	}

	public int positionX() {
		return p_x;
	}

	public int positionY() {
		return p_y;
	}

	public int width() {

		return width;
	}

	public int height() {

		return height;
	}

}
