package deco2800.arcade.guesstheword.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.guesstheword.gameplay.GameModel;
import deco2800.arcade.guesstheword.gameplay.GetterSetter;
import deco2800.arcade.guesstheword.gameplay.Pictures;
import deco2800.arcade.guesstheword.gameplay.PlayerScore;
import deco2800.arcade.guesstheword.gameplay.WordShuffler;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;

//Main Class
/**
 * Main Class for Guess the Word game.
 * 
 * @author Xu Duangui
 */
@ArcadeGame(id = "GuessTheWord")
public class GuessTheWord extends GameClient{
	//--------------------------
	//PRIVATE VARIABLES
	//--------------------------
	/**
	 * Game instance
	* */
	private static final Game GAME;
	/**
	 * NetworkClient instance
	* */
	private static NetworkClient networkClient;
	/**
	 * AchievementClient instance
	* */
	private AchievementClient achievementClient;
	
	/**
	 * Player instance
	* */
	private static Player player;
	
	/**
	 * The Splash Screen's screen
	* */
	Screen splashScreen;
	/**
	 * The Main Screen's screen
	* */
	Screen mainScreen;
	/**
	 * The Game Screen's screen
	* */
	Screen gameScreen;
	/**
	 * The Settings Screen's screen
	* */
	Screen settingsScreen;
	
	
	//--------------------------
	//PUBLIC VARIABLES
	//--------------------------
	/**
	 * GetterSetter instance
	* */
	public GetterSetter getterSetter;
	/**
	 * Pictures instance
	* */
	public Pictures picture;
	/**
	 * PlayerScore instance
	* */
	public PlayerScore playerScore;
	/**
	 * GameModel instance
	* */
	public GameModel gs;
	/**
	 * Skin instance - other screens also using the skins
	* */
	public Skin skin;

//	public GuessTheWord(){
//		super(player, networkClient);
//		getterSetter = new GetterSetter();		
//		picture =  new Pictures();
//		gs = new GameModel(this, new GameScreen(), new WordShuffler());
//
//	}
	/**
	 * Guess the word constructor
	* */
	public GuessTheWord(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		
		this.player = player;
		this.networkClient = networkClient;
		
		this.achievementClient = new AchievementClient(networkClient);
		playerScore =  new PlayerScore(this,player,networkClient);
		getterSetter = new GetterSetter();		
		
	}
	
	@Override
	public void create() {
		loadGamePicture();
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		splashScreen = new SplashScreen(this);
		mainScreen = new MainScreen(this);
		gameScreen = new GameScreen(this);
		settingsScreen = new SettingScreen(this);	
		
		setScreen(splashScreen);
	}
	
	public Pictures loadGamePicture(){
		picture = new Pictures();
		picture.loadPictures();
		
		return picture;
	}

	public void resume() {
		super.resume();
	}
	
	public void render() {
		super.render();
	}

	static {
		GAME = new Game();
		GAME.id = "GuessTheWord";
		GAME.name = "GuessTheWord";
		GAME.description = "This word guessing is popular with people of all ages. " +
				"Its challenging and exciting at the same time!";
	}
	
	@Override
	public Game getGame() {		
		return GAME;
	}
}
