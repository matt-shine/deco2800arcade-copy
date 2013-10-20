package deco2800.arcade.client.highscores;

/**
 * Returned by HighscoreClient when fetching scores, this object contains
 * all the information that will be returned from a score fetching query.
 * 
 * @author TeamA
 */
public class Highscore {
	/* The name of the player that the score is being returned for */
	public String playerName;
	
	/* The value that is being returned */
	public int score;
	
	/* The date that the score was scored on */
	public String date;
	
	/* The type of the score that is being returned. Will not always be of a 
	 * valid score input type.*/
	public String type;
}
