package com.test.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TestGame2 extends Game {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private Screen screen;
	private int screenWidth;
	private int screenHeight;
	
    //public GameData gameData;  
	
	private int framecount = 0;
	private int framecountmax = 80;
      
    public SplashScreen getSplashScreen() {
    	return new SplashScreen(this);
    }
	
	@Override
	public void create() {		
		/*float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(320, 480);
		camera.position.set(320*0.5f, 480*0.5f, 0);
		batch = new SpriteBatch();
		
		screenWidth = 320;
		screenHeight = 480;
		
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		Sounds.load();
		ImageCache.load();
		Sounds.playtest();*/
		
		//Set to splash screen
		setScreen(getSplashScreen());
		//OR go straight to the action
		//setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		//batch.dispose();
		//texture.dispose();
	}

	@Override
	public void render() {		
		/*Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);
		batch.end();
		
		if (framecount++ == framecountmax) {
			Sounds.playtest();
			framecount = 0;
			if (framecountmax > 25) {
				framecountmax -= 10;
			} else framecountmax--;
		}*/
		super.render();
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
