package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import deco2800.arcade.client.GameScreen;

public class Overlay  extends GameScreen {
	
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private float width, height;
	private boolean isUIOpen = false;
	private boolean hasTabPressedLast = false;

	
	
	public Overlay() {
		
	}
	
	

	@Override
	public void show() {
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, width, height);
		shapeRenderer = new ShapeRenderer();
		
	}

	@Override
	public void render(float arg0) {
		
		camera.update();
	    shapeRenderer.setProjectionMatrix(camera.combined);

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
