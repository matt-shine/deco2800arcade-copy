package deco2800.arcade.arcadeui.store;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;

public class Utilities {
	
	/**
	 * A little Utilities method for things that I want to share across the
	 * store. I don't know if there's a better way, but this works.
	 * @author Addison Gourluck
	 */
	static Utilities helper = new Utilities();
	
	/**
	 * This method will return the game with the name that matches most closely.
	 * 
	 * @author Addison Gourluck
	 * @param String input
	 * @return Game
	 */
	public Game search(String input) {
		if (input.length() <= 2) {
			return null; // No searches for 0, 1 or 2 chars.
		}
		input = input.toLowerCase();
		// Check if the input is a substring of a game.
		for (Game game : ArcadeSystem.getArcadeGames()) {
			if (game.name.toLowerCase().contains(input)
					|| game.id.toLowerCase().contains(input)) {
				return game;
			}
		}
		if (input.length() > 6) {
			input = input.substring(0, 6); // crop to first 6 chars for regex.
		}
		// If no results are produced yet, get desperate. Proceed to look
		// for a game that includes search, with 1 wrong/missing char.
		String regex;
		for (Game game : ArcadeSystem.getArcadeGames()) {
			for (int i = 0; i < input.length(); ++i) {
				// Super duper awesome regex, that will find any combination of
				// the string, with 1 letter missing or wrong. Really cool.
				regex = "(.*)" + input.substring(0, i) + "(.?)"
						+ input.substring(i + 1, input.length()) + "(.*)";
				if (game.name.toLowerCase().matches(regex)) {
					return game;
				}
			}
		}
		return null; // No results at all.
	}
}