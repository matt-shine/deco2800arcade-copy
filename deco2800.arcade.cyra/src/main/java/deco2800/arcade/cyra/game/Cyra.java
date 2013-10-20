package deco2800.arcade.cyra.game;

import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.UIOverlay.PopupMessage;
import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.client.highscores.Highscore;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Game;

/** The extension of Game class that is opened by the desktop application.
 * Will need to be altered to extend GameClient instead.
 * GameScreen.java might need to get merged with this one, so that this will
 * be the class in control of level progression 
 * @author Game Over
 */
@ArcadeGame(id = "Cyra")
public class Cyra extends GameClient {

	private NetworkClient networkClient;
	private AchievementClient achievementClient;
	private HighscoreClient highscoreClient;
	private Player player;
	private boolean isPaused = false;
      
    public Cyra(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		this.networkClient = networkClient; //this is a bit of a hack
        this.achievementClient = new AchievementClient(networkClient);
        this.player = player;
        this.highscoreClient = new HighscoreClient(player.getUsername(), "Cyra", networkClient);

	}
    
    /** 
     * Adds a players score to the Highscore client. Called when the player dies or reaches 
     * end of level. 
     * @param score	- the player's score
     */
    public void addHighscore(int score) {
    	highscoreClient.storeScore("Number", score);
    }

    
    public void createPopup(final String message) {
		this.getOverlay().addPopup(new UIOverlay.PopupMessage() {

			@Override
			public String getMessage() {
				return message;
			}

		});
	}
    
    /**
     * Grabs a list of the top ten scores for the game. Returns the scores as 
     * a list of the Highscore object.
     * @return List of top ten highscore objects
     */
	public List<Highscore> getHighscores() {
		return highscoreClient.getGameTopPlayers(10, true, "Number");
	}
    
    
	public SplashScreen getSplashScreen() {
    	return new SplashScreen(this);
    }
	
	@Override
	public void create() {		
		super.create();
		//Set listeners for the Overlay
		this.getOverlay().setListeners(new Screen() {
			@Override
			public void hide() {
				//Unpause your game here
				isPaused = false;
			}
			
			@Override
			public void show() {
				//Pause your game here
				isPaused = true;
			}
			
			@Override
			public void pause() {}
			@Override
			public void render(float arg0) {}
			@Override
			public void resize(int arg0, int arg1) {}
			@Override
			public void resume() {}
			@Override
			public void dispose() {}
		});
		// End Overlay
		//setScreen(new MainMenu(this));
		//Set to splash screen
		setScreen(getSplashScreen());
		//OR go straight to the action

		
		
		
		
	}
	
	public boolean isPaused() {
		return isPaused;
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
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	//Providing information about the game
	private static final Game game;
	static {
		game = new Game();
		game.id = "cyra";
		game.name = "Cyra";
		game.description = "Run around and kill stuff with sword, yay!";
	}
	
	@Override
	public deco2800.arcade.model.Game getGame() {
		return game;
	}
}
