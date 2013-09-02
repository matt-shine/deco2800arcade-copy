package deco2800.arcade.mixmaze;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import deco2800.arcade.mixmaze.domain.WallModel;

public class WallViewModel extends Actor {
	private WallModel model;
	private ShapeRenderer shapeRenderer;
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();
		
		// If the wall is built, render it
		if(model.isBuilt()) { 
			
		}
		
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
