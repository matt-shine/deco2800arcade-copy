package deco2800.arcade.client;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.badlogic.gdx.ApplicationListener;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

public abstract class GameClient implements ApplicationListener {

	protected Player player; 
	protected NetworkClient networkClient;
	protected List<GameOverListener> gameOverListeners;
	
	public GameClient(Player player, NetworkClient networkClient) {
		this.player = player;
		this.networkClient = networkClient;
		gameOverListeners = new ArrayList<GameOverListener>();
	}
	
	public abstract Game getGame();

	public void addGameOverListener(GameOverListener gameOverListener) {
		gameOverListeners.add(gameOverListener);
	}
	
	public void gameOver() {
		for (GameOverListener listener : gameOverListeners) {
			listener.notify(this);
		}
		this.dispose();
	}
	
	
	/**
	 * Because create is called from the main thread and render is
	 * called from the event thread, the context has to be 
	 * from the main thread at the end of create
	 */
	protected void detachOpenGL(){
		try {
			Display.releaseContext();
		} catch (LWJGLException e) {
			e.printStackTrace();
			gameOver();
		}
	}
	
	/**
	 * Should be called at the start of render
	 */
	protected void attachOpenGL(){
		try {
			if (!Display.isCurrent()) {
				Display.makeCurrent();
			}
		} catch (LWJGLException e) {
			e.printStackTrace();
			gameOver();
		}
	}
	
}
