package deco2800.arcade.mixmaze;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.FilledRectangle;
import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Rectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import deco2800.arcade.mixmaze.domain.TileModel;

public class TileViewModel extends Actor {
	private TileModel model;
	private ShapeRenderer shapeRenderer;
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();
		shapeRenderer.begin(FilledRectangle);
		shapeRenderer.setColor(1f, 1f, 0f, 1f);
		shapeRenderer.end();
		batch.begin();
		System.out.println("Working");
		super.draw(batch, parentAlpha);
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
