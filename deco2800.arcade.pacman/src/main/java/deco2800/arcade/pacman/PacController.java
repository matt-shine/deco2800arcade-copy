package deco2800.arcade.pacman;

import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.pacman.PacChar.Dir;
import deco2800.arcade.pacman.PacChar.PacState;

/**
 * Takes input from the keyboard to control Pacman. Checked every time before 
 * rendering happens. Has a bunch of methods cause it implements InputProcessor
 * @author Nicholas
 *
 */
public class PacController implements InputProcessor {

	private PacChar player;
	private GameMap gameMap;
	
	public PacController(PacChar player, GameMap gameMap) {
		this.player = player;
		this.gameMap = gameMap;
	}
	
		
	@Override
	public boolean keyDown(int key) {
		Dir facing;
		//move based on key pressed
		if (key == Keys.RIGHT) {
			facing = Dir.RIGHT;
		} else if (key == Keys.LEFT){
			facing = Dir.LEFT;
		} else if (key == Keys.UP) {
			facing = Dir.UP;
		} else if (key == Keys.DOWN){ 
			facing = Dir.DOWN;	
		} else if (key == Keys.ENTER) {
			facing = Dir.TEST;			
		} else {
			// if not one of the arrow keys
			return false;
		}
		// check for collisions
		Tile pTile = player.getTile();
		if (checkNoWallCollision(pTile)) {
			System.out.println("Now moving");
			player.setCurrentState(PacState.MOVING);	
		} else {
			System.out.println("now idle");
			player.setCurrentState(PacState.IDLE);
		}
		checkGhostCollision(pTile);
		player.setFacing(facing);			
		return true;
	}

	private void checkGhostCollision(Tile pTile) {	
		List<Mover> colList = pTile.getMovers();
		if (colList.size() > 1) {
			for (int i=0; i < colList.size(); i++) {
				if (colList.get(i) instanceof Ghost) {
					//TODO some death thing
					player.setCurrentState(PacState.DEAD);
				}
			}
		}
		
	}
	
	public boolean checkNoWallCollision(Tile pTile) {
		boolean canMove = true;
		int x = gameMap.getTilePos(pTile).getX();
		int y = gameMap.getTilePos(pTile).getY();
		Tile[][] grid = gameMap.getGrid();
		//System.out.println(x + ", " + y);
		switch(player.getFacing()) {
		case LEFT: x -= 1; break;
		case RIGHT: x += 1; break;
		case UP: y += 1; break;
		case DOWN: y -= 1; break;
		case TEST: break;
		}
		//System.out.println(grid[x][y]);
		//System.out.println(grid[x][y].getClass());
		if (!(grid[x][y].getClass() == WallTile.class)) {
			canMove = false;
		}
		//System.out.println(canMove);
		return canMove;
	}
	
	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/** Shouldn't do anything significant */
	@Override
	public boolean keyUp(int arg0) {
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
