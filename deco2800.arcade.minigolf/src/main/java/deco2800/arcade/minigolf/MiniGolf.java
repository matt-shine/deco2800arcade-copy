package deco2800.arcade.minigolf;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.ArcadeSystem;
import com.badlogic.gdx.Screen;
import deco2800.arcade.client.AchievementClient;

/* main game class, sets the screens to be displayed */

@ArcadeGame(id = "MiniGolf")
public class MiniGolf extends GameClient {
	
	GameScreen hole; 
	MenuScreen menu;
	private boolean firstCall = true;
	private String playerName;
	
	
	public MiniGolf(Player player, NetworkClient network){
		super(player, network); 
		this.playerName = player.getUsername();
		this.incrementAchievement("minigolf.360");
	}
	
	
	@Override
	public void create() {
	
	
		this.getOverlay().setListeners(new Screen() {

			@Override
			public void dispose() {
			}

			@Override
			public void hide() {
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
			}
			
        });
		menu = new MenuScreen(this, this.firstCall);
		hole = new GameScreen(this, 1);
		setScreen(menu);
		
		
		
	}	
		
	
	
	@Override
	public void dispose(){
		menu.dispose(); 
		hole.dispose();
		super.dispose();
	}
	
	@Override
	public void pause() {
		super.pause();
	}

	/**
	 * Resumes Application from a Paused State
	 */
	@Override
	public void resume() {
		super.resume();
	}

	/**
	 * Renders game mechanics.
	 */
	public void render() {
		 super.render();

	}

	@Override
	public Game getGame() {
		return game;
	}

	
	public void setScreen(GameScreen hole, int level) {
		super.setScreen(hole); 		
	}
	public void setCall(boolean value){
		this.firstCall = value;
	}
	
	private static final Game game;
	static {
		game = new Game();
		game.id = "minigolf";
		game.name = "MiniGolf";
		game.description = "Search for buried treasure";
	}
	

}
