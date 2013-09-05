package deco2800.arcade.raiden;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class Bang {
	
	private int x;
	private int y;
	private int w;
	private int h;
	public boolean isBang = false;
	private int xpic;
	
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

//	public void drawMe(Graphics g){
//		g.drawImage(img, x, y, x + w, y + h, xpic, 0, xpic + 66, 66);
//	}
}
