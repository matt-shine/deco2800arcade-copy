package deco2800.arcade.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.chess.Board;
import deco2800.arcade.chess.FixedSizeList;
public class King extends Piece {

	boolean isChecked;

	/**
	 * Initialises the piece
	 * 
	 * @param team
	 */
	public King(boolean team) {
		super(team, 0);
		isChecked = false;
		this.preference = 6;
	}

	public List<int[]> possibleMoves(int[] currentPos, FixedSizeList<FixedSizeList<Piece>> board_state) {
		List<int[]> moves = new ArrayList<int[]>();
		int x = currentPos[0];// current row position
		int y = currentPos[1];// current column position

		int[] a = { x + 1, y }; // forward
		int[] b = { x + 1, y + 1 }; // forward right
		int[] c = { x + 1, y - 1 }; // forward left
		int[] d = { x, y + 1 }; // right
		int[] e = { x, y - 1 }; // left
		int[] f = { x - 1, y + 1 }; // Back right
		int[] g = { x - 1, y }; // back
		int[] h = { x - 1, y - 1 }; // back left
		moves.add(a);
		moves.add(b);
		moves.add(c);
		moves.add(d);
		moves.add(e);
		moves.add(g);
		moves.add(h);
		moves.add(f);

		if (firstMove) {
			int[] castle = { x, y + 2 };
			moves.add(castle);

			if (!firstMove) {
				moves.remove(castle);
			}
		}

		if (y == 7) {

			moves.remove(b);
			moves.remove(d);
			moves.remove(f);
		}
		if (x == 7) {

			moves.remove(a);
			moves.remove(b);
			moves.remove(c);
		}

		if (y == 0) {

			moves.remove(c);
			moves.remove(e);
			moves.remove(h);
		}
		if (x == 0) {
			;
			moves.remove(f);
			moves.remove(g);
			moves.remove(h);
		}
		
		List<int[]> allowableMoves = new ArrayList<int[]>();
		
		for (int i = 0; i < moves.size(); i++) {
			// If the space is unoccupied add to list of allowable
			if (!occupiedSpace(board_state, moves.get(i))) {
				allowableMoves.add(moves.get(i));
			} else {
				// If the space is occupied check by which team
				int occx = moves.get(i)[0];
				int occy = moves.get(i)[1];
				List<Piece> row = board_state.get(occx);
				Piece onSquare = row.get(occy);
				// If piece on the space is on opposing team add to
				// allowable
				if (getTeam() != onSquare.getTeam()) {
					allowableMoves.add(moves.get(i));
				}
			}
		}
		
		List<int[]> movesToReturn = new ArrayList<int[]>();
		//Check for and remove moves not on the board
		for(int i=0; i<allowableMoves.size(); i++) {
			int[] move = allowableMoves.get(i);
			if((0 <= move[0] && move[0] <= 7) && (0 <= move[1] && move[1] <= 7)) {
				movesToReturn.add(allowableMoves.get(i));	
			}
		}
		
		
		return movesToReturn;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean check) {

		isChecked = check;
	}

	public void checkForCheck(Board board, Piece piece) {

		ArrayList<int[]> checks = new ArrayList<int[]>();

		for (int x = 0; x < checks.size(); x++) {
			if (checks.get(x).equals(board.findPiece(piece))) {
				isChecked = true;
				break;
			} else {
				isChecked = false;
			}
		}
	}
	
	public String toString() {
		String toString = "";

		if (!team) {
			toString += "white ";
		} else {
			toString += "black ";
		}

		toString += "King";

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
		King other = (King) obj;
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
