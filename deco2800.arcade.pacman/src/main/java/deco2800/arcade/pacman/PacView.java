package deco2800.arcade.pacman;

import org.lwjgl.util.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * A view for the Pacman game- separates out all LibGDX drawing functions 
 * because these don't work correctly with JUnit tests
 * @author Nicholas
 *
 */
public class PacView {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont scoreText; 
	public final int SCREENHEIGHT = 720;
	public final int SCREENWIDTH = 1280;	
	
	
	// sprite sheet, divided into array of arrays of 8x8 tile images
	private final TextureRegion[][] tileSprites = TextureRegion.split(
			new Texture(Gdx.files.internal("wallsAndPellets.png")), 8, 8);
	// Static variables for pulling sprites from pacman sheet
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 4;
	private TextureRegion[] pacmanFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	private TextureRegion currentFrame; // for pacman animation
	private static final int GHOST_LENGTH = 8;
	private static final int GHOST_NUM = 4;
	private TextureRegion[][] ghostFrames = new TextureRegion[GHOST_NUM][GHOST_LENGTH];
	
	private GameMap gameMap;
	private PacChar player;
	private Ghost blinky;
	
	/** TODO make the gameMap the model and have them added on to that in 
	 * the main Pacman class, then just pass the gameMap here like it currently is.
	 */
	public PacView(GameMap gameMap, PacChar player, Ghost blinky) {
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
		this.gameMap = gameMap;
		this.player = player;
		this.blinky = blinky;
		
	}
	public void setUp(GameMap gameMap) {
		
	}

	/**
	 * Render method for pacman- called by main Pacman class and calls all
	 * private methods for drawing
	 */
	public void render(GameMap gameMap, PacChar player, Ghost blinky) {
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
	    drawPacman();
	    drawGhost();
	    //end the drawing
	    batch.end();
	}
	
	private void drawGameMap() {
		//should do some fancy stuff to position pacman grid in middle of screen horizontally
		//instead of what I've done here with just setting them to a value I picked		
		Tile[][] grid = gameMap.getGrid();
		int hOffset = gameMap.getHOffset();
		int vOffset = gameMap.getVOffset();
		int side = gameMap.getTileSideLength();
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				Class<? extends Tile> tileType = grid[x][y].getClass();
				int spriteRow = 7;
				int spriteCol = 1;
				if (tileType ==  DotTile.class) {
					DotTile dotTile = (DotTile) grid[x][y];
					if (!dotTile.isEaten()) {
						spriteRow = dotTile.isEnergiser() ? 1:4;
					}
				} else if (tileType == WallTile.class) {
					Point rowCol = drawWallTile((WallTile) grid[x][y]);
					spriteRow = rowCol.getX();
					spriteCol = rowCol.getY();
				} 	
				batch.draw(tileSprites[spriteRow][spriteCol], 
						hOffset + x*side, vOffset + y*side, side, side);					
			}
		}
	}
	
	private Point drawWallTile(WallTile wall) {
		int row = 7;
		int col = 1;
		switch(wall.getType()) {
		case 'A': row = 0; col = 0; break;
		case 'B': row = 0; break;
		case 'C': row = 0; col = 2; break;
		case 'J': row = 0; col = 3; break;
		case 'D': row = 1; col = 0; break;
		case 'E': row = 1; col = 2; break;
		case 'K': row = 1; col = 3; break;
		case 'F': row = 2; col = 0; break;
		case 'G': row = 2; break;
		case 'H': row = 2; col = 2; break;
		case 'L': row = 2; col = 3; break;
		case 'a': row = 3; col = 0; break;
		case 'b': row = 3; break;
		case 'c': row = 3; col = 2; break;
		case 'M': row = 3; col = 3; break;
		case 'd': row = 4; col = 0; break;
		case 'e': row = 4; col = 2; break;
		case 'R': row = 4; col = 3; break;
		case 'f': row = 5; col = 0; break;
		case 'g': row = 5; break;
		case 'h': row = 5; col = 2; break;
		case 'S': row = 5; col = 3; break;
		case '1': row = 6; col = 0; break;
		case '2': row = 6; break;
		case '3': row = 6; col = 2; break;
		case 'w': row = 6; col = 3; break;
		case '4': col = 0; break;
		case '5': col = 2; break;
		case 'x': col = 3; break;
		case '6': row = 8; col = 0; break;
		case '7': row = 8; break;
		case '8': row = 8; col = 2; break;
		case 'y': row = 8; col = 3; break;
		case '9': row = 9; col = 0; break;
		case 'Q': row = 9; break;
		case '0': row = 9; col = 2; break;
		case 'z': row = 9; col = 3; break;
		case 'W': row = 10; col = 0; break;
		case 'X': row = 10; break;
		case 'Y': row = 10; col = 2; break;
		case 'Z': row = 10; col = 3; break;
		}
		return new Point(row, col);
	}
	
	/**
	 * Called everytime the main render method happens.
	 * Draws the Ghost
	 */
	private void drawGhost() {
		//draw ghost facing the appropriate direction
		// TODO NOTE CURRENTLY ONLY DRAWS RED GHOST WHICHEVER GHOST IT IS
		batch.draw(ghostFrames[0][blinky.getSpritePos()], blinky.getDrawX(), 
				blinky.getDrawY(), blinky.getWidth(), blinky.getHeight());
	}
	
	private void drawPacman() {
		//draw pacman facing the appropriate direction
		batch.draw(pacmanFrames[player.getSpritePos()], player.getDrawX(), 
				player.getDrawY(), player.getWidth(), player.getHeight());
	}
	
}
