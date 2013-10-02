package deco2800.arcade.pacman;

import deco2800.arcade.model.Player;

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

private enum GhostMode {
	CHASE, SCATTER, FRIGHT, DEAD
}
private int ghostNum;
// 1 - Blinky, 2 - Pinky, 3 - Inky, 4 - Clyde
private int xpos;
private int ypos;
private int targetx;
private int targety;
private PacChar player;
private boolean allowedOut; //says whether the ghost can go through the ghost pen door

public Ghost(int ghostNum, int xpos, int ypos, PacChar player) {
	this.ghostNum = ghostNum;
	this.xpos = xpos;
	this.ypos = ypos;
	this.player = player;
}



public void targetTile() {
	// needs to check the GhostMode first. If in chase do first block
	// if in scatter, targets change to corners
	// if in fright, targets change randomly
	// if dead, target is monster pen
	
	if (ghostNum == 1) { // Blinky
		// add if statement for dots remaining in maze, for Elroy mode
		targetx = player.getX();
		targety = player.getY();
	}
	else if (ghostNum == 2) { // Pinky
		// if statements for direction
	}
	else if (ghostNum == 3) { // Inky
		// complex
	}
	else if (ghostNum == 4) { // Clyde
		// random
	}
	
}

public void intersectionDecision() {
	// This method uses targetTile to decide which way it will turn at the next interseciton
	
}

public void changeMode() {
	// This method is for changing between ghost modes
}


}
