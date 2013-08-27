package deco2800.arcade.towerdefence;

import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.graphics.GL20.GL_DEPTH_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	private static float STATUS_HEIGHT = 50f;
	private static float BOTTOM_HEIGHT = 100f;
	private Label randomLabel;
	private Label randomLabel2;
	private Label randomLabel3;
	
	/*
	 * Constructor for GameScreen, creates the platform for which the game will be played.
	 */
	public GameScreen(TowerDefence game){
		this.game = game;
		stage = new Stage();
		table = new Table();
		gameTable = new Table();
		table.setFillParent(true);
		table.debug();
		stage.addActor(table);
		
		randomLabel = new Label("Imagine this is a status bar", 
				new Label.LabelStyle(new BitmapFont(),
				CYAN));
		
		randomLabel2 = new Label("BOTTOM BAR!", 
				new Label.LabelStyle(new BitmapFont(),
				RED));
		
		randomLabel3 = new Label("Game screen here?", 
				new Label.LabelStyle(new BitmapFont(),
				MAGENTA));
		
		gameTable = new Table();
		
		table.add(randomLabel).top().height(STATUS_HEIGHT).expandX().fill(); //row for top status bar
		table.row();
		table.add(randomLabel3).center().expandY().fill(); //row for gameTable
		table.row();
		table.add(randomLabel2).bottom().height(BOTTOM_HEIGHT).fill(); //row for bottomBar
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
