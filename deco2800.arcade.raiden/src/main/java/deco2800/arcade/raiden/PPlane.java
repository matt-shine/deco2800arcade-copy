package deco2800.arcade.raiden;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;


public class PPlane extends Plane{

	private Image img;
	private JPanel jpanel;
	private int direction;
	public static boolean UP;
	public static boolean DOWN;
	public static boolean LEFT;
	public static boolean RIGHT;
	public static boolean isFired;
	public static int life = 100;
	
	public PPlane(){
		
	}
	/**
	 * The constructor of PPlane.
	 * Load the image from source.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public PPlane(int x, int y, int width, int height) {
		super(x, y, width, height);
		img = new javax.swing.ImageIcon(this.getClass().
				getResource("/Image/Boss1.png")).getImage();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @return jpanel
	 */
	public JPanel getJpanel() {
		return jpanel;
	}
	/**
	 * Set the jpanel
	 * @param jpanel
	 */
	public void setJpanel(JPanel jpanel) {
		this.jpanel = jpanel;
	}
	/**
	 * 
	 * @return direction
	 */
	public int getDirection() {
		return direction;
	}
	/**
	 * Set the direction.
	 * @param direction
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}
	/**
	 * Draw the image on the screen.
	 */
	public void drawMe(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(img, this.x, this.y, this.w, this.h, this.jpanel);
	}
	/**
	 * Change the direction by user.
	 * @param direction
	 */
	public void changeDirection(int direction){
		this.direction = direction;
	}
	/**
	 * The move of PPlane.
	 */
	public void pplaneMove(){
		if (UP && y > 0){
			y -= Global.SPEED;
		}else if (DOWN && y < 450){
			y += Global.SPEED;
		}else if (LEFT && x > 0){
			x -= Global.SPEED;
		}else if (RIGHT && x < 550){
			x += Global.SPEED;
			}
		}
	/**
	 * Check if the Player is alive or not.
	 * @return boolean value
	 */
	public boolean isAlive(){
		if(PPlane.life <= 0){
			PPlane.life = 0;
			return false;    
		} 
		else
			return true;
	}
}
