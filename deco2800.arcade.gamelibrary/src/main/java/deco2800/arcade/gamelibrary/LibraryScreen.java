package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;

/**
 * GDX Screen class for the game Library
 * @author Aaron Hayes
 */
public class LibraryScreen implements Screen {

    private GameLibrary gameLibrary;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;

    public LibraryScreen(GameLibrary gl) {
        gameLibrary = gl;
    }

    @Override
    public void render(float v) {
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0.2f, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        //draw a placeholder shape
        shapeRenderer.begin(ShapeRenderer.ShapeType.FilledRectangle);

        shapeRenderer.filledRect(100,
                100,
                1280 - 200,
                720 - 200);

        shapeRenderer.end();

        batch.begin();
        font.setColor(Color.BLACK);

        int h = 110;
        int index = 0;
        font.draw(batch, "Select a game by pressing a number key:", 110, h);
        h += 8;

        for (Game game : gameLibrary.getAvailableGames()) {
            h += 16;
            font.draw(batch, "" + index + ". " + game.name + " : " + (game.description == null ? "No Description Found" : game.description), 110, h);

            if (Gdx.input.isKeyPressed(Input.Keys.NUM_0 + index)) {
                ArcadeSystem.goToGame(game.id);
            }

            index++;

        }

        batch.end();
    }

    @Override
    public void resize(int i, int i2) {
    }

    @Override
    public void show() {
        font = new BitmapFont(true);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 1280, 720);
        shapeRenderer = new ShapeRenderer();
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
    }
}
