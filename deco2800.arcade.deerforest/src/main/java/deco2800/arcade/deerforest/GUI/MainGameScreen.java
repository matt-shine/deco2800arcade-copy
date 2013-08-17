package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainGameScreen implements Screen {
	
	private final MainGame game;
	
	private OrthographicCamera camera;
	
	public MainGameScreen(final MainGame gam) {
		this.game = gam;
		
		//create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, getWidth(), getHeight());
	}

	@Override
	public void render(float delta) {
		//clear the screen with a dark blue colour
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//tell the camera to update its matrices
		camera.update();
		
		//tell the SpriteBatch to render in the
		//coordinate system specified by the camera
		game.batch.setProjectionMatrix(camera.combined);
		
		//draw some random text  
	    game.batch.begin();

	    game.font.draw(game.batch, "Welcome To Deer Forest", 100, Gdx.graphics.getHeight() - 100);
	    game.font.draw(game.batch, "This is all we have", 100, Gdx.graphics.getHeight() - 150);
	    game.font.draw(game.batch, "The loaded model: " + game.getModel(), 100, Gdx.graphics.getHeight() - 200);

	    game.batch.end();
		
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
		camera.setToOrtho(false, getWidth(), getHeight());
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public int getWidth() {
		return Gdx.graphics.getWidth();
	}
	
	public int getHeight() {
		return Gdx.graphics.getHeight();
	}
}
