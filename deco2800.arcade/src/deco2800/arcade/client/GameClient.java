package deco2800.arcade.client;

import com.badlogic.gdx.ApplicationListener;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

public abstract class GameClient implements ApplicationListener {

	protected Player player; 
	protected NetworkClient networkClient;
	
	public GameClient(Player player, NetworkClient networkClient) {
		this.player = player;
		this.networkClient = networkClient;
	}
	
	public abstract Game getGame();
	
}
