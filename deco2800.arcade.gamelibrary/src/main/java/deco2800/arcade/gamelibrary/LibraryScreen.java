package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.Set;
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
    private int y = 100;
    private Actor button;
    private Skin skin;
    private String description;
    private OrthographicCamera camera;
    private Label label;


    public LibraryScreen(GameLibrary gl) {
        gameSelected = false;
        gameLibrary = gl;
        setupUI();
    }

    private void setupUI() {
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 1280, 720);
        stage = new Stage();
        font = new BitmapFont(true);
        font.setColor(Color.BLACK);
        skin = new Skin();
        batch = new SpriteBatch();
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

        Actor exitButton = new TextButton("Exit", skin);
        exitButton.setWidth(300);
        exitButton.setHeight(50);
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
            button.setHeight(50);
            button.setX(x);
            button.setY(y);
            y+= 80;

            button.addListener(new GameButtonActionHandler(this, gameClient));

            stage.addActor(button);

        }

        Actor playButton = new TextButton("Play", skin);
        playButton.setWidth(200);
        playButton.setHeight(50);
        playButton.setX(x);
        playButton.setY(y);

        playButton.addListener(new PlayButtonActionHandler(this));

        stage.addActor(playButton);
        label = new Label(description, skin);
        label.setWidth(200);
        label.setHeight(50);
        label.setY(300);
        label.setX(200);
        stage.addActor(label);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
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

    private void play() {
        ArcadeSystem.goToGame(currentClient);
    }

    public void setSelectedGame(final GameClient gameClient) {
        currentClient = gameClient;
    }
}
