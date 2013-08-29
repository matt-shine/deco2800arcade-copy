package deco2800.arcade.hunter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.model.EntityCollection;
import deco2800.arcade.hunter.model.Map;
import deco2800.arcade.hunter.model.MapPane;
import deco2800.arcade.hunter.model.Player;
import deco2800.arcade.platformergame.model.Entity;

/**
 * A Hunter game for use in the Arcade
 * @author Nessex, DLong94
 *
 */
public class GameScreen implements Screen {
	private OrthographicCamera camera;
	private Hunter parent;
	
	private EntityCollection entities = new EntityCollection();
	private Player player;
	private Map foreground;
	
	private float gameSpeed = 64;
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch = new SpriteBatch();
	
	public GameScreen(Hunter p){
		parent = p;
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, parent.screenWidth, parent.screenHeight);
		
		shapeRenderer = new ShapeRenderer();
		
		player = new Player(new Vector2(0, 0), 64, 128);
		entities.add(player);
		
		foreground = new Map((int) (Math.ceil((parent.screenWidth / (float)(Map.PANE_SIZE * Map.TILE_SIZE))))+1); //Screen width / map pane width in tiles / tile width in pixels, plus one
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
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
			//Attack
			
		}
		
		if (Gdx.input.isKeyPressed(Keys.ALT_LEFT) && player.isGrounded()) {
			//Jump
			player.jump();
		}
		
		player.update(delta);
		foreground.update(delta, gameSpeed);
		
		batch.begin();
		
		int paneCount = 0;
		for (MapPane p : foreground.getPanes()) {
			batch.draw(p.getRendered(), foreground.getXOffset()+(paneCount * Map.TILE_SIZE * Map.PANE_SIZE), delta);
			paneCount++;
		}
		batch.end();
		
		
		shapeRenderer.setProjectionMatrix(camera.combined);
	    
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    shapeRenderer.setColor(Color.RED);
	    
	    for (Entity e : entities) {	
	    	//Draw each entity
	    	shapeRenderer.filledRect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
	    }
	    
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