package deco2800.arcade.towerdefence.view;

import static com.badlogic.gdx.graphics.Color.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
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
import deco2800.arcade.towerdefence.controller.TowerDefence;
import deco2800.arcade.towerdefence.view.TexturePart;

/* GameScreen is where the game will take place
 * There are many buttons on the HUD for selecting different towers, etc.
 * @author Tuddz
 */
public class GameScreen implements Screen{
	
	private TowerDefence game;
	
	private Stage stage, hudStage;
	//private ShapeRenderer shapeRenderer;
	private static float STATUS_HEIGHT = 50f;
	private static float BOTTOM_HEIGHT = 150f;
	Button frostB, fireB, holyB, darknessB, piercingB, auraB, backB, randomBut1, randomBut2; //TowerButtons
	private Label towerInfo;
	BitmapFont black;
    BitmapFont white;
    Skin skin;
	TextureAtlas atlas, barsAtlas;
	TextureRegion healthBarRegion, attackBarRegion, costBarRegion, penetrationBarRegion, armorBarRegion;
	TexturePart healthBar, attackBar, costBar, penetrationBar, armorBar;
	SpriteBatch batch;
	TextField resourceTF;
	private OrthographicCamera camera;
	private static final float BUTTON_HEIGHT = 64f, CAMERA_HEIGHT = (720- STATUS_HEIGHT - BOTTOM_HEIGHT), BUTTON_WIDTH = 64f;
	int resource; //int for player's resources.
	
	//static final int WIDTH  = 480;
    //static final int HEIGHT = 320;

	Texture crystalsTexture;
    private float rotationSpeed;
	
	/*
	 * Constructor for GameScreen, creates the platform for which the game will be played.
	 * 720p (720 x 1024) pixels
	 */
	public GameScreen(TowerDefence game){
		this.game = game;
		stage = new Stage();
		hudStage = new Stage();
		hudStage.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - BOTTOM_HEIGHT - STATUS_HEIGHT, true);
		
		//setting style for resource textfield
		TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
	    textFieldStyle.font = new BitmapFont(Gdx.files.internal("white_font.fnt"), false);
	    textFieldStyle.fontColor = Color.WHITE;
	       
		resource = 0;
		resourceTF = new TextField("", textFieldStyle);
		
		//Need to work out how to add this sprite to the table.
		crystalsTexture = new Texture(Gdx.files.internal("crystals.png"));
        crystalsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Sprite crystals = new Sprite(crystalsTexture);
		
		towerInfo = new Label("......\n......\nTower Info goes here.", //Label for displaying each tower's info
				new Label.LabelStyle(new BitmapFont(),
				WHITE));
		towerInfo.setWrap(true);
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
		if(!game.isPaused()) {			
			stage.act(delta);
			hudStage.act(delta);
		}
		
		batch.begin();
		batch.draw(crystalsTexture, 5, Gdx.graphics.getHeight() - crystalsTexture.getHeight() - 5);
		healthBar.Draw(batch);
		attackBar.Draw(batch);
		costBar.Draw(batch);
		penetrationBar.Draw(batch);
		armorBar.Draw(batch);
		batch.end();
		
	}
	
	/* Taken from libGDX google docs, tweaked very slightly
	 * 
	 */
	private void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
                camera.zoom += 0.02;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
                camera.zoom -= 0.02;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if (camera.position.x > 0)
                        camera.translate(-3, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                if (camera.position.x < 1024)
                        camera.translate(3, 0, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (camera.position.y > 0)
                        camera.translate(0, -3, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if (camera.position.y < 1024)
                        camera.translate(0, 3, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
                camera.rotate(-rotationSpeed, 0, 0, 1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
                camera.rotate(rotationSpeed, 0, 0, 1);
        }
}

	@Override
	public void resize(int width, int height) {
		if(hudStage == null) {
			hudStage = new Stage(width, height, true);
		}
		if(stage == null) {
			stage = new Stage(width, height, true);
		}
		camera = (OrthographicCamera) stage.getCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				
		// Setting the "Style of a TextButton",
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = white;

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
		
		backB = new TextButton("X", style);
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
		
		//random buttons for testing the stage-camera movement
		randomBut1 = new TextButton("T", style);
		randomBut1.setWidth(BUTTON_WIDTH);
		randomBut1.setHeight(BUTTON_HEIGHT);
		randomBut1.addListener(new InputListener() { 
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        	}
		 });
		randomBut1.setX(200);
		randomBut1.setY(200);
		
		randomBut2 = new TextButton("T", style);
		randomBut2.setWidth(BUTTON_WIDTH);
		randomBut2.setHeight(BUTTON_HEIGHT);
		randomBut2.addListener(new InputListener() { 
        	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { //touch down method is needed for the rest to work
        		return true; //do nothing
        	}
        	
        	public void touchUp(InputEvent event, float x, float y, int pointer, int button) { //on button release do this
        	}
		 });
		randomBut2.setX(300);
		randomBut2.setY(300);
		
		resourceTF.setX(45);
		resourceTF.setY(Gdx.graphics.getHeight() - resourceTF.getHeight() - 15);
		resourceTF.setDisabled(true);
		
		towerInfo.setX(piercingB.getX() + towerInfo.getWidth()/2 + 10);
		towerInfo.setY(69);
		
		//adding bars for showing information
		healthBar = new TexturePart(healthBarRegion, towerInfo.getX() + towerInfo.getWidth() + 20, towerInfo.getY() + 50);
		attackBar = new TexturePart(attackBarRegion, healthBar.position.x, healthBar.position.y - 26);
		costBar = new TexturePart(costBarRegion, healthBar.position.x, attackBar.position.y- 26);
		penetrationBar = new TexturePart(penetrationBarRegion, healthBar.position.x, costBar.position.y - 26);
		armorBar = new TexturePart(armorBarRegion, healthBar.position.x, penetrationBar.position.y - 26);
		
		//adding actors to the HUD
		hudStage.addActor(frostB);
		hudStage.addActor(fireB);
		hudStage.addActor(darknessB);
		hudStage.addActor(holyB);
		hudStage.addActor(auraB);
		hudStage.addActor(piercingB);
		hudStage.addActor(backB);
		hudStage.addActor(resourceTF);
		hudStage.addActor(towerInfo);
		//adding actors for the camera
		stage.addActor(randomBut2);
		stage.addActor(randomBut1);
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		ArcadeInputMux.getInstance().addProcessor(hudStage);
		
		//adding styles for buttons
		atlas = new TextureAtlas(Gdx.files.internal("black_button.pack"));
		skin = new Skin();
        skin.addRegions(atlas);
        white = new BitmapFont(Gdx.files.internal("white_font.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("black_font.fnt"), false);
        
        batch = new SpriteBatch();
        
        //adding textures for the coloured bars
        barsAtlas = new TextureAtlas(Gdx.files.internal("Bars.pack"));
		healthBarRegion = barsAtlas.findRegion("Red_Bar");
		attackBarRegion = barsAtlas.findRegion("Blue_Bar");
		costBarRegion = barsAtlas.findRegion("Orange_Bar");
		penetrationBarRegion = barsAtlas.findRegion("Purple_Bar");
		armorBarRegion = barsAtlas.findRegion("White_Bar");
	}
}
