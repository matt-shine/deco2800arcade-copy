package deco2800.arcade.pacman;

import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.pacman.Mover.Dir;
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
	
	public PacController(PacModel model) {
		this.player = model.getPlayer();
		this.gameMap = model.getGameMap();
	}
	
		
	@Override
	public boolean keyDown(int key) {
		Dir facing;
		//Change facing based on key press if that direction is valid
		if (key == Keys.RIGHT) {
			facing = Dir.RIGHT;
		} else if (key == Keys.LEFT){
			facing = Dir.LEFT;
		} else if (key == Keys.UP) {
			facing = Dir.UP;
		} else if (key == Keys.DOWN){ 
			facing = Dir.DOWN;	
		} else {
			// if not one of the arrow keys
			return false;
		}
		// check for collisions
		Tile pTile = player.getTile();
//		Dir tempFacing = player.getFacing();
		player.setFacing(facing);
		System.out.println("Can pacman move? Next tile is " + 
							player.nextTile(pTile, 1));
		// Check for teleport tile
		
//		if (player.nextTile(pTile, 1).getClass() == TeleportTile.class){
//			
//		}
//		
//		if (player.nextTile(pTile, 1).getClass() != WallTile.class) {
//			player.setCurrentState(PacState.MOVING);
//		} else {
//			player.setCurrentState(PacState.IDLE);
//			//stops pacman changing facing if he can't move in that direction			
//			player.setFacing(tempFacing);
//		}
		//checkGhostCollision(pTile);			
		return true;
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
