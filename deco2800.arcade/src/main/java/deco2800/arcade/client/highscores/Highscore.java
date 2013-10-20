package deco2800.arcade.client.highscores;

/**
 * Returned by HighscoreClient when fetching scores, this object contains
 * all the information that will be returned from a score fetching query.
 * 
 * @author TeamA
 */
public class Highscore {
	public String playerName;
	public int score;
	public String date;
	public String type;
}
