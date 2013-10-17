package deco2800.arcade.chess.pieces;

import java.util.*;
public abstract class Piece {
	boolean team;
	boolean firstMove;
	boolean active;
	int preference;
	int pieceNo;
	
	public Piece(boolean team, int pieceNo) {
		this.team = team;
		this.firstMove = false;
		this.active = true;
		this.pieceNo = pieceNo;
	}
	
	/**
	 * Returns a list of all possible moves that the piece can make
	 * 
	 * @return
	 * 		List of the locations of all possible moves.
	 */
	public abstract List<int[]> possibleMoves(int[] currentPos);
	
	/**
	 * Remove the piece from gameplay and send to the graveyard
	 */
	public void deActivate() {
		this.active = false;
	}
	
	/**
	 * Add the piece back into gameplay and remove from the graveyard
	 */
	public void reActivate() {
		this.active = true;
	}
	/**
	 * Takes in a piece and returns it's team as a boolean
	 * 		- False = white team
	 * 		- True = black team 
	 * 
	 * @param piece
	 * 		The instance of the piece to check
	 * @return
	 * 		False if on the white team, true if on the black team
	 */
	public boolean getTeam() {
		return this.team;
	}
	
	/**
	 * Takes in a piece and returns a boolean based on whether it's taken it's
	 * first move
	 * 
	 * @param piece
	 * 		The instance of the piece to check
	 * @return
	 * 		True if piece has moved, false otherwise
	 */
	public boolean getFirstMove() {
		return this.firstMove;
	}
	
	/**
	 * Takes in a piece and returns a boolean based on whether the piece is 
	 * still active or not
	 * 
	 * @param piece
	 * 		The instance of the piece to check
	 * @return
	 * 		True if the piece is still in play, false otherwise
	 */
	public boolean getActiveState() {
		return this.active;
	}
	
	/**
	 * Takes in a piece and returns it's numerical preference
	 * 
	 * @param piece
	 * 		The instance of the piece to check
	 * @return
	 * 		The integer value of the pieces preference
	 */
	public int getPreference() {
		return this.preference;
	}

	/**
	 * Updates piece to say it has moved
	 */
	public void hasMoved() {
		this.firstMove = true;
	}
}
