package deco2800.arcade.hunter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.hunter.model.BackgroundLayer;
import deco2800.arcade.hunter.model.EntityCollection;
import deco2800.arcade.hunter.model.Player;
import deco2800.arcade.hunter.model.ForegroundLayer;

/**
 * A Hunter game for use in the Arcade
 * @author Nessex, DLong94
 *
 */
public class GameScreen implements Screen {
	private OrthographicCamera camera;
	private Hunter hunter;
	
	private EntityCollection entities = new EntityCollection();
	
	private Player player;

	private BackgroundLayer backgroundLayer;
	private SpriteLayer spriteLayer;
	private ForegroundLayer foregroundLayer;
	
	private float gameSpeed = 64;
	private SpriteBatch batch = new SpriteBatch();
	private boolean paused = false;
	
	public GameScreen(Hunter hunter){
		this.hunter = hunter;
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Config.screenWidth, Config.screenHeight);
		
		backgroundLayer = new BackgroundLayer(0);
		spriteLayer = new SpriteLayer((float) 0.6);

		int numPanes = (int) (Math.ceil(Config.screenWidth / Config.PANE_SIZE_PX) + 1);
		foregroundLayer = new ForegroundLayer(1, numPanes);


		player = new Player(new Vector2(0, 0), 64, 128);
		entities.add(player);
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
		if (!paused) {
			pollInput();
			
			entities.updateAll(delta);
			checkCollisions();
			
			backgroundLayer.update(delta, gameSpeed);
			spriteLayer.update(delta, gameSpeed);
			foregroundLayer.update(delta, gameSpeed);
			
			batch.begin();
			
			backgroundLayer.draw(batch);
			spriteLayer.draw(batch);
			foregroundLayer.draw(batch);
			
			entities.drawAll();
		    
			batch.end();
		}
		
	}
	
	private void pollInput() {
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
			//Attack
		}
		
//		if (Gdx.input.isKeyPressed(Keys.SPACE) && player.isGrounded()) {
//			//Jump
//			player.jump();
//		}
	}
	
	private void checkCollisions() {
		
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