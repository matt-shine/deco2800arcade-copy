package deco2800.arcade.chess;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

public class jUnitTests {
	Board board, expectedBoard;
	Null nullPiece;
	ArrayList<Piece> expectedWhiteGraveyard, expectedBlackGraveyard;
	
	@Before
	public void setUp() {
		board = new Board();
		nullPiece = board.nullPiece;
		expectedBoard = new Board();
		expectedWhiteGraveyard = new ArrayList<Piece>();
		expectedBlackGraveyard = new ArrayList<Piece>();
	}
	
	/**
	 * Tests the movement of pawns and their capabilities to take pieces
	 */
	@Test
	public void testPawn() {
		//Set up the board how it should be when completed the moves
		System.out.println("Test Pawn:");
		expectedBoard.Board_State.get(1).add(1, nullPiece);
		expectedBoard.Board_State.get(1).add(4, nullPiece);
		expectedBoard.Board_State.get(1).add(5, nullPiece);
		expectedBoard.Board_State.get(3).add(1, board.whitePawn2);
		expectedBoard.Board_State.get(3).add(5, board.blackPawn7);
		expectedBoard.Board_State.get(6).add(3, nullPiece);
		expectedBoard.Board_State.get(6).add(4, board.whitePawn5);
		expectedBoard.Board_State.get(6).add(6, nullPiece);
		/////
		
		//Move all the pieces
		int[] w5 = {3,4};
		board.movePiece(board.whitePawn5, w5);
		int[] b4 = {4,3};
		board.movePiece(board.blackPawn4, b4);
		int[] w2 = {3,1};
		board.movePiece(board.whitePawn2, w2);
		int[] b7 = {4,6};
		board.movePiece(board.blackPawn7, b7);
		int[] w6 = {3,5};
		board.movePiece(board.whitePawn6, w6);
		int[] w5b = {4,3};
		board.movePiece(board.whitePawn5, w5b);
		expectedBlackGraveyard.add(board.blackPawn4);	//Update graveyard
		int[] b7b = {3,5};
		board.movePiece(board.blackPawn7, b7b);
		expectedWhiteGraveyard.add(board.whitePawn6);	//Update graveyard
		int[] w5c = {5,3};
		board.movePiece(board.whitePawn5, w5c);
		int[] w5d = {6,4};
		board.movePiece(board.whitePawn5, w5d);
		expectedBlackGraveyard.add(board.blackPawn5);	//Update graveyard
		
		//Test that everything is in the state it should be
		assertEquals("Black Graveyard: ", expectedBlackGraveyard, board.blackGraveyard);
		assertEquals("White Graveyard: ", expectedWhiteGraveyard, board.whiteGraveyard);
		assertEquals("Check for Check: ", true, board.checkForCheck(true));
		assertEquals("Board State: ", expectedBoard.Board_State, board.Board_State);
		
		//Make one final test
		int[] w5e = {7,3};
		board.movePiece(board.whitePawn5, w5e);
		expectedBlackGraveyard.add(board.blackKing);
	}
	
	@Test
	public void testBishop() {
		System.out.println("Test Bishop:");
		//Set up the board how it should be when completed the moves
		expectedBoard.Board_State.get(0).add(5, nullPiece);
		expectedBoard.Board_State.get(1).add(2, board.blackBishop1);
		expectedBoard.Board_State.get(1).add(6, nullPiece);
		expectedBoard.Board_State.get(2).add(6, board.whitePawn7);
		expectedBoard.Board_State.get(5).add(3, board.blackPawn4);
		expectedBoard.Board_State.get(6).add(1, nullPiece);
		expectedBoard.Board_State.get(6).add(3, nullPiece);
		expectedBoard.Board_State.get(7).add(0, board.whiteBishop2);
		expectedBoard.Board_State.get(7).add(2, nullPiece);
		/////

		//Move all the pieces
		int[] wp7 = {2,6};
		board.movePiece(board.whitePawn7, wp7);
		int[] bp4 = {5,3};
		board.movePiece(board.blackPawn4, bp4);
		int[] wb2 = {2,7};
		board.movePiece(board.whiteBishop2, wb2);
		int[] bb1 = {4,5};
		board.movePiece(board.blackBishop1, bb1);
		int[] badwb1 = {2,4};
		board.movePiece(board.whiteBishop1, badwb1);
		int[] badwb2 = {1,2};
		board.movePiece(board.whiteBishop1, badwb2);
		int[] badwb3 = {0,1};
		board.movePiece(board.whiteBishop1, badwb3);
		int[] bb1a = {1,2};
		board.movePiece(board.blackBishop1, bb1a);
		expectedWhiteGraveyard.add(board.whitePawn3);	//Update graveyard
		int[] bb2a = {7,2};
		board.movePiece(board.whiteBishop2, bb2a);
		int[] bb2b = {6,1};
		board.movePiece(board.whiteBishop2, bb2b);
		expectedBlackGraveyard.add(board.blackPawn2);	//Update graveyard
		int[] bb2c = {7,0};
		board.movePiece(board.whiteBishop2, bb2c);
		expectedBlackGraveyard.add(board.blackRook1);	//Update graveyard

		//Test that everything is in the state it should be
		assertEquals("Black Graveyard: ", expectedBlackGraveyard, board.blackGraveyard);
		assertEquals("White Graveyard: ", expectedWhiteGraveyard, board.whiteGraveyard);
		assertEquals("Board State: ", expectedBoard.Board_State, board.Board_State);
	}
	
	@Test
	public void testKingCastleSwap() {
		System.out.println("Test king-castle swap:");
		//Set up the board how it should be when completed the moves
		expectedBoard.Board_State.get(0).add(0, nullPiece);
		expectedBoard.Board_State.get(0).add(3, nullPiece);
		expectedBoard.Board_State.get(1).add(3, board.whiteBishop1);
		expectedBoard.Board_State.get(2).add(0, board.whiteKnight1);
		expectedBoard.Board_State.get(2).add(3, board.whitePawn4);
		expectedBoard.Board_State.get(0).add(1, board.whiteKing);
		expectedBoard.Board_State.get(0).add(2, board.whiteRook1);
		/////
		
		int[] wk1 = {2,0};
		board.movePiece(board.whiteKnight1, wk1);
		int[] wp4 = {2,3};
		board.movePiece(board.whitePawn4, wp4);
		int[] wb1 = {1,3};
		board.movePiece(board.whiteBishop1, wb1);
		int[] wking = {0,1};
		board.movePiece(board.whiteKing, wking);
		
		assertEquals("Black Graveyard: ", expectedBlackGraveyard, board.blackGraveyard);
		assertEquals("White Graveyard: ", expectedWhiteGraveyard, board.whiteGraveyard);
		assertEquals("Board State: ", expectedBoard.Board_State, board.Board_State);
	}
}
