package deco2800.cyra.game;

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
 *
 */
@ArcadeGame(id = "Cyra")
public class Cyra extends GameClient {

	private NetworkClient networkClient;
	private AchievementClient achievementClient;
	
      
    public Cyra(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		this.networkClient = networkClient; //this is a bit of a hack
        this.achievementClient = new AchievementClient(networkClient);
        
	}

	public SplashScreen getSplashScreen() {
    	return new SplashScreen(this);
    }
	
	@Override
	public void create() {		
	
		super.create();
		//setScreen(new MainMenu(this));
		//Set to splash screen
		setScreen(getSplashScreen());
		//OR go straight to the action
		//setScreen(new GameScreen(this));
		
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
