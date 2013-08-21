package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class ExtendedSprite extends Sprite {

	public ExtendedSprite(Texture t) {
		super(t);
		setOrigin(0,0);
		this.flip(false, true);
	}
	
	public boolean containsPoint(int x, int y) {
		
		Rectangle r = this.getBoundingRectangle();
		
		double leftSide = r.getX();
		double rightSide = r.getX() + r.getWidth();
		double topSide = r.getY();
		double bottomSide = r.getY() + r.getHeight();
		
		if(x > leftSide && x < rightSide && y > topSide && y < bottomSide) {
			return true;
		}
		return false;
	}
	
}
