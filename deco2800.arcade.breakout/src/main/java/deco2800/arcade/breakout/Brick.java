package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

/**
 * Brick class for the breakout game
 * @author Carlie Smits
 *
 */
public class Brick {

	private SpriteBatch sBatch;
	private Sprite[] brickImgs;
	//The default height and width of the brick
	private final float width = 120f;
	private final float height = 40f;
	//A float for setting the size of small rectangles for collision detection
	private final float E = 0.001f;
	private boolean state;
	private Rectangle brickShape;

	/**
	 * Create the brick at the given x and y coordinates, using specified
	 * images
	 * @param x - the x coordinate of the brick
	 * @param y - the y coordinate of the brick
	 */
	public Brick(float x, float y) {
		brickImgs = new Sprite[6];
		brickImgs[0] = new Sprite(new Texture(Gdx.files.classpath("imgs/green.png")));
		brickImgs[1] = new Sprite(new Texture(Gdx.files.classpath("imgs/red.png")));
		brickImgs[2] = new Sprite(new Texture(Gdx.files.classpath("imgs/yellow.png")));
		brickImgs[3] = new Sprite(new Texture(Gdx.files.classpath("imgs/blue.png")));
		brickImgs[4] = new Sprite(new Texture(Gdx.files.classpath("imgs/orange.png")));
		brickImgs[5] = new Sprite(new Texture(Gdx.files.classpath("imgs/purple.png")));
		brickShape = new Rectangle();
		this.brickShape.x = x;
		this.brickShape.y = y;
		this.brickShape.height = this.height;
		this.brickShape.width = this.width;
		this.state = true;
	}
	
	/**
	 * Create the brick at the given x and y coordinates, using specified
	 * images and sizing 
	 * @param x - the x coordinate of the brick
	 * @param y - the y coordinate of the brick
	 * @param width - the width of the brick
	 * @param height - the height of the brick
	 */
	public Brick(float x, float y, float width, float height) {
		brickImgs = new Sprite[6];
		brickImgs[0] = new Sprite(new Texture(Gdx.files.classpath("imgs/green.png")));
		brickImgs[1] = new Sprite(new Texture(Gdx.files.classpath("imgs/red.png")));
		brickImgs[2] = new Sprite(new Texture(Gdx.files.classpath("imgs/yellow.png")));
		brickImgs[3] = new Sprite(new Texture(Gdx.files.classpath("imgs/blue.png")));
		brickImgs[4] = new Sprite(new Texture(Gdx.files.classpath("imgs/orange.png")));
		brickImgs[5] = new Sprite(new Texture(Gdx.files.classpath("imgs/purple.png")));
		brickShape = new Rectangle();
		this.brickShape.x = x;
		this.brickShape.y = y;
		this.brickShape.height = height;
		this.brickShape.width = width;
		this.state = true;
	}
	
	/**
	 * 
	 * @return the state of the brick i.e. whether the brick
	 * has been hit or not
	 */
	public boolean getState() {
		return this.state;
	}
	
	/**
	 * 
	 * @return the bricks rectangular form 
	 */
	public Rectangle getShape() {
		return this.brickShape;
	}

	/**
	 * 
	 * @param state - sets the current state of the brick 
	 * i.e. if the brick has been hit set state to be false
	 */
	public void setState(boolean state) {
		// Set the state of the brick (if hit -> false)
		this.state = state;
	}

	/**
	 * 
	 * @return - the bricks width
	 */
	public float getWidth() {
		return this.brickShape.width;
	}

	/**
	 * 
	 * @return - the bricks height
	 */
	public float getHeight() {
		return this.brickShape.height;
	}
	
	/**
	 * Creates a small rectangle on the left of the brick to 
	 * detect whether the ball hits the brick on this side, so 
	 * we can handle the velocity accordingly
	 * @param ball - the current game ball
	 * @return true if the ball has intersected this side, false
	 * otherwise
	 */
	public boolean checkLeftCollision(Circle ball) {
		Rectangle leftSide = new Rectangle();
		leftSide.x = this.brickShape.x;
		leftSide.y = this.brickShape.y - this.E;
		leftSide.width = this.E;
		leftSide.height = this.brickShape.height - this.E;
		return Intersector.overlapCircleRectangle(ball, leftSide);
	}
	
	/**
	 * Creates a small rectangle on the right of the brick to 
	 * detect whether the ball hits the brick on this side, so 
	 * we can handle the velocity accordingly
	 * @param ball - the current game ball
	 * @return true if the ball has intersected this side, false
	 * otherwise
	 */
	public boolean checkRightCollision(Circle ball) {
		Rectangle rightSide = new Rectangle();
		rightSide.x = this.brickShape.x + this.brickShape.width - this.E;
		rightSide.y = this.brickShape.y - this.E;
		rightSide.width = this.E;
		rightSide.height = this.brickShape.height - this.E;
		return Intersector.overlapCircleRectangle(ball, rightSide);
	}
	
