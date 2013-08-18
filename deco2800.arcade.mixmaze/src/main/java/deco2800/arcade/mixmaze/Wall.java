/*
 * Wall
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.*;

final class Wall extends Actor {
	static enum Type {
		NORTH, EAST, SOUTH, WEST
	}

	private static final String LOG = Wall.class.getSimpleName();
	private final ShapeRenderer shapeRenderer;
	private final Type type;

	private boolean isBuilt;

	Wall(Type type, ShapeRenderer shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
		this.type = type;
		this.isBuilt = false;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();

		if (isBuilt) {
			shapeRenderer.begin(FilledRectangle);
			shapeRenderer.setColor(1f, 1f, 0f, 1f);
			switch (type) {
			case NORTH:
				shapeRenderer.filledRect(0f, 120f, 128f, 16f);
				break;
			case EAST:
				shapeRenderer.filledRect(120f, 0f, 16f, 128f);
				break;
			case SOUTH:
				shapeRenderer.filledRect(0f, -8f, 128f, 16f);
				break;
			case WEST:
				shapeRenderer.filledRect(-8f, 0f, 16f, 128f);
				break;
			}
			shapeRenderer.end();
		}

		batch.begin();
	}

	/**
	 * Toggle the state of this.
	 */
	boolean toggle() {
		isBuilt = !isBuilt;
		return isBuilt;
	}
}
