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
 * GDX Screen class for Grid View
 * @author Aaron Hayes
 */
public class GridScreen implements Screen, LibraryScreen {


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
    private TextButton storeButton;
    private TextButton userProfileButton;
    private TextButton currentButton;
    private TextButton homeButton;
    private Skin skin;
    private Texture splashTexture;
    private Texture gridTexture;
    private Actor image;
    private Skin libSkin;

    private Texture listIconTexture;
    private Texture gridIconTexture;
    private ImageButton listImageButton;
    private ImageButton gridImageButton;



    public GridScreen(GameLibrary gl) {
        gameSelected = false;
        gameLibrary = gl;
        styleSetup();
        setupGridUI();
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


    private void setupGridUI() {

        stage = new Stage();
        batch = new SpriteBatch();
        splashTexture = new Texture("Assets/splashscreen-grid.jpg");
        gridTexture = new Texture("Assets/gridbk.jpg");
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
            public void changed(ChangeEvent event, Actor actor) {
                ArcadeSystem.exit();
            }
        });
        stage.addActor(exitButton);

        games = gameLibrary.getAvailableGames();
        int gridX = 25;
        int gridY = 435;
        int count = 0;
        for (Game game : games) {
            if (game != null) {

                Actor background = new Image(gridTexture);
                background.setX(gridX);
                background.setY(gridY);

                Label gridLabel = new Label(game.name, skin, "titleStyle");
                gridLabel.setWidth(background.getWidth());
                gridLabel.setHeight(40);
                gridLabel.setX(gridX + background.getWidth()/4);
                gridLabel.setY(gridY + background.getHeight() - 40);

                TextButton gamePlay = new TextButton("Play", skin);
                gamePlay.setWidth(150);
                gamePlay.setHeight(30);
                gamePlay.setX(gridX + 5);
                gamePlay.setY(gridY + 50 - gridLabel.getHeight());
                gamePlay.addListener(new PlayButtonActionHandler(this, game));

                if (++count % 7 == 0) {
                    gridX = 20;
                    gridY -= (background.getHeight() + 15);
                } else {
                    gridX += (background.getWidth() + 15);
                }

                //y = gridY - 80;

                stage.addActor(background);
                stage.addActor(gridLabel);
                stage.addActor(gamePlay);
            }
        }

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

        stage.addActor(homeButton);
        stage.addActor(storeButton);
        stage.addActor(userProfileButton);
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
    }

    public Player getPlayer() {
        return gameLibrary.getPlayer();
    }

    public void changeView() {
        gameLibrary.switchViews();
    }
}
