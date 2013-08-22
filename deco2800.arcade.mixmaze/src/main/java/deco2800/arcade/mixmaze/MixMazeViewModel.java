package deco2800.arcade.mixmaze;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.graphics.GL20.GL_DEPTH_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import deco2800.arcade.mixmaze.domain.MixMazeModel;

public class MixMazeViewModel implements Screen {
	private MixMazeModel model;
	private Stage stage;
	private Table table;
	private Table tileTable;
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void hide() {
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
		stage.setViewport(800, 640, true);
		stage.getCamera().translate(-stage.getGutterWidth(),
				-stage.getGutterHeight(), 0);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
	
	public MixMazeViewModel() {
		model = new MixMazeModel(5, 5);
		stage = new Stage();
		table = new Table();
		table.setFillParent(true);
		tileTable = new Table();
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
