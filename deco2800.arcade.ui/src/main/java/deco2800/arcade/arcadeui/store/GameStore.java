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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.arcadeui.ArcadeUI;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

public class GameStore implements Screen, StoreScreen {
    private Skin skin = new Skin(Gdx.files.internal("store/storeSkin.json"));
    private Stage stage = new Stage();
    private static Game featured = new Game();;
    
    private float fade = 1;
    private Label description;
    private Button featured_bg;
    private Button featured_icon;
    
    //private ArcadeUI arcadeUI;
    
    /**
     * @author Addison Gourluck
     * @param ui
     */
    public GameStore(ArcadeUI ui) {
    	//arcadeUI = ui;
		skin.add("background", new Texture(Gdx.files.internal("store/main_bg.png")));
		
		for (String gamename : ArcadeSystem.getGamesList()) {
			try {
				skin.add(gamename, new Texture(Gdx.files.internal("logos/" + gamename + ".png")));
				System.out.println("logos/" + gamename + ".png");
			} catch (Exception e) {
				skin.add(gamename, new Texture(Gdx.files.internal("logos/default.png")));
				System.out.println("logos/default.png");
			}
		}
		
		Table bg = new Table();
		bg.setFillParent(true);
		bg.setBackground(skin.getDrawable("background"));
		stage.addActor(bg);
		
		final TextField searchField = new TextField("", skin);
		final TextButton searchButton = new TextButton("Search", skin);
		final TextButton libraryButton = new TextButton("Library", skin);
		final TextButton transactionsButton = new TextButton("Transactions", skin);
		final TextButton wishlistButton = new TextButton("Wishlist", skin);
		final TextButton reviewsButton = new TextButton("Reviews", skin);
		
		populateGamesBox(stage, skin);
		
		featured_bg = new Button(skin, "icon");
		featured_bg.setSize(158, 158);
		featured_bg.setPosition(226, 453);
		stage.addActor(featured_bg);
		
		featured_icon = new Button(skin);
		featured_icon.setSize(140, 140);
		featured_icon.setPosition(235, 462);
		stage.addActor(featured_icon);
		
		description = new Label("Loading...", skin);
		description.setWrap(true);
		description.setSize(400, 120);
		description.setPosition(350, 480);
		stage.addActor(description);
		
		searchField.setSize(300, 42);
		searchField.setPosition(860, 535);
		searchField.setMessageText("Search");
		stage.addActor(searchField);
		
		searchButton.setSize(140, 40);
		searchButton.setPosition(1025, 480);
		stage.addActor(searchButton);
		
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
		
		libraryButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Library clicked");
				//arcadeUI.setScreen(arcadeUI.getLobby());
				dispose();
				ArcadeSystem.goToGame("arcadeui");
			}
		});
		
		transactionsButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Transactions clicked");
			}
		});
		
		wishlistButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Wishlist clicked");
			}
		});
		
		reviewsButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Reviews clicked");
			}
		});
	}
	
    /**
     * This places 8 icons into the a grid pattern in the display section in
     * the centre of the main store page. The icons are randomly assigned a
     * game to represent, and have a listener which will change the "featured"
     * section.
     * 
     * @author Addison Gourluck
     * @param stage
     * @param skin
     */
	private void populateGamesBox(Stage stage, Skin skin)  {
		int number = (int)Math.floor(ArcadeSystem.getGamesList().size() * Math.random());
		// ^Used to find the first of the 8 games to be displayed.
		featured = (Game)ArcadeSystem.getArcadeGames().toArray()
				[number%ArcadeSystem.getGamesList().size()];
		for (int i = 0; i < 8; ++i) {
			String Gamename = ArcadeSystem.getGamesList().toArray()
					[(number+i)%ArcadeSystem.getGamesList().size()] + "";
			final TextButton gameGrid =
					new TextButton("\n\n\n\n\n" + Gamename, skin, "icon");
			gameGrid.setSize(160, 170);
			gameGrid.setName(Gamename);
			
			final Button gameGridIcon = new Button(skin.getDrawable(Gamename));
			gameGridIcon.setSize(120, 112);
			gameGridIcon.setName(Gamename);
			
			// ^This sets the games 
			if (i < 4) {
				gameGrid.setPosition(112 + (i%4) * 172, 255);
				gameGridIcon.setPosition(132 + (i%4) * 172, 294);
			} else {
				gameGrid.setPosition(112 + (i%4) * 172, 76);
				gameGridIcon.setPosition(132 + (i%4) * 172, 115);
			}
			gameGrid.addListener(new ChangeListener() {
				public void changed (ChangeEvent event, Actor actor) {
					if (fade == 70) {
						setSelected(gameGrid.getName());
						fade--; // Will trigger textFade and iconFade in render.
					}
				}
			});
			gameGridIcon.addListener(new ChangeListener() {
				public void changed (ChangeEvent event, Actor actor) {
					if (fade == 70) {
						setSelected(gameGrid.getName());
						fade--; // Will trigger textFade and iconFade in render.
					}
				}
			});
			stage.addActor(gameGrid);
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
		if (fade <= -70) {
			fade = 70;
			description.setColor(1, 1, 1, 1);
			featured_bg.setColor(1, 1, 1, 1);
			featured_bg.setX(112);
			featured_icon.setColor(1, 1, 1, 1);
			featured_icon.setX(121);
		} else if (fade < 40 && fade > -40) {
			fade--;
			iconFade();
			if (fade == 0) {
				featured_icon.getStyle().up = skin.getDrawable(featured.id);
				if(featured.getDescription() == null
						|| featured.getDescription().equals("N/A")) {
		            description.setText(featured.name
		            		+ "\nNo Description Available");
		        } else {
		        	description.setText(featured.name
		        			+ "\n" + featured.getDescription());
		        }
			}
			description.setColor(1, 1, 1, 0);
		} else if (fade < 70 && fade > -70) {
			fade--;
			description.setColor(1, 1, 1, (Math.abs(fade) - 40) / 30);
		}
	}
	
	/**
	 * The other half of the fading on the 'featured' box. This is called when
	 * the text first becomes invisible. It will fade the icon out, and slide
	 * it out at the same sign, then do the reverse. Also has a small pause.
	 * 
	 * @author Addison Gourluck
	 * @param fade < 40
	 * @param fade > -40
	 */
	private void iconFade() {
		if (fade > -1) {
			featured_bg.setX(featured_bg.getX() + 3); // move right
			featured_icon.setX(featured_icon.getX() + 3);
		} else if (fade < -1) {
			featured_bg.setX(featured_bg.getX() - 3); // move back left
			featured_icon.setX(featured_icon.getX() - 3);
		} else if (fade == -1) {
			naptime(400); // stay hidden for a short while
		}
		featured_bg.setColor(1, 1, 1, Math.abs(fade) / 40);
		featured_icon.setColor(1, 1, 1, Math.abs(fade) / 40);
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
		    Thread.sleep(zzzz);
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
	public boolean buyTokens(int amount, Game game) {
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
}
