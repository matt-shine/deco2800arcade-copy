package deco2800.arcade.guesstheword.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.guesstheword.gameplay.Achievements;
import deco2800.arcade.guesstheword.gameplay.GetterSetter;
import deco2800.arcade.guesstheword.gameplay.Pictures;
import deco2800.arcade.guesstheword.gameplay.PlayerScore;
import deco2800.arcade.guesstheword.gameplay.WordShuffler;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;

//Main Class
@ArcadeGame(id = "GuessTheWord")
public class GuessTheWord extends GameClient{
	
	private static final Game GAME;
	
	private NetworkClient networkClient;
	private AchievementClient achievementClient;
	private Player player;
	
	public GetterSetter getterSetter;
	public Pictures picture;
	public PlayerScore playerScore;
	
	Screen splashScreen;
	Screen mainScreen;
	Screen gameScreen;
	Screen settingsScreen;
//	Screen acheivementScreen;
	Skin skin;


	public GuessTheWord(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		
		this.player = player;
		this.networkClient = networkClient;
		this.achievementClient = new AchievementClient(networkClient);
		playerScore =  new PlayerScore(this,player,networkClient);
	}
	
	@Override
	public void create() {
		getterSetter = new GetterSetter();		
		
		loadGamePicture();
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		splashScreen = new SplashScreen(this);
		mainScreen = new MainScreen(this);
		gameScreen = new GameScreen(this);
		settingsScreen = new SettingScreen(this);
	//	acheivementScreen = new AchievementScreen(this);
		
		
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
