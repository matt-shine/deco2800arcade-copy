/*
 * HostScreen
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.Direction;
import deco2800.arcade.mixmaze.domain.MixMazeModel;
import deco2800.arcade.mixmaze.domain.PlayerModel;
import deco2800.arcade.mixmaze.domain.WallModel;
import deco2800.arcade.mixmaze.domain.view.IMixMazeModel;
import deco2800.arcade.mixmaze.domain.view.IMixMazeModel.MixMazeDifficulty;
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

class HostScreen extends GameScreen {

	private Server server;

	HostScreen(final MixMaze game) {
		super(game);
	}

	@Override
	public void show() {
		logger.info("this is host");
		model = new MixMazeModel(5, MixMazeDifficulty.Beginner, 60*2);

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
					logger.info("string received"
							+ o);
					os = new ObjectSpace();
					os.register(42, model);
					os.addConnection(c);
					c.sendTCP(
						"sending model");
					model.startGame();
				}
			}
		});

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		/* FIXME: game size and time limit should be passed from UI */
		setupGameBoard();

		/* set timer */
		Label.LabelStyle style = timerLabel.getStyle();
		style.fontColor = WHITE;
		timerLabel.setStyle(style);

		countdown = model.getGameMaxTime();
		Timer.schedule(new Timer.Task() {
			public void run() {
				int min = countdown / 60;
				int sec = countdown % 60;

				if (countdown == 10) {
					Label.LabelStyle style = timerLabel
							.getStyle();
					style.fontColor = RED;
					timerLabel.setStyle(style);
				}
				timerLabel.setText(String.format("%s%d:%s%d",
						(min < 10) ? "0" : "", min,
						(sec < 10) ? "0" : "", sec));
				countdown -= 1;
			}
		}, 0, 1, model.getGameMaxTime());
		Timer.schedule(new Timer.Task() {
			public void run() {
				IPlayerModel winner;

				stage.setKeyboardFocus(null);
				winner = model.endGame();
				if (winner == null) {
					/* draw */
					resultLabel.setText("Draw");
				} else {
					/* winner */
					resultLabel.setText("Player "
							+ winner.getPlayerID()
							+ " win");
				}
				endGameTable.setVisible(true);
			}
		/*
		 * FIXME: this does not look like a good solution.
		 * It takes some time for timerLabel to change text,
		 * and therefore, without the extra 1, the game will end
		 * before the timer showing up 00:00.
		 */
		}, model.getGameMaxTime() + 1);

		/* start game */
		Gdx.input.setInputProcessor(stage);
		stage.setKeyboardFocus(gameArea);
	}

	@Override
	protected void setupGameBoard() {
		int tileSize = 640 / model.getBoardSize();

		for (int j = 0; j < model.getBoardSize(); j++) {
			for (int i = 0; i < model.getBoardSize(); i++) {
				tileTable.add(new TileViewModel(
						model.getBoardTile(i, j),
						renderer,
						tileSize))
						.size(tileSize, tileSize);
			}

			/*
			 * Luran:
			 * Not sure if an extra row() after boardHeight
			 * will cause problem, but it is easy to check.
			 */
			if (j < model.getBoardSize())
				tileTable.row();
		}

		// Might rcommend not passing the entire model to the PlayerViewModel, just to keep good seperation
		p1 = new PlayerViewModel(model.getPlayer1(), model, tileSize,
				1,new Settings().p1Controls);
		p2 = new PlayerViewModel(model.getPlayer2(), model, tileSize,
				2,new Settings().p2Controls);
		gameArea.addActor(p1);
		gameArea.addActor(p2);

	}

}
