package deco2800.arcade.model.test;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.model.Player;

/**
 * PlayerTestFactory is a class providing easy access to Player instances for use in
 * testing.
 * 
 * @author Leggy : Lachlan Healey : lachlan.j.healey@gmail.com
 * 
 */
public class PlayerTestFactory {

	/**
	 * Creates a new player with given ID and username
	 * 
	 * @param id
	 *            The Player's ID
	 * @param username
	 *            The Player's username
	 * @return Returns a new Player.
	 */
	public static Player createPlayer(int id, String username) {
		Player player;
		List<String> info = new ArrayList<String>();
		info.add(username);
		info.add("Rick Astley");
		info.add("rick@astley.giveyouup");
		info.add("ARTS");
		info.add("#Rickroll");
		info.add("20");
		
		ArrayList<Boolean> privset = new ArrayList<Boolean>();
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);
		privset.add(true);

		player = new Player(id, "THIS IS NOT A VALID PATH.html", info, null,
				null, null, null, privset);

		return player;
	}

}
