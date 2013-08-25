package deco2800.arcade.hunter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.client.ArcadeInputMux;

/**
 * A Hunter game for use in the Arcade
 * @author Nessex, DLong94
 *
 */
public class MenuScreen implements Screen {
	private Hunter game;
		
	private Stage stage;
	
	public MenuScreen(Hunter p){
		game = p;
		
		//Set up stage
		stage = new Stage();
		ArcadeInputMux.getInstance().addProcessor(stage);
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		//Add play button to the table
		Button playButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("textures/playbutton.png"))));
		playButton.setSize(200, 60);
		//implement listener to the button
		playButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("test");
				game.setScreen(new GameScreen(game));
			}

		});
		//add to the table
		table.add(playButton).size(200,60).spaceBottom(20);
		table.row();
		
		//Add options button to the table
//		TextButton optionsButton = new TextButton("Options");
		Button optionsButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("textures/optionsbutton.png"))));
		optionsButton.setSize(200,60);
		
		optionsButton.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				System.out.println("Options worked!");
			}
		});
		
		table.add(optionsButton).size(200,60).spaceBottom(20);
		table.row();
		
		
		//Add highscore button to the table
//		TextButton highScoreButton = new TextButton("HighScore", new Skin());
		Button highScoreButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("textures/highscorebutton.png"))));
		highScoreButton.setSize(200, 60);
		
		highScoreButton.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				System.out.println("HighScore worked!");
			}
		});
		
		table.add(highScoreButton).size(200,60).spaceBottom(20);
		table.row();
		
//		TextButton exitButton = new TextButton("Exit", new Skin());
		Button exitButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("textures/exitbutton.png"))));
		exitButton.setSize(200, 60);
		
		exitButton.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				System.out.println("Exit Works");
				game.dispose();
			}
		});
		
		table.add(exitButton).size(200,60).spaceBottom(20);
		
	}

	@Override
	public void dispose() {
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
	public void render(float delta) {
		//Black background
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
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		/*menuTitle = new Sprite(texture);
		menuTitle.setX(Gdx.graphics.getWidth()/2 - menuTitle.getWidth()/2);
		menuTitle.setY(Gdx.graphics.getHeight() - (menuTitle.getHeight()+ 20));*/
	
	}
	
}