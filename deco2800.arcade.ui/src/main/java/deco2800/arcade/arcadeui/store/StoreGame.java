package deco2800.arcade.arcadeui.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import deco2800.arcade.arcadeui.ArcadeUI;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

/**
 * This page generates differently based upon the parameter 'featuredGame'
 * given to it upon instantiation. It will load the game's name, description
 * and icon based upon it, and display other Players reviews and ratings at the
 * side of the screen, as well as the game's average rating.
 * @author Addison Gourluck
 */
public class StoreGame implements Screen, StoreScreen {
	private Skin skin = new Skin(Gdx.files.internal("store/storeSkin.json"));
	private Stage stage = new Stage();
	private static Game featured; // featured game
	private ArcadeUI arcadeUI;
	private Player player; // The currently logged in player.
	private float rating; // The rating of the feature game.
	
	/**
	 * @author Addison Gourluck
	 * @param ArcadeUI ui
	 * @param Player user
	 * @param Game featuredGame
	 */
	public StoreGame(ArcadeUI ui, Player user, Game featuredGame) {
		featured = featuredGame;
		arcadeUI = ui;
		player = user;
		
		final Table bg = new Table();
		final Table logo = new Table();
		final Button logoGlow = new Button(skin, "icon");
		final Label gameTitle = new Label(featuredGame.name, skin, "default-34");
		final Label gameDescription = new Label(featuredGame.description, skin);
		final Label ratingTitle = new Label("Ratings + Reviews", skin, "default-28");
		final Label ratingScore = new Label("0.0", skin, "rating-score");
		final Label ratingScoreText = new Label("Rating", skin, "default-14");
		final Table starbg = new Table();
		final Button homeButton = new Button(skin, "home");
		final Button buyButton = new Button(skin, "buy");
		final Button reviewButton = new Button(skin, "review");
		final CheckBox wishButton = new CheckBox("", skin, "wish");
		
		// The background for the store.
		skin.add("background", new Texture(Gdx.files.internal("store/game_bg.png")));
		bg.setFillParent(true);
		bg.setBackground(skin.getDrawable("background"));
		stage.addActor(bg);
		
		// Home button at top right corner of screen.
		homeButton.setSize(51, 46);
		homeButton.setPosition(1149, 650);
		stage.addActor(homeButton);
		
		// Title of the featured game, located center of screen.
		gameTitle.setSize(380, 40);
		gameTitle.setPosition(96, 515);
		stage.addActor(gameTitle);
		
		// Main text body, located in center of screen.
		gameDescription.setSize(430, 228);
		gameDescription.setPosition(141, 40);
		gameDescription.setWrap(true);
		gameDescription.setAlignment(Align.top | Align.left);
		stage.addActor(gameDescription);
		
		// Static element: text "Ratings + Reviews", located at top of right bar.
		ratingTitle.setSize(400, 50);
		ratingTitle.setPosition(870, 520);
		stage.addActor(ratingTitle);
		
		// The average rating of the game, located at top of right bar.
		ratingScore.setSize(90, 60);
		ratingScore.setPosition(870, 450);
		ratingScore.setAlignment(Align.bottom | Align.left);
		stage.addActor(ratingScore);
		
		// Static element: text "Average Rating", located right beside the Score.
		ratingScoreText.setSize(130, 30);
		ratingScoreText.setPosition(956, 455);
		ratingScoreText.setAlignment(Align.bottom | Align.left);
		stage.addActor(ratingScoreText);
		
		// Static element: glow effect around logo, at the left of the screen.
		logoGlow.setSize(160, 160);
		logoGlow.setPosition(135, 300);
		stage.addActor(logoGlow);
		
		// Attempt to load the featured game's logo, else load default logo.
		try {
			skin.add("logo", new Texture(Gdx.files.internal("logos/" + featured.id + ".png")));
		} catch (Exception e) {
			skin.add("logo", new Texture(Gdx.files.internal("logos/default.png")));
		}
		
		// Logo for the featured game, located at left of screen.
		logo.setBackground(skin.getDrawable("logo"));
		logo.setSize(140, 140);
		logo.setPosition(145, 310);
		stage.addActor(logo);
		
		// Static Element (Variable Listener). Will open transactions pop-up.
		buyButton.setSize(149, 62);
		buyButton.setPosition(298, 335);
		stage.addActor(buyButton);
		
		// Static Element (Variable Listener). Checkable, to add/remove from wishlist.
		wishButton.setSize(158, 62);
		wishButton.setPosition(443, 335);
		stage.addActor(wishButton);
		
		// Static Element (Variable Listener). Will re-direct to review screen.
		reviewButton.setSize(186, 68);
		reviewButton.setPosition(863, 335);
		stage.addActor(reviewButton);
		
		// Grey stars background for star rating.
		skin.add("starbg", new Texture(Gdx.files.internal("store/big_stars.png")));
		starbg.setBackground(skin.getDrawable("starbg"));
		starbg.setColor(0.5f, 0.5f, 0.5f, 1);
		starbg.setPosition(881, 414);
		starbg.setSize(142, 23);
		stage.addActor(starbg);
		
		// Places the checkbox-style rating stars over the grey starbg, and
		// adds listeners to allow user to select rating.
		placeRatingStars(ratingScore);

		// Button linking to back to the home page
		homeButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				arcadeUI.setScreen(arcadeUI.getStoreHome());
			}
		});

		// Button for purchasing the game.
		buyButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("buy");
			}
		});

		// Button for adding/removing featured game to wishlist.
		wishButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("wishlist");
			}
		});

		// Button adding a review to the featured game.
		reviewButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("review");
			}
		});
	}
	
	/**
	 * Places 5 invisible checkboxs over each other, which will highlight on
	 * mouseover, and stay highlighted on mouseclick.
	 * @author Addison Gourluck
	 * @param Label ratingScore 
	 */
	private void placeRatingStars(final Label ratingScore) {
		for (int i = 5; i >= 1; --i) {
			final CheckBox star = new CheckBox("", skin, "star" + i);
			star.setSize(i * 28.4f, 23);
			star.setName("STAR" + i); // "STAR1", "STAR2", etc
			star.setPosition(882, 413);
			// Listener to change rating when a star is clicked (not changed).
			star.addListener(new ClickListener() {
				public void clicked(InputEvent event, float x, float y) {
					// Sets all other stars to be unchecked.
					for (Actor find : stage.getActors()) {
						if (find.getName() != null && find != star
								&& find.getName().startsWith("STAR")) {
							((CheckBox)find).setChecked(false);
						}
					}
					// Sets the ratings, only for star clicked.
					if (!star.isChecked()) {
						rating = 0; // Star unchecked. Set rating to 0.
					} else {
						// Star checked, set rating to stars number.
						rating = star.getName().charAt(4) - 48f;
					}
					// Update rating text.
					ratingScore.setText(rating + "");
				}
			});
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
		return featured;
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
		for (Game search : ArcadeSystem.getArcadeGames()) {
			if (search.id.equals(game)) {
				featured = search;
				return;
			}
		}
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
