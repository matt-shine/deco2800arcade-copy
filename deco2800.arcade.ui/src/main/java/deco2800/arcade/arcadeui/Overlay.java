package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Logger;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Game.InternalGame;
import deco2800.arcade.model.Player;

@InternalGame
@ArcadeGame(id="arcadeoverlay")
public class Overlay extends GameClient implements UIOverlay {
	
	private Logger logger = new Logger("Overlay");
	
	private boolean notifiedForMissingCallbacks = false;
	private boolean notifiedForMissingInput = false;
	
    private Skin skin;
    private Stage stage;
    Table table = new Table();
    
	private OverlayScreen screen = new OverlayScreen(this);
	private OverlayPopup popup = new OverlayPopup(this);
	private SpriteBatch batch = new SpriteBatch();
	
	private GameClient host = null;
	
	public Overlay(Player player, NetworkClient networkClient) {
		super(player, networkClient);

		this.setScreen(screen);

        stage = new Stage();
        
        
        ArcadeInputMux.getInstance().addProcessor(stage);
        
        table.setFillParent(true);
              
        stage.addActor(table);
        
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
		
		//Check nobody is being selfish, keeping all the precious inputs for themselves
		if (Gdx.input.getInputProcessor() != ArcadeInputMux.getInstance() && !notifiedForMissingInput) {
			notifiedForMissingInput = true;
			logger.error("Something has stolen the inputlistener. " +
					"See src/main/java/deco2800/arcade/client/ArcadeInputMux.java");
		}
		
		//Check that people love me
		if (screen.getListeners() == null && !notifiedForMissingCallbacks) {
	    	notifiedForMissingCallbacks = true;
	    	logger.error("Overlay event listeners are not set. " + 
	    			"See https://github.com/UQdeco2800/deco2800-2013/wiki/Overlay");
	    }

		//Check that I'm not being run on top of myself.
		//Why would anyone do that?
		if (this.getHost() instanceof UIOverlay) {
			logger.error("The overlay is being run on top of another overlay." + 
	    			" What??? Why???");
		}
		
	}
	
	@Override
	public void dispose() {
	    
	    stage.dispose();
        skin.dispose();
        
	    ArcadeInputMux.getInstance().removeProcessor(stage);

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

	public GameClient getHost() {
		return host;
	}
	
	
	public void setHost(GameClient host) {
		this.host = host;
	}
	
	
	
}
