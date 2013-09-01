/*
 * WallViewModel
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.Direction;
import deco2800.arcade.mixmaze.domain.WallModel;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer
		.ShapeType.FilledRectangle;

public class WallViewModel extends Actor {
	private WallModel model;
	private ShapeRenderer renderer;

	public WallViewModel(WallModel model, ShapeRenderer renderer) {
		this.model = model;
		this.renderer = renderer;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();

		if (model.isBuilt()) {
			renderer.begin(FilledRectangle);
			renderer.setColor(1f, 1f, 0f, 1f);
			if (model.getDirection() == Direction.WEST) {
				renderer.filledRect(-8f, 0f, 16f, 128f);
			} else if(model.getDirection() == Direction.NORTH) {
				renderer.filledRect(0f, 120f, 128f, 16f);
			} else if(model.getDirection() == Direction.EAST) {
				renderer.filledRect(120f, 0f, 16f, 128f);
			} else {
				renderer.filledRect(0f, -8f, 128f, 16f);
			}
			renderer.end();
		}

		batch.begin();
		super.draw(batch, parentAlpha);
	}
}
