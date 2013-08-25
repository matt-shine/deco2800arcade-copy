package deco2800.arcade.mixmaze;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.graphics.GL20.GL_DEPTH_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import deco2800.arcade.mixmaze.domain.MixMazeModel;

public class MixMazeViewModel implements Screen {
	private static final String LOG = MixMazeViewModel.class.getSimpleName();
	
	private MixMazeModel model;
	private TileViewModel[][] board;
	
	private Stage stage;
	private Table table;
	private Table tileTable;
	
	@Override
	public void show() {
		Gdx.app.debug(LOG, "Showing");
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void hide() {
		Gdx.app.debug(LOG, "Hiding");
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0.2f, 0.2f, 0, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.debug(LOG, String.format("Resizing %d*%d", width, height));
		stage.setViewport(800, 640, true);
		stage.getCamera().translate(-stage.getGutterWidth(), -stage.getGutterHeight(), 0);
	}

	@Override
	public void pause() {
		Gdx.app.debug(LOG, "Pausing");
	}

	@Override
	public void resume() {
		Gdx.app.debug(LOG, "Resuming");
	}
	
	@Override
	public void dispose() {
		Gdx.app.debug(LOG, "Disposing");
		stage.dispose();
	}
	
	public MixMazeViewModel() {
		Gdx.app.debug(LOG, "Initializing");
		
		// Initialize model and UI elements
		model = new MixMazeModel(5, 5);
		stage = new Stage();
		table = new Table();
		tileTable = new Table();
		
		// Initialize tiles
		board = new TileViewModel[model.getBoardHeight()][model.getBoardWidth()];
		for(int row = 0; row < model.getBoardHeight(); ++row) {
			for(int column = 0; column < model.getBoardWidth(); ++column) {
				board[row][column] = new TileViewModel(model.getTile(column, row));
				tileTable.add(board[row][column])
						.width(128)
						.height(128);
			}
			tileTable.row();
		}
		table.setFillParent(true);
		table.add(tileTable)
				.bottom()
				.left()
				.expand()
				.fill()
				.width(640)
				.height(640);
		stage.addActor(table);
	}
}
