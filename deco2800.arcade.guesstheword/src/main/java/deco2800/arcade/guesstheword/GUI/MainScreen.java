package deco2800.arcade.guesstheword.GUI;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainScreen implements Screen {
	
	private final GuessTheWord game;
	private final Skin skin;
	private final Stage stage;
	private final Label titleLabel;
	private final TextButton startButton;
	private final TextButton settingsButton;
	private final TextButton achieveButton;
	
	// setup the dimensions of the menu buttons
    private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    private static final float BUTTON_SPACING = 10f;
    
	
	MainScreen(final GuessTheWord game){
		this.game = game;
		this.skin = game.skin;
		this.stage = new Stage();
		//Gdx.input.setInputProcessor(stage);
		titleLabel = new Label("Welcome to Guess The Word!" , skin);
		startButton = new TextButton("Click to Play" , skin);
		startButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.getterSetter.setLevel("Default");
				System.out.println("Changing to Game Screen");
				game.setScreen(game.gameScreen);
			}
		});
		settingsButton = new TextButton("Game Settings" , skin);
		settingsButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Changing to Settings Screen");
				game.setScreen(game.settingsScreen);
			}
		});
		
		achieveButton = new TextButton("Achievement " , skin);
//		achieveButton.addListener(new ChangeListener() {
//			public void changed (ChangeEvent event, Actor actor) {
//				game.setScreen(game.acheivementScreen);
//			}
//		});
		
		// Creating of the table to store the buttons and labels
		Table mainTable =  new Table();
		mainTable.setFillParent(true);

		mainTable.add(titleLabel).padBottom(15);
		mainTable.row();
		mainTable.add(startButton).width(BUTTON_WIDTH).padBottom(5);
		mainTable.row();
		mainTable.add(settingsButton).width(BUTTON_WIDTH).padBottom(5);
		mainTable.row();
		mainTable.add(achieveButton).width(BUTTON_WIDTH).padBottom(5);

		stage.addActor(mainTable);
		
	}
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
		stage.act(arg0);
		stage.draw();

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
