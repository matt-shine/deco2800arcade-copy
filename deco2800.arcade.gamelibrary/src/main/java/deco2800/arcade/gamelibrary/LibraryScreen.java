package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private Set<GameClient> games = null;
    private GameClient currentClient;
    private Stage stage;
    private int x = 0;
    private int y = 580;
    private Button button;
    private Button storeButton;
    private Button gridViewButton;
    private Button userProfileButton;
    private Button currentButton;
    private Skin skin;
    private String description;
    private String gameTitle;
    private Label label;
    private Label titleLabel;
    private Texture splashTexture;
    private Actor image;


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

        Label.LabelStyle titleLabelStyle = new Label.LabelStyle();
        BitmapFont titleFont = skin.getFont("default");
        titleFont.setColor(new Color(125,100,129,1.0f));
        //titleFont.setScale(1.5f);
        titleLabelStyle.font = titleFont;
        skin.add("titleStyle", titleLabelStyle);




        // Specify font, fontColor, cursor, selection, and background
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.cursor = skin.newDrawable("white", Color.WHITE);
        textFieldStyle.selection = skin.newDrawable("white", Color.WHITE);
        //textFieldStyle.background = ;
        skin.add("default", textFieldStyle);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        //textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.WHITE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        TextButton.TextButtonStyle playButtonStyle = new TextButton.TextButtonStyle();
        playButtonStyle.down = skin.newDrawable("white", Color.LIGHT_GRAY);
        playButtonStyle.checked = skin.newDrawable("white", Color.WHITE);
        playButtonStyle.over = skin.newDrawable("white", Color.DARK_GRAY);
        playButtonStyle.up = skin.newDrawable("white", new Color(135, 103, 140, 100));
        playButtonStyle.font = skin.getFont("default");
        skin.add("playButton", playButtonStyle);

    }

    private void setupUI() {

        stage = new Stage();
        batch = new SpriteBatch();
        splashTexture = new Texture("Assets/splashscreen.jpg");
        image = new Image(splashTexture);
        stage.addActor(image);


        Actor exitButton = new TextButton("Exit", skin);
        exitButton.setWidth(200);
        exitButton.setHeight(40);
        exitButton.setX(1000);
        exitButton.setY(50);

        exitButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                ArcadeSystem.close();
            }
        });
        stage.addActor(exitButton);



        label = new Label(description, skin, "default");
        label.setWidth(150);
        label.setHeight(40);
        label.setX(290);
        label.setY(475);

        titleLabel = new Label(gameTitle, skin, "titleStyle");
        titleLabel.setWidth(150);
        titleLabel.setHeight(40);
        titleLabel.setX(290);
        titleLabel.setY(530);

        games = ArcadeSystem.getGameList();
        int count = 0;
        for (final GameClient gameClient : games) {
            final Game game = gameClient.getGame();
            button = new TextButton("" + game.name, skin);
            button.setWidth(275);
            button.setHeight(33);
            button.setX(x);
            button.setY(y);
            y-= 35;

            button.addListener(new GameButtonActionHandler(this, gameClient, button));

            if (count++ == 0) {
                button.setChecked(true);
                setCurrentButton(button);
                setSelectedGame(gameClient);
            }

            stage.addActor(button);

        }

        Actor playButton = new TextButton("Play", skin, "playButton");
        playButton.setWidth(50);
        playButton.setHeight(40);
        playButton.setX(380);
        playButton.setY(580);
        playButton.addListener(new PlayButtonActionHandler(this));

        storeButton = new TextButton("Game Store", skin);
        storeButton.setWidth(200);
        storeButton.setHeight(40);
        storeButton.setX(x);
        storeButton.setY(650);
        storeButton.addListener(new PlayButtonActionHandler(this));

        userProfileButton = new TextButton("User Profile", skin);
        userProfileButton.setWidth(200);
        userProfileButton.setHeight(40);
        userProfileButton.setX(300);
        userProfileButton.setY(650);
        userProfileButton.addListener(new PlayButtonActionHandler(this));

        stage.addActor(label);
        stage.addActor(titleLabel);
        stage.addActor(storeButton);
        stage.addActor(userProfileButton);
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
        gameTitle = game.name;
        description = "Game Details: " + "\n";
        if(game.getDescription() == null) {
            description += "No Description Available";
        } else {
            description += game.getDescription();
        }
        label.remove();
        label.setText(description);
        stage.addActor(label);

        titleLabel.remove();
        titleLabel.setText(gameTitle);
        stage.addActor(titleLabel);
    }

}
