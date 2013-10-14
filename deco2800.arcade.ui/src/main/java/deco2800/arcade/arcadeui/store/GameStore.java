package deco2800.arcade.arcadeui.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private Skin skin = new Skin(Gdx.files.internal("storeSkin.json"));
    private Stage stage = new Stage();
    private SpriteBatch batch = new SpriteBatch();
    private static Game featured = new Game();;

    Sprite left = new Sprite(new Texture(Gdx.files.internal("left_glow.png")), 0, 0, 39, 31);
    
    private float fade = 1;
    private Label description;
    private TextButton icon;
    
    //private ArcadeUI arcadeUI;

    public GameStore(ArcadeUI ui) {
    	//arcadeUI = ui;
		skin.add("background", new Texture("main_bg.png"));
		skin.add("temp", new Texture("right_glow.png"));
		
		Table bg = new Table();
		bg.setFillParent(true);
		bg.setBackground(skin.getDrawable("background"));
		stage.addActor(bg);
		
		icon = new TextButton("", skin, "icon");
		icon.setSize(140, 158);
		icon.setPosition(262, 453);
		stage.addActor(icon);
		
		final TextField searchField = new TextField("", skin);
		final TextButton searchButton = new TextButton("Search", skin, "green");
		final TextButton libraryButton = new TextButton("Library", skin, "green");
		final TextButton transactionsButton = new TextButton("Transactions", skin, "green");
		final TextButton wishlistButton = new TextButton("Wishlist", skin, "green");
		final TextButton reviewsButton = new TextButton("Reviews", skin, "green");
		
		description = new Label("de fault", skin);
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
		
		populateGamesBox(stage, skin, this);
		
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
		
		icon.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Something");
			}
		});
		
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
	
	private void populateGamesBox(Stage stage, Skin skin, Screen parent) {
		int number = (int)Math.floor(16 * Math.random());
		featured.id = ArcadeSystem.getGamesList().toArray()[number%16] + "";
		for (int i = 0; i < 8; ++i) {
			final TextButton gameGrid = new TextButton("\n\n\n\n\n" + ArcadeSystem.getGamesList().toArray()[(number+i)%16], skin);
			gameGrid.setSize(160, 169);
			gameGrid.setName("" + ArcadeSystem.getGamesList().toArray()[(number+i)%16]);
			if (i < 4) {
				gameGrid.setPosition(112 + (i%4) * 172, 255);
			} else {
				gameGrid.setPosition(112 + (i%4) * 172, 76);
			}
			gameGrid.addListener(new ChangeListener() {
				public void changed (ChangeEvent event, Actor actor) {
					setSelected(gameGrid.getName());
					fade--; // Will trigger textFade and iconFade in render.
					System.out.println(featured.id);
				}
			});
			stage.addActor(gameGrid);
		}
	}
	private void textFade() {
		if (fade <= -100) {
			fade = 100;
			description.setColor(1, 1, 1, 1);
		}
		if (fade < 50 && fade > -50) {
			fade--;
			iconFade();
			if (fade == 0) {
				if(featured.getDescription() == null || featured.getDescription().equals("N/A")) {
		            description.setText(featured.name + "\nNo Description Available");
		        } else {
		        	description.setText(featured.name + "\n" + featured.getDescription());
		        }
			}
			description.setColor(1, 1, 1, 0);
		} else if (fade < 100 && fade > -100) {
			fade--;
			description.setColor(1, 1, 1, (Math.abs(fade) - 50) / 50);
		}
	}
	
	private void iconFade() {
		// Used to reset icon movement in case it breaks due to spamming
		if (fade > 0) {
			icon.setX(icon.getX() + 3); // move right
		} else if (fade < 0) {
			icon.setX(icon.getX() - 3); // move back left
		} else if (fade == 0) {
			naptime(300); // stay hidden for a short while
		}
		icon.setColor(1, 1, 1, Math.abs(fade) / 50);
	}
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		textFade();
		batch.begin();
		//bgSprite.draw(batch);
		batch.end();
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
