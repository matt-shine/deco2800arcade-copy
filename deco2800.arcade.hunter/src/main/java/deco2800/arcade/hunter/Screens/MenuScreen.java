package deco2800.arcade.hunter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.MenuInput;

/**
 * A Hunter game for use in the Arcade
 * @author Nessex
 *
 */
public class MenuScreen implements Screen {
	private OrthographicCamera camera;
	private Hunter parent;
	
	private MenuInput input = new MenuInput();
	
	private ShapeRenderer shapeRenderer;
	
	//Darryl's additions (feel free to delete or change them)
	private Texture texture;
	private Sprite menuTitle;
	private SpriteBatch batch;
	
	
	public MenuScreen(Hunter p){
		parent = p;
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, parent.screenWidth, parent.screenHeight);
		
		shapeRenderer = new ShapeRenderer();
		
		
		//Darryl's Addition
		batch = new SpriteBatch();
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
	    shapeRenderer.filledRect(parent.screenWidth - 100, parent.screenHeight - 100, 100, 100);
	    
	    shapeRenderer.end();
	    
	  //Darryl's Addition
	    batch.begin();
	    menuTitle.draw(batch);
		batch.end();
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
		//Darryl's Addition
		texture = new Texture("res/huntergame.png");
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		menuTitle = new Sprite(texture);
		menuTitle.setX(Gdx.graphics.getWidth()/2 - menuTitle.getWidth()/2);
		menuTitle.setY(Gdx.graphics.getHeight() - (menuTitle.getHeight()+ 20));
	
	}
	
}