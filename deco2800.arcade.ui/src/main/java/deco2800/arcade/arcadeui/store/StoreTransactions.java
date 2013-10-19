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
 * TODO describe.
 * @author Addison Gourluck
 */
public class StoreTransactions implements Screen, StoreScreen {
	private Skin skin = new Skin(Gdx.files.internal("store/storeSkin.json"));
	private Stage stage = new Stage();
	private ArcadeUI arcadeUI;
	private int balance = 0;
	
	/**
	 * @author Addison Gourluck
	 * @param ArcadeUI ui
	 */
	public StoreTransactions(ArcadeUI ui) {
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
		
		wishlistButton.setSize(360, 95);
		wishlistButton.setPosition(833, 176);
		stage.addActor(wishlistButton);
		
		addCoins();
		
		homeButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				arcadeUI.setScreen(new StoreHome(arcadeUI));
			}
		});
		
		searchField.setTextFieldListener(new TextFieldListener() {
			public void keyTyped(TextField textField, char key) {
				Game result = Utilities.helper.search(searchField.getText());
				if (result == null) {
					searchResult.setText("No results.");
					return;
				}
				searchResult.setText(result.name);
			}
		});
		
		searchButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				try {
					Game result = Utilities.helper.search(searchField.getText());
					if (result == null) {
						searchResult.setText("No results.");
						return;
					}
					dispose();
					arcadeUI.setScreen(new StoreGame(arcadeUI, result));
				} catch (Exception e) {
					searchResult.setText("Invalid Search");
				}
			}
		});
		
		wishlistButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				arcadeUI.setScreen(new StoreWishlist(arcadeUI));
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
		ArcadeInputMux.getInstance().addProcessor(stage);
	}
	
	@Override
	public void hide() {
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
		return null;
	}
	
	@Override
	public Game getSelected() {
		return null;
	}
	
	@Override
	public boolean buyTokens(int amount) {
		return false;
	}
	
	@Override
	public boolean buyGame(Game game) {
		return false;
	}
	
	@Override
	public void setSelected(String game) {
		// No selected game for transactions screen.
	}
	
	@Override
	public boolean addWishlist(Game game) {
		return true;
	}
}
