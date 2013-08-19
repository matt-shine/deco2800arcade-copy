package deco2800.arcade.arcadeui;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Rectangle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;

public class OverlayPopup extends Group {

	private ShapeRenderer shapeRenderer;
	
	
	public OverlayPopup() {
		super();
		
		shapeRenderer = new ShapeRenderer();
	}
	
	@Override
	public void act(float d) {
		super.act(d);
		
		//TODO: Actions will be notifications to display
		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		shapeRenderer.setProjectionMatrix(
				getStage().getCamera().combined);
		
		
		shapeRenderer.begin(Rectangle);
		shapeRenderer.setColor(1f, 1f, 1f, 1f);
		shapeRenderer.rect(getX(), getY(), 128f, 128f);
		shapeRenderer.end();

		super.draw(batch, parentAlpha);
		
	}
	
	
	
	
	
	
	
	
	
}
