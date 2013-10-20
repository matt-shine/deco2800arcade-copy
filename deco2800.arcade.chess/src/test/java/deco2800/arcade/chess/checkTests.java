package deco2800.arcade.chess;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import deco2800.arcade.chess.pieces.Piece;

public class checkTests {
	@Test
	public void checkMateTest() {
		//initialise board
		Board board = new Board();
		//check white pieces are where they should be
			int [] move1 = {3, 4};
			int [] move2 = {4, 4};
			int [] move3 = {2, 5};
			int [] move4 = {5, 0};
			int [] move5 = {3, 2};
			int [] move6 = {5, 1};
			int [] move7 = {6, 5};
			
			int[] whitePawnPos = {1,4};
			int[] whiteQueenPos = {0, 3};
			int[] whiteBishopPos = {0, 5};
			
			int[] blackPawnPosS = {6,4};
			int[] blackPawnPos = {6,0};
			int[] blackPawnPos2 = {6,1};
			
			Piece whitePawn = board.getPiece(whitePawnPos);
			Piece blackPawn = board.getPiece(blackPawnPosS);
			Piece blackPawn2 = board.getPiece(blackPawnPos);
			Piece blackPawn3 = board.getPiece(blackPawnPos2);
			Piece whiteQueen = board.getPiece(whiteQueenPos);
			Piece whiteBishop = board.getPiece(whiteBishopPos);
			
			board.movePiece(whitePawn, move1);
			board.movePiece(blackPawn, move2);
			board.movePiece(whiteQueen, move3);
			board.movePiece(blackPawn2, move4);
			board.movePiece(whiteBishop, move5);
			board.movePiece(blackPawn3, move6);
			board.movePiece(whiteQueen, move7);
			assertTrue(board.checkForCheckmate(true));
			
		}
	
	@Test
	public void checkTest(){
		Board board = new Board();
		int [] move1 = {3, 4};
		int [] move2 = {4, 3};
		int [] move3 = {4, 1};
		
		int[] whitePawnPos = {1, 4};
		int[] whiteBishopPos = {0, 5};
		int[] blackPawnPosS = {6, 3};

		Piece whitePawn = board.getPiece(whitePawnPos);
		Piece blackPawn = board.getPiece(blackPawnPosS);
		Piece whiteBishop = board.getPiece(whiteBishopPos);
		
		board.movePiece(whitePawn, move1);
		board.movePiece(blackPawn, move2);
		board.movePiece(whiteBishop, move3);
		
		assertTrue(board.checkForCheck(true));
	}
}
