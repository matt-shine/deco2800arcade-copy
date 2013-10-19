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

    private Hunter hunter;
    private Stage stage;
    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    private List<Highscore> topPlayers;


    private Texture background;
    private SpriteBatch batch;

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

        table.add("HighScore 1 - " + topPlayers.get(0).playerName + " : ");
        System.out.println("HighScore 1 - " + topPlayers.get(0).playerName + " : " + topPlayers.get(0).score);
        table.add(String.valueOf(topPlayers.get(0).score)).colspan(2);
        table.row();

        table.add("HighScore 2 - " + topPlayers.get(1).playerName + " : ");
        System.out.println("HighScore 2 - " + topPlayers.get(1).playerName + " : " +topPlayers.get(1).score);
        table.add(String.valueOf(topPlayers.get(1).score)).colspan(2);
        table.row();

        table.add("HighScore 3 - " + topPlayers.get(2).playerName + " : ");
        table.add(String.valueOf(topPlayers.get(2).score)).colspan(2);
        table.row();

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
        // TODO Auto-generated method stub
        ArcadeInputMux.getInstance().addProcessor(stage);
    }

    @Override
    public void hide() {
        ArcadeInputMux.getInstance().removeProcessor(stage);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void drawBackground() {
        batch.draw(background, 0f, 0f, Hunter.State.screenWidth, Hunter.State.screenHeight, 0, 0, background.getWidth(), background.getHeight(), false, false);
    }
}
