package deco2800.arcade.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.chess.FixedSizeList;


public class Queen extends Piece {
	
	/**
	 * Initialises the piece
	 * 
	 * @param team
	 */
	public Queen(boolean team) {
		super(team, 0);
		this.preference = 5;
	}

	public List<int[]> possibleMoves(int[] currentPos, FixedSizeList<FixedSizeList<Piece>> board_state) {
		List<int[]> moves = new ArrayList<int[]>();
		//add all possible moves for directions queen can move in
		moves.addAll(removeJumps(currentPos, board_state, 1));
		moves.addAll(removeJumps(currentPos, board_state, 2));
		moves.addAll(removeJumps(currentPos, board_state, 3));
		moves.addAll(removeJumps(currentPos, board_state, 4));
		moves.addAll(removeJumps(currentPos, board_state, 5));
		moves.addAll(removeJumps(currentPos, board_state, 6));
		moves.addAll(removeJumps(currentPos, board_state, 7));
		moves.addAll(removeJumps(currentPos, board_state, 8));
		return moves;
	}

	public String toString() {
		String toString = "";
		
		if(!team) {
			toString+="white ";
		} else {
			toString+="black ";
		}
		
		toString+="Queen";
		
		return toString;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + (firstMove ? 1231 : 1237);
		result = prime * result + preference;
		result = prime * result + (team ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Queen other = (Queen) obj;
		if (active != other.active)
			return false;
		if (firstMove != other.firstMove)
			return false;
		if (preference != other.preference)
			return false;
		if (team != other.team)
			return false;
		return true;
	}

}
