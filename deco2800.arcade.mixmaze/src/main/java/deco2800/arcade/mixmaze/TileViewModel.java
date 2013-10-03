package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.view.IItemModel;
import deco2800.arcade.mixmaze.domain.view.IItemModel.ItemType;
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
public final class TileViewModel extends Group implements TileNetworkView {
	static final TextureRegion PILE_BRICK_REGION;
	static final TextureRegion PICK_REGION;
	static final TextureRegion TNT_REGION;
	static final TextureRegion SELECTION_REGION;
	static final TextureRegion UNKNOWN_REGION;
	static final TextureRegion WHITE_REGION;
	static final TextureRegion BRICK_REGION;
	static final TextureRegion EMPTY_PICK_REGION;
	static final TextureRegion EMPTY_TNT_REGION;

	static {
		Texture texture = new Texture(Gdx.files.internal("item.png"));

		PILE_BRICK_REGION = new TextureRegion(texture, 0, 0, 256, 256);
		PICK_REGION = new TextureRegion(texture, 256, 0, 256, 256);
		TNT_REGION = new TextureRegion(texture, 512, 0, 256, 256);
		SELECTION_REGION = new TextureRegion(texture, 768, 0, 256, 256);
		BRICK_REGION = new TextureRegion(texture, 1024, 0, 256, 256);
		UNKNOWN_REGION = new TextureRegion(texture, 1280, 0, 256, 256);
		EMPTY_TNT_REGION = new TextureRegion(texture, 1536, 0,
				256, 256);
		EMPTY_PICK_REGION = new TextureRegion(texture, 1792, 0,
				256, 256);
		WHITE_REGION = new TextureRegion(texture, 2048, 0, 256, 256);
	}

	private final float tileSize;
	private final float offset;
	private final int x;
	private final int y;
	private final ShapeRenderer renderer;

	private int boxerId;
	private boolean[] isWallBuilt;
	private ItemType type;

	/**
	 * Constructor
	 *
	 * @param renderer	the renderer
	 * @param tileSize	the graphical size of the tile
	 */
	public TileViewModel(int x, int y,
			float tileSize, ShapeRenderer renderer) {
		this.tileSize = tileSize;
		offset = tileSize / 32;
		this.renderer = renderer;
		this.x = x;
		this.y = y;
		boxerId = 0;
		isWallBuilt = new boolean[4];
		type = NONE;
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

	@Override
	public void updateBoxer(int id) {
		boxerId = id;
	}

	@Override
	public void updateWall(int direction, boolean isBuilt) {
		isWallBuilt[direction] = isBuilt;
	}

	@Override
	public void updateType(ItemType type) {
		this.type = type;
	}

	private void drawBox() {
		renderer.begin(FilledRectangle);
		if (boxerId == 0) {
			renderer.setColor(.8f, .8f, .8f, 1f);
		} else if (boxerId == 1) {
			renderer.setColor(1f, 0f, 0f, 1f);
		} else if (boxerId == 2) {
			renderer.setColor(0f, 0f, 1f, 1f);
		}
		renderer.filledRect(0, 0, tileSize, tileSize);
		renderer.end();
	}

	private void drawWalls() {
		renderer.begin(FilledRectangle);
		renderer.setColor(1f, 1f, 0f, 1f);
		if (isWallBuilt[WEST])
			renderer.filledRect(0, 0, offset, tileSize);
		if (isWallBuilt[NORTH])
			renderer.filledRect(0, tileSize - offset,
					tileSize, offset);
		if (isWallBuilt[EAST])
			renderer.filledRect(tileSize - offset, 0,
					offset, tileSize);
		if (isWallBuilt[SOUTH])
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
		TextureRegion region;

		if (type != NONE) {
			switch (type) {
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
