package deco2800.arcade.chess;


import junit.framework.Assert;

import org.junit.Test;

import deco2800.arcade.chess.pieces.Piece;

public class StartupTest {

	@Test
	public void pieceTeamTest() {
		//initialise board
		Board board = new Board();
		//check white pieces are where they should be
		for (int i = 0; i < 8; i++) {
			int[] frontRow = {1,i};
			int[] backRow = {0,i};
			Piece currentFront = board.getPiece(frontRow);
			Piece currentBack = board.getPiece(backRow);
			Assert.assertTrue(!currentFront.getTeam());
			Assert.assertTrue(!currentBack.getTeam());
		}
		//check black pieces are where they should be
		for (int i = 0; i < 8; i++) {
			int[] frontRow = {6,i};
			int[] backRow = {7,i};
			Piece currentFront = board.getPiece(frontRow);
			Piece currentBack = board.getPiece(backRow);
			Assert.assertTrue(currentFront.getTeam());
			Assert.assertTrue(currentBack.getTeam());
		}
	}
	
	@Test
	public void emptySquaresTest() {
		//initialise board
		Board board = new Board();
	    //check correct squares are empty
		//iterate through all what should be empty squares
		for (int i = 0; i < 8; i++) {
			for (int j = 2; j < 6; j++) {
				int[] current = {j,i};
				Piece empty = board.getPiece(current);
				Assert.assertTrue(board.isNullPiece(empty));
			}
		}
	}
	
	@Test
	public void firstMoveTest() {
		//initialise board
		Board board = new Board();
		//check white has first move
		Assert.assertTrue(!board.whoseTurn());
	}
	
}
