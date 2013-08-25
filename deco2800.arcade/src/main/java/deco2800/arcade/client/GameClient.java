package deco2800.arcade.client;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

public abstract class GameClient extends com.badlogic.gdx.Game {

	protected Player player;
	protected NetworkClient networkClient;
	protected List<GameOverListener> gameOverListeners;
	private ApplicationListener overlay = null;
	private UIOverlay overlayBridge = null;
	private boolean overlayInitialised = false;
	private int width, height;
    private AchievementClient achievementClient;

	public GameClient(Player player, NetworkClient networkClient) {
		this.player = player;
		this.networkClient = networkClient;
        this.achievementClient = new AchievementClient(networkClient);
		gameOverListeners = new ArrayList<GameOverListener>();
	}

	public abstract Game getGame();

    public void incrementAchievement(String achievementID) {
        achievementClient.incrementProgress(achievementID, player);
    }

	/**
	 * Adds the in game overlay
	 * @param overlay
	 */
	public void addOverlay(ApplicationListener overlay) {
		this.overlay = overlay;
	}

	/**
	 * Adds the in game overlay
	 * @param overlay
	 */
	public void addOverlayBridge(UIOverlay overlay) {
		this.overlayBridge = overlay;
	}

	/**
	 * @return The overlay
	 */
	public UIOverlay getOverlay() {
		return overlayBridge;
	}


	/**
	 * Updates the in game overlay
	 */
	public void processOverlay() {
		if (this.overlay != null) {
			if (!overlayInitialised) {
				overlay.resize(this.width, this.height);
				overlay.create();
				overlayInitialised = true;
			}
			overlay.render();
		}
	}


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
	public void gameOver() {
		for (GameOverListener listener : gameOverListeners) {
			listener.notify(this);
		}
	}

	@Override
	public void create() {
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void render() {
		super.render();
	    processOverlay();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		super.resize(width, height);
	}

	@Override
	public void resume() {
		super.resume();
	}

}
