package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Brick {

	private Texture bricksImage;
	private TextureRegion bricksImageRegion;
	private Sprite brickImg;
	private SpriteBatch sBatch;

	// protected int x;
	// protected int y;

	private final float width = 120f;
	private final float height = 40f;
	private final float E = 0.001f;
	private boolean state;
	private Rectangle brickShape;

	public Brick(int x, int y) {
		// TextureAtlas myTextures = new TextureAtlas("packed.txt");
		// Pixmap pixels = new Pixmap("data/libgdx.png");
		// bricksImage = new Texture(Gdx.files.absolute("C:/Users/Owner/Desktop/
		// bricks.png"));
		// bricksImage = new Texture(1,1,Pixmap.Format.RGB888);
		brickShape = new Rectangle();
		this.brickShape.x = x;
		this.brickShape.y = y;
		this.brickShape.height = this.height;
		this.brickShape.width = this.width;
		// this.width = bricksImage.getWidth();
		// this.height = bricksImage.getHeight();
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
	/*
	 * Creates a small rectangle on the left of the brick to 
	 * detect whether the ball hits the brick. This is so
	 * we can then determine the appropriate way to handle the velocity
	 * of the ball 
	 */
	public boolean checkLeftCollision(Rectangle ball) {
		Rectangle leftSide = new Rectangle();
		leftSide.x = this.brickShape.x;
		leftSide.y = this.brickShape.y - this.E;
		leftSide.width = this.E;
		leftSide.height = this.height - this.E;
		return ball.overlaps(leftSide);
	}
	/*
	 * Creates a small rectangle on the right of the brick to 
	 * detect whether the ball hits the brick. This is so
	 * we can then determine the appropriate way to handle the velocity
	 * of the ball 
	 */
	public boolean checkRightCollision(Rectangle ball) {
		Rectangle rightSide = new Rectangle();
		rightSide.x = this.brickShape.x + this.width - this.E;
		rightSide.y = this.brickShape.y - this.E;
		rightSide.width = this.E;
		rightSide.height = this.height - this.E;
		return ball.overlaps(rightSide);
	}
	/*
	 * Creates a small rectangle on the top of the brick to 
	 * detect whether the ball hits the brick. This is so
	 * we can then determine the appropriate way to handle the velocity
	 * of the ball 
	 */
	public boolean checkTopCollision(Rectangle ball) {
		Rectangle topSide = new Rectangle();
		topSide.x = this.brickShape.x;
		topSide.y = this.brickShape.y;
		topSide.width = this.width;
		topSide.height = this.E;
		return ball.overlaps(topSide);
	}
	/*
	 * Creates a small rectangle on the bottom of the brick to 
	 * detect whether the ball hits the brick. This is so
	 * we can then determine the appropriate way to handle the velocity
	 * of the ball 
	 */
	public boolean checkBottomCollision(Rectangle ball) {
		Rectangle bottomSide = new Rectangle();
		bottomSide.x = this.brickShape.x;
		bottomSide.y = this.brickShape.y + this.height - this.E;
		bottomSide.width = this.width;
		bottomSide.height = this.E;
		return ball.overlaps(bottomSide);
	}
	
	public void create() {
		sBatch = new SpriteBatch();
	}
	
	public void render(ShapeRenderer render) {
//		brickImg = new Sprite(new Texture(Gdx.files.classpath("imgs/brick.png")));
//		brickImg.setSize(width, height);
//		sBatch = batch;
//		sBatch.begin();
//		sBatch.draw(brickImg, brickShape.x, brickShape.y);
//		sBatch.end();
		render.setColor(Color.GREEN);
		render.filledRect(brickShape.x, brickShape.y, brickShape.width,
				brickShape.height, Color.LIGHT_GRAY, Color.GRAY, Color.GRAY, Color.LIGHT_GRAY);
	}

	public String toString() {
		return "brickpos: " + this.brickShape.x + ", " + this.brickShape.y;
	}
}