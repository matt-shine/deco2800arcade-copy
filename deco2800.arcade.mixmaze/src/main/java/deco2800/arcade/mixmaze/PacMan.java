/*
 * PacMan
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

final class PacMan extends Actor {
	private static final String LOG = PacMan.class.getSimpleName();

	private Texture texture;
	private TextureRegion region;

	private class PacManInputListener extends InputListener {
		@Override
		public boolean keyDown(InputEvent event, int keycode) {
			Actor actor = event.getListenerActor();

			Gdx.app.debug(LOG, keycode + " pressed");
			switch (keycode) {
			case LEFT:
				actor.addAction(moveBy(-128f, 0f));
				break;
			case RIGHT:
				actor.addAction(moveBy(128f, 0f));
				break;
			case UP:
				actor.addAction(moveBy(0f, 128f));
				break;
			case DOWN:
				actor.addAction(moveBy(0f, -128f));
				break;
			default:
				return false;	// event not handled
			}
			return true;
		}
	}

	PacMan() {
		texture = new Texture(Gdx.files.internal("pacman.png"));
		region = new TextureRegion(texture, 0, 0, 512, 512);
		setX(100f);
		setY(100f);
		addListener(new PacManInputListener());
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		//batch.setColor(0f, 1f, 1f, 1f);
		batch.draw(region, getX(), getY(), 128, 128);
	}
}
