package deco2800.arcade.wl6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen {

    private WL6 wl6;
    private SpriteBatch batch;
    private Texture texture;

    public SplashScreen(WL6 game) {
        wl6 = game;
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("splash_background.png"));
    }

    @Override
    public void render(float arg0) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            wl6.setScreen(wl6.menuScreen);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Gdx.input.setCursorCatched(false);
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
        texture.dispose();
        batch.dispose();
    }
}
