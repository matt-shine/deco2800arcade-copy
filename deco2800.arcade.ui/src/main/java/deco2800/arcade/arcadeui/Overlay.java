package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
<<<<<<< HEAD
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Logger;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameScreen;

public class Overlay extends GameScreen {
	
	private Logger logger = new Logger("Overlay");
	
	private Screen callbacks = null;
	private boolean notifiedForMissingCallbacks = false;
		
    private Skin skin;
    private Stage stage;
    Table table = new Table();
    
    private OverlayPopup popup = new OverlayPopup();
	
	private boolean isUIOpen = false;
	private boolean hasTabPressedLast = false;
	private SpriteBatch batch;
=======

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Game.InternalGame;
import deco2800.arcade.model.Player;
>>>>>>> master

@InternalGame
@ArcadeGame(id="arcadeoverlay")
public class Overlay extends GameClient implements UIOverlay {
	
	private OverlayScreen screen = new OverlayScreen(this);
	private OverlayPopup popup = new OverlayPopup(this);
	private SpriteBatch batch = new SpriteBatch();
	
	public Overlay(Player player, NetworkClient networkClient) {
		super(player, networkClient);

		this.setScreen(screen);

<<<<<<< HEAD
        stage = new Stage();
        
        
        ArcadeInputMux.getInstance().addProcessor(stage);
        
        table.setFillParent(true);
        
        Label quitLabel = new Label("Press escape to quit...", skin);
        table.row();
        table.add(quitLabel).expand().space(40).top();
        table.layout();
        
        stage.addActor(table);
        
=======
>>>>>>> master
	}
	
	@Override
	public void setListeners(Screen l) {
		screen.setListeners(l);
	}

	@Override
	public void addPopup(PopupMessage s) {
		popup.addMessageToQueue(s);
	}

	@Override
	public void render() {
		
		super.render();
		
		popup.act(Gdx.graphics.getDeltaTime());
		batch.begin();
		popup.draw(batch, 1f);
		batch.end();
		
		
<<<<<<< HEAD
		
		if (callbacks == null && !notifiedForMissingCallbacks) {
	    	notifiedForMissingCallbacks = true;
	    	logger.error("No overlay listener is set");
	    }
		
		if (Gdx.input.getInputProcessor() != ArcadeInputMux.getInstance()) {
			logger.error("Something has stolen the inputlistener");
		}
		
		
=======
>>>>>>> master
	}
	
	@Override
	public void dispose() {
<<<<<<< HEAD
	    if (callbacks != null) {
	    	callbacks.dispose();
	    }
	    
	    stage.dispose();
        skin.dispose();
        
	    ArcadeInputMux.getInstance().removeProcessor(stage);
=======
		// TODO Auto-generated method stub
		
>>>>>>> master
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void create() {
		this.setScreen(screen);
	}


}
