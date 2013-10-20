
package deco2800.arcade.pacman;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import deco2800.arcade.pacman.Ghost.GhostState;

public final class PacChar extends Mover{
	
	// Describes the current state of pacman- starts IDLE
	public enum PacState {
		IDLE, MOVING, DEAD
	}
			
	private PacState currentState;
	private static int widthVal = 26;
	private static int heightVal = 26;

	private Animation walkAnimation;
	
	public PacChar(GameMap gameMap) {
		super(gameMap);
		currentTile = gameMap.getPacStart();
		//set up pacman to be drawn in the right place- this is defintely right
		drawX = gameMap.getTileCoords(currentTile).getX() + 4;
		drawY = gameMap.getTileCoords(currentTile).getY() - 4;
		// initialise some variables
		currentState = PacState.IDLE;
		facing = Dir.LEFT; // the way he'll turn at the next intersection if possible
		drawFacing = Dir.LEFT; // the way pacman appears
		width = widthVal;
		height = heightVal;
		spritePos = 3;
		currentTile.addMover(this);
		updatePosition();
		moveDist = 2;
		//System.out.println(this);
//		animation not necessary unless Pacman moving		
//		walkAnimation = new Animation(0.025f, pacmanFrames);
//		stateTime = 0f;	
	}
	
	/**
	 * Prepare to draw pacman
	 */
	public void prepareDraw() {
		
		// Update pacman's facing dir
		if (currentState == PacState.DEAD) {
			spritePos = 0;
		} else {
			spritePos = 3;
			if (drawFacing == Dir.RIGHT) {
				spritePos = 1;
			} else if (drawFacing == Dir.UP) {
				spritePos = 5;
			} else if (drawFacing == Dir.DOWN) {
				spritePos = 7;
			} else {
				drawFacing = Dir.LEFT;
			}
		}
		if (!(currentState == PacState.DEAD)){
			// If pacman is able to turn, update drawFacing
			if (canTurn()) {
				drawFacing = facing;
				this.setCurrentState(PacState.MOVING);
			}
			
			if (!this.checkNoWallCollision()){
				this.setCurrentState(PacState.IDLE);
			}
		}
		
		// checks if pacman is moving, and if so keeps him moving in that direction
		if (currentState == PacState.MOVING) {
			int corr = 2; // a correction factor to ensure pacman is properly centred.
			if (drawFacing == Dir.LEFT){
    			drawX -= moveDist;
    			drawY = gameMap.getTileCoords(currentTile).getY() - corr;
    		} else if (drawFacing == Dir.RIGHT) {
    			drawX += moveDist;
    			drawY = gameMap.getTileCoords(currentTile).getY() - corr;
    		} else if (drawFacing == Dir.UP) {
    			drawY += moveDist;
    			drawX = gameMap.getTileCoords(currentTile).getX() - corr;
    		} else if (drawFacing == Dir.DOWN){ 
    			drawY -= moveDist;
    			drawX = gameMap.getTileCoords(currentTile).getX() - corr;
    		} else {
    			currentState = PacState.IDLE;
    			facing = Dir.LEFT;
    		}
//			checkTile(this.nextTile(currentTile, 1));
    	} 
		updatePosition();
		checkGhostCollision(currentTile);
	}
	
	/**
	 * Checks whether pac man can turn
	 */
	public boolean canTurn(){
		int x = gameMap.getTilePos(currentTile).getX();
		int y = gameMap.getTilePos(currentTile).getY();
		Tile[][] grid = gameMap.getGrid();
		switch(facing) {
		case LEFT: x -= 1; break;
		case RIGHT: x += 1; break;
		case UP: y += 1; break;
		case DOWN: y -= 1; break;
		}
		return grid[x][y].getClass() != WallTile.class;
	}
	
	private void checkGhostCollision(Tile pTile) {
		List<Mover> colList = pTile.getMovers();
		if (colList.size() > 1) {
			for (int i = 0; i < colList.size(); i++) {
				if (colList.get(i).getClass() == Ghost.class) {
					if (((Ghost) colList.get(i)).getCurrentState() == GhostState.SCATTER
							|| ((Ghost) colList.get(i)).getCurrentState() == GhostState.FRIGHT) {
						// Ghost is scared! Time to feast :>
						((Ghost) colList.get(i))
								.setCurrentState(GhostState.DEAD);
						gameMap.setGhostsEaten(gameMap.getGhostsEaten() + 1);
						this.setScore(this.getScore() + getGhostScore());
						setGhostScore(getGhostScore() * 2);
					} else if (((Ghost) colList.get(i)).getCurrentState() == GhostState.CHASE
							&& (currentState == PacState.MOVING ||
									currentState == PacState.IDLE)) {
						// Pacman has been hit!
						currentState = PacState.DEAD;
						Timer.schedule(new Task() { // Game's not over! Revive in 3
							public void run() {
								currentState = PacState.MOVING;
							}
						}, 3);
						System.out.println(currentState);
						System.out.println(" >>> life lsot!");
						setLives(getLives() - 1);
						System.out.println(" >>> lives: + " + getLives());
						// Is it game over?
						if (getLives() <= 0) {
							this.setCurrentState(PacState.DEAD);
							// gamePaused = true;
							gameMap.setGameOver(true);
							// Do gameover stuff
						}
					}
				}
			}
		}
	}
	
	public void setFacing(Dir facing) {
		this.facing = facing;
	}
	
	public void setdrawFacing(Dir facing) {
		this.drawFacing = facing;
	}
	
	public PacState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(PacState currentState) {
		this.currentState = currentState;
	}
	
	public String toString() {
		return "Pacman at (" + midX + ", " + midY + ") drawn at {" + drawX + 
				", " + drawY + "}, " + currentState + " in " + currentTile;
	}
		
}

