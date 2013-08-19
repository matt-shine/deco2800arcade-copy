package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ExtendedSprite extends Sprite {

	public ExtendedSprite(Texture t) {
		super(t);
	}
	
	public boolean containsPoint(int x, int y) {
		double leftSide = this.getX();
		double rightSide = this.getX() + this.getWidth()*this.getScaleX();
		double topSide = this.getY();
		double bottomSide = this.getY() + this.getHeight()*this.getScaleY();
		
		if(x > leftSide && x < rightSide && y > topSide && y < bottomSide) {
			return true;
		}
		return false;
	}
	
}
