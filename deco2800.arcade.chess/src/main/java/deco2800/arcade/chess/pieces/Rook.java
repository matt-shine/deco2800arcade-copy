package deco2800.arcade.chess.pieces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import deco2800.arcade.chess.FixedSizeList;

public class Rook extends Piece {

	/**
	 * Initialises the piece
	 * 
	 * @param team
	 */
	public Rook(boolean team, int pieceNo) {
		super(team, pieceNo);
		this.preference = 3;
	}

	public List<int[]> possibleMoves(int[] currentPos, FixedSizeList<FixedSizeList<Piece>> board_state) {
		List<int[]> moves = new ArrayList<int[]>();
		int x = currentPos[0];// current row position
		int y = currentPos[1];// current column position
		//add all possible moves for directions rook can move in
		moves.addAll(removeJumps(currentPos, board_state, 5));
		moves.addAll(removeJumps(currentPos, board_state, 6));
		moves.addAll(removeJumps(currentPos, board_state, 7));
		moves.addAll(removeJumps(currentPos, board_state, 8));
		return moves;
		
		
	}
	
	public String toString() {
		String toString = "";

		if (!team) {
			toString += "white ";
		} else {
			toString += "black ";
		}

		toString += "Rook";

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
		Rook other = (Rook) obj;
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
