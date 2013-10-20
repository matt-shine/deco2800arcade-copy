package deco2800.arcade.arcadeui.store;

import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;

/**
 * A little Utilities method for things that I want to share across the store.
 * I don't know if there's a better way, but this works. Be gentle marking me?
 * @author Addison Gourluck
 */
public class Utilities {
	
	/**
	 * This method will add all of the images from the 'logos' folder into the
	 * skin given to it. If any of them are missing, default is loaded instead.
	 * 
	 * @author Addison Gourluck
	 * @param Skin skin
	 */
	public static void loadIcons(Skin skin) {
		// Load the logo for all of the games (or default), and store them
		// in the skin, with their name as their key.
		Set<Game> gameslist = null;
		try {
			gameslist = ArcadeSystem.getArcadeGames();
		} catch (Exception e) {
			// Could not load arcade game. Critical error.
			// Wait, and retry it, since this is REALLY important.
			try {
				Thread.sleep(1000); // Zzzzzzz *snore*
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			gameslist = ArcadeSystem.getArcadeGames();
			// If it fails again, bad luck. Restart arcade. Should fix it.
		}
		
		for (Game gamename : gameslist) {
			try {
				// Search for png file with name equal to the game's id.
				skin.add(gamename.id, new Texture(Gdx.files.internal("logos/"
						+ gamename.id.toLowerCase() + ".png")));
			} catch (Exception e) {
				// Loading file failed, load default instead.
				skin.add(gamename.id, new Texture
						(Gdx.files.internal("logos/default.png")));
			}
		}
	}
	

	/**
	 * This method generates the inner pieces of the popup, including the grey
	 * overlay, and the buttons, field, slider, and listeners for them.
	 * @author Addison Gourluck
	 * @param Button greyOverlay
	 * @param Button popupBox
	 */
	public static void generatePopup(
			final Button greyOverlay, final Button popupBox, Skin skin) {
		greyOverlay.setFillParent(true);
		greyOverlay.setColor(0.6f, 0.6f, 0.6f, 0.6f);
		
		popupBox.getStyle().up = skin.getDrawable("blue_frame");
		popupBox.setSize(545, 300);
		popupBox.setPosition(368, 210);
		
		// Textfield describing the amount of coins to purchase.
		final TextField buyField = new TextField("1", skin);
		// Label displaying the cost of the transaction, without discount.
		final Label costLabel = new Label("Cost: 1", skin);
		// Label displaying the discount to be given on transaction.
		final Label discountLabel = new Label("Discount: 0", skin);
		// Label displaying the total purchase cost, including discount. 
		final Label totalLabel = new Label("Total: 1", skin);
		// Slider to select amount of coins to purchase.
		final Slider slider = new Slider(1, 999, 1, false, skin);
		
		buyField.setTextFieldFilter(
				new TextField.TextFieldFilter.DigitsOnlyFilter());
		buyField.setSize(50, 30);
		buyField.setPosition(70, 190);
		popupBox.addActor(buyField);
		
		costLabel.setSize(100, 30);
		costLabel.setPosition(130, 190);
		popupBox.addActor(costLabel);
		
		discountLabel.setSize(120, 30);
		discountLabel.setPosition(230, 190);
		popupBox.addActor(discountLabel);
		
		totalLabel.setSize(70, 30);
		totalLabel.setPosition(370, 190);
		popupBox.addActor(totalLabel);
		
	    slider.setSize(405, 50);
	    slider.setPosition(70, 130);
		popupBox.addActor(slider);
		
		// Buy button at bottom of popup.
		Button buy = new Button(skin, "buy");
		buy.setSize(149, 62);
		buy.setPosition(198, 50);
		popupBox.addActor(buy);
		
		// Listener for textfield keystroke.
		buyField.setTextFieldListener(new TextFieldListener() {
			public void keyTyped(TextField textField, char key) {
				if (buyField.getText().equals("") ||
						Float.parseFloat(buyField.getText()) < 1) {
					buyField.setText("1"); // Ensures that at least 1 is entered.
				} else if (Float.parseFloat(buyField.getText()) > 999) {
					buyField.setText("999"); // Ensures no more than 999 is entered.
				}
				// Sets the slider to the value entered, upon any keystroke.
				slider.setValue(Float.parseFloat(buyField.getText()));
				costLabel.setText("Cost: " + (int)slider.getValue());
				discountLabel.setText("Discount: " + (int)(slider.getValue()
						- Utilities.discountValue(slider.getValue())));
				totalLabel.setText("Total: "
						+ Utilities.discountValue(slider.getValue()));
			}
		});
		
		// Listener for the slider movement.
		slider.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				// Sets the text of the field to the slider value.
				buyField.setText((int)slider.getValue() + "");
				// Moves cursor to end of field, to prevent typing interruption.
				buyField.setCursorPosition(buyField.getText().length());
				costLabel.setText("Cost: " + (int)slider.getValue());
				discountLabel.setText("Discount: " + (int)(slider.getValue()
						- Utilities.discountValue(slider.getValue())));
				totalLabel.setText("Total: "
						+ Utilities.discountValue(slider.getValue()));
			}
		});
		
		// Buy button listener.
		buy.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Buying " + (int)slider.getValue() + " Tokens for " + Utilities.discountValue(slider.getValue()));
			}
		});
		
		// Make grey overlay, and transactions box disappear upon clicking away.
		greyOverlay.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				popupBox.remove();
				greyOverlay.remove();
			}
		});
	}

	/**
	 * Returns the coins discounted price.
	 * @author Addison Gourluck
	 * @param int tokens
	 */
	public static int discountValue(double tokens) {
		if (tokens < 20) { // buying 1-19 gives no discount
			return (int)(1.0 * tokens);
		} else if (tokens < 50) { // buying 20-49 gives discount of 0.8
			return (int)(0.8 * tokens);
		} else if (tokens < 150) { // buying 50-149 gives discount of 0.6
			return (int)(0.6 * tokens);
		} else if (tokens < 500) { // buying 150-499 gives discount of 0.5
			return (int)(0.5 * tokens);
		} else { // buying 500+ gives discount of 0.4
			return (int)(0.4 * tokens);
		}
	}
	
	/**
	 * This method will return the game with the name that matches most closely.
	 * 
	 * @author Addison Gourluck
	 * @param String input
	 * @return Game
	 */
	public static Game search(String input) {
		if (input.length() <= 2) {
			return null; // No searches for 0, 1 or 2 chars.
		}
		String search = input.toLowerCase();
		// Check if the input is a substring of a game.
		for (Game game : ArcadeSystem.getArcadeGames()) {
			if (game.name.toLowerCase().contains(search)
					|| game.id.toLowerCase().contains(search)) {
				return game;
			}
		}
		if (search.length() > 6) {
			search = search.substring(0, 6); // crop to first 6 chars for regex.
		}
		if (search.length() <= 3) {
			return null; // 3 characters in the regex give bogus results.
		}
		// If no results are produced yet, get desperate. Proceed to look
		// for a game that includes search, with 1 wrong/missing char.
		String regex;
		for (Game game : ArcadeSystem.getArcadeGames()) {
			for (int i = 0; i < search.length(); ++i) {
				// Super duper awesome regex, that will find any combination of
				// the string, with 1 letter missing or wrong. Really cool.
				regex = "(.*)" + search.substring(0, i) + "(.?)"
						+ search.substring(i + 1, search.length()) + "(.*)";
				if (game.name.toLowerCase().matches(regex)) {
					return game;
				}
			}
		}
		return null; // No results at all.
	}
}