package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class HomeScreen implements Screen {

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private float width, height;
	
	
	
	public HomeScreen() {
		
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
		
		Gdx.gl.glClearColor(0.2f, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
		//draw a placeholder shape
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    
	    shapeRenderer.filledRect(100,
	        100,
	        width - 200,
	        height - 200);
	    
	    shapeRenderer.end();
	    
		
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
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;

	}

	@Override
	public void resume() {
	}

}
