/*
 * SplashScreen
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.badlogic.gdx.Input.Keys.ANY_KEY;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * This is the entry of the game application, and the next
 * should be the menu screen.
 */
final class SplashScreen implements Screen {
	private static final String LOG = SplashScreen.class.getSimpleName();

	private final MixMaze game;
	private final SpriteBatch batch;
	private final Texture texture;

	/**
	 * Constructor
	 */
	SplashScreen(final MixMaze game) {
		this.game = game;
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("splash.png"));
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(ANY_KEY)) {
			Gdx.app.debug(LOG, "switching to menu screen");
			game.setScreen(game.menuScreen);
		}

		Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void dispose() {
		texture.dispose();
		batch.dispose();
	}

	@Override
	public void hide() {
	}

	@Override
	public void show() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
