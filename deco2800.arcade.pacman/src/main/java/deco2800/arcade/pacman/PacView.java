package deco2800.arcade.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import deco2800.arcade.pacman.PacChar.PacState;

/**
 * A view for the Pacman game- separates out all LibGDX drawing functions 
 * because these don't work correctly with JUnit tests
 * @author Nicholas
 *
 */
public class PacView {

	private static OrthographicCamera camera;
	private static SpriteBatch batch;
	private static BitmapFont scoreText; 
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;	
	
	
	// sprite sheet, divided into array of arrays of 8x8 tile images
	public static final TextureRegion[][] tileSprites = TextureRegion.split(
			new Texture(Gdx.files.internal("wallsAndPellets.png")), 8, 8);
	// Static variables for pulling sprites from pacman sheet
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 4;
	public static TextureRegion[] pacmanFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	private static TextureRegion currentFrame; // for pacman animation
	private static final int GHOST_LENGTH = 8;
	private static final int GHOST_NUM = 4;
	public static TextureRegion[][] ghostFrames = new TextureRegion[GHOST_NUM][GHOST_LENGTH];
	
	private static GameMap gameMap;
	
	
	/** Constructor, probably never to be used since I'll treat this as just a
	 * collection of methods like the Math class
	 */
	public PacView() {
	}
	//TODO add player and blinky into this pretend constructor, or make the gameMap the model
	//and have them added on to that in the main Pacman class, then just pass the gameMap here like it currently is.
	public static void setUp(GameMap gameMap) {
		//Initialize camera
		camera = new OrthographicCamera();
		// set resolution
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		// initialise spriteBatch for drawing things
		batch = new SpriteBatch();		
		//grabs file- should be pacMove2.png, pacTest marks the edges and middle pixel in red
		Texture spriteSheet = new Texture(Gdx.files.internal("pacMove2.png"));		
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
				spriteSheet.getWidth() / FRAME_COLS, spriteSheet.getHeight()
									/ FRAME_ROWS);	
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				pacmanFrames[index++] = tmp[i][j];
			}
		}
		//for ghosts
		String colour;		
		for (int i=0; i<4; i++) {
			switch(i) {
			case 1: colour = "pink"; break;
			case 2: colour = "teal"; break;
			case 3: colour = "orange"; break;
			default: colour = "red"; break;
			}
			spriteSheet = new Texture(Gdx.files.internal(colour + "ghostmove.png"));
			// splits into columns and rows then puts them into one array in order
			tmp = TextureRegion.split(spriteSheet,
			spriteSheet.getWidth() / GHOST_LENGTH, spriteSheet.getHeight());
			index = 0;
			for (int j = 0; j < GHOST_LENGTH; j++) {
				ghostFrames[i][index++] = tmp[0][j];
			}			
		}
		PacView.gameMap = gameMap;
	}

	/**
	 * Render method for pacman- called by main Pacman class and calls all
	 * private methods for drawing
	 */
	public static void render(GameMap gameMap, PacChar player, Ghost blinky) {
		//FIXME big method
		//make changes to location of object etc, then draw
		//makeChanges();
		
		//Black background
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    // updating camera is something we should do once per frame
	    camera.update();
	    //tell the spritebatch to use the coordinate system of the camera
	    batch.setProjectionMatrix(camera.combined);	    
	    // start the drawing
	    batch.begin();
	    drawGameMap();

	    
	    //testing bitmap text print
//	    scoreText.setColor(Color.WHITE);
//	    scoreText.draw(batch, "Pacman!", 10, 10);
	    
	    // TODO move this check elsewhere check if pacman is trying to move into a wall
	    // this stops him even if no key is pressed
//	    if (!player.checkNoWallCollision(player.getTile())) {
//			player.setCurrentState(PacState.IDLE);
//		}
	    player.render(batch);
	    blinky.render(batch);
	    //end the drawing
	    batch.end();
	}
	
	private static void drawGameMap() {
		//should do some fancy stuff to position pacman grid in middle of screen horizontally
		//instead of what I've done here with just setting them to a value I picked		
		Tile[][] grid = gameMap.getGrid();
		int hOffset = gameMap.getHOffset();
		int vOffset = gameMap.getVOffset();
		int side = gameMap.getTileSideLength();
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				grid[x][y].render(batch, hOffset + x*side, vOffset + y*side);
			}
		}
	}
	
}
