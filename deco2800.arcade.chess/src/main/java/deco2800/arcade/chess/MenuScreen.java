package deco2800.arcade.chess;


import deco2800.arcade.client.ArcadeInputMux;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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


import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;

public class MenuScreen implements Screen {
	
	//@SuppressWarnings("unused")
	Texture splashTexture;
	Texture splashTexture2;
	Sprite splashSprite;
	private Chess game;
    private Stage stage;
    private BitmapFont black;
    private BitmapFont white;
    private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch batch;
    private TextButton sB;
    private TextButton optionsButton;
    private TextButton scoresButton;
    private TextButton hB;
    private TextButton eB;
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
		ArcadeInputMux.getInstance().removeProcessor(stage);
		this.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        //stage.act(delta);
        batch.begin();
        int width = Chess.SCREENWIDTH;
        int height = Chess.SCREENHEIGHT;
      
        batch.draw(splashTexture, 0, 0);
        batch.end();
        batch.begin();
        batch.draw(splashTexture2, 0, (float) ((float)height*0.88));
        batch.end();
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
		splashTexture = new Texture(Gdx.files.internal("chessMenu.png"));
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		splashTexture2 = new Texture(Gdx.files.internal("chessTitle.png"));
		splashTexture2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		splashSprite = new Sprite(splashTexture);
		//moves sprite to centre of screen
		splashSprite.setX(Gdx.graphics.getWidth() / 2 - (splashSprite.getWidth() / 2));
        splashSprite.setY(Gdx.graphics.getHeight() / 2 - (splashSprite.getHeight() / 2));
		//FIXME gigantic method
		batch = new SpriteBatch(); //batch for render
        atlas = new TextureAtlas("images/button.pack");
        skin = new Skin();
        skin.addRegions(atlas);
        white = new BitmapFont(Gdx.files.internal("imgs/gameFont.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("imgs/gameFont.fnt"), false);
        setBoundaries();
        

	}
	
	public void setBoundaries(){
		int width = Chess.SCREENWIDTH;
        int height = Chess.SCREENHEIGHT;
	        
	    stage = new Stage(width, height, true);
	
        ArcadeInputMux.getInstance().addProcessor(stage);
	
	    TextButtonStyle style = new TextButtonStyle();
	    style.up = skin.getDrawable("buttonnormal");
	    style.down = skin.getDrawable("buttonpressed");
	    style.font = black;
	
	    sB = new TextButton("Start", style);
	    hB = new TextButton("Help", style);
	    eB = new TextButton("Exit", style);
	    
	    sB.setWidth(200);
	    sB.setHeight(50);
	    hB.setWidth(200);
	    hB.setHeight(50);
	    eB.setWidth(200);
	    eB.setHeight(50);
	    
	    sB.setX(width / 2 - sB.getWidth() / 2);
	    hB.setX(width / 2 - hB.getWidth() / 2);
	    eB.setX(width / 2 - eB.getWidth() / 2);
	    
	    hB.setY(height / 2 - hB.getHeight() / 2);
	    sB.setY(hB.getY() + (2 * (sB.getHeight() + 10)));
	    eB.setY(hB.getY() - (2 * (sB.getHeight() + 10)));
	
	    addListeners(sB, hB, eB);
	    
	    LabelStyle ls = new LabelStyle(white, Color.WHITE);
	    /*label = new Label("Chess Masters", ls);
	    label.setX(0);
	    label.setY((float)(height*0.95));
	    label.setWidth(width);
	    label.setAlignment(Align.center);*/
	   	
	    
	    stage.addActor(hB);
	    stage.addActor(sB);
	    stage.addActor(eB);
	    //stage.addActor(label);
	}
	
	public void addListeners(TextButton sB, TextButton hB, TextButton eB){
		
		sB.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(game);
            }
    });
    
    hB.addListener(new InputListener() {
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new HelpScreen(game));
        }
    });
    
    eB.addListener(new InputListener() {
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
        }

        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        	ArcadeSystem.goToGame(ArcadeSystem.UI);
        }
    });
	}

}
