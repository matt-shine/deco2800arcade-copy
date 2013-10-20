package deco2800.arcade.chess;

import deco2800.arcade.chess.SplashScreen;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.listener.ReplayListener;
import deco2800.arcade.client.replay.ReplayEventListener;
import deco2800.arcade.client.replay.ReplayHandler;
import deco2800.arcade.client.replay.ReplayNode;
import deco2800.arcade.client.replay.ReplayNodeFactory;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.chess.pieces.Piece;
import deco2800.arcade.client.highscores.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.Input.Keys;

@ArcadeGame(id = "chess")
public class Chess extends GameClient implements InputProcessor, Screen {
	private static final Game game;
	static {
		game = new Game();
		game.id = "chess";
		game.name = "Chess";
		game.description = "A game of Chess, where 2 players, dubbed white"
				+ "or black, aim to CheckMate their opponent before being"
				+ "CheckMated themselves."; 
	}
	
	private ReplayHandler replayHandler;
	private ReplayListener replayListener;
	// This shows whether a piece is selected and ready to move.
	private boolean moving = false;
	private static Piece movingPiece = null;
	private static boolean isReplaying = false;
	// Sprite offsets
	private int horizOff = SCREENWIDTH / 2 - 256;
	private int verticOff = SCREENHEIGHT / 2 - 256;
	private int pieceHorizOff = 24;
	private int pieceVerticOff = 24;
	private String info;
	private BitmapFont gameInfo;
	private String teamCheck;
	private BitmapFont checkInfo;
	
	private List<Highscore> hs;
	
	// Piece positions
	private int[] whiteRook1Pos, whiteKnight1Pos, whiteBishop1Pos, whiteKingPos,
			whiteQueenPos, whiteBishop2Pos, whiteKnight2Pos, whiteRook2Pos;
	private int[] blackRook1Pos, blackKnight1Pos, blackBishop1Pos, blackKingPos,
			blackQueenPos, blackBishop2Pos, blackKnight2Pos, blackRook2Pos;
	private int[] whitePawn0Pos, whitePawn1Pos, whitePawn2Pos, whitePawn3Pos,
			whitePawn4Pos, whitePawn5Pos, whitePawn6Pos, whitePawn7Pos;
	private int[] blackPawn0Pos, blackPawn1Pos, blackPawn2Pos, blackPawn3Pos,
			blackPawn4Pos, blackPawn5Pos, blackPawn6Pos, blackPawn7Pos;

	static Board board;
	private String[] players = new String[2];

	private OrthographicCamera camera;

	private InputMultiplexer inputMultiplexer = new InputMultiplexer(this);

	private static boolean recording;
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	public static final int NUM_1 = 8, NUM_2 = 9, NUM_3 = 10, NUM_4 = 11;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	private Texture chessBoard;

	// Buttons and screens
	private TextButton replayButton, startreplayButton, backButton,
			newGameButton, newGameButtonEasy;
	private Stage stage;
	private BitmapFont BmFontB;
	private TextureAtlas map;
	private Skin skin;
	private Texture splashTexture;
	private Texture splashTexture2;
	private Sprite splashSprite;

	private Sprite blackBishop1, blackBishop2, blackRook1, blackRook2,
			blackKnight1, blackKnight2, blackKing, blackQueen, blackPawn0,
			blackPawn1, blackPawn2, blackPawn3, blackPawn4, blackPawn5,
			blackPawn6, blackPawn7, whiteBishop2, whiteBishop1, whiteRook1,
			whiteRook2, whiteKnight1, whiteKnight2, whiteKing, whiteQueen,
			whitePawn0, whitePawn1, whitePawn2, whitePawn3, whitePawn4,
			whitePawn5, whitePawn6, whitePawn7;

	private int loadedStyle;
	private ArrayList<String> styles;

	// Tracks whether the game is paused
	private boolean paused = false;
	// Tracks level of single player mode
	public boolean EasyComputerOpponent;
	public boolean HardComputerOpponent;

	// Tracks if multiplayer is on
	private boolean Multiplayer = false;

	// Network client for communicating with the server.
	// Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;

	public SplashScreen splashScreen;
	public MenuScreen menuScreen;
	HighscoreClient player1, player2;

