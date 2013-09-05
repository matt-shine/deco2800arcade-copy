package deco2800.arcade.raiden;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PBullet extends Bullet{
	private Texture bullet;

	public PBullet(int x, int y, int width, int heigth) {
		super(x, y, width, heigth);
		// TODO Auto-generated constructor stub
	}

	public void bulletMove() {
		// TODO Auto-generated method stub
		this.y-=20;              
	}

	public int isPbulletHitEplane(){		
//		for(int j = 0; j < Controller.eplanes.size(); j++){
//			Rectangle recPbullet = new Rectangle(x, y, width, heigth);
//			Rectangle recEplane = new Rectangle(Controller.eplanes.elementAt(j).x,
//			Controller.eplanes.elementAt(j).y, Controller.eplanes.elementAt(j).w,
//			Controller.eplanes.elementAt(j).h);
//			if(recPbullet.intersects(recEplane)) 
//			{
//				 return j;
//			 }
//		}
		return -1;
	}

	public void drawMe(SpriteBatch batch) {
		bullet =  new Texture(Gdx.files.classpath("resources/fire.png"));
		batch.draw(bullet, x, y);
		
	}
}
