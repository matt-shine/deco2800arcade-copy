package deco2800.arcade.raiden;

import java.awt.Rectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EBullet extends Bullet{

	
	
    public boolean isUsed = false;     
	

	public EBullet(int x, int y, int width, int heigth) {
		super(x, y, width, heigth);
		// TODO Auto-generated constructor stub
	}

	public void bulletMove() {
		// TODO Auto-generated method stub
		y+=10;
	}


	public boolean isEBulletHitPPlane(){
		
		int x = Raiden.pplane.x; 
		int y = Raiden.pplane.y;
		int w = Raiden.pplane.w;
		int h = Raiden.pplane.h;
		Rectangle recEbullet = new Rectangle(this.x, this.y, width, heigth);
		Rectangle recPplane = new Rectangle(x, y, w, h);
		return recEbullet.intersects(recPplane) && !isUsed;
	}

	public void drawMe(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}
}
