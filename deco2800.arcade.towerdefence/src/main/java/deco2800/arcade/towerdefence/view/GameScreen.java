package deco2800.arcade.towerdefence.view;

import static com.badlogic.gdx.graphics.Color.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.towerdefence.controller.TowerDefence;
import deco2800.arcade.towerdefence.model.GridObject;

/* GameScreen is where the game will take place
 * There are many buttons on the HUD for selecting different towers, etc.
 * @author Tuddz
 */

public class GameScreen implements Screen {

	private TowerDefence game;

	private Stage stage, hudStage;
	// unused
	// private static float STATUS_HEIGHT = 50f;
	// private static float BOTTOM_HEIGHT = 150f;
	Button standardB, fireB, holyB, cryoB, piercingB, barricadeB, backB;
	private Label towerInfo;
	BitmapFont black;
	BitmapFont white;
	TextButtonStyle style;
	Skin skin;
	TextureAtlas atlas, barsAtlas;
	TextureRegion healthBarRegion, attackBarRegion, costBarRegion,
			penetrationBarRegion, armorBarRegion;
	TexturePart healthBar, attackBar, costBar, penetrationBar, armorBar;
	Image gridMap;
	SpriteBatch batch;
	TextField resourceTF;
	private OrthographicCamera camera;
	private static final float BUTTON_HEIGHT = 64f;
	// unused
	// private static final float CAMERA_HEIGHT = (720 - STATUS_HEIGHT -
	// BOTTOM_HEIGHT);
	private static final float BUTTON_WIDTH = 64f;
	int resource; /* int for player's resources. */

	Texture crystalsTexture, gridMapTexture;

	String fireStr, cryoStr, piercingStr, holyStr, attackStr, standardStr,
			barricadeStr, defenceStr;

	/*
	 * Constructor for GameScreen, creates the platform for which the game will
	 * be played. 720p (720 x 1024) pixels
	 */
	public GameScreen(TowerDefence game) {
		this.game = game;
		stage = new Stage();
		stage.setViewport(4000, 4000, true);
		hudStage = new Stage();
		hudStage.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true);

		/* setting style for resource textfield */
		TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
		textFieldStyle.font = new BitmapFont(
				Gdx.files.internal("black_font.fnt"), false);
		textFieldStyle.fontColor = Color.BLACK;

		resource = 0;
		resourceTF = new TextField("", textFieldStyle);

		crystalsTexture = new Texture(Gdx.files.internal("crystals.png"));
		crystalsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		gridMapTexture = new Texture(Gdx.files.internal("gridMap.png"));
		gridMapTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		gridMap = new Image(gridMapTexture);

		towerInfo = new Label("..............", new Label.LabelStyle(
				new BitmapFont(), BLACK));
		towerInfo.setWrap(true);

