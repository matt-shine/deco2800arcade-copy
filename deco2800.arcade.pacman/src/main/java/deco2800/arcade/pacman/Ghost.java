package deco2800.arcade.pacman;

import java.util.List;

import deco2800.arcade.model.Player;
import deco2800.arcade.pacman.GhostChar.GhostState;
import deco2800.arcade.pacman.PacChar.PacState;

public class Ghost {
	

	/* path finding logic
	 	Four modes, chase, scatter, frightened and dead
	 	5 character modes: 
	 		blinky - highly aggressive
	 		Pinky - Aggressive but tries to predict your moves
	 		Inky - seems randome
	 		Clyde - kind of useless
	 		Elroy - Blinky's alter ego
	 		
	 	
	 	Charcter Targeting:
	 		blinky - Target tile is pacman's tile, unless in scatter mode, top left corner
	 		Pinky - Target tile is 4 tiles in the direction Pacman is moving
	 		Inky - The most complexed of the ghosts, will need to read his more thoroughly
	 		Clyde - Proximity based
	*/ 
	
	private GhostChar ghost;
	private List<Collideable> colList;
	
	public Ghost(GhostChar ghost, List<Collideable> colList) {
		this.ghost = ghost;
		this.colList = colList;
	}
	
	private void checkCollisions() {
		//check collisions here, using x,y,width, height against the two walls
		// variables are left, right, top, bottom
		int pl = ghost.getX();
		int pb = ghost.getY();
		int pt = ghost.getHeight() + pb;
		int pr = ghost.getWidth() + pl;
	    for (int i=1; i < colList.size(); i++) {
	    	Collideable c = colList.get(i);
	    	int cl = c.getX();
	    	int cb = c.getY();
	    	int cr = cl + c.getWidth();
	    	int ct = cb + c.getHeight();
	    	if ((pl < cl && pr >= cl) || (pr > cr && pl <= cr) || (pb < cb && pt >= cb) || (pt > ct && pb <= ct)) {
	    		//set pacman's state to idle so he stops moving
	    		ghost.setCurrentState(GhostState.IDLE);
	    		System.out.println("COLLISION DETECTED!");
	    		return;
	    	}
	    }
	}

	public void targetTile() {
		
	}

}
