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

/** The extension of Game class that is opened by the desktop application.
 * Will need to be altered to extend GameClient instead.
 * GameScreen.java might need to get merged with this one, so that this will
 * be the class in control of level progression 
 * @author Game Over
 *
 */
public class TestGame2 extends Game {

      
    public SplashScreen getSplashScreen() {
    	return new SplashScreen(this);
    }
	
	@Override
	public void create() {		
	
		
		//Set to splash screen
		//setScreen(getSplashScreen());
		//OR go straight to the action
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		//batch.dispose();
		//texture.dispose();
	}

	@Override
	public void render() {		
		
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
