package deco2800.arcade.raiden;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class PPlane extends Plane{

	public static int life = 100;
	private Texture Plane;
	public PPlane(){
		
	}
	public PPlane(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	

	public void drawMe(SpriteBatch batch) {
		// TODO Auto-generated method stub
		Plane = new Texture(Gdx.files.classpath("resources/Boss1.png"));
		batch.draw(Plane, x, y);
	}

	public boolean isAlive(){
		if(PPlane.life <= 0){
			PPlane.life = 0;
			return false;    
		} 
		else
			return true;
	}


}
