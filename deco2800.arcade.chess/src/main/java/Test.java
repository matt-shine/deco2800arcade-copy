import java.util.List;

import deco2800.arcade.chess.Board;
import deco2800.arcade.chess.Chess;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.NetworkException;
import deco2800.arcade.model.Player;


public class Test {

	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	
	public static void main(String[] args) throws NetworkException {
		Board board = new Board();
		
		List<int[]> allowed = board.allowedMoves(board.blackPawn1);
		
		//System.out.println(board.blackPawn1);
		for(int[] a : allowed) {
			//System.out.println("[" + a[0] + "," + a[1] + "]");
		}
		
		System.out.println(board.blackPawn1);
		
		List<int[]> allowed2 = board.blackPawn1.possibleMoves(board.findPiece(board.blackPawn1), board.Board_State);
		for(int[] a : allowed2) {
			System.out.println("[" + a[0] + "," + a[1] + "]");
		}
		
	}

}
