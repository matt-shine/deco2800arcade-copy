package deco2800.arcade.breakout.screens;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import deco2800.arcade.breakout.powerup.DecreasePaddle;
import deco2800.arcade.breakout.powerup.IncreaseBallNo;
import deco2800.arcade.breakout.powerup.IncreasePaddle;
import deco2800.arcade.breakout.powerup.LifePowerup;
import deco2800.arcade.breakout.powerup.SlowBall;
import deco2800.arcade.client.ArcadeSystem;

public class LevelScreen1 implements Screen {
	private final Breakout game;
	private final SpriteBatch batch;
	private final Texture texture;
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	Stage stage;
	/*
	 * creates instance variables for each image buttons.
	 */
	TextureRegionDrawable level1up;
	TextureRegionDrawable level1down;
	TextureRegionDrawable level2up;
	TextureRegionDrawable level2down;
	TextureRegionDrawable level3up;
	TextureRegionDrawable level3down;
	TextureRegionDrawable level4up;
	TextureRegionDrawable level4down;
	TextureRegionDrawable level5up;
	TextureRegionDrawable level5down;
	TextureRegionDrawable nextup;
	TextureRegionDrawable nextdown;
	TextureRegionDrawable backup;
	TextureRegionDrawable backdown;

	TextureRegion level1buttonUp;
	TextureRegion level1buttonDown;
	TextureRegion level2buttonUp;
	TextureRegion level2buttonDown;
	TextureRegion level3buttonUp;
	TextureRegion level3buttonDown;
	TextureRegion level4buttonUp;
	TextureRegion level4buttonDown;
	TextureRegion level5buttonUp;
	TextureRegion level5buttonDown;
	TextureRegion nextbuttonUp;
	TextureRegion nextbuttonDown;
	TextureRegion backbuttonUp;
	TextureRegion backbuttonDown;

	Texture tex;
	Texture tex2;
	ImageButton level1button;
	ImageButton level2button;
	ImageButton level3button;
	ImageButton level4button;
	ImageButton level5button;
	ImageButton nextbutton;
	ImageButton backbutton;

