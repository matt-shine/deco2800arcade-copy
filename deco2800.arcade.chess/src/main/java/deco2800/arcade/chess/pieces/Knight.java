package deco2800.arcade.chess.pieces;

import java.util.ArrayList;
import java.util.List;

public class Knight implements Piece {

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
	public Knight(boolean team, int pieceNo) {
		this.team = team;
		this.firstMove = false;
		this.active = true;
		this.preference = 4;
		this.pieceNo = pieceNo;
	}

	@Override
	public void deActivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reActivate() {
		// TODO Auto-generated method stub
		
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

	@Override
	public List<int[]> possibleMoves(int[] currentPos) {
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
		
		return movesToReturn;
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
