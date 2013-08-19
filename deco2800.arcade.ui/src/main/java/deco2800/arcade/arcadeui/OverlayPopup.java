package deco2800.arcade.arcadeui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class OverlayPopup extends WidgetGroup {

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
		
		
		shapeRenderer.begin(ShapeType.FilledRectangle);
		//shapeRenderer.setColor(1f, 1f, 1f, 1f);
		//shapeRenderer.rect(getX(), getY(), this.getWidth(), this.getHeight());
		shapeRenderer.end();
		
		
	}
	
	@Override
	public float getMinWidth() {
		return 100;
	}
	
	@Override
	public float getMinHeight() {
		return 100;
	}
	
	
	
	
	
	
}
