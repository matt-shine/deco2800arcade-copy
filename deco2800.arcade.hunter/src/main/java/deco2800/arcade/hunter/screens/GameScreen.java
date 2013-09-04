package deco2800.arcade.hunter.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private TextureRegion background;
	
	private float gameSpeed = 64;
	
	private SpriteBatch batch = new SpriteBatch();
	
	private boolean paused = false;
	
	private float stateTime;
	private TextureRegion currFrame;
	private Animation runAnim;
	
	public GameScreen(Hunter p){
		parent = p;
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, parent.screenWidth, parent.screenHeight);
		
		//Load in background
		background = new TextureRegion(new Texture("textures/background.png"));
		
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
		if (!paused) {
			pollInput();
			
			player.update(delta);
			foreground.update(delta, gameSpeed);
			
			batch.begin();
			
			drawBackground();
			drawMapPanes(delta);
			
		    runAnim = player.getAnimation();
		    stateTime += Gdx.graphics.getDeltaTime();
		    currFrame = runAnim.getKeyFrame(stateTime,true);
		    for (Entity e : entities) {	
		    	//Draw each entity
		    	batch.draw(currFrame, e.getX(), e.getY(), currFrame.getRegionWidth()/3, currFrame.getRegionHeight()/3);
	//	    	shapeRenderer.filledRect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
		    }
		    batch.end();
		}
		
	}
	
	private void drawMapPanes(float delta) {
		//Still no good, needs to use some sort of world space coordinates for offsets TODO
		ArrayList<MapPane> panes = foreground.getPaneArray();
		int offset = 0;
		
		for (int i = 0; i < panes.size(); i++) {
			if (i != 0) {
				offset += (panes.get(i-1).getEndOffset() - panes.get(i).getStartOffset()) * Map.TILE_SIZE;
			}
			
			batch.draw(panes.get(i).getRendered(), foreground.getXOffset() + (i * Map.TILE_SIZE * Map.PANE_SIZE), offset);
		}
	}
	
	private void drawBackground() {
		//Black background, replace with image
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.draw(background, 0, 0, parent.screenWidth, parent.screenHeight);
	}
	
	private void pollInput() {
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
			//Attack
		}
		
		if (Gdx.input.isKeyPressed(Keys.SPACE) && player.isGrounded()) {
			//Jump
			player.jump();
		}
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