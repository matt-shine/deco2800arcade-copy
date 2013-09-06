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

import static deco2800.arcade.mixmaze.domain.Direction.*;
import static deco2800.arcade.mixmaze.domain.ItemModel.ItemType.*;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer
		.ShapeType.FilledRectangle;
import static com.badlogic.gdx.graphics.glutils.ShapeRenderer
		.ShapeType.Rectangle;

public class TileViewModel extends Group {
	static final TextureRegion PILE_BRICK_REGION;
	static final TextureRegion PICK_REGION;
	static final TextureRegion TNT_REGION;
	static final TextureRegion UNKNOWN_REGION;
	static final TextureRegion BRICK_REGION;

	static {
		Texture texture = new Texture(Gdx.files.internal("item.png"));

		PILE_BRICK_REGION = new TextureRegion(texture, 0, 0, 256, 256);
		PICK_REGION = new TextureRegion(texture, 256, 0, 256, 256);
		TNT_REGION = new TextureRegion(texture, 512, 0, 256, 256);
		UNKNOWN_REGION = new TextureRegion(texture, 768, 0, 256, 256);
		BRICK_REGION = new TextureRegion(texture, 1024, 0, 256, 256);
	}

	private static final String LOG = TileViewModel.class.getSimpleName();

	/** The tile model. */
	private final TileModel model;
	private final ShapeRenderer renderer;
	private final int tileSize;

	/**
	 * Constructor
	 */
	public TileViewModel(TileModel model, ShapeRenderer renderer,
			int tileSize) {
		this.model = model;
		this.renderer = renderer;
		this.tileSize = tileSize;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		Vector2 stagePos;

		batch.end();

		/*
		 * Begin shape renderer drawing.
		 */
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

		/* draw wall */
		renderer.begin(FilledRectangle);
		renderer.setColor(1f, 1f, 0f, 1f);
		if (model.getWall(WEST).isBuilt())
			renderer.filledRect(stagePos.x, stagePos.y, 4f, 128f);
		if (model.getWall(NORTH).isBuilt())
			renderer.filledRect(stagePos.x, stagePos.y + 124f,
					128f, 4f);
		if (model.getWall(EAST).isBuilt())
			renderer.filledRect(stagePos.x + 124f, stagePos.y,
					4f, 128f);
		if (model.getWall(SOUTH).isBuilt())
			renderer.filledRect(stagePos.x, stagePos.y, 128f, 4f);
		renderer.end();

		/*
		 * Begin batch drawing.
		 */
		batch.begin();
		applyTransform(batch, computeTransform());

		/* draw item */
		ItemModel item = model.getSpawnedItem();
		if (item != null) {
			TextureRegion region;

			switch (item.getType()) {
			case BRICK:
				region = PILE_BRICK_REGION;
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

		resetTransform(batch);
	}
}
