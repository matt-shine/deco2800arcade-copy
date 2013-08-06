package deco2800.arcade.client;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

public abstract class GameClient implements ApplicationListener {

	protected Player player; 
	protected NetworkClient networkClient;
	protected List<GameOverListener> gameOverListeners;
	private Object mon = null;
	
	public GameClient(Player player, NetworkClient networkClient) {
		this.player = player;
		this.networkClient = networkClient;
		gameOverListeners = new ArrayList<GameOverListener>();
	}
	
	public abstract Game getGame();

	
	/**
	 * Adds gameOverListener's to the GameClient
	 * @param gameOverListener
	 */
	public void addGameOverListener(GameOverListener gameOverListener) {
		gameOverListeners.add(gameOverListener);
	}
	
	
	/**
	 * Controls what happens when the game is over
	 */
	public void gameOver(boolean sync) {
		for (GameOverListener listener : gameOverListeners) {
			if (sync) {
				listener.notifySync(this);
			} else {
				listener.notify(this);
			}
		}
	}
	
	
	/**
	 * Controls what happens when the game is over
	 */
	public void gameOver() {
		gameOver(false);
	}
	
	
	/**
	 * Sets the object that controls waking up the main thread
	 */
	public void setArcadeThreadMonitor(Object mon) {
		this.mon = mon;
	}
	
	
	/**
	 * wakes up the main thread
	 */
	public void wakeArcadeThread() {
		if (this.mon != null){ 
			synchronized (mon) {
				this.mon.notify();
				this.mon = null;
			}
		}
	}
	
}
