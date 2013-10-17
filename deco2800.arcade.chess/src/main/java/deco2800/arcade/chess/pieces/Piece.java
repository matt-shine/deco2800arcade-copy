package deco2800.arcade.chess.pieces;

import deco2800.arcade.chess.*;

import java.util.*;
public abstract class Piece {
	boolean team;
	boolean firstMove;
	boolean active;
	int preference;
	int pieceNo;
	
	public Piece(boolean team, int pieceNo) {
		this.team = team;
		this.firstMove = false;
		this.active = true;
		this.pieceNo = pieceNo;
	}
	
	/**
	 * Returns a list of all possible moves that the piece can make
	 * 
	 * @return
	 * 		List of the locations of all possible moves.
	 */
	public abstract List<int[]> possibleMoves(int[] currentPos, FixedSizeList<FixedSizeList<Piece>> board_state);
	
	/**
	 * Remove the piece from gameplay and send to the graveyard
	 */
	public void deActivate() {
		this.active = false;
	}
	
	/**
	 * Add the piece back into gameplay and remove from the graveyard
	 */
	public void reActivate() {
		this.active = true;
	}
	/**
	 * Takes in a piece and returns it's team as a boolean
	 * 		- False = white team
	 * 		- True = black team 
	 * 
	 * @param piece
	 * 		The instance of the piece to check
	 * @return
	 * 		False if on the white team, true if on the black team
	 */
	public boolean getTeam() {
		return this.team;
	}
	
	/**
	 * Takes in a piece and returns a boolean based on whether it's taken it's
	 * first move
	 * 
	 * @param piece
	 * 		The instance of the piece to check
	 * @return
	 * 		True if piece has moved, false otherwise
	 */
	public boolean getFirstMove() {
		return this.firstMove;
	}
	
	/**
	 * Takes in a piece and returns a boolean based on whether the piece is 
	 * still active or not
	 * 
	 * @param piece
	 * 		The instance of the piece to check
	 * @return
	 * 		True if the piece is still in play, false otherwise
	 */
	public boolean getActiveState() {
		return this.active;
	}
	
	/**
	 * Takes in a piece and returns it's numerical preference
	 * 
	 * @param piece
	 * 		The instance of the piece to check
	 * @return
	 * 		The integer value of the pieces preference
	 */
	public int getPreference() {
		return this.preference;
	}

	/**
	 * Updates piece to say it has moved
	 */
	public void hasMoved() {
		this.firstMove = true;
	}
	
