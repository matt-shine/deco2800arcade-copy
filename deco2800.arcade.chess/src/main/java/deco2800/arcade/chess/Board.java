package deco2800.arcade.chess;

import java.util.*;

import deco2800.arcade.chess.pieces.*;

public class Board {
	private FixedSizeList<FixedSizeList<Piece>> boardState;
	// used to check if moves will move you into check without altering actual
	// board
	private ArrayList<Piece> whiteGraveyard, blackGraveyard;
	// true = blacks turn, false = whites turn
	private boolean turn;
	
	// Initialise pieces
	public Pawn whitePawn1, whitePawn2, whitePawn3, whitePawn4;
	public Pawn whitePawn5, whitePawn6, whitePawn7, whitePawn8;
	public Pawn blackPawn1, blackPawn2, blackPawn3, blackPawn4;
	public Pawn blackPawn5, blackPawn6, blackPawn7, blackPawn8;
	public Rook whiteRook1, whiteRook2, blackRook1, blackRook2;
	public Knight whiteKnight1, whiteKnight2, blackKnight1, blackKnight2;
	public Bishop whiteBishop1, whiteBishop2, blackBishop1, blackBishop2;
	public King whiteKing, blackKing;
	public Queen whiteQueen, blackQueen;
	public Null nullPiece;


	/**
	 * Initialises board to the default setup.
	 */
	public Board() {
		turn = false;
		boardState = new FixedSizeList<FixedSizeList<Piece>>();
		blackGraveyard = new ArrayList<Piece>();
		whiteGraveyard = new ArrayList<Piece>();
		nullPiece = new Null(true);

		// Add 8 rows to the board
		for (int a = 0; a < 8; a++) {
			boardState.add(a, new FixedSizeList<Piece>());
		}
		initialisePieces();
		addPieces();

		// Add null values to all other squares
		FixedSizeList<Piece> row;
		for (int c = 2; c < 6; c++) {
			row = boardState.get(c);
			for (int d = 0; d < 8; d++) {
				row.add(d, nullPiece);
			}
		}
	}
	
	public FixedSizeList<FixedSizeList<Piece>> getBoardState() {
		return boardState;
	}
	
	public ArrayList<Piece> getGraveyard(boolean team) {
		if (team) {
			return blackGraveyard;
		}
		return whiteGraveyard;
	}

	/**
	 * Check if either team is in 'Check'
	 * 
	 * @param Team
	 *            The team who is being checked for being in 'Check'. - False is
	 *            for White - True is for Black
	 * @return True if the team is in 'Check', false otherwise.
	 */
	public boolean checkForCheck(boolean Team) {
		List<Piece> activePieces = findActivePieces();
		List<Piece> activeEnemyPieces = new ArrayList<Piece>();
		List<int[]> enemyMoves = new ArrayList<int[]>();
		
		int[] kingPos;
		kingPos = (Team ? (findPiece(blackKing)) : (findPiece(whiteKing)));
		for (int i = 0; i < activePieces.size(); i++) {
			if (activePieces.get(i).getTeam() != Team) {
				activeEnemyPieces.add(activePieces.get(i));
			}
		}
		for (int i = 0; i < activeEnemyPieces.size(); i++) {
			List<int[]> pieceMoves = allowedMoves(activeEnemyPieces.get(i));
			for (int j = 0; j < pieceMoves.size(); j++) {
				if (!enemyMoves.contains(pieceMoves.get(j))) {
					enemyMoves.add(pieceMoves.get(j));
				}
			}
		}
		try {
			for (int i = 0; i < enemyMoves.size(); i++) {
				if ((enemyMoves.get(i)[0] == kingPos[0])
						&& (enemyMoves.get(i)[1] == kingPos[1])) {
					return true;
				}
			}
			return false;
		} catch (NullPointerException e) {
			System.err.println("King has been taken");
			return false;
		}
	}

