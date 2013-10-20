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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;


import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;

import java.util.*;

public class BettingLobby implements Screen {
	
	private static String pName = "user";
	private static int pCredits = 165;
	private static ArcadeUI arcadeUI;
	private Array<String> bets;
	private int betNum;
	private class FrontPageStage extends Stage {
	}

	
	private FrontPageStage stage;

	boolean multiplayerEnabled;
	private boolean bclicked;
	

	Texture bg;
	Sprite bgSprite;
	SpriteBatch batch;
	Skin skin = new Skin();
	

	
	ArrayList<ArrayList<Object>> matches;
	static Label topUserLabel;
	Table topTable = new Table();
	Label invalidLabel;

	public BettingLobby(ArcadeUI ui) {
		
		arcadeUI = ui;
		skin = new Skin(Gdx.files.internal("loginSkin.json"));
		skin.add("background", new Texture("homescreen_bg.png"));
		skin.add("menubar", new Texture("menuBar.png"));
		stage = new FrontPageStage();
		bets = new Array<String>();
		betNum= bets.size; 
		
		matches = ArcadeSystem.requestActiveGamesList();
		
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
		
		
		topTable.setSize(1280, 30);
		topTable.setPosition(0, 690);
		topTable.setBackground(skin.getDrawable("menubar"));
		stage.addActor(topTable);
		
		Table lobbyTable = new Table();
		lobbyTable.setFillParent(true);
		stage.addActor(lobbyTable);
		
		final Table gamesTable = new Table();
		gamesTable.setFillParent(true);
		stage.addActor(gamesTable);
		
		final Table betsTable = new Table();
		betsTable.setFillParent(true);
		stage.addActor(betsTable);
		
		final Table invalidTable = new Table();
		invalidTable.setFillParent(true);
		stage.addActor(invalidTable);

		Label gameLabel = new Label("Games to bet on:", skin);
		Label betLabel = new Label("Bets made:", skin);
		
		invalidLabel = new Label("Bet not valid", skin);
		invalidLabel.setVisible(false);
		
		Label lobbyLabel = new Label("Betting Lobby", skin);
		Label topBarLabel = new Label("Team !Shop", skin);
		topUserLabel = new Label(pName+ " | "+ pCredits + " Credits", skin);

		TextButton returnButton = new TextButton("Return to Lobby", skin);
		TextButton updateBetButton = new TextButton("Update Bet", skin, "magenta");
		TextButton updateButton = new TextButton("Update Games", skin, "magenta");
		//TextButton placeBetButton = new TextButton("Place Bet", skin, "magenta");

		table.center().bottom().left();
		titleTable.center().top().left();
		table2.center().bottom();
		topTable.center().top();
		lobbyTable.center().top();
		invalidTable.center().bottom().left();
		
		topTable.add(topBarLabel).width(150).height(30).padTop(0).padLeft(5);
		topTable.add(topUserLabel).width(150).height(30).padTop(0).padLeft(925);
		lobbyTable.add(lobbyLabel).width(150).height(30).padTop(50).padLeft(0);
		titleTable.add(gameLabel).width(150).height(30).padTop(150).padLeft(30);
		titleTable.add(betLabel).width(150).height(30).padTop(150).padLeft(550);
		table.add(updateButton).width(150).height(40).padBottom(150).padLeft(30);
		table.add(updateBetButton).width(150).height(40).padBottom(150).padLeft(500);
		table2.add(returnButton).width(300).height(40).padBottom(50);
		invalidTable.add(invalidLabel).width(150).height(30).padBottom(60).padLeft(30);

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
				matches= ArcadeSystem.requestActiveGamesList();
				System.out.print(matches);
				gamesTable.clear();
				invalidLabel.setVisible(false);
				if (matches.size() > 0) {
					int matchNum;
					if (matches.size()>9) {
						matchNum = 9;
					}else{
						matchNum = matches.size();
					}
					for (int i = 0; i < matchNum; i++) {
						final int p1 = 1;//matches.get(i).hostPlayerId;
						final int p2 = 2;//matches.get(i).hostPlayerId;
						final String game = "PONG";//matches.get(i).gameId;
						Label gameInfo = new Label("Player: "+p1+" Vs "+"Player: "+p2+ " in "+ game, skin);
						final TextField betfield = new TextField("", skin);
						betfield.setMessageText("0");
						final TextButton betButton = new TextButton("place Bet", skin);
						

						gamesTable.center().left();
						gamesTable.add(gameInfo).width(130).padTop(5).padLeft(30);
						gamesTable.add(betfield).width(40).height(20).padTop(5).padLeft(225);
						gamesTable.add(betButton).width(130).height(20).padTop(5).padLeft(25);
						gamesTable.row();
						
						betButton.addListener(new ChangeListener() {
							public void changed(ChangeEvent event, Actor actor) {
								betNum = bets.size;
								if(betNum<9){
									createBet(betfield.getText(), p1, p2, game);
								}else{
									invalidLabel.setText("Bet Maximum Reached");
									invalidLabel.setVisible(true);
								}
							}
						});
						
					}
				}
			}
		});
		
		
		updateBetButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				betsTable.clear();
				
				if (bets.size > 0) {
					for (int i = 0; i < bets.size; i++) {
						
						TextButton bet = new TextButton(bets.get(i), skin, "green");
						bet.setSize(500, 30);
						
						betsTable.center().right();
						betsTable.add(bet).size(500, 30).padTop(5).padRight(75);
						betsTable.row();
						
					}
				}
			}
		});

	}
	public static void setName() {
        topUserLabel.setText(pName + " | " + pCredits + " Credits");
	}
	private void createBet(String text, int p1, int p2,
			String game) {
		int bet = -1;
		try {
			bet = Integer.parseInt(text);
		} catch (Exception e) {
			invalidLabel.setText("Bet Not Valid");
			invalidLabel.setVisible(true);
			return;
		}
		if(bet>0 && bet<pCredits){
			pCredits = pCredits -bet;
			String player = "Player "+Integer.toString(p1);
			String player2 = "Player "+Integer.toString(p1);
			String betAmount = text;
			String betString = player + " vs " + player2 + " in "+ game+ " for "+ betAmount;
			bets.add(betString);
			invalidLabel.setVisible(false);
			System.out.print("Remaining Credits "+ pCredits);
		}
		else {
			invalidLabel.setText("Not Enough Credits");
			invalidLabel.setVisible(true);
		}
		
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