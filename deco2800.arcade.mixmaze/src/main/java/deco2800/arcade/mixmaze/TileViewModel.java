/*
 * TileViewModel
 */
package deco2800.arcade.mixmaze;

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

	/* position relative to the game board */
	private Vector2 localPos;

	/**
	 * Constructor
	 */
	public TileViewModel(TileModel model, ShapeRenderer renderer) {
		Gdx.app.debug(LOG, String.format("initializing (%d, %d)",
				model.getRow(), model.getColumn()));

		this.model = model;
		this.renderer = renderer;
		localPos = new Vector2(128f * model.getColumn(),
				128f * model.getRow());

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
		renderer.setProjectionMatrix(getStage().getCamera().combined);
		//stagePos = localToStageCoordinates(localPos);
		stagePos = localToStageCoordinates(new Vector2(0f, 0f));

		//if (model.isBox()) {
		if (true) {
			renderer.begin(FilledRectangle);
			renderer.setColor(1f, 0f, 0f, 1f);
			renderer.filledRect(stagePos.x, stagePos.y,
					128f, 128f);
			renderer.end();
		}

		renderer.begin(Rectangle);
		renderer.setColor(0f, 0f, 0f, 1f);
		renderer.rect(stagePos.x, stagePos.y, 128f, 128f);
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
