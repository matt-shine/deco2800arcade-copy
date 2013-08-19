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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameScreen;

public class Overlay extends GameScreen {
	
	
    private Skin skin;
    private Stage stage;
    
    private OverlayPopup popup = new OverlayPopup();
	
	
	private boolean isUIOpen = false;
	private boolean hasTabPressedLast = false;

	
	
	public Overlay() {

		System.out.println(this.getWidth() + " " + this.getHeight());
		
        skin = new Skin();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = skin.getFont("default");
        skin.add("default", textFieldStyle);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        
        
        stage = new Stage();
        
        
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        
        
        Label quitLabel = new Label("Press escape to quit...", skin);
        table.add(quitLabel);
        
        
        stage.addActor(table);
        //stage.addActor(popup);
        
        
        //popup.setPosition(100, 100);
        
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
			stage.draw();
			
			
		    
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