	/**
	 * Initialises a new game
	 */
	public Chess(Player player, NetworkClient networkClient) {

		super(player, networkClient);
		this.incrementAchievement("chess.winGame");
		initPiecePos();
		board = new Board();
		movePieceGraphic();
		splashScreen = new SplashScreen(this);
		menuScreen = new MenuScreen(this);
		setScreen(splashScreen);
		this.networkClient = networkClient;
		players[0] = player.getUsername();
		players[1] = "Player 2";
		// setup highscore client
		// replay stuff
		replayHandler = new ReplayHandler(this.networkClient);
		replayListener = new ReplayListener(replayHandler);
		this.networkClient.addListener(replayListener);
		player1 = new HighscoreClient(players[0], "chess", networkClient);
		// Set up the movePiece event to take a piece id, target_x position and
		// target_y position

		replayHandler.addReplayEventListener(initReplayEventListener());
		ReplayNodeFactory.registerEvent("movePiece", new String[] { "start_x",
				"start_y", "target_x", "target_y" });

		// True means AI is playing, false if it isn't
		// EasyComputerOpponent = false;
		
		hs = player1.getWinLoss();

		styles = new ArrayList<String>();

		try {
			BufferedReader br = Gdx.files.internal("imgs/styles.txt").reader(512);
			String line = br.readLine();

			while (line != null) {
				styles.add(line);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		loadedStyle = 2;
	}

	@Override
	public void create() {
		super.create();
		this.getOverlay().setListeners(new Screen() {
			@Override
			public void hide() {
			}
			@Override
			public void show() {
			}
			@Override
			public void pause() {}
			@Override
			public void render(float arg0) {}
			@Override
			public void resume() {}
			@Override
			public void dispose() {}
			@Override
			public void resize(int arg0, int arg1) {
				//arg1 = SCREENHEIGHT;
				//arg0 =  SCREENWIDTH;
				
			}
		});
		// Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false);

		// Necessary for rendering
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();

		info = "White teams turn";
		gameInfo = new BitmapFont();
		teamCheck = "";
		checkInfo = new BitmapFont();

		Texture.setEnforcePotImages(false);
		
		// Add in correct multiplexer
		inputMultiplexer.addProcessor(this);
		ArcadeInputMux.getInstance().addProcessor(inputMultiplexer);
		
		// load the images for the droplet and the bucket, 512x512 pixels each
		chessBoard = new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/board.png"));

		// Pieces
		blackBishop1 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/Black B.png")));
		blackRook1 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/Black R.png")));
		blackKnight1 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/Black N.png")));
		blackKing = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/Black K.png")));
		blackQueen = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/Black Q.png")));
		blackPawn0 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/Black P.png")));

		whiteBishop1 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/White B.png")));
		whiteRook1 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/White R.png")));
		whiteKnight1 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/White N.png")));
		whiteKing = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/White K.png")));
		whiteQueen = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/White Q.png")));
		whitePawn0 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/White P.png")));

		whiteRook2 = whiteRook1;
		whiteBishop2 = whiteBishop1;
		whiteKnight2 = whiteKnight1;
		blackBishop2 = blackBishop1;
		blackKnight2 = blackKnight1;
		blackRook2 = blackRook1;

		whitePawn1 = whitePawn0;
		whitePawn2 = whitePawn0;
		whitePawn3 = whitePawn0;
		whitePawn4 = whitePawn0;
		whitePawn5 = whitePawn0;
		whitePawn6 = whitePawn0;
		whitePawn7 = whitePawn0;
		blackPawn1 = blackPawn0;
		blackPawn2 = blackPawn0;
		blackPawn3 = blackPawn0;
		blackPawn4 = blackPawn0;
		blackPawn5 = blackPawn0;
		blackPawn6 = blackPawn0;
		blackPawn7 = blackPawn0;


		makeButtons();
		replayHandler.startSession("chess", player.getUsername());
		// Pause game and wait for connection if multiplayer is selected
		if (Multiplayer) {
			paused = true;
		}
	
	}

	
	private static ReplayEventListener initReplayEventListener() {
		return new ReplayEventListener() {
			public void replayEventReceived(String eType, ReplayNode eData) {
				System.out.println("Got event!");

				// Built in event types
				if (eType.equals("node_pushed")) {
					System.out.println(eType);
					System.out.println(eData);
				}
				if (eType.equals("event_pushed")) {
					System.out.println(eType);
				}
				if (eType.equals("replay_reset")) {
					System.out.println("replay reset");
				}
				if (eType.equals("playback_complete")) {
					System.out.println("playback finished");
					isReplaying = false;
				}

				// Custom events
				if (eType.equals("movePiece")) {
					int startx = eData.getItemForString("start_x").intVal();
					int starty = eData.getItemForString("start_y").intVal();
					System.out.println("Move from: " + startx + "," + starty);
					for (Piece piece : board.findActivePieces()) {
						if (board.findPiece(piece)[0] == startx
								&& board.findPiece(piece)[1] == starty) {
							int[] movement = {
									eData.getItemForString("target_x").intVal(),
									eData.getItemForString("target_y").intVal() };
							board.movePiece(piece, movement);
							break;
						}
					}
				}
				if (eType.equals("playback_complete")) {
					System.out.println("playback finished");
				}
			}
		};
	}
	/**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {
		Gdx.graphics.getGLCommon().glClear(
				GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		batch.begin();
		int height = Chess.SCREENHEIGHT;
		batch.draw(splashTexture, 0, 0);
		batch.draw(splashTexture2, 0, (float) ((float) height * 0.88));
		gameInfo.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		checkInfo.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		if (board.whoseTurn()) {
			info = "Black teams turn";
		} else {
			info = "White teams turn";
		}
		if (board.checkForCheck(true)) {
			teamCheck = "Black team in check";
			gameInfo.draw(batch, teamCheck, 600, 50);
		}
		if (board.checkForCheck(false)) {
			teamCheck = "White team in check";
			gameInfo.draw(batch, teamCheck, 600, 50);
		}
		Highscore b = hs.get(0);
		Highscore a = hs.get(1);
		int  c = a.score;
		int d = b.score;
		String player = players[0];
		//String text =  Integer.toString(c)+  Integer.toString(d);
		String wins = Integer.toString(d) + " win(s)";
		String losses = Integer.toString(c) + " loss(es)";
		gameInfo.draw(batch, player, 400, 80);
		gameInfo.draw(batch, wins, 400, 60);
		gameInfo.draw(batch, losses, 400, 40);
		//Camera updating options
		gameInfo.draw(batch, info, 600, 70);
		batch.end();
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		drawPieces();
		stage.draw();
		//Show possible moves on squares
		if (moving) {
			showPossibleMoves(movingPiece);
		}
		//Handle a replay
		if (isReplaying) {
			movePieceGraphic();
			replayHandler.runLoop();
		}
		movePieceGraphic();
		super.render();
	}

	public void reset() {
		board = new Board();
		movePieceGraphic();
		drawButton();
	}


	@Override
	public void resume() {
		super.resume();
	}

	/**
	 * Ends the game and allows replay if the last game was recorded
	 */
	private void finishGame(boolean loser, boolean stalemate) {
		System.err.println("GAME OVER");
	
		// loser was black i.e. not this player, increment achievement
		// if ((loser == true) && (!stalemate)) {
		// this.incrementAchievement("chess.winGame");
		// }
		if (!Multiplayer) {
			if ((!stalemate) && (loser)) {
				player1.logWin();
				hs = player1.getWinLoss();
			}
			if ((!stalemate) && (!loser)) {
				player1.logLoss();
				hs = player1.getWinLoss();
			}
		}
		
		System.err.println("wins" + " " +player1.getWin().toString());
		if (recording) {
			drawButton();
			replayHandler.endCurrentSession();
			replayHandler.finishRecording();
			stage.addActor(replayButton);
			board = new Board();
			movePieceGraphic();
			replayButton.setVisible(true);
			recording = false;
		} else {
			// reset board
			board = new Board();
			movePieceGraphic();
			drawButton();
		}
		return;
	}


	@Override
	public deco2800.arcade.model.Game getGame() {
		return null;
	}

	public void paint(Graphics g) {
	}

	public void startReplay(int num) {
		replayHandler.playbackLastSession();
		isReplaying = true;
	}

	@Override
	public boolean keyDown(int arg0) {
		if (arg0 == Keys.CONTROL_LEFT) {
			if (loadedStyle == styles.size() - 1) {
				loadedStyle = 0;
			} else {
				loadedStyle++;
			}

			setPiecePics();
			drawPieces();
		}
		return true;
	}

	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (!paused) {
			if (!moving) {
				movingPiece = checkSquare(x, y);
				try {
					if (board.isNullPiece(movingPiece)) {
						return false;
					}
					if (movingPiece.getTeam() == board.whoseTurn()) {
						moving = true;
						showPossibleMoves(movingPiece);
						return true;
					}
				} catch (NullPointerException e) {
					System.err.println("No valid square selected");
				}
				return false;
			} else {
				int[] newPos = determineSquare(x, y);
				int[] prevPos = board.findPiece(movingPiece);
				if (board.movePiece(movingPiece, newPos)) {
					if (recording) {
						replayHandler.pushEvent(ReplayNodeFactory
								.createReplayNode("movePiece", prevPos[0],
										prevPos[1], newPos[0], newPos[1]));

					}
					movePieceGraphic();

					// Push the move that was just performed
					moving = false;
					System.out.println(7);
					// if team in checkmate, gameover, log win/loss
					if (board.checkForCheckmate(board.whoseTurn())) {
						if (recording) {
							replayHandler.pushEvent(ReplayNodeFactory
									.createReplayNode("movePiece", prevPos[0],
											prevPos[1], newPos[0], newPos[1]));
						}
						this.finishGame(board.whoseTurn(), false);
					}
					/*
					 * If the easy computer opponent is playing, and black teams
					 * turn (computer controlled team)
					 */
					if (EasyComputerOpponent && board.whoseTurn()) {
						Piece AIPiece = board.chooseAIPiece();
						List<int[]> allowed = board.allowedMoves(AIPiece);
						System.out.println("AI is: " + AIPiece);
						System.out.println("Allowed Moves: ");
						for (int[] move : allowed) {
							System.out.print("[" + move[0] + ", " + move[1]
									+ "], ");
						}
						System.out.println("");
						List<int[]> removed = board.removeCheckMoves(AIPiece);
						System.out.println("Allowed Moves check removed: ");
						for (int[] move : removed) {
							System.out.print("[" + move[0] + ", " + move[1]
									+ "], ");
						}
						System.out.println("");
						int[] prevAI = board.findPiece(AIPiece);
						board.moveAIPieceEasy(AIPiece);
						int[] newAI = board.findPiece(AIPiece);
						if (recording) {
							replayHandler.pushEvent(ReplayNodeFactory
									.createReplayNode("movePiece", prevAI[0],
											prevAI[1], newAI[0], newAI[1]));
						}
						movePieceGraphic();

					}
					// if team in checkmate, gameover, log win/loss
					if (board.checkForCheckmate(board.whoseTurn())) {
						this.finishGame(board.whoseTurn(), false);
					}
					if (board.checkForStaleMate(board.whoseTurn())) {
						this.finishGame(board.whoseTurn(), true);
					}
					return true;
				}

				movingPiece = board.nullPiece;
				moving = false;
				return false;

			}
		}
		return true;

	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Takes in an x and y co-ordinate and returns which piece is on that
	 * square.
	 * 
	 * @param x
	 *            X co-ordinate of mouse click
	 * @param y
	 *            Y co-ordinate of mouse click
	 * @return Piece on the square that was clicked on
	 */
	private Piece checkSquare(int x, int y) {

		int[] square = determineSquare(x, y);

		if (square[0] < 0) {
			return null;
		} else if (square[1] < 0) {
			return null;
		}
		Piece onSquare;

		try {
			onSquare = board.getPiece(square);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return onSquare;
	}

	/**
	 * Determine the square that was clicked on.
	 * 
	 * @param x
	 *            x co-ordinate of the mouse click
	 * @param y
	 *            y co-ordinate of the mouse click
	 * @return An int[] represent the [row, column] of the square that was
	 *         clicked
	 */
	public int[] determineSquare(int x, int y) {
		int xSquare = -1;
		int ySquare = -1;

		// Determine x square
		for (int i = 0; i < 8; i++) {
			int j = i;
			if ((y >= (verticOff + pieceVerticOff + (59) * i))
					&& (y <= (verticOff + pieceVerticOff + (59) * (j + 1)))) {
				xSquare = i;
			}
		}
		// Determine y square
		for (int i = 0; i < 8; i++) {
			if ((x >= (horizOff + pieceHorizOff + (59) * i))
					&& (x <= (horizOff + pieceHorizOff + (59) * (i + 1)))) {
				ySquare = i;
			}
		}
		switch (xSquare) {
		case 0:
			xSquare = 7;
			break;
		case 1:
			xSquare = 6;
			break;
		case 2:
			xSquare = 5;
			break;
		case 3:
			xSquare = 4;
			break;
		case 4:
			xSquare = 3;
			break;
		case 5:
			xSquare = 2;
			break;
		case 6:
			xSquare = 1;
			break;
		case 7:
			xSquare = 0;
			break;
		default:
			xSquare = -1;
			break;
		}
		int[] returnValue = { xSquare, ySquare };
		return returnValue;
	}

	public void startMultiplayerGame() {
		paused = false;
	}

	private void showPossibleMoves(Piece piece) {
		startreplayButton.remove();
		replayButton.remove();
		List<int[]> possibleMoves = board.removeCheckMoves(movingPiece);
		Sprite allowedSquare = new Sprite(new Texture(
				Gdx.files.classpath("imgs/spot2.png")));
		List<Sprite> neededPics = new ArrayList<Sprite>();

		for (int i = 0; i < possibleMoves.size(); i++) {
			neededPics.add(allowedSquare);
			int xcoord = pieceHorizOff + horizOff + (59)
					* possibleMoves.get(i)[1];
			int ycoord = pieceVerticOff + verticOff + (59)
					* possibleMoves.get(i)[0];
			batch.begin();
			batch.draw(neededPics.get(i), xcoord, ycoord);
			batch.end();
		}

	}

	/**
	 * Initialised piece start positions as instance variables. This allows them
	 * to be accessed by other methods therefore allowing movements.
	 */
	void initPiecePos() {
		int[] pos = { 25 + horizOff, 25 + verticOff };
		whiteRook1Pos = pos;
		int[] pos1 = { 85 + horizOff, 25 + verticOff };
		whiteKnight1Pos = pos1;
		int[] pos2 = { 145 + horizOff, 25 + verticOff };
		whiteBishop1Pos = pos2;
		int[] pos3 = { 260 + horizOff, 25 + verticOff };
		whiteKingPos = pos3;
		int[] pos4 = { 205 + horizOff, 25 + verticOff };
		whiteQueenPos = pos4;
		int[] pos5 = { 320 + horizOff, 25 + verticOff };
		whiteBishop2Pos = pos5;
		int[] pos6 = { 380 + horizOff, 25 + verticOff };
		whiteKnight2Pos = pos6;
		int[] pos7 = { 435 + horizOff, 25 + verticOff };
		whiteRook2Pos = pos7;

		int[] pos8 = { 25 + horizOff, 435 + verticOff };
		blackRook1Pos = pos8;
		int[] pos9 = { 145 + horizOff, 435 + verticOff };
		blackKnight1Pos = pos9;
		int[] pos10 = { 85 + horizOff, 435 + verticOff };
		blackBishop1Pos = pos10;
		int[] pos11 = { 260 + horizOff, 435 + verticOff };
		blackKingPos = pos11;
		int[] pos12 = { 205 + horizOff, 435 + verticOff };
		blackQueenPos = pos12;
		int[] pos13 = { 320 + horizOff, 435 + verticOff };
		blackBishop2Pos = pos13;
		int[] pos14 = { 380 + horizOff, 435 + verticOff };
		blackKnight2Pos = pos14;
		int[] pos15 = { 435 + horizOff, 435 + verticOff };
		blackRook2Pos = pos15;

		int[] pos16 = { 25 + horizOff, 90 + verticOff };
		whitePawn0Pos = pos16;
		int[] pos17 = { 85 + horizOff, 90 + verticOff };
		whitePawn1Pos = pos17;
		int[] pos18 = { 145 + horizOff, 90 + verticOff };
		whitePawn2Pos = pos18;
		int[] pos19 = { 205 + horizOff, 90 + verticOff };
		whitePawn3Pos = pos19;
		int[] pos20 = { 260 + horizOff, 90 + verticOff };
		whitePawn4Pos = pos20;
		int[] pos21 = { 320 + horizOff, 90 + verticOff };
		whitePawn5Pos = pos21;
		int[] pos22 = { 380 + horizOff, 90 + verticOff };
		whitePawn6Pos = pos22;
		int[] pos23 = { 435 + horizOff, 90 + verticOff };
		whitePawn7Pos = pos23;

		int[] pos24 = { 25 + horizOff, 380 + verticOff };
		blackPawn0Pos = pos24;
		int[] pos25 = { 85 + horizOff, 380 + verticOff };
		blackPawn1Pos = pos25;
		int[] pos26 = { 145 + horizOff, 380 + verticOff };
		blackPawn2Pos = pos26;
		int[] pos27 = { 205 + horizOff, 380 + verticOff };
		blackPawn3Pos = pos27;
		int[] pos28 = { 260 + horizOff, 380 + verticOff };
		blackPawn4Pos = pos28;
		int[] pos29 = { 320 + horizOff, 380 + verticOff };
		blackPawn5Pos = pos29;
		int[] pos30 = { 380 + horizOff, 380 + verticOff };
		blackPawn6Pos = pos30;
		int[] pos31 = { 435 + horizOff, 380 + verticOff };
		blackPawn7Pos = pos31;
	
	}

	/**
	 * Moves all the pieces to their correct places on the board
	 */
	void movePieceGraphic() {
		
		ArrayList<Piece> whiteGraveyard = board.getGraveyard(false);
		ArrayList<Piece> blackGraveyard = board.getGraveyard(true);

		for (FixedSizeList<Piece> row : board.getBoardState()) {
			for (Piece piece : row) {
				if (piece.equals(board.whiteRook1)) {
					int[] correctPos = board.findPiece(board.whiteRook1);
					whiteRook1Pos[0] = (pieceHorizOff + horizOff + (59) * correctPos[1]);
					whiteRook1Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whiteKnight1)) {
					int[] correctPos = board.findPiece(board.whiteKnight1);
					whiteKnight1Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whiteKnight1Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whiteBishop1)) {
					int[] correctPos = board.findPiece(board.whiteBishop1);
					whiteBishop1Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whiteBishop1Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whiteQueen)) {
					int[] correctPos = board.findPiece(board.whiteQueen);
					whiteQueenPos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whiteQueenPos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whiteKing)) {
					int[] correctPos = board.findPiece(board.whiteKing);
					whiteKingPos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whiteKingPos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whiteBishop2)) {
					int[] correctPos = board.findPiece(board.whiteBishop2);
					whiteBishop2Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whiteBishop2Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whiteKnight2)) {
					int[] correctPos = board.findPiece(board.whiteKnight2);
					whiteKnight2Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whiteKnight2Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whiteRook2)) {
					int[] correctPos = board.findPiece(board.whiteRook2);
					whiteRook2Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whiteRook2Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whitePawn1)) {
					int[] correctPos = board.findPiece(board.whitePawn1);
					whitePawn0Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whitePawn0Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whitePawn2)) {
					int[] correctPos = board.findPiece(board.whitePawn2);
					whitePawn1Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whitePawn1Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whitePawn3)) {
					int[] correctPos = board.findPiece(board.whitePawn3);
					whitePawn2Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whitePawn2Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whitePawn4)) {
					int[] correctPos = board.findPiece(board.whitePawn4);
					whitePawn3Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whitePawn3Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whitePawn5)) {
					int[] correctPos = board.findPiece(board.whitePawn5);
					whitePawn4Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whitePawn4Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whitePawn6)) {
					int[] correctPos = board.findPiece(board.whitePawn6);
					whitePawn5Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whitePawn5Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whitePawn7)) {
					int[] correctPos = board.findPiece(board.whitePawn7);
					whitePawn6Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whitePawn6Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.whitePawn8)) {
					int[] correctPos = board.findPiece(board.whitePawn8);
					whitePawn7Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					whitePawn7Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackRook1)) {
					int[] correctPos = board.findPiece(board.blackRook1);
					blackRook1Pos[0] = (pieceHorizOff + horizOff + (59) * correctPos[1]);
					blackRook1Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackKnight1)) {
					int[] correctPos = board.findPiece(board.blackKnight1);
					blackKnight1Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackKnight1Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackBishop1)) {
					int[] correctPos = board.findPiece(board.blackBishop1);
					blackBishop1Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackBishop1Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackQueen)) {
					int[] correctPos = board.findPiece(board.blackQueen);
					blackQueenPos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackQueenPos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackKing)) {
					int[] correctPos = board.findPiece(board.blackKing);
					blackKingPos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackKingPos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackBishop2)) {
					int[] correctPos = board.findPiece(board.blackBishop2);
					blackBishop2Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackBishop2Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackKnight2)) {
					int[] correctPos = board.findPiece(board.blackKnight2);
					blackKnight2Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackKnight2Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackRook2)) {
					int[] correctPos = board.findPiece(board.blackRook2);
					blackRook2Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackRook2Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackPawn1)) {
					int[] correctPos = board.findPiece(board.blackPawn1);
					blackPawn0Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackPawn0Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackPawn2)) {
					int[] correctPos = board.findPiece(board.blackPawn2);
					blackPawn1Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackPawn1Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackPawn3)) {
					int[] correctPos = board.findPiece(board.blackPawn3);
					blackPawn2Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackPawn2Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackPawn4)) {
					int[] correctPos = board.findPiece(board.blackPawn4);
					blackPawn3Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackPawn3Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackPawn5)) {
					int[] correctPos = board.findPiece(board.blackPawn5);
					blackPawn4Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackPawn4Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackPawn6)) {
					int[] correctPos = board.findPiece(board.blackPawn6);
					blackPawn5Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackPawn5Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackPawn7)) {
					int[] correctPos = board.findPiece(board.blackPawn7);
					blackPawn6Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackPawn6Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				} else if (piece.equals(board.blackPawn8)) {
					int[] correctPos = board.findPiece(board.blackPawn8);
					blackPawn7Pos[0] = (pieceHorizOff + horizOff + (59) * (correctPos[1]));
					blackPawn7Pos[1] = (pieceVerticOff + verticOff + (59) * correctPos[0]);
				}

			}

		}

		int blackCount = 0;
		for (Piece piece : blackGraveyard) {
			if (piece.equals(board.blackRook1)) {
				blackCount++;
				blackRook1Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackRook1Pos[0] -= 59;
				}
				blackRook1Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard.indexOf(board.blackRook1)) % (59 * 8));
			} else if (piece.equals(board.blackKnight1)) {
				blackCount++;
				blackKnight1Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackKnight1Pos[0] -= 59;
				}
				blackKnight1Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard
								.indexOf(board.blackKnight1)) % (59 * 8));
			} else if (piece.equals(board.blackBishop1)) {
				blackCount++;
				blackBishop1Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackBishop1Pos[0] -= 59;
				}
				blackBishop1Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard
								.indexOf(board.blackBishop1)) % (59 * 8));
			} else if (piece.equals(board.blackQueen)) {
				blackCount++;
				blackQueenPos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackQueenPos[0] -= 59;
				}
				blackQueenPos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard.indexOf(board.blackQueen)) % (59 * 8));
			} else if (piece.equals(board.blackKing)) {
				blackCount++;
				blackKingPos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackKingPos[0] -= 59;
				}
				blackKingPos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard.indexOf(board.blackKing)) % (59 * 8));
			} else if (piece.equals(board.blackBishop2)) {
				blackCount++;
				blackBishop2Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackBishop2Pos[0] -= 59;
				}
				blackBishop2Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard
								.indexOf(board.blackBishop2)) % (59 * 8));
			} else if (piece.equals(board.blackKnight2)) {
				blackCount++;
				blackKnight2Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackKnight2Pos[0] -= 59;
				}
				blackKnight2Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard
								.indexOf(board.blackKnight2)) % (59 * 8));
			} else if (piece.equals(board.blackRook2)) {
				blackCount++;
				blackRook2Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackRook2Pos[0] -= 59;
				}
				blackRook2Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard.indexOf(board.blackRook2)) % (59 * 8));
			} else if (piece.equals(board.blackPawn1)) {
				blackCount++;
				blackPawn0Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackPawn0Pos[0] -= 59;
				}
				blackPawn0Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard.indexOf(board.blackPawn1)) % (59 * 8));
			} else if (piece.equals(board.blackPawn2)) {
				blackCount++;
				blackPawn1Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackPawn1Pos[0] -= 59;
				}
				blackPawn1Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard.indexOf(board.blackPawn2)) % (59 * 8));
			} else if (piece.equals(board.blackPawn3)) {
				blackCount++;
				blackPawn2Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackPawn2Pos[0] -= 59;
				}
				blackPawn2Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard.indexOf(board.blackPawn3)) % (59 * 8));
			} else if (piece.equals(board.blackPawn4)) {
				blackCount++;
				blackPawn3Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackPawn3Pos[0] -= 59;
				}
				blackPawn3Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard.indexOf(board.blackPawn4)) % (59 * 8));
			} else if (piece.equals(board.blackPawn5)) {
				blackCount++;
				blackPawn4Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackPawn4Pos[0] -= 59;
				}
				blackPawn4Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard.indexOf(board.blackPawn5)) % (59 * 8));
			} else if (piece.equals(board.blackPawn6)) {
				blackCount++;
				blackPawn5Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackPawn5Pos[0] -= 59;
				}
				blackPawn5Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard.indexOf(board.blackPawn6)) % (59 * 8));
			} else if (piece.equals(board.blackPawn7)) {
				blackCount++;
				blackPawn6Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackPawn6Pos[0] -= 59;
				}
				blackPawn6Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard.indexOf(board.blackPawn7)) % (59 * 8));
			} else if (piece.equals(board.blackPawn8)) {
				blackCount++;
				blackPawn7Pos[0] = (horizOff - 59);
				if (blackCount > 8) {
					blackPawn7Pos[0] -= 59;
				}
				blackPawn7Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * blackGraveyard.indexOf(board.blackPawn8)) % (59 * 8));
			}
		}
		int whiteCount = 0;
		for (Piece piece : whiteGraveyard) {
			if (piece.equals(board.whiteRook1)) {
				whiteCount++;
				whiteRook1Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whiteRook1Pos[0] += 59;
				}
				whiteRook1Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard.indexOf(board.whiteRook1)) % (59 * 8));
			} else if (piece.equals(board.whiteKnight1)) {
				whiteCount++;
				whiteKnight1Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whiteKnight1Pos[0] += 59;
				}
				whiteKnight1Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard
								.indexOf(board.whiteKnight1)) % (59 * 8));
			} else if (piece.equals(board.whiteBishop1)) {
				whiteCount++;
				whiteBishop1Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whiteBishop1Pos[0] += 59;
				}
				whiteBishop1Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard
								.indexOf(board.whiteBishop1)) % (59 * 8));
			} else if (piece.equals(board.whiteQueen)) {
				whiteCount++;
				whiteQueenPos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whiteQueenPos[0] += 59;
				}
				whiteQueenPos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard.indexOf(board.whiteQueen)) % (59 * 8));
			} else if (piece.equals(board.whiteKing)) {
				whiteCount++;
				whiteKingPos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whiteKingPos[0] += 59;
				}
				whiteKingPos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard.indexOf(board.whiteKing)) % (59 * 8));
			} else if (piece.equals(board.whiteBishop2)) {
				whiteCount++;
				whiteBishop2Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whiteBishop2Pos[0] += 59;
				}
				whiteBishop2Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard
								.indexOf(board.whiteBishop2)) % (59 * 8));
			} else if (piece.equals(board.whiteKnight2)) {
				whiteCount++;
				whiteKnight2Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whiteKnight2Pos[0] += 59;
				}
				whiteKnight2Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard
								.indexOf(board.whiteKnight2)) % (59 * 8));
			} else if (piece.equals(board.whiteRook2)) {
				whiteCount++;
				whiteRook2Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whiteRook2Pos[0] += 59;
				}
				whiteRook2Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard.indexOf(board.whiteRook2)) % (59 * 8));
			} else if (piece.equals(board.whitePawn1)) {
				whiteCount++;
				whitePawn0Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whitePawn0Pos[0] += 59;
				}
				whitePawn0Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard.indexOf(board.whitePawn1)) % (59 * 8));
			} else if (piece.equals(board.whitePawn2)) {
				whiteCount++;
				whitePawn1Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whitePawn1Pos[0] += 59;
				}
				whitePawn1Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard.indexOf(board.whitePawn2)) % (59 * 8));
			} else if (piece.equals(board.whitePawn3)) {
				whiteCount++;
				whitePawn2Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whitePawn2Pos[0] += 59;
				}
				whitePawn2Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard.indexOf(board.whitePawn3)) % (59 * 8));
			} else if (piece.equals(board.whitePawn4)) {
				whiteCount++;
				whitePawn3Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whitePawn3Pos[0] += 59;
				}
				whitePawn3Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard.indexOf(board.whitePawn4)) % (59 * 8));
			} else if (piece.equals(board.whitePawn5)) {
				whiteCount++;
				whitePawn4Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whitePawn4Pos[0] += 59;
				}
				whitePawn4Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard.indexOf(board.whitePawn5)) % (59 * 8));
			} else if (piece.equals(board.whitePawn6)) {
				whiteCount++;
				whitePawn5Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whitePawn5Pos[0] += 59;
				}
				whitePawn5Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard.indexOf(board.whitePawn6)) % (59 * 8));
			} else if (piece.equals(board.whitePawn7)) {
				whiteCount++;
				whitePawn6Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whitePawn6Pos[0] += 59;
				}
				whitePawn6Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard.indexOf(board.whitePawn7)) % (59 * 8));
			} else if (piece.equals(board.whitePawn8)) {
				whiteCount++;
				whitePawn7Pos[0] = (horizOff + 512);
				if (whiteCount > 8) {
					whitePawn7Pos[0] += 59;
				}
				whitePawn7Pos[1] = pieceVerticOff
						+ verticOff
						+ ((59 * whiteGraveyard.indexOf(board.whitePawn8)) % (59 * 8));
			}
		}
	}

	void drawPieces() {
		whiteRook2 = whiteRook1;
		whiteBishop2 = whiteBishop1;
		whiteKnight2 = whiteKnight1;
		blackBishop2 = blackBishop1;
		blackKnight2 = blackKnight1;
		blackRook2 = blackRook1;
		whitePawn1 = whitePawn0;
		whitePawn2 = whitePawn0;
		whitePawn3 = whitePawn0;
		whitePawn4 = whitePawn0;
		whitePawn5 = whitePawn0;
		whitePawn6 = whitePawn0;
		whitePawn7 = whitePawn0;
		blackPawn1 = blackPawn0;
		blackPawn2 = blackPawn0;
		blackPawn3 = blackPawn0;
		blackPawn4 = blackPawn0;
		blackPawn5 = blackPawn0;
		blackPawn6 = blackPawn0;
		blackPawn7 = blackPawn0;

		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		// Board - blue
		batch.draw(chessBoard, horizOff, verticOff);

		// black pieces - dark blue
		batch.draw(blackRook1, blackRook1Pos[0], blackRook1Pos[1]);
		batch.draw(blackKnight1, blackKnight1Pos[0], blackKnight1Pos[1]);
		batch.draw(blackBishop1, blackBishop1Pos[0], blackBishop1Pos[1]);
		batch.draw(blackQueen, blackQueenPos[0], blackQueenPos[1]);
		batch.draw(blackKing, blackKingPos[0], blackKingPos[1]);
		batch.draw(blackBishop2, blackBishop2Pos[0], blackBishop2Pos[1]);
		batch.draw(blackKnight2, blackKnight2Pos[0], blackKnight2Pos[1]);
		batch.draw(blackRook2, blackRook2Pos[0], blackRook2Pos[1]);
		// whitepieces - light blue
		batch.draw(whiteRook1, whiteRook1Pos[0], whiteRook1Pos[1]);
		batch.draw(whiteBishop1, whiteBishop1Pos[0], whiteBishop1Pos[1]);
		batch.draw(whiteKnight1, whiteKnight1Pos[0], whiteKnight1Pos[1]);
		batch.draw(whiteQueen, whiteQueenPos[0], whiteQueenPos[1]);
		batch.draw(whiteKing, whiteKingPos[0], whiteKingPos[1]);
		batch.draw(whiteBishop2, whiteBishop2Pos[0], whiteBishop2Pos[1]);
		batch.draw(whiteKnight2, whiteKnight2Pos[0], whiteKnight2Pos[1]);
		batch.draw(whiteRook2, whiteRook2Pos[0], whiteRook2Pos[1]);
		// pawns white
		batch.draw(whitePawn0, whitePawn0Pos[0], whitePawn0Pos[1]);
		batch.draw(whitePawn1, whitePawn1Pos[0], whitePawn1Pos[1]);
		batch.draw(whitePawn2, whitePawn2Pos[0], whitePawn2Pos[1]);
		batch.draw(whitePawn3, whitePawn3Pos[0], whitePawn3Pos[1]);
		batch.draw(whitePawn4, whitePawn4Pos[0], whitePawn4Pos[1]);
		batch.draw(whitePawn5, whitePawn5Pos[0], whitePawn5Pos[1]);
		batch.draw(whitePawn6, whitePawn6Pos[0], whitePawn6Pos[1]);
		batch.draw(whitePawn7, whitePawn7Pos[0], whitePawn7Pos[1]);
		// pawns black
		batch.draw(blackPawn0, blackPawn0Pos[0], blackPawn0Pos[1]);
		batch.draw(blackPawn1, blackPawn1Pos[0], blackPawn1Pos[1]);
		batch.draw(blackPawn2, blackPawn2Pos[0], blackPawn2Pos[1]);
		batch.draw(blackPawn3, blackPawn3Pos[0], blackPawn3Pos[1]);
		batch.draw(blackPawn4, blackPawn4Pos[0], blackPawn4Pos[1]);
		batch.draw(blackPawn5, blackPawn5Pos[0], blackPawn5Pos[1]);
		batch.draw(blackPawn6, blackPawn6Pos[0], blackPawn6Pos[1]);
		batch.draw(blackPawn7, blackPawn7Pos[0], blackPawn7Pos[1]);
		batch.end();
		// Im sorry i just made an even more awfulling long code longer

	}

	private void setPiecePics() {
		chessBoard = new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/board.png"));

		blackBishop1 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/Black B.png")));
		blackRook1 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/Black R.png")));
		blackKnight1 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/Black N.png")));
		blackKing = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/Black K.png")));
		blackQueen = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/Black Q.png")));
		blackPawn0 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/Black P.png")));
		whiteBishop1 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/White B.png")));
		whiteRook1 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/White R.png")));
		whiteKnight1 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/White N.png")));
		whiteKing = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/White K.png")));
		whiteQueen = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/White Q.png")));
		whitePawn0 = new Sprite(new Texture(Gdx.files.classpath("imgs/"
				+ styles.get(loadedStyle) + "/White P.png")));
	}
	@Override
	public void hide() {

	}

	@Override
	public void render(float arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		drawButton();
	}

	public void makeButtons() {
		splashTexture = new Texture(Gdx.files.internal("chess.png"));
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		splashTexture2 = new Texture(Gdx.files.internal("chessTitle.png"));
		splashTexture2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		splashSprite = new Sprite(splashTexture);
		// moves sprite to centre of screen
		splashSprite.setX(Gdx.graphics.getWidth() / 2
				- (splashSprite.getWidth() / 2));
		splashSprite.setY(Gdx.graphics.getHeight() / 2
				- (splashSprite.getHeight() / 2));
		batch = new SpriteBatch();
		map = new TextureAtlas("b.pack");
		skin = new Skin();
		skin.addRegions(map);
		BmFontB = new BitmapFont(Gdx.files.internal("imgs/gameFont2.fnt"),
				false);

		int width = Chess.SCREENWIDTH;
		int height = Chess.SCREENHEIGHT;

		stage = new Stage(width, height, true);

		ArcadeInputMux.getInstance().addProcessor(stage);

		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = BmFontB;

		backButton = new TextButton("Quit to Menu", style);
		backButton.setWidth(200);
		backButton.setHeight(50);
		backButton.setX((float) (width * 0.02));
		backButton.setY((float) (height * 0.02));

		replayButton = new TextButton("Replay", style);
		replayButton.setWidth(200);
		replayButton.setHeight(50);
		replayButton.setX((float) (width * 0.78));
		replayButton.setY((float) (height * 0.68));

		startreplayButton = new TextButton("Record The Next Game", style);
		startreplayButton.setWidth(300);
		startreplayButton.setHeight(50);
		startreplayButton.setX((float) (width * 0.6));
		startreplayButton.setY((float) (height * 0.02));

		newGameButton = new TextButton("Start New Game", style);
		newGameButton.setWidth(200);
		newGameButton.setHeight(50);
		newGameButton.setX((float) (width * 0.78));
		newGameButton.setY((float) (height * 0.28));

		newGameButtonEasy = new TextButton("Easy Computer Game", style);
		newGameButtonEasy.setWidth(300);
		newGameButtonEasy.setHeight(50);
		newGameButtonEasy.setX((float) (width * 0.02));
		newGameButtonEasy.setY((float) (height * 0.70));

	}

	public void drawButton() {
		makeButtons();
		backButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				setScreen(menuScreen);
			}
		});
		replayButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				drawButton();
				try {
					board = new Board();
					movePieceGraphic();
					recording = false;
					replayHandler.playbackLastSession();
					isReplaying = true;

				} catch (NullPointerException e) {
					System.out.println("nothing to replay");
				}
			}
		});
		// record the next game
		startreplayButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				try {
					recording = true;
					replayHandler.startRecording();
				} catch (Exception e) {
					System.out.println("Already Recording");
				}

			}
		});
		newGameButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				drawButton();
				if (recording) {
					replayHandler.endCurrentSession();
					replayHandler.finishRecording();
					replayButton.setVisible(true);
				}
				EasyComputerOpponent = false;
				HardComputerOpponent = false;
				recording = false;
				board = new Board();
				movePieceGraphic();
			}
		});
		newGameButtonEasy.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				drawButton();
				if (recording) {
					replayHandler.endCurrentSession();
					replayHandler.finishRecording();
					replayButton.setVisible(true);
				}
				EasyComputerOpponent = true;
				HardComputerOpponent = false;
				recording = false;
				board = new Board();
				movePieceGraphic();
			}
		});
		stage.addActor(replayButton);
		replayButton.setVisible(false);
		stage.addActor(backButton);
		stage.addActor(startreplayButton);
		stage.addActor(newGameButton);
		stage.addActor(newGameButtonEasy);
	}
}
