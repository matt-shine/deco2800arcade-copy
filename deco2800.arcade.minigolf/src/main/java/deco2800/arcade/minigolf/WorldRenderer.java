package deco2800.arcade.minigolf;


import deco2800.arcade.minigolf.Block1.BlockType;
import deco2800.arcade.minigolf.Block1.FacingDir;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10; 
import com.badlogic.gdx.graphics.OrthographicCamera; 
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer; 
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType; 
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * 
 * This class is used to render the various blocks and objects onto the game
 * screen.
 * 
 * 
 */

@SuppressWarnings("unused")
public class WorldRenderer { 
	
	private static final float CAM_WIDTH = 1280f; 
	private static final float CAM_HEIGHT = 720f; 
	
	private World world; 
	private DirectionValues controller;
	private WorldController wControl;
	private OrthographicCamera cam;	
	ShapeRenderer debugRend = new ShapeRenderer();
	
	private Texture ballTexture, groundTexture, closedTexture, holeTexture,
	waterTexture, teleTexture;
	
	private Texture wallSouthTexture, wallWestTexture, 
	wallEastTexture, wallNorthTexture, invWallNorthTexture,
	invWallSouthTexture, invWallEastTexture, invWallWestTexture; 
	
	private Texture capBlockTexture;
	
	private Texture cornerSouthTexture, cornerNorthTexture,
	cornerEastTexture, cornerWestTexture, invCornerWestTexture,
	invCornerEastTexture, invCornerNorthTexture, invCornerSouthTexture;
	
	private Texture backgroundTexture, arrowTexture, diagNorthTexture,
	diagSouthTexture, diagEastTexture, diagWestTexture; 
	
	private SpriteBatch sprite; 
	private Sprite trajectorySprite;
	private Stage stage;
	private DirectionLogic directLogic; 
	
	private Trajectory traject;
	
	private int width, height; 
	private float ppuX; //pixels/unit on width
	private float ppuY; //pixels/unit on height
	private boolean debug = false; 
	public boolean renderTrajectory = true;
	
	public void setSize(int w, int h) {
		this.width = w; 
		this.height = h;
		ppuX = (float)width/CAM_WIDTH; 
		ppuY = (float)height/CAM_HEIGHT;
	}
	
	/* constructor */
	public WorldRenderer (World world, boolean debug, int level, 
			ArrayList<Integer> scoreCard) { 
		this.world = world; 
		this.wControl = new WorldController(this.world, level, scoreCard);
		this.cam = new OrthographicCamera(1024,720); 
		this.cam.position.set(640f, 360f, 0); 
		this.cam.update(); 
		this.debug = debug;
		this.renderTrajectory = true;
		this.sprite = new SpriteBatch(); 
		loadTextures(); 
	}
	/* render sprites and direction trajectory */
	public void render() {
		Ball ball = world.getBall();		
		if((ball.getVelocity().x == 0 && ball.getVelocity().y == 0)){
			drawBallTrajectory();
			directLogic.update();
		}
		if((ball.getVelocity().x == 0 && ball.getVelocity().y == 0 && !(ball.inHole))){
			  stage.act(); 
			  stage.draw();
			}				
		sprite.begin();
		    sprite.draw(backgroundTexture, 0, 0);
			drawGround();
			drawWall();
			drawDiags();
			drawCorners();
			drawInvWalls();
			drawCaps();
			drawWater();
			drawTele();
			drawHole();
			drawBall();
			
		sprite.end();
		if((ball.getVelocity().x == 0 && ball.getVelocity().y == 0 && !(ball.inHole))){
		  stage.act(); 
		  stage.draw();
		}
		
		if(debug) {
			debug(); 
		}		
	}
	
	/* get the power and direction of the ball trajectory, used in GameScreen */
	public float getPower(){
		return directLogic.getPower();
	}
	public Vector2 getDir(){
		return directLogic.getDirection();
	}
	
