package deco2800.arcade.wl6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import deco2800.arcade.client.ArcadeInputMux;

public class MainGameScreen implements Screen {
	
	private GameModel model;
	private WL6 game;
	private boolean debugMode = false;
	@SuppressWarnings("unused")
	private boolean overlayPause = false;
	
	Renderer b = new Renderer();
	
	public MainGameScreen(WL6 game) {
		this.model = new GameModel(1);
		this.game = game;
		
		//atlas = new Texture(Gdx.files.internal("wl6atlas.png"));
		//unknown = new TextureRegion(atlas);
		
		ArcadeInputMux.getInstance().addProcessor(new WL6InputProcessor(game, model));
		
		b.setGame(model);
		b.generateTerrain(model.getMap(), false);
		b.load();
		
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float arg0) {
		
		Gdx.gl20.glViewport(0, 0, game.getWidth(), game.getHeight());

		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		    
		b.draw(this.debugMode);
		
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
	}
	
	public void toggleDebugMode() {
		debugMode = !debugMode;
	}
	
	public void setOverlayPause(boolean pause) {
		this.overlayPause = pause;
	}

}
