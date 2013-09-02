package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class ExtendedSprite extends Sprite {

	private Rectangle originZone;
	private int player;
	private boolean field;
	private boolean monster;
	private String area;
	
	public ExtendedSprite(Texture t) {
		super(t);
		setOrigin(0,0);
		this.flip(false, true);
		originZone = null;
		setPlayer(0);
		setField(false);
		setMonster(false);
		setArea(null);
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
	
	public Rectangle getOriginZone() {
		return originZone;
	}
	
	public void setOriginZone(Rectangle r) {
		originZone = r;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public boolean isField() {
		return field;
	}

	public void setField(boolean field) {
		this.field = field;
	}

	public boolean isMonster() {
		return monster;
	}

	public void setMonster(boolean monster) {
		this.monster = monster;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
}
