package deco2800.arcade.guesstheword.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainScreen implements Screen {
	
	private final GuessTheWord game;
	private final Skin skin;
	private final Stage stage;
	private final Label titleLabel;
	private final Texture texture;
	private final SpriteBatch batch;
	
	private final TextButton startButton;
	private final TextButton settingsButton;
	private final TextButton achieveButton;
	

	
	// setup the dimensions of the menu buttons
    private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    
	MainScreen(final GuessTheWord game){
		this.game = game;
		this.skin = game.skin;
		this.stage = new Stage();
	
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("mainScreen.png"));

		titleLabel = new Label("Welcome to Guess The Word!" , skin);
		titleLabel.setFontScale(1);
		
		startButton = new TextButton("Click to Play" , skin);
		startButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				clearLabel();
				clearTextfield();
				//Users will start the game in a default mode
				game.getterSetter.setLevel("Default");
				System.out.println("Changing to Game Screen");
				game.setScreen(game.gameScreen);
			}
		});
		settingsButton = new TextButton("Game Settings" , skin);
		settingsButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Changing to Settings Screen");
				clearLabel();
				clearTextfield();
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
		mainTable.add(startButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5);
		mainTable.row();
		mainTable.add(settingsButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5);
		mainTable.row();
		mainTable.add(achieveButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(5);

		stage.addActor(mainTable);
	}
	
	private void clearLabel(){
		game.getterSetter.setScore(0);
		game.getterSetter.setLevel("");
	}
	
	private void clearTextfield(){
		game.getterSetter.setText1("");
		game.getterSetter.setText2("");
		game.getterSetter.setText3("");
		game.getterSetter.setText4("");
		game.getterSetter.setText5("");
		game.getterSetter.setText6("");
	}
	
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texture, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		batch.end();
		
		
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
