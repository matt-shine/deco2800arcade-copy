package deco2800.arcade.minigolf;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.ArcadeSystem;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.audio.Music;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.net.URL;
import java.io.File;
import javax.sound.sampled.Clip;
import java.util.ArrayList;

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
		this.playMusic();
	}
	
	
	@Override
	public void create() {
	
	
		this.getOverlay().setListeners(new Screen() {
			@Override
			public void dispose() {}
			
			@Override
			public void hide() {
			hole.gamePaused = false;
			}
			
			@Override
			public void show() {
			hole.gamePaused = true;
			}
			
			@Override
			public void pause() {}

			@Override
			public void render(float arg0) {}

			@Override
			public void resize(int arg0, int arg1) {}

			@Override
			public void resume() {}

			
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
	private void playMusic(){	
		URL path = this.getClass().getResource("/");
		try {
			System.out.println("path: \n\n" + path.toString());
			String resource = path.toString().replace(".arcade/build/classes/main/", 
			".arcade.minigolf/src/main/").replace("file:", "") + 
			"resources/newHero.wav";
			System.out.println(resource);
			File file = new File(resource);
			new FileHandle(file);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (Exception e) {
			e.printStackTrace();
		}			
	
	}
	
	private static final Game game;
	static {
		game = new Game();
		game.id = "minigolf";
		game.name = "MiniGolf";
		game.description = "Search for buried treasure";
	}
	

}