		resource = 2000;
		fireStr = "Fire I: \n" + "Cost: 200\n" + "Max Health: 100\n"
				+ "Damage: 15";
		cryoStr = "Cryo I: \n" + "Cost: 200\n" + "Max Health: 100\n"
				+ "Damage: 15";
		piercingStr = "Piercing I: \n" + "Cost: 200\n" + "Max Health: 100\n"
				+ "Damage: 15";
		holyStr = "Holy I: \n" + "Cost: 250\n" + "Max Health: 100\n"
				+ "Damage: 15";
		barricadeStr = "Barricade I: \n" + "Cost: 100\n" + "Max Health: 200\n"
				+ "Damage: 0";
		standardStr = "Standard I: \n" + "Cost: 100\n" + "Max Health: 100\n"
				+ "Damage: 10";

	}

	@Override
	public void dispose() {
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		white.dispose();
		black.dispose();
		stage.dispose();
		hudStage.dispose();
		game.gameScreen.dispose();
	}

	@Override
	public void hide() {
		ArcadeInputMux.getInstance().removeProcessor(hudStage);
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		handleInput();
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		camera.update();

		resourceTF.setMessageText("" + resource);

		stage.act(delta);
		stage.draw();

		hudStage.act(delta);
		hudStage.draw();

		Table.drawDebug(hudStage);
		if (!game.isPaused()) {
			stage.act(delta);
			hudStage.act(delta);
		}

		batch.begin();
		batch.draw(crystalsTexture, 5, Gdx.graphics.getHeight()
				- crystalsTexture.getHeight() - 5);
		healthBar.Draw(batch);
		attackBar.Draw(batch);
		costBar.Draw(batch);
		penetrationBar.Draw(batch);
		armorBar.Draw(batch);
		batch.end();

	}

	/*
	 * Input handler for navigating the map Taken from libGDX google docs,
	 * edited.
	 */
	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (camera.position.x > 0)
				camera.translate(-3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (camera.position.x < 4000)
				camera.translate(3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (camera.position.y > 0)
				camera.translate(0, -3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (camera.position.y < 4000)
				camera.translate(0, 3, 0);
		}
	}

	@Override
	public void resize(int width, int height) {
		if (hudStage == null) {
			hudStage = new Stage(width, height, true);
		}
		if (stage == null) {
			stage = new Stage(4000, 4000, true);
		}
		camera = (OrthographicCamera) stage.getCamera();
		camera.setToOrtho(false, width, height);

		standardB = new TextButton("ST", style);
		standardB.setWidth(BUTTON_WIDTH);
		standardB.setHeight(BUTTON_HEIGHT);
		standardB.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				towerInfo.setText(standardStr);
			}
		});
		standardB.setX(5);
		standardB.setY(69);

		fireB = new TextButton("FI", style);
		fireB.setWidth(BUTTON_WIDTH);
		fireB.setHeight(BUTTON_HEIGHT);
		fireB.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				towerInfo.setText(fireStr);
			}
		});
		fireB.setX(5);
		fireB.setY(5);

		holyB = new TextButton("HO", style);
		holyB.setWidth(BUTTON_WIDTH);
		holyB.setHeight(BUTTON_HEIGHT);
		holyB.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				towerInfo.setText(holyStr);
			}
		});
		holyB.setX(standardB.getX() + BUTTON_WIDTH + 5);
		holyB.setY(69);

		cryoB = new TextButton("CR", style);
		cryoB.setWidth(BUTTON_WIDTH);
		cryoB.setHeight(BUTTON_HEIGHT);
		cryoB.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				towerInfo.setText(cryoStr);
			}
		});
		cryoB.setX(fireB.getX() + BUTTON_WIDTH + 5);
		cryoB.setY(5);

		piercingB = new TextButton("PI", style);
		piercingB.setWidth(BUTTON_WIDTH);
		piercingB.setHeight(BUTTON_HEIGHT);
		piercingB.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				towerInfo.setText(piercingStr);
			}
		});
		piercingB.setX(holyB.getX() + BUTTON_WIDTH + 5);
		piercingB.setY(69);

		barricadeB = new TextButton("BA", style);
		barricadeB.setWidth(BUTTON_WIDTH);
		barricadeB.setHeight(BUTTON_HEIGHT);
		barricadeB.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				towerInfo.setText(barricadeStr);
			}
		});
		barricadeB.setX(cryoB.getX() + BUTTON_WIDTH + 5);
		barricadeB.setY(5);

		backB = new TextButton("X", style);
		backB.setWidth(BUTTON_WIDTH);
		backB.setHeight(BUTTON_HEIGHT);
		backB.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(game.menuScreen);
			}
		});
		backB.setX(Gdx.graphics.getWidth() - BUTTON_WIDTH - 5);
		backB.setY(Gdx.graphics.getHeight() - BUTTON_HEIGHT - 5);

		resourceTF.setX(45);
		resourceTF.setY(Gdx.graphics.getHeight() - resourceTF.getHeight() - 15);
		resourceTF.setDisabled(true);

		towerInfo.setX(piercingB.getX() + piercingB.getWidth() + 10);
		towerInfo.setY(69);
		towerInfo.setWidth(128);

		/* adding bars for showing information */
		healthBar = new TexturePart(healthBarRegion, towerInfo.getX()
				+ towerInfo.getWidth() + 20, towerInfo.getY() + 50);
		attackBar = new TexturePart(attackBarRegion, healthBar.getX(),
				healthBar.getY() - 26);
		costBar = new TexturePart(costBarRegion, healthBar.getX(),
				attackBar.getY() - 26);
		penetrationBar = new TexturePart(penetrationBarRegion,
				healthBar.getX(), costBar.getY() - 26);
		armorBar = new TexturePart(armorBarRegion, healthBar.getX(),
				penetrationBar.getY() - 26);

		// gridMap.setOrigin(0, 0);

		/* adding actors to the HUD */
		hudStage.addActor(standardB);
		hudStage.addActor(fireB);
		hudStage.addActor(cryoB);
		hudStage.addActor(holyB);
		hudStage.addActor(barricadeB);
		hudStage.addActor(piercingB);
		hudStage.addActor(backB);
		hudStage.addActor(resourceTF);
		hudStage.addActor(towerInfo);
		/* adding actors for the camera */
		// stage.addActor(randomBut2);
		stage.addActor(gridMap);

		camera.position.set(2000, 2000, 0);
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		ArcadeInputMux.getInstance().addProcessor(hudStage);

		/* adding styles for buttons */
		atlas = new TextureAtlas(Gdx.files.internal("black_button.pack"));
		skin = new Skin();
		skin.addRegions(atlas);
		white = new BitmapFont(Gdx.files.internal("white_font.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("black_font.fnt"), false);

		/* Setting the "Style of a TextButton" */
		style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = white;

		batch = new SpriteBatch();

		/* adding textures for the coloured bars */
		barsAtlas = new TextureAtlas(Gdx.files.internal("Bars.pack"));
		healthBarRegion = barsAtlas.findRegion("Red_Bar");
		attackBarRegion = barsAtlas.findRegion("Blue_Bar");
		costBarRegion = barsAtlas.findRegion("Orange_Bar");
		penetrationBarRegion = barsAtlas.findRegion("Purple_Bar");
		armorBarRegion = barsAtlas.findRegion("White_Bar");
	}

	// Methods
	/**
	 * Create List of sprites from frame filenames with set rotation.
	 * @param object
	 * @param filenames
	 * @param rotation
	 */
	public List<Sprite> spriteBuild(GridObject object, List<String> filenames){
		
		List<Sprite> builtSprites = new ArrayList<Sprite>();
		// Iterate over the list of filenames
		for (int i=0; i<filenames.size();i++){
			Texture texture = new Texture(Gdx.files.internal(filenames.get(i)));
			Sprite sprite = new Sprite(texture);
			sprite.setPosition(object.position().x, object.position().y);
			sprite.setRotation(object.rotation());
			builtSprites.add(sprite);
		}
		return builtSprites;
		
	}
	
	/**
	 * Draw sprites to the screen.
	 * @param sprites
	 */
	public void animate(List<Sprite> sprites) {
		// Create an Iterator
		Iterator<Sprite> sprIter = sprites.iterator();
		// So long as there's more sprites in the animation
		while (sprIter.hasNext()) {
			// Get the next frame
			Sprite currentFrame = sprIter.next();
			batch.begin();
			currentFrame.draw(batch);
			batch.end();
		}

	}
}