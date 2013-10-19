package deco2800.arcade.arcadeui;

import java.util.*;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.listener.LobbyListener;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.lobby.ActiveMatchDetails;
import deco2800.arcade.protocol.lobby.CreateMatchRequest;
import deco2800.arcade.protocol.lobby.JoinLobbyMatchRequest;
import deco2800.arcade.protocol.multiplayerGame.MultiGameRequestType;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.lobby.LobbyMessageRequest;
import deco2800.arcade.protocol.lobby.LobbyMessageResponse;

/**
 * 
 * @author Kieran Burke
 * 
 */

public class MultiplayerLobby implements Screen {

	private OrthographicCamera camera;
	private Stage stage;
	private Skin skin;
	private Skin skin2;
	private ShapeRenderer shapeRenderer;
	private ArcadeUI arcadeUI;
	private MultiplayerLobby lobby;
	ArrayList<ActiveMatchDetails> matches;
	private Player player;
	public String name;

	public MultiplayerLobby(ArcadeUI ui, Player player) {
		System.out.println(ui);
		arcadeUI = ui;
		this.player = player;
		this.lobby = this;
	}

	public void show() {
		ArcadeSystem.addPlayerToLobby();
		ArcadeSystem.initializeLobbyMatchList();
		ArcadeSystem.setMultiplayerEnabled(true);
		matches = ArcadeSystem.requestLobbyGamesList();
		shapeRenderer = new ShapeRenderer();
		stage = new Stage();
		ArcadeInputMux.getInstance().addProcessor(stage);
		
		bg = new Texture("homescreen_bg.png");
		bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bgSprite = new Sprite(bg);
		batch = new SpriteBatch();

		/* Add player to servers list of lobby users */
		ArcadeSystem.addPlayerToLobby();

		// Gui button & label Styles
		skin = new Skin();
		final Skin skin = new Skin(Gdx.files.internal("loginSkin.json"));
		skin.add("background", new Texture("homescreen_bg.png"));

		Label label = new Label("Matchmaking Lobby", skin);
		Label label2 = new Label("Chat:", skin);

		skin2 = new Skin();

		final Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		skin.add("white", new Texture(pixmap));
		skin2.add("white", new Texture(pixmap));

		skin.add("default", new BitmapFont());

		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = skin.getFont("default");
		skin2.add("default", labelStyle);

		TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
		textFieldStyle.font = skin.getFont("default");
		textFieldStyle.fontColor = Color.WHITE;
		textFieldStyle.cursor = skin2.newDrawable("white", Color.BLACK);
		textFieldStyle.selection = skin2.newDrawable("white", Color.BLUE);
		textFieldStyle.background = skin2.newDrawable("white", Color.DARK_GRAY);
		skin2.add("default", textFieldStyle);

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

		textButtonStyle.up = skin2.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin2.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin2.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.over = skin2.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");

		skin2.add("default", textButtonStyle);

		// Create buttons, labels and tables for layout purposes
		TextButton createbutton = new TextButton("Create Match", skin);
		TextButton refreshButton = new TextButton("Refresh", skin);
		TextButton button4 = new TextButton("Match Me!", skin);
		TextButton button2 = new TextButton("Return to Menu", skin);
		TextButton bettingButton = new TextButton("BL", skin);

		TextButton button3 = new TextButton(">>", skin);

		final TextField chatfield = new TextField("", skin);
		chatfield.setMessageText("Enter Chat");
		
		final Table table8 = new Table();
		final Table table4 = new Table();
		final Table table = new Table();
		final Table table2 = new Table();
		final Table table3 = new Table();
		final Table table5 = new Table();
		final Table bettingButtonTable = new Table();
		final Table table7 = new Table();
		Table chattable = new Table();
		
		final TextButton chatback = new TextButton("", skin, "green");
		
		final TextButton joinback = new TextButton("", skin, "green");

		table.setFillParent(true);
		table2.setFillParent(true);
		table3.setFillParent(true);
		chattable.setFillParent(true);
		table4.setFillParent(true);
		table5.setFillParent(true);
		bettingButtonTable.setFillParent(true);
		table7.setFillParent(true);
		table8.setFillParent(true);

		table3.setBackground(skin.getDrawable("background"));

		// Add tables to stage.
		stage.addActor(table3);
		stage.addActor(table8);
		stage.addActor(table5);
		stage.addActor(chattable);
		stage.addActor(table2);
		stage.addActor(table);
		stage.addActor(bettingButtonTable);
		stage.addActor(table7);

		bettingButtonTable.center().top().right();
		
		table8.center().left();
		table8.add(joinback).width(950).height(390);
		
		table7.center().right();
		table7.add(chatback).padRight(0).width(340).height(390);

		// Add tables and set position.
		table3.add(chatfield).width(200).height(35).padLeft(960).padTop(440);

		chattable.add(label2).padTop(440).padLeft(620);

		table3.add(button3).width(45).height(35).padLeft(3).padTop(440);

		table.add(createbutton).width(300).height(40).padRight(20).padTop(600);

		table.add(refreshButton).width(300).height(40).padRight(20).padTop(600);

		// table.add(button).width(300).height(40).padRight(20).padLeft(20).padTop(600);

		table.add(button4).width(300).height(40).padRight(20).padLeft(20)
				.padTop(600);

		table.add(button2).width(300).height(40).padLeft(20).padTop(600);
		
		bettingButtonTable.add(label).width(80).height(40).padRight(560)
				.padTop(25);

		bettingButtonTable.add(bettingButton).width(40).height(40).padRight(50)
				.padTop(25);

		if (matches.size() > 0) {
			matches = ArcadeSystem.requestLobbyGamesList();
			for (int i = 0; i < matches.size(); i++) {
				Label matchLabel = new Label(
						"GameId: " + matches.get(i).gameId, skin2);
				Label player = new Label("Player: "
						+ matches.get(i).hostPlayerId, skin2);
				final TextButton button5 = new TextButton("Join", skin);
				final int matchId = matches.get(i).matchId;

				table2.center().left();
				table2.add(matchLabel).width(130).padTop(5).padLeft(150);
				table2.add(player).width(130).padTop(5).padLeft(130);
				table2.add(button5).width(130).height(20).padTop(5);
				table2.row();

				button5.addListener(new JoinGameListener(matchId, matches.get(i).gameId, lobby));
			}
		}

		

		// "Refresh" button event listener.
		refreshButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				table2.clear();

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

						button5.addListener(new JoinGameListener(matchId, matches.get(i).gameId, lobby));
					}
				}
			}
		});

		// "Create Match" Button Event Listener
		createbutton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {

				dispose();
				arcadeUI.setScreen(arcadeUI.getMultigame2());

				// Code below commented out for overlay
				/*
				 * createMatch();
				 */
			}
		});

		// "Match Me" button event listener
		button4.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				// add to matchmaking queue method
				// add overlay to say 'waiting for match'
				// add second overlay (or modify first) to 'accept' or 'decline'
				// match
				dispose();

				ArcadeSystem.setMatchMaking(true);

				arcadeUI.setScreen(arcadeUI.getMultigame());

				// arcadeUI.setScreen(arcadeUI.getWait());

				// Code below has been commented out for overlay
				/*
				 * NewMultiGameRequest request = new NewMultiGameRequest();
				 * Set<String> games = ArcadeSystem.getGamesList(); int i = 0;
				 * String gameid = ""; for (String game : games) { if
				 * (game.equals("chess")) { gameid = game; break; } }
				 * request.gameId = gameid; request.playerID =
				 * arcadeUI.getPlayer().getID(); request.requestType =
				 * MultiGameRequestType.NEW; ArcadeSystem.goToGame(gameid);
				 * ArcadeSystem.createMultiplayerGame(request);
				 */
			}
		});

		// Betting Button: Betting Lobby
		bettingButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				ArcadeSystem.setBettingLobby(true);
				arcadeUI.setScreen(arcadeUI.getBettingLobby());
			}
		});

		// "Return to Menu" Button Event Listener
		button2.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				ArcadeSystem.removePlayerFromLobby();
				ArcadeSystem.setMultiplayerEnabled(false);
				arcadeUI.setScreen(arcadeUI.main);

			}
		});

		// "Chat" [>>] Button Event Listener
		button3.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {

				String message = chatfield.getText();
				System.out.println("You Said: " + chatfield.getText());

				chat(message);
				chatfield.setText("");
				
				

			}
		});

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1280, 720);

		// Gdx.app.exit();
	}

	@Override
	public void render(float arg0) {

		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);

		// Gdx.gl.glClearColor(0.9f, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		

		if (Gdx.input.isKeyPressed(Keys.B)) {
			//dispose();
			//ArcadeSystem.setPlayerBetting(true);
			//arcadeUI.setScreen(arcadeUI.getBetting());
		}
		if (Gdx.input.isKeyPressed(Keys.L)) {

		}

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		skin.dispose();
		skin2.dispose();
		ArcadeInputMux.getInstance().removeProcessor(stage);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	public void joinGame(int matchId, String gameId) {
		JoinLobbyMatchRequest request = new JoinLobbyMatchRequest();
		request.matchId = matchId;
		request.playerID = arcadeUI.getPlayer().getID();
		arcadeUI.getNetworkClient().sendNetworkObject(request);
		ArcadeSystem.setMultiplayerEnabled(true);
		ArcadeSystem.setGameWaiting(true);
		arcadeUI.setScreen(arcadeUI.getWait());
		ArcadeSystem.goToGame(gameId);
	}
	
	public void chat(String message) {
		LobbyMessageRequest request = new LobbyMessageRequest();
		request.playerID = arcadeUI.getPlayer().getID();
		request.user = player.getUsername();
		request.message = message;
		//chatfield.getText();
		arcadeUI.getNetworkClient().sendNetworkObject(request);
		//client.sendTCP(Request);
	}
	
	public void displayChat(LobbyMessageResponse request) {
		String message = request.message;
		String username = request.username;
		System.out.println(username + ": " + message);

		name = username + ": " + message;
	
		final TextField chatfield2 = new TextField(name, skin2);
		chatfield2.setText(name);

		final Table table6 = new Table(skin2);

		table6.setFillParent(true);
		stage.addActor(table6);
		
		table6.clear();
		table6.center().right();
		table6.add(chatfield2).padRight(20).width(300).height(345).padTop(23);
		table6.row();
		table6.add(" ").padRight(200);
	}


}
