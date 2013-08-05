package deco2800.arcade.arcadeui;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;


@ArcadeGame(id="arcadeui")
public class ArcadeUI extends GameClient {
	   
	public ArcadeUI(Player player, NetworkClient networkClient){
		super(player, networkClient);
	}

	@Override
	public void create() {
	}

	@Override
	public void render() {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	
	//there are no achievements for this
	private static Set<Achievement> achievements = new HashSet<Achievement>();


	private static final Game game;
	static {
		game = new Game();
		game.gameId = "arcadeui";
		game.name = "Arcade UI";
		game.availableAchievements = achievements;
	}

	public Game getGame() {
		return game;
	}
		
}
