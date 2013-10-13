package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.MixMazeModel;
import deco2800.arcade.mixmaze.domain.PlayerModel;
import deco2800.arcade.mixmaze.domain.TileModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Timer;

import static deco2800.arcade.mixmaze.domain.MixMazeModel.Difficulty.*;
import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.GL20.*;

/**
 * Local game on a the same machine.
 */
class LocalScreen extends GameScreen {

	LocalScreen(MixMaze game) {
		super(game);
	}

	@Override
	protected void newGame() {
		model = new MixMazeModel(5, BEGINNER, 60 * 2);

		setupGameBoard();
		setupTimer(model.getGameMaxTime());
		startGame();
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0.13f, 0.13f, 0.13f, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		model.spawnItems();

		stage.act(delta);
		stage.draw();
		// Table.drawDebug(stage);

		if (endGameTable.isVisible() && backMenu.isChecked()) {
			/* clean up this session and go to menu screen */
			backMenu.toggle();
			endGameTable.setVisible(false);
			tileTable.clear();
			gameArea.clear();
			game.setScreen(game.menuScreen);
		}
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

				TileModel tileModel = model.getBoardTile(i, j);
				tileModel.addObserver(tile);
				for (int direction = 0; direction < 4; ++direction) {
					tile.watchWall(direction, tileModel.getWall(direction));
				}
			}
			if (j < boardSize)
				tileTable.row();
		}

		p1 = new PlayerViewModel(model, tileSize, 1, p1Controls, scorebar[0],
				left);
		p2 = new PlayerViewModel(model, tileSize, 2, p2Controls, scorebar[1],
				right);
		model.getPlayer(1).addViewer(p1);
		model.getPlayer(2).addViewer(p2);
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

		countdown = timeLimit;
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
		}, 0, 1, timeLimit);
		Timer.schedule(new Timer.Task() {
			public void run() {
				PlayerModel winner;

				stage.setKeyboardFocus(null);
				winner = model.endGame();
				if (winner == null) {
					/* draw */
					resultLabel.setText("Draw");
				} else {
					/* winner */
					resultLabel.setText("Player " + winner.getId() + " win");
				}
				endGameTable.setVisible(true);
				Sounds.stop();
			}
			/*
			 * FIXME: this does not look like a good solution. It takes some
			 * time for timerLabel to change text, and therefore, without the
			 * extra 1, the game will end before the timer showing up 00:00.
			 */
		}, timeLimit + 1);
	}

	/**
	 * Starts the game.
	 */
	protected void startGame() {
		Gdx.input.setInputProcessor(stage);
		stage.setKeyboardFocus(gameArea);
		model.startGame();
		logger.info("start game");
	}

}
