package deco2800.arcade.arcadeui.store;

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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.arcadeui.ArcadeUI;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

/**
 * The Store's wishlist page, featuring the currently logged in users games
 * wishlist, and all of their ratings. From this page they can search to
 * navigate to any other games page, and can click on any game to bring up the
 * transactions screen.
 * @author Addison Gourluck
 */
public class StoreWishlist implements Screen, StoreScreen {
	private Skin skin = new Skin(Gdx.files.internal("store/storeSkin.json"));
	private Stage stage = new Stage();
	private ArcadeUI arcadeUI;
	
	/**
	 * @author Addison Gourluck
	 * @param ArcadeUI ui
	 */
	public StoreWishlist(ArcadeUI ui) {
		arcadeUI = ui;
		
		Utilities.helper.loadIcons(skin); // load the icons into the skin.
		
		skin.add("big_star", new Texture(Gdx.files.internal("store/big_stars.png")));
		
		final Table bg = new Table();
		final Button homeButton = new Button(skin, "home");
		final Label title = new Label("Wish List", skin, "default-34");
		final Button searchButton = new Button(skin, "search");
		final TextField searchField = new TextField("", skin);
		final Label searchResult = new Label("", skin);
		final TextButton transactionsButton = new TextButton("Transactions", skin);
		
		// The background for the store.
		skin.add("background", new Texture(Gdx.files.internal("store/wishlist_bg.png")));
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
		
		// The transactions button, located right of screen.
		transactionsButton.setSize(360, 95);
		transactionsButton.setPosition(834, 353);
		stage.addActor(transactionsButton);
		
		// The number 8 here is just a random index. Since nobody knows how to get
		// a players wishlist, I just put in a placeholder number.
		populateWishlist(8);
		
		// HOME BUTTON
		homeButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				arcadeUI.setScreen(new StoreHome(arcadeUI));
			}
		});
		
		// TRANSACTIONS BUTTON
		transactionsButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				arcadeUI.setScreen(new StoreTransactions(arcadeUI));
			}
		});
		
		// Listener to update the search prediction result as the user types.
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
		
		// Searchbutton listener, which will attempt to redirect to a game based
		// upon the searchfield's text, else will set the result as invalid.
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
	}
	
	/**
	 * This places 6 icons into a grid pattern in the display section in
	 * the centre of the wishlist page. The icons are assigned according to
	 * the index given, and given a listener that links to their store page
	 * on click.
	 * 
	 * For example, a player who has 10 games, index 0 will display game 0 to
	 * 6 on their wishlist, whereas index 6 would display games 6 - 9, and 0
	 * to 2. It uses modulo, so any index is a valid index. E.g: Index 10 will
	 * show games 0 - 6.
	 * 
	 * @author Addison Gourluck
	 * @param Stage stage
	 * @param Skin skin
	 * @param int index
	 */
	private void populateWishlist(int index) {
		for (int i = 0; i < 6; ++i) {
			final Game game = (Game)ArcadeSystem.getArcadeGames().toArray()
					[(index + i)%ArcadeSystem.getArcadeGames().size()];
			final Button gameGridGlow = new Button(skin, "icon");
			gameGridGlow.setSize(122, 122);
			
			// Retrieve icon for the game.
			final Button gameGridIcon = new Button(skin.getDrawable(game.id));
			gameGridIcon.setSize(110, 110);
			
			final Table star = new Table();
			star.setBackground(skin.getDrawable("big_star"));
			star.setSize(142, 23);
			
			final Label gameName;
			if (game.name.length() > 12) {
				gameName = new Label(game.name, skin, "default-22");
			} else { // Games with long names use smaller font.
				gameName = new Label(game.name, skin, "default-24");
			}
			gameName.setSize(200, 40);
			
			if (i < 2) { // Bottom 2 games
				gameGridGlow.setPosition(124 + i * 320, 54);
				gameGridIcon.setPosition(130 + i * 320, 60);
				gameName.setPosition(260 + i * 320, 120);
				star.setPosition(260 + i * 320, 80);
			} else if (i < 4) { // Middle 2 games
				gameGridGlow.setPosition(124 + i%2 * 320, 194);
				gameGridIcon.setPosition(130 + i%2 * 320, 200);
				gameName.setPosition(260 + i%2 * 320, 260);
				star.setPosition(260 + i%2 * 320, 220);
			} else { // Top 2 games
				gameGridGlow.setPosition(124 + i%2 * 320, 334);
				gameGridIcon.setPosition(130 + i%2 * 320, 340);
				gameName.setPosition(260 + i%2 * 320, 400);
				star.setPosition(260 + i%2 * 320, 360);
			}
			gameGridGlow.addListener(new ChangeListener() {
				public void changed(ChangeEvent event, Actor actor) {
					dispose();
					arcadeUI.setScreen(new StoreGame(arcadeUI, game));
				}
			});
			gameGridIcon.addListener(new ChangeListener() {
				public void changed(ChangeEvent event, Actor actor) {
					dispose();
					arcadeUI.setScreen(new StoreGame(arcadeUI, game));
				}
			});
			stage.addActor(gameGridGlow);
			stage.addActor(gameGridIcon);
			stage.addActor(gameName);
			stage.addActor(star);
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
		// No selected game for wishlist screen.
	}
	
	@Override
	public boolean addWishlist(Game game) {
		return true;
	}
}
