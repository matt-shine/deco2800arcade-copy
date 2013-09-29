package deco2800.arcade.arcadeui;

import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;

public class HomeScreen implements Screen {

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;
	boolean multiplayerEnabled;
	Set<String> games = null;
	
	
	
	
	
	public HomeScreen() {
		
		//check that no input listeners are left
		if (ArcadeInputMux.getInstance().getProcessors().size != 0) {
			System.err.println("Home Screen: Input listener leak detected. The following " +
					ArcadeInputMux.getInstance().getProcessors().size + " listener(s) remain:");
			
			for (int i = ArcadeInputMux.getInstance().getProcessors().size - 1; i >= 0; i--) {
				System.err.println("Home Screen: " + ArcadeInputMux.getInstance().getProcessors().get(i));
			}
		}
		
	}
	
	

	@Override
	public void show() {
		font = new BitmapFont(true);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1280, 720);
		shapeRenderer = new ShapeRenderer();
		
		games = ArcadeSystem.getGamesList();
	    
	}
	
	@Override
	public void render(float arg0) {
		
		camera.update();
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
		Gdx.gl.glClearColor(0.2f, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//draw a placeholder shape
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    
	    shapeRenderer.filledRect(100,
	        100,
	        1280 - 200,
	        720 - 200);
	    
	    shapeRenderer.end();
	    
	    batch.begin();
	    font.setColor(Color.BLACK);
	    
	    int h = 110;
	    int index = 0;
	    font.draw(batch, "Select a game by pressing a number key:", 110, h);
	    font.draw(batch, "Press 'm' to enable multiplayer", 110, h+20);
	    h += 20;
	    
	    if (Gdx.input.isKeyPressed(Keys.M)) {
	    	multiplayerEnabled = true;
	    }
	    if (Gdx.input.isKeyPressed(Keys.S)) {
	    	multiplayerEnabled = false;
	    }
	    if (multiplayerEnabled) {
	    	font.draw(batch,  "Multiplayer Enabled (Press 's' for single player mode)", 500, 600);
	    }
	    
	    
	    for (String game : games) {
	    	h += 16;
		    font.draw(batch, "" + (char)(index + 65) + ". " + game, 110, h);
		    
		    if (Gdx.input.isKeyPressed(Keys.NUM_0 + index)) {
		    	if (multiplayerEnabled) {
		    		//If user selects multiplayer mode, set arcades multiplayer and return to arcadeUI
		    		ArcadeSystem.setMultiplayerEnabled(true);
		    		ArcadeSystem.goToGame("arcadeui");
		    		
		    	} else {
		    		ArcadeSystem.goToGame(game);
		    	}
		    if (Gdx.input.isKeyPressed(Keys.A + index)) {
		    	ArcadeSystem.goToGame(game);
		    }
		    }
		    
		    index++;
	    }
	    
	    batch.end();
	    
	    
	    //TODO implement this better
	    
	    
		
	}

	@Override
	public void dispose() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}