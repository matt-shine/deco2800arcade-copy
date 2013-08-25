package deco2800.arcade.mixmaze;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.FilledRectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import deco2800.arcade.mixmaze.domain.Direction;
import deco2800.arcade.mixmaze.domain.WallModel;

public class WallViewModel extends Actor {
	private WallModel model;
	private ShapeRenderer shapeRenderer;
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();
		shapeRenderer.begin(FilledRectangle);
		shapeRenderer.setColor(1f, 1f, 0f, 1f);
		if(model.getDirection() == Direction.WEST) {
			shapeRenderer.filledRect(-8f, 0f, 16f, 128f);
		} else if(model.getDirection() == Direction.NORTH) {
			shapeRenderer.filledRect(0f, 120f, 128f, 16f);
		} else if(model.getDirection() == Direction.EAST) {
			shapeRenderer.filledRect(120f, 0f, 16f, 128f);
		} else {
			shapeRenderer.filledRect(0f, -8f, 128f, 16f);
		}
		shapeRenderer.end();
		batch.begin();
		super.draw(batch, parentAlpha);
	}
	
	public void dispose() {
		shapeRenderer.dispose();
	}
	
	public WallViewModel(WallModel m) {
		if(m == null) {
			throw new IllegalArgumentException("m cannot be null.");
		}
		model = m;
		shapeRenderer = new ShapeRenderer();
	}
}
