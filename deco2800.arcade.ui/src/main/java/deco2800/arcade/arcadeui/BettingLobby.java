package deco2800.arcade.arcadeui;

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

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.protocol.lobby.ActiveMatchDetails;

import java.util.*;

public class BettingLobby implements Screen {

	private class FrontPageStage extends Stage {
	}

	private Skin skin;
	private FrontPageStage stage;

	private float funds;
	private int tokens;
	boolean multiplayerEnabled;
	private boolean bclicked;

	Texture bg;
	Sprite bgSprite;
	SpriteBatch batch;

	private ArcadeUI arcadeUI;

	private MultiplayerLobby lobby;
	ArrayList<ActiveMatchDetails> matches;

	public BettingLobby(ArcadeUI ui) {

		arcadeUI = ui;
		skin = new Skin(Gdx.files.internal("loginSkin.json"));
		skin.add("background", new Texture("homescreen_bg.png"));
		stage = new FrontPageStage();

		Table table = new Table();
		table.setFillParent(true);
		table.setBackground(skin.getDrawable("background"));
		stage.addActor(table);

		bg = new Texture("homescreen_bg.png");
		bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bgSprite = new Sprite(bg);
		batch = new SpriteBatch();

		Table table2 = new Table();
		table2.setFillParent(true);
		stage.addActor(table2);

		Table titleTable = new Table();
		titleTable.setFillParent(true);
		stage.addActor(titleTable);

		Label gameLabel = new Label("Games to bet on:", skin);
		Label betLabel = new Label("Bets made:", skin);

		TextButton returnButton = new TextButton("Return to Lobby", skin);
		TextButton updateButton = new TextButton("Update Games", skin);
		TextButton placeBetButton = new TextButton("Place Bet", skin);

		table.center().bottom().left();
		titleTable.center().top().left();
		table2.center().bottom();

		titleTable.add(gameLabel).width(150).height(30).padTop(150).padLeft(30);
		titleTable.add(betLabel).width(150).height(30).padTop(150).padLeft(550);
		table.add(updateButton).width(150).height(40).padBottom(100)
				.padLeft(30);
		table.add(placeBetButton).width(150).height(40).padBottom(100)
				.padLeft(30);
		table2.add(returnButton).width(300).height(40).padBottom(50);

		// Return to lobby event listener
		returnButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {

				ArcadeSystem.setBettingLobby(false);
				ArcadeSystem.setMultiplayerEnabled(true);
				arcadeUI.setScreen(arcadeUI.getLobby());
			}
		});
		
		updateButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				gamesTable.clear();

				if (matches.size() > 0) {
					for (int i = 0; i < matches.size(); i++) {
						Label matchLabel = new Label("GameId: "
								+ matches.get(i).gameId, skin2);
						Label player = new Label("Player: "
								+ matches.get(i).hostPlayerId, skin2);
						final TextButton button5 = new TextButton("Join", skin);
						final int matchId = matches.get(i).matchId;

						table2.center().left();
						table2.add(matchLabel).width(130).padTop(5)
								.padLeft(150);
						table2.add(player).width(130).padTop(5).padLeft(130);
						table2.add(button5).width(130).height(20).padTop(5);
						table2.row();

						button5.addListener(new JoinGameListener(matchId, lobby));
					}
				}
			}
		});

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
		Table.drawDebug(stage); // Shows table debug lines

		if (bclicked == true) {
			System.out.println("going to arcadeui");
			ArcadeSystem.goToGame("arcadeui");
		}

	}

	@Override
	public void dispose() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
		stage.dispose();
		skin.dispose();
		arcadeUI.dispose();
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
}