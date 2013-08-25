package deco2800.arcade.mixmaze;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.FilledRectangle;
import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;

import deco2800.arcade.mixmaze.domain.TileModel;

public class TileViewModel extends Group {
	private static final String LOG = TileViewModel.class.getSimpleName();
	
	private TileModel model;
	private WallViewModel[] walls;
	
	private ShapeRenderer shapeRenderer;
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();
		shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
		if (model.isBox()) {
			shapeRenderer.begin(FilledRectangle);
			shapeRenderer.setColor(1f, 0f, 0f, 1f);
			shapeRenderer.filledRect(getX(), getY(), 128f, 128f);
			shapeRenderer.end();
		}
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
		for(int i = 0; i < 4; ++i) {
			walls[i].dispose();
		}
		shapeRenderer.dispose();
	}
	
	public TileViewModel(TileModel m) {
		if(m == null) {
			throw new IllegalArgumentException("m cannot be null.");
		}
		Gdx.app.debug(LOG, String.format("Initializing (%d, %d)", m.getX(), m.getY()));
		model = m;
		setX(128f * model.getX());
		setY(128f * model.getY());
		
		// Initialize wall view models
		walls = new WallViewModel[4];
		for(int i = 0; i < 4; ++i) {
			walls[i] = new WallViewModel(model.getWall(i));
			this.addActor(walls[i]);
		}
		shapeRenderer = new ShapeRenderer();
	}
}
