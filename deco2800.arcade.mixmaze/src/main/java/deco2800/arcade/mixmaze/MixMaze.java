package deco2800.arcade.mixmaze;

import java.util.HashSet;
import java.util.Set;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

@ArcadeGame(id="mixmaze")
public class MixMaze extends GameClient {
	public MixMaze(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// This method will never be called on the desktop.
	}

	private static Set<Achievement> achievements =
			new HashSet<Achievement>();

	private static final Game game;
	static {
		game = new Game();
		game.gameId = "mixmaze";
		game.name = "Mix Maze";
		game.availableAchievements = achievements;
	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return game;
	}
}
