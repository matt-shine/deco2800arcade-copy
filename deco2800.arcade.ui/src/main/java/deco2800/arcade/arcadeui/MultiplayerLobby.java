package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;




public class MultiplayerLobby implements Screen {
	
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	public void show() {
		font = new BitmapFont(true);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1280, 720);
		shapeRenderer = new ShapeRenderer();   
	}
	@Override
	public void render(float arg0) {
		
		camera.update();
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
		Gdx.gl.glClearColor(0.9f, 0, 0, 1);
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
	    font.draw(batch, "Multiplayer Lobby", 110, h);
	    batch.end();
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
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
