package deco2800.arcade.breakout;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.*;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;

/**
 * 
 * 
 * 
 */
@ArcadeGame(id="Breakout")

public class Breakout extends GameClient{
	
	private OrthographicCamera camera;
	private String player;
	private NetworkClient nc;
	private int score;
	
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;
	
	
	
	
	public Breakout(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		 this.player = player.getUsername();
		 this.nc = networkClient;
	}


	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}

	
}


