package deco2800.arcade.pong;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.protocol.multiplayerGame.GameStateUpdateRequest;
import deco2800.arcade.protocol.multiplayerGame.MultiGameRequestType;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.highscores.Highscore;
import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.AchievementClient;

import java.util.ArrayList;

import deco2800.arcade.model.Achievement;

/**
 * A Pong game for use in the Arcade
 * 
 * @author uqjstee8
 * 
 */
@ArcadeGame(id = "Pong")
public class Pong extends GameClient {

	private OrthographicCamera camera;

	private static final Game GAME;

	private Paddle leftPaddle;
	private Paddle rightPaddle;
	private Ball ball;
	// private enum GameState {READY,INPROGRESS,GAMEOVER}
	private GameState gameState;
	private int[] scores = new int[2];
	
	// The names of the players: the local player is always players[0]
	private String[] players = new String[2]; 

	public static final int WINNINGSCORE = 3;
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;

	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	private String statusMessage;

	// Network client for communicating with the server.
	// Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;
	private AchievementClient achievementClient;

	/**
	 * Basic constructor for the Pong game
	 * 
	 * @param player: The name of the player
	 * 
	 * @param networkClient: The network client for sending/receiving messages 
	 * 		to/from the server
	 */
	public Pong(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		players[0] = player.getUsername();
		players[1] = "Player 2"; 
		
		// TODO eventually the server may send back the opponent's actual username
		
		// this is a bit of a hack this.achievementClient = new 
		// AchievementClient(networkClient);
		this.networkClient = networkClient; 

		// These calls are just used for testing HighscoreClient
		// Creating new HighscoreClient connection
		HighscoreClient hsd = new HighscoreClient(player.getUsername(), "Pong",
				networkClient);

	}

	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		// add the overlay listeners
		this.getOverlay().setListeners(new Screen() {

			@Override
			public void dispose() {
			}

			@Override
			public void hide() {
				// TODO: unpause pong
			}

			@Override
			public void pause() {
			}

			@Override
			public void render(float arg0) {
			}

			@Override
			public void resize(int arg0, int arg1) {
			}

			@Override
			public void resume() {
			}

			@Override
			public void show() {
				// TODO: unpause pong
			}

		});

		super.create();

		// Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);

		// Create the paddles
		setLeftPaddle(new LocalUserPaddle(new Vector2(20, SCREENHEIGHT / 2
				- Paddle.INITHEIGHT / 2)));
		getLeftPaddle().setColor(1, 0, 0, 1);

		if (ArcadeSystem.isMultiplayerEnabled()) {
			setRightPaddle(new NetworkPaddle(new Vector2(SCREENWIDTH
					- Paddle.WIDTH - 20, SCREENHEIGHT / 2 - Paddle.INITHEIGHT
					/ 2), this));
			getRightPaddle().setColor(0, 0, 1, 1);
		} else {
			setRightPaddle(new AIPaddle(new Vector2(SCREENWIDTH - Paddle.WIDTH
					- 20, SCREENHEIGHT / 2 - Paddle.INITHEIGHT / 2)));
			getRightPaddle().setColor(0, 0, 1, 1);
		}

		/**
		 * TODO Allow network games 1. Create local player (LOBBY) 2.
		 * "Waiting for other players. Press '1' to play local game against the computer"
		 * 3a. Receive game join request 3b.
		 * "Player 'Bob' wishes to join the game. Press 'Y' to accept" 3c1.
		 * ('Y') Create Network player, move to READY 3c2. ('N') Go to 2.
		 */

		// Create the ball
		setBall(new Ball());
		getBall().setColor(1, 1, 1, 1);

		// Necessary for rendering
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();

		// Initialise the scores and game state
		scores[0] = 0;
		scores[1] = 0;
		// gameState = GameState.READY;
		gameState = new ReadyState();
		// Multiplayer games show different message
		if (!ArcadeSystem.isMultiplayerEnabled()) {
			statusMessage = "Click to start!";
		} else {
			statusMessage = "Waiting for Opponent!";
		}

