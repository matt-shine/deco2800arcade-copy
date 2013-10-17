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
		
		// moves.add(castle);
		for (int i = 1; i <= 7; i++) {

			int[] a = { x + i, y };// move up i spaces
			int[] b = { x - i, y };// move down i spaces
			int[] c = { x, y + i };// move right
			int[] d = { x, y - i };// move left
			moves.add(a);
			moves.add(b);
			moves.add(c);
			moves.add(d);
			if ((x + i) > 7) {
				// don't add this move
				moves.remove(a);

			}

			if ((x - i) < 0) {
				// break;
				moves.remove(b);

			}

			if ((y + i) > 7) {
				// break;
				moves.remove(c);
			}

			if ((y - i) < 0) {
				// break;
				moves.remove(d);
			}

			if (x == 0) {

				moves.remove(b);
			}
			if (y == 0) {

				moves.remove(d);
			}
			if (y == 7) {

				moves.remove(c);
			}
			if (x == 7) {

				moves.remove(a);
			}
			
		}
		HashSet<int []> hs = new HashSet<int []>();
		hs.addAll(moves);
		moves.clear();
		moves.addAll(hs);
		
		List<int[]> allowableMoves;
		
		allowableMoves = new ArrayList<int[]>(removeJumpsUp(
				moves, currentPos, board_state));
		
		return allowableMoves;
		
		
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
