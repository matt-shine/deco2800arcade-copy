package deco2800.arcade.hunter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * A Hunter game for use in the Arcade
 * @author Nessex
 *
 */
public class GameScreen implements Screen {
	private OrthographicCamera camera;
	private Hunter parent;
	
	private ShapeRenderer shapeRenderer;
	
	private float exampleCoordX, exampleCoordY;
	private int speed = 100;
	
	public GameScreen(Hunter p){
		parent = p;
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, parent.screenWidth, parent.screenHeight);
		
		shapeRenderer = new ShapeRenderer();
		
		exampleCoordX = parent.screenWidth / 2 - 5;
		exampleCoordY = parent.screenHeight / 2 - 5;
		
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
		
		//Poll for input
		if (Gdx.input.isKeyPressed(Keys.UP)){
			exampleCoordY += speed * delta;
		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			exampleCoordY -= speed * delta;
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			exampleCoordX -= speed * delta;
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			exampleCoordX += speed * delta;
		}
		
		shapeRenderer.setProjectionMatrix(camera.combined);
	    
		//Draw the boxes
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    shapeRenderer.setColor(Color.RED);
	    shapeRenderer.filledRect(0, 0, 100, 100);
	    
	    shapeRenderer.setColor(Color.BLUE);
	    shapeRenderer.filledRect(parent.screenWidth - 100, parent.screenHeight - 100, 100, 100);
	    
	    shapeRenderer.end();
	    
	    //Draw the circle
	    shapeRenderer.begin(ShapeType.FilledCircle);
	    
	    shapeRenderer.setColor(Color.GREEN);
	    shapeRenderer.filledCircle(exampleCoordX, exampleCoordY, (float)5);
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