	/**
	 * Check if either team is in 'Checkmate'
	 * 
	 * @param Team
	 *            The team who is being checked for being in 'Checkmate'. -
	 *            False is for White - True is for Black
	 * @return true if the team is in checkmate false otherwise
	 */
	public boolean checkForCheckmate(boolean team) {
		List<int[]> checkMoves;
		List<Piece> activePieces = this.findActivePieces();
		List<Piece> activeWhite = new ArrayList<Piece>();
		List<Piece> activeBlack = new ArrayList<Piece>();
		// can't be in checkmate if not in check
		if (!(this.checkForCheck(team))) {
			return false;
		}
		// find active pieces on team
		for (Piece piece : activePieces) {
			if (piece.getTeam()) {
				activeBlack.add(piece);
			} else {
				activeWhite.add(piece);
			}
		}
		if (team) {
			for (Piece piece : activeBlack) {
				checkMoves = this.removeCheckMoves(piece);
				// if a piece can move, not in checkmate
				if (!(checkMoves.isEmpty())) {
					return false;
				}
			}
		} else {
			for (Piece piece : activeWhite) {
				checkMoves = this.removeCheckMoves(piece);
				// if a piece can move not in checkmate
				if (!(checkMoves.isEmpty())) {
					return false;
				}
			}
		}
		// no pieces can move, in checkmate
		System.err.println("In checkmate");
		return true;
	}

	/**
	 * Creates a list of only the moves that don't put the team in check
	 * 
	 * @param piece
	 *            The piece for which moves will be determined
	 * @return The list of all allowable moves
	 */
	public List<int[]> removeCheckMoves(Piece piece) {
		int index = 0;
		List<int[]> allowedMoves = this.allowedMoves(piece);
		List<int[]> allowedMovesCopy = new ArrayList<int[]>();
		for (int i = 0; i < allowedMoves.size(); i++) {
			allowedMovesCopy.add(allowedMoves.get(i));
		}
		boolean inCheck;
		boolean team = piece.getTeam();
		int currentx = this.findPiece(piece)[0];
		int currenty = this.findPiece(piece)[1];
		for (int[] moveTo : allowedMoves) {
			Piece onSquare = this.getPiece(moveTo);
			boardState.get(moveTo[0]).add(moveTo[1], piece);
			boardState.get(currentx).add(currenty, nullPiece);
			inCheck = this.checkForCheck(team);
			if (inCheck) {
				allowedMovesCopy.remove(index);
				index--;
			}
			if (this.isNullPiece(onSquare)) {
				boardState.get(currentx).add(currenty, piece);
				boardState.get(moveTo[0]).add(moveTo[1], nullPiece);
			} else {
				onSquare.reActivate();
				if (onSquare.getTeam()) {
					blackGraveyard.remove(onSquare);
				} else {
					whiteGraveyard.remove(onSquare);
				}
				boardState.get(currentx).add(currenty, piece);
				boardState.get(moveTo[0]).add(moveTo[1], onSquare);
			}
			index++;
		}
		return allowedMovesCopy;
	}

	/**
	 * Determines if a stalemate has been reached.
	 * 
	 * @param Team
	 *            The team that is currently in turn.
	 * @return True if a stalemate has been reached, false otherwise.
	 */
	public boolean checkForStaleMate(boolean team) {
		boolean staleMate = false;
		// if only kings remain also stalemate, check for this
		List<Piece> blackPieces = this.findActivePieces(true);
		List<Piece> whitePieces = this.findActivePieces(false);
		// if only one piece remains on each team it is the king, in stalemate
		if ((blackPieces.size() == 1) && (whitePieces.size() == 1)) {
			return true;
		}
		// check that the king isn't in check, then check all the pieces to see
		// if they can move anywhere, if they can't then it's a stalemate
		if (!checkForCheck(team) && !checkMoves(team).contains(true)) {
			staleMate = true;
		}
		return staleMate;
	}

	/**
	 * Reactivates the piece into play and removes it from the graveyard
	 * 
	 * @param retrievalPiece
	 *            Piece to be retrieved into play
	 */
	/*private void retrievePiece(Piece retrievalPiece) {

	}*/