		// achievements demo
		AchievementClient achClient = getAchievementClient();
		ArrayList<Achievement> achievements = achClient
				.achievementsForGame(getGame());
		for (Achievement ach : achievements) {
			System.out.println(ach.toString());
		}
		// Multiplayer Game waiting for opponent
		if (ArcadeSystem.isGameWaiting()) {
			requestMultiplayerGame();
		}

	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	/**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {

		// Black background
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();

		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);

		// Begin drawing of shapes
		shapeRenderer.begin(ShapeType.FilledRectangle);

		getLeftPaddle().render(shapeRenderer);

		getRightPaddle().render(shapeRenderer);

		getBall().render(shapeRenderer);

		// End drawing of shapes
		shapeRenderer.end();

		// render score
		batch.begin();
		font.setColor(Color.YELLOW);
		font.draw(batch, players[0], SCREENWIDTH / 2 - 100, SCREENHEIGHT - 20);
		font.draw(batch, players[1], SCREENWIDTH / 2 + 50, SCREENHEIGHT - 20);
		font.draw(batch, Integer.toString(scores[0]), SCREENWIDTH / 2 - 50,
				SCREENHEIGHT - 50);
		font.draw(batch, Integer.toString(scores[1]), SCREENWIDTH / 2 + 75,
				SCREENHEIGHT - 50);

		// If there is a current status message (i.e. if the game is in the
		// ready or gameover state)
		// then show it in the middle of the screen
		if (statusMessage != null) {
			font.setColor(Color.WHITE);
			font.draw(batch, statusMessage, SCREENWIDTH / 2 - 100,
					SCREENHEIGHT - 100);
			if (gameState instanceof GameOverState) {
				font.draw(batch, "Click to exit", SCREENWIDTH / 2 - 100,
						SCREENHEIGHT - 200);
			}
		}
		batch.end();
		handleInput();
		super.render();

	}

	/**
	 * Handle user input from keyboard or mouse
	 */
	private void handleInput() {
		// Respond to user input and move the ball depending on the game state
		gameState.handleInput(this);
	}

	/**
	 * The point has ended: update scores, reset the ball, check for a winner
	 * and move the game state to ready or gameover
	 * 
	 * @param winner: 0 for player 1, 1 for player 2
	 */
	void endPoint(int winner) {
		ball.reset();
		scores[winner]++;
		// If we've reached the victory point then update the display
		if (scores[winner] == WINNINGSCORE) {
			int loser = winner == 1 ? 0 : 1; // The loser is the player who
												// didn't win!
			statusMessage = players[winner] + " Wins " + scores[winner] + " - "
					+ scores[loser] + "!";
			// Game was multiplayer and may therefore affect player matchmaking
			// rating
			if (winner == 0 && ArcadeSystem.isMultiplayerEnabled()) {
				multiGameOver();
			}
			gameState = new GameOverState();
			// Update the game state to the server
			networkClient.sendNetworkObject(createScoreUpdate());
			// If the local player has won, send an achievement
			if (winner == 0) {
				incrementAchievement("pong.winGame");
				incrementAchievement("pong.master");
			}
		} else {
			// No winner yet, get ready for another point
			gameState = new ReadyState();
			// Define messages based off host/multiplayer variables
			if (!ArcadeSystem.isMultiplayerEnabled() || getMPHost()) {
				statusMessage = "Click to start!";
			} else {
				statusMessage = "Waiting for host!";
			}
		}
	}

	/**
	 * Create an update object to send to the server notifying of a score change
	 * or game outcome
	 * 
	 * @return The Game Status Update.
	 */
	private GameStatusUpdate createScoreUpdate() {
		GameStatusUpdate update = new GameStatusUpdate();
		update.gameId = GAME.id;
		update.username = players[0];
		// TODO Should also send the score!
		return update;
	}

