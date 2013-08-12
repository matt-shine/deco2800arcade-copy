package deco2800.arcade.client;

import java.util.Set;

public class ArcadeSystem {
	
	private static Arcade arcade = null;
	
	public static void setArcadeInstance(Arcade a) {
		arcade = a;
	}
	
	public static void goToGame(String gameid) {
		arcade.stopDashboard();
		arcade.startGame(gameid);
	}
	
	public static void openConnection() {
		try {
			arcade.connectToServer();
			System.out.println("connected");
		} catch (ArcadeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Set<String> getGamesList() {
		return arcade.findGameIds();
	}
	
	public static void login(String username) {
		arcade.connectAsUser(username);
	}
	
	
}
