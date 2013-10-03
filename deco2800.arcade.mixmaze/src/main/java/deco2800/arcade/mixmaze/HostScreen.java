package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.MixMazeModel;
import deco2800.arcade.mixmaze.domain.view.IItemModel.ItemType;
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

import static deco2800.arcade.mixmaze.domain.view.IMixMazeModel.Difficulty.*;
import static com.badlogic.gdx.graphics.Color.*;

class HostScreen extends LocalScreen {

	private Server server;
	private Connection client;
	private ObjectSpace os;
	private PlayerNetworkView p1;
	private PlayerNetworkView p2;
	private boolean isConnected;

	HostScreen(final MixMaze game) {
		super(game);
	}

	@Override
	protected void newGame() {
		model = new MixMazeModel(5, BEGINNER, 60*2);
		isConnected = false;

		os = new ObjectSpace();
		server = new Server();
		register(server);
		server.start();
		try {
			server.bind(64532, 65532);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		server.addListener(new HostListener());
		logger.info("host waiting for connection");
		setupGameBoard();

		while (!isConnected) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		model.getPlayer1().addViewer(p1);
		model.getPlayer2().addViewer(p2);
		setupTimer();
		startGame();
		client.sendTCP("signal: game started");
	}

	static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();

		ObjectSpace.registerClasses(kryo);
		kryo.register(IMixMazeModel.class);
		kryo.register(TileNetworkView.class);
		kryo.register(PlayerNetworkView.class);
		kryo.register(IPlayerModel.Action.class);
		kryo.register(ItemType.class);
		/*
		kryo.register(IllegalStateException.class);
		kryo.register(IllegalArgumentException.class);
		*/
	}

	public class HostListener extends Listener {

		public void received(Connection c, Object o) {
			if (o instanceof String) {
				String msg = (String) o;

				logger.debug("string received: {}", msg);
				if ("request: game model".equals(msg)) {
					sendModel(c);
				} else if ("response: client viewers"
						.equals(msg)) {
					recvViewers(c);
					client = c;
					isConnected = true;
				}
			}
		}

		private void sendModel(Connection c) {
			os.register(42, model);
			os.addConnection(c);
			c.sendTCP("response: game model");
		}

		private void recvViewers(Connection c) {
			int boardSize = model.getBoardSize();
			TileNetworkView t;

			for (int j = 0; j < boardSize; j++)
				for (int i = 0; i < boardSize; i++) {
					t = ObjectSpace.getRemoteObject(c,
							1700 + j*100 + i,
							TileNetworkView.class);
					((RemoteObject) t).setNonBlocking(true);
					((RemoteObject) t).setTransmitExceptions(false);
					model.getBoardTile(i, j).addViewer(t);
				}

			p1 = ObjectSpace.getRemoteObject(c, 101,
					PlayerNetworkView.class);
			((RemoteObject) p1).setNonBlocking(true);
			((RemoteObject) p1).setTransmitExceptions(false);
			p2 = ObjectSpace.getRemoteObject(c, 102,
					PlayerNetworkView.class);
			((RemoteObject) p2).setNonBlocking(true);
			((RemoteObject) p2).setTransmitExceptions(false);
		}

	}

}
