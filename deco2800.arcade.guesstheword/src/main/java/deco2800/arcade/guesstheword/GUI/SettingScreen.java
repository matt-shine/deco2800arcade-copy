package deco2800.arcade.guesstheword.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class SettingScreen implements Screen{

	private final GuessTheWord game;
	private final Skin skin;
	private final Stage stage;

	private List list;
	private Label settingLabel;
	private Table mainTable, settingTable;
	private TextButton playButton;
	
	SettingScreen(final GuessTheWord game){
		this.game = game;
		this.skin = game.skin;
		this.stage = new Stage();
		
		list = new List(new String [] {"Level 1 - 4 letters", "Level 2 - 5 letters","Level 3 - 6 letters"}, skin);
		
		settingLabel = new Label("Choose Level", skin);
		
		playButton = new TextButton("Click to Play", skin);
		playButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				String s = list.getSelection();
				System.out.println(s);
				game.getterSetter.setLevel(s);
				game.setScreen(new GameScreen(game));
			}
		});
		
		mainTable = new Table();
		mainTable.setFillParent(true);
		
		mainTable.add(settingLabel);
		mainTable.row();
		mainTable.add(list).padBottom(10);
		mainTable.row();
		mainTable.add(playButton).width(list.getWidth());
		stage.addActor(mainTable);
	}
	
	@Override
	public void render(float arg0) {
		// TODO Auto-generated method stub

		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        
		stage.act(arg0);
		stage.draw();
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			System.out.println("Back to MainScreen");
			game.setScreen(game.mainScreen);
		} 
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
	}
	
	

}
