package deco2800.cyra.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CutsceneObject extends MovableEntity{

	private Texture texture;
	private Rectangle collRect;
	private float xOrigin;
	private float yOrigin;
	public CutsceneObject(Texture texture, Vector2 pos, float width, float height) {
		super(0, 0, pos, width, height);
		this.texture = texture;
		collRect = new Rectangle (0,0,0,0);
		xOrigin = 0;
		yOrigin = 0;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public Rectangle getCollisionRectangle() {
		return collRect;
	}
	
	public void setCollisionRectangle(float xOrigin, float yOrigin, float width, float height) {
		collRect.set(new Rectangle(getPosition().x + xOrigin,getPosition().y+yOrigin,width,height));
		this.xOrigin=xOrigin;
		this.yOrigin = yOrigin;
	}
	
	@Override
	public void update(Ship ship) {
		super.update(ship);
		collRect.x = getPosition().x + xOrigin;
		collRect.y = getPosition().y + yOrigin;
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleTopOfMovingPlatform(MovablePlatform movablePlatform) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleXCollision(Rectangle tile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleYCollision(Rectangle tile, boolean onMovablePlatform,
			MovablePlatform movablePlatform) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleNoTileUnderneath() {
		// TODO Auto-generated method stub
		
	}

}
