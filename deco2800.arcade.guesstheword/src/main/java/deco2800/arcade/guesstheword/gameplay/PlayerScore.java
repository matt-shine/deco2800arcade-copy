package deco2800.arcade.guesstheword.gameplay;

import java.util.ArrayList;

import deco2800.arcade.guesstheword.GUI.GuessTheWord;
import deco2800.arcade.model.Player;

import deco2800.arcade.client.highscores.Highscore;
import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.client.network.NetworkClient;
/**
 * This will add player score to database and retrieve it from database
 * 
 * @author Xu Duangui
 * */
public class PlayerScore {
	
	//--------------------------
	//PRIVATE VARIABLES
	//--------------------------
	/**
	 * Instance of network client
	 * */
	private NetworkClient networkClient;
	/**
	 * Instance of GuesstheWord
	 * */
	private GuessTheWord game;
	/**
	 * Instance of Player
	 * */
	private Player player;
	
	//--------------------------
	//PUBLIC VARIABLES
	//--------------------------
	/**
	 * Instance of HighscoreClient for level 1 scores
	 * */
	public HighscoreClient highScorePlayer1;
	/**
	 * Instance of HighscoreClient for level 2 scores
	 * */
	public HighscoreClient highScorePlayer2;
	/**
	 * Instance of HighscoreClient for level 3 scores
	 * */
	public HighscoreClient highScorePlayer3;
	
	
	
	/**
	 * Player Score Constructor. Instance of HighscoreClient will be created
	 * 
	 * @param game - Instance of game
	 * @param player - Instance of player
	 * @param networkClient - Instance of network client 
	 * */
	public PlayerScore(GuessTheWord game, Player player, NetworkClient networkClient){
		this.game = game;
		this.player = player;
		this.networkClient = networkClient;
		
		highScorePlayer1 = new HighscoreClient(player.getUsername(),"GuessTheWord", networkClient);
		highScorePlayer2 = new HighscoreClient(player.getUsername(),"GuessTheWord", networkClient);
		highScorePlayer3 = new HighscoreClient(player.getUsername(),"GuessTheWord", networkClient);
	}


	//--------------------------
	//Methods to add and retrieve Scores from database
	//--------------------------
	
	/**
	 * Add the score to the database. 
	 * @param highScorePlayer - the level's highScorePlayer
	 * */
	public void countScore(HighscoreClient highScorePlayer){
		highScorePlayer.storeScore("Number", game.getterSetter.getScore() );
	}
	
	/**
	 * Retrieve the score to the database for level 1
	 * 
	 *@return an arraylist of the results
	 * */
	public ArrayList<Highscore> getHighScore1(){
		return (ArrayList<Highscore>) highScorePlayer1.getGameTopPlayers(1, true, "Number");
	}
	/**
	 * Retrieve the score to the database for level 2
	 * 
	 *@return an arraylist of the results
	 * */
	public ArrayList<Highscore> getHighScore2(){
		return (ArrayList<Highscore>) highScorePlayer2.getGameTopPlayers(1, true, "Number");
	}
	
	/**
	 * Retrieve the score to the database for level 3
	 * 
	 *@return an arraylist of the results
	 * */
	public ArrayList<Highscore> getHighScore3(){
		return (ArrayList<Highscore>) highScorePlayer3.getGameTopPlayers(1, true, "Number");
	}

}
