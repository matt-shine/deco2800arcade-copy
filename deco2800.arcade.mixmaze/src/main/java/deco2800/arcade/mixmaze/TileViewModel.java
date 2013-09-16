/*
 * TileViewModel
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.ItemModel;
import deco2800.arcade.mixmaze.domain.PlayerModel;
import deco2800.arcade.mixmaze.domain.TileModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;

import static deco2800.arcade.mixmaze.domain.Direction.*;
import static deco2800.arcade.mixmaze.domain.ItemModel.ItemType.*;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.*;

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

	private final TileModel model;
	private final ShapeRenderer renderer;
	private final int tileSize;

	/**
	 * Constructor
	 *
	 * @param model 	the tile model
	 * @param renderer	the renderer
	 * @param tileSize	the graphical size of the tile
	 */
	public TileViewModel(TileModel model, ShapeRenderer renderer,
			int tileSize) {
		this.model = model;
		this.renderer = renderer;
		this.tileSize = tileSize;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();

		/*
		 * Begin shape renderer drawing.
		 */
		renderer.setTransformMatrix(computeTransform());

		/* draw box */
		PlayerModel p = model.getBoxer();
		renderer.begin(FilledRectangle);
		if (p == null) {
			renderer.setColor(.8f, .8f, .8f, 1f);
		} else if (p.getPlayerID() == 1) {
			renderer.setColor(1f, 0f, 0f, 1f);
		} else if (p.getPlayerID() == 2) {
			renderer.setColor(0f, 0f, 1f, 1f);
		}
		renderer.filledRect(0, 0, tileSize, tileSize);
		renderer.end();

		/* draw frame */
		renderer.begin(Rectangle);
		renderer.setColor(0f, 0f, 0f, 1f);
		renderer.rect(0, 0, tileSize, tileSize);
		renderer.end();

		/* draw wall */
		renderer.begin(FilledRectangle);
		renderer.setColor(1f, 1f, 0f, 1f);
		if (model.getWall(WEST).isBuilt())
			renderer.filledRect(0, 0, 4f, 128f);
		if (model.getWall(NORTH).isBuilt())
			renderer.filledRect(0, 124f, 128f, 4f);
		if (model.getWall(EAST).isBuilt())
			renderer.filledRect(124f, 0, 4f, 128f);
		if (model.getWall(SOUTH).isBuilt())
			renderer.filledRect(0, 0, 128f, 4f);
		renderer.end();

		renderer.identity();

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
