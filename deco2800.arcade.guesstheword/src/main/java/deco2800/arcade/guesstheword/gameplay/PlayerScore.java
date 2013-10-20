package deco2800.arcade.guesstheword.gameplay;

import java.util.ArrayList;
import java.util.List;

import deco2800.arcade.guesstheword.GUI.GuessTheWord;
import deco2800.arcade.model.Player;

import deco2800.arcade.client.highscores.Highscore;
import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.client.network.NetworkClient;

public class PlayerScore {
	
	ArrayList<Integer> scores;
	
	private NetworkClient networkClient;
	
	public HighscoreClient highScorePlayer1;
	public HighscoreClient highScorePlayer2;
	public HighscoreClient highScorePlayer3;
	
	GuessTheWord game;
	Player player;
	
	public PlayerScore(GuessTheWord game, Player player, NetworkClient networkClient){
		this.game = game;
		this.player = player;
		this.networkClient = networkClient;
		
		scores =  new ArrayList<Integer>();
		
		highScorePlayer1 = new HighscoreClient(player.getUsername(),"GuessTheWord", networkClient);
		highScorePlayer2 = new HighscoreClient(player.getUsername(),"GuessTheWord", networkClient);
		highScorePlayer3 = new HighscoreClient(player.getUsername(),"GuessTheWord", networkClient);
	}
	
	public void countScore(HighscoreClient highScorePlayer){
		highScorePlayer.storeScore("Number", game.getterSetter.getScore() );
	}
	
	public List getHighScore(){
		List<Highscore> topPlayers1 = highScorePlayer1.getGameTopPlayers(5, true, "Number");
		List<Highscore> topPlayers2 = highScorePlayer2.getGameTopPlayers(5, true, "Number");
		List<Highscore> topPlayers3 = highScorePlayer3.getGameTopPlayers(5, true, "Number");
//		System.out.println(topPlayers);

		highScorePlayer1.printHighscores(topPlayers1);
		highScorePlayer2.printHighscores(topPlayers2);
		highScorePlayer3.printHighscores(topPlayers3);
		
		return topPlayers1;
	}

}
