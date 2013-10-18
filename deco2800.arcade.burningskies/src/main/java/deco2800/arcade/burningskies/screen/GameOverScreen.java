package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.client.ArcadeInputMux;

public class GameOverScreen implements Screen {

	private BurningSkies game;
    private Stage stage;
    private SpriteBatch batch;
    private TextButton submitButton;
    private Image background;
    private Skin skin;
	private MenuInputProcessor processor;
	private TextField playerNameInput;
	private String playerName;

	private int width = BurningSkies.SCREENWIDTH;
    private int height = BurningSkies.SCREENHEIGHT;

    
	public GameOverScreen( BurningSkies game){
		this.game = game;		
	}

	@Override
	public void dispose() {
		batch.dispose();
        skin.dispose();
        stage.dispose();
	}

	@Override
	public void hide() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
		ArcadeInputMux.getInstance().removeProcessor(processor);
		this.dispose();
	}

	@Override
	public void pause() {
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        batch.begin();
        stage.draw();
        batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("images/menu/uiskin32.json"));
        background = new Image(new Texture(Gdx.files.internal("images/menu/game_over.png")));
        
        stage = new Stage(width, height, true);
        ArcadeInputMux.getInstance().addProcessor(stage);
        
        processor = new MenuInputProcessor(game);
    	ArcadeInputMux.getInstance().addProcessor(processor);
    	
    	playerNameInput = new TextField("Enter your name", skin);
    	playerNameInput.setWidth(200);
    	playerNameInput.setHeight(50);
    	playerNameInput.setX((width/2) - playerNameInput.getWidth());
    	playerNameInput.setY(height/2 + playerNameInput.getHeight()/2);
    	
	    submitButton = new TextButton("Submit", skin);
	    submitButton.setWidth(200);
	    submitButton.setHeight(50);

	    submitButton.setX(width/2);
	    submitButton.setY(height/2 + submitButton.getHeight()/2);
	    
	    submitButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {        		
        		playerName = playerNameInput.getMessageText();
        		
//        		if (playerName.length() > 3) {
//        			// Display a popup saying that only the first three characters will be used
//        		}
            	game.setScreen(game.scoreScreen);
            }
		});

	    stage.addActor(background);
	    stage.addActor(submitButton);
	    stage.addActor(playerNameInput);
	}
	
	public String getLastScoreName() {
		return playerName;
	}
}
