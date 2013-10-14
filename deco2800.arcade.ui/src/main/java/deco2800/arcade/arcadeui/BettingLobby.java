package deco2800.arcade.arcadeui;



import java.util.ArrayList;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.protocol.lobby.ActiveMatchDetails;


/**
 * 
 * @author Carl Beaverson
 *
 */

public class BettingLobby implements Screen {

	private OrthographicCamera camera;
	private Stage stage;
	private Skin skin;
	private Skin skin2;
	private ShapeRenderer shapeRenderer;
	private ArcadeUI arcadeUI;
	ArrayList<ActiveMatchDetails> matches; 
	BettingLobby betLobby;
	private ArrayList<String> betsPlaced; //This is to be replaced with ArrayList<Bet>
	
	final Table returnArea = new Table();
	final Table gamesArea = new Table();
	final Table gameButtonArea = new Table();
	final Table labelArea = new Table();
	final Table betArea = new Table();
	
	
	public BettingLobby(ArcadeUI ui) {
		arcadeUI = ui;
		betLobby = this;
	}
	
	
	public void show() {
		shapeRenderer = new ShapeRenderer();
		stage = new Stage();
		ArcadeInputMux.getInstance().addProcessor(stage);
		ArcadeSystem.initializeLobbyMatchList();
		matches  = ArcadeSystem.requestLobbyGamesList();
		
		//Simple bet for test purposes

		// Gui button & label Styles 		
		skin = new Skin();
		final Skin skin = new Skin(Gdx.files.internal("loginSkin.json"));
		skin.add("background", new Texture("homescreen_bg.png"));

		

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
		
		TextButton returnToLobby = new TextButton("Return to Lobby", skin);

		TextButton placeBet = new TextButton("Place Bet", skin);

		TextButton refresh = new TextButton("refresh", skin);

		final Label gameLabel = new Label("Games To bet on: ", skin);
		final Label betLabel = new Label("Your Bets: ", skin);
	
		

		returnArea.setFillParent(true);
		labelArea.setFillParent(true);
		gamesArea.setFillParent(true);
		gameButtonArea.setFillParent(true);
		betArea.setFillParent(true);
		gameButtonArea.center().left();
		betArea.center().right();
		
		gameButtonArea.setBackground(skin.getDrawable("background"));

		//Add tables to stage.
		stage.addActor(gameButtonArea);
		stage.addActor(betArea);
		stage.addActor(labelArea);
		stage.addActor(gamesArea);
		stage.addActor(returnArea);


		// Add tables and set position.
		labelArea.center().top().left();
		
		labelArea.add(gameLabel).width(150).height(40).padLeft(50).padTop(150);
		labelArea.add(betLabel).width(150).height(40).padLeft(550).padTop(150);
		
		
		
		gameButtonArea.add(refresh).width(100).height(35).padLeft(50).padTop(240);

		gameButtonArea.add(placeBet).width(100).height(35).padLeft(200).padTop(240);
		

		returnArea.add(returnToLobby).width(300).height(40).padLeft(0).padTop(600);
	

		// "Return to Menu" Button Event Listener
		returnToLobby.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				dispose();
				ArcadeSystem.setBetLobby(false);
				ArcadeSystem.setMultiplayerEnabled(true);
				arcadeUI.setScreen(arcadeUI.getLobby());

			}
		});
		
		refresh.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				updateGameList();

			}
		});

		
		placeBet.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				int bet = -1;
				String input = "Bet Placed";
				try {
					bet = Integer.parseInt(input);
				} catch (Exception e) {	
					
					return;
				}
				
				if ((bet<1000) && (bet>0)) {
					System.out.println("Bet Placed: " + input);
					
				}else {
					System.out.println("Bet not valid");
				}
	
			}
		});
		updateGameList();
		updateBetList();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 640, 360);

		//Gdx.app.exit();
	}
	
public void updateGameList() {
	
	gamesArea.clear();
	
	if (matches.size() > 0 ) {
		matches = ArcadeSystem.requestLobbyGamesList();
		for (int i = 0; i < matches.size(); i++) {

			Label matchLabel = new Label("Game: " + matches.get(i).gameId, skin2);
			Label players = new Label("Player1:  " + matches.get(i).hostPlayerId +"  Vs  "+"Player2:  "+matches.get(i).playerID, skin2);
			//final TextButton button5 = new TextButton("Join", skin);
			final TextField bet = new TextField("", skin2);
			bet.setText("0");
			gamesArea.center().left();
			gamesArea.add(players).width(130).padTop(5).padLeft(50);
			gamesArea.add(matchLabel).width(130).padTop(5).padLeft(50);
			gamesArea.add(bet).width(30).height(20).padTop(5).padLeft(50);
			gamesArea.row();
			}
		}
	}
public void updateBetList() {
	
	//betArea.clear();
	
	if (/*betsPlaced.size()*/ 1 > 0 ) {
		for (int i = 0; i < 2/*betsPlaced.size()*/; i++) {
			Label matchPlayers = new Label("Player1" + " Vs " + "Player2", skin2);
			Label betInfo = new Label("Your Bet:  " + "100"+";" + " Tokens" +" Odds: "+"2:1", skin2);
			//final TextButton button5 = new TextButton("Join", skin);
			
			betArea.add(matchPlayers).width(200).padTop(5).padRight(50);
			betArea.add(betInfo).width(200).padTop(5).padRight(100);
			betArea.row();
		}
	}
}
	
	
	public void refreshGames() {
		
	}

	@Override
	public void render(float arg0) {

		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0.2f, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

	}


	@Override
	public void dispose() {
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