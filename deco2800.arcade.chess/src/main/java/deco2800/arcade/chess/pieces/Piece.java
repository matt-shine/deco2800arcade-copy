package deco2800.arcade.chess.pieces;

import deco2800.arcade.chess.*;

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
	public abstract List<int[]> possibleMoves(int[] currentPos, FixedSizeList<FixedSizeList<Piece>> board_state);
	
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
	
	/**
	 * Checks whether a space is currently occupied
	 * 
	 * @param position
	 *            The position to be checked for occupation
	 * @return True if the space is occupied, false if available
	 */
	public boolean occupiedSpace(FixedSizeList<FixedSizeList<Piece>> board_state, int[] position) {
		int x = position[0];
		int y = position[1];
		if ((x < 0) || (x > 7) || (y < 0) || (y > 7)) {
			return false;
		}
		FixedSizeList<Piece> row = board_state.get(x);
		Piece onSpace = row.get(y);

		Piece nullPiece = new Null(true);;
		if (onSpace.getClass() == nullPiece .getClass()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Checks whether position argument is a valid position on the board
	 * 
	 * @param position
	 * 			The position to be checked if it is valid
	 * @return True if it is a valid position, false otherwise
	 */
	public boolean inBounds(int[] position) {
		int x = position[0];
		int y = position[1];
		//check if x position is valid
		if ((x < 0) || (x > 7)) {
			return false;
		}
		//check if y position is valid
		if ((y < 0) || (y > 7)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns a list of all possible moves in one of eight directions
	 * specified by dirCase without the piece jumping any others
	 * 
	 * @param position
	 * 			the current position of the piece
	 * @param board_state
	 * 			the current state of the board
	 * @param dirCase
	 * 			integer representing direction to move in
	 * @return List<int[]> that has all allowable moves in specified direction
	 */
	public List<int[]> removeJumps(int[] position, 
			FixedSizeList<FixedSizeList<Piece>> board_state, int dirCase) {
		List<int[]> noJumps = new ArrayList<int[]>();
		for (int i = 1; i < 8; i++) {
			//get move in direction
			int[] move = getPos(position, dirCase, i);
			//check it is a position on the board
			if (inBounds(move)) {
				//if the space is not occupied add the move
				if (!occupiedSpace(board_state, move)) {
					noJumps.add(move);
				} else {
					Piece onSquare = board_state.get(move[0]).get(move[1]);
					//if it is occupied by enemy, add move then break
					if (getTeam() != onSquare.getTeam()) {
						noJumps.add(move);
						break;
					} else {
						//if occupied by ally piece, do not add move and break
						break;
					}
				}
			}
		}
		return noJumps;
	}
	
	/**
	 * Returns the move in direction encoded by dirCase for index amount of
	 * squares
	 * 
	 * @param position
	 * 			the current position of the piece
	 * @param dirCase
	 * 			the encoding for the direction to travel in
	 * @param index
	 * 			the amount of steps to take in that direction
	 * @return An int[] giving the position after moving in the specified
	 * 			direction for however many steps
	 */
	public int[] getPos(int[] position, int dirCase, int steps) {
		int x = position[0];
		int y = position[1];
		int[] move = {0,0};
		switch (dirCase) {
			case 1:
				move[0] += (x + steps);
				move[1] += (y + steps);
				break;
			case 2:
				move[0] += (x + steps);
				move[1] += (y - steps);
				break;
			case 3:
				move[0] += (x - steps);
				move[1] += (y + steps);
				break;
			case 4:
				move[0] += (x - steps);
				move[1] += (y - steps);
				break;
			case 5:
				move[0] += (x + steps);
				move[1] += y;
				break;
			case 6:
				move[0] += (x - steps);
				move[1] += y;
				break;
			case 7:
				move[0] += x;
				move[1] += (y + steps);
				break;
			case 8:
				move[0] += x;
				move[1] += (y - steps);
				break;
		}
		return move;
	}
	
}
