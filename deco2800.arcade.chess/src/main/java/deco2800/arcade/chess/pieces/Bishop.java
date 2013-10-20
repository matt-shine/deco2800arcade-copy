package deco2800.arcade.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.chess.FixedSizeList;


public class Bishop extends Piece{
	
	/**
	 * Initialises the piece
	 * 
	 * @param team
	 */
	public Bishop(boolean team, int pieceNo) {
		super(team, pieceNo);
		this.preference = 3;

	}

	public List<int[]> possibleMoves(int[] currentPos, FixedSizeList<FixedSizeList<Piece>> board_state) {
		List<int[]> moves = new ArrayList<int[]>();
		//add all possible moves for directions bishop can move in
		moves.addAll(removeJumps(currentPos, board_state, 1));
		moves.addAll(removeJumps(currentPos, board_state, 2));
		moves.addAll(removeJumps(currentPos, board_state, 3));
		moves.addAll(removeJumps(currentPos, board_state, 4));
		return moves;
	}
	
	public String toString() {
		String toString = "";
		
		if(!team) {
			toString+="white ";
		} else {
			toString+="black ";
		}
		
		toString+="Bishop";
		
		return toString;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + (firstMove ? 1231 : 1237);
		result = prime * result + pieceNo;
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
		Bishop other = (Bishop) obj;
		if (active != other.active)
			return false;
		if (firstMove != other.firstMove)
			return false;
		if (pieceNo != other.pieceNo)
			return false;
		if (preference != other.preference)
			return false;
		if (team != other.team)
			return false;
		return true;
	}

}