	/**
	 * Checks whether a space is currently occupied
	 * 
	 * @param position
	 *            The position to be checked for occupation
	 * @return True if the space is occupied, false if available
	 */
	public boolean occupiedSpace(FixedSizeList<FixedSizeList<Piece>> board_state, int[] position) {
		int x = position[0];
		int y = position[1];
		FixedSizeList<Piece> row = board_state.get(x);
		Piece onSpace = row.get(y);

		Piece nullPiece = new Null(true);;
		if (onSpace.getClass() == nullPiece .getClass()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a list with all impossible diagonal jumps removed from the possible
	 * moves.
	 * 
	 * @param possibleMoves
	 *            List of all possible moves the piece can make, not
	 *            disregarding disallowed moves
	 * @param currentPos
	 *            The current position of the given piece
	 * @param piece
	 *            The piece for whom the moves are being removed from
	 * @return A list of all possible moves the piece can make without the
	 *         impossible jumps included in possibleMoves
	 */
	public List<int[]> removeJumpsDiagonal(List<int[]> possibleMoves,
			int[] currentPos, FixedSizeList<FixedSizeList<Piece>> board_state) {
		Piece whiteBishop1 = new Bishop(false,1);
		Piece whiteQueen = new Queen(false);
		int x = currentPos[0];
		int y = currentPos[1];
		int xCheck;
		int yCheck;
		List<int[]> possible = new ArrayList<int[]>(possibleMoves);
		if (this.getClass() == whiteBishop1.getClass()
				|| this.getClass() == whiteQueen.getClass()) {
			for (int i = 1; i <= 7; i++) {
				int[] upRight = { x + i, y + i };
				if ((upRight[0] >= 0) && (upRight[0] <= 7) && (upRight[1] >= 0)
						&& (upRight[1] <= 7)) {
					if (!occupiedSpace(board_state, upRight)) {
						continue;
					} else {
						List<Piece> row = board_state.get(x + i);
						Piece onSquare = row.get(y + i);
						// remove possible moves AFTER enemy piece onwards
						if (getTeam() != onSquare.getTeam()) {
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
							// remove possible moves FROM ally piece onwards
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
					if (!occupiedSpace(board_state, upLeft)) {
						continue;
					} else {
						List<Piece> row = board_state.get(x + i);
						Piece onSquare = row.get(y - i);
						// remove possible moves AFTER enemy piece onwards
						if (getTeam() != onSquare.getTeam()) {
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
							// remove possible moves FROM ally piece onwards
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
				int[] downRight = { x - i, y + i };
				if ((downRight[0] >= 0) && (downRight[0] <= 7)
						&& (downRight[1] >= 0) && (downRight[1] <= 7)) {
					if (!occupiedSpace(board_state, downRight)) {
						continue;
					} else {
						List<Piece> row = board_state.get(x - i);
						Piece onSquare = row.get(y + i);
						// remove possible moves AFTER enemy piece onwards
						if (getTeam() != onSquare.getTeam()) {
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
							// remove possible moves FROM ally piece onwards
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
				if ((downLeft[0] >= 0) && (downLeft[0] <= 7)
						&& (downLeft[1] >= 0) && (downLeft[1] <= 7)) {
					if (!occupiedSpace(board_state, downLeft)) {
						continue;
					} else {
						List<Piece> row = board_state.get(x - i);
						Piece onSquare = row.get(y - i);
						// remove possible moves AFTER enemy piece onwards
						if (getTeam() != onSquare.getTeam()) {
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
							// remove possible moves FROM ally piece onwards
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
		}
		return possible;
	}
	
	/**
	 * Returns a list with all impossible up jumps removed from the possible
	 * moves.
	 * 
	 * @param possibleMoves
	 *            List of all possible moves the piece can make, not
	 *            disregarding disallowed moves
	 * @param currentPos
	 *            The current position of the given piece
	 * @param piece
	 *            The piece for whom the moves are being removed from
	 * @return A list of all possible moves the piece can make without the
	 *         impossible jumps included in possibleMoves
	 */
	public List<int[]> removeJumpsUp(List<int[]> possibleMoves,
			int[] currentPos, FixedSizeList<FixedSizeList<Piece>> board_state) {
		Piece whiteRook1 = new Rook(false,1);
		Piece whiteQueen = new Queen(false);
		int x = currentPos[0];
		int y = currentPos[1];
		int xCheck;
		int yCheck;
		List<int[]> possible = new ArrayList<int[]>(possibleMoves);
		if (this.getClass() == whiteRook1.getClass()
				|| getClass() == whiteQueen.getClass()) {
			// remove jumps UP
			for (int i = 1; i <= 7; i++) {

				int[] up = { x + i, y };
				if ((up[0] >= 0) && (up[0] <= 7) && (up[1] >= 0)
						&& (up[1] <= 7)) {

					if (!occupiedSpace(board_state, up)) {
						continue;

					} else {

						List<Piece> row = board_state.get(x + i);
						Piece onSquare = row.get(y);

						// remove possible moves AFTER enemy piece onwards
						if (getTeam() != onSquare.getTeam()) {
							for (int j = 1; j <= 7; j++) {
								for (int k = 0; k < possible.size(); k++) {
									xCheck = possible.get(k)[0];

									yCheck = possible.get(k)[1];
									if ((xCheck == (x + i + j))
											&& (yCheck == (y))) {
										possible.remove(k);

									}
								}
							}
							break;
							// remove possible moves FROM ally piece onwards
						} else {
							for (int j = 0; j <= 7; j++) {
								for (int k = 0; k < possible.size(); k++) {
									xCheck = possible.get(k)[0];
									yCheck = possible.get(k)[1];
									if ((xCheck == (x + i + j))
											&& (yCheck == (y))) {
										possible.remove(k);

									}
								}
							}
							break;
						}
					}
				}
			}

			// remove jumps LEFT
			for (int i = 1; i <= 7; i++) {
				int[] Left = { x, y - i };
				if ((Left[0] >= 0) && (Left[0] <= 7) && (Left[1] >= 0)
						&& (Left[1] <= 7)) {
					if (!occupiedSpace(board_state, Left)) {
						continue;
					} else {
						List<Piece> row = board_state.get(x);
						Piece onSquare = row.get(y - i);
						// remove possible moves AFTER enemy piece onwards
						if (getTeam() != onSquare.getTeam()) {
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

			// remove jumps RIGHT
			for (int i = 1; i <= 7; i++) {
				int[] Right = { x, y + i };
				if ((Right[0] >= 0) && (Right[0] <= 7) && (Right[1] >= 0)
						&& (Right[1] <= 7)) {
					if (!occupiedSpace(board_state, Right)) {
						continue;
					} else {
						List<Piece> row = board_state.get(x);
						Piece onSquare = row.get(y + i);
						// remove possible moves AFTER enemy piece onwards
						if (getTeam() != onSquare.getTeam()) {
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
							// remove possible moves FROM ally piece onwards
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
			// remove jumps downwards
			for (int i = 1; i <= 7; i++) {
				int[] down = { x - i, y };
				if ((down[0] >= 0) && (down[0] <= 7) && (down[1] >= 0)
						&& (down[1] <= 7)) {

					// if space is not occupied continue
					if (!occupiedSpace(board_state, down)) {
						continue;
					} else {
						List<Piece> row = board_state.get(x - i);
						Piece onSquare = row.get(y);
						// remove possible moves AFTER enemy piece onwards
						if (getTeam() != onSquare.getTeam()) {

							for (int j = 1; j <= 7; j++) {
								for (int k = 0; k < possible.size(); k++) {
									xCheck = possible.get(k)[0];
									yCheck = possible.get(k)[1];
									if ((xCheck == (x - i - j))
											&& (yCheck == (y))) {
										possible.remove(k);
									}
								}
							}
							break;
							// remove possible moves FROM ally piece onwards
						} else {
							for (int j = 0; j <= 7; j++) {
								for (int k = 0; k < possible.size(); k++) {
									xCheck = possible.get(k)[0];
									yCheck = possible.get(k)[1];
									if ((xCheck == (x - i - j))
											&& (yCheck == (y))) {
										possible.remove(k);
									}
								}
							}
							break;
						}
					}
				}
			}
		}
		if (getClass() == whiteRook1.getClass()) {
			return possible;
		} else {
			return removeJumpsDiagonal(possible, currentPos, board_state);
		}
	}
}
