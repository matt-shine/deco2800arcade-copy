package deco2800.arcade.chess.pieces;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Null implements Piece {
	int piece = 0;
	@Override
	public void deActivate() {
		// TODO Auto-generated method stub
		
	}

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

	@Override
	public void reActivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getTeam() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getFirstMove() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getActiveState() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPreference() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString() {
		return "Empty Square";
	}

	@Override
	public List<int[]> possibleMoves(int[] currentPos) {
		// TODO Auto-generated method stub
		return null;
	}



}
