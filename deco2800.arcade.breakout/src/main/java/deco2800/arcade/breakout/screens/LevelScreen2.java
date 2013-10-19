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
import deco2800.arcade.client.ArcadeSystem;

public class LevelScreen2 implements Screen {
	private final Breakout game;
	private final SpriteBatch batch;
	private final Texture texture;
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	Stage stage;
	/*
	 * creates instance variables for each image buttons.
	 */
	TextureRegionDrawable level6up;
	TextureRegionDrawable level6down;
	TextureRegionDrawable level7up;
	TextureRegionDrawable level7down;
	TextureRegionDrawable level8up;
	TextureRegionDrawable level8down;
	TextureRegionDrawable level9up;
	TextureRegionDrawable level9down;
	TextureRegionDrawable level10up;
	TextureRegionDrawable level10down;
	TextureRegionDrawable previousup;
	TextureRegionDrawable previousdown;
	TextureRegionDrawable backup;
	TextureRegionDrawable backdown;

	TextureRegion level6buttonUp;
	TextureRegion level6buttonDown;
	TextureRegion level7buttonUp;
	TextureRegion level7buttonDown;
	TextureRegion level8buttonUp;
	TextureRegion level8buttonDown;
	TextureRegion level9buttonUp;
	TextureRegion level9buttonDown;
	TextureRegion level10buttonUp;
	TextureRegion level10buttonDown;
	TextureRegion previousbuttonUp;
	TextureRegion previousbuttonDown;
	TextureRegion backbuttonUp;
	TextureRegion backbuttonDown;

