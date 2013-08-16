package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;


public class Brick {
	
	private Texture bricksImage;
	private TextureRegion bricksImageRegion;
	
//	protected int x;
//	protected int y;
	private final float width = 80f;
	private final float height = 20f;
	private boolean state;
	private Rectangle brickShape;
	
	public Brick(int x, int y) {
		//TextureAtlas myTextures = new TextureAtlas("packed.txt");
//		Pixmap pixels = new Pixmap("data/libgdx.png");
//		bricksImage = new Texture(Gdx.files.absolute("C:/Users/Owner/Desktop/
//		bricks.png"));
//		bricksImage = new Texture(1,1,Pixmap.Format.RGB888);
		brickShape = new Rectangle();
		this.brickShape.x = x;
		this.brickShape.y = y;
		this.brickShape.height = this.height;
		this.brickShape.width = this.width;
//		this.width = bricksImage.getWidth();
//		this.height = bricksImage.getHeight();
		this.state = true;
	}
	
	public boolean getState() {
		return this.state;
	}
	
	public Rectangle getShape() {
		return this.brickShape;
	}
	
	public void setState(boolean state) {
		this.state = state;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public void render(ShapeRenderer render){
		render.setColor(Color.GREEN);
		render.filledRect(brickShape.x, brickShape.y, brickShape.width, 
				brickShape.height, Color.YELLOW, Color.BLUE, Color.PINK, Color.RED);
	}
	
	public String toString() {
		return "brickpos: " + this.brickShape.x + ", " + this.brickShape.y;
	}
}