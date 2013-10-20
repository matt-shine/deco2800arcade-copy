package deco2800.arcade.breakout.screens;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import deco2800.arcade.breakout.Breakout;
import deco2800.arcade.client.ArcadeSystem;

/**
 * Class for the SplashScreen 
 * @author Tony Wu and ZhuLun Liang
 * 
 */

public class ModelScreen implements Screen {
	private final Breakout game;
	private final SpriteBatch batch;
	private final Texture texture;
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	Stage stage;
	/*
	 * creates instance variables for each image buttons.
	 */
	TextureRegionDrawable model1up;
	TextureRegionDrawable model1down;
	TextureRegionDrawable backup;
	TextureRegionDrawable backdown;
	TextureRegion model1Up;
	TextureRegion model1Down;
	TextureRegion model1buttonUp;
	TextureRegion model1buttonDown;
	TextureRegion backUp;
	TextureRegion backDown;
	TextureRegion backbuttonUp;
	TextureRegion backbuttonDown;
	TextureRegionDrawable model2up;
	TextureRegionDrawable model2down;
	TextureRegion model2Up;
	TextureRegion model2Down;
	TextureRegion model2buttonUp;
	TextureRegion model2buttonDown;

	Texture tex;
	Texture tex2;

	ImageButton model1button;
	ImageButton model2button;
	ImageButton backbutton;

	/**
	 * Constructor.
	 * Load the image from the resource
	 * @param game
	 * 				-The Breakout game
	 */    
	
	public ModelScreen(final Breakout game) {

		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		// insert the button images for mode select
		texture = new Texture(Gdx.files.classpath("imgs/ModeSelection.png"));
		tex = new Texture(Gdx.files.classpath("imgs/mode_select.png"));
		tex2 = new Texture(Gdx.files.classpath("imgs/button.png"));
		TextureRegion[][] tmp = TextureRegion.split(tex, 565, 350);

		TextureRegion[][] tmp2 = TextureRegion.split(tex2, 130, 45);

		//set classic mode button
		model1buttonUp = tmp[0][0];
		model1buttonDown = tmp[0][1];
		model1up = new TextureRegionDrawable(model1buttonUp);
		model1down = new TextureRegionDrawable(model1buttonDown);
		model1button = new ImageButton(model1up, model1down);
		model1button.setPosition(0, 120);
		
		// Instantiate a input listener
		model1button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) { 
												

				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) { 
				game.getGamescreen().switchGameMode(false);
				game.getLevelScreen1().enableButtons();
				game.setScreen(game.getLevelScreen1());

			}
		});
		//set enhanced mode button
		model2buttonUp = tmp[1][0];
		model2buttonDown = tmp[1][1];
		model2up = new TextureRegionDrawable(model2buttonUp);
		model2down = new TextureRegionDrawable(model2buttonDown);
		model2button = new ImageButton(model2up, model2down);
		model2button.setPosition(550, 100);

		// Instantiate a input listener
		model2button.addListener(new InputListener() {
			/**
			 * called when a button goes down on the actor, if return true, it
			 * will receive all touch up events.
			 * 
			 * @param event
			 *            - Event for actor input: touch, mouse, keyboard, and
			 *            scroll.
			 * @return - true
			 */
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) { 
												

				return true;
			}
			
			/**
			 * called when a button goes up, the screen will move to the help
			 * screen
			 * 
			 * @param event
			 *            - Event for actor input: touch, mouse, keyboard, and
			 *            scroll.
			 */
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) { 
				game.getGamescreen().switchGameMode(true);
				game.getLevelScreen1().enableButtons();
				game.setScreen(game.getLevelScreen1());

			}
		});
		//set back menu button
		backbuttonUp = tmp2[2][2];
		backbuttonDown = tmp2[2][3];
		backup = new TextureRegionDrawable(backbuttonUp);
		backdown = new TextureRegionDrawable(backbuttonDown);
		backbutton = new ImageButton(backup, backdown);
		backbutton.setPosition(0, 590);
		
		// Instantiate a input listener
		backbutton.addListener(new InputListener() {
			/**
			 * called when a button goes down on the actor, if return true, it
			 * will receive all touch up events.
			 * 
			 * @param event
			 *            - Event for actor input: touch, mouse, keyboard, and
			 *            scroll.
			 * @return - true
			 */
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) { 
												

				return true;
			}
			
			/**
			 * called when a button goes up, the screen will move to the help
			 * screen
			 * 
			 * @param event
			 *            - Event for actor input: touch, mouse, keyboard, and
			 *            scroll.
			 */
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) { 
				game.setScreen(game.getMenuScreen());

			}
		});
		stage = new Stage(480, 640, true);
		
		//add all buttons to the stage
		stage.addActor(model1button);
		stage.addActor(model2button);
		stage.addActor(backbutton);

	}

	@Override
	public void dispose() {
		texture.dispose();
		batch.dispose();

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}
	/**
	 * get into the level screens
	 */
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 0, 0);
		batch.end();
		stage.act();
		stage.draw();

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
		Gdx.input.setInputProcessor(stage);

	}

}
