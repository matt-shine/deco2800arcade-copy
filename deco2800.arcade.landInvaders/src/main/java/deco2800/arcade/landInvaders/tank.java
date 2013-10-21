package deco2800.arcade.landInvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class tank implements KeyListener {

	private int p_x;
	private int p_y;
	private Image im = null;
	private boolean shotState = false;
	private boolean Mleft = false;
	private boolean Mright = false;
	private String img;
	private int width;
	private int height;
	private boolean tankState = false;

	/**
	 * Tank initial starting point
	 */
	/**
	 * @param img set the name of the image of the enemy sprite
	 */
	public tank(String img) {
		p_x = 370;
		p_y = 550;
		this.img = img;
		width = 40;
		height = 80;
		tankState = true;
	}
	
	public boolean checkTankState()
	{
		return tankState;
	}

	/**
	 * @param g the graphic of the player sprite, tank
	 * @param p the main frame of the game
	 */
	public void drawTank(Graphics g, JFrame p) {
		g.setColor(Color.green);
		im = new javax.swing.ImageIcon(this.getClass().getResource(img))
				.getImage();
		g.drawImage(im, p_x, p_y, width, height, p);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			Mleft = true;

		}

		if (key == KeyEvent.VK_RIGHT) {
			Mright = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE) {
			shotState = true;
		}
		if (key == KeyEvent.VK_LEFT) {
			Mleft = false;
		}
		if (key == KeyEvent.VK_RIGHT) {
			Mright = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * Check input of keyboard listener and move player(tank) sprite accordingly
	 */
	public void tankMove() {
		if (Mleft == true && p_x > 10)
			p_x -= 7;
		if (Mright == true && p_x < 750)
			p_x += 7;
	}

	/**
	 * @return true if tank has fired a shot else false otherwise
	 */
	public boolean shotCheck() {
		return shotState;
	}

	/**
	 * @return x-coordinate of player sprite
	 */
	public int PositionX() {
		return p_x;
	}

	/**
	 * @return y-coordinate of player sprite
	 */
	public int PositionY() {
		return p_y;
	}

	/**
	 * Reset the state of shots fired by player
	 */
	public void finishShot() {
		shotState = false;
	}

	/**
	 * @return width of player sprite tank
	 */
	public int width() {
		return width;
	}

	/**
	 * @return height of player sprite tank
	 */
	public int height() {
		return height;
	}

}
