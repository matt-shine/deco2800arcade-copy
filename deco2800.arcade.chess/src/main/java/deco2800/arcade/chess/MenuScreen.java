package deco2800.arcade.chess;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;


import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;

public class MenuScreen implements Screen {
	private NetworkClient networkClient;	
	 Player player;
	//@SuppressWarnings("unused")
	private Chess game;
    private Stage stage;
    private BitmapFont black;
    private BitmapFont white;
    private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch batch;
    private TextButton startButton;
    private TextButton helpButton;
    private TextButton exitButton;
    private Label label;
	
	public MenuScreen( Chess game){
		this.game = game;		
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
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		//FIXME gigantic method
		batch = new SpriteBatch();
        atlas = new TextureAtlas("images/button.pack");
        skin = new Skin();
        skin.addRegions(atlas);
        white = new BitmapFont(Gdx.files.internal("images/whitefont.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("images/font.fnt"), false);
        
        int width = game.SCREENWIDTH;
        int height = game.SCREENHEIGHT;
        
        stage = new Stage(width, height, true);
	
	    Gdx.input.setInputProcessor(stage);
	
	    TextButtonStyle style = new TextButtonStyle();
	    style.up = skin.getDrawable("buttonnormal");
	    style.down = skin.getDrawable("buttonpressed");
	    style.font = black;
	
	    startButton = new TextButton("Start", style);
	    helpButton = new TextButton("Help", style);
	    exitButton = new TextButton("Exit", style);
	    
	    startButton.setWidth(200);
	    startButton.setHeight(50);
	    helpButton.setWidth(200);
	    helpButton.setHeight(50);
	    exitButton.setWidth(200);
	    exitButton.setHeight(50);
	    
	    startButton.setX(width / 2 - startButton.getWidth() / 2);
	    helpButton.setX(width / 2 - helpButton.getWidth() / 2);
	    exitButton.setX(width / 2 - exitButton.getWidth() / 2);
	    
	    startButton.setY(height / 2 - startButton.getHeight() / 2);
	    helpButton.setY(startButton.getY() + (2 * (helpButton.getHeight() + 10)));
	    exitButton.setY(startButton.getY() - (helpButton.getHeight() + 10));
	    
	    startButton.addListener(new InputListener() {
	            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	                    return true;
	            }
	           
	            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	            	//game.setScreen(new PlayScreen(game));
	            		//game.setScreen(game);
	            }
	    });
	    
	    /*optionsButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(new Chess(player, networkClient));
            }
	    });*/
	    
	  
	    
	   /*
	    
	    exitButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            	ArcadeSystem.goToGame(ArcadeSystem.UI);
            }
	    });*/
	    
	    LabelStyle ls = new LabelStyle(white, Color.WHITE);
	    label = new Label("Chess Masters", ls);
	    label.setX(0);
	    label.setY(startButton.getY() + startButton.getHeight() + 10);
	    label.setWidth(width);
	    label.setAlignment(Align.center);
	
	    stage.addActor(startButton);
	    stage.addActor(exitButton);
	    stage.addActor(label);

	}

}
