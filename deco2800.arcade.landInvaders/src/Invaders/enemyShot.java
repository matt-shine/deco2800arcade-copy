package Invaders;

import java.awt.Color;
import java.awt.Graphics;

public class enemyShot {
	
	private int p_x;
	private int p_y;
	private int width;
	private int height;

	public enemyShot(int x, int y) {
		p_x = x +15;
		p_y = y+30;
		width = 5;
		height = 10;

	}

	public void drawshot(Graphics g) {

		g.setColor(Color.red);

		g.fillRect(p_x, p_y, width, height);
	}

	public void Update() {
		p_y += 6;
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



