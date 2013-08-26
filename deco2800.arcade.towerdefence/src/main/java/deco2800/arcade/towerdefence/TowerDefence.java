package deco2800.arcade.towerdefence;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.towerdefence.SplashScreen;


@ArcadeGame(id="towerdefence")
public class TowerDefence extends GameClient {
	Screen splashScreen;
	Screen menuScreen;
	Screen gameScreen;
	
	private static final String LOG = TowerDefence.class.getSimpleName();
	
	public TowerDefence(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void create() {		
		super.create();
		
		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		setScreen(splashScreen);		
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	private static final Game game;
	static {
		game = new Game();
		game.id = "towerdefence";
		game.name = "Tower Defence";
	}

	public Game getGame() {
		return game;
	}

}

