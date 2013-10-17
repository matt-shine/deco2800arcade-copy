package deco2800.arcade.cyra.game;

import com.badlogic.gdx.Screen;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.GameClient;
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
	private boolean isPaused;
	
      
    public Cyra(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		this.networkClient = networkClient; //this is a bit of a hack
        this.achievementClient = new AchievementClient(networkClient);
        isPaused = false;
        
	}

	public SplashScreen getSplashScreen() {
    	return new SplashScreen(this);
    }
	
	@Override
	public void create() {		
	
		//Set overlay
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
		
		super.create();
		//setScreen(new MainMenu(this));
		//Set to splash screen
		//setScreen(getSplashScreen());
		//OR go straight to the action
		setScreen(new GameScreen(this, 0.71f));
		
		
	}
	
	public boolean isPaused() {
		return isPaused;
	}

	@Override
	public void dispose() {
		super.dispose();
		//batch.dispose();
		//texture.dispose();
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
