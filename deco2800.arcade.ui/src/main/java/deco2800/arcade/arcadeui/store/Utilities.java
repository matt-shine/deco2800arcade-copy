package deco2800.arcade.arcadeui.store;

import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;

public class Utilities {
	
	/**
	 * A little Utilities method for things that I want to share across the store.
	 * I don't know if there's a better way, but this works. Be gentle marking me?
	 * @author Addison Gourluck
	 */
	static Utilities helper = new Utilities();
	
	/**
	 * This method will add all of the images from the 'logos' folder into the
	 * skin given to it. If any of them are missing, default is loaded instead.
	 * 
	 * @author Addison Gourluck
	 * @param Skin skin
	 */
	public void loadIcons(Skin skin) {
		// Load the logo for all of the games (or default), and store them
		// in the skin, with their name as their key.
		Set<Game> gameslist = null;
		try {
			gameslist = ArcadeSystem.getArcadeGames();
		} catch (Exception e) {
			// Could not load arcade game. Critical error.
			// Wait, and retry it, since this is REALLY important.
			try {
				Thread.sleep(1000); // Zzzzzzz *snore*
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			gameslist = ArcadeSystem.getArcadeGames();
			// If it fails again, bad luck. Restart arcade. Should fix it.
		}
		
		for (Game gamename : gameslist) {
			try {
				// Search for png file with name equal to the game's id.
				skin.add(gamename.id, new Texture(Gdx.files.internal("logos/"
						+ gamename.id.toLowerCase() + ".png")));
			} catch (Exception e) {
				// Loading file failed, load default instead.
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
		String search = input.toLowerCase();
		// Check if the input is a substring of a game.
		for (Game game : ArcadeSystem.getArcadeGames()) {
			if (game.name.toLowerCase().contains(search)
					|| game.id.toLowerCase().contains(search)) {
				return game;
			}
		}
		if (search.length() > 6) {
			search = search.substring(0, 6); // crop to first 6 chars for regex.
		}
		// If no results are produced yet, get desperate. Proceed to look
		// for a game that includes search, with 1 wrong/missing char.
		String regex;
		for (Game game : ArcadeSystem.getArcadeGames()) {
			for (int i = 0; i < search.length(); ++i) {
				// Super duper awesome regex, that will find any combination of
				// the string, with 1 letter missing or wrong. Really cool.
				regex = "(.*)" + search.substring(0, i) + "(.?)"
						+ search.substring(i + 1, search.length()) + "(.*)";
				if (game.name.toLowerCase().matches(regex)) {
					return game;
				}
			}
		}
		return null; // No results at all.
	}
}