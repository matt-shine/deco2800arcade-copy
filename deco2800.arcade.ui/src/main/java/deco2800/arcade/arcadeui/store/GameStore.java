package deco2800.arcade.arcadeui.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

public class GameStore implements Screen, StoreScreen {
    private Skin skin;
    private Stage stage;
	
    Texture bg;
    Sprite bgSprite;
    SpriteBatch batch;

    public GameStore() {
		skin = new Skin(Gdx.files.internal("storeSkin.json"));
		skin.add("background", new Texture("homescreen_bg.png"));
		stage = new Stage();
		
		Table table = new Table();
		table.setFillParent(true);
		table.setBackground(skin.getDrawable("background"));
		stage.addActor(table);
		
		bg = new Texture("homescreen_bg.png");
		bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bgSprite = new Sprite(bg);
		batch = new SpriteBatch();
		
		final TextButton featuredBox = new TextButton("featured", skin, "magenta-box");
		final TextButton searchBox = new TextButton("", skin, "yellow-box");
		final TextField searchField = new TextField("", skin);
		final TextButton searchButton = new TextButton("Search", skin, "yellow-box");
		final TextButton gamesBox = new TextButton("", skin, "blue-box");
		final TextButton libraryButton = new TextButton("Library", skin, "magenta");
		final TextButton transactionsButton = new TextButton("Transactions", skin, "green");
		final TextButton wishlistButton = new TextButton("Wishlist", skin, "green");
		final TextButton reviewsButton = new TextButton("Reviews", skin, "green");
		
		featuredBox.setSize(780, 145);
		featuredBox.setPosition(60, 460);
		stage.addActor(featuredBox);

		searchBox.setSize(360, 145);
		searchBox.setPosition(860, 460);
		stage.addActor(searchBox);
		
		searchField.setSize(300, 40);
		searchField.setPosition(890, 540);
		searchField.setMessageText("Search");
		stage.addActor(searchField);
		
		searchButton.setSize(140, 40);
		searchButton.setPosition(1040, 490);
		stage.addActor(searchButton);
		
		gamesBox.setSize(780, 430);
		gamesBox.setPosition(60, 20);
		stage.addActor(gamesBox);
		
		populateGamesBox(stage, skin, this);
		
		libraryButton.setSize(360, 90);
		libraryButton.setPosition(860, 350);
		stage.addActor(libraryButton);
		
		transactionsButton.setSize(360, 90);
		transactionsButton.setPosition(860, 240);
		stage.addActor(transactionsButton);
		
		wishlistButton.setSize(360, 90);
		wishlistButton.setPosition(860, 130);
		stage.addActor(wishlistButton);
		
		reviewsButton.setSize(360, 90);
		reviewsButton.setPosition(860, 20);
		stage.addActor(reviewsButton);
		
		libraryButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Library clicked");
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
	
	private static void populateGamesBox (Stage stage, Skin skin, Screen parent) {
		//You can't put this kind of thing in master. It crashes if I
		//don't have the exact same classpath configuration as you.
		//I won't fix it, I'm just going to comment it out.
		//--Simon
		/*
		int number = (int)Math.floor(16 * Math.random());
		for (int i = 0; i < 8; ++i) {
			Label game = new Label("------------\n|                |\n|" +
					"    icon     |\n|                |\n------------\n " + ArcadeSystem.getGamesList().toArray()[(number+i)%16], skin);
			game.setSize(160, 170);
			game.setWrap(true);
			game.setAlignment(Align.center);
			if (i < 4) {
				game.setPosition(115 + (i%4)*170, 240);
			} else {
				game.setPosition(115 + (i%4)*170, 60);
			}
			stage.addActor(game);
		}
		*/
	}
    
	@Override
	public void show() {
		ArcadeInputMux.getInstance().addProcessor(stage);
	}
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		bgSprite.draw(batch);
		batch.end();
		
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        Table.drawDebug(stage);  // Shows table debug lines
	}
	
	@Override
	public void dispose() {
        stage.dispose();
        skin.dispose();
        ArcadeInputMux.getInstance().removeProcessor(stage);
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
	public void setSelected(Game game) {
	}

	@Override
	public Game getSelected() {
		return null;
	}

	@Override
	public boolean buyTokens(int amount, Game game) {
		return false;
	}
}
