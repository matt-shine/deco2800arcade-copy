package deco2800.arcade.blackjack.client;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.game.CasinoServerUpdate;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.listener.CasinoListener;

/**
 * A Blackjack Cainso game for use in the Arcade
 * @author Dane Cousins, Kristian Zembic, Robert Macdonald, Scott Fredericks, Fionntan Shanahan
 *
 */
@ArcadeGame(id="blackjack")
public class Blackjack extends GameClient {
	

    // Screen Parameters
    public static final int SCREENHEIGHT = 720;
    public static final int SCREENWIDTH = 1280;
    
	//Network client for communicating with the server.
	private NetworkClient networkClient;
	
	//Menu variables
	private MainMenuScreen MainMenuScreen;
	
	//Game variables
    public String player;
    private NetworkClient networkClient;
	
	/**
	 * Basic constructor for the Blackjack game
	 * @param userName The name of the player
	 * @param client The network client for sending/receiving messages to/from the server
	 */
	public Blackjack(Player player, NetworkClient networkClient) {
		super(player, networkClient);
        this.player = player.getUsername();
        this.networkClient = networkClient;
        networkClient.addListener(new CasinoListener());
	}
	
	//Reusable list of achievements
	private static Set<Achievement> achievements = new HashSet<Achievement>();
	static {
		Achievement randomAchievement = new Achievement("Create these later");
		achievements.add(randomAchievement);
	}
	
	
	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		super.create();
		MainMenuScreen = new MainMenuScreen(this);
    	setScreen(MainMenuScreen);
    	
		//  This is a test message and an example 
		CasinoServerUpdate msg = new CasinoServerUpdate();
		msg.username = "test"; 
		msg.message = "testme";
		this.networkClient.sendNetworkObject(msg);	
	}

	@Override
	public void dispose() {
		super.dispose();
		MainMenuScreen.dispose();
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

	}

	public void resize(int arg0, int arg1) {
		super.resize(arg0, arg1);
	}

	@Override
	public void resume() {
		super.resume();
	}

	
	private static final Game game;
	static {
		game = new Game();
		game.gameId = "blackjack";
		game.name = "Blackjack";
		game.availableAchievements = achievements;
	}
	
	public Game getGame() {
		return game;
	}
	
	@Override
	public void updateCasinoState(CasinoServerUpdate obj) {
		System.out.println("GOT THIS MESSAGE:" + obj.message);
	}
}
