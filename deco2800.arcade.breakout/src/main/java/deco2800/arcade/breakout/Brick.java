package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Brick {
	
	private Texture bricksImage;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	private boolean state;
	
	public Brick(int x, int y) {
		this.bricksImage = new Texture(Gdx.files.classpath("resources/bricks.PNG"));
		this.x = x;
		this.y = y;
		
		this.width = bricksImage.getWidth();
		this.height = bricksImage.getHeight();
		this.state = true;
	}
	
	public boolean getState() {
		return this.state;
	}
	
	public void setState(boolean state) {
		this.state = state;
	}
}