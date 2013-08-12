package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameScreen;

public class Overlay  extends GameScreen {
	
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private BitmapFont font;
	private SpriteBatch batch;
	private boolean isUIOpen = false;
	private boolean hasTabPressedLast = false;

	
	
	public Overlay() {
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
	}
	
	@Override
	public void render(float arg0) {
		
		
		
		camera.update();
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
		//toggles isUIOpen on tab key down
		if (Gdx.input.isKeyPressed(Keys.TAB) != hasTabPressedLast && (hasTabPressedLast = !hasTabPressedLast)) {
			isUIOpen = !isUIOpen;
		}
		
		if (isUIOpen) {
			
			//draw a placeholder shape
		    shapeRenderer.begin(ShapeType.FilledRectangle);
		    
		    shapeRenderer.filledRect(100,
		        100,
		        getWidth() - 200,
		        getHeight() - 200);
		    
		    shapeRenderer.end();
		    
		    
		    batch.begin();
		    font.setColor(Color.BLACK);
		    font.draw(batch, "Press ESC to quit the game.", 110, 110);
		    batch.end();
		    
		    if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
		    	ArcadeSystem.goToGame("arcadeui");
		    }
		    
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
