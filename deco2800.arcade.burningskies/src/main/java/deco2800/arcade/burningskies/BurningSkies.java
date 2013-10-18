package deco2800.arcade.burningskies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.burningskies.screen.HelpScreen;
import deco2800.arcade.burningskies.screen.MenuScreen;
import deco2800.arcade.burningskies.screen.OptionsScreen;
import deco2800.arcade.burningskies.screen.PlayScreen;
import deco2800.arcade.burningskies.screen.ScoreScreen;
import deco2800.arcade.burningskies.screen.SplashScreen;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
/**
 * Burning Skies, a 2D top town shooter for use in the arcade. 
 * Borrows concepts from RaidenX, Jamestown, 1942 etc. Touhou influences also.
 * @author AstroSonic
 *
 */
@ArcadeGame(id="burningskies")
public class BurningSkies extends GameClient {
	
	public static final int SCREENWIDTH = 1280;
	public static final int SCREENHEIGHT = 720;
	
	//private String[] players = new String[2]; // The names of the players: the local player is always players[0]
	
	private Music nowPlaying;
	private boolean isPaused = false;
	
	//shh
	public int zalgo = 0;

	//TODO: ACHIEVEMENTS

	@SuppressWarnings("unused")
	private NetworkClient networkClient;
	
	public SplashScreen splashScreen;
	public MenuScreen menuScreen;
	public OptionsScreen optionsScreen;
	public HelpScreen helpScreen;
	public ScoreScreen scoreScreen;
	public PlayScreen gameScreen;

	/**
	 * Basic constructor for the Burning Skies game
	 * @param userName The name of the player
	 * @param client The network client for sending/receiving messages to/from the server
	 */
	public BurningSkies(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		//players[0] = player.getUsername();
		//players[1] = "Player 2"; //TODO eventually the server may send back the opponent's actual username
		this.networkClient = networkClient;
		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this);
		optionsScreen = new OptionsScreen(this);
		helpScreen = new HelpScreen(this);
		scoreScreen = new ScoreScreen(this);
	}
	
	public void playSong(String songName, boolean loop) {
		if(nowPlaying != null) {
			nowPlaying.dispose();
		}
		nowPlaying = Gdx.audio.newMusic(Gdx.files.internal("sound/music/" + songName + ".ogg"));
		nowPlaying.setLooping(loop);
		nowPlaying.setVolume(Configuration.getBackgroundVolume());
		nowPlaying.play();
	}
	
	public void playSong(String songName) {
		playSong(songName, true);
	}
	
	public void pauseSong() {
		if(nowPlaying != null) nowPlaying.pause();
	}
	
	public void resumeSong() {
		if(nowPlaying != null) nowPlaying.play();
	}
	
	public void stopSong() {
		if(nowPlaying != null) nowPlaying.stop();
	}
	
	public void changeVolume(int volume) {
		if(nowPlaying != null) nowPlaying.setVolume((float)volume/100);
	}
	
	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		super.create();
		setScreen( new SplashScreen(this) );
		this.getOverlay().setListeners(new Screen() {
			@Override
			public void hide() {
				isPaused = false;
			}
		
			@Override
			public void show() {
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
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public void setPause(boolean pause) {
		isPaused = pause;
	}

	@Override
	public void dispose() {
		super.dispose();
		stopSong();
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

	
	private static final Game game;
	static {
		game = new Game();
		game.id = "burningskies";
		game.name = "Burning Skies";
		game.description = "Engage in one of the most thrilling space combat battles this side of the galaxy in the critically acclaimed" +
		", user verified and robot tested GoTY 2101! Kill aliens! Get powerups! Shoot things! Eat lead! Don't die! Hold on to your hats, it's about to get crazy.";
	}
	
	public Game getGame() {
		return game;
	}
}
