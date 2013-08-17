package deco2800.arcade.deerforest.GUI;

import java.util.HashSet;
import java.util.Set;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.deerforest.models.gameControl.GameSystem;
/**
 * A card game for use in the Arcade
 * @author uqjstee8
 *
 */
@ArcadeGame(id="deerforest")
public class DeerForest extends GameClient {
	
	public DeerForest(Player player, NetworkClient networkClient){
		super(player, networkClient);
	}

	@Override
	public void create() {
		//start up main game
		GameSystem tempSystem = new GameSystem(null, null);
		MainGame gam = new MainGame(tempSystem);
		gam.create();
		this.setScreen(new MainGameScreen(gam));
		super.create();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	
	//there are no achievements for this
	private static Set<Achievement> achievements = new HashSet<Achievement>();


	private static final Game game;
	static {
		game = new Game();
		game.gameId = "deerforest";
		game.name = "Deer Forest";
		game.availableAchievements = achievements;
	}

	public Game getGame() {
		return game;
	}
	
}
