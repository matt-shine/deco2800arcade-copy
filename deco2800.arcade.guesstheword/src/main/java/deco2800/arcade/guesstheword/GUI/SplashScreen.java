package deco2800.arcade.guesstheword.GUI;

import static com.badlogic.gdx.Input.Keys.ANY_KEY;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen {
	private final GuessTheWord game;
	private final SpriteBatch batch;
	private final Texture texture;
//	private final TextureRegion splashTextureRegion;

	SplashScreen(final GuessTheWord game) {
		this.game = game;
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("splashScreen.png"));

	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(ANY_KEY)) {
			game.setScreen(game.mainScreen);
		System.out.println("Procceeding to MainScreen");
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
