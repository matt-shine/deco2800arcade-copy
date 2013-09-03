package deco2800.arcade.chess.pieces;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Rook implements Piece {

	boolean team;
	boolean firstMove;
	boolean active;
	int preference;
	int pieceNo;

	/**
	 * Initialises the piece
	 * 
	 * @param team
	 */
	public Rook(boolean team, int pieceNo) {
		this.team = team;
		this.firstMove = false;
		this.active = true;
		this.preference = 3;
		this.pieceNo = pieceNo;
	}

	@Override
	public void deActivate() {
		active = false;

	}

	@Override
	public void reActivate() {
		active = true;

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
	public boolean getTeam() {
		return this.team;
	}

	@Override
	public boolean getFirstMove() {
		return this.firstMove;
	}

	@Override
	public boolean getActiveState() {
		return this.active;
	}

	@Override
	public int getPreference() {
		return this.preference;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<int[]> possibleMoves(int[] currentPos) {
		List<int[]> moves = new ArrayList<int[]>();
		int x = currentPos[0];// current row position
		int y = currentPos[1];// current column position

		// int [] castle = {x, y+2};
		
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
			

		
			
		// if (!firstMove){
		// moves.remove(castle);
		// }
		}
		HashSet<int []> hs = new HashSet<int []>();
		hs.addAll(moves);
		moves.clear();
		moves.addAll(hs);
		//for(int v =0; v<moves.size(); v++){
			//System.out.println(Arrays.toString(moves.get(v)));
		//}
		
		return moves;
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
