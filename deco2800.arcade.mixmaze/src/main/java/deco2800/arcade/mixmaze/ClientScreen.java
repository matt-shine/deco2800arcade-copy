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
	private int boardSize;
	private ITileModel[][] remoteTile;
	private IPlayerModel[] remotePlayer;

	ClientScreen(final MixMaze game) {
		super(game);
	}

	@Override
	protected void newGame() {
		model = null;

		logger.info("this is client");
		client = new Client();
		HostScreen.register(client);
		client.start();
		logger.debug("trying to connect");
		try {
			client.connect(5000, "127.0.0.1", 64532, 65532);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		client.addListener(new Listener() {
			public void received(Connection c, Object o) {
				if (o instanceof String) {
					logger.debug("string received: "
							+ o);
					model = ObjectSpace.getRemoteObject(c, 42, IMixMazeModel.class);
					logger.debug("model: " + model);
				}
			}
		});
		client.sendTCP("give me model");

		while (model == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		boardSize = model.getBoardSize();
		remoteTile = new ITileModel[boardSize][boardSize];
		for (int j = 0; j < boardSize; j++)
			for (int i = 0; i < boardSize; i++)
				remoteTile[i][j] = ObjectSpace.getRemoteObject(client, 100 + j*boardSize + i, ITileModel.class);
		remotePlayer = new IPlayerModel[2];
		remotePlayer[0] = ObjectSpace.getRemoteObject(client, 1700,
				IPlayerModel.class);
		remotePlayer[1] = ObjectSpace.getRemoteObject(client, 1701,
				IPlayerModel.class);
		setupGameBoard();
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0.13f, 0.13f, 0.13f, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		/*
		left.update(p1);
		right.update(p2);
		scorebar[0].update(p1);
		scorebar[1].update(p2);
		*/

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
		logger.debug("boardSize: {}", boardSize);
		int tileSize = 640 / boardSize;

		/*
		for (int j = 0; j < boardSize; j++) {
			for (int i = 0; i < boardSize; i++) {
				tileTable.add(new TileViewModel(
						remoteTile[i][j],
						model,
						tileSize,
						renderer))
						.size(tileSize, tileSize);
			}
			if (j < boardSize)
				tileTable.row();
		}
		*/

		/*
		p1 = new PlayerViewModel(remotePlayer[0], model, tileSize,
				1,new Settings().p1Controls, scorebar[0]);
		p2 = new PlayerViewModel(remotePlayer[1], model, tileSize,
				2,new Settings().p2Controls, scorebar[1]);
		gameArea.addActor(p1);
		gameArea.addActor(p2);
		*/
	}

}
