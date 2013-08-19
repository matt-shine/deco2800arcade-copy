package deco2800.arcade.burningskies;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.achievement.AddAchievementRequest;
import deco2800.arcade.protocol.game.GameStatusUpdate;
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
	
	public static final int SCREENHEIGHT = 600;
	public static final int SCREENWIDTH = 800;
	
	private String[] players = new String[2]; // The names of the players: the local player is always players[0]

	//TODO: ACHIEVEMENTS

	private NetworkClient networkClient;

	/**
	 * Basic constructor for the Burning Skies game
	 * @param userName The name of the player
	 * @param client The network client for sending/receiving messages to/from the server
	 */
	public BurningSkies(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		players[0] = player.getUsername();
		players[1] = "Player 2"; //TODO eventually the server may send back the opponent's actual username
		this.networkClient = networkClient; //this is a bit of a hack
	}
	
	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		super.create();
		Texture.setEnforcePotImages(false); // use images != power of 2 w&h
		setScreen( new Splash(this) );
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
		super.render();
		//TODO: do this
		
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
		game.name = "Burning Skies";
	}
	
	public Game getGame() {
		return game;
	}
	
}
