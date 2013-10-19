package deco2800.arcade.chess;

import org.junit.Assert;
import org.junit.Test;

import deco2800.arcade.chess.Board;
import deco2800.arcade.chess.pieces.Piece;

public class MovementTest {

	@Test
	public void basicPawnMovementTest() {
		//initialise board
		Board board = new Board();
		int[] pawnStartPos = {1,2};
		int[] pawnNewPos = {2,2};
		//get pawn on square
		Piece pawn = board.getPiece(pawnStartPos);
		//move pawn
		board.movePiece(pawn, pawnNewPos);
		int[] expectedPos = {2,2};
		//check it moved correctly
		Assert.assertEquals(expectedPos[0], board.findPiece(pawn)[0]);
		Assert.assertEquals(expectedPos[1], board.findPiece(pawn)[1]);
	}
	
	@Test
	public void basicKnightMovementTest() {
		//intialise board
		Board board = new Board();
		int[] knightStartPos = {0,1};
		int[] knightNewPos = {2,2};
		//get knight on square
		Piece knight = board.getPiece(knightStartPos);
		//move knight
		board.movePiece(knight, knightNewPos);
		int[] expectedPos = {2,2};
		//check it moved correctly
		Assert.assertEquals(expectedPos[0], board.findPiece(knight)[0]);
		Assert.assertEquals(expectedPos[1], board.findPiece(knight)[1]);
	}
	
	@Test
	public void basicBishopMovementTest() {
		//initialise board
		Board board = new Board();
		int[] bishopStartPos = {0,2};
		int[] pawnToMovePos = {1,3};
		int[] bishopNewPos = {3,5};
		int[] pawnNewPos = {2,3};
		//get bishop and pawn
		Piece bishop = board.getPiece(bishopStartPos);
		Piece pawn = board.getPiece(pawnToMovePos);
		//move pawn so bishop can move
		board.movePiece(pawn, pawnNewPos);
		//move bishop
		board.movePiece(bishop, bishopNewPos);
		int[] expectedPos = {3,5};
		//check it moved correctly
		Assert.assertEquals(expectedPos[0], board.findPiece(bishop)[0]);
		Assert.assertEquals(expectedPos[1], board.findPiece(bishop)[1]);
	}
	
	@Test
	public void basicRookMovementTest() {
		//initialise board
		Board board = new Board();
		int[] rookPos = {0,0};
		int[] pawnPos = {1,0};
		int[] pawnNewPos = {3,0};
		int[] rookNewPos = {2,0};
		//get rook and pawn
		Piece rook = board.getPiece(rookPos);
		Piece pawn = board.getPiece(pawnPos);
		//move pawn so rook can move
		board.movePiece(pawn, pawnNewPos);
		//move rook
		board.movePiece(rook, rookNewPos);
		int[] expectedPos = {2,0};
		//check it moved correctly
		Assert.assertEquals(expectedPos[0], board.findPiece(rook)[0]);
		Assert.assertEquals(expectedPos[1], board.findPiece(rook)[1]);
	}
	
	@Test
	public void basicQueenMovementTest() {
		//intialise board
		Board board = new Board();
		int[] queenPos = {0,3};
		int[] pawnPos = {1,4};
		int[] queenNewPos = {4,7};
		int[] pawnNewPos = {2,4};
		//get queen and pawn
		Piece queen = board.getPiece(queenPos);
		Piece pawn = board.getPiece(pawnPos);
		//move pawn so queen can move
		board.movePiece(pawn, pawnNewPos);
		//move queen
		board.movePiece(queen, queenNewPos);
		int[] expectedPos = {4,7};
		//check it moved correctly
		Assert.assertEquals(expectedPos[0], board.findPiece(queen)[0]);
		Assert.assertEquals(expectedPos[1], board.findPiece(queen)[1]);
	}
	
	@Test
	public void basicKingMovementTest() {
		//initialise board
		Board board = new Board();
		int[] kingPos = {0,4};
		int[] pawnPos = {1,3};
		int[] kingNewPos = {1,3};
		int[] pawnNewPos = {2,3};
		//get king and pawn
		Piece king = board.getPiece(kingPos);
		Piece pawn = board.getPiece(pawnPos);
		//move pawn so king can move
		board.movePiece(pawn, pawnNewPos);
		//move king
		board.movePiece(king, kingNewPos);
		int[] expectedPos = {1,3};
		//check it moved correctly
		Assert.assertEquals(expectedPos[0], board.findPiece(king)[0]);
		Assert.assertEquals(expectedPos[1], board.findPiece(king)[1]);
	}
	
	@Test
	public void turnsTest() {
		//initialise board
		Board board = new Board();
		//intialise where pieces will move to
		int[] whitePawnPos = {1,0};
		int[] blackPawnPos = {6,0};
		int[] whitePawnNewPos = {2,0};
		int[] blackPawnNewPos = {5,0};
		int[] whitePawnLastPos = {3,0};
		int[] blackPawnLastPos = {4,0};
		//get white and black pawn
		Piece whitePawn = board.getPiece(whitePawnPos);
		Piece blackPawn = board.getPiece(blackPawnPos);
		//move white pawn and check its blacks turn
		board.movePiece(whitePawn, whitePawnNewPos);
		Assert.assertTrue(board.whoseTurn());
		//move black pawn and check its whites turn
		board.movePiece(blackPawn, blackPawnNewPos);
		Assert.assertTrue(!board.whoseTurn());
		//move white pawn and check its blacks turn
		board.movePiece(whitePawn, whitePawnLastPos);
		Assert.assertTrue(board.whoseTurn());
		//move black pawn and check its whites turn
		board.movePiece(blackPawn, blackPawnLastPos);
		Assert.assertTrue(!board.whoseTurn());
	}
	
	@Test
	public void kingCastleSwapTest() {
		Board board = new Board();
		//Test white teams king castle swap
		int[] wp7 = {2,6}, wk2 = {2,5}, wb2 = {2,7}, swapMove = {0,6};
		
		board.movePiece(board.whitePawn7, wp7);
		board.movePiece(board.whiteKnight2, wk2);
		board.movePiece(board.whiteBishop2, wb2);
		board.movePiece(board.whiteKing, swapMove);
		
		int[] expKing = {0,6}, expRook = {0,5};
		
		Assert.assertEquals(expKing[0], board.findPiece(board.whiteKing)[0]);
		Assert.assertEquals(expKing[1], board.findPiece(board.whiteKing)[1]);
		Assert.assertEquals(expRook[0], board.findPiece(board.whiteRook2)[0]);
		Assert.assertEquals(expRook[1], board.findPiece(board.whiteRook2)[1]);
		
		//Test white teams king castle swap
		board = new Board();
		int[] bp7 = {5,6}, bk2 = {5,5}, bb2 = {5,7}, swapMove2 = {7,6};

		board.movePiece(board.blackPawn7, bp7);
		board.movePiece(board.blackKnight2, bk2);
		board.movePiece(board.blackBishop2, bb2);
		board.movePiece(board.blackKing, swapMove2);

		int[] expKing2 = {7,6}, expRook2 = {7,5};

		Assert.assertEquals(expKing2[0], board.findPiece(board.blackKing)[0]);
		Assert.assertEquals(expKing2[1], board.findPiece(board.blackKing)[1]);
		Assert.assertEquals(expRook2[0], board.findPiece(board.blackRook2)[0]);
		Assert.assertEquals(expRook2[1], board.findPiece(board.blackRook2)[1]);
	}

}
