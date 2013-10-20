package deco2800.arcade.hunter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.hunter.Hunter;

/**
 * A Hunter game for use in the Arcade
 *
 * @author Nessex, DLong94
 */
public class MenuScreen implements Screen {
    private final Hunter hunter;
    private final Stage stage;
    private final SpriteBatch batch;
    private final Texture background;

    public MenuScreen(Hunter p) {
        hunter = p;
        Texture.setEnforcePotImages(false);
        background = new Texture("textures/mainmenu.png");
        //Set up stage
        stage = new Stage();
        batch = new SpriteBatch();

        Table table = new Table();
        table.padRight(400f);
        table.padTop(300f);
        table.setFillParent(true);
        stage.addActor(table);

        //Add play button to the table
        Button playButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("textures/playbutton.png"))));
        playButton.setSize(300, 80);
        //implement listener to the button
        playButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hunter.setScreen(new GameScreen(hunter));
            }

        });
        //add to the table
        table.add(playButton).size(300, 80).spaceBottom(20);
        table.row();

        //Add options button to the table
        Button optionsButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("textures/optionsbutton.png"))));
        optionsButton.setSize(300, 80);

        optionsButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hunter.setScreen(new OptionScreen(hunter));
            }
        });

        table.add(optionsButton).size(300, 80).spaceBottom(20);
        table.row();


        //Add highscore button to the table
        Button highScoreButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("textures/highscorebutton.png"))));
        highScoreButton.setSize(200, 60);

        highScoreButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                hunter.setScreen(new HighScoreScreen(hunter));
            }
        });

        table.add(highScoreButton).size(300, 80).spaceBottom(20);
        table.row();

        Button exitButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("textures/exitbutton.png"))));
        exitButton.setSize(300, 80);

        exitButton.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ArcadeSystem.goToGame(ArcadeSystem.UI);
            }
        });
        table.add(exitButton).size(300, 80).spaceBottom(20);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void hide() {
        ArcadeInputMux.getInstance().removeProcessor(stage);
    }

    @Override
    public void pause() {

    }

    @Override
    public void render(float delta) {
        //Black background
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.begin();
        drawBackground();
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(width, height, true);

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        ArcadeInputMux.getInstance().addProcessor(stage);
    }

    private void drawBackground() {
        batch.draw(background, 0f, 0f, Hunter.Config.SCREEN_WIDTH, Hunter.Config.SCREEN_HEIGHT, 0, 0, background.getWidth(), background.getHeight(), false, false);
    }

}
