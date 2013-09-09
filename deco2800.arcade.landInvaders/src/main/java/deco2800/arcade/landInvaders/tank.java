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
		im = new javax.swing.ImageIcon(this.getClass().getResource("/image/tank.png")).getImage();
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
		
<<<<<<< HEAD
		if(key == KeyEvent.VK_SPACE){
			shotState = true;
			
		}
		
			
=======
		
>>>>>>> origin/master
		
		
		
		

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
<<<<<<< HEAD
		
=======
		if(key == KeyEvent.VK_SPACE){
			shotState = true;
			
		}
>>>>>>> origin/master
		if (key == KeyEvent.VK_LEFT) {
			Mleft =false;
			
			
		}
		

		if (key == KeyEvent.VK_RIGHT ) {
			Mright=false;
			
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	
	
	
<<<<<<< HEAD
	public void moveTank(){
		
=======
	public void tankMove(){
>>>>>>> origin/master
		if(Mleft == true && p_x > 10)p_x -= 7;
		if(Mright == true&& p_x < 750)p_x += 7;
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
