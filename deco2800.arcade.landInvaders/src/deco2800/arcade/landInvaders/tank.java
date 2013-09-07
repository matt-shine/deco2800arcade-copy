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
	private Image im =null;
	private boolean shotState = false;
	private boolean Mleft=false;
	private boolean Mright=false;
	private boolean Fshot =false;
/**
 * Tank initial starting point
 */
	public tank() {
		p_x = 370;
		p_y = 400;

	}
	// testing
	public void drawTank(Graphics g, JFrame p) {
		g.setColor(Color.green);
		im = new javax.swing.ImageIcon("Tank.png").getImage();
		g.drawImage(im,p_x, p_y,40,80,p);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			Mleft = true;
			
		}
		

		if (key == KeyEvent.VK_RIGHT ) {
			Mright =true;
		}
		
			if(Mleft == true && p_x > 10)p_x -= 7;
			if(Mright == true&& p_x < 750)p_x += 7;
		
		
		
		

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_SPACE){
			shotState = true;
			Fshot=true;
		}
		if (key == KeyEvent.VK_LEFT) {
			Mleft =false;
			Fshot=false;
			
		}
		

		if (key == KeyEvent.VK_RIGHT ) {
			Mright=false;
			Fshot=false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	
	public boolean moveLeft(){
		return Mleft&&Fshot;
	}
	
	public boolean moveRight(){
		return Mright&&Fshot;
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
