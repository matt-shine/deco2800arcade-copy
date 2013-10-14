package deco2800.arcade.guesstheword.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.guesstheword.gameplay.GetterSetter;
import deco2800.arcade.guesstheword.gameplay.Pictures;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;


//Main Class
@ArcadeGame(id = "GuessTheWord")
public class GuessTheWord extends GameClient{
	
	private static final Game GAME;
	
	GetterSetter getterSetter;
	Pictures picture;
	
	Screen splashScreen;
	Screen mainScreen;
	Screen gameScreen;
	Screen settingsScreen;
	Screen acheivementScreen;
	Skin skin;


	public GuessTheWord(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}
	
	@Override
	public void create() {
		
		Gdx.graphics.setContinuousRendering(false);
//		Gdx.graphics.requestRendering();
		
		getterSetter = new GetterSetter();
		picture = new Pictures();
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		splashScreen = new SplashScreen(this);
		mainScreen = new MainScreen(this);
		gameScreen = new GameScreen(this);
		settingsScreen = new SettingScreen(this);
	//	acheivementScreen = new AchievementScreen(this);
		picture.loadPictures();
		setScreen(splashScreen);
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
		GAME.description = "GuessTheWord game!";
	}
	@Override
	public Game getGame() {
		
		return GAME;
	}
}
