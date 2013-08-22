package deco2800.arcade.mixmaze;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Rectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;

import deco2800.arcade.mixmaze.domain.TileModel;

public class TileViewModel extends Group {
	private TileModel model;
	private ShapeRenderer shapeRenderer;
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();
		shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
		
		// Render base rectangle
		shapeRenderer.begin(Rectangle);
		shapeRenderer.setColor(0f, 0f, 0f, 1f);
		shapeRenderer.rect(getX(), getY(), 128f, 128f);
		shapeRenderer.end();
		shapeRenderer.setTransformMatrix(computeTransform());
		
		batch.begin();
		super.draw(batch, parentAlpha);
		shapeRenderer.identity();
	}
	
	public void dispose() {
		shapeRenderer.dispose();
	}
	
	public TileViewModel(TileModel m) {
		model = m;
		shapeRenderer = new ShapeRenderer();
		setX(128f * model.getX());
		setX(128f * model.getY());
	}
}
