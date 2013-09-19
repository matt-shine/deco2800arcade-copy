package deco2800.arcade.pacman;

import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.pacman.PacChar.PacState;

/**
 * Takes input from the keyboard to control Pacman. Checked every time before 
 * rendering happens. Has a bunch of methods cause it implements InputProcessor
 * @author Nicholas
 *
 */
public class PacController implements InputProcessor {

	private PacChar player;
	private List<Collideable> colList;
	
	public PacController(PacChar player, List<Collideable> colList) {
		this.player = player;
		this.colList = colList;
	}
	
	private void checkCollisions() {
		//check collisions here, using x,y,width, height against the two walls
		// variables are left, right, top, bottom
		int pl = player.getX();
		int pb = player.getY();
		int pt = player.getHeight() + pb;
		int pr = player.getWidth() + pl;
	    for (int i=1; i < colList.size(); i++) {
	    	Collideable c = colList.get(i);
	    	int cl = c.getX();
	    	int cb = c.getY();
	    	int cr = cl + c.getWidth();
	    	int ct = cb + c.getHeight();
	    	if ((pl < cl && pr >= cl) || (pr > cr && pl <= cr) || (pb < cb && pt >= cb) || (pt > ct && pb <= ct)) {
	    		//set pacman's state to idle so he stops moving
	    		player.setCurrentState(PacState.IDLE);
	    		System.out.println("COLLISION DETECTED!");
	    		return;
	    	}
	    }
	}
	
	@Override
	public boolean keyDown(int key) {
		int facing;
		//move based on key pressed
		if (key == Keys.RIGHT) {
			facing = 1;
		} else if (key == Keys.LEFT){
			facing = 2;
		} else if (key == Keys.UP) {
			facing = 3;
		} else if (key == Keys.DOWN){ 
			facing = 4;	
		} else if (key == Keys.ENTER) {
			facing = 5;			
		} else {
			// if not one of the arrow keys
			return false;
		}
		// check for collisions
		checkCollisions();
		
		//set facing and state
		player.setFacing(facing);
		if (player.getCurrentState().equals(PacState.IDLE)) {
			player.setCurrentState(PacState.MOVING);	
		}		
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
