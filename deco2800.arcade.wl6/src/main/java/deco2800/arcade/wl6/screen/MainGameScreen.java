package deco2800.arcade.wl6.screen;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.wl6.*;
import deco2800.arcade.wl6.Doodad;

public class MainGameScreen implements Screen {
	
	private GameModel model;
	private WL6 game;
	private boolean debugMode = false;
	@SuppressWarnings("unused")
	private boolean overlayPause = false;
	private WL6InputProcessor input = null;
	
	
	Renderer b = new Renderer();
	
	public MainGameScreen(WL6 game) {
		this.model = new GameModel();
		model.goToLevel("e1l1");
		this.game = game;
		
		input = new WL6InputProcessor(game, model);
		ArcadeInputMux.getInstance().addProcessor(input);
		
		b.setGame(model);
		b.generateTerrain(model.getMap(), false);
		b.load();
		
	}
	
	@Override
	public void dispose() {
		b.dispose();
		ArcadeInputMux.getInstance().removeProcessor(input);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		
		model.setDelta(delta);
		
		//iterate over all game objects
		model.beginTick();
		Iterator<Doodad> itr = model.getDoodadIterator();
		while (itr.hasNext()) {
			Doodad d = itr.next();
			d.tick(model);
		}
		model.endTick();
		
		Gdx.gl20.glViewport(0, 0, game.getWidth(), game.getHeight());
		b.draw(this.debugMode);
		
	}

	@Override
	public void resize(int width, int height) {
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
		if (pause) {
			ArcadeInputMux.getInstance().removeProcessor(input);
		} else {
			ArcadeInputMux.getInstance().addProcessor(input);
		}
	}

}
