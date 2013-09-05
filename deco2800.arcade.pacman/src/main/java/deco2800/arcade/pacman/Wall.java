package deco2800.arcade.pacman;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.*;

public class Wall implements ApplicationListener {

	private SpriteBatch batch;
	private Texture wallTexture;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		wallTexture = new Texture(Gdx.files.internal("testwall.png"));	
		
	}
	
	@Override
	public void render() {
		batch.begin();
		batch.draw(wallTexture,100,100);
		batch.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	
}

