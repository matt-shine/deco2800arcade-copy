package deco2800.arcade.client;

import com.badlogic.gdx.Screen;

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
	
	
	public static interface PopupMessage {
		String getMessage();
		//TODO: icon
		//TODO: onclick behaviour
	}
	
	
	/**
	 * For achievements, chat messages, etc.
	 * @param s
	 */
	void addPopup(PopupMessage p);
	
	GameClient getHost();
		
	void setHost(GameClient host);
	
}
