package deco2800.arcade.pacman;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.*;

public class walls extends Actor {

	static enum WallType {
		OUTER_HOR, OUTER_VERT, OUTER_CORN_1
	}
	
	private final ShapeRenderer shapeRenderer;
	private final WallType type;
	
	private boolean built;
	
	walls(WallType type, ShapeRenderer shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
		this.type = type;
		this.built = false;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
	}
	
	
	// Add draw function. Look at mixmaze.Wall.java
}
