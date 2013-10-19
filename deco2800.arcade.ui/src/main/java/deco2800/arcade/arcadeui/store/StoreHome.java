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
 * @author Addison Gourluck
 */
public class StoreHome implements Screen, StoreScreen {
	private Skin skin = new Skin(Gdx.files.internal("store/storeSkin.json"));
	private Stage stage = new Stage();
	private static Game featured = new Game();
	private float fade = 1; // Used for the featured bar fade-out.
	// Initially set to 1, so that the icon and text will fade in upon load.
	private Label description; // featured text
	private Button featured_icon; // featured icon
	private Button featured_bg; // featured icon glow/box
	private Button greyOverlay = new Button(skin, "black");
	private Button popupBox = new Button(skin, "white");
	
	private ArcadeUI arcadeUI;
	
	/**
	 * @author Addison Gourluck
	 * @param ui
	 */
	public StoreHome(ArcadeUI ui) {
		skin.add("blue_frame", new Texture(Gdx.files.internal("store/blue_frame.png")));
		arcadeUI = ui;
		Utilities.helper.loadIcons(skin);
		
		// The background for the store.
		skin.add("background", new Texture(Gdx.files.internal("store/main_bg.png")));
		Table bg = new Table();
		bg.setFillParent(true);
		bg.setBackground(skin.getDrawable("background"));
		stage.addActor(bg);
		
		final TextField searchField = new TextField("", skin);
		final Button searchButton = new Button(skin, "search");
		final Label searchResult = new Label("", skin);
		final TextButton libraryButton = new TextButton("Library", skin);
		final TextButton transactionsButton = new TextButton("Transactions", skin);
		final TextButton wishlistButton = new TextButton("Wishlist", skin);
		final TextButton reviewsButton = new TextButton("Reviews", skin);
		
		populateGamesBox();
		
		// The glowing border of the icon in the featured box.
		featured_bg = new Button(skin, "icon_bg");
		featured_bg.setSize(158, 158);
		featured_bg.setPosition(224, 453);
		stage.addActor(featured_bg);
		
		// The featured game's icon, located at the left of the top bar.
		featured_icon = new Button(skin, "icon");
		featured_icon.setSize(140, 140);
		featured_icon.setPosition(233, 462);
		stage.addActor(featured_icon);
		
		// The description of the featured game, 
		description = new Label("Loading...", skin);
		description.setWrap(true);
		description.setSize(400, 120);
		description.setPosition(350, 470);
		stage.addActor(description);
		
		// Entry field for search term. Will update the featured game, as well
		// as the search result located below it.
		searchField.setSize(300, 42);
		searchField.setPosition(860, 536);
		searchField.setMessageText(" Search");
		stage.addActor(searchField);
		
		// Search button. Will re-direct user to the (valid) "SearchResult" game page
		searchButton.setSize(126, 55);
		searchButton.setPosition(1039, 475);
		stage.addActor(searchButton);

		// The auto-changing text that displays the current game to be searched for.
		searchResult.setSize(165, 55);
		searchResult.setPosition(860, 475);
		stage.addActor(searchResult);
		
		libraryButton.setSize(360, 95);
		libraryButton.setPosition(834, 354.5f);
		stage.addActor(libraryButton);
		
		transactionsButton.setSize(360, 95);
		transactionsButton.setPosition(834, 253);
		stage.addActor(transactionsButton);
		
		wishlistButton.setSize(360, 95);
		wishlistButton.setPosition(834, 151.5f);
		stage.addActor(wishlistButton);
		
		reviewsButton.setSize(360, 95);
		reviewsButton.setPosition(834, 50);
		stage.addActor(reviewsButton);
		
		generatePopup();
		
		libraryButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				ArcadeSystem.goToGame("arcadeui");
			}
		});
		
		transactionsButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				arcadeUI.setScreen(new StoreTransactions(arcadeUI));
			}
		});
		
		wishlistButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				arcadeUI.setScreen(new StoreWishlist(arcadeUI));
			}
		});
		
		reviewsButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				stage.addActor(greyOverlay);
				stage.addActor(popupBox);
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
				if (featured.id != result.id) {
					featured = result;
					if (fade == 60) {
						fade--; // Will trigger textFade and iconFade in render.
					}
				}
			}
		});
		
		searchButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				try {
					setSelected(Utilities.helper.search(searchResult.getText() + "").id);
					dispose();
					arcadeUI.setScreen(new StoreGame(arcadeUI, featured));
				} catch (Exception e) {
					searchResult.setText("Invalid Search");
				}
			}
		});
		
		featured_icon.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				arcadeUI.setScreen(new StoreGame(arcadeUI, featured));
			}
		});
	}
	
	private void generatePopup() {
		greyOverlay.setFillParent(true);
		greyOverlay.setColor(0.5f, 0.5f, 0.5f, 0.5f);
		
		popupBox.getStyle().up = skin.getDrawable("blue_frame");
		popupBox.setSize(545, 300);
		popupBox.setPosition(368, 210);
		
		Button buy = new Button(skin, "buy");
		buy.setSize(149, 62);
		buy.setPosition(198, 10);
		popupBox.addActor(buy);
		
		// Buy button listener
		buy.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("DO BUY MANY TOKEN.");
			}
		});
		
		// Make grey table disappear upon being clicked
		greyOverlay.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				popupBox.remove();
				greyOverlay.remove();
			}
		});
	}

	/**
	 * This places 8 icons into a grid pattern in the display section in the
	 * centre of the main store page. The icons are randomly assigned a game to
	 * represent, and have a listener which will change the "featured" section.
	 * 
	 * @author Addison Gourluck
	 * @param Stage stage
	 * @param Skin skin
	 */
	private void populateGamesBox() {
		int number = (int)Math.floor(ArcadeSystem.getArcadeGames().size()
				* Math.random());
		// ^Used to find the first of the 8 games to be displayed.
		featured = (Game)ArcadeSystem.getArcadeGames().toArray()[number];
		for (int i = 0; i < 8; ++i) {
			Game game = (Game)ArcadeSystem.getArcadeGames().toArray()
					[(number + i)%ArcadeSystem.getArcadeGames().size()];
			final TextButton gameGridGlow =
					new TextButton("\n\n\n\n\n" + game.name, skin, "icon");
			gameGridGlow.setSize(160, 170);
			gameGridGlow.setName(game.id);
			
			final Button gameGridIcon = new Button(skin.getDrawable(game.id));
			gameGridIcon.setSize(120, 112);
			gameGridIcon.setName(game.id);
			
			// ^This sets the games 
			if (i < 4) {
				gameGridGlow.setPosition(112 + i * 172, 255);
				gameGridIcon.setPosition(132 + i * 172, 294);
			} else {
				gameGridGlow.setPosition(112 + i%4 * 172, 76);
				gameGridIcon.setPosition(132 + i%4 * 172, 115);
			}
			gameGridGlow.addListener(new ChangeListener() {
				public void changed(ChangeEvent event, Actor actor) {
					if (fade == 60) {
						setSelected(gameGridGlow.getName());
						fade--; // Will trigger textFade and iconFade in render.
					}
				}
			});
			gameGridIcon.addListener(new ChangeListener() {
				public void changed(ChangeEvent event, Actor actor) {
					if (fade == 60) {
						setSelected(gameGridIcon.getName());
						fade--; // Will trigger textFade and iconFade in render.
					}
				}
			});
			stage.addActor(gameGridGlow);
			stage.addActor(gameGridIcon);
		}
	}
	
	/**
	 * Checks every frame if 'fade' has changed. If so, then we get action!
	 * Text will fade out, then in, in the first 30 and last 30 frames.
	 * The icon will fade/slide out in the middle 80 frames (40, then 40).
	 * While 'description' is hidden, it will change to the new text.
	 * 'fade' is then reset.
	 * 
	 * @author Addison Gourluck
	 * @param fade < 70
	 * @param fade > -70
	 */
	private void textFade() {
		if (fade <= -60) {
			// reset text, in case some terrible occurrence occurred.
			fade = 60;
			description.setColor(1, 1, 1, 1);
			featured_bg.setColor(1, 1, 1, 1);
			featured_bg.setX(112);
			featured_icon.setColor(1, 1, 1, 1);
			featured_icon.setX(121);
		} else if (fade < 30 && fade > -30) {
			// While 'fade' is between 30 and -30, it will call iconfade.
			// The text will remain invisible, and will update to the new
			// description when fade == 0.
			fade--;
			iconFade();
			if (fade == 0) {
				featured_icon.getStyle().up = skin.getDrawable(featured.id);
				if(featured.description == null
						|| featured.description.equals("N/A")) {
					description.setText(featured.name
							+ "\nNo Description Available");
				} else if (featured.description.length() > 105) {
					description.setText(featured.name + "\n"
							+ featured.description.substring(0, 105) + "...");
				} else {
					description.setText(featured.name + "\n" + featured.description);
				}
			}
			description.setColor(1, 1, 1, 0);
		} else if (fade < 60 && fade > -60) {
			// text fade in and out.
			fade--;
			description.setColor(1, 1, 1, (Math.abs(fade) - 30) / 30);
		}
	}
	
	/**
	 * The other half of the fading on the 'featured' box. This is called when
	 * the text first becomes invisible. It will fade the icon out, and slide
	 * it out at the same sign, then do the reverse. Also has a small pause.
	 * 
	 * @author Addison Gourluck
	 * @param fade < 30
	 * @param fade > -30
	 */
	private void iconFade() {
		if (fade > -1) {
			featured_bg.setX(featured_bg.getX() + 4); // move right first
			featured_icon.setX(featured_icon.getX() + 4);
		} else if (fade < -1) {
			featured_bg.setX(featured_bg.getX() - 4); // then move back left
			featured_icon.setX(featured_icon.getX() - 4);
		} else if (fade == -1) {
			naptime(300); // stay hidden for a short while
		}
		featured_bg.setColor(1, 1, 1, Math.abs(fade) / 30);
		featured_icon.setColor(1, 1, 1, Math.abs(fade) / 30);
		// fade out icon
	}
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		textFade();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	public void naptime(int zzzz) {
		try {
			Thread.sleep(zzzz); // Zzzzzzz *snore*
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
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
		return null;
	}
	
	@Override
	public Game getSelected() {
		return featured;
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
		for (Game search : ArcadeSystem.getArcadeGames()) {
			if (search.id.equals(game)) {
				featured = search;
				return;
			}
		}
	}
	
	@Override
	public boolean addWishlist(Game game) {
		return true;
	}
}