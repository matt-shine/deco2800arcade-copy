package deco2800.arcade.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.AchievementClient;

/**
 * A Pong game for use in the Arcade
 * @author uqjstee8
 *
 */
@ArcadeGame(id="wolfenstein")
public class WL6 extends GameClient {

	private static Game GAME = null;
	@SuppressWarnings("unused")
	private NetworkClient networkClient;
	@SuppressWarnings("unused")
	private AchievementClient achievementClient;

	private WL6Map currentMap = null;
	private int currentLevel = 0;
	
	public WL6(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		this.networkClient = networkClient; //this is a bit of a hack
        this.achievementClient = new AchievementClient(networkClient);
	}
	
	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		
		currentMap = new WL6Map("[0]");
		
        //add the overlay listeners
        this.getOverlay().setListeners(new Screen() {

			@Override
			public void dispose() {
			}

			@Override
			public void hide() {
				//TODO: unpause wl6
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
				//TODO: unpause wl6
			}
			
        });

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

	/**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {
		
		//Black background
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    
	    
	    
		super.render();
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		super.resize(arg0, arg1);
	}

	@Override
	public void resume() {
		super.resume();
	}

	static {
		GAME = new Game();
		GAME.id = "wolfenstein";
		GAME.name = "Wolfenstein";
		GAME.description = "Killin' Natzis";
	}
	
	public Game getGame() {
		return GAME;
	}
	
}
