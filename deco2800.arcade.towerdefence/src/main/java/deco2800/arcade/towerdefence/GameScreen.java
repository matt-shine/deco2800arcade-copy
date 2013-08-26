package deco2800.arcade.towerdefence;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.graphics.GL20.GL_DEPTH_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


public class GameScreen implements Screen{
	
	private TowerDefence game;
	
	private Stage stage;
	//private ShapeRenderer shapeRenderer;
	private Table table;
	private Table gameTable;
	private Table topStatus;
	private Table bottomBar;
	float statusHeight = 200;
	float bottomHeight = 200;
	
	public GameScreen(TowerDefence game){
		this.game = game;
		stage = new Stage();
		table = new Table();
		gameTable = new Table();
		table.setFillParent(true);
		table.debug();
		stage.addActor(table);
		
		topStatus = new Table();
		topStatus.setColor(Color.BLUE);
		
		bottomBar = new Table();
		bottomBar.setColor(Color.PINK);
		
		gameTable = new Table();
		gameTable.setColor(Color.YELLOW);
		
		table.add(topStatus).top().height(statusHeight).expand().fill();
		table.add(bottomBar).bottom().height(bottomHeight).fill();
		table.add(gameTable).expandY().fill();
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0.2f, 0.2f, 0, 1);
		Gdx.gl20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		stage.setViewport(1024, 768, true);
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
	}

}
