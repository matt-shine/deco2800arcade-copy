package deco2800.arcade.chess;

import java.util.*;

import deco2800.arcade.chess.pieces.*;

public class Board {

	FixedSizeList<FixedSizeList<Piece>> Board_State;
	// used to check if moves will move you into check without altering actual
	// board
	ArrayList<Piece> whiteGraveyard, blackGraveyard;
	// stores all moves pieces have made
	ArrayList<int[]> moves;
	ArrayList<Piece> pieceMoved;
	// true = blacks turn false = whites turn
	private boolean turn;

	// Initialise pieces
	Pawn whitePawn1, whitePawn2, whitePawn3, whitePawn4;
	Pawn whitePawn5, whitePawn6, whitePawn7, whitePawn8;
	Pawn blackPawn1, blackPawn2, blackPawn3, blackPawn4;
	Pawn blackPawn5, blackPawn6, blackPawn7, blackPawn8;
	Rook whiteRook1, whiteRook2, blackRook1, blackRook2;
	Knight whiteKnight1, whiteKnight2, blackKnight1, blackKnight2;
	Bishop whiteBishop1, whiteBishop2, blackBishop1, blackBishop2;
	King whiteKing, blackKing;
	Queen whiteQueen, blackQueen;
	Null nullPiece;

	String NEWLINE = System.getProperty("line.separator");

	boolean blackCheck, whiteCheck;

	/**
	 * Initialises board to the default setup.
	 */
	public Board() {
		turn = false;
		Board_State = new FixedSizeList<FixedSizeList<Piece>>();
		moves = new ArrayList<int[]>();
		pieceMoved = new ArrayList<Piece>();
		blackGraveyard = new ArrayList<Piece>();
		whiteGraveyard = new ArrayList<Piece>();
		nullPiece = new Null();

		blackCheck = false;
		whiteCheck = false;

		// Add 8 rows to the board
		for (int a = 0; a < 8; a++) {
			Board_State.add(a, new FixedSizeList<Piece>());
		}
		initialisePieces();
		addPieces();

		// Add null values to all other squares
		FixedSizeList<Piece> row;
		for (int c = 2; c < 6; c++) {
			row = Board_State.get(c);
			for (int d = 0; d < 8; d++) {
				row.add(d, nullPiece);
			}
		}
	}

