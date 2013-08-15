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
	private int row;
	private int col;

	private class PacManInputListener extends InputListener {
		@Override
		public boolean keyDown(InputEvent event, int keycode) {
			Actor actor = event.getListenerActor();

			Gdx.app.debug(LOG, keycode + " pressed");
			switch (keycode) {
			case LEFT:
				col--;
				actor.addAction(moveBy(-128f, 0f));
				break;
			case RIGHT:
				col++;
				actor.addAction(moveBy(128f, 0f));
				break;
			case UP:
				row++;
				actor.addAction(moveBy(0f, 128f));
				break;
			case DOWN:
				row--;
				actor.addAction(moveBy(0f, -128f));
				break;
			default:
				return false;	// event not handled
			}
			Gdx.app.debug(LOG, "row " + row + " col " + col);
			return true;
		}
	}

	PacMan() {
		texture = new Texture(Gdx.files.internal("pacman.png"));
		region = new TextureRegion(texture, 0, 0, 512, 512);
		row = 0;
		col = 0;
		setX(128f * col);
		setY(128f * row);
		addListener(new PacManInputListener());
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		//batch.setColor(0f, 1f, 1f, 1f);
		batch.draw(region, getX(), getY(), 128, 128);
	}
}
