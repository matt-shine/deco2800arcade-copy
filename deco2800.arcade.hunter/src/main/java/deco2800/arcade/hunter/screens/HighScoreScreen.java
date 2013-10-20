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
import deco2800.arcade.client.highscores.Highscore;
import deco2800.arcade.hunter.Hunter;

import java.util.List;

public class HighScoreScreen implements Screen {

    private final Hunter hunter;
    private final Stage stage;
    private final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    private final List<Highscore> topPlayers;


    private final Texture background;
    private final SpriteBatch batch;

    public HighScoreScreen(Hunter h) {
        hunter = h;
        stage = new Stage();
        ArcadeInputMux.getInstance().addProcessor(stage);

        topPlayers = hunter.highscore.getGameTopPlayers(3, true, "Number");
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


        table.add("HighScore Menu!").colspan(2);
        table.row();

        for (int i = 0; i < Math.min(3, topPlayers.size()); i++) {
            table.add("HighScore " + (i + 1) + " - " + topPlayers.get(i).playerName + " : ");
            System.out.println("HighScore " + (i + 1) + " - " + topPlayers.get(i).playerName + " : " + topPlayers.get(i).score);
            table.add(String.valueOf(topPlayers.get(i).score)).colspan(2);
            table.row();
        }

        if (topPlayers.size() == 0) {
            table.add("No high scores have been recorded!");
            table.row();
        }

        TextButton backButton = new TextButton("Back to main menu", skin);
        backButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Going back to the main menu!");
                hunter.setScreen(new MenuScreen(hunter));
            }
        });

        table.add(backButton).size(300, 70);
        table.row();


    }

    public void getHighScores() {
        /*Get the high scores */
    }


    @Override
    public void render(float delta) {
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
    public void show() {
        ArcadeInputMux.getInstance().addProcessor(stage);
    }

    @Override
    public void hide() {
        ArcadeInputMux.getInstance().removeProcessor(stage);
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
    }

    private void drawBackground() {
        batch.draw(background, 0f, 0f, Hunter.State.screenWidth, Hunter.State.screenHeight, 0, 0, background.getWidth(), background.getHeight(), false, false);
    }
}
