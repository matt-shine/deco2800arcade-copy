package deco2800.arcade.pacman;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.lwjgl.util.Point;
import static java.lang.Math.*; 

import deco2800.arcade.model.Player;
import deco2800.arcade.pacman.PacChar.PacState;


public class Ghost extends Mover {
	
	private enum GhostState {
	CHASE, SCATTER, FRIGHT, DEAD
	}
	
	public enum GhostName {
		BLINKY, PINKY, INKY, CLYDE
	}
	private GhostName ghost;
	private GhostState currentState;
	// Static variables for pulling sprites from sprite sheet
	private static final int FRAME_COLS = 8;
	private static final int FRAME_ROWS = 1;

	
	// the distance ghost moves each frame
	private PacChar player;
	private float moveDist;
	private Tile targetTile;
	private Animation walkAnimation;
	private Texture walkSheet;
	private TextureRegion[] walkFrames;
	private TextureRegion currentFrame;
	
	public Ghost(GameMap gameMap, GhostName ghost, PacChar player) {
		super(gameMap);
		this.player = player;
		this.ghost = ghost;
		currentTile = gameMap.getFruitLeft(); // CHANGE TO appropriate ghost start
		//set up pacman to be drawn in the right place- this is defintely right
		drawX = gameMap.getTileCoords(currentTile).getX() + 4;
		drawY = gameMap.getTileCoords(currentTile).getY() - 4;
		
		String file = "";
		switch (ghost) {
		case BLINKY : file = "redghostmove.png"; break;
		case PINKY : file = "pinkghostmove.png"; break;
		case INKY : file = "tealghostmove.png"; break;
		case CLYDE : file = "orangeghostmove.png"; break;
		}
		
		walkSheet = new Texture(Gdx.files.internal(file));
		// splits into columns and rows then puts them into one array in order
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
		walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
							/ FRAME_ROWS);
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		// initialise some variables
		currentState = GhostState.CHASE;
		facing = Dir.LEFT;
		width = walkFrames[1].getRegionWidth() * 2;
		height = walkFrames[1].getRegionHeight() * 2;
		updatePosition();
		moveDist = 0; //made it so he can't move at the moment, because he
						// currently goes through walls and crashes it.
		currentTile.addMover(this);
		//System.out.println(this);
//		animation not necessary unless Pacman moving		
//		walkAnimation = new Animation(0.025f, walkFrames);
//		stateTime = 0f;	
	}
	
	/**
	 * Called everytime the main render method happens.
	 * Draws the Ghost
	 */
	public void render(SpriteBatch batch) {
		
		int spritePos = 3;
		if (facing == Dir.RIGHT) {
			spritePos = 1;
		} else if (facing == Dir.UP) {
			spritePos = 5;
		} else if (facing == Dir.DOWN){ 
			spritePos = 7;
		} else {
			facing = Dir.LEFT;
		}
		// checks if pacman is moving, and if so keeps him moving in that direction
		if (currentState == GhostState.CHASE) {
			if (facing == Dir.LEFT){
    			drawX -= moveDist;
    		} else if (facing == Dir.RIGHT) {
    			drawX += moveDist;
    		} else if (facing == Dir.UP) {
    			drawY += moveDist;
    		} else if (facing == Dir.DOWN){ 
    			drawY -= moveDist;
    		} else {
    			currentState = GhostState.SCATTER;
    			facing = Dir.LEFT;
    		}			
			updatePosition();			
    	} 
		//draw pacman facing the appropriate direction
		batch.draw(walkFrames[spritePos], drawX, drawY, width, height);
	}
	 
	public Dir getFacing() {
			return facing;
		}
	
	public void setFacing(Dir facing) {
		this.facing = facing;
	}
	
	public GhostState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(GhostState currentState) {
		this.currentState = currentState;
	}

	public float getMoveDist() {
		return moveDist;
	}

	public void setMoveDist(float moveDist) {
		this.moveDist = moveDist;
	}
	
	public String toString() {
		return "Ghost at (" + midX + ", " + midY + ") drawn at {" + drawX + 
				", " + drawY + "}, " + currentState + " in " + currentTile;
	}
		
	
	public Tile getTargetTile() {
		if (ghost == GhostName.BLINKY) {
			return player.getTile();
		}
		else if (ghost == GhostName.PINKY) {
			return player.nextTile(player.getTile(), 4);
		}
		else {
			return player.getTile();
		}
	}
	
	public double calcDist(Tile start, Tile target) {
		Point startPoint = gameMap.getTilePos(start);
		Point targetPoint = gameMap.getTilePos(target);
		int startx = startPoint.getX();
		int starty = startPoint.getY();
		int targetx = targetPoint.getX();
		int targety = targetPoint.getY();
		double dist;
		int distx = startx - targetx;
		int disty = starty - targety;
		dist = sqrt((distx*distx + disty*disty));
		return dist;
	}

	public void setTargetTile(Tile targetTile) {
		this.targetTile = targetTile;
	}
	

}
