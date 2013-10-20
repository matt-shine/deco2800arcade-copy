package deco2800.arcade.client;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

/**
 * This interface provides a way for games to talk to the overlay without the ui package being
 * in the main jar.
 * @author s4266321
 *
 */
public interface UIOverlay {

	
	/**
	 * Takes a GDX Screen object. The overlay is also implemented in terms of a
	 * screen, and more-or-less just forwards the calls it gets on to the listener.
	 * @param l
	 */
	void setListeners(Screen l);
	
	
	public static abstract class PopupMessage {
	    public abstract String getMessage();
	    
	    public Texture getTexture() {
		return null;
	    }

	    public float displayTime() {
		return 1.0f;
	    }
		//TODO: onclick behaviour
	}
	
	
	/**
	 * For achievements, chat messages, etc.
	 * @param p
	 */
	void addPopup(PopupMessage p);
	
	GameClient getHost();
		
	void setHost(GameClient host);
	
}
