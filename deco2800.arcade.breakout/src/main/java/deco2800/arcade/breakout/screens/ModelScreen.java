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

public class ModelScreen implements Screen {
	private final Breakout game;
	private final SpriteBatch batch;
	private final Texture texture;
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	private Stage stage;
	private TextureRegionDrawable model1up;
	private TextureRegionDrawable model1down;
	private TextureRegionDrawable backup;
	private TextureRegionDrawable backdown;
	private TextureRegion model1Up;
	private TextureRegion model1Down;
	private TextureRegion model1buttonUp;
	private TextureRegion model1buttonDown;
	private TextureRegion backUp;
	private TextureRegion backDown;
	private TextureRegion backbuttonUp;
	private TextureRegion backbuttonDown;
	private TextureRegionDrawable model2up;
	private TextureRegionDrawable model2down;
	private TextureRegion model2Up;
	private TextureRegion model2Down;
	private TextureRegion model2buttonUp;
	private TextureRegion model2buttonDown;

	private Texture tex;
	private Texture tex2;

	private ImageButton model1button;
	private ImageButton model2button;
	private ImageButton backbutton;

	public ModelScreen(final Breakout game) {
		this.game = game;
		batch = new SpriteBatch();
		Texture.setEnforcePotImages(false);
		texture = new Texture(Gdx.files.classpath("imgs/ModeSelection.png"));
		tex = new Texture(Gdx.files.classpath("imgs/mode_select.png"));
		tex2 = new Texture(Gdx.files.classpath("imgs/button.png"));
		TextureRegion[][] tmp = TextureRegion.split(tex, 565, 350);

		TextureRegion[][] tmp2 = TextureRegion.split(tex2, 130, 45);

		// classic mode
		model1buttonUp = tmp[0][0];
		model1buttonDown = tmp[0][1];
		model1up = new TextureRegionDrawable(model1buttonUp);
		model1down = new TextureRegionDrawable(model1buttonDown);
		model1button = new ImageButton(model1up, model1down);
		model1button.setPosition(0, 120);

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
		// enhanced mode
		model2buttonUp = tmp[1][0];
		model2buttonDown = tmp[1][1];
		model2up = new TextureRegionDrawable(model2buttonUp);
		model2down = new TextureRegionDrawable(model2buttonDown);
		model2button = new ImageButton(model2up, model2down);
		model2button.setPosition(550, 100);

		model2button.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) { 
												

				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) { 
				game.getGamescreen().switchGameMode(true);
				game.getLevelScreen1().enableButtons();
				game.setScreen(game.getLevelScreen1());

			}
		});
		// back menu
		backbuttonUp = tmp2[2][2];
		backbuttonDown = tmp2[2][3];
		backup = new TextureRegionDrawable(backbuttonUp);
		backdown = new TextureRegionDrawable(backbuttonDown);
		backbutton = new ImageButton(backup, backdown);
		backbutton.setPosition(0, 590);
		
		backbutton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) { 
												

				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) { 
				game.setScreen(game.getMenuScreen());

			}
		});
		stage = new Stage(480, 640, true);
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
