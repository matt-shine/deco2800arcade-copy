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
	private ObjectSpace os;

	HostScreen(final MixMaze game) {
		super(game);
	}

	@Override
	protected void newGame() {
		model = new MixMazeModel(10, BEGINNER, 60*2);

		server = new Server();
		register(server);
		server.start();
		try {
			server.bind(64532, 65532);
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		server.addListener(new Listener() {
			public void received(Connection c, Object o) {
				if (o instanceof String) {
					logger.debug("string received: "
							+ o);
					os = new ObjectSpace();
					os.register(42, model);
					os.register(1700, model.getPlayer1());
					os.register(1701, model.getPlayer2());
					int boardSize = model.getBoardSize();
					for (int j = 0; j < boardSize; j++)
						for (int i = 0; i < boardSize; i++)
							os.register(100 + j*boardSize + i, model.getBoardTile(i, j));
					os.addConnection(c);
					c.sendTCP(
						"sending model");
					logger.info("connection accepted and game started");
					setupTimer();
					startGame();
				}
			}
		});
		logger.info("host waiting for connection");
		setupGameBoard();
	}

	static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();

		ObjectSpace.registerClasses(kryo);
		kryo.register(IMixMazeModel.class);
		kryo.register(IPlayerModel.class);
		kryo.register(ITileModel.class);
		kryo.register(ItemType.class);
	}

}
