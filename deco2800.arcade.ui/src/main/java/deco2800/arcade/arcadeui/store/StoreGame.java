package deco2800.arcade.arcadeui.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.arcadeui.ArcadeUI;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

/**
 * @author Addison Gourluck
 */
public class StoreGame implements Screen, StoreScreen {
	private Skin skin = new Skin(Gdx.files.internal("store/storeSkin.json"));
	private Stage stage = new Stage();
	private static Game featured;
	private ArcadeUI arcadeUI;
	
	/**
	 * @author Addison Gourluck
	 * @param ui
	 */
	public StoreGame(ArcadeUI ui, Game featuredGame) {
		setSelected(featuredGame.id);
		arcadeUI = ui;
		
		final Table bg = new Table();
		final Table logo = new Table();
		final Button logoGlow = new Button(skin, "icon");
		final Label gameTitle = new Label(featuredGame.name, skin, "default-34");
		final Label gameDescription = new Label(featuredGame.description, skin);
		final Label ratingTitle = new Label("Ratings + Reviews", skin, "default-34");
		final Label ratingScore = new Label("0.0", skin, "rating-score");
		final Label ratingScoreText = new Label("Average Rating", skin, "default-14");
		final Button homeButton = new Button(skin, "home");
		final Button buyButton = new Button(skin, "buy");
		final Button reviewButton = new Button(skin, "review");
		final CheckBox wishButton = new CheckBox("", skin, "wish");
		
		// The background for the store.
		skin.add("background", new Texture(Gdx.files.internal("store/game_bg.png")));
		bg.setFillParent(true);
		bg.setBackground(skin.getDrawable("background"));
		stage.addActor(bg);
		
		homeButton.setSize(51, 46);
		homeButton.setPosition(1149, 650);
		stage.addActor(homeButton);
		
		gameTitle.setSize(380, 40);
		gameTitle.setPosition(96, 513);
		stage.addActor(gameTitle);
		
		gameDescription.setSize(430, 250);
		gameDescription.setPosition(141, 50);
		gameDescription.setWrap(true);
		gameDescription.setAlignment(Align.top | Align.left);
		stage.addActor(gameDescription);
		
		ratingTitle.setSize(400, 50);
		ratingTitle.setPosition(870, 520);
		stage.addActor(ratingTitle);
		
		ratingScore.setSize(90, 60);
		ratingScore.setPosition(870, 450);
		ratingScore.setAlignment(Align.bottom | Align.left);
		stage.addActor(ratingScore);
		
		ratingScoreText.setSize(130, 30);
		ratingScoreText.setPosition(955, 455);
		ratingScoreText.setAlignment(Align.bottom | Align.left);
		stage.addActor(ratingScoreText);
		
		logoGlow.setSize(136, 136);
		logoGlow.setPosition(137, 322);
		stage.addActor(logoGlow);
		
		try {
			skin.add("logo", new Texture(Gdx.files.internal("logos/" + featured.id + ".png")));
		} catch (Exception e) {
			skin.add("logo", new Texture(Gdx.files.internal("logos/default.png")));
		}
		logo.setBackground(skin.getDrawable("logo"));
		logo.setSize(120, 120);
		logo.setPosition(145, 330);
		stage.addActor(logo);
		
		buyButton.setSize(149, 62);
		buyButton.setPosition(275, 335);
		stage.addActor(buyButton);
		
		wishButton.setSize(158, 62);
		wishButton.setPosition(420, 335);
		stage.addActor(wishButton);
		
		reviewButton.setSize(186, 68);
		reviewButton.setPosition(863, 335);
		stage.addActor(reviewButton);
		
		homeButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				arcadeUI.setScreen(new StoreHome(arcadeUI));
			}
		});
		
		buyButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("buy");
			}
		});
		
		wishButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("wish");
			}
		});
		
		reviewButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("review");
			}
		});
	}
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
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