	/* load textures from file into specific variables */
	private void loadTextures() {
		ballTexture = new Texture (Gdx.files.internal("ball.png"));
		groundTexture = new Texture (Gdx.files.internal("grass.png"));
		closedTexture = new Texture (Gdx.files.internal("closed.png"));
		
		wallSouthTexture = new Texture (Gdx.files.internal("wall-s.png"));
		wallNorthTexture = new Texture (Gdx.files.internal("wall-n.png"));
		wallEastTexture = new Texture (Gdx.files.internal("wall-e.png"));
		wallWestTexture = new Texture (Gdx.files.internal("wall-w.png"));		
		
		cornerWestTexture = new Texture (Gdx.files.internal("corner-n.png"));
		cornerNorthTexture = new Texture (Gdx.files.internal("corner-e.png"));
		cornerEastTexture = new Texture (Gdx.files.internal("corner-s.png"));
		cornerSouthTexture = new Texture (Gdx.files.internal("corner-w.png"));		
		
		diagWestTexture = new Texture (Gdx.files.internal("diag-w.png"));
		diagNorthTexture = new Texture (Gdx.files.internal("diag-n.png"));
		diagEastTexture = new Texture (Gdx.files.internal("diag-e.png"));
		diagSouthTexture = new Texture (Gdx.files.internal("diag-s.png"));
		
		capBlockTexture = new Texture(Gdx.files.internal("grass.png"));
		
		holeTexture = new Texture (Gdx.files.internal("hole.png"));
		teleTexture = new Texture (Gdx.files.internal("tele.png"));
		waterTexture = new Texture (Gdx.files.internal("water.png"));
		backgroundTexture.setEnforcePotImages(false);//disable base 2 images
		backgroundTexture = new Texture(Gdx.files.internal("background1.png"));
		arrowTexture = new Texture(Gdx.files.internal("circle.png"));
		arrowTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	/* draw the shot trajectory for the ball */
	private void drawBallTrajectory() {
		Ball ball = world.getBall();		
		trajectorySprite = new Sprite(arrowTexture);
		controller = new DirectionValues();
		
		directLogic = new DirectionLogic(controller, ball.getPosition());
		traject = new Trajectory(controller, trajectorySprite, this.world);
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		stage.addActor(traject);
		directLogic.update();
		
	}
	
	// Draw the ball blocks
	private void drawBall() {			
		Ball ball = world.getBall();
		if (ball.inHole) return;
		sprite.draw(ballTexture, ball.getPosition().x * ppuX, ball.getPosition().y * ppuY,
				Ball.SIZE * ppuX, Ball.SIZE * ppuY);		
	}
	
	// Draw all ground blocks
	private void drawGround(){
		for(Block1 block : world.getGroundBlocks()){
			sprite.draw(groundTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, 
					Block1.SIZE * ppuX, Block1.SIZE * ppuY);
		}
	}
	// Draw all water blocks
	private void drawWater() {
		for(Block1 block : world.getWaterBlocks()){
			sprite.draw(waterTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, 
					Block1.SIZE * ppuX, Block1.SIZE * ppuY);
		}
	}
	// Draw all teleporters
	private void drawTele() {
		  for(Block1 block : world.getTeleBlocks()){
			  sprite.draw(teleTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, 
					    Block1.SIZE * ppuX, Block1.SIZE * ppuY);
		  }
		}
	
	// Draw all walls
	private void drawWall() {
		for(Block1 block : world.getWallBlocks()){
			//if(block.type == BlockType.Wall)
			//change texture position based on it's FacingDir
			if(block.dir == FacingDir.NORTH){ //draw blocks from getWallBlocks() that face north
				sprite.draw(wallNorthTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, 
						Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0, 0, 32, 32, false, false); 
				}
				if(block.dir == FacingDir.SOUTH){
					sprite.draw(wallSouthTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, 
							Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0, 0, 32, 32, false, false);	
				}
				if(block.dir == FacingDir.EAST){
					sprite.draw(wallEastTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, 
							Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0, 0, 32, 32, false, false);
				}
				if(block.dir == FacingDir.WEST){
					sprite.draw(wallWestTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, 
							Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0, 0, 32, 32, false, false);
				}
			}
		}
	//Draw all corners
	private void drawCorners() {
		for(Block1 block : world.getCornerBlocks()){
			//change texture position based on FacingDir
			if(block.dir == FacingDir.NORTH){ //draw blocks from getCornerBlocks() 
				sprite.draw(cornerNorthTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, 
						Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0, 0, 32, 32, false, false); 
			}
			if(block.dir == FacingDir.SOUTH){
				sprite.draw(cornerSouthTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, 
						Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0, 0, 32, 32, false, false);	
			}
			if(block.dir == FacingDir.EAST){
				sprite.draw(cornerEastTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, 
						Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0, 0, 32, 32, false, false);	
			} 
			if(block.dir == FacingDir.WEST){
				sprite.draw(cornerWestTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, 
						Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0, 0, 32, 32, false, false);	
			}
		}
	}	
	// Draw all diagonals
	private void drawDiags() {
		for(Block1 block : world.getDiagBlocks()){
			if(block.dir == FacingDir.NORTH)
				sprite.draw(diagNorthTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY,
						Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0,0,32,32,false,false);
			if(block.dir == FacingDir.SOUTH)
				sprite.draw(diagSouthTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY,
						Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0,0,32,32,false,false);
			if(block.dir == FacingDir.EAST)
				sprite.draw(diagEastTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY,
						Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0,0,32,32,false,false);
			if(block.dir == FacingDir.WEST)
				sprite.draw(diagWestTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY,
						Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0,0,32,32,false,false);
		}
	}
	// Draw all Inverted Walls
	private void drawInvWalls() {
		for(Block1 block : world.getInvWallBlocks()){
			sprite.draw(capBlockTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY,
					Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0,0,32,32,false,false);
		}
	}
	
	// Draw all the cap blocks
	private void drawCaps(){
		for(Block1 block : world.getCapBlocks()){
			sprite.draw(capBlockTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY,
						Block1.SIZE * ppuX, Block1.SIZE * ppuY, 0,0,32,32,false,false);
		}
	}
	
	private void drawHole() {
		for(Block1 block : world.getHoleBlock()) {
			sprite.draw(holeTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, 
					Block1.SIZE * ppuX, Block1.SIZE * ppuY);
		}
	}

	/* if the constructor is called with a true value, will enable debugging 
	 * This draws a coloured outline of the specified objects' bounds (Wall, Ball)
	 */
	public void debug() { 
		//render the outline of the blocks
		debugRend.setProjectionMatrix(cam.combined); 
		debugRend.begin(ShapeType.Rectangle); 
		for(Block1 block : world.getWallBlocks()) {
			Polygon poly = block.getBounds(); 
			float x1 = poly.getX();
			float y1 = poly.getY(); 
			debugRend.setColor(new Color(1,0,0,1)); //red
			debugRend.rect(x1, y1, Block1.SIZE, Block1.SIZE);
		}
		//render the outline of the ball
		Ball ball = world.getBall(); 
		Polygon poly = ball.getBounds(); 
		float x1 = poly.getX(); 
		float y1 = poly.getY(); 
		debugRend.setColor(new Color(0,1,0,1)); //green
		debugRend.rect(x1, y1, Ball.SIZE, Ball.SIZE);
		debugRend.end();
	}
	
	public void dispose() { 
		stage.dispose();
		sprite.dispose();
		
		
		
	}
	
}