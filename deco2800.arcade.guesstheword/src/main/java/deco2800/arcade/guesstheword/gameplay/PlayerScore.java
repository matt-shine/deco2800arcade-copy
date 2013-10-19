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
	HighscoreClient highScorePlayer;
	
	GuessTheWord game;
	Player player;
	
	public PlayerScore(GuessTheWord game, Player player, NetworkClient networkClient){
		this.game = game;
		this.player = player;
		this.networkClient = networkClient;
		
		scores =  new ArrayList<Integer>();
		
//		highScorePlayer = new HighscoreClient("BENNY","GuessTheWord", networkClient);
	}
	
	public void countScore(){
		highScorePlayer.storeScore(game.getterSetter.getLevel(), game.getterSetter.getScore() );
	}
	
	public void getHighScore(){
		List<Highscore> topPlayers = highScorePlayer.getGameTopPlayers(10, true, game.getterSetter.getLevel());
//		System.out.println(topPlayers);
		highScorePlayer.printHighscores(topPlayers);
	}

}
