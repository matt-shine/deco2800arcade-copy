package Invaders;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class tank implements KeyListener {

	private int p_x;
	private int p_y;
	private Image im =null;
	private boolean shotState = false;

	public tank() {
		p_x = 370;
		p_y = 400;

	}

	public void drawTank(Graphics g, JFrame p) {
		g.setColor(Color.green);
		im = new javax.swing.ImageIcon("Tank2.png").getImage();
		g.drawImage(im,p_x, p_y,40,80,p);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT && p_x > 10) {
			p_x -= 7;
		}

		if (key == KeyEvent.VK_RIGHT && p_x < 750) {
			p_x += 7;
		}
		
		if(key == KeyEvent.VK_SPACE){
			shotState = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public boolean shotCheck(){
		return shotState;
		
	}
	
	public int PositionX(){
		return p_x;
	}
	
	public int PositionY(){
		return p_y;
	}
	
	public void finishShot(){
		shotState = false;
	}

}
