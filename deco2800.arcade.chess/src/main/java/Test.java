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
		
		System.out.println(board.blackPawn1);
		for(int[] a : allowed) {
			System.out.println("[" + a[0] + "," + a[1] + "]");
		}
		
		List<int[]> allowed2 = board.allowedMoves(board.blackKnight1);
		
		System.out.println(board.blackKnight1.getPreference());
		for(int[] a : allowed) {
			System.out.println("[" + a[0] + "," + a[1] + "]");
		}
		
	}

}
