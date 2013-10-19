package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.LibraryStyle;
import deco2800.arcade.model.Player;


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
    private java.util.List<Game> games = null;
    private Game currentGame;
    private Stage stage;
    private TextButton storeButton;
    private TextButton userProfileButton;
    private TextButton currentButton;
    private TextButton homeButton;
    private Texture splashTexture;
    private Texture gridTexture;
    private Actor image;
    private Skin libSkin;

    private Texture listIconTexture;
    private Texture gridIconTexture;
    private ImageButton listImageButton;
    private ImageButton gridImageButton;


    /**
     * Constructor for gird screen
     * @param gl The Game Library
     */
    public GridScreen(GameLibrary gl) {
        gameSelected = false;
        gameLibrary = gl;
        styleSetup();
        setupGridUI();
    }

    /**
     * Setup required styles
     */
    private void styleSetup() {
        libSkin = new Skin(Gdx.files.classpath("Assets/libSkin.json"));
    }

    /**
     * Setup User Interface
     */
    private void setupGridUI() {

        stage = new Stage();
        batch = new SpriteBatch();
        splashTexture = new Texture(Gdx.files.classpath("Assets/splashscreen-grid.jpg"));
        gridTexture = new Texture(Gdx.files.classpath("Assets/gridbk.png"));
        image = new Image(splashTexture);
        stage.addActor(image);

        listIconTexture = new Texture(Gdx.files.classpath("Assets/list-icon.png"));
        gridIconTexture = new Texture(Gdx.files.classpath("Assets/grid-icon.png"));
        listImageButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(listIconTexture)));
        gridImageButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(gridIconTexture)));

        listImageButton.setX(1075);
        gridImageButton.setX(1155);
        listImageButton.setY(643);
        gridImageButton.setY(643);

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

                Label gridLabel = new Label(game.name, libSkin);
                gridLabel.setAlignment(2, 2);
                gridLabel.setWidth(background.getWidth());
                gridLabel.setHeight(40);
                gridLabel.setX(gridX);
                gridLabel.setY(gridY + background.getHeight() - 40);

                TextButton gamePlay = new TextButton("Play", libSkin, "green");
                gamePlay.setWidth(152);
                gamePlay.setHeight(30);
                gamePlay.setX(gridX + 5);
                gamePlay.setY(gridY + 45 - gridLabel.getHeight());
                gamePlay.addListener(new PlayButtonActionHandler(this, game));

                if (++count % 7 == 0) {
                    gridX = 25;
                    gridY -= (background.getHeight() + 15);
                } else {
                    gridX += (background.getWidth() + 15);
                }

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
        
        //you cannot use this method
        //Gdx.input.setInputProcessor(stage);
        //use this instead
        ArcadeInputMux.getInstance().addProcessor(stage);
        //you need to remove this listener when you're done with it.
        //-Simon
        
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.2f, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        batch.begin();
        stage.draw();
        batch.end();

        if (gameSelected) {
            play();
        }
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
    	ArcadeInputMux.getInstance().removeProcessor(stage);
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

    /**
     * Start an Arcade Game
     */
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

    public void showMore() {

    }
}
