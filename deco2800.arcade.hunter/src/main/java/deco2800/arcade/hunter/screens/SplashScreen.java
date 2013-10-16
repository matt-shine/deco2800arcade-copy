package deco2800.arcade.hunter.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.hunter.Hunter;

public class SplashScreen implements Screen {
	
	private Hunter hunter;
	private Stage stage;
	
	private Image splashImage;
	
	
	public SplashScreen(Hunter h){
		hunter = h;
		stage = new Stage();
		ArcadeInputMux.getInstance().addProcessor(stage);
		Texture text = new Texture("textures/splashscreen.png");
		
		splashImage = new Image(text);
		splashImage.setFillParent(true);
		
		splashImage.getColor().a = 0f;
		
		
		splashImage.addAction(Actions.sequence(Actions.fadeIn(0.75f), Actions.delay(0.5f), Actions.fadeOut(0.75f),
			new RunnableAction() {
				public void run() {
					hunter.setScreen(new MenuScreen(hunter));
				}
			})
		);
		
		stage.addActor(splashImage);
	}
	
	

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		//stage.dispose();
	}
	

}
