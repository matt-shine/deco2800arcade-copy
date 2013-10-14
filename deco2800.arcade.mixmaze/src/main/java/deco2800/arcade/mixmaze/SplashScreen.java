/*
 * SplashScreen
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.badlogic.gdx.Input.Keys.ANY_KEY;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * This is the entry of the game application, and the next
 * should be the menu screen.
 */
final class SplashScreen implements Screen {

	/**
	 * The minimum show time of this screen in milliseconds.
	 */
	static final long MIN_SHOW_TIME = 500;

	final Logger logger = LoggerFactory.getLogger(SplashScreen.class);

	private final MixMaze game;
	private final SpriteBatch batch;
	private final Texture texture;

	private long showTime;

	/**
	 * Constructor.
	 *
	 * @param game	the MixMaze game
	 */
	SplashScreen(final MixMaze game) {
		this.game = game;
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("splash.png"));
	}

	@Override
	public void render(float delta) {
		if (TimeUtils.millis() - showTime > MIN_SHOW_TIME
				&& Gdx.input.isKeyPressed(ANY_KEY)) {
			logger.debug("switch to menu screen");
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
		showTime = TimeUtils.millis();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

}
