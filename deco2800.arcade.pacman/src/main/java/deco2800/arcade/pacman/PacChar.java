
package deco2800.arcade.pacman;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;

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
		spritePos = 3;
		if (drawFacing == Dir.RIGHT) {
			spritePos = 1;
		} else if (drawFacing == Dir.UP) {
			spritePos = 5;
		} else if (drawFacing == Dir.DOWN){ 
			spritePos = 7;
		} else {
			drawFacing = Dir.LEFT;
		}
		
	
		// If pacman is able to turn, update drawFacing
		if (canTurn()) {
			drawFacing = facing;
			this.setCurrentState(PacState.MOVING);
		}
		
		if (!this.checkNoWallCollision()){
			this.setCurrentState(PacState.IDLE);
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
	
	
	private void checkGhostCollision(Tile pTile) {	
		List<Mover> colList = pTile.getMovers();
		if (colList.size() > 1) {
			for (int i=0; i < colList.size(); i++) {
				if (colList.get(i).getClass() == Ghost.class) {
					if (((Ghost)colList.get(i)).getCurrentState() == GhostState.SCATTER ||
							((Ghost)colList.get(i)).getCurrentState() == GhostState.FRIGHT){
						// Ghost is scared! Time to feast :>
						((Ghost)colList.get(i)).setCurrentState(GhostState.DEAD);
						gameMap.setGhostsEaten(gameMap.getGhostsEaten() + 1);
						System.out.println("Yummy ghost");
					} else if(((Ghost)colList.get(i)).getCurrentState() == GhostState.CHASE){
						System.out.println("Disaster!! Pacman hit a ghost!");
						if (getLives() <= 1){
							this.setCurrentState(PacState.DEAD);
							// Do gameover stuff
						} else {
							setLives(getLives() - 1);
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

