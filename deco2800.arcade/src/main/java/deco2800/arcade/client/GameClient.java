package deco2800.arcade.client;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

public abstract class GameClient implements ApplicationListener {

	protected Player player; 
	protected NetworkClient networkClient;
	protected List<GameOverListener> gameOverListeners;
	
	public GameClient(Player player, NetworkClient networkClient) {
		this.player = player;
		this.networkClient = networkClient;
		gameOverListeners = new ArrayList<GameOverListener>();
	}
	
	public abstract Game getGame();

	public void addGameOverListener(GameOverListener gameOverListener) {
		gameOverListeners.add(gameOverListener);
	}
	
	public void gameOver() {
		for (GameOverListener listener : gameOverListeners) {
			listener.notify(this);
		}
		this.dispose();
	}
	
}