	/**
	 * Start a new point: start the ball moving and change the game state
	 */
	void startPoint() {
		// Do not allow game to start if it is multiplayer without an opponent
		if (getMPHost() || !ArcadeSystem.isMultiplayerEnabled()) {
			getBall().randomizeVelocity();
			Ball ball = getBall();
			sendInitState();
			gameState = new InProgressState();
			statusMessage = null;
		}
	}

	@Override
	public void resize(int arg0, int arg1) {
		super.resize(arg0, arg1);
	}

	@Override
	public void resume() {
		super.resume();
	}

	static {
		GAME = new Game();
		GAME.id = "pong";
		GAME.name = "Pong";
		GAME.description = "Tennis, without that annoying 3rd dimension!";
	}

	public Game getGame() {
		return GAME;
	}

	public Paddle getLeftPaddle() {
		return leftPaddle;
	}

	public void setLeftPaddle(Paddle leftPaddle) {
		this.leftPaddle = leftPaddle;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public Paddle getRightPaddle() {
		return rightPaddle;
	}

	public void setRightPaddle(Paddle rightPaddle) {
		this.rightPaddle = rightPaddle;
	}

	/**
	 * Sends a request to the server to start a Multiplayer Matchmaking game
	 */
	private void requestMultiplayerGame() {
		NewMultiGameRequest request = new NewMultiGameRequest();
		request.playerID = this.getPlayer().getID();
		request.gameId = this.getGame().getID();
		request.requestType = MultiGameRequestType.MATCHMAKING;
		networkClient.sendNetworkObject(request);
	}

	/**
	 * Starts the Multiplayer Game once an opponent is found
	 */
	public void startMultiplayerGame() {
		getBall().randomizeVelocity();
		if (getMPHost()) {
			long time = System.currentTimeMillis();
			while (time + 3000 > System.currentTimeMillis())
				;
			sendInitState();
		}
		gameState = new InProgressState();
		statusMessage = null;
	}

	/**
	 * Update the state of the game. For pong each client can keep track of the
	 * ball so the important issue is each user's paddle.
	 */
	public void sendStateUpdate() {
		GameStateUpdateRequest request = new GameStateUpdateRequest();
		request.playerID = player.getID();
		request.gameId = "pong";
		request.gameSession = super.getMultiSession();
		request.initial = false;
		request.gameOver = false;
		request.stateChange = (Object) getLeftPaddle().getPosition();
		networkClient.sendNetworkObject((request));
	}

	/**
	 * Sends the initial state of the game. Pong randomly chooses the velocity
	 * of the ball so this is the initial state.
	 */
	private void sendInitState() {
		GameStateUpdateRequest request = new GameStateUpdateRequest();
		request.playerID = player.getID();
		request.gameId = "pong";
		request.gameSession = super.getMultiSession();
		request.initial = true;
		request.gameOver = false;
		Vector2 vect = getBall().getVelocity();
		// *-1 to make both users see their paddle on the left side
		vect.x = -1 * vect.x;
		request.stateChange = (Object) vect;
		networkClient.sendNetworkObject((request));
	}

	/**
	 * Updates the game state based off network communication For pong this
	 * updates the location of the opponent's paddle
	 * 
	 * @param request: The update request sent by the server
	 */
	public void updateGameState(GameStateUpdateRequest request) {
		// The first request will contain information about the ball
		if (request.initial == true) {
			startMultiplayerGame();
			getBall().setVelocity((Vector2) request.stateChange);
			return;
		}
		// Each one after moves opponent paddle
		if (request.playerID != player.getID()) {
			Vector2 vector = (Vector2) request.stateChange;
			vector.x = getRightPaddle().getPosition().x;
			getRightPaddle().setPosition(vector);
		}
	}

	/**
	 * Informs the server that the game is complete and who won to allow for
	 * matchmaking rating calculations
	 */
	private void multiGameOver() {
		GameStateUpdateRequest request = new GameStateUpdateRequest();
		request.gameId = "pong";
		request.gameOver = true;
		request.winner = player.getID();
		request.playerID = player.getID();
		request.gameSession = super.getMultiSession();
		request.initial = false;
		networkClient.sendNetworkObject(request);
	}

}
