package deco2800.arcade.client;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.AchievementListener;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.PlayerClient;
import deco2800.arcade.client.image.ImageClient;
import deco2800.arcade.client.image.ImageManager;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.EncodedImage;
import deco2800.arcade.protocol.lobby.LobbyMessageResponse;
import deco2800.arcade.protocol.multiplayerGame.GameStateUpdateRequest;
import deco2800.arcade.protocol.game.CasinoServerUpdate;
import deco2800.arcade.utils.Handler;


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
	private ImageClient imageClient;
	private PlayerClient playerClient;
	private ImageManager imageManager;
	private boolean hasF11PressedLast = false;
	private boolean host = false;

	public GameClient(Player player, NetworkClient networkClient) {

		this.player = player;
		this.networkClient = networkClient;
		this.playerClient = new PlayerClient(networkClient);
		this.achievementClient = new AchievementClient(networkClient);
		this.achievementClient.addListener(this);
		gameOverListeners = new ArrayList<GameOverListener>();
		this.imageClient = new ImageClient(networkClient);
		this.imageManager = new ImageManager(imageClient);
	}

	public abstract Game getGame();

    public void achievementAwarded(final Achievement ach) {
	if (this.overlayBridge == null)
	    return;

	this.imageManager.getTexture(ach.icon).setHandler(new Handler<Texture>() {
		public void handle(final Texture texture) {
		    GameClient.this.overlayBridge.addPopup(new UIOverlay.PopupMessage() {
		        @Override
		        public String getMessage() {
			    return "Achievement " + ach.name + " awarded!";
			}		       

			@Override
			public Texture getTexture() {
			    return texture;
			}

		        @Override
			public float displayTime() {
			    return 2.5f;
			}
		    });
		}
	});
    }

    public void progressIncremented(final Achievement ach, final int progress) {
	if (this.overlayBridge == null)
	    return;
	this.imageManager.getTexture(ach.icon).setHandler(new Handler<Texture>() {
		public void handle(final Texture texture) {
		    GameClient.this.overlayBridge.addPopup(new UIOverlay.PopupMessage() {
		        @Override
		        public String getMessage() {
			    return "Progress in achievement " + ach.name + " (" + progress + "/" + ach.awardThreshold + ")";
			}		  

			@Override
			public Texture getTexture() {
			    return texture;
			}

			@Override
			public float displayTime() {
			    return 2.5f;
			}
		    });
		}
	});
    }

	public void setNetworkClient(NetworkClient client) {
		achievementClient.setNetworkClient(client);
		playerClient.setNetworkClient(client);
		imageClient.setNetworkClient(client);
	}

	public void setThisNetworkClient(NetworkClient client) {
		this.networkClient = client;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void incrementAchievement(final String achievementID) {
		achievementClient.incrementProgress(achievementID, player);
	}

	public AchievementClient getAchievementClient() {
		return this.achievementClient;
	}

	public PlayerClient getPlayerClient() {
		return this.playerClient;
	}

	/**
	 * Adds the in game overlay
	 *
	 * @param overlay
	 */
	public void addOverlay(ApplicationListener overlay) {
		this.overlay = overlay;
		overlay.resize(width, height);
	}

	/**
	 * Adds the in game overlay
	 *
	 * @param overlay
	 */
	public void addOverlayBridge(UIOverlay overlay) {
	    System.out.println("adding overlay bridge");

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
	 *
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

		achievementClient.setNetworkClient(null);
		achievementClient.removeListener(this);
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

		// toggles fullscreen on F11
		if (Gdx.input.isKeyPressed(Keys.F11) != hasF11PressedLast
				&& (hasF11PressedLast = !hasF11PressedLast)) {

			Gdx.graphics.setDisplayMode(
					Gdx.graphics.getDesktopDisplayMode().width,
					Gdx.graphics.getDesktopDisplayMode().height,
					!Gdx.graphics.isFullscreen());
		}

	}

	@Override
	public void resize(int width, int height) {

		this.width = width;
		this.height = height;
		if (overlay != null) {
			overlay.resize(width, height);
		}
		//super.resize(width, height);
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
		if (getMPHost()) {
			startMultiplayerGame();
		}
	}
	
	public void updateCasinoState(CasinoServerUpdate obj) {
		
	}

	public int getMultiSession() {
		return multiplayerSession;
	}

	public void setHost(boolean host) {
		this.host = host;
	}
	
	public boolean getMPHost() {
		return host;
	}
	
	public void startMultiplayerGame() {
	}

	public void updateGameState(GameStateUpdateRequest request) {
	}
	
	public void sendStateUpdate() {
	}
	
	private void requestMultiplayerGame() {	
	}
	
	public void displayChat(LobbyMessageResponse response) {
	}

}


