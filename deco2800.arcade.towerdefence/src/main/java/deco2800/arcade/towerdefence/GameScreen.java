package deco2800.arcade.towerdefence;

import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.graphics.GL20.GL_DEPTH_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;


public class GameScreen implements Screen{
	
	private TowerDefence game;
	
	private Stage stage;
	//private ShapeRenderer shapeRenderer;
	private Table table;
	private Table gameTable;
	private Table topStatus;
	private Table bottomBar, bottomBarRight, bottomBarLeft;
	private static float STATUS_HEIGHT = 50f;
	private static float BOTTOM_HEIGHT = 128f;
	Button tower1, tower2, tower3, tower4; //initialising some buttons for gameScreen
	private Label randomLabel;
	private Label randomLabel2;
	private Label randomLabel3;
	BitmapFont black;
    BitmapFont white;
	TextureAtlas atlas;
	Skin skin;
	SpriteBatch batch;
	private static final float BUTTON_HEIGHT = 64f;
	private static final float BUTTON_WIDTH = 64f;
	
	/*
	 * Constructor for GameScreen, creates the platform for which the game will be played.
	 */
	public GameScreen(TowerDefence game){
		this.game = game;
		stage = new Stage();
		table = new Table();
		gameTable = new Table();
		bottomBar = new Table();
		bottomBarLeft = new Table();
		bottomBarRight = new Table();
		table.setFillParent(true);
		table.debug();
		bottomBar.debug();
		
		randomLabel = new Label("Imagine this is a status bar", 
				new Label.LabelStyle(new BitmapFont(),
				CYAN));
		
		randomLabel2 = new Label("Tower Info over here.", 
				new Label.LabelStyle(new BitmapFont(),
				RED));
		
		randomLabel3 = new Label("Game screen here?", 
				new Label.LabelStyle(new BitmapFont(),
				MAGENTA));
	}

	@Override
	public void dispose() {
		batch.dispose();
		skin.dispose();
		atlas.dispose();
		white.dispose();
		black.dispose();
		stage.dispose();
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0.2f, 0.2f, 0, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
		
	}

	@Override
	public void resize(int width, int height) {
		if(stage == null) {
			stage = new Stage(width, height, true);
		}
		stage.clear();
		//stage.setViewport(1280, 720, true);
				
		// Setting the "Style of a TextButton",
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = black;

		//tower buttons will probably change to Image button once we have mini-button-sized sprites.
		tower1 = new TextButton("1", style);
		tower1.setWidth(BUTTON_WIDTH);
		tower1.setHeight(BUTTON_HEIGHT);

		tower2 = new TextButton("2", style);
		tower2.setWidth(BUTTON_WIDTH);
		tower2.setHeight(BUTTON_HEIGHT);
		
		tower3 = new TextButton("3", style);
		tower3.setWidth(BUTTON_WIDTH);
		tower3.setHeight(BUTTON_HEIGHT);
		
		tower4 = new TextButton("4", style);
		tower4.setWidth(BUTTON_WIDTH);
		tower4.setHeight(BUTTON_HEIGHT);

		table.add(randomLabel).top().height(STATUS_HEIGHT).expandX().fill(); // row	 for top status bar
		table.row();
		table.add(randomLabel3).center().expandY().fill(); // row for gameTable
		table.row();
		bottomBarLeft.add(tower1).left();
		bottomBarLeft.add(tower2);
		bottomBarLeft.row();
		bottomBarLeft.add(tower3).left();
		bottomBarLeft.add(tower4);
		bottomBarRight.add(randomLabel2);
		bottomBar.add(bottomBarLeft).expandY().left();
		bottomBar.add(bottomBarRight).right().expandX().fill();
		table.add(bottomBar).bottom().height(BOTTOM_HEIGHT); // row for bottomBar
		//table.add(bottomBarRight);
		
		stage.addActor(table);
		stage.addActor(bottomBar);
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		atlas = new TextureAtlas(Gdx.files.internal("button.pack"));
		skin = new Skin();
        skin.addRegions(atlas);
        white = new BitmapFont(Gdx.files.internal("white_font.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("black_font.fnt"), false);		
	}
}
