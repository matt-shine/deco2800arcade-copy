package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.IMixMazeModel;
import deco2800.arcade.mixmaze.domain.MixMazeModel;
import deco2800.arcade.mixmaze.domain.PlayerModelObserver;
import deco2800.arcade.mixmaze.domain.TileModelObserver;
import deco2800.arcade.mixmaze.domain.ItemModel;
import deco2800.arcade.mixmaze.domain.PlayerModel;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;
import com.esotericsoftware.kryonet.rmi.RemoteObject;
import java.io.IOException;

import static deco2800.arcade.mixmaze.domain.MixMazeModel.Difficulty.*;

class HostScreen extends LocalScreen {

	private Server server;
	private Connection client;
	private ObjectSpace os;
	private PlayerModelObserver p1;
	private PlayerModelObserver p2;
	private boolean isConnected;

	HostScreen(final MixMaze game) {
		super(game);
	}

	@Override
	protected void newGame() {
		model = new MixMazeModel(5, BEGINNER, 60 * 2);
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
		model.getPlayer(1).addViewer(p1);
		model.getPlayer(2).addViewer(p2);
		client.sendTCP("signal: game started");
		setupTimer(model.getGameMaxTime());
		startGame();
	}

	static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();

		ObjectSpace.registerClasses(kryo);
		kryo.register(IMixMazeModel.class);
		kryo.register(TileModelObserver.class);
		kryo.register(PlayerModelObserver.class);
		kryo.register(PlayerModel.Action.class);
		kryo.register(ItemModel.Type.class);
		/*
		 * kryo.register(IllegalStateException.class);
		 * kryo.register(IllegalArgumentException.class);
		 */
	}

	private class HostListener extends Listener {

		public void received(Connection c, Object o) {
			if (o instanceof String) {
				String msg = (String) o;

				logger.debug("string received: {}", msg);
				if ("request: game model".equals(msg)) {
					sendModel(c);
				} else if ("response: client viewers".equals(msg)) {
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
			TileModelObserver t;

			for (int j = 0; j < boardSize; j++){
				for (int i = 0; i < boardSize; i++) {
					t = ObjectSpace.getRemoteObject(c, 1700 + j * 100 + i,
							TileModelObserver.class);
					((RemoteObject) t).setNonBlocking(true);
					((RemoteObject) t).setTransmitExceptions(false);
					model.getBoardTile(i, j).addObserver(t);
				}
			}

			p1 = ObjectSpace.getRemoteObject(c, 101, PlayerModelObserver.class);
			((RemoteObject) p1).setNonBlocking(true);
			((RemoteObject) p1).setTransmitExceptions(false);
			p2 = ObjectSpace.getRemoteObject(c, 102, PlayerModelObserver.class);
			((RemoteObject) p2).setNonBlocking(true);
			((RemoteObject) p2).setTransmitExceptions(false);
		}

	}

}