	// Instantiate a input listener called input1
	InputListener input1 = new InputListener() {
		/**
		 * called when a button goes down on the actor, if return true, it will
		 * receive all touch up events.
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
		 * called when a button goes up, the screen will move to the game screen
		 * at level1, then disable all the listener which come from the
		 * disableButton() class.
		 * 
		 * @param event
		 *            - Event for actor input: touch, mouse, keyboard, and
		 *            scroll.
		 */
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			disableButton();
			game.gamescreen.setLevel(1);
			dispose();
			game.gamescreen.gamearea();
			game.setScreen(game.gamescreen);
		}
	};
	// Instantiate a input listener called input2
	InputListener input2 = new InputListener() {
		/**
		 * called when a button goes down on the actor, if return true, it will
		 * receive all touch up events.
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
		 * called when a button goes up, the screen will move to the game screen
		 * at level2, then disable all the listener which come from the
		 * disableButton() class.
		 * 
		 * @param event
		 *            - Event for actor input: touch, mouse, keyboard, and
		 *            scroll.
		 */
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			disableButton();
			game.gamescreen.setLevel(2);
			dispose();
			game.gamescreen.gamearea();
			game.setScreen(game.gamescreen);
		}
	};
	// Instantiate a input listener called input3
	InputListener input3 = new InputListener() {
		/**
		 * called when a button goes down on the actor, if return true, it will
		 * receive all touch up events.
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
		 * called when a button goes up, the screen will move to the game screen
		 * at level3, then disable all the listener which come from the
		 * disableButton() class.
		 * 
		 * @param event
		 *            - Event for actor input: touch, mouse, keyboard, and
		 *            scroll.
		 */
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			disableButton();
			game.gamescreen.setLevel(3);
			dispose();
			game.gamescreen.gamearea();
			game.setScreen(game.gamescreen);
		}
	};
	// Instantiate a input listener called input4
	InputListener input4 = new InputListener() {
		/**
		 * called when a button goes down on the actor, if return true, it will
		 * receive all touch up events.
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
		 * called when a button goes up, the screen will move to the game screen
		 * at level4, then disable all the listener which come from the
		 * disableButton() class.
		 * 
		 * @param event
		 *            - Event for actor input: touch, mouse, keyboard, and
		 *            scroll.
		 */
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			disableButton();
			game.gamescreen.setLevel(4);
			dispose();
			game.gamescreen.gamearea();
			game.setScreen(game.gamescreen);
		}
	};
	// Instantiate a input listener called input5
	InputListener input5 = new InputListener() {
		/**
		 * called when a button goes down on the actor, if return true, it will
		 * receive all touch up events.
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
		 * called when a button goes up, the screen will move to the game screen
		 * at level5, then disable all the listener which come from the
		 * disableButton() class.
		 * 
		 * @param event
		 *            - Event for actor input: touch, mouse, keyboard, and
		 *            scroll.
		 */
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			disableButton();
			game.gamescreen.setLevel(5);
			dispose();
			game.gamescreen.gamearea();
			game.setScreen(game.gamescreen);
		}
	};
	// Instantiate a input listener called levelScreen2
	InputListener levelScreen2 = new InputListener() {
		/**
		 * called when a button goes down on the actor, if return true, it will
		 * receive all touch up events.
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
		 * called when a button goes up, the screen will move to LevelScreen2
		 * screen
		 * 
		 * @param event
		 *            - Event for actor input: touch, mouse, keyboard, and
		 *            scroll.
		 */
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			game.LevelScreen2.enableButtons();
			game.setScreen(game.LevelScreen2);
		}
	};
	// Instantiate a input listener called backmenu
	InputListener backmenu = new InputListener() {
		/**
		 * called when a button goes down on the actor, if return true, it will
		 * receive all touch up events.
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
		 * called when a button goes up, the screen will move to menu screen
		 * 
		 * @param event
		 *            - Event for actor input: touch, mouse, keyboard, and
		 *            scroll.
		 */
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			game.setScreen(game.MenuScreen);
		}
	};

	/**
	 * Instantiate a new instance of the LevelScreen1 class
	 * 
	 * @param game
	 *            - The current Breakout class
	 */
	public LevelScreen1(final Breakout game) {
		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.classpath("imgs/Level_Select1.png"));
		/*
		 * insert the button images and split to get each image button
		 */
		tex = new Texture(Gdx.files.classpath("imgs/buttons1.png"));
		tex2 = new Texture(Gdx.files.classpath("imgs/button.png"));
		TextureRegion[][] tmp = TextureRegion.split(tex, 350, 210);
		TextureRegion[][] tmp2 = TextureRegion.split(tex2, 130, 45);
		level1buttonUp = tmp[0][0];
		level1buttonDown = tmp[0][1];
		level2buttonUp = tmp[0][2];
		level2buttonDown = tmp[0][3];
		level3buttonUp = tmp[1][0];
		level3buttonDown = tmp[1][1];
		level4buttonUp = tmp[1][2];
		level4buttonDown = tmp[1][3];
		level5buttonUp = tmp[2][0];
		level5buttonDown = tmp[2][1];
		nextbuttonUp = tmp[2][2];
		nextbuttonDown = tmp[2][3];
		backbuttonUp = tmp2[2][2];
		backbuttonDown = tmp2[2][3];
		Button1();
		Button2();
		Button3();
		Button4();
		Button5();
		Next();
		backmenu();

		stage = new Stage(480, 640, true);
		/*
		 * add all buttons to the stage
		 */
		stage.addActor(level1button);
		stage.addActor(level2button);
		stage.addActor(level3button);
		stage.addActor(level4button);
		stage.addActor(level5button);
		stage.addActor(nextbutton);
		stage.addActor(backbutton);

	}

	@Override
	public void dispose() {
		texture.dispose();
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
	/**
	 * draw back ground and start stage
	 */
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

	/**
	 * remove all the buttons input listener
	 */
	public void disableButton() {
		level1button.removeListener(this.input1);
		level2button.removeListener(this.input2);
		level3button.removeListener(this.input3);
		level4button.removeListener(this.input4);
		level5button.removeListener(this.input5);
		nextbutton.removeListener(this.levelScreen2);
		backbutton.removeListener(this.backmenu);

	}
	
	/**
	 * Add all the button input listeners if they no longer exist
	 */
	public void enableButtons() {
		if (level1button.getCaptureListeners().contains(input1, true)) {
			return;
		}
		level1button.addListener(this.input1);
		level2button.addListener(this.input2);
		level3button.addListener(this.input3);
		level4button.addListener(this.input4);
		level5button.addListener(this.input5);
		nextbutton.addListener(this.levelScreen2);
		backbutton.addListener(this.backmenu);
	}

	/**
	 * creates button1 set the position and add the listener
	 */
	public void Button1() {

		level1up = new TextureRegionDrawable(level1buttonUp);
		level1down = new TextureRegionDrawable(level1buttonDown);
		level1button = new ImageButton(level1up, level1down);
		level1button.setPosition(50, 350);
		level1button.addListener(input1);

	}

	/**
	 * creates button2 set the position and add the listener
	 */
	public void Button2() {

		level2up = new TextureRegionDrawable(level2buttonUp);
		level2down = new TextureRegionDrawable(level2buttonDown);
		level2button = new ImageButton(level2up, level2down);
		level2button.setPosition(400, 350);
		level2button.addListener(input2);

	}

	/**
	 * creates button3 set the position and add the listener
	 */
	public void Button3() {

		level3up = new TextureRegionDrawable(level3buttonUp);
		level3down = new TextureRegionDrawable(level3buttonDown);
		level3button = new ImageButton(level3up, level3down);
		level3button.setPosition(750, 350);
		level3button.addListener(input3);

	}

	/**
	 * creates button4 set the position and add the listener
	 */
	public void Button4() {

		level4up = new TextureRegionDrawable(level4buttonUp);
		level4down = new TextureRegionDrawable(level4buttonDown);
		level4button = new ImageButton(level4up, level4down);
		level4button.setPosition(50, 90);
		level4button.addListener(input4);

	}

	/**
	 * creates button5 set the position and add the listener
	 */
	public void Button5() {

		level5up = new TextureRegionDrawable(level5buttonUp);
		level5down = new TextureRegionDrawable(level5buttonDown);
		level5button = new ImageButton(level5up, level5down);
		level5button.setPosition(400, 100);
		level5button.addListener(input5);

	}

	/**
	 * creates next button set the position and add the listener
	 */
	public void Next() {

		nextup = new TextureRegionDrawable(nextbuttonUp);
		nextdown = new TextureRegionDrawable(nextbuttonDown);
		nextbutton = new ImageButton(nextup, nextdown);
		nextbutton.setPosition(750, 100);
		nextbutton.addListener(levelScreen2);
	}

	/**
	 * creates back menu button set the position and add the listener
	 */
	public void backmenu() {

		backup = new TextureRegionDrawable(backbuttonUp);
		backdown = new TextureRegionDrawable(backbuttonDown);
		backbutton = new ImageButton(backup, backdown);
		backbutton.setPosition(0, 590);
		backbutton.addListener(backmenu);
	}
}
