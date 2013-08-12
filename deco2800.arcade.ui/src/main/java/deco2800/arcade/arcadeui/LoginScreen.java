package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameScreen;

public class LoginScreen extends GameScreen {
	
	
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private BitmapFont font;
	private SpriteBatch batch;
	
	
	
	public LoginScreen() {
		font = new BitmapFont(true);
		batch = new SpriteBatch();
	}
	
	

	@Override
	public void show() {
	}
	

	@Override
	public void firstResize() {
		camera = new OrthographicCamera();
		camera.setToOrtho(true, getWidth(), getHeight());
		shapeRenderer = new ShapeRenderer();
		
		ArcadeSystem.openConnection();
		
	}
	
	@Override
	public void render(float arg0) {
		
		camera.update();
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//draw a placeholder shape
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    
	    shapeRenderer.filledRect(100,
	        100,
	        getWidth() - 200,
	        getHeight() - 200);
	    
	    shapeRenderer.end();
	    
	    batch.begin();
	    font.setColor(Color.BLACK);
	    font.draw(batch, "This is the login screen. Press space to log in as 'debuguser'.", 110, 110);
	    batch.end();
	    
	    if (Gdx.input.isKeyPressed(Keys.SPACE)) {
	    	ArcadeSystem.login("debuguser");
	    }
	    
	    if (ArcadeSystem.isLoggedIn()) {
	    	ArcadeSystem.goToGame("arcadeui");
	    }
	    
	    
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
	
	
}
