package deco2800.arcade.pacman;

import org.lwjgl.util.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import deco2800.arcade.pacman.Ghost.GhostState;
import deco2800.arcade.pacman.PacChar.PacState;


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
	private BitmapFont gameOverText; 
	private BitmapFont gameOverText2; 
	
	// sprite sheet, divided into array of arrays of 8x8 tile images
	private final TextureRegion[][] tileSprites;
	// Static variables for pulling sprites from pacman sheet
	private static final int MOVER_SPRITE_NUM = 8;
	private static final int PACMAN_SIDE_PIX = 13;
	private static final int GHOST_SIDE_PIX = 14;
	private TextureRegion[] pacmanFrames = new TextureRegion[MOVER_SPRITE_NUM];
	private TextureRegion currentFrame; // for pacman animation
	private static final int NUM_GHOSTS = 5;
	private TextureRegion[][] ghostFrames = new TextureRegion[NUM_GHOSTS][MOVER_SPRITE_NUM];
	private static int SCREEN_WIDTH;
	private static int SCREEN_HEIGHT;
	
	private GameMap gameMap;
	private PacChar player;
	private Ghost blinky;
	private Ghost pinky;
	
	
	/** TODO make the gameMap the model and have them added on to that in 
	 * the main Pacman class, then just pass the gameMap here like it currently is.
	 */
	public PacView(PacModel model) {
		SCREEN_WIDTH = model.getSCREENWIDTH();
		SCREEN_HEIGHT = model.getSCREENHEIGHT();
		//Initialize camera
		camera = new OrthographicCamera();
		// set resolution
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		// initialise spriteBatch for drawing things
		batch = new SpriteBatch();		
		//get tile sprites
			tileSprites = TextureRegion.split(
				new Texture(Gdx.files.internal("wallsAndPellets.png")), 8, 8);		
		//grabs file- should be pacMove2.png, pacTest marks the edges and middle pixel in red
		Texture spriteSheet = new Texture(Gdx.files.internal("pacMove2.png"));		
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
				PACMAN_SIDE_PIX, PACMAN_SIDE_PIX);	
		int index = 0;
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				pacmanFrames[index++] = tmp[i][j];
			}
		}
		//for ghosts
		String colour;
		for (int i=0; i<5; i++) {
			switch(i) {
			case 1: colour = "pink"; break;
			case 2: colour = "teal"; break;
			case 3: colour = "orange"; break;
			case 4: colour = "scared"; break;
			default: colour = "red"; break;
			}
			spriteSheet = new Texture(Gdx.files.internal(colour + "ghostmove_tran.png"));
			// splits into columns and rows then puts them into one array in order
			tmp = TextureRegion.split(spriteSheet, GHOST_SIDE_PIX, GHOST_SIDE_PIX);
			for (int j = 0; j < MOVER_SPRITE_NUM; j++) {
				ghostFrames[i][j] = tmp[0][j];
			}			
		}
		scoreText = new BitmapFont(Gdx.files.internal("pacfont2.fnt"),
		         Gdx.files.internal("pacfont2.png"), false);
		gameOverText = new BitmapFont(Gdx.files.internal("pacfont.fnt"),
		         Gdx.files.internal("pacfont1.png"), false);
		gameOverText2 = new BitmapFont(Gdx.files.internal("pacfont.fnt"),
				Gdx.files.internal("pacfont1.png"), false);
		this.gameMap = model.getGameMap();
		this.player = model.getPlayer();
		this.blinky = model.getBlinky();
		this.pinky = model.getPinky();
	}
	
	/**
	 * Render method for pacman- called by main Pacman class and calls all
	 * private methods for drawing
	 */
	public void render() {
		//Black background
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    // updating camera is something we should do once per frame
	    camera.update();
	    //tell the spritebatch to use the coordinate system of the camera
	    batch.setProjectionMatrix(camera.combined);	    
	    // start the drawing
	    batch.begin();
	    displayScore(player);
	    drawGameMap();
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
		if (blinky.getCurrentState() == GhostState.DEAD ||
				blinky.getCurrentState() == GhostState.SCATTER){
			batch.draw(ghostFrames[4][blinky.getSpritePos()], blinky.getDrawX(), 
					blinky.getDrawY(), blinky.getWidth(), blinky.getHeight());
		} else {
		batch.draw(ghostFrames[0][blinky.getSpritePos()], blinky.getDrawX(), 
				blinky.getDrawY(), blinky.getWidth(), blinky.getHeight());
		}
		
		if (pinky.getCurrentState() == GhostState.DEAD ||
				pinky.getCurrentState() == GhostState.SCATTER){
			batch.draw(ghostFrames[4][pinky.getSpritePos()], pinky.getDrawX(), 
					pinky.getDrawY(), pinky.getWidth(), pinky.getHeight());
		} else {
			batch.draw(ghostFrames[1][pinky.getSpritePos()], pinky.getDrawX(), 
					pinky.getDrawY(), pinky.getWidth(), pinky.getHeight());
		}
	}
	
	private void drawPacman() {
		//draw pacman facing the appropriate direction
		if (player.getCurrentState() == PacState.DEAD){
			
		} else {
		
		batch.draw(pacmanFrames[player.getSpritePos()], player.getDrawX(), 
				player.getDrawY(), player.getWidth(), player.getHeight());
		}
	}
	
	/**
	 * Displays the score of the current Mover.
	 * When support for multiple player-controlled movers is implemented, printing
	 * will have to occur in different positions.
	 */
	public void displayScore(Mover mover) {
		// Set score text
		CharSequence str = "Score: " + mover.getScore();
		CharSequence str2 = "";	
		CharSequence str3 = "";	
		
		scoreText.setColor(Color.WHITE);
		scoreText.draw(batch, str, 50, 50);
		if (gameMap.isGameOver()){
			str2 = "Game";
			str3 = "over";
		}
		gameOverText.setColor(Color.WHITE);
		gameOverText.draw(batch, str2, 170, 170);
		gameOverText2.setColor(Color.WHITE);
		gameOverText2.draw(batch, str3, 180, 130);
	}
	
}
