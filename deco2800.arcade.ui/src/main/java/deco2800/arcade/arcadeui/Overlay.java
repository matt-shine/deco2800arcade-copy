package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameScreen;

public class Overlay extends GameScreen {
	
	
    private Skin skin;
    private Stage stage;
    Table table = new Table();
    
    private OverlayPopup popup = new OverlayPopup();
	
	private boolean isUIOpen = false;
	private boolean hasTabPressedLast = false;

	
	
	public Overlay() {

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
        table.add(quitLabel).expand().top();
        table.row();
        table.add(popup).bottom();
        
        table.layout();
        
        stage.addActor(table);
        
	}
	
	

	@Override
	public void show() {
	}
	
	
	@Override
	public void firstResize() {
	}
	
	@Override
	public void render(float arg0) {
		
		
		//toggles isUIOpen on tab key down
		if (Gdx.input.isKeyPressed(Keys.TAB) != hasTabPressedLast && (hasTabPressedLast = !hasTabPressedLast)) {
			isUIOpen = !isUIOpen;
		}
		
		if (isUIOpen) {
			
			stage.act();
			table.debug();
			table.debugTable();
			stage.draw();
			Table.drawDebug(stage);
			
		    
		    if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
		    	ArcadeSystem.goToGame("arcadeui");
		    }
		    
		}
		
	}

	@Override
	public void dispose() {
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


}
