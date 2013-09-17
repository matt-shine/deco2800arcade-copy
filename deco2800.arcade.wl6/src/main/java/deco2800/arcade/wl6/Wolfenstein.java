package deco2800.arcade.wl6;

import java.awt.EventQueue;

import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeException;
import deco2800.arcade.client.ArcadeSystem;

public class Wolfenstein {
	
	/**
	 * For convenience.
	 * @param args
	 */
	public static void main(String[] args) {
		final Arcade arcade = new Arcade(args);

		ArcadeSystem.setArcadeInstance(arcade);

		arcade.addCanvas();
		
		try {
			arcade.connectToServer();
		} catch (ArcadeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				arcade.connectAsUser("wolfTest");
				
				ArcadeSystem.goToGame("wolfenstein");
			}
		});
		

	}
	
}
