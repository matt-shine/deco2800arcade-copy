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
 * GDX Screen for List View
 * @author Aaron Hayes
 */
public class ListScreen implements Screen, LibraryScreen {


    private GameLibrary gameLibrary;
    private Boolean gameSelected;
    private Boolean more;

    /**
     * UI Objects
     */
    private SpriteBatch batch;
    private java.util.List<Game> games = null;
    private Game currentGame;
    private Stage stage;
    private int x = 0;
    private int y = 580;
    private TextButton button;
    private TextButton storeButton;
    private TextButton userProfileButton;
    private TextButton currentButton;
    private TextButton homeButton;

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


    /**
     * Constructor for list screen
     * @param gl The Game Library
     * @param nextPage boolean to show the second page of games
     */
    public ListScreen(GameLibrary gl, boolean nextPage) {
        gameSelected = false;
        gameLibrary = gl;
        more = nextPage;
        styleSetup();
        setupListUI();
    }

    /**
     * Setup required UI styles
     */
    private void styleSetup() {
        libSkin = new Skin(Gdx.files.classpath("Assets/libSkin.json"));
    }

    /**
     * Setup UI
     */
    private void setupListUI() {

        stage = new Stage();
        batch = new SpriteBatch();
        splashTexture = new Texture(Gdx.files.classpath("Assets/splashscreen.jpg"));
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

                if (count == 0 && more) {
                    button = new TextButton("More...", libSkin, "gameslistbutton");
                    button.setWidth(275);
                    button.setHeight(33);
                    button.setX(x);
                    button.setY(y);

                    button.addListener(new MoreButtonActionHandler(this, button));
                    stage.addActor(button);
                    y-= 35;
                }

                if (more && count++ <= 15) {
                    continue;
                }


                button = new TextButton("" + game.name, libSkin, "gameslistbutton");
                button.setWidth(275);
                button.setHeight(33);
                button.setX(x);
                button.setY(y);
                y-= 35;

                button.addListener(new GameButtonActionHandler(this, game, button));

                if ((more && count == 17) || count++ == 0) {
                    button.setChecked(true);
                    setCurrentButton(button);
                    setSelectedGame(game);
                }

                stage.addActor(button);

                if (count >= 16 && !more) {
                    button = new TextButton("More...", libSkin, "gameslistbutton");
                    button.setWidth(275);
                    button.setHeight(33);
                    button.setX(x);
                    button.setY(y);

                    button.addListener(new MoreButtonActionHandler(this, button));
                    stage.addActor(button);
                    break;
                }
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

    public void showMore() {
        if (more) {
            gameLibrary.showLess();
        } else {
            gameLibrary.showMore();
        }
    }

}
