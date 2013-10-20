package deco2800.arcade.arcadeui.store;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.arcadeui.ArcadeUI;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

/**
 * The transactions page is used exclusively for buying tokens, and shows the
 * prices of different token packs. The transactions page was intended to have
 * the ability to buy individual games, but this was not implemented.
 * @author Addison Gourluck
 */
public class StoreTransactions implements Screen, StoreScreen {
	private Skin skin = new Skin(Gdx.files.internal("store/storeSkin.json"));
	private Stage stage = new Stage();
	private ArcadeUI arcadeUI;
	private Player player; // The currently logged in player.
	private int balance = 0; // TEMPORARY: The players credit balance.
	
	/**
	 * @author Addison Gourluck
	 * @param ArcadeUI ui
	 * @param Player user
	 */
	public StoreTransactions(ArcadeUI ui, Player user) {
		player = user;
		arcadeUI = ui;
		
		final Table bg = new Table();
		final Button homeButton = new Button(skin, "home");
		final Label title = new Label("Buy More Coins", skin, "default-34");
		final Button searchButton = new Button(skin, "search");
		final TextField searchField = new TextField("", skin);
		final Label searchResult = new Label("", skin);
		final Label balanceNumber = new Label(balance + "", skin, "current-coins");
		final Label balanceTitle = new Label("Current Balance", skin, "default-28");
		final TextButton wishlistButton = new TextButton("Wishlist", skin);
		
		// The background for the store.
		skin.add("background", new Texture(
				Gdx.files.internal("store/transactions_bg.png")));
		bg.setFillParent(true);
		bg.setBackground(skin.getDrawable("background"));
		stage.addActor(bg);
		
		// Home button at top right corner of screen.
		homeButton.setSize(51, 46);
		homeButton.setPosition(1149, 650);
		stage.addActor(homeButton);
		
		// Title "Buy More Coins", located center of screen.
		title.setSize(380, 40);
		title.setPosition(96, 515);
		stage.addActor(title);
		
		// Entry field for search term. Will update the featured game, as well.
		// as the search result located below it.
		searchField.setSize(300, 42);
		searchField.setPosition(860, 536);
		searchField.setMessageText(" Search");
		stage.addActor(searchField);
		
		// Search button. Will re-direct user to the (valid) "SearchResult" game page.
		searchButton.setSize(126, 55);
		searchButton.setPosition(1039, 475);
		stage.addActor(searchButton);
		
		// The auto-changing text that displays the current game to be searched for.
		searchResult.setSize(165, 55);
		searchResult.setPosition(860, 475);
		stage.addActor(searchResult);
		
		// Static element: text "Current Balance", located at top of right bar.
		balanceTitle.setSize(300, 50);
		balanceTitle.setPosition(862, 365);
		stage.addActor(balanceTitle);
		
		// The amount of tokens/coins the user has. Located below balanceTitle.
		balanceNumber.setSize(90, 50);
		balanceNumber.setPosition(966, 308);
		balanceNumber.setAlignment(Align.center);
		stage.addActor(balanceNumber);
		
		// Button linking to wishlist page.
		wishlistButton.setSize(360, 95);
		wishlistButton.setPosition(833, 176);
		stage.addActor(wishlistButton);
		
		// Adds the images of the different coins, the text and buttons beside
		// them, and the listeners for the buttons.
		addCoins();
		
		// Top right Home button and listener.
		homeButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				hide();
				arcadeUI.setScreen(arcadeUI.getStoreHome());
			}
		});
		
		// Search Field, with listen. Located top right.
		searchField.setTextFieldListener(new TextFieldListener() {
			public void keyTyped(TextField textField, char key) {
				// Run search whenever a key is typed.
				Game result = Utilities.helper.search(searchField.getText());
				// No results if search returns null.
				if (result == null) {
					searchResult.setText("No results.");
					return;
				}
				// Else display closest match.
				searchResult.setText(result.name);
			}
		});
		
		// Search button, paired with search field. Top right screen.
		searchButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				try {
					// Attempt to search for whatever is in the field.
					Game result = Utilities.helper.search(searchField.getText());
					if (result == null) {
						searchResult.setText("No results."); // Bad text
						return;
					}
					hide(); // On successful search, go to its page.
					arcadeUI.setScreen(new StoreGame(arcadeUI, player, result));
				} catch (Exception e) {
					searchResult.setText("Invalid Search"); // No text
				}
			}
		});
		
		// Button linking to wishlist page.
		wishlistButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				hide();
				arcadeUI.setScreen(arcadeUI.getStoreWishlist());
			}
		});
	}
	
	/**
	 * Adds the coin icons, text, and buy button for each of the 5 coin packs,
	 * as well as the listener for the buy button.
	 * @author Addison Gourluck
	 */
	private void addCoins() {
		int i = 0;
		for (int check : Arrays.asList(5, 10, 20, 50, 100)) {
			skin.add("coin" + check, new Texture(
					Gdx.files.internal("store/coin_" + check + ".png")));
			
			// Buy options icons.
			final Table coins = new Table();
			coins.setBackground(skin.getDrawable("coin" + check));
			coins.setSize(57, 39);
			coins.setPosition(130, 390 - (i * 80));
			stage.addActor(coins);
			
			// Buy options text.
			final Label buyText = new Label("", skin, "default-26");
			if (check == 5) {
				buyText.setText("Booster Pack [5 coins]");
			} else if (check == 10) {
				buyText.setText("Super Pack [10 coins]");
			} else if (check == 20) {
				buyText.setText("Mega Pack [20 coins]");
			} else if (check == 50) {
				buyText.setText("Ultra Pack [50 coins]");
			} else {
				buyText.setText("Ultimate Pack [100 coins]");
			}
			buyText.setSize(149, 62);
			buyText.setPosition(200, 379 - (i * 80));
			stage.addActor(buyText);
			
			// Buy options 'Buy' buttons.
			final Button buyButton = new Button(skin, "buy");
			buyButton.setSize(149, 62);
			buyButton.setPosition(620, 377 - (i * 80));
			buyButton.setName(check + "");
			stage.addActor(buyButton);
			
			// Buy button Listener, which will buy tokens for user on click.
			buyButton.addListener(new ChangeListener() {
				public void changed(ChangeEvent event, Actor actor) {
					System.out.println("buying "
							+ Integer.parseInt(actor.getName()));
					buyTokens(Integer.parseInt(actor.getName()));
					// not yet implemented^
				}
			});
			i++;
		}
	}
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		ArcadeInputMux.getInstance().removeProcessor(stage);
	}

	@Override
	public void show() {
		player = arcadeUI.getPlayer();
		ArcadeInputMux.getInstance().addProcessor(stage);
	}
	
	@Override
	public void hide() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void resume() {
	}
	
	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void popup() {
	}

	@Override
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public Game getSelected() {
		return null; // No selected game for transactions screen.
	}
	
	@Override
	public boolean buyTokens(int amount) {
		// TODO
		// FIXME
		// NEEDS IMPLEMENTING!
		// TODO
		// FIXME
		return false;
	}
	
	@Override
	public boolean buyGame(Game game) {
		// TODO
		// FIXME
		// NEEDS IMPLEMENTING!
		// TODO
		// FIXME
		return false;
	}
	
	@Override
	public void setSelected(String game) {
		// No selected game for transactions screen.
	}
	
	@Override
	public boolean addWishlist(Game game) {
		// TODO
		// FIXME
		// NEEDS IMPLEMENTING!
		// TODO
		// FIXME
		return true;
	}
}
