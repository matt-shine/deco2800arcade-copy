/*
 * TileViewModel
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.view.IItemModel;
import deco2800.arcade.mixmaze.domain.view.IMixMazeModel;
import deco2800.arcade.mixmaze.domain.view.IPlayerModel;
import deco2800.arcade.mixmaze.domain.view.ITileModel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;

import static deco2800.arcade.mixmaze.domain.Direction.*;
import static deco2800.arcade.mixmaze.domain.view.IItemModel.ItemType.*;
import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.*;

/**
 * TileViewModel draws the associated tile.
 */
class TileViewModel extends Group {
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

	private final ITileModel model;
	private final IMixMazeModel gameModel;
	private final float tileSize;
	private final float offset;
	private final ShapeRenderer renderer;

	/**
	 * Constructor
	 *
	 * @param model 	the tile model
	 * @param gameModel 	the game model
	 * @param renderer	the renderer
	 * @param tileSize	the graphical size of the tile
	 */
	public TileViewModel(ITileModel model, IMixMazeModel gameModel,
			float tileSize, ShapeRenderer renderer) {
		this.model = model;
		this.gameModel = gameModel;
		this.tileSize = tileSize;
		this.offset = tileSize / 32;
		this.renderer = renderer;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();

		/* shape renderer drawing */
		renderer.setTransformMatrix(computeTransform());
		drawBox();
		drawWalls();
		drawCorners();
		renderer.identity();

		/* sprite batch drawing */
		batch.begin();
		applyTransform(batch, computeTransform());
		drawItem(batch);
		resetTransform(batch);
	}

	private void drawBox() {
		IPlayerModel p = model.getBoxer();

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
	}

	private void drawWalls() {
		renderer.begin(FilledRectangle);
		renderer.setColor(1f, 1f, 0f, 1f);
		if (model.getWall(WEST).isBuilt())
			renderer.filledRect(0, 0, offset, tileSize);
		if (model.getWall(NORTH).isBuilt())
			renderer.filledRect(0, tileSize - offset,
					tileSize, offset);
		if (model.getWall(EAST).isBuilt())
			renderer.filledRect(tileSize - offset, 0,
					offset, tileSize);
		if (model.getWall(SOUTH).isBuilt())
			renderer.filledRect(0, 0, tileSize, offset);
		renderer.end();
	}

	private void drawCorners() {
		renderer.begin(FilledRectangle);
		renderer.setColor(0f, 0f, 0f, 1f);
		renderer.filledRect(0, 0, offset, offset);
		renderer.filledRect(0, tileSize - offset, offset, offset);
		renderer.filledRect(tileSize - offset, tileSize - offset,
				offset, offset);
		renderer.filledRect(tileSize - offset, 0, offset, offset);
		renderer.end();
	}

	private void drawItem(SpriteBatch batch) {
		IItemModel item = gameModel.getSpawnedItem(
				model.getX(), model.getY());

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
	}

}
