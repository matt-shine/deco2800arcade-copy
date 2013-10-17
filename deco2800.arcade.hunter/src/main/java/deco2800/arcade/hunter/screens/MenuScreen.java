package deco2800.arcade.hunter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.hunter.Hunter;

/**
 * A Hunter game for use in the Arcade
 * @author Nessex, DLong94
 *
 */
public class MenuScreen implements Screen {
	private Hunter hunter;
		
	private Stage stage;
	
	private SpriteBatch batch;
	
	private Texture background;
	
	public MenuScreen(Hunter p){
		hunter = p;
		Texture.setEnforcePotImages(false);
		background = new Texture("textures/mainmenu.png");
		//Set up stage
		stage = new Stage();
		batch = new SpriteBatch();
		
		ArcadeInputMux.getInstance().addProcessor(stage);
		
		Table table = new Table();
		table.padRight(600f);
		table.padTop(200f);
		table.setFillParent(true);
		stage.addActor(table);
		
		//Add play button to the table
		Button playButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("textures/playbutton.png"))));
		playButton.setSize(300, 80);
		//implement listener to the button
		playButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
                stage.clear(); //Stops buttons being clickable when you move out of this screen. See: http://stackoverflow.com/questions/13890472/ligbdx-touch-listener-on-wrong-screen for a potentially better way TODO
				hunter.setScreen(new GameScreen(hunter));
			}

		});
		//add to the table
		table.add(playButton).size(300,80).spaceBottom(20);
		table.row();
		
		//Add options button to the table
		Button optionsButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("textures/optionsbutton.png"))));
		optionsButton.setSize(300,80);
		
		optionsButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Options worked!");
				hunter.setScreen(new OptionScreen(hunter));
				stage.clear();
				/*TODO fix the broken options screen, then re-enable this*/
			}
		});
		
		table.add(optionsButton).size(300,80).spaceBottom(20);
		table.row();
		
		
		//Add highscore button to the table
		Button highScoreButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("textures/highscorebutton.png"))));
		highScoreButton.setSize(200, 60);
		
		highScoreButton.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				System.out.println("HighScore worked!");
				hunter.setScreen(new HighScoreScreen(hunter));
				stage.clear();
				/*TODO Fix the broken High Score screen, then re-enable this*/
			}
		});
		
		table.add(highScoreButton).size(300,80).spaceBottom(20);
		table.row();
		
		Button exitButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("textures/exitbutton.png"))));
		exitButton.setSize(300, 80);
		
		exitButton.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				System.out.println("Exit Works");
				ArcadeSystem.goToGame(ArcadeSystem.UI);
				stage.clear();
				/*TODO Get this to quit properly, maybe take a look at how it is implemented in the Overlay to get back to the menu*/
			}
		});
		
		table.add(exitButton).size(300,80).spaceBottom(20);
	}

	@Override
	public void dispose() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
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
		
		batch.begin();
		drawBackground();
		batch.end();
		
		//Gdx.input.setInputProcessor(stage);
        ArcadeInputMux.getInstance().addProcessor(stage);
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
			
	}
	
	private void drawBackground(){
		batch.draw(background, 0f, 0f, Hunter.State.screenWidth, Hunter.State.screenHeight, 0, 0, background.getWidth(), background.getHeight(), false, false);
	}
	
}
