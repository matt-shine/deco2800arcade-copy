package deco2800.arcade.arcadeui.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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
	 * This method will add all of the images from the logos/ folder into the
	 * skin given to it. If any of them are missing, default is laoded instead.
	 * 
	 * @author Addison Gourluck
	 * @param Skin skin
	 */
	public void loadIcons(Skin skin) {
		// Load the logo for all of the games (or default), and store them
		// in the skin, with their name as their key.
		for (Game gamename : ArcadeSystem.getArcadeGames()) {
			try {
				skin.add(gamename.id, new Texture(Gdx.files.internal("logos/"
						+ gamename.id.toLowerCase() + ".png")));
			} catch (Exception e) {
				skin.add(gamename.id, new Texture
						(Gdx.files.internal("logos/default.png")));
			}
		}
	}
	
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