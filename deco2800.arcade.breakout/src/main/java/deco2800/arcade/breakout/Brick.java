package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Brick class for the breakout game
 * @author Carlie Smits
 *
 */
public class Brick {

	private SpriteBatch sBatch;
	private Sprite[] brickImgs;

	// protected int x;
	// protected int y;

	private final float width = 120f;
	private final float height = 40f;
	private final float E = 0.001f;
	private boolean state;
	private Rectangle brickShape;

	/**
	 * Create the brick at the given x and y co-ordinates
	 * @param x
	 * @param y
	 */
	public Brick(float x, float y) {
		//TextureAtlas myTextures = new TextureAtlas("packed.txt");
		// Pixmap pixels = new Pixmap("data/libgdx.png");
		brickImgs = new Sprite[6];
		brickImgs[0] = new Sprite(new Texture(Gdx.files.classpath("imgs/green.png")));
		brickImgs[1] = new Sprite(new Texture(Gdx.files.classpath("imgs/red.png")));
		brickImgs[2] = new Sprite(new Texture(Gdx.files.classpath("imgs/yellow.png")));
		brickImgs[3] = new Sprite(new Texture(Gdx.files.classpath("imgs/blue.png")));
		brickImgs[4] = new Sprite(new Texture(Gdx.files.classpath("imgs/orange.png")));
		brickImgs[5] = new Sprite(new Texture(Gdx.files.classpath("imgs/purple.png")));
//		bricksImage = new Texture(Gdx.files.absolute("C:/Users/Owner/Desktop/bricks.png"));
//		bricksImage = new Texture(1,1,Pixmap.Format.RGB888);
		brickShape = new Rectangle();
		this.brickShape.x = x;
		this.brickShape.y = y;
		this.brickShape.height = this.height;
		this.brickShape.width = this.width;
		// this.width = bricksImage.getWidth();
		// this.height = bricksImage.getHeight();
		this.state = true;
	}
	
	public Brick(float x, float y, float width, float height) {
		brickShape = new Rectangle();
		this.brickShape.x = x;
		this.brickShape.y = y;
		this.brickShape.height = height;
		this.brickShape.width = width;
		this.state = true;
	}

	// Check if the brick has already been hit or not
	public boolean getState() {
		return this.state;
	}

	// Getter method for the brick's rectangular form
	public Rectangle getShape() {
		return this.brickShape;
	}

	// Set the state of the brick (if hit -> false)
	public void setState(boolean state) {
		this.state = state;
	}

	// Getter method for bricks width
	public float getWidth() {
		return this.width;
	}

	// Getter method for the bricks height
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
//		sBatch = new SpriteBatch();
	}
	
	public void render(ShapeRenderer render, int num, int level, SpriteBatch b, int index) {
//		brickImg = new Sprite(new Texture(Gdx.files.classpath("imgs/brick.png")));
//		brickImg.setSize(width, height);
//		sBatch = batch;
//		sBatch.begin();
//		sBatch.draw(brickImg, brickShape.x, brickShape.y);
//		sBatch.end();
		if (level == 1) {
			renderLevelOne(num, b);
		}
		if (level == 2) {
			renderLevelTwo(render, num);
		}
		if (level == 3) {
			renderLevelThree(render, num);
		}
		if (level == 4) {
			renderLevelFour(b, index);
		}
	}
	
	public void renderLevelOne(int num, SpriteBatch b) {
		for (int i = 0; i < 6; i++) {
			brickImgs[i].setSize(width, height);	
		}
		sBatch = b;
		sBatch.begin();
		num = num%6;
		if (num == 1) {
			sBatch.draw(brickImgs[0], brickShape.x, brickShape.y);
		} else if (num == 2){
			sBatch.draw(brickImgs[1], brickShape.x, brickShape.y);
		} else if (num == 3){
			sBatch.draw(brickImgs[2], brickShape.x, brickShape.y);
//			render.filledRect(brickShape.x, brickShape.y, brickShape.width,
//					brickShape.height, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
		} else if (num == 4){
			sBatch.draw(brickImgs[3], brickShape.x, brickShape.y);
		} else if (num == 5){
			sBatch.draw(brickImgs[4], brickShape.x, brickShape.y);
		} else {
			sBatch.draw(brickImgs[5], brickShape.x, brickShape.y);	
		} 
		sBatch.end();
	}
	
	public void renderLevelTwo(ShapeRenderer render, int num) {
		num = num%4;
		if (num == 1) {
			render.filledRect(brickShape.x, brickShape.y, brickShape.width,
					brickShape.height, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
		} else if (num == 2) {
			render.filledRect(brickShape.x, brickShape.y, brickShape.width,
					brickShape.height, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW);
		} else if (num == 3) {
			render.filledRect(brickShape.x, brickShape.y, brickShape.width,
					brickShape.height, Color.RED, Color.RED, Color.RED, Color.RED);
		} else {
			render.filledRect(brickShape.x, brickShape.y, brickShape.width,
					brickShape.height, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
		}
	}
	
	public void renderLevelThree(ShapeRenderer render, int num) {
		num = num%4;
		if (num == 1) {
			render.filledRect(brickShape.x, brickShape.y, brickShape.width,
					brickShape.height, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
		} else if (num == 2) {
			render.filledRect(brickShape.x, brickShape.y, brickShape.width,
					brickShape.height, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW);
		} else if (num == 3) {
			render.filledRect(brickShape.x, brickShape.y, brickShape.width,
					brickShape.height, Color.RED, Color.RED, Color.RED, Color.RED);
		} else {
			render.filledRect(brickShape.x, brickShape.y, brickShape.width,
					brickShape.height, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
		}
	}
	
	public void renderLevelFour(SpriteBatch b, int index) {
		for (int i = 0; i < 4; i++) {
			brickImgs[i].setSize(width, height);	
		}
		sBatch = b;
		sBatch.begin();
		if (index < 4) {
			sBatch.draw(brickImgs[0], brickShape.x, brickShape.y);
		} else if (index >= 4 && index < 10) {
			sBatch.draw(brickImgs[1], brickShape.x, brickShape.y);
		} else if (index >= 10 && index < 20) {
			sBatch.draw(brickImgs[2], brickShape.x, brickShape.y);
		} else {
			sBatch.draw(brickImgs[3], brickShape.x, brickShape.y);
		}
		sBatch.end();
	}
	// return string representation of the bricks position
	public String toString() {
		return "brickpos: " + this.brickShape.x + ", " + this.brickShape.y;
	}
}