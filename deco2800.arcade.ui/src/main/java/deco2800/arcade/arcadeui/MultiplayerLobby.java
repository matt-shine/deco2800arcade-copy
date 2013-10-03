package deco2800.arcade.arcadeui;

import java.util.*;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.InternalGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.protocol.lobby.ActiveMatchDetails;
import deco2800.arcade.protocol.lobby.CreateMatchRequest;
import deco2800.arcade.protocol.multiplayerGame.MultiGameRequestType;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;

/**
 * 
 * @author Kieran Burke
 *
 */

public class MultiplayerLobby implements Screen {

	private OrthographicCamera camera;
	private Stage stage;
	private Screen screen;
	private Skin skin;
	private Skin skin2;
	private ShapeRenderer shapeRenderer;
	private ShapeRenderer shapeRenderer2;
	private ArcadeUI arcadeUI;
	;

	public MultiplayerLobby(ArcadeUI ui) {
		arcadeUI = ui;
	}
	
	public void show() {
		shapeRenderer = new ShapeRenderer();
		stage = new Stage();
		ArcadeInputMux.getInstance().addProcessor(stage);

        
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


		//Create buttons, labels and tables for layout purposes 
		//Changed open games 
		TextButton createbutton = new TextButton("Create Match", skin);
		TextButton button = new TextButton("Open Games", skin);
		TextButton button4 = new TextButton("Match Me!", skin);
		TextButton button2 = new TextButton("Return to Menu", skin);

		TextButton button3 = new TextButton(">>", skin);

		final TextField chatfield = new TextField("", skin);
		chatfield.setMessageText("Enter Chat");

		final Table table4 = new Table();
		final Table table = new Table();
		final Table table2 = new Table();
		final Table table3 = new Table();	
		final Table table5 = new Table();

		Table chattable = new Table();

		table.setFillParent(true);
		table2.setFillParent(true);
		table3.setFillParent(true);
		chattable.setFillParent(true);
		table4.setFillParent(true);
		table5.setFillParent(true);

		table3.setBackground(skin.getDrawable("background"));

		//Add tables to stage.
		stage.addActor(table3);
		stage.addActor(table5);
		stage.addActor(chattable);
		stage.addActor(table2);
		stage.addActor(table);


		// Add tables and set position.
		table3.add(chatfield).width(200).height(35).padLeft(960).padTop(440);

		chattable.add(label2).padTop(440).padLeft(620);

		table3.add(button3).width(45).height(35).padLeft(3).padTop(440);

		table.add(createbutton).width(300).height(40).padRight(20).padTop(600);
		
		table.add(button).width(300).height(40).padRight(20).padLeft(20).padTop(600);
		
		table.add(button4).width(300).height(40).padRight(20).padLeft(20).padTop(600);

		table.add(button2).width(300).height(40).padLeft(20).padTop(600);
		
		
		/** Open Games Button Event Listener 
		The open games button Looks through an array and returns the number of elements; 
		in the form of buttons with unique ids as well as avatars and player info etc.
		 */
		button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {

				ArcadeSystem.requestLobbyGamesList();
				final ArrayList<ActiveMatchDetails> matches = Arcade.getMatches();
				System.out.println("Matches: " + matches.toString());

				//table2.removeActor();
				table2.clear();
				
			
				if (matches.size() > 0 ) {
					for (int i = 0; i < matches.size(); i++) {
						Label matchLabel = new Label("GameId: " + matches.get(i).gameId, skin2);
						Label player = new Label("Player: " + matches.get(i).hostPlayerId, skin2);
						final TextButton button4 = new TextButton("Join", skin);
						
						table2.center().left();
						table2.add(matchLabel).width(130).padTop(20).padLeft(150);
						table2.add(player).width(130).padTop(20).padLeft(130);
						table2.add(button4).width(130).height(30).padTop(20);
						table2.row();
						
						button4.addListener(new ChangeListener() {
				            public void changed (ChangeEvent event, Actor actor) {
				                System.out.println("You Clicked: " + button4.getName());
								
							}
				            
				        });
					}
		
				}
	
				
				
				//Create "Refresh" Button.
				table2.row();
				final TextButton refresh = new TextButton("Refresh", skin);
				final TextButton spacer = new TextButton("", skin);
				table2.add(spacer).width(0).height(0).padTop(30).padBottom(80);
				table2.add(refresh).width(160).height(35).padTop(30).padBottom(80);	
				
				// "Refresh" button event listener.
					 refresh.addListener(new ChangeListener() {
				public void changed (ChangeEvent event, Actor actor) {
					table2.clear();
					
					if (matches.size() > 0 ) {
						for (int i = 0; i < matches.size(); i++) {
							Label matchLabel = new Label("GameId: " + matches.get(i).gameId, skin2);
							Label player = new Label("Player: " + matches.get(i).hostPlayerId, skin2);
							final TextButton button4 = new TextButton("Join", skin);
							
							table2.center().left();
							table2.add(matchLabel).width(130).padTop(20).padLeft(150);
							table2.add(player).width(130).padTop(20).padLeft(130);
							table2.add(button4).width(130).height(30).padTop(20);
							table2.row();
							
							button4.addListener(new ChangeListener() {
					            public void changed (ChangeEvent event, Actor actor) {
					                System.out.println("You Clicked: " + button4.getName());
									
								}
					            
					        });
						}
			
					}
					//Create "Refresh" Button.
					table2.row();
					final TextButton refresh = new TextButton("Refresh", skin);
					final TextButton spacer = new TextButton("", skin);
					table2.add(spacer).width(0).height(0).padTop(30).padBottom(80);
					table2.add(refresh).width(160).height(35).padTop(30).padBottom(80);	
				
			}
				
				
            });
				
				
            }
			
			
        });

		
		
		
		// "Create Match" Button Event Listener
		 createbutton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	//dispose();
            	//System.out.println("DISPOSED************************************************");
            	ArcadeSystem.setMultiplayerEnabled(false);
            	NewMultiGameRequest request = new NewMultiGameRequest();
            	Set<String> games = ArcadeSystem.getGamesList();
            	int i = 0;
            	String gameid = "";
            	for (String game : games) {
            		if (game.equals("chess")) {
            			gameid = game;
            			break;
            		}
            	}
            	System.out.println("game id for reqeust is : " + gameid);
            	request.gameId = "arcadeui";
            	request.playerID = 0;
            	request.requestType = MultiGameRequestType.NEW;
            	arcadeUI.setScreen(arcadeUI.getHome());
            	ArcadeSystem.createMatch(request);
            	/*CreateMatchRequest request = new CreateMatchRequest();
            	request.gameId = Integer.toString((int)(Math.random()*10000));
            	System.out.println("**********" + request.gameId);
            	request.hostPlayerId = (int) (Math.random()*1000);*/
            	//ArcadeSystem.createMatch(request);
            }
        });

		
		//"Match Me" button event listener
		button4.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				//add to matchmaking queue method
				//add overlay to say 'waiting for match'
				//add second overlay (or modify first) to 'accept' or 'decline' match
			}
		});


		// "Return to Menu" Button Event Listener
		button2.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {

				dispose();
				ArcadeSystem.setMultiplayerEnabled(false);
				//ArcadeSystem.goToGame("arcadeui");
				arcadeUI.setScreen(arcadeUI.getHome());

			}
		});

		// "Chat" [>>] Button Event Listener
		button3.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {


				chatfield.getText();
				System.out.println("You Said: " + chatfield.getText());

				Label chat = new Label(chatfield.getText(), skin2);

				table5.clear();
				table5.center().right();
				table5.add(chat).padRight(200);
				table5.row();
				chatfield.setText("");


			}
		});

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1280, 720);

		//Gdx.app.exit();
	}
	
	public void refreshGames() {
		
	}

	@Override
	public void render(float arg0) {

		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		//stage.pause();
		//screen.dispose();

		//Gdx.gl.glClearColor(0.9f, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		/*
		shapeRenderer.begin(ShapeType.FilledRectangle);

	    shapeRenderer.filledRect(50,
	        100,
	        1280 - 100,
	        720 - 200);

	    shapeRenderer.end();*/
		
		if (Gdx.input.isKeyPressed(Keys.B)) {
	    	ArcadeSystem.setPlayerBetting(true);
	    	arcadeUI.setScreen(arcadeUI.getBetting());
	    }

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		System.out.println("########LOBBY DISPOSED############");
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
	


}
