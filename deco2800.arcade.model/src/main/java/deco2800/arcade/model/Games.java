package deco2800.arcade.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Games is an abstraction of a Player's Games List.
 * 
 * @author iamwinrar
 * 
 */
public class Games {

	private Set<Game> games;

	/**
	 * Creates a new Games.
	 */
	public Games() {
		this.games = new HashSet<Game>();
	}

	/**
	 * Created a new Games given an existing Games, copying the entries.
	 * 
	 * @param g
	 *            The Games instance to be copied.
	 */
	public Games(Games g) {
		this.games = new HashSet<Game>(g.games);
	}

	/**
	 * Access method for a Set representation of this.
	 * 
	 * @return Returns a Set representation of this.
	 */
	public Set<Game> getSet() {
		return new HashSet<Game>(games);
	}

	/**
	 * Adds a Game to this.
	 * 
	 * @param game
	 *            The Game to be added.
	 */
	public void add(Game game) {
		this.games.add(game);
	}

	/**
	 * Removes a Game from this.
	 * 
	 * @param game
	 *            The Game to be removed.
	 */
	public void remove(Game game) {
		this.games.remove(game);
	}

	/**
	 * Checks if a Game is in this.
	 * 
	 * @param gamer
	 *            The Game to check.
	 * @return Returns true if the Game is in this, false otherwise.
	 */
	public boolean contains(Game game) {
		return this.games.contains(game);
	}

}