	/**
	 * Creates a small rectangle on the top of the brick to 
	 * detect whether the ball hits the brick on this side, so 
	 * we can handle the velocity accordingly
	 * @param ball - the current game ball
	 * @return true if the ball has intersected this side, false
	 * otherwise
	 */
	public boolean checkTopCollision(Circle ball) {
		Rectangle topSide = new Rectangle();
		topSide.x = this.brickShape.x;
		topSide.y = this.brickShape.y;
		topSide.width = this.brickShape.width;
		topSide.height = this.E;
		return Intersector.overlapCircleRectangle(ball, topSide);
	}
	
	/**
	 * Creates a small rectangle on the bottom of the brick to 
	 * detect whether the ball hits the brick on this side, so 
	 * we can handle the velocity accordingly
	 * @param ball - the current game ball
	 * @return true if the ball has intersected this side, false
	 * otherwise
	 */
	public boolean checkBottomCollision(Circle ball) {
		Rectangle bottomSide = new Rectangle();
		bottomSide.x = this.brickShape.x;
		bottomSide.y = this.brickShape.y + this.brickShape.height - this.E;
		bottomSide.width = this.brickShape.width;
		bottomSide.height = this.E;
		return Intersector.overlapCircleRectangle(ball, bottomSide);
	}
	
	/**
	 * Render method that handles each level and it's specific styling
	 * @param level - the level to be rendered
	 * @param b - contains the rendering information
	 * @param index - relates to the bricks to be rendered in the
	 * first level
	 */
	public void render(int level, SpriteBatch b, int index) {
		if (level == 1) {
			renderLevelOne(index/8, b);
		}
		if (level == 2) {
			renderLevelTwo(b, index);
		}
		if (level == 3) {
			renderLevelThree(b, index);  
		}
		if (level == 4) {
			renderLevelFour(b, index);
		}
		if (level == 5) {
			renderLevelFive(b, index);
		}
		if (level == 6) {
			renderLevelSix(b, index);
		}
		if (level == 7){
			renderLevelSeven(b, index);
		}
		if (level == 8){
			renderLevelEight(b, index);
		}
		if (level == 9){
			renderLevelNine(b, index);
		}
		if (level == 10) {
			renderLevelTen(b, index);
		}
//		if (level == 11) {
//			renderLevelEleven(b);
//		}
	}
	//TODO: Delete this method when finished debugging
	public void renderLevelEleven(SpriteBatch b) {
		sBatch = b;
		sBatch.begin();
		sBatch.draw(brickImgs[0], brickShape.x, brickShape.y);
		sBatch.end();
	}
	
