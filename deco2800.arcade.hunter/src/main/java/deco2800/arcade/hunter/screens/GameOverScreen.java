package deco2800.arcade.hunter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.hunter.Hunter;

/**
 * A Hunter game for use in the Arcade
 *
 * @author Nessex, DLong94
 */
public class GameOverScreen implements Screen {
    private final Hunter hunter;
    private final Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    private final Texture background;
    private final SpriteBatch batch;

    public GameOverScreen(Hunter p, float distance, int score, int killCount) {
        hunter = p;
        stage = new Stage();
        ArcadeInputMux.getInstance().addProcessor(stage);

        Texture.setEnforcePotImages(false);
        background = new Texture("textures/mainmenu.png");
        batch = new SpriteBatch();

        Table table = new Table(skin);
        table.setFillParent(true);
        table.padRight(400f);
        table.padTop(400f);
        stage.addActor(table);

        //set table defaults
        table.defaults().spaceBottom(20);
        table.columnDefaults(0).colspan(2);


        table.add("GAME OVER").colspan(2);
        table.row();

        table.add("Distance Travelled: ");
        table.add(String.valueOf(distance)).colspan(2);
        table.row();

        table.add("Score: ");
        table.add(String.valueOf(score)).colspan(2);
        table.row();

        table.add("Kills: ");
        table.add(String.valueOf(killCount) + " Animals").colspan(2);
        table.row();

        TextButton playButton = new TextButton("Play Again", skin);
        playButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hunter.setScreen(new GameScreen(hunter));
            }
        });

        TextButton backButton = new TextButton("Back to main menu", skin);
        backButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hunter.setScreen(new MenuScreen(hunter));
            }
        });

        table.add(backButton).size(300, 70);
        table.row();
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
        // Black background
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