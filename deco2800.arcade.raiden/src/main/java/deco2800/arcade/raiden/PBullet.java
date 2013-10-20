package deco2800.arcade.raiden;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.JPanel;


public class PBullet extends Bullet{

	private Image img;                        
	private JPanel jpanel;
	
	
	/**
	 * 
	 * @return the jpanel
	 */
	public JPanel getJpanel() {
		return jpanel;
	}
	/**
	 * 
	 * @param jpanel
	 * set the jpanel
	 */
	public void setJpanel(JPanel jpanel) {
		this.jpanel = jpanel;
	}
	/**
	 * The constructor of Pbullet.
	 * Load the image from source.
	 * @param x
	 * @param y
	 * @param width
	 * @param heigth
	 */
	public PBullet(int x, int y, int width, int heigth) {
		super(x, y, width, heigth);
		img = new javax.swing.ImageIcon(this.getClass()
				.getResource("/Image/fire.png")).getImage();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * The move of bullet.
	 */
	public void bulletMove() {
		// TODO Auto-generated method stub
		this.y-=20;              
	}
	/**
	 * Draw the bullet on the screen
	 */
	public void drawMe(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(img, x, y, width, heigth, jpanel);
	}
	
	/**
	 * The function to judge if it hit eplane or not.
	 * @return int
	 */
	public int isPbulletHitEplane(){		
		for(int j=0;j < Controller.eplanes.size();j++){
			Rectangle recPbullet = new Rectangle(x, y, width, heigth);
			Rectangle recEplane = new Rectangle(Controller.eplanes.elementAt(j).x,
			Controller.eplanes.elementAt(j).y,Controller.eplanes.elementAt(j).w,
			Controller.eplanes.elementAt(j).h);
			if(recPbullet.intersects(recEplane)) 
			{
				 return j;
			 }
		}
		return -1;
	}
}
