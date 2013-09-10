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
import deco2800.arcade.hunter.model.BackgroundSprite;
import deco2800.arcade.hunter.model.EntityCollection;
import deco2800.arcade.hunter.model.MapPane;
import deco2800.arcade.hunter.model.Player;
import deco2800.arcade.hunter.model.TileMap;
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
	private EntityCollection backgroundSprites = new EntityCollection();
	private Player player;
	private TileMap foreground;
	private TextureRegion background;
	
	private float gameSpeed = 64;
	
	private SpriteBatch batch = new SpriteBatch();
	
	private boolean paused = false;
	
	private float stateTime;
	private TextureRegion currFrame;
	private Animation runAnim;
	
	private class Config {
		public static final int TILE_SIZE = 64;
		public static final int PANE_SIZE = 16;
	}
	
	public GameScreen(Hunter p){
		parent = p;
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, parent.screenWidth, parent.screenHeight);
		
		//Load in background
		background = new TextureRegion(new Texture("textures/background.png"));
		
		player = new Player(new Vector2(0, 0), 64, 128);
		entities.add(player);
		
		for (int i = 0; i < 10; i++) {
			backgroundSprites.add(new BackgroundSprite(new Vector2((float) (parent.screenWidth / 2 + parent.screenWidth * Math.random()), (float) Math.random() * parent.screenHeight), 147, 86));
		}
		
		foreground = new TileMap((int) (Math.ceil((parent.screenWidth / (float)(Config.PANE_SIZE * Config.TILE_SIZE))))+1); //Screen width / map pane width in tiles / tile width in pixels, plus one
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
			
			for (Entity e : backgroundSprites) {
				e.setX(e.getX() - 64 * delta);
			}
			
			player.update(delta);
			foreground.update(delta, gameSpeed);
			
			batch.begin();
			
			drawBackground();
			drawBackgroundSprites();
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
				offset += (panes.get(i-1).getEndOffset() - panes.get(i).getStartOffset()) * Config.TILE_SIZE;
			}
			
			batch.draw(panes.get(i).getRendered(), foreground.getXOffset() + (i * Config.TILE_SIZE * Config.PANE_SIZE), offset);
		}
	}
	
	private void drawBackground() {
		//Black background, replace with image
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.draw(background, 0, 0, parent.screenWidth, parent.screenHeight);
	}
	
	private void drawBackgroundSprites() {
		TextureRegion cloud = new TextureRegion(new Texture("textures/cloud.png"));
		
		for (Entity e : backgroundSprites) {
			batch.draw(cloud, e.getX(), e.getY());
		}
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