package deco2800.arcade.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Application;

import static com.badlogic.gdx.Input.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;




public class SplashScreen implements Screen, UIOverlay, InputProcessor {

	Texture splashTexture;
	Sprite splashSprite;
	SpriteBatch batch;
	Chess game;
	
	private InputMultiplexer inputMultiplexer = new InputMultiplexer(this);
	
	public SplashScreen(Chess game){
		this.game = game;
	}


	@Override
	public void dispose() {
		splashTexture.dispose();
		
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(splashTexture, 0, 0);
		batch.end();
		
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void show() {
		batch = new SpriteBatch(); //batch for render
		splashTexture = new Texture(Gdx.files.internal("chess.png"));
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				
		splashSprite = new Sprite(splashTexture);
		//moves sprite to centre of screen
		splashSprite.setX(Gdx.graphics.getWidth() / 2 - (splashSprite.getWidth() / 2));
        splashSprite.setY(Gdx.graphics.getHeight() / 2 - (splashSprite.getHeight() / 2));
        
        inputMultiplexer.addProcessor(this);
		ArcadeInputMux.getInstance().addProcessor(inputMultiplexer);
		
		
	
	}


	@Override
	public void setListeners(Screen l) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addPopup(PopupMessage p) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public GameClient getHost() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setHost(GameClient host) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		game.setScreen(game);
		return false;
	}


	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
