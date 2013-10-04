package deco2800.arcade.client;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.AchievementListener;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.packman.PackageClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Achievement;

public abstract class GameClient extends com.badlogic.gdx.Game implements AchievementListener {

	protected Player player;
	protected NetworkClient networkClient;
	protected List<GameOverListener> gameOverListeners;
	private int multiplayerOn = 0;
	private int multiplayerSession;
	private ApplicationListener overlay = null;
	private UIOverlay overlayBridge = null;
	private boolean overlayInitialised = false;
	private int width, height;
    private AchievementClient achievementClient;
    private boolean hasF11PressedLast = false;
    
	private PackageClient packClient;
    
	public GameClient(Player player, NetworkClient networkClient) {
		
		this.player = player;
		this.networkClient = networkClient;
        this.achievementClient = new AchievementClient(networkClient);
        this.achievementClient.addListener(this);
		gameOverListeners = new ArrayList<GameOverListener>();
		
		this.packClient = new PackageClient();
	}

	public abstract Game getGame();
	
    public void achievementAwarded(Achievement ach) {
        System.out.println("Achievement `" + ach.name + "` awarded!");
    }

    public void progressIncremented(Achievement ach, int progress) {
        System.out.println("Progress in achievement `" + ach.name + "`: (" + progress +
                           "/" + ach.awardThreshold + ")");
    }

    public void setNetworkClient(NetworkClient client) {
        achievementClient.setNetworkClient(client);
    }

    public void incrementAchievement(final String achievementID) {
        achievementClient.incrementProgress(achievementID, player);
        
        /*if (achievementClient.progressForPlayer(player).
        		progressForAchievement(achievementClient.achievementForID(achievementID)) >= 
        		achievementClient.achievementForID(achievementID).awardThreshold) {
        */
        
        	this.overlayBridge.addPopup(new UIOverlay.PopupMessage() {
				
				@Override
				public String getMessage() {
					//return achievementClient.achievementForID(achievementID).name;
					return achievementID;
				}
	        	
	        });
        	
    	//}
    }

    public AchievementClient getAchievementClient() {
        return this.achievementClient;
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
		if (this.overlay != null) {
			overlay.dispose();
		}
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
		if (Gdx.input.isKeyPressed(Keys.F11) != hasF11PressedLast &&
				(hasF11PressedLast = !hasF11PressedLast)) {
			
			Gdx.graphics.setDisplayMode(
					Gdx.graphics.getDesktopDisplayMode().width,
					Gdx.graphics.getDesktopDisplayMode().height,
					!Gdx.graphics.isFullscreen()
			);
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
	
	public void setMultiplayerOn() {
		multiplayerOn = 1;
	}
	
	public void setMultiplayerOff() {
		multiplayerOn = 0;
	}
	
	public boolean multiplayerMode() {
		if (multiplayerOn == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setMultiSession(int session) {
		multiplayerSession = session;
		startMultiplayerGame();
	}
	
	public void startMultiplayerGame() {
	}
	
	public void updateGameState(Object update) {
	}
	
	}
	
}
