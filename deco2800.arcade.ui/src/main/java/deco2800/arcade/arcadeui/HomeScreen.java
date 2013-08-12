package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import deco2800.arcade.client.GameScreen;

public class HomeScreen  extends GameScreen {

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;
	
	public HomeScreen() {
		
	}
	
	

	@Override
	public void show() {
		font = new BitmapFont(true);
		batch = new SpriteBatch();
	}
	
	@Override
	public void firstResize() {
		camera = new OrthographicCamera();
		camera.setToOrtho(true, getWidth(), getHeight());
		shapeRenderer = new ShapeRenderer();
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
	        getWidth() - 200,
	        getHeight() - 200);
	    
	    shapeRenderer.end();
	    
	    batch.begin();
	    font.setColor(Color.BLACK);
	    font.draw(batch, "This is the Home Screen. Game selection is NYI.", 100, 100);
	    batch.end();
		
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
