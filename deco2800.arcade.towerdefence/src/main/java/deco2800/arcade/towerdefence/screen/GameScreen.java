package deco2800.arcade.towerdefence.screen;

import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.graphics.GL20.GL_DEPTH_BUFFER_BIT;

import java.awt.TextArea;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.towerdefence.TowerDefence;


public class GameScreen implements Screen{
	
	private TowerDefence game;
	
	private Stage stage;
	//private ShapeRenderer shapeRenderer;
	private Table table;
	private Table gameTable;
	private Table topStatus;
	private Table bottomBar, bottomBarRight, bottomBarLeft;
	private static float STATUS_HEIGHT = 50f;
	private static float BOTTOM_HEIGHT = 150f;
	Button frostB, fireB, holyB, darknessB, piercingB, auraB, backB; //TowerButtons
	private Label randomLabel3, towerInfo;
	BitmapFont black;
    BitmapFont white;
    Skin skin;
	TextureAtlas atlas;
	SpriteBatch batch;
	TextField resourceTF;
	private OrthographicCamera camera;
	private static final float BUTTON_HEIGHT = 64f, CAMERA_HEIGHT = (720- STATUS_HEIGHT - BOTTOM_HEIGHT), BUTTON_WIDTH = 64f;
	int resource; //int for player's resources.
	
	/*
	 * Constructor for GameScreen, creates the platform for which the game will be played.
	 * 720p (720 x 1024) pixels
	 */
	public GameScreen(TowerDefence game){
		this.game = game;
		stage = new Stage();
		camera = new OrthographicCamera(1024, CAMERA_HEIGHT);
		
		//setting style for resource textfield
		TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
	    textFieldStyle.font = new BitmapFont(Gdx.files.internal("white_font.fnt"), false);
	    textFieldStyle.fontColor = Color.WHITE;
	       
		resource = 0;
		resourceTF = new TextField("", textFieldStyle);
		
		//Need to work out how to add this sprite to the table.
		Texture crystalsTexture = new Texture(Gdx.files.internal("crystals.png"));
        crystalsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Sprite crystals = new Sprite(crystalsTexture);
		
		towerInfo = new Label("Blah BLAH Yada YADA\nTower Info goes here.", //Label for displaying each tower's info
				new Label.LabelStyle(new BitmapFont(),
				WHITE));
		towerInfo.setWrap(true);
		
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
		game.gameScreen.dispose();
	}

	@Override
	public void hide() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
	}

	@Override
	public void pause() {		
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		resourceTF.setMessageText("Resources: " + resource);
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
		if(!game.isPaused()) {
			stage.act(delta);
		}		
		//camera.update();
        //camera.apply(Gdx.gl10);
	}

	@Override
	public void resize(int width, int height) {
		if(stage == null) {
			stage = new Stage(width, height, true);
		} 
		
		//stage.setViewport(1280, 720, true);
				
		// Setting the "Style of a TextButton",
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = white;

		//tower buttons will probably change to Image button once we have mini-button-sized sprites.
		frostB = new TextButton("1", style);
		frostB.setWidth(BUTTON_WIDTH);
		frostB.setHeight(BUTTON_HEIGHT);
		frostB.addListener(new InputListener() { //adding listener
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		towerInfo.setText("Frost Tower\nCost: One Million Dollars >:)"); 
        	}
		 });
		frostB.setX(5);
		frostB.setY(69);

		fireB = new TextButton("2", style);
		fireB.setWidth(BUTTON_WIDTH);
		fireB.setHeight(BUTTON_HEIGHT);
		fireB.addListener(new InputListener() {
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		towerInfo.setText("Fire Tower\nCost: "); 
        	}
		 });
		fireB.setX(5);
		fireB.setY(5);
		
		holyB = new TextButton("3", style);
		holyB.setWidth(BUTTON_WIDTH);
		holyB.setHeight(BUTTON_HEIGHT);
		holyB.addListener(new InputListener() { 
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		towerInfo.setText("Holy Tower\nCost: "); 
        	}
		 });
		holyB.setX(frostB.getX() + BUTTON_WIDTH + 5);
		holyB.setY(69);
		
		
		darknessB = new TextButton("4", style);
		darknessB.setWidth(BUTTON_WIDTH);
		darknessB.setHeight(BUTTON_HEIGHT);
		darknessB.addListener(new InputListener() {
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		towerInfo.setText("Darkness Tower\nCost: "); 
        	}
		 });
		darknessB.setX(fireB.getX() + BUTTON_WIDTH + 5);
		darknessB.setY(5);
		
		piercingB = new TextButton("5", style);
		piercingB.setWidth(BUTTON_WIDTH);
		piercingB.setHeight(BUTTON_HEIGHT);
		piercingB.addListener(new InputListener() { 
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		towerInfo.setText("Piercing Tower\nCost: "); 
        	}
		 });
		piercingB.setX(holyB.getX() + BUTTON_WIDTH + 5);
		piercingB.setY(69);
		
		auraB = new TextButton("6", style);
		auraB.setWidth(BUTTON_WIDTH);
		auraB.setHeight(BUTTON_HEIGHT);
		auraB.addListener(new InputListener() { 
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		towerInfo.setText("Aura Tower\nCost: "); 
        	}
		 });
		auraB.setX(darknessB.getX() + BUTTON_WIDTH + 5);
		auraB.setY(5);
		
		backB = new TextButton("Back", style);
		backB.setWidth(BUTTON_WIDTH);
		backB.setHeight(BUTTON_HEIGHT);
		backB.addListener(new InputListener() { 
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        		game.setScreen(game.menuScreen); 
        	}
        });
		backB.setX(Gdx.graphics.getWidth() - BUTTON_WIDTH - 5);
		backB.setY(Gdx.graphics.getHeight() - BUTTON_HEIGHT - 5);
		
		resourceTF.setX(5);
		resourceTF.setY(Gdx.graphics.getHeight() - resourceTF.getHeight() - 5);
		
		towerInfo.setX(piercingB.getX() + towerInfo.getWidth() + 5);
		towerInfo.setY(69);
		
		//camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - BOTTOM_HEIGHT - STATUS_HEIGHT);
	
		stage.addActor(frostB);
		stage.addActor(fireB);
		stage.addActor(darknessB);
		stage.addActor(holyB);
		stage.addActor(auraB);
		stage.addActor(piercingB);
		stage.addActor(backB);
		stage.addActor(resourceTF);
		stage.addActor(towerInfo);
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		
		ArcadeInputMux.getInstance().addProcessor(stage);
		atlas = new TextureAtlas(Gdx.files.internal("black_button.pack"));
		skin = new Skin();
        skin.addRegions(atlas);
        white = new BitmapFont(Gdx.files.internal("white_font.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("black_font.fnt"), false);
        batch = new SpriteBatch();
	}
}
