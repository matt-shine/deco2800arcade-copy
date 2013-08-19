package deco2800.arcade.arcadeui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
		
		
		this.getPrefWidth();
	}

	
	@Override
	public float getPrefWidth() {
		return 100;
	}
	
	@Override
	public float getPrefHeight() {
		return 100;
	}
	
	
	
	
	
	
}
