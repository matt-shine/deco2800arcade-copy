/*
 * Box
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;

import static deco2800.arcade.mixmaze.Wall.Type.*;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.*;

final class Box extends Group {
	/*
	 * FIXME: All boxes should be inactive at the start,
	 * and therefore invisible. PacMan should be able to
	 * `fire' the box at its position to build a wall.
	 * A controller class would be the choice.
	 */
	private static final String LOG = Box.class.getSimpleName();
	private final int row;
	private final int col;
	private final ShapeRenderer shapeRenderer;
	private final Wall north;
	private final Wall east;
	private final Wall south;
	private final Wall west;

	private int numWalls;
	private boolean active;

	Box(int row, int col, ShapeRenderer shapeRenderer) {
		this.row = row;
		this.col = col;
		this.shapeRenderer = shapeRenderer;
		setX(128f * col);
		setY(128f * row);

		north = new Wall(NORTH, shapeRenderer);
		addActor(north);
		east = new Wall(EAST, shapeRenderer);
		addActor(east);
		south = new Wall(SOUTH, shapeRenderer);
		addActor(south);
		west = new Wall(WEST, shapeRenderer);
		addActor(west);

		numWalls = 0;
		active = false;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();

		shapeRenderer.setProjectionMatrix(
				getStage().getCamera().combined);
		if (active) {
			shapeRenderer.begin(FilledRectangle);
			shapeRenderer.setColor(1f, 0f, 0f, 1f);
			shapeRenderer.filledRect(getX(), getY(), 128f, 128f);
			shapeRenderer.end();
		}

		shapeRenderer.begin(Rectangle);
		shapeRenderer.setColor(0f, 0f, 0f, 1f);
		shapeRenderer.rect(getX(), getY(), 128f, 128f);
		shapeRenderer.end();

		shapeRenderer.setTransformMatrix(computeTransform());

		batch.begin();

		super.draw(batch, parentAlpha);

		shapeRenderer.identity();
	}

	/**
	 * Toggle the state of the specified wall.
	 */
	void toggleWall(Wall.Type wall) {
		Boolean increment = null;

		switch (wall) {
		case NORTH:
			increment = north.toggle();
			break;
		case EAST:
			increment = east.toggle();
			break;
		case SOUTH:
			increment = south.toggle();
			break;
		case WEST:
			increment = west.toggle();
			break;
		}

		numWalls = (increment) ? numWalls + 1 : numWalls - 1;
		active = (numWalls == 4) ? true : false;
		Gdx.app.debug(LOG, "toggle wall, numWalls: " + numWalls
				   + ", active: " + active);
	}
}
