package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;


import java.util.Set;
import java.util.HashSet;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.model.Game;

/**
 * GDX Screen class for the game Library
 * @author Aaron Hayes
 */
public class LibraryScreen implements Screen {


    private GameLibrary gameLibrary;
    private Boolean gameSelected;

    /**
     * UI Objects
     */
    private SpriteBatch batch;
    private BitmapFont font;
    Set<GameClient> games = null;
    private GameClient currentClient;
    private Stage stage;
    private int x = 10;
    private int y = 325;
    private Button button;
	private Button currentButton;
    private Skin skin;
    private String description;
    private Label label;


    public LibraryScreen(GameLibrary gl) {
        gameSelected = false;
        gameLibrary = gl;
        styleSetup();
        setupUI();
    }
    
    private void styleSetup() {
    	font = new BitmapFont(true);
        font.setColor(Color.BLACK);
        skin = new Skin();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
       
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);

        // Specify font, fontColor, cursor, selection, and background
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.cursor = skin.newDrawable("white", Color.WHITE);
        textFieldStyle.selection = skin.newDrawable("white", Color.WHITE);
        //textFieldStyle.background = ;
        skin.add("default", textFieldStyle);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.WHITE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
    	
    }

    private void setupUI() {

        stage = new Stage();
        batch = new SpriteBatch();

        Actor exitButton = new TextButton("Exit", skin);
        exitButton.setWidth(200);
        exitButton.setHeight(40);
        exitButton.setX(x+300);
        exitButton.setY(y);

        exitButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                ArcadeSystem.close();
            }
        });
        stage.addActor(exitButton);

        games = ArcadeSystem.getGameList();
        for (final GameClient gameClient : games) {
            final Game game = gameClient.getGame();
            button = new TextButton("" + game.name, skin);
            button.setWidth(200);
            button.setHeight(40);
            button.setX(x);
            button.setY(y);
            y+= 50;

            button.addListener(new GameButtonActionHandler(this, gameClient, button));

            stage.addActor(button);

        }

        Actor playButton = new TextButton("Play", skin);
        playButton.setWidth(200);
        playButton.setHeight(40);
        playButton.setX(x);
        playButton.setY(y + 50);

        playButton.addListener(new PlayButtonActionHandler(this));
        label = new Label(description, skin);
        label.setWidth(200);
        label.setHeight(40);
        label.setY(550);
        label.setX(400);
        stage.addActor(label);
        stage.addActor(playButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.2f, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        stage.act(Gdx.graphics.getDeltaTime());
        batch.begin();
        stage.draw();      
        batch.end();

        if (gameSelected) play();
    }

    @Override
    public void resize(int i, int i2) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }

    public void setGameSeletcted() {
        gameSelected = true;
    }
	
	public void setCurrentButton(Button b) {
		currentButton = b;
	}
	
	public Button getCurrentButton() {
		return currentButton;
	}

    private void play() {
        ArcadeSystem.goToGame(currentClient);
    }

    public void setSelectedGame(final GameClient gameClient) {
        currentClient = gameClient;
        Game game = gameClient.getGame();
        description = game.name;
        label.remove();
        label.setText(description);
        stage.addActor(label);
    }
 
}
