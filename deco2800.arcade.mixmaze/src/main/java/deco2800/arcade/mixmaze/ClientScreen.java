package deco2800.arcade.mixmaze;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.mixmaze.domain.IMixMazeModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;

import java.io.IOException;

import static com.badlogic.gdx.graphics.Color.RED;
import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.badlogic.gdx.graphics.GL20.*;

class ClientScreen extends GameScreen {

	private Client client;
	private ObjectSpace os;
	private int timeLimit;

	ClientScreen(final MixMaze game) {
		super(game);
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0.13f, 0.13f, 0.13f, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

		if (endGameTable.isVisible() && backMenu.isChecked()) {
			backMenu.toggle();
			endGameTable.setVisible(false);
			tileTable.clear();
			gameArea.clear();
			game.setScreen(game.menuScreen);
		}
	}

	@Override
	protected void newGame() {
		logger.info("this is client");
		model = null;

		os = new ObjectSpace();
		client = new Client();
		HostScreen.register(client);
		client.start();
		logger.debug("trying to connect");
		try {
			client.connect(5000, "127.0.0.1", 64532, 65532);
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		client.addListener(new ClientListener());
		client.sendTCP("request: game model");

		while (model == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.warn(e.getMessage());
			}
		}
		timeLimit = model.getGameMaxTime();
		setupGameBoard();
		os.addConnection(client);
		client.sendTCP("response: client viewers");
	}

	@Override
	protected void setupGameBoard() {
		TileViewModel tile;
		int boardSize = model.getBoardSize();
		int tileSize = 640 / boardSize;

		for (int j = 0; j < boardSize; j++) {
			for (int i = 0; i < boardSize; i++) {
				tile = new TileViewModel(i, j, tileSize, renderer);
				tileTable.add(tile).size(tileSize, tileSize);
				os.register(1700 + j * 100 + i, tile);
			}
			if (j < boardSize)
				tileTable.row();
		}

		p1 = new PlayerViewModel(model, tileSize, 1, p1Controls, scorebar[0],
				left, GameScreen.p1HeadRegion);
		p2 = new PlayerViewModel(model, tileSize, 2, p2Controls, scorebar[1],
				right, GameScreen.p2HeadRegion);
		os.register(101, p1);
		os.register(102, p2);
		gameArea.addActor(p1);
		gameArea.addActor(p2);
		scorebar[0].setBoxColor(p1.getColor());
		scorebar[1].setBoxColor(p2.getColor());
		left.setPlayerName("kate_is_kewl");
		right.setPlayerName("mixMAZEr0x");
	}

	@Override
	protected void setupTimer(int timeLimit) {
		LabelStyle style = timerLabel.getStyle();
		style.fontColor = WHITE;
		timerLabel.setStyle(style);

		countdown = timeLimit - 1;
		Timer.schedule(new Timer.Task() {
			public void run() {
				int min = countdown / 60;
				int sec = countdown % 60;

				if (countdown == 10) {
					LabelStyle style = timerLabel.getStyle();
					style.fontColor = RED;
					timerLabel.setStyle(style);
				}
				timerLabel.setText(String.format("%s%d:%s%d", (min < 10) ? "0"
						: "", min, (sec < 10) ? "0" : "", sec));
				countdown -= 1;
			}
		}, 0, 1, timeLimit - 1);
		Timer.schedule(new Timer.Task() {
			public void run() {
				stage.setKeyboardFocus(null);
				endGameTable.setVisible(true);
			}
		}, timeLimit);
	}

	private class ClientListener extends Listener {
		public void received(Connection c, Object o) {
			if (o instanceof String) {
				String msg = (String) o;

				logger.debug("string received: {}", msg);
				if ("response: game model".equals(msg)) {
					model = ObjectSpace.getRemoteObject(c, 42,
							IMixMazeModel.class);
					((RemoteObject) model).setTransmitExceptions(false);
				} else if ("signal: game started".equals(msg)) {
					setupTimer(timeLimit);
					ArcadeInputMux.getInstance().addProcessor(stage);
					stage.setKeyboardFocus(gameArea);
				}
			}
		}
	}
}
