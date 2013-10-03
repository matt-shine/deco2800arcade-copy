package deco2800.arcade.hunter.screens;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.Hunter.Config;

public class HighScoreScreen implements Screen {

	private Hunter hunter;
	private Stage stage;
	private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
	private static final String[] HIGH_SCORE_LIST = {"HIGHSCORE1","HIGHSCORE2","HIGHSCORE3"};
	private HashMap<String,Integer> highScoreList;
	
	private ArrayList<Integer> highscore = new ArrayList<Integer>();
	
	private Texture background;
	private SpriteBatch batch;
	
	public HighScoreScreen(Hunter h){
		hunter = h;
		stage= new Stage();
		ArcadeInputMux.getInstance().addProcessor(stage);
		highScoreList = hunter.getPreferencesManager().getHighScore();
		
		Texture.setEnforcePotImages(false);
		background = new Texture("textures/mainmenu.png");
		batch = new SpriteBatch();
		for(int x = 0; x <3; x++){
			highscore.add(highScoreList.get(HIGH_SCORE_LIST[x]));
		}
		System.out.println(highScoreList);
		
		Table table = new Table(skin);
		table.setFillParent(true);
		table.padRight(600f);
		table.padTop(200f);
		stage.addActor(table);
				
		//set table defaults
		table.defaults().spaceBottom(20);
		table.columnDefaults(0).colspan(2);
		
		
		table.add("HighScore Menu!").colspan(2);
		table.row();
		
		table.add("HighScore 1: ");
		table.add(String.valueOf(highscore.get(0))).colspan(2);
		table.row();
		
		table.add("HighScore 2: ");
		table.add(String.valueOf(highscore.get(1))).colspan(2);
		table.row();
		
		table.add("HighScore 3: ");
		table.add(String.valueOf(highscore.get(2))).colspan(2);
		table.row();
		
		TextButton backButton = new TextButton("Back to main menu", skin);
        backButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
        		System.out.println("Going back to the main menu!");
        		hunter.setScreen(new MenuScreen(hunter));
        		stage.clear();
        	}
        });
        
        table.add(backButton).size(300,70);
        table.row();
		
		
	}
	
	public void getHighScores(){
		/*Get the high scores */
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		drawBackground();
		batch.end();
		
		Gdx.input.setInputProcessor(stage);
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		dispose();
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		ArcadeInputMux.getInstance().removeProcessor(stage);
	}

	private void drawBackground(){
		batch.draw(background, 0f, 0f, Config.screenWidth, Config.screenHeight, 0, 0, background.getWidth(), background.getHeight(), false, false);
	}
}
