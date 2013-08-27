package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.UIOverlay.PopupMessage;

/**
 * Create a sidebar on the left side of the screen
 * @author s4266321
 *
 */
public class OverlayScreen implements Screen {
	
	private Screen callbacks = null;
	private boolean notifiedForMissingCallbacks = false;
	
    private Stage stage;
    Sidebar sidebar = null;
    
	private boolean isUIOpen = false;
	private boolean hasTabPressedLast = false;
	
	private Overlay overlay;
	
	public OverlayScreen(Overlay overlay) {
		
		sidebar = new Sidebar(overlay);
		
        stage.addActor(sidebar);
        
	}
	
	

	@Override
	public void show() {
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
			
			
			if (isUIOpen) {
				overlay.addPopup(new PopupMessage() {
					public String getMessage() {
						return "Test overlay popup message.";
					}
				});
			}
		}
		
		if (isUIOpen) {
			
			stage.act();
			stage.draw();
			
		    if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
		    	ArcadeSystem.goToGame(ArcadeSystem.UI);
		    }
		    
		    if (callbacks != null) {
		    	callbacks.render(d);
		    } 
		    
		}
		
		
		if (callbacks == null && !notifiedForMissingCallbacks) {
	    	notifiedForMissingCallbacks = true;
	    	System.err.println("Overlay event listeners are not set. See https://github.com/UQdeco2800/deco2800-2013/wiki/Overlay");
	    }
	}

	@Override
	public void dispose() {
	    if (callbacks != null) {
	    	callbacks.dispose();
	    }
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
	public void resize(int arg0, int arg1) {
	    if (callbacks != null) {
	    	callbacks.resize(arg0, arg1);
	    }
	}
	


}
