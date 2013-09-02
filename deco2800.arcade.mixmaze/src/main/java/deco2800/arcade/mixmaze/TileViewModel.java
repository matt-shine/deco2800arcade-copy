/*
 * TileViewModel
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.ItemModel;
import deco2800.arcade.mixmaze.domain.TileModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer
		.ShapeType.FilledRectangle;
import static com.badlogic.gdx.graphics.glutils.ShapeRenderer
		.ShapeType.Rectangle;

public class TileViewModel extends Group {
	private static final String LOG = TileViewModel.class.getSimpleName();

	private final TileModel model;
	private final ShapeRenderer renderer;
	private final WallViewModel[] walls;
	private final int tileSize;

	/**
	 * Constructor
	 */
	public TileViewModel(TileModel model, ShapeRenderer renderer,
			int tileSize) {
		Gdx.app.debug(LOG, String.format("initializing (%d, %d)",
				model.getY(), model.getX()));

		this.model = model;
		this.renderer = renderer;
		this.tileSize = tileSize;

		/* initialize wall view models */
		walls = new WallViewModel[4];
		for (int i = 0; i < 4; ++i) {
			walls[i] = new WallViewModel(model.getWall(i),
					renderer);
			this.addActor(walls[i]);
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		Vector2 stagePos;

		batch.end();

		/*
		 * FIXME: we should be able to use the transform matrix
		 * in batch.
		 */
		renderer.setProjectionMatrix(getStage().getCamera().combined);
		stagePos = localToStageCoordinates(new Vector2(0f, 0f));

		/* draw box */
		renderer.begin(FilledRectangle);
		if (model.isBox()) {
			renderer.setColor(1f, 0f, 0f, 1f);
		} else {
			renderer.setColor(.8f, .8f, .8f, 1f);
		}
		renderer.filledRect(stagePos.x, stagePos.y,
				tileSize, tileSize);
		renderer.end();

		/* draw item */
		ItemModel item = model.getSpawnedItem();
		if (item != null) {
			renderer.begin(FilledRectangle);
			renderer.setColor(0f, 1f, 0f, 1f);
			renderer.filledRect(stagePos.x, stagePos.y,
					tileSize, tileSize);
			renderer.end();
		}

		/* draw frame */
		renderer.begin(Rectangle);
		renderer.setColor(0f, 0f, 0f, 1f);
		renderer.rect(stagePos.x, stagePos.y, tileSize, tileSize);
		renderer.end();

		batch.begin();

		/*
		 * Set transform for the children, and the default
		 * draw() will call drawChildren().
		 */
		renderer.setTransformMatrix(computeTransform());
		super.draw(batch, parentAlpha);
		renderer.identity();
	}
}
