package deco2800.arcade.hunter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import deco2800.arcade.hunter.Hunter;
/*import deco2800.arcade.client.ArcadeInputMux;*/

/**
 * A Hunter game for use in the Arcade
 * @author Nessex, DLong94
 *
 */
public class MenuScreen implements Screen {
	private Hunter parent;
		
	private Stage stage;
	
	public MenuScreen(Hunter p){
		parent = p;
		
		//Set up stage
		stage = new Stage();
		/*ArcadeInputMux.getInstance().addProcessor(stage);*/
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		TextureRegionDrawable playButtonImage = new TextureRegionDrawable(new TextureRegion(new Texture("textures/huntergame.png")));
		
		Button playButton = new Button(playButtonImage);
		playButton.setSize(100, 100);
		playButton.setPosition(0, 0);
		
		playButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("test");
				/*parent.startGame();*/
			}
		});
		
		table.add(playButton);
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