package deco2800.arcade.client;

import com.badlogic.gdx.Screen;

/**
 * This interface provides a way for games to talk to the overlay without the ui package being
 * in the main jar.
 * @author s4266321
 *
 */
public interface UIOverlay {

	void setListeners(Screen l);
	void addPopup(String s);
	
}
