package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.view.IMixMazeModel;
import deco2800.arcade.mixmaze.domain.view.IMixMazeModel.Difficulty;
import deco2800.arcade.mixmaze.domain.view.IPlayerModel;
import deco2800.arcade.mixmaze.domain.view.ITileModel;

import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;

import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.GL20.*;

class ClientScreen extends GameScreen {

	private Client client;
	private ObjectSpace os;
	private int boardSize;
	private ITileModel[][] remoteTile;
	private IPlayerModel[] remotePlayer;

	ClientScreen(final MixMaze game) {
		super(game);
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
		setupGameBoard();
		os.addConnection(client);
		client.sendTCP("response: client viewers");
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0.13f, 0.13f, 0.13f, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

		/*
		if (endGameTable.isVisible() && backMenu.isChecked()) {
			backMenu.toggle();
			endGameTable.setVisible(false);
			tileTable.clear();
			gameArea.clear();
			game.setScreen(game.menuScreen);
		}
		*/
	}

	private void setupGameBoard() {
		TileViewModel tile;
		int boardSize = model.getBoardSize();
		int tileSize = 640 / boardSize;

		for (int j = 0; j < boardSize; j++) {
			for (int i = 0; i < boardSize; i++) {
				tile = new TileViewModel(i, j,
						tileSize, renderer);
				tileTable.add(tile).size(tileSize, tileSize);
				os.register(1700 + j*100 + i, tile);
			}
			if (j < boardSize)
				tileTable.row();
		}

		p1 = new PlayerViewModel(model, tileSize,
				1, new Settings().p1Controls, scorebar[0],
				left);
		p2 = new PlayerViewModel(model, tileSize,
				2, new Settings().p2Controls, scorebar[1],
				right);
		os.register(101, p1);
		os.register(102, p2);
		gameArea.addActor(p1);
		gameArea.addActor(p2);
		scorebar[0].setBoxColor(p1.getColor());
		scorebar[1].setBoxColor(p2.getColor());
		left.setPlayerName("kate_is_kewl");
		right.setPlayerName("mixMAZEr0x");
	}

	public class ClientListener extends Listener {

		public void received(Connection c, Object o) {
			if (o instanceof String) {
				String msg = (String) o;

				logger.debug("string received: {}", msg);
				if ("response: game model".equals(msg)) {
					model = ObjectSpace.getRemoteObject(c,
							42,
							IMixMazeModel.class);
					((RemoteObject) model).setTransmitExceptions(false);
				} else if ("signal: game started".equals(msg)) {
					Gdx.input.setInputProcessor(stage);
					stage.setKeyboardFocus(gameArea);
				}
			}
		}

	}

}
