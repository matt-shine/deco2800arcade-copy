package deco2800.arcade.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.chess.FixedSizeList;


public class Pawn extends Piece {

	/**
	 * Initialises the piece
	 * 
	 * @param team
	 */
	public Pawn(boolean team, int pieceNo) {
		super(team, pieceNo);
		this.preference = 1;
	}
	
	public List<int[]> possibleMoves(int[] currentPos, FixedSizeList<FixedSizeList<Piece>> board_state) {
		List<int[]> possibleMoves = new ArrayList<int[]>();
		int x = currentPos[0];
		int y = currentPos[1];
		
		//White
		if(!this.team) {
			//hasn't made first move
			if(!this.firstMove) {
				int[] a = {x+2, y};
				possibleMoves.add(a);
			}
			int[] b = {x+1, y};
			int[] c = {x+1, y+1};
			int[] d = {x+1, y-1};
			possibleMoves.add(b);
			possibleMoves.add(c);
			possibleMoves.add(d);
		} else {	//Black
			//hasn't made first move
			if(!this.firstMove) {
				int[] a = {x-2, y};
				possibleMoves.add(a);
			}
			int[] b = {x-1, y};
			int[] c = {x-1, y+1};
			int[] d = {x-1, y-1};
			possibleMoves.add(b);
			possibleMoves.add(c);
			possibleMoves.add(d);
		}
		
		List<int[]> movesToReturn = new ArrayList<int[]>();
		//Check for and remove moves not on the board
		for(int i=0; i<possibleMoves.size(); i++) {
			int[] move = possibleMoves.get(i);
			if((0 <= move[0] && move[0] <= 7) && (0 <= move[1] && move[1] <= 7)) {
				movesToReturn.add(possibleMoves.get(i));	
			}
		}
		
		List<int[]> allowableMoves = new ArrayList<int[]>();
		
		if (!getFirstMove()) {
			if (occupiedSpace(board_state, movesToReturn.get(1))) {
				movesToReturn.remove(0);
			}
		}
		for (int i = 0; i < movesToReturn.size(); i++) {
			if (occupiedSpace(board_state, movesToReturn.get(i))) {
				// If the space is occupied check by which team
				int occx = movesToReturn.get(i)[0];
				int occy = movesToReturn.get(i)[1];
				List<Piece> row = board_state.get(occx);
				Piece onSquare = row.get(occy);
				/*
				 * If piece on the space is on opposing team add to
				 * allowable if diagonal
				 */
				if ((getTeam() != onSquare.getTeam())
						&& (movesToReturn.get(i)[1] != currentPos[1])) {
					allowableMoves.add(movesToReturn.get(i));
				}
			} else { // If diagonal squares are empty don't add
				if (movesToReturn.get(i)[1] == currentPos[1]) {
					allowableMoves.add(movesToReturn.get(i));
				}
			}
		}
		
		return allowableMoves;
	}
	
	public String toString() {
		String toString = "";
		
		if(!team) {
			toString+="white ";
		} else {
			toString+="black ";
		}
		
		toString+="Pawn";
		
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
		Pawn other = (Pawn) obj;
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
