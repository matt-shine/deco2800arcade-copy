package deco2800.arcade.towerdefence.view;

import static com.badlogic.gdx.Input.Keys.ANY_KEY;
import static com.badlogic.gdx.Input.Buttons.LEFT;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.arcade.towerdefence.model.TowerDefence;

/* Credits screen, any key to exit
 * @author Tuddz
 */
public class CreditsScreen implements Screen{
	private static final String LOG = CreditsScreen.class.getSimpleName();
	private final TowerDefence game;
	SpriteBatch batch;
	Texture texture;
	private long startTime;

	public CreditsScreen(final TowerDefence game) {
		this.game = game;
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("Credits.png"));
		//Texture.setEnforcePotImages(false); 
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void hide() {		
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		if (millis() - startTime > 500
				&& (Gdx.input.isKeyPressed(ANY_KEY) || Gdx.input.isButtonPressed(LEFT))) {
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
	public void resume() {
	}

	@Override
	public void show() {
		startTime = millis();
	}
}
