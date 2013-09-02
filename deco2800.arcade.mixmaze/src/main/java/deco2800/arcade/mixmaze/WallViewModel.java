/*
 * WallViewModel
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.WallModel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static deco2800.arcade.mixmaze.domain.Direction.*;
import static com.badlogic.gdx.graphics.glutils.ShapeRenderer
		.ShapeType.FilledRectangle;

public class WallViewModel extends Actor {
	private WallModel model;
	private ShapeRenderer renderer;
	private int dir;

	/**
	 * Constructor.
	 *
	 * @param model		the {@link WallModel}
	 * @param renderer	the <code>ShapeRenderer</code> for drawing
	 */
	public WallViewModel(WallModel model, int dir, ShapeRenderer renderer) {
		this.model = model;
		this.dir = dir;
		this.renderer = renderer;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();

		if (model.isBuilt()) {
			renderer.begin(FilledRectangle);
			renderer.setColor(1f, 1f, 0f, 1f);
			switch (dir) {
			case WEST:
				renderer.filledRect(0f, 0f, 4f, 128f);
				break;
			case NORTH:
				renderer.filledRect(0f, 124f, 128f, 4f);
				break;
			case EAST:
				renderer.filledRect(124f, 0f, 4f, 128f);
				break;
			case SOUTH:
				renderer.filledRect(0f, 0f, 128f, 4f);
				break;
			}
			renderer.end();
		}

		batch.begin();
		super.draw(batch, parentAlpha);
	}
}
