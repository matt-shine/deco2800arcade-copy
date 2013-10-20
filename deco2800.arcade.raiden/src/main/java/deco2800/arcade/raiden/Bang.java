package deco2800.arcade.raiden;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Bang {
	
	private int x;
	private int y;
	private int w;
	private int h;
	private JPanel jpanel;
	public boolean isBang = false;
	private int xpic;
	private Image img = new javax.swing.ImageIcon(this.getClass().getResource("/Image/flame.png")).getImage();
	/**
	 * The basic attributes for bang.
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Bang(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		Timer timer = new Timer(50,new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				xpic += 66;
				if(xpic >= 66 * 8)
					isBang = true;  		
			}
		});
		timer.start();
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
	 * draw the image.
	 * @param g
	 */
	public void drawMe(Graphics g){
		g.drawImage(img, x, y, x + w, y + h, xpic, 0, xpic + 66, 66, jpanel);
	}
}