	Texture tex;
	Texture tex2;
	ImageButton level6button;
	ImageButton level7button;
	ImageButton level8button;
	ImageButton level9button;
	ImageButton level10button;
	ImageButton previousbutton;
	ImageButton backbutton;
	// Instantiate a input listener called input6
	InputListener input6 = new InputListener() {
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
		 * at level6, then disable all the listener which come from the
		 * disableButton() class.
		 * 
		 * @param event
		 *            - Event for actor input: touch, mouse, keyboard, and
		 *            scroll.
		 */
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			disableButton();
			game.gamescreen.setLevel(6);
			dispose();
			game.gamescreen.gamearea();
			game.setScreen(game.gamescreen);
		}
	};
	// Instantiate a input listener called input7
	InputListener input7 = new InputListener() {
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
		 * at level7, then disable all the listener which come from the
		 * disableButton() class.
		 * 
		 * @param event
		 *            - Event for actor input: touch, mouse, keyboard, and
		 *            scroll.
		 */
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			disableButton();
			game.gamescreen.setLevel(7);
			dispose();
			game.gamescreen.gamearea();
			game.setScreen(game.gamescreen);
		}
	};
	// Instantiate a input listener called input8
	InputListener input8 = new InputListener() {
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
		 * at level8, then disable all the listener which come from the
		 * disableButton() class.
		 * 
		 * @param event
		 *            - Event for actor input: touch, mouse, keyboard, and
		 *            scroll.
		 */
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			disableButton();
			game.gamescreen.setLevel(8);
			dispose();
			game.gamescreen.gamearea();
			game.setScreen(game.gamescreen);
		}
	};
	// Instantiate a input listener called input9
	InputListener input9 = new InputListener() {
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
		 * at level9, then disable all the listener which come from the
		 * disableButton() class.
		 * 
		 * @param event
		 *            - Event for actor input: touch, mouse, keyboard, and
		 *            scroll.
		 */
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			disableButton();
			game.gamescreen.setLevel(9);
			dispose();
			game.gamescreen.gamearea();
			game.setScreen(game.gamescreen);
		}
	};
	// Instantiate a input listener called input10
	InputListener input10 = new InputListener() {
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
		 * at level10, then disable all the listener which come from the
		 * disableButton() class.
		 * 
		 * @param event
		 *            - Event for actor input: touch, mouse, keyboard, and
		 *            scroll.
		 */
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) { // on button release do this
			disableButton();
			game.gamescreen.setLevel(10);
			dispose();
			game.gamescreen.gamearea();
			game.setScreen(game.gamescreen);

		}
	};
	// Instantiate a input listener called levelScreen1
	InputListener levelScreen1 = new InputListener() {
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
		 * called when a button goes up, the screen will move to the
		 * levelScreen1 screen
		 * 
		 * @param event
		 *            - Event for actor input: touch, mouse, keyboard, and
		 *            scroll.
		 */
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			game.setScreen(game.LevelScreen1);
		}
	};
	// Instantiate a input listener called backmenu screen
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
		 * called when a button goes up, the screen will move to the menu screen
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
	 * Instantiate a new instance of the LevelScreen2 class
	 * 
	 * @param game
	 *            - The current Breakout class
	 */
	public LevelScreen2(final Breakout game) {

		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.classpath("imgs/Level_Select2.png"));
		/*
		 * insert the button images and split to get each image button
		 */
		tex = new Texture(Gdx.files.classpath("imgs/buttons2.png"));
		tex2 = new Texture(Gdx.files.classpath("imgs/button.png"));
		TextureRegion[][] tmp = TextureRegion.split(tex, 350, 210);
		TextureRegion[][] tmp2 = TextureRegion.split(tex2, 130, 45);

		level6buttonUp = tmp[0][0];
		level6buttonDown = tmp[0][1];
		level7buttonUp = tmp[0][2];
		level7buttonDown = tmp[0][3];
		level8buttonUp = tmp[1][0];
		level8buttonDown = tmp[1][1];
		level9buttonUp = tmp[1][2];
		level9buttonDown = tmp[1][3];
		level10buttonUp = tmp[2][0];
		level10buttonDown = tmp[2][1];
		previousbuttonUp = tmp[2][2];
		previousbuttonDown = tmp[2][3];
		backbuttonUp = tmp2[2][2];
		backbuttonDown = tmp2[2][3];

		Button6();
		Button7();
		Button8();
		Button9();
		Button10();
		buttonPrevious();
		backmenu();

		stage = new Stage(480, 640, true);
		/*
		 * add all buttons to the stage
		 */
		stage.addActor(level6button);
		stage.addActor(level7button);
		stage.addActor(level8button);
		stage.addActor(level9button);
		stage.addActor(level10button);
		stage.addActor(previousbutton);
		stage.addActor(backbutton);
	}

	/**
	 * remove all the buttons input listener
	 */
	public void disableButton() {
		level6button.removeListener(this.input6);
		level7button.removeListener(this.input7);
		level8button.removeListener(this.input8);
		level9button.removeListener(this.input9);
		level10button.removeListener(this.input10);
		previousbutton.removeListener(this.levelScreen1);
		backbutton.removeListener(this.backmenu);
	}
	
	/**
	 * Add all the button input listeners if they no longer exist
	 */
	public void enableButtons() {
		if (level6button.getCaptureListeners().contains(input6, true)) {
			return;
		}
		level6button.addListener(this.input6);
		level7button.addListener(this.input7);
		level8button.addListener(this.input8);
		level9button.addListener(this.input9);
		level10button.addListener(this.input10);
		previousbutton.addListener(this.levelScreen1);
		backbutton.addListener(this.backmenu);
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
	 * creates button6 set the position and add the listener
	 */
	public void Button6() {
		level6up = new TextureRegionDrawable(level6buttonUp);
		level6down = new TextureRegionDrawable(level6buttonDown);
		level6button = new ImageButton(level6up, level6down);
		level6button.setPosition(50, 350);
		level6button.addListener(input6);
	}

	/**
	 * creates button7 set the position and add the listener
	 */
	public void Button7() {
		level7up = new TextureRegionDrawable(level7buttonUp);
		level7down = new TextureRegionDrawable(level7buttonDown);
		level7button = new ImageButton(level7up, level7down);
		level7button.setPosition(400, 350);
		level7button.addListener(input7);
	}

	/**
	 * creates button8 set the position and add the listener
	 */
	public void Button8() {
		level8up = new TextureRegionDrawable(level8buttonUp);
		level8down = new TextureRegionDrawable(level8buttonDown);
		level8button = new ImageButton(level8up, level8down);
		level8button.setPosition(750, 350);
		level8button.addListener(input8);
	}

	/**
	 * creates button9 set the position and add the listener
	 */
	public void Button9() {
		level9up = new TextureRegionDrawable(level9buttonUp);
		level9down = new TextureRegionDrawable(level9buttonDown);
		level9button = new ImageButton(level9up, level9down);
		level9button.setPosition(50, 90);
		level9button.addListener(input9);
	}

	/**
	 * creates button10 set the position and add the listener
	 */
	public void Button10() {
		level10up = new TextureRegionDrawable(level10buttonUp);
		level10down = new TextureRegionDrawable(level10buttonDown);
		level10button = new ImageButton(level10up, level10down);
		level10button.setPosition(400, 100);
		level10button.addListener(input10);
	}

	/**
	 * creates button previous button set the position and add the listener
	 */
	public void buttonPrevious() {
		previousup = new TextureRegionDrawable(previousbuttonUp);
		previousdown = new TextureRegionDrawable(previousbuttonDown);
		previousbutton = new ImageButton(previousup, previousdown);
		previousbutton.setPosition(750, 100);
		previousbutton.addListener(levelScreen1);
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
