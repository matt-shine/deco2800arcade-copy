package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;

/**
 * Create a sidebar on the left side of the screen
 * @author s4266321
 *
 */
public class OverlayScreen implements Screen {
	
	private class OverlayScreenStage extends Stage {}
	
	private Screen callbacks = null;
	
    private OverlayScreenStage stage;
    private Sidebar sidebar = null;
    private OverlayWindow window = null;
    
	private boolean isUIOpen = false;
	private boolean hasTabPressedLast = false;
	
	@SuppressWarnings("unused")
	private Overlay overlay;
	
	public OverlayScreen(Overlay overlay) {
		
		this.overlay = overlay;
		window = new OverlayWindow(overlay);
		sidebar = new Sidebar(overlay, window);
		
		stage = new OverlayScreenStage();
		stage.addActor(window);
		stage.addActor(sidebar);
		
	}
	
	

	@Override
	public void show() {
		ArcadeInputMux.getInstance().addProcessor(stage);
	}
	
	
	@Override
	public void render(float d) {
		
		//toggles isUIOpen on tab key down
		if (Gdx.input.isKeyPressed(Keys.TAB) != hasTabPressedLast && (hasTabPressedLast = !hasTabPressedLast)) {
			isUIOpen = !isUIOpen;
			
			if (callbacks != null) {
				if (isUIOpen) {
					callbacks.show();
				} else {
					callbacks.hide();
				}
			}
			
			
		}
		
	    if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
	    	ArcadeSystem.goToGame(ArcadeSystem.UI);
	    }
	    
		
		if (isUIOpen) {
			
			stage.act();
			stage.draw();
			Table.drawDebug(stage);
			
		    if (callbacks != null) {
		    	callbacks.render(d);
		    }
		    
		}
		
		
	}

	@Override
	public void dispose() {
	    if (callbacks != null) {
	    	callbacks.dispose();
	    }
	    ArcadeInputMux.getInstance().removeProcessor(stage);
	    this.window.destroy();
	}
	
	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	    if (callbacks != null) {
	    	callbacks.pause();
	    }
	}

	@Override
	public void resume() {
	    if (callbacks != null) {
	    	callbacks.resume();
	    }
	}
	

	public void setListeners(Screen l) {
		this.callbacks = l;
	}



	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		stage.getCamera().position.set(width / 2, height / 2, 0);
		if (callbacks != null) {
	    	callbacks.resize(width, height);
	    }
	    sidebar.resize(width, height);
	}
	
	public Screen getListeners() {
		return this.callbacks;
	}

}
