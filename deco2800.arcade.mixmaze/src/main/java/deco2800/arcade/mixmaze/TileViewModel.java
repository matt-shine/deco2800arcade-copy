/*
 * TileViewModel
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.ItemModel;
import deco2800.arcade.mixmaze.domain.TileModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import static deco2800.arcade.mixmaze.domain.ItemModel.Type.*;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer
		.ShapeType.FilledRectangle;
import static com.badlogic.gdx.graphics.glutils.ShapeRenderer
		.ShapeType.Rectangle;

public class TileViewModel extends Group {
	private static final String LOG = TileViewModel.class.getSimpleName();
	private static final TextureRegion BRICK_REGION;
	private static final TextureRegion PICK_REGION;
	private static final TextureRegion TNT_REGION;
	private static final TextureRegion UNKNOWN_REGION;

	static {
		Texture texture = new Texture(Gdx.files.internal("item.png"));

		BRICK_REGION = new TextureRegion(texture, 0, 0, 256, 256);
		PICK_REGION = new TextureRegion(texture, 256, 0, 256, 256);
		TNT_REGION = new TextureRegion(texture, 512, 0, 256, 256);
		UNKNOWN_REGION = new TextureRegion(texture, 768, 0, 256, 256);
	}

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

		/* draw frame */
		renderer.begin(Rectangle);
		renderer.setColor(0f, 0f, 0f, 1f);
		renderer.rect(stagePos.x, stagePos.y, tileSize, tileSize);
		renderer.end();

		batch.begin();
		applyTransform(batch, computeTransform());

		/* draw item */
		ItemModel item = model.getSpawnedItem();
		if (item != null) {
			TextureRegion region;

			switch (item.getType()) {
			case BRICK:
				region = BRICK_REGION;
				break;
			case PICK:
				region = PICK_REGION;
				break;
			case TNT:
				region = TNT_REGION;
				break;
			default:
				region = UNKNOWN_REGION;
			}
			batch.draw(region, 0, 0, tileSize, tileSize);
		}

		/*
		 * Set transform for the children, and the default
		 * draw() will call drawChildren().
		 */
		renderer.setTransformMatrix(computeTransform());
		drawChildren(batch, parentAlpha);
		renderer.identity();
		resetTransform(batch);
	}
}
