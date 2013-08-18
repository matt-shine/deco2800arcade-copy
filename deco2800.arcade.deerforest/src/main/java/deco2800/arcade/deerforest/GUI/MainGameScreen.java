package deco2800.arcade.deerforest.GUI;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MainGameScreen implements Screen {
	
	private final MainGame game;
	private OrthographicCamera camera;
	
	//Variables for Card locations and what they contain
	private int p1DeckSize;
	private int p2DeckSize;
	
	//assets
	private ExtendedSprite s1;
	private int[] s1Position = {300, 300};
	private ExtendedSprite s2;
	private int[] s2Position = {200,200};
	
	public MainGameScreen(final MainGame gam) {
		this.game = gam;
		
		//create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, getWidth(), getHeight());
		
		//load some assets
		s1 = new ExtendedSprite(new Texture(Gdx.files.internal("DeerForestAssets/1.png")));
	    s2 = new ExtendedSprite(new Texture(Gdx.files.internal("DeerForestAssets/2.png")));
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

	    game.batch.draw(s1, s1Position[0], s1Position[1]);
	    game.batch.draw(s2, s2Position[0], s2Position[1]);
	    
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
		camera.setToOrtho(true, getWidth(), getHeight());
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public int[] getS1Position() {
		return s1Position;
	}
	
	public int[] getS2Position() {
		return s2Position;
	}
	
	public ExtendedSprite getS1() {
		return s1;
	}
	
	public ExtendedSprite getS2() {
		return s2;
	}
	
	public void setS1(int x, int y) {
		s1Position[0] = x;
		s1Position[1] = y;
	}
	
	public void setS2(int x, int y) {
		s2Position[0] = x;
		s2Position[1] = y;
	}

	public int getWidth() {
		return Gdx.graphics.getWidth();
	}
	
	public int getHeight() {
		return Gdx.graphics.getHeight();
	}
}
