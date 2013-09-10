package deco2800.arcade.hunter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.Hunter.Config;

/**
 * A Hunter game for use in the Arcade
 * @author Nessex, DLong94
 *
 */
public class GameOverScreen implements Screen {
	private OrthographicCamera camera;
	private Hunter parent;
	
//	private MenuInput input = new MenuInput();
	
	private ShapeRenderer shapeRenderer;
	
	public GameOverScreen(Hunter p){
		parent = p;
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Config.screenWidth, Config.screenHeight);
		
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
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
		//Black background
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		shapeRenderer.setProjectionMatrix(camera.combined);
	    
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    shapeRenderer.setColor(Color.RED);
	    shapeRenderer.filledRect(0, 0, 100, 100);
	    
	    shapeRenderer.setColor(Color.BLUE);
	    shapeRenderer.filledRect(Config.screenWidth - 100, Config.screenHeight - 100, 100, 100);
	    
	    shapeRenderer.end();
		
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
		// TODO Auto-generated method stub
		
	}
	
}