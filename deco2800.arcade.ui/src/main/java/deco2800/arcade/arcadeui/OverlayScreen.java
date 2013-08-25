package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import deco2800.arcade.client.ArcadeSystem;

public class OverlayScreen implements Screen {
	
	private Screen callbacks = null;
	private boolean notifiedForMissingCallbacks = false;
		
    private Skin skin;
    private Stage stage;
    Table table = new Table();
    
    private OverlayPopup popup = new OverlayPopup();
	
	private boolean isUIOpen = false;
	private boolean hasTabPressedLast = false;
	private SpriteBatch batch;

	
	
	public OverlayScreen() {

		batch = new SpriteBatch();
		
        skin = new Skin();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);

        stage = new Stage();
        
        
        Gdx.input.setInputProcessor(stage);
        
        table.setFillParent(true);
        
        Label quitLabel = new Label("Press escape to quit...", skin);
        table.row();
        table.add(quitLabel).expand().space(40).top();
        table.layout();
        
        stage.addActor(table);
        
	}
	
	

	@Override
	public void show() {
	}
	
	
	@Override
	public void render(float d) {
		//toggles isUIOpen on tab key down
		if (Gdx.input.isKeyPressed(Keys.TAB) != hasTabPressedLast && (hasTabPressedLast = !hasTabPressedLast)) {
			isUIOpen = !isUIOpen;
			
			if (callbacks != null) {
				if (isUIOpen) {
					callbacks.show();
				} else {
					callbacks.hide();
				}
			}
			
		}
		
		if (isUIOpen) {
			
			stage.act();
			table.debug();
			table.debugTable();
			stage.draw();
			Table.drawDebug(stage);
			
			
			
			popup.act(d);
			popup.draw(batch, 1);
		    
		    if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
		    	ArcadeSystem.goToGame(ArcadeSystem.UI);
		    }
		    
		    if (callbacks != null) {
		    	callbacks.render(d);
		    } 
		    
		}
		
		
		if (callbacks == null && !notifiedForMissingCallbacks) {
	    	notifiedForMissingCallbacks = true;
	    	System.err.println("Overlay event listeners are not set. See https://github.com/UQdeco2800/deco2800-2013/wiki/Overlay");
	    }
	}

	@Override
	public void dispose() {
	    if (callbacks != null) {
	    	callbacks.dispose();
	    }
	}
	
	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	    if (callbacks != null) {
	    	callbacks.pause();
	    }
	}

	@Override
	public void resume() {
	    if (callbacks != null) {
	    	callbacks.resume();
	    }
	}
	

	public void setListeners(Screen l) {
		this.callbacks = l;
	}



	@Override
	public void resize(int arg0, int arg1) {
	    if (callbacks != null) {
	    	callbacks.resize(arg0, arg1);
	    }
	}
	


}