	/**
	 * Checks whether a space is currently occupied
	 * 
	 * @param position
	 *            The position to be checked for occupation
	 * @return True if the space is occupied, false if available
	 */
	public boolean occupiedSpace(int[] position) {
		int x = position[0];
		int y = position[1];
		FixedSizeList<Piece> row = boardState.get(x);
		Piece onSpace = row.get(y);

		if (onSpace.getClass() == nullPiece.getClass()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Returns a list of all moves the given piece is allowed to make with it's
	 * specific move restrictions.
	 * 
	 * @param piece
	 *            The piece for which the allowed moves are desired
	 * @return A list of all allowed moves the piece can make
	 */
	public List<int[]> allowedMoves(Piece piece) {
		int[] currentPos = findPiece(piece);

		List<int[]> possibleMoves = piece.possibleMoves(currentPos, boardState);
		
		return possibleMoves;
	}

	/**
	 * Moves the given piece to the given position.
	 * 
	 * @param piece
	 *            The piece to be moved.
	 * @param newPosition
	 *            The position to which the piece will be moved.
	 * @return True if the piece moved, false if the move failed.
	 */
	public boolean movePiece(Piece piece, int[] newPosition) {
		int[] oldPos = findPiece(piece);
		int x = newPosition[0];
		int y = newPosition[1];

		List<int[]> allowedMoves = removeCheckMoves(piece);

		boolean allowed = false;
		for (int i = 0; i < allowedMoves.size(); i++) {

			if (newPosition[0] == allowedMoves.get(i)[0]
					&& newPosition[1] == allowedMoves.get(i)[1]) {
				allowed = true;
			}
		}

		// If the piece is a king
		if ((piece.getClass() == blackKing.getClass())
				|| (piece.getClass() == whiteKing.getClass())) {
			// Check if the kingCastle swap can be performed, if so allow move
			if (checkKingCastleSwap(piece)) {
				if(performKingCastleSwap(piece, newPosition, oldPos)) {
					return true;
				}
			}
		}

		if (!allowed) {
			System.err.println("not allowable move for " + piece + ":"
					+ newPosition[0] + ", " + newPosition[1]);
			return false;
		}

		if (piece.getClass() == whitePawn1.getClass()) {
			piece.hasMoved();
		}
		
		/*
		 * If space is occupied (can only be by other team due to possibleMoves)
		 * deactivate the piece, add it to the graveyard and move new piece to
		 * that square
		 */
		if (occupiedSpace(newPosition)) {
			// Remove enemy piece on the newPosition
			Piece onSquare = boardState.get(x).get(y);
			onSquare.deActivate();

			
			if (onSquare.getTeam()) {
				blackGraveyard.add(onSquare);
			} else {
				whiteGraveyard.add(onSquare);
			}
			
			boardState.get(oldPos[0]).add(oldPos[1], nullPiece);
			boardState.get(x).add(y, piece);
			checkPawnSwap(piece, turn);
			this.nextTurn();
			return true;
		} else {
			boardState.get(oldPos[0]).add(oldPos[1], nullPiece);
			boardState.get(x).add(y, piece);
			checkPawnSwap(piece, turn);
			this.nextTurn();
			return true;
		}
		
	}
	
	/**
	 * Checks if piece is pawn and if it has reached end of board.
	 * If it has, replaces with piece in graveyard (if there is a piece that
	 * isn't a pawn)
	 * 
	 * @param piece
	 * 			the piece to check
	 * @param team
	 * 			the team who is currently moving
	 */
	private void checkPawnSwap(Piece piece, boolean team) {
		boolean atEnd = false;
		int highestPref = 1;
		Piece replaceWith = piece;
		if (piece.getClass() != whitePawn1.getClass()) {
			return;
		}
		int[] piecePos = this.findPiece(piece);
		if (team) {
			if (piecePos[0] == 0) {
				atEnd = true;
			}
		} else {
			if (piecePos[0] == 7) {
				atEnd = true;
			}
		}
		if (atEnd) {
			if (team) {
				for (Piece deadPiece: blackGraveyard) {
					int currentPref;
					currentPref = deadPiece.getPreference();
					if (currentPref > highestPref) {
						highestPref = currentPref;
						replaceWith = deadPiece;
					}
				}
			} else {
				for (Piece deadPiece: whiteGraveyard) {
					int currentPref;
					currentPref = deadPiece.getPreference();
					if (currentPref > highestPref) {
						highestPref = currentPref;
						replaceWith = deadPiece;
					}
				}
			}
		} else {
			return;
		}
		if (highestPref == 1) {
			return;
		}
		System.out.println("replacing pawn with " + replaceWith.toString());
		piece.deActivate();
		if (team) {
			blackGraveyard.add(piece);
		} else {
			whiteGraveyard.add(piece);
		}
		//replaceWith.reActivate();
		boardState.get(piecePos[0]).add(piecePos[1], replaceWith);
		replaceWith.reActivate();
	}

	/**
	 * Piece the king castle swap if the user attempted to.
	 * 
	 * @param piece
	 * 		The piece that is being attempted to move
	 * @param newPosition
	 * 		The position that the given piece was attempted to move to
	 * @param oldPos
	 * 		The position that the given piece began in
	 * @return
	 * 		True if the swap succeeded, false otherwise
	 */
	private boolean performKingCastleSwap(Piece piece, int[] newPosition, int[] oldPos) {
		boolean team = piece.getTeam();
		int x = newPosition[0];
		int y = newPosition[1];
		
		int[] bKingPos = {7,6}, bCastlePos = {7,5}, wKingPos = {0,6}, wCastlePos = {0,5};
		int[] kingSwapPos =  team ? bKingPos : wKingPos;
		int[] castleSwapPos = team ? bCastlePos : wCastlePos;

		// Check they attempted the swap
		if( ((piece.getClass() == blackKing.getClass()) || (piece.getClass() == whiteKing.getClass())) 
				&& (newPosition[0] == kingSwapPos[0]) && (newPosition[1] == kingSwapPos[1])) {

			// Move King to new position
			boardState.get(oldPos[0]).add(oldPos[1], nullPiece);
			boardState.get(x).add(y, piece);

			// Move Rook to new position
			Piece rook = piece.getTeam() ? blackRook2 : whiteRook2;
			
			boardState.get(findPiece(rook)[0]).add(
					findPiece(rook)[1], nullPiece);
					
			boardState.get(castleSwapPos[0]).add(castleSwapPos[1],
					rook);
			this.nextTurn();
			return true;
		} else {
			return false;
		}
	}
		
	/**
	 * Finds and returns the board co-ordinates for the given piece
	 * 
	 * @param piece
	 *            The piece for which the location is desires
	 * @return The current co-ordinates of the piece on the board
	 */
	public int[] findPiece(Piece piece) {
		for (int i = 0; i < 8; i++) {
			FixedSizeList<Piece> row = boardState.get(i);
			for (int j = 0; j < 8; j++) {
				if (row.get(j).equals(piece)) {
					int[] pos = { i, j };
					return pos;
				}
			}
		}
		return null;
	}

	/**
	 * Undoes last move
	 */
	/*private void removeMove() {
		moves.remove(moves.size() - 1);
		pieceMoved.remove(pieceMoved.size() - 1);
	}*/

	/**
	 * Finds and returns a list of all active pieces currently on the board
	 * 
	 * @return A list of all currently active pieces on the board
	 */
	public List<Piece> findActivePieces() {
		List<Piece> activePieces = new ArrayList<Piece>();

		for (int i = 0; i < 8; i++) {
			FixedSizeList<Piece> row = boardState.get(i);
			for (int j = 0; j < 8; j++) {
				Piece onSquare = row.get(j);
				if ((onSquare.getClass() != nullPiece.getClass())
						&& onSquare.getActiveState()) {
					activePieces.add(onSquare);
				}
			}
		}
		return activePieces;
	}

	/**
	 * Finds and returns a list of all currently active pieces for the given
	 * team.
	 * 
	 * @param team
	 *            The team for which the active pieces will be found.
	 * @return A list of all currently active pieces for the team.
	 */
	public List<Piece> findActivePieces(boolean team) {
		List<Piece> allActive;
		List<Piece> returnPieces = new ArrayList<Piece>();

		allActive = findActivePieces();

		for (Piece piece : allActive) {
			if (piece.getTeam() == team) {
				returnPieces.add(piece);
			}
		}

		return returnPieces;
	}

	public String toString() {
		return boardState.toString();
	}


	public Piece getPiece(int[] pos) {
		FixedSizeList<Piece> row = boardState.get(pos[0]);

		return row.get(pos[1]);
	}

	/**Takes in a piece and determines whether it is the null piece or not.
	 * 
	 * @param piece
	 * 		Piece to test if null
	 * @return 
	 * 		True if the given piece in Null. i.e the square is empty
	 */
	public boolean isNullPiece(Piece piece) {
		if (piece.getClass() == nullPiece.getClass()) {
			return true;
		}
		return false;
	}

	/**Returns whose turn it is to make the next move
	 * 
	 * @return 
	 * 		True if it is the black teams turn, false if whites
	 */
	public boolean whoseTurn() {
		return turn;
	}

	/**
	 * 
	 * @return the next team's turn
	 */
	public void nextTurn() {
		turn = !turn;
	}

	/**
	 * Returns a list of booleans corresponding to the pieces on the team that
	 * can and cannot move
	 * 
	 * @param team
	 * 		Team that is being checked for possible moves
	 * @return
	 * 		Return a list of boolean values, corresponding to the pieces that
	 * 		can move
	 */
	public List<Boolean> checkMoves(boolean team) {
		List<Piece> activePieces = findActivePieces(team);
		List<Boolean> canMove = new ArrayList<Boolean>();

		for (int i = 0; i < activePieces.size(); i++) {
			if (removeCheckMoves(activePieces.get(i)).isEmpty()) {
				canMove.add(false);
			} else {
				canMove.add(true);
			}
		}
		return canMove;
	}

	/**
	 * Takes in a piece and returns true if that pieces team can perform the
	 * king-castle swap and false otherwise
	 * 
	 * @param piece
	 * 		Piece who's team is to be checked
	 * @return 
	 * 		True if piece's team can perform the move, false otherwise
	 */
	private boolean checkKingCastleSwap(Piece piece) {
		// If on the black team and king and rook1 haven't moved
		if (piece.getTeam() && !blackKing.getFirstMove()
				&& !blackRook1.getFirstMove()) {
			int[] middleSquare1 = { 7, 5 };
			int[] middleSquare2 = { 7, 6 };
			// If both middle squares are empty
			if (!occupiedSpace(middleSquare1) && !occupiedSpace(middleSquare2)) {
				return true;
			}
		}

		// If on the white team and king and rook1 haven't moved
		if (!piece.getTeam() && !whiteKing.getFirstMove()
				&& !whiteRook1.getFirstMove()) {
			int[] middleSquare1 = { 0, 5 };
			int[] middleSquare2 = { 0, 6 };
			// If both middle squares are empty
			if (!occupiedSpace(middleSquare1) && !occupiedSpace(middleSquare2)) {
				return true;
			}
		}
		// Required conditions aren't met
		return false;
	}

	/**
	 * Makes a computer determined move of the given piece based on basic
	 * logic
	 * 
	 * @param movePiece
	 * 		The piece that will be moves
	 */
	public void moveAIPieceEasy(Piece movePiece) {

		int[] moveSquare = chooseAISquare(movePiece);
		System.out.println("board3");

		System.out.println("AI move: " + movePiece);
		System.out.println("AI move: [" + moveSquare[0] + ", " + moveSquare[1]
				+ "]");
		if (!checkForStaleMate(turn)) {
			movePiece(movePiece, moveSquare);
		}

	}

	/**
	 * Chooses a piece from the computer controlled teams active pieces to move.
	 * 
	 * @return The piece that will be moved by the computer controlled player.
	 */
	Piece chooseAIPiece() {
		Piece returnPiece;

		List<Piece> activePieces = findActivePieces(turn);
		int numPieces;
		List<Piece> allowedPieces = new ArrayList<Piece>();

		for (Piece piece : activePieces) {
			if (removeCheckMoves(piece).size() != 0) {
				allowedPieces.add(piece);
			}
		}

		numPieces = allowedPieces.size();
		int pieceIndex = (int) (Math.random() * ((numPieces - 1) + 1));

		returnPiece = allowedPieces.get(pieceIndex);

		return returnPiece;
	}

	/**
	 * Chooses an allowable move for the given piece for the computer controlled
	 * player to make.
	 * 
	 * @param movePiece
	 *            The piece that will be moved by the computer controlled
	 *            player.
	 * @return The square to which the given piece will be moved.
	 */
	private int[] chooseAISquare(Piece movePiece) {

		List<int[]> moves = removeCheckMoves(movePiece);

		int numMoves = moves.size();

		int moveIndex = (int) (Math.random() * ((numMoves - 1) + 1));

		return moves.get(moveIndex);
	}

	/**
	 * Initialises all pieces on the board
	 */
	public void initialisePieces() {
		// Initialise white pieces
		whitePawn1 = new Pawn(false, 1);
		whitePawn2 = new Pawn(false, 2);
		whitePawn3 = new Pawn(false, 3);
		whitePawn4 = new Pawn(false, 4);
		whitePawn5 = new Pawn(false, 5);
		whitePawn6 = new Pawn(false, 6);
		whitePawn7 = new Pawn(false, 7);
		whitePawn8 = new Pawn(false, 8);
		whiteRook1 = new Rook(false, 1);
		whiteKnight1 = new Knight(false, 1);
		whiteBishop1 = new Bishop(false, 1);
		whiteKing = new King(false);
		whiteQueen = new Queen(false);
		whiteBishop2 = new Bishop(false, 2);
		whiteKnight2 = new Knight(false, 2);
		whiteRook2 = new Rook(false, 2);

		// Initialise black pieces
		blackPawn1 = new Pawn(true, 1);
		blackPawn2 = new Pawn(true, 2);
		blackPawn3 = new Pawn(true, 3);
		blackPawn4 = new Pawn(true, 4);
		blackPawn5 = new Pawn(true, 5);
		blackPawn6 = new Pawn(true, 6);
		blackPawn7 = new Pawn(true, 7);
		blackPawn8 = new Pawn(true, 8);
		blackRook1 = new Rook(true, 1);
		blackKnight1 = new Knight(true, 1);
		blackBishop1 = new Bishop(true, 1);
		blackKing = new King(true);
		blackQueen = new Queen(true);
		blackBishop2 = new Bishop(true, 2);
		blackKnight2 = new Knight(true, 2);
		blackRook2 = new Rook(true, 2);
	}

	/**
	 * Adds all pieces to their starting positions on the board
	 */
	public void addPieces() {
		// Add white pieces to row 1

		FixedSizeList<Piece> row = boardState.get(0);
		row.add(0, whiteRook1);
		row.add(1, whiteKnight1);
		row.add(2, whiteBishop1);
		row.add(3, whiteQueen);
		row.add(4, whiteKing);
		row.add(5, whiteBishop2);
		row.add(6, whiteKnight2);
		row.add(7, whiteRook2);

		// Add white pawns to row 2
		row = boardState.get(1);
		row.add(0, whitePawn1);
		row.add(1, whitePawn2);
		row.add(2, whitePawn3);
		row.add(3, whitePawn4);
		row.add(4, whitePawn5);
		row.add(5, whitePawn6);
		row.add(6, whitePawn7);
		row.add(7, whitePawn8);

		// Add black pieces to row 1
		row = boardState.get(7);
		row.add(0, blackRook1);
		row.add(1, blackKnight1);
		row.add(2, blackBishop1);
		row.add(3, blackQueen);
		row.add(4, blackKing);
		row.add(5, blackBishop2);
		row.add(6, blackKnight2);
		row.add(7, blackRook2);

		// Add black pawns to row 2
		row = boardState.get(6);
		row.add(0, blackPawn1);
		row.add(1, blackPawn2);
		row.add(2, blackPawn3);
		row.add(3, blackPawn4);
		row.add(4, blackPawn5);
		row.add(5, blackPawn6);
		row.add(6, blackPawn7);
		row.add(7, blackPawn8);
	}
}