	/**
	 * Initialises board to the given saved state
	 * 
	 * @param savedState
	 *            The saved board from a previous match
	 */
	public Board(Board savedState) {

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
	 * @return True if the team is in 'Checkmate', false otherwise.
	 */
	public boolean checkForCheckmate(boolean Team) {

		return true; // Change when implemented
	}

	private boolean checkForStaleMate(boolean Team) {

		boolean staleMate = false;
		// check that the king isn't in check, then check all the pieces to see
		// if they can move anywhere, if they can't then it's a stalemate
		if (!checkForCheck(Team) && !checkMoves().contains(true)) {
			staleMate = true;
		}
		return staleMate;
	}

	/**
	 * Deactivates the piece from play and adds it to the graveyard
	 * 
	 * @param removalPiece
	 *            Piece to be removed from play
	 */
	private void removePiece(Piece removalPiece) {

	}

	/**
	 * Reactivates the piece into play and removes it from the graveyard
	 * 
	 * @param retrievalPiece
	 *            Piece to be retrieved into play
	 */
	private void retrievePiece(Piece retrievalPiece) {

	}

	/**
	 * Checks whether a space is currently occupied
	 * 
	 * @param position
	 *            The position to be checked for occupation
	 * @return True if the space is occupied, false if available
	 */
	private boolean occupiedSpace(int[] position) {
		int x = position[0];
		int y = position[1];
		FixedSizeList<Piece> row = Board_State.get(x);
		Piece onSpace = row.get(y);

		if (onSpace.getClass() == nullPiece.getClass()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Reset the board to it's default state
	 */
	private void reset() {

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

		List<int[]> possibleMoves = piece.possibleMoves(currentPos);
		List<int[]> allowableMoves = new ArrayList<int[]>();
		/*
		 * Checks if possibleMoves spaces are occupied, if occupied by own team
		 * don't allow, if occupied by other team allow.
		 */
		// Used by: knight
		if (piece.getClass() == blackKnight1.getClass()) {
			for (int i = 0; i < possibleMoves.size(); i++) {
				// If the space is unoccupied add to list of allowable
				if (!occupiedSpace(possibleMoves.get(i))) {
					allowableMoves.add(possibleMoves.get(i));
				} else {
					// If the space is occupied check by which team
					int x = possibleMoves.get(i)[0];
					int y = possibleMoves.get(i)[1];
					List<Piece> row = Board_State.get(x);
					Piece onSquare = row.get(y);

					// If piece on the space is on opposing team add to
					// allowable
					if (piece.getTeam() != onSquare.getTeam()) {
						allowableMoves.add(possibleMoves.get(i));
					}
				}
			}
		}

		// Pawn Movement
		if (piece.getClass() == blackPawn1.getClass()) {
			for (int i = 0; i < possibleMoves.size(); i++) {
				if (occupiedSpace(possibleMoves.get(i))) {
					// If the space is occupied check by which team
					int x = possibleMoves.get(i)[0];
					int y = possibleMoves.get(i)[1];
					List<Piece> row = Board_State.get(x);
					Piece onSquare = row.get(y);
					/*
					 * If piece on the space is on opposing team add to
					 * allowable if diagonal
					 */
					if ((piece.getTeam() != onSquare.getTeam())
							&& (possibleMoves.get(i)[1] != currentPos[1])) {
						allowableMoves.add(possibleMoves.get(i));
					}
				} else { // If diagonal squares are empty don't add
					if (possibleMoves.get(i)[1] == currentPos[1]) {
						allowableMoves.add(possibleMoves.get(i));
					}
				}
			}
		}
		// Rook movement
		if (piece.getClass() == blackRook1.getClass()) {

			this.removeJumpsRook(possibleMoves, currentPos, piece);

			allowableMoves = new ArrayList<int[]>(this.removeJumpsRook(
					possibleMoves, currentPos, piece));

		}
		// King Movement
		if (piece.getClass() == whiteKing.getClass()) {

			for (int i = 0; i < possibleMoves.size(); i++) {
				// If the space is unoccupied add to list of allowable
				if (!occupiedSpace(possibleMoves.get(i))) {
					allowableMoves.add(possibleMoves.get(i));
				} else {

					// If the space is occupied check by which team
					int x = possibleMoves.get(i)[0];
					int y = possibleMoves.get(i)[1];
					List<Piece> row = Board_State.get(x);
					Piece onSquare = row.get(y);
					// If piece on the space is on opposing team add to
					// allowable
					if (piece.getTeam() != onSquare.getTeam()) {
						allowableMoves.add(possibleMoves.get(i));
					}
				}
			}
		}
		// Bishop Movement
		if (piece.getClass() == whiteBishop1.getClass()) {
			this.removeJumpsBishop(possibleMoves, currentPos, piece);
			allowableMoves = new ArrayList<int[]>(this.removeJumpsBishop(
					possibleMoves, currentPos, piece));
		}
		// Queen movement
		if (piece.getClass() == whiteQueen.getClass()) {
			this.removeJumpsQueen(possibleMoves, currentPos, piece);
			allowableMoves = new ArrayList<int[]>(this.removeJumpsQueen(
					possibleMoves, currentPos, piece));

		}
		return allowableMoves;
	}

	public boolean movePiece(Piece piece, int[] newPosition) {
		int[] oldPos = findPiece(piece);
		int x = newPosition[0];
		int y = newPosition[1];
		boolean kingCastleSwap = false;
		boolean inCheck;

		inCheck = checkForCheck(whoseTurn());

		List<int[]> allowedMoves = allowedMoves(piece);

		boolean allowed = false;
		for (int i = 0; i < allowedMoves.size(); i++) {

			if (newPosition[0] == allowedMoves.get(i)[0]
					&& newPosition[1] == allowedMoves.get(i)[1]) {
				allowed = true;
			}
		}

		// If the piece is a king or a rook
		if ((piece.getClass() == blackKing.getClass())
				|| (piece.getClass() == blackRook1.getClass())) {
			// Check if the kingCastle swap can be performed, if so allow move
			if (kingCastleSwap(piece)) {
				// Allow black teams move
				if (piece.getTeam()) {
					int[] kingSwapPos = { 7, 1 };
					int[] castleSwapPos = { 7, 2 };
					// Check they attempted the swap
					if ((piece.getClass() == blackKing.getClass())
							&& (newPosition == kingSwapPos)) {
						// Move King to new position
						Board_State.get(oldPos[0]).add(oldPos[1], nullPiece);
						Board_State.get(x).add(y, piece);
						// Move Rook to new position
						Board_State.get(findPiece(blackRook1)[0]).add(
								findPiece(blackRook1)[1], nullPiece);
						Board_State.get(castleSwapPos[0]).add(castleSwapPos[1],
								blackRook1);
						allowed = true;
						kingCastleSwap = true;
						this.nextTurn();
						return true;
					}
				}
				// Allow white teams move
				if (!piece.getTeam()) {
					// Check they attempted the swap
					int[] kingSwapPos = { 0, 1 };
					int[] castleSwapPos = { 0, 2 };
					// Check they attempted the swap
					if ((piece.getClass() == whiteKing.getClass())
							&& (newPosition[0] == kingSwapPos[0])
							&& (newPosition[1] == kingSwapPos[1])) {
						// Move King to new position
						Board_State.get(oldPos[0]).add(oldPos[1], nullPiece);
						Board_State.get(x).add(y, piece);
						// Move Rook to new position
						Board_State.get(findPiece(whiteRook1)[0]).add(
								findPiece(whiteRook1)[1], nullPiece);
						Board_State.get(castleSwapPos[0]).add(castleSwapPos[1],
								whiteRook1);
						allowed = true;
						kingCastleSwap = true;
						this.nextTurn();
						return true;
					}

				}
			}
		}

		if (!allowed) {
			System.err.println("not allowable move for " + piece + ":"
					+ newPosition[0] + ", " + newPosition[1]);
			return false;
		}

		/*
		 * If space is occupied (can only be by other team due to possibleMoves)
		 * deactivate the piece, add it to the graveyard and move new piece to
		 * that square
		 */
		if (piece.getClass() == whitePawn1.getClass()) {
			piece.hasMoved();
		}
		if (occupiedSpace(newPosition) && !kingCastleSwap) {
			// Remove enemy piece on the newPosition
			Piece onSquare = Board_State.get(x).get(y);
			onSquare.deActivate();

			if (onSquare.getTeam()) {
				blackGraveyard.add(onSquare);
			} else {
				whiteGraveyard.add(onSquare);
			}
			Board_State.get(oldPos[0]).add(oldPos[1], nullPiece);
			Board_State.get(x).add(y, piece);
			moves.add(newPosition);
			pieceMoved.add(piece);
			inCheck = checkForCheck(whoseTurn());
			// revert the move if in check
			if (inCheck) {
				onSquare.reActivate();
				if (onSquare.getTeam()) {
					blackGraveyard.remove(onSquare);
				} else {
					whiteGraveyard.remove(onSquare);
				}
				Board_State.get(oldPos[0]).add(oldPos[1], piece);
				Board_State.get(x).add(y, onSquare);
				removeMove();
				return false;
			}
			this.nextTurn();
			return true;
		} else {
			Board_State.get(oldPos[0]).add(oldPos[1], nullPiece);
			Board_State.get(x).add(y, piece);
			moves.add(newPosition);
			pieceMoved.add(piece);
			inCheck = checkForCheck(whoseTurn());
			if (inCheck) {
				// revert move if in check
				Board_State.get(oldPos[0]).add(oldPos[1], piece);
				Board_State.get(x).add(y, nullPiece);
				removeMove();
				return false;
			}
			this.nextTurn();
			return true;
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
			FixedSizeList<Piece> row = Board_State.get(i);
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
	 * Undos last move
	 */
	private void removeMove() {
		moves.remove(moves.size() - 1);
		pieceMoved.remove(pieceMoved.size() - 1);
	}

	/**
	 * Finds and returns a list of all active pieces currently on the board
	 * 
	 * @return A list of all currently active pieces on the board
	 */
	public List<Piece> findActivePieces() {
		List<Piece> activePieces = new ArrayList<Piece>();

		for (int i = 0; i < 8; i++) {
			FixedSizeList<Piece> row = Board_State.get(i);
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

	public String toString() {
		return Board_State.toString();
	}

	/**
	 * Checks directions bishop can move and all removes jumps from possible
	 * moves
	 */
	private List<int[]> removeJumpsBishop(List<int[]> possibleMoves,
			int[] currentPos, Piece piece) {
		int x = currentPos[0];
		int y = currentPos[1];
		int xCheck;
		int yCheck;
		List<int[]> possible = new ArrayList<int[]>(possibleMoves);

		// remove jumps for moving UP and RIGHT
		for (int i = 1; i <= 7; i++) {
			int[] upRight = { x + i, y + i };
			if ((upRight[0] >= 0) && (upRight[0] <= 7) && (upRight[1] >= 0)
					&& (upRight[1] <= 7)) {

				// int g = possibleMoves.get(i)[0];
				// int f = possibleMoves.get(i)[1];
				// if space is not occupied continue
				if (!occupiedSpace(upRight)) {
					continue;

				} else {
					List<Piece> row = Board_State.get(x + i);
					Piece onSquare = row.get(y + i);
					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {
						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x + i + j))
										&& (yCheck == (y + i + j))) {
									possible.remove(k);
								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x + i + j))
										&& (yCheck == (y + i + j))) {
									possible.remove(k);
								}
							}
						}

						break;
					}
				}
			}
		}

		// remove jumps for moving UP and LEFT
		for (int i = 1; i <= 7; i++) {

			int[] upLeft = { x + i, y - i };
			if ((upLeft[0] >= 0) && (upLeft[0] <= 7) && (upLeft[1] >= 0)
					&& (upLeft[1] <= 7)) {
				// if space is not occupied continue
				if (!occupiedSpace(upLeft)) {
					continue;
				} else {
					List<Piece> row = Board_State.get(x + i);
					Piece onSquare = row.get(y - i);
					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {
						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x + i + j))
										&& (yCheck == (y - i - j))) {
									possible.remove(k);
								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x + i + j))
										&& (yCheck == (y - i - j))) {
									possible.remove(k);
								}
							}
						}
						break;
					}
				}
			}
		}

		// remove jumps for moving DOWN and RIGHT
		for (int i = 1; i <= 7; i++) {
			/**/

			int[] downRight = { x - i, y + i };
			if ((downRight[0] >= 0) && (downRight[0] <= 7)
					&& (downRight[1] >= 0) && (downRight[1] <= 7)) {
				// if space is not occupied continue
				if (!occupiedSpace(downRight)) {
					continue;
				} else {
					List<Piece> row = Board_State.get(x - i);
					Piece onSquare = row.get(y + i);
					// remove possible moves AFTER enemy piece onwards in
					// direction

					if (piece.getTeam() != onSquare.getTeam()) {

						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x - i - j))
										&& (yCheck == (y + i + j))) {
									possible.remove(k);
								}
							}
						}

						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x - i - j))
										&& (yCheck == (y + i + j))) {
									possible.remove(k);
								}
							}
						}
						break;
					}
				}
			}
		}

		// remove jumps for moving DOWN and LEFT
		for (int i = 1; i <= 7; i++) {

			int[] downLeft = { x - i, y - i };
			if ((downLeft[0] >= 0) && (downLeft[0] <= 7) && (downLeft[1] >= 0)
					&& (downLeft[1] <= 7)) {
				// if space is not occupied continue
				if (!occupiedSpace(downLeft)) {
					continue;
				} else {
					List<Piece> row = Board_State.get(x - i);
					Piece onSquare = row.get(y - i);
					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {
						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x - i - j))
										&& (yCheck == (y - i - j))) {
									possible.remove(k);
								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x - i - j))
										&& (yCheck == (y - i - j))) {
									possible.remove(k);
								}
							}
						}
						break;
					}
				}
			}
		}
		/*
		 * for(int i=0;i<possibleMoves.size();i++) {
		 */

		return possible;

	}

	/**
	 * Returns a list with all impossible jumps removed from the rooks possible
	 * moves.
	 * 
	 * @param possibleMoves
	 *            List of all possible moves the rook can make, not disregarding
	 *            disallowed moves
	 * @param currentPos
	 *            The current position of the given rook
	 * @param piece
	 *            The rook for whom the moves are being removed from
	 * @return A list of all possible moves the rook can make without the
	 *         impossible jumps included in possibleMoves
	 */

	private List<int[]> removeJumpsRook(List<int[]> possibleMoves,
			int[] currentPos, Piece piece) {
		int x = currentPos[0];
		int y = currentPos[1];
		int xCheck;
		int yCheck;
		List<int[]> possible = new ArrayList<int[]>(possibleMoves);

		// remove jumps for moving UP
		for (int i = 1; i <= 7; i++) {

			int[] up = { x + i, y };
			if ((up[0] >= 0) && (up[0] <= 7) && (up[1] >= 0) && (up[1] <= 7)) {

				if (!occupiedSpace(up)) {
					continue;

				} else {

					List<Piece> row = Board_State.get(x + i);
					Piece onSquare = row.get(y);

					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {
						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];

								yCheck = possible.get(k)[1];
								if ((xCheck == (x + i + j)) && (yCheck == (y))) {
									possible.remove(k);

								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x + i + j)) && (yCheck == (y))) {
									possible.remove(k);

								}
							}
						}
						break;
					}
				}
			}
		}

		// remove jumps for moving LEFT
		for (int i = 1; i <= 7; i++) {

			int[] Left = { x, y - i };
			if ((Left[0] >= 0) && (Left[0] <= 7) && (Left[1] >= 0)
					&& (Left[1] <= 7)) {
				// if space is not occupied continue
				if (!occupiedSpace(Left)) {
					continue;
				} else {
					List<Piece> row = Board_State.get(x);
					Piece onSquare = row.get(y - i);
					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {
						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x) && (yCheck == (y - i - j)))) {
									possible.remove(k);
								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x) && (yCheck == (y - i - j)))) {
									possible.remove(k);
								}
							}
						}
						break;
					}
				}
			}
		}

		// remove jumps for moving RIGHT
		for (int i = 1; i <= 7; i++) {
			int[] Right = { x, y + i };
			if ((Right[0] >= 0) && (Right[0] <= 7) && (Right[1] >= 0)
					&& (Right[1] <= 7)) {
				// if space is not occupied continue
				if (!occupiedSpace(Right)) {
					continue;
				} else {
					List<Piece> row = Board_State.get(x);
					Piece onSquare = row.get(y + i);
					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {
						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x) && (yCheck == (y + i + j)))) {
									possible.remove(k);
								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x) && (yCheck == (y + i + j)))) {
									possible.remove(k);

								}
							}
						}
						break;
					}
				}
			}
		}

		// remove jumps for moving DOWN
		for (int i = 1; i <= 7; i++) {
			int[] down = { x - i, y };
			if ((down[0] >= 0) && (down[0] <= 7) && (down[1] >= 0)
					&& (down[1] <= 7)) {

				// if space is not occupied continue
				if (!occupiedSpace(down)) {
					continue;
				} else {
					List<Piece> row = Board_State.get(x - i);
					Piece onSquare = row.get(y);

					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {

						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x - i - j)) && (yCheck == (y))) {
									possible.remove(k);
								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x - i - j)) && (yCheck == (y))) {
									possible.remove(k);

								}
							}
						}

						break;
					}
				}
			}
		}

		return possible;

	}

	private List<int[]> removeJumpsQueen(List<int[]> possibleMoves,
			int[] currentPos, Piece piece) {

		int x = currentPos[0];
		int y = currentPos[1];
		int xCheck;
		int yCheck;
		List<int[]> possible = new ArrayList<int[]>(possibleMoves);

		// remove jumps for moving UP
		for (int i = 1; i <= 7; i++) {

			int[] up = { x + i, y };
			if ((up[0] >= 0) && (up[0] <= 7) && (up[1] >= 0) && (up[1] <= 7)) {

				if (!occupiedSpace(up)) {
					continue;

				} else {

					List<Piece> row = Board_State.get(x + i);
					Piece onSquare = row.get(y);

					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {
						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];

								yCheck = possible.get(k)[1];
								if ((xCheck == (x + i + j)) && (yCheck == (y))) {
									possible.remove(k);

								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x + i + j)) && (yCheck == (y))) {
									possible.remove(k);

								}
							}
						}
						break;
					}
				}
			}
		}

		// remove jumps for moving LEFT
		for (int i = 1; i <= 7; i++) {

			int[] Left = { x, y - i };
			if ((Left[0] >= 0) && (Left[0] <= 7) && (Left[1] >= 0)
					&& (Left[1] <= 7)) {
				// if space is not occupied continue
				if (!occupiedSpace(Left)) {
					continue;
				} else {
					List<Piece> row = Board_State.get(x);
					Piece onSquare = row.get(y - i);
					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {
						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x) && (yCheck == (y - i - j)))) {
									possible.remove(k);
								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x) && (yCheck == (y - i - j)))) {
									possible.remove(k);
								}
							}
						}
						break;
					}
				}
			}
		}

		// remove jumps for moving RIGHT
		for (int i = 1; i <= 7; i++) {
			int[] Right = { x, y + i };
			if ((Right[0] >= 0) && (Right[0] <= 7) && (Right[1] >= 0)
					&& (Right[1] <= 7)) {
				// if space is not occupied continue
				if (!occupiedSpace(Right)) {
					continue;
				} else {
					List<Piece> row = Board_State.get(x);
					Piece onSquare = row.get(y + i);
					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {
						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x) && (yCheck == (y + i + j)))) {
									possible.remove(k);
								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x) && (yCheck == (y + i + j)))) {
									possible.remove(k);

								}
							}
						}
						break;
					}
				}
			}
		}

		// remove jumps for moving DOWN
		for (int i = 1; i <= 7; i++) {
			int[] down = { x - i, y };
			if ((down[0] >= 0) && (down[0] <= 7) && (down[1] >= 0)
					&& (down[1] <= 7)) {

				// if space is not occupied continue
				if (!occupiedSpace(down)) {
					continue;
				} else {
					List<Piece> row = Board_State.get(x - i);
					Piece onSquare = row.get(y);

					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {

						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x - i - j)) && (yCheck == (y))) {
									possible.remove(k);
								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x - i - j)) && (yCheck == (y))) {
									possible.remove(k);

								}
							}
						}

						break;
					}
				}
			}
		}
		for (int i = 1; i <= 7; i++) {
			int[] upRight = { x + i, y + i };
			if ((upRight[0] >= 0) && (upRight[0] <= 7) && (upRight[1] >= 0)
					&& (upRight[1] <= 7)) {

				// int g = possibleMoves.get(i)[0];
				// int f = possibleMoves.get(i)[1];
				// if space is not occupied continue
				if (!occupiedSpace(upRight)) {
					continue;

				} else {
					List<Piece> row = Board_State.get(x + i);
					Piece onSquare = row.get(y + i);
					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {
						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x + i + j))
										&& (yCheck == (y + i + j))) {
									possible.remove(k);
								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x + i + j))
										&& (yCheck == (y + i + j))) {
									possible.remove(k);
								}
							}
						}

						break;
					}
				}
			}
		}

		// remove jumps for moving UP and LEFT
		for (int i = 1; i <= 7; i++) {

			int[] upLeft = { x + i, y - i };
			if ((upLeft[0] >= 0) && (upLeft[0] <= 7) && (upLeft[1] >= 0)
					&& (upLeft[1] <= 7)) {
				// if space is not occupied continue
				if (!occupiedSpace(upLeft)) {
					continue;
				} else {
					List<Piece> row = Board_State.get(x + i);
					Piece onSquare = row.get(y - i);
					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {
						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x + i + j))
										&& (yCheck == (y - i - j))) {
									possible.remove(k);
								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x + i + j))
										&& (yCheck == (y - i - j))) {
									possible.remove(k);
								}
							}
						}
						break;
					}
				}
			}
		}

		// remove jumps for moving DOWN and RIGHT
		for (int i = 1; i <= 7; i++) {
			/**/

			int[] downRight = { x - i, y + i };
			if ((downRight[0] >= 0) && (downRight[0] <= 7)
					&& (downRight[1] >= 0) && (downRight[1] <= 7)) {
				// if space is not occupied continue
				if (!occupiedSpace(downRight)) {
					continue;
				} else {
					List<Piece> row = Board_State.get(x - i);
					Piece onSquare = row.get(y + i);
					// remove possible moves AFTER enemy piece onwards in
					// direction

					if (piece.getTeam() != onSquare.getTeam()) {

						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x - i - j))
										&& (yCheck == (y + i + j))) {
									possible.remove(k);
								}
							}
						}

						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x - i - j))
										&& (yCheck == (y + i + j))) {
									possible.remove(k);
								}
							}
						}
						break;
					}
				}
			}
		}

		// remove jumps for moving DOWN and LEFT
		for (int i = 1; i <= 7; i++) {

			int[] downLeft = { x - i, y - i };
			if ((downLeft[0] >= 0) && (downLeft[0] <= 7) && (downLeft[1] >= 0)
					&& (downLeft[1] <= 7)) {
				// if space is not occupied continue
				if (!occupiedSpace(downLeft)) {
					continue;
				} else {
					List<Piece> row = Board_State.get(x - i);
					Piece onSquare = row.get(y - i);
					// remove possible moves AFTER enemy piece onwards in
					// direction
					if (piece.getTeam() != onSquare.getTeam()) {
						for (int j = 1; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x - i - j))
										&& (yCheck == (y - i - j))) {
									possible.remove(k);
								}
							}
						}
						break;
						// remove possible moves FROM ally piece onwards in
						// direction
					} else {
						for (int j = 0; j <= 7; j++) {
							for (int k = 0; k < possible.size(); k++) {
								xCheck = possible.get(k)[0];
								yCheck = possible.get(k)[1];
								if ((xCheck == (x - i - j))
										&& (yCheck == (y - i - j))) {
									possible.remove(k);
								}
							}
						}
						break;
					}
				}
			}
		}
		return possible;
	}

	public Piece getPiece(int[] pos) {
		FixedSizeList<Piece> row = Board_State.get(pos[0]);

		return row.get(pos[1]);
	}

	public boolean isNullPiece(Piece piece) {
		if (piece.getClass() == nullPiece.getClass()) {
			return true;
		}
		return false;
	}

	public boolean whoseTurn() {
		return turn;
	}

	public void nextTurn() {
		turn = !turn;
	}

	/**
	 * Checks all active pieces on the board to see whether any pieces can move
	 * and returns a list of boolean values
	 */
	public List<Boolean> checkMoves() {
		List<Piece> activePieces = findActivePieces();
		List<Boolean> canMove = new ArrayList<Boolean>();

		for (int i = 0; i < activePieces.size(); i++) {
			if (allowedMoves(activePieces.get(i)).isEmpty()) {
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
	 *            Piece who's team is to be checked
	 * @return True if piece's team can perform the move, false otherwise
	 */
	private boolean kingCastleSwap(Piece piece) {
		// If on the black team and king and rook1 haven't moved
		if (piece.getTeam() && !blackKing.getFirstMove()
				&& !blackRook1.getFirstMove()) {
			int[] middleSquare1 = { 7, 1 };
			int[] middleSquare2 = { 7, 2 };
			// If both middle squares are empty
			if (!occupiedSpace(middleSquare1) && !occupiedSpace(middleSquare2)) {
				return true;
			}
		}

		// If on the white team and king and rook1 haven't moved
		if (!piece.getTeam() && !whiteKing.getFirstMove()
				&& !whiteRook1.getFirstMove()) {
			int[] middleSquare1 = { 0, 1 };
			int[] middleSquare2 = { 0, 2 };
			// If both middle squares are empty
			if (!occupiedSpace(middleSquare1) && !occupiedSpace(middleSquare2)) {
				return true;
			}
		}
		// Required conditions aren't met
		return false;
	}

	/**
	 * Initialises all pieces on the board
	 */
	private void initialisePieces() {
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
		FixedSizeList<Piece> row = Board_State.get(0);
		row.add(0, whiteRook1);
		row.add(1, whiteKnight1);
		row.add(2, whiteBishop1);
		row.add(3, whiteQueen);
		row.add(4, whiteKing);
		row.add(5, whiteBishop2);
		row.add(6, whiteKnight2);
		row.add(7, whiteRook2);

		// Add white pawns to row 2
		row = Board_State.get(1);
		row.add(0, whitePawn1);
		row.add(1, whitePawn2);
		row.add(2, whitePawn3);
		row.add(3, whitePawn4);
		row.add(4, whitePawn5);
		row.add(5, whitePawn6);
		row.add(6, whitePawn7);
		row.add(7, whitePawn8);

		// Add black pieces to row 1
		row = Board_State.get(7);
		row.add(0, blackRook1);
		row.add(1, blackKnight1);
		row.add(2, blackBishop1);
		row.add(3, blackQueen);
		row.add(4, blackKing);
		row.add(5, blackBishop2);
		row.add(6, blackKnight2);
		row.add(7, blackRook2);

		// Add black pawns to row 2
		row = Board_State.get(6);
		row.add(0, blackPawn1);
		row.add(1, blackPawn2);
		row.add(2, blackPawn3);
		row.add(3, blackPawn4);
		row.add(4, blackPawn5);
		row.add(5, blackPawn6);
		row.add(6, blackPawn7);
		row.add(7, blackPawn8);
	}

	/**
	 * Basic test that moves pieces for to compare to actual board
	 */
	private void micksTest() {
		int[] a = { 2, 2 };
		int[] b = { 4, 7 };
		int[] c = { 2, 1 };
		int[] d = { 5, 7 };
		int[] e = { 2, 0 };
		int[] f = { 4, 4 };
		int[] g = { 3, 7 };
		int[] h = { 5, 4 };
		int[] i = { 0, 2 };
		int[] j = { 2, 1 };

		movePiece(whiteKnight1, a);
		movePiece(blackPawn8, b);
		movePiece(whitePawn2, c);
		movePiece(blackRook2, d);
		movePiece(whiteBishop1, e);
		movePiece(blackPawn5, f);
		movePiece(whitePawn8, g);
		movePiece(blackQueen, h);
		movePiece(whiteKing, i);
		movePiece(blackQueen, j);

		System.out.println(Board_State);
		System.out.println(blackGraveyard);
		System.out.println(whiteGraveyard);

	}

}