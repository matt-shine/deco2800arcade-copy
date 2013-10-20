package deco2800.arcade.raiden;


import com.badlogic.gdx.Screen;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.client.network.NetworkClient;

/**
 * A raiden game for use in the Arcade
 * @author Team lion
 *
 */
@ArcadeGame(id="Raiden")
public class Raiden extends GameClient {
	HighscoreClient highscoreUser;
	public String player;
	/**
	 * The constructor for game raiden.
	 * @param player
	 * @param networkClient
	 */
	//private String statusMessage;
	public Raiden(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		this.player = player.getUsername();
		this.highscoreUser = new HighscoreClient
				(player.getUsername(), "Raiden", networkClient);
		GameFrame raiden = new GameFrame();
	}
	/**
	 * Set the overlay.
	 */
	@Override
	public void create() {
		super.create();
		
        //add the overlay listeners
        this.getOverlay().setListeners(new Screen() {

			@Override
			public void dispose() {
			}

			@Override
			public void hide() {
				resume();
			}

			@Override
			public void pause() {
			}

			@Override
			public void render(float arg0) {
			}

			@Override
			public void resize(int arg0, int arg1) {
			}

			@Override
			public void resume() {
			}

			@Override
			public void show() {
				pause();
			}
			
        });
        //Set the highscore.
        HighscoreClient player1 = new HighscoreClient
        		(player, "Raiden", networkClient);
        //Store the score.
        player1.storeScore("Number", GameFrame.score);
	}
	/**
	 *  resume the game.
	 */
	public void resume() {
		super.resume();
	}
	/**
	 * Renders game mechanics.
	 */
	public void render() {
		 super.render();

	}
	/**
	 * Get the game.
	 */
	@Override
	public Game getGame() {
		return game;
	}
	/**
	 * 
	 * @return the player
	 */
	public String playerName() {
		return player;
	}

	/**
	 * The game description.
	 */
	private static final Game game;
	static {
		game = new Game();
		game.id = "Raiden";
		game.name = "Raiden";
        game.description = "Flight Fighter";
	}
}