package deco2800.arcade.client;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

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
    private boolean hasF11PressedLast = false;
    
	public GameClient(Player player, NetworkClient networkClient) {
		
		this.player = player;
		this.networkClient = networkClient;
        this.achievementClient = new AchievementClient(networkClient);
		gameOverListeners = new ArrayList<GameOverListener>();
	}

	public abstract Game getGame();

    public void incrementAchievement(final String achievementID) {
        achievementClient.incrementProgress(achievementID, player);
        
        
        //if (achievementClient.progressForPlayer(player).progressForAchievement(achievementClient.achievementForID(achievementID)) >= achievementClient.achievementForID(achievementID).awardThreshold) {
        
        
        	this.overlayBridge.addPopup(new UIOverlay.PopupMessage() {
				
				@Override
				public String getMessage() {
					//return achievementClient.achievementForID(achievementID).name;
					return achievementID;
				}
	        	
	        });
        	
    	//}
    }

	/**
	 * Adds the in game overlay
	 * @param overlay
	 */
	public void addOverlay(ApplicationListener overlay) {
		this.overlay = overlay;
		overlay.resize(width, height);
	}

	/**
	 * Adds the in game overlay
	 * @param overlay
	 */
	public void addOverlayBridge(UIOverlay overlay) {
		this.overlayBridge = overlay;
		overlay.setHost(this);
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
	    

		//toggles fullscreen on F11
		if (Gdx.input.isKeyPressed(Keys.F11) != hasF11PressedLast && (hasF11PressedLast = !hasF11PressedLast)) {
			Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, !Gdx.graphics.isFullscreen());
		}
		
	}

	@Override
	public void resize(int width, int height) {
		
		this.width = width;
		this.height = height;
		if (overlay != null) {
			overlay.resize(width, height);
		}
		super.resize(width, height);
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	public int getWidth() {
		return width;
	}
	
	
	public int getHeight() {
		return height;
	}

	public NetworkClient getNetworkClient() {
		return this.networkClient;
	}
	
	
	public Player getPlayer() {
		return player;
	}
	
	
}
