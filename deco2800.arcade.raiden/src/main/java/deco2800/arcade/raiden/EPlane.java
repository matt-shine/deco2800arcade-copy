package deco2800.arcade.raiden;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class EPlane extends Plane{

	private Texture Eplane;
	
	public EPlane(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void eplaneMove(){
		this.y += 3;
	}
	public void drawMe(SpriteBatch batch) {
		// TODO Auto-generated method stub
		Eplane = new Texture(Gdx.files.classpath("resources/enemy.png"));
		batch.draw(Eplane, this.x, this.y);
	}
}