	/**
	 * The rendered bricks for level one
	 * @param num - a number used to determine what bricks to
	 * assign specific images
	 * @param b - contains the rendering method
	 */
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
		} else if (num == 4){
			sBatch.draw(brickImgs[3], brickShape.x, brickShape.y);
		} else if (num == 5){
			sBatch.draw(brickImgs[4], brickShape.x, brickShape.y);
		} else {
			sBatch.draw(brickImgs[5], brickShape.x, brickShape.y);	
		} 
		sBatch.end();
	}
	
	/**
	 * The rendered bricks for level two
	 * @param b - contains the rendering method
	 * @param index - determines what bricks to assign different
	 * images to
	 */
	public void renderLevelTwo(SpriteBatch b, int index) {
		sBatch = b;
		sBatch.begin();
		if (index < 10) {
			sBatch.draw(brickImgs[0], brickShape.x, brickShape.y);
		} else if (index >= 10 && index < 16){
			sBatch.draw(brickImgs[1], brickShape.x, brickShape.y);
		} else if (index >= 16 && index < 22){
			sBatch.draw(brickImgs[2], brickShape.x, brickShape.y);
		} else if (index >= 22 && index < 28){
			sBatch.draw(brickImgs[3], brickShape.x, brickShape.y);
		} else if (index >= 28 && index < 34){
			sBatch.draw(brickImgs[4], brickShape.x, brickShape.y);
		} else {
			sBatch.draw(brickImgs[5], brickShape.x, brickShape.y);	
		} 
		sBatch.end();
	}
	
	/**
	 * The rendered bricks for level three
	 * @param b - contains the rendering method
	 * @param index - determines what bricks to assign different
	 * images to
	 */
	public void renderLevelThree(SpriteBatch b, int index) {
		for (int i = 0; i < 6; i++) {
			brickImgs[i].setSize(40, 80);	
		}
		sBatch = b;
		sBatch.begin();
		if (index < 9) {
			sBatch.draw(brickImgs[0], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 9 && index < 18) {
			sBatch.draw(brickImgs[1], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 18 && index < 27) {
			sBatch.draw(brickImgs[2], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 27 && index < 36){
			sBatch.draw(brickImgs[3], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 36 && index < 48){
			sBatch.draw(brickImgs[4], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else {
			sBatch.draw(brickImgs[5], brickShape.x, brickShape.y, getWidth(), getHeight());
		}
		sBatch.end();
	}
	
	/**
	 * The rendered bricks for level four
	 * @param b - contains the rendering method
	 * @param index - determines what bricks to assign different 
	 * images to
	 */
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
	
	/**
	 * The rendered bricks for level five
	 * @param b - contains the rendering method
	 * @param index - determines what bricks to assign different 
	 * images to
	 */
	public void renderLevelFive(SpriteBatch b, int index) {
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
	
	/**
	 * The rendered bricks for level six
	 * @param b - contains the rendering method
	 * @param index - determine what bricks to assign different 
	 * images to
	 */
	public void renderLevelSix(SpriteBatch b, int index) {
		for (int i = 0; i < 5; i++) {
			brickImgs[i].setSize(40, 80);	
		}
		sBatch = b;
		sBatch.begin();
		if (index < 4) {
			sBatch.draw(brickImgs[0], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 4 && index < 12) {
			sBatch.draw(brickImgs[1], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 12 && index < 24) {
			sBatch.draw(brickImgs[2], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 24 && index < 40){
			sBatch.draw(brickImgs[3], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else {
			sBatch.draw(brickImgs[4], brickShape.x, brickShape.y, getWidth(), getHeight());
		}
		sBatch.end();
	}
	
	/**
	 * The rendered bricks for level seven
	 * @param b - contains the rendering method
	 * @param index - determine what bricks to assign different 
	 * images to
	 */
	public void renderLevelSeven(SpriteBatch b, int index) {
		for (int i = 0; i < 6; i++) {
			brickImgs[i].setSize(80, 40);	
		}
		sBatch = b;
		sBatch.begin();
		if (index < 14) {
			sBatch.draw(brickImgs[0], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 14 && index < 26) {
			sBatch.draw(brickImgs[1], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 26 && index < 36) {
			sBatch.draw(brickImgs[2], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 36 && index < 44){
			sBatch.draw(brickImgs[3], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 44 && index < 50){
			sBatch.draw(brickImgs[4], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else {
			sBatch.draw(brickImgs[5], brickShape.x, brickShape.y, getWidth(), getHeight());
		}
		sBatch.end();
	}
	
	/**
	 * The rendered bricks for level eight
	 * @param b - contains the rendering method
	 * @param index - determine what bricks to assign different 
	 * images to
	 */
	public void renderLevelEight(SpriteBatch b, int index) {
		sBatch = b;
		sBatch.begin();
		if (index < 48) {
			sBatch.draw(brickImgs[0], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else {
			sBatch.draw(brickImgs[3], brickShape.x, brickShape.y, getWidth(), getHeight());
		}
		sBatch.end();
	}
	
	/**
	 * The rendered bricks for level nine
	 * @param b - contains the rendering method
	 * @param index - determine what bricks to assign different
	 * images to
	 */
	public void renderLevelNine(SpriteBatch b, int index) {
		sBatch = b;
		sBatch.begin();
		if (index < 33) {
			sBatch.draw(brickImgs[5], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else {
			sBatch.draw(brickImgs[1], brickShape.x, brickShape.y, getWidth(), getHeight());
		}
		sBatch.end();
	}
	
	/**
	 * The rendered bricks for level ten
	 * @param b - contains the rendering method
	 * @param index - determine what bricks to assign different
	 * images to
	 */
	public void renderLevelTen(SpriteBatch b, int index) {
		sBatch = b;
		sBatch.begin();
		if (index < 26) {
			sBatch.draw(brickImgs[2], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 26 && index < 52) {
			sBatch.draw(brickImgs[0], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 52 && index < 74) {
			sBatch.draw(brickImgs[1], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 74 && index < 107){
			sBatch.draw(brickImgs[3], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else if (index >= 107 && index < 137){
			sBatch.draw(brickImgs[4], brickShape.x, brickShape.y, getWidth(), getHeight());
		} else {
			sBatch.draw(brickImgs[5], brickShape.x, brickShape.y, getWidth(), getHeight());
		}
		sBatch.end();
	}
	
	/**
	 * 
	 * @return a string representation of the bricks position
	 */
	public String toString() {
		return "brickpos: " + this.brickShape.x + ", " + this.brickShape.y;
	}
}