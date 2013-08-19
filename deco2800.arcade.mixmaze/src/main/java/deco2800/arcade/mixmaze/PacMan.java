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

import static deco2800.arcade.mixmaze.Wall.Type.*;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

final class PacMan extends Actor {
	private static final String LOG = PacMan.class.getSimpleName();

	private Box[][] boxes;
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
				if (col > 0) {
					col--;
					actor.addAction(moveBy(-128f, 0f));
				}
				break;
			case RIGHT:
				if (col < 4) {
					col++;
					actor.addAction(moveBy(128f, 0f));
				}
				break;
			case UP:
				if (row < 4) {
					row++;
					actor.addAction(moveBy(0f, 128f));
				}
				break;
			case DOWN:
				if (row > 0) {
					row--;
					actor.addAction(moveBy(0f, -128f));
				}
				break;
			case K:
				boxes[row][col].toggleWall(NORTH);
				if (row < 4) {
					boxes[row + 1][col].toggleWall(SOUTH);
				}
				break;
			case L:
				boxes[row][col].toggleWall(EAST);
				if (col < 4) {
					boxes[row][col + 1].toggleWall(WEST);
				}
				break;
			case J:
				boxes[row][col].toggleWall(SOUTH);
				if (row > 0) {
					boxes[row - 1][col].toggleWall(NORTH);
				}
				break;
			case H:
				boxes[row][col].toggleWall(WEST);
				if (col > 0) {
					boxes[row][col - 1].toggleWall(EAST);
				}
				break;
			default:
				return false;	// event not handled
			}
			Gdx.app.debug(LOG, "row " + row + " col " + col);
			return true;
		}
	}

	PacMan(Box[][] boxes) {
		this.boxes = boxes;
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
