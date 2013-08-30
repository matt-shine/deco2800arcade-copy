package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.arcade.deerforest.models.gameControl.GameSystem;

//This class functions as sort of a higher level game system controller
//As well as (most importantly) being an instance of a game (according to Gdx)
//to run
public class MainGame extends Game {

	SpriteBatch batch;
	BitmapFont font;
	final private GameSystem model;

	
	public MainGame(GameSystem model) {
		this.model = model;
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font
		font = new BitmapFont(true);
		this.setScreen(new MainGameScreen(this));	
		model.startgame(true);
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
	
	public GameSystem getModel() {
		return this.model;
	}
	
	public void changeTurns() {
		
		//check to see if we are on start phase already
		if(model.getPhase().equals("StartPhase")) model.nextPhase();
		
		//Go to next phases till end of turn
		while(!model.getPhase().equals("StartPhase")) {
			model.nextPhase();
		}
	}
	
	public void nextPhase() {
		model.nextPhase();
	}
	
	public String getPhase() {
		return model.getPhase();
	}
	
	public int getCurrentPlayer() {
		return model.currentPlayer()==model.player1()?1:2;
	}
	
	public boolean getSummoned() {
		return model.getSummoned();
	}
	
	public void setSummoned(boolean b) {
		model.setSummoned(b);
	}

}
