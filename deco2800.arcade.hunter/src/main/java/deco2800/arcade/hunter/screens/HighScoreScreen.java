package deco2800.arcade.hunter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import deco2800.arcade.hunter.Hunter;

public class HighScoreScreen implements Screen {

	private Hunter hunter;
	private Stage stage;
	
	private int highScore1;
	private int highScore2;
	private int highScore3;
	
	public HighScoreScreen(Hunter h){
		hunter = h;
		Stage stage= new Stage();
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		
		//set table defaults
		table.defaults().spaceBottom(20);
		table.columnDefaults(0).colspan(2);
		
		
		table.add("HighScore Menu!").colspan(2);
		table.row();
		
		table.add("Number 1: ");
		table.add(String.valueOf(highScore1)).colspan(2);
		table.row();
		
		table.add("Number 2: ");
		table.add(String.valueOf(highScore2)).colspan(2);
		table.row();
		
		table.add("Number 3: ");
		table.add(String.valueOf(highScore3)).colspan(2);
		
		TextButton backButton = new TextButton("Back to main menu", new Skin());
        backButton.addListener(new ClickListener(){
        	@Override
            public void touchUp(InputEvent event,float x,float y,int pointer,int button ){
        		System.out.println("Going back to the main menu!");
        	}
        });
        
        table.add("backButton").size(300,70);
        table.row();
		
		
	}
	
	public void getHighScores(){
		/*Get the high scores */
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

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
		hunter.dispose();
		
	}

}
