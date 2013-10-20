package deco2800.arcade.raiden;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.JPanel;


public class EBullet extends Bullet{

	private Image img;
	private JPanel jpanel;
	
	
    public boolean isUsed = false;     
	
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
	 * The constructor of Ebullet.
	 * load the image
	 * @param x
	 * @param y
	 * @param width
	 * @param heigth
	 */
	public EBullet(int x, int y, int width, int heigth) {
		super(x, y, width, heigth);
		img = new javax.swing.ImageIcon(this.getClass().
				getResource("/Image/fire.png")).getImage();
		// TODO Auto-generated constructor stub
	}
	/**
	 * The enemy bullet move.
	 */
	public void bulletMove() {
		// TODO Auto-generated method stub
		y+=10;
	}
	/**
	 * Draw it  on the screen.
	 */
	public void drawMe(Graphics g) {
		// TODO Auto-generated method stub
		if(!isUsed)
		g.drawImage(img, x, y, width, heigth, jpanel);
	}
	/**
	 * The boolean function to check if the bullet hit player
	 * @return boolean
	 */
	public boolean isEBulletHitPPlane(){
		int x = Controller.pplane.x; 
		int y = Controller.pplane.y;
		int w = Controller.pplane.w;
		int h = Controller.pplane.h;
		Rectangle recEbullet = new Rectangle(this.x, this.y, width, heigth);
		Rectangle recPplane = new Rectangle(x, y, w, h);
		
		return recEbullet.intersects(recPplane) && !isUsed;
	}
}
