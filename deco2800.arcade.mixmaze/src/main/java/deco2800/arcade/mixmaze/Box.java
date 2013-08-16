/*
 * Box
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;

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

	Box(int row, int col, ShapeRenderer shapeRenderer) {
		this.row = row;
		this.col = col;
		this.shapeRenderer = shapeRenderer;
		setX(128f * col);
		setY(128f * row);

		north = new Wall(Wall.Type.NORTH, shapeRenderer);
		addActor(north);
		east = new Wall(Wall.Type.EAST, shapeRenderer);
		addActor(east);
		south = new Wall(Wall.Type.SOUTH, shapeRenderer);
		addActor(south);
		west = new Wall(Wall.Type.WEST, shapeRenderer);
		addActor(west);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();

		shapeRenderer.setProjectionMatrix(
				getStage().getCamera().combined);
		shapeRenderer.begin(FilledRectangle);
		shapeRenderer.setColor(1f, 0f, 0f, 1f);
		shapeRenderer.filledRect(getX(), getY(), 128f, 128f);
		shapeRenderer.end();

		shapeRenderer.begin(Rectangle);
		shapeRenderer.setColor(0f, 0f, 0f, 1f);
		shapeRenderer.rect(getX(), getY(), 128f, 128f);
		shapeRenderer.end();

		shapeRenderer.setTransformMatrix(computeTransform());

		batch.begin();

		super.draw(batch, parentAlpha);

		shapeRenderer.identity();
	}
}
