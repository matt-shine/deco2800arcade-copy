package deco2800.arcade.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.chess.FixedSizeList;

public class Knight extends Piece {
	
	/**
	 * Initialises the piece
	 * 
	 * @param team
	 */
	public Knight(boolean team, int pieceNo) {
		super(team, pieceNo);
		this.preference = 4;
	}

	public List<int[]> possibleMoves(int[] currentPos, FixedSizeList<FixedSizeList<Piece>> board_state) {
		List<int[]> possibleMoves = new ArrayList<int[]>();
		int x = currentPos[0];
		int y = currentPos[1];
		
		//Create and add all moves, whether they are on the board or not
		int [] possible1 = {x+2, y+1};
		int [] possible2 = {x+2, y-1};
		int [] possible3 = {x+1, y+2};
		int [] possible4 = {x+1, y-2};
		int [] possible5 = {x-2, y+1};
		int [] possible6 = {x-2, y-1};
		int [] possible7 = {x-1, y+2};
		int [] possible8 = {x-1, y-2};
		possibleMoves.add(possible1);
		possibleMoves.add(possible2);
		possibleMoves.add(possible3);
		possibleMoves.add(possible4);
		possibleMoves.add(possible5);
		possibleMoves.add(possible6);
		possibleMoves.add(possible7);
		possibleMoves.add(possible8);
		
		List<int[]> movesToReturn = new ArrayList<int[]>();
		//Check for and remove moves not on the board
		for(int i=0; i<possibleMoves.size(); i++) {
			int[] move = possibleMoves.get(i);
			if((0 <= move[0] && move[0] <= 7) && (0 <= move[1] && move[1] <= 7)) {
				movesToReturn.add(possibleMoves.get(i));	
			}
		}
		
		List<int[]> allowableMoves = new ArrayList<int[]>();
		
		for (int i = 0; i < movesToReturn.size(); i++) {
			// If the space is unoccupied add to list of allowable
			if (!occupiedSpace(board_state, movesToReturn.get(i))) {
				allowableMoves.add(movesToReturn.get(i));
			} else {
				// If the space is occupied check by which team
				int occx = movesToReturn.get(i)[0];
				int occy = movesToReturn.get(i)[1];
				List<Piece> row = board_state.get(occx);
				Piece onSquare = row.get(occy);

				// If piece on the space is on opposing team add to
				// allowable
				if (getTeam() != onSquare.getTeam()) {
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
		
		toString+="Knight";
		
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
		Knight other = (Knight) obj;
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
