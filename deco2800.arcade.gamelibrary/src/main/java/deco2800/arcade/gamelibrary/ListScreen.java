package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.LibraryStyle;
import deco2800.arcade.model.Player;

import java.util.ArrayList;

/**
 * GDX Screen for List View
 * @author Aaron Hayes
 */
public class ListScreen implements Screen, LibraryScreen {


    private GameLibrary gameLibrary;
    private Boolean gameSelected;

    /**
     * UI Objects
     */
    private SpriteBatch batch;
    private BitmapFont font;
    private ArrayList<Game> games = null;
    private Game currentGame;
    private Stage stage;
    private int x = 0;
    private int y = 580;
    private TextButton button;
    private TextButton storeButton;
    private TextButton userProfileButton;
    private TextButton currentButton;
    private TextButton homeButton;

    private Skin skin;
    private String description;
    private String gameTitle;
    private Label label;
    private Label titleLabel;
    private Texture splashTexture;
    private Actor image;
    private Skin libSkin;

    private Texture listIconTexture;
    private Texture gridIconTexture;
    private ImageButton listImageButton;
    private ImageButton gridImageButton;



    public ListScreen(GameLibrary gl) {
        gameSelected = false;
        gameLibrary = gl;
        styleSetup();
        setupListUI();
    }

    private void styleSetup() {
        font = new BitmapFont(true);
        font.setColor(Color.BLACK);
        skin = new Skin();
        libSkin = new Skin(Gdx.files.internal("libSkin.json"));
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

    }

    private void setupListUI() {

        stage = new Stage();
        batch = new SpriteBatch();
        splashTexture = new Texture("Assets/splashscreen.jpg");
        image = new Image(splashTexture);
        stage.addActor(image);

        listIconTexture = new Texture("Assets/list-icon.jpg");
        gridIconTexture = new Texture("Assets/grid-icon.jpg");
        listImageButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(listIconTexture)));
        gridImageButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(gridIconTexture)));

        listImageButton.setX(1000);
        gridImageButton.setX(1050);
        listImageButton.setY(650);
        gridImageButton.setY(650);

        listImageButton.addListener(new ViewSwitchButtonActionHandler(this, LibraryStyle.LIST_VIEW));
        gridImageButton.addListener(new ViewSwitchButtonActionHandler(this, LibraryStyle.GRID_VIEW));

        stage.addActor(listImageButton);
        stage.addActor(gridImageButton);


        Actor exitButton = new TextButton("Exit", libSkin, "red");
        exitButton.setWidth(200);
        exitButton.setHeight(40);
        exitButton.setX(1000);
        exitButton.setY(50);

        exitButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                ArcadeSystem.exit();
            }
        });
        stage.addActor(exitButton);



        label = new Label(description, libSkin);
        label.setWidth(150);
        label.setHeight(40);
        label.setX(320);
        label.setY(475);

        titleLabel = new Label(gameTitle, libSkin, "heading");
        titleLabel.setWidth(150);
        titleLabel.setHeight(40);
        titleLabel.setX(320);
        titleLabel.setY(530);

        games = gameLibrary.getAvailableGames();
        int count = 0;
        for (Game game : games) {
            if (game != null) {
                button = new TextButton("" + game.name, libSkin, "gameslistbutton");
                button.setWidth(275);
                button.setHeight(33);
                button.setX(x);
                button.setY(y);
                y-= 35;

                button.addListener(new GameButtonActionHandler(this, game, button));

                if (count++ == 0) {
                    button.setChecked(true);
                    setCurrentButton(button);
                    setSelectedGame(game);
                }

                stage.addActor(button);
            }
        }

        Actor playButton = new TextButton("Play", libSkin, "green");
        playButton.setWidth(150);
        playButton.setHeight(40);
        playButton.setX(320);
        playButton.setY(575);
        playButton.addListener(new PlayButtonActionHandler(this));

        homeButton = new TextButton("Home", libSkin);
        homeButton.setWidth(150);
        homeButton.setHeight(40);
        homeButton.setX(100);
        homeButton.setY(650);
        homeButton.addListener(new PlayButtonActionHandler(this, "arcadeui"));

        storeButton = new TextButton("Game Store", libSkin);
        storeButton.setWidth(150);
        storeButton.setHeight(40);
        storeButton.setX(300);
        storeButton.setY(650);
        storeButton.addListener(new PlayButtonActionHandler(this, "arcadeui"));

        userProfileButton = new TextButton("User Profile", libSkin);
        userProfileButton.setWidth(150);
        userProfileButton.setHeight(40);
        userProfileButton.setX(500);
        userProfileButton.setY(650);
        userProfileButton.addListener(new PlayButtonActionHandler(this, "arcadeui"));

        stage.addActor(label);
        stage.addActor(homeButton);
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

    public void setGameSelected() {
        gameSelected = true;
    }

    public void setCurrentButton(TextButton b) {
        currentButton = b;
    }

    public Button getCurrentButton() {
        return currentButton;
    }

    private void play() {
        dispose();
        ArcadeSystem.goToGame(currentGame.id);
    }

    public void setSelectedGame(Game game) {
        currentGame = game;
        gameTitle = game.name;
        description = "Game Details: " + "\n";
        if(game.getDescription() == null || game.getDescription().equals("N/A")) {
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

    public Player getPlayer() {
        return gameLibrary.getPlayer();
    }

    public void changeView() {
        gameLibrary.switchViews();
    }

}
