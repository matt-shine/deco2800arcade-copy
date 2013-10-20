package deco2800.arcade.chess.pieces;

import java.util.List;

import deco2800.arcade.chess.FixedSizeList;

public class Null extends Piece {
	
	public Null(boolean team) {
		super(team, 0);
	}

	int piece = 0;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + piece;
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
		Null other = (Null) obj;
		if (piece != other.piece)
			return false;
		return true;
	}
	
	public String toString() {
		return "Empty Square";
	}

	@Override
	public List<int[]> possibleMoves(int[] currentPos, FixedSizeList<FixedSizeList<Piece>> board_state) {
		// TODO Auto-generated method stub
		return null;
	}

}
