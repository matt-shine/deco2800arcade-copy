package deco2800.arcade.towerdefence.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.badlogic.gdx.Input.*;

import deco2800.arcade.towerdefence.controller.TowerDefence;

public class SplashScreen implements Screen {

	Texture splashTexture;
	Sprite splashSprite;
	SpriteBatch batch;
	TowerDefence game;

	public SplashScreen(TowerDefence game) {
		this.game = game;
	}

	@Override
	public void dispose() {
		splashTexture.dispose();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		splashSprite.draw(batch);
		batch.end();

		/* If LMB is pressed change to menuScreen */
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			game.setScreen(game.menuScreen);
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		splashTexture = new Texture(Gdx.files.internal("Splash2.png"));
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		splashSprite = new Sprite(splashTexture);
		/* moves sprite to centre of screen */
		splashSprite.setX(Gdx.graphics.getWidth() / 2
				- (splashSprite.getWidth() / 2));
		splashSprite.setY(Gdx.graphics.getHeight() / 2
				- (splashSprite.getHeight() / 2));
		/* batch for render */
		batch = new SpriteBatch();

	}

}
