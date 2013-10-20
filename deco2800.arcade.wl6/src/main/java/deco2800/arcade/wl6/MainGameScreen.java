package deco2800.arcade.wl6;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.wl6.Doodad;

public class MainGameScreen implements Screen {
	
	private GameModel model;
	private WL6 game;
	private boolean debugMode = false;
	private boolean overlayPause = false;
	private WL6InputProcessor input = null;
	private IngameUI ui = new IngameUI();
	
	Renderer b = new Renderer();
	
	public MainGameScreen(WL6 game, GameModel model) {
		this.model = model;
		this.game = game;
		
		input = new WL6InputProcessor(game, model);
		
		b.setGame(model, game);
		
	}
	
	@Override
	public void dispose() {
		b.dispose();
	}

	@Override
	public void hide() {
        ArcadeInputMux.getInstance().removeProcessor(input);
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl20.glViewport(0, 0, game.getWidth(), game.getHeight());
		
		model.setDelta(delta);
		
		if (!overlayPause) {

			input.tick();
			
			//iterate over all game objects
			model.beginTick();
			Iterator<Doodad> itr = model.getDoodadIterator();
			while (itr.hasNext()) {
				Doodad d = itr.next();
				d.tick(model);
			}
			model.endTick();
			
		}


		b.draw(this.debugMode);
        ui.draw(this.model);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
        Gdx.input.setCursorCatched(true);
        ArcadeInputMux.getInstance().addProcessor(input);

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
