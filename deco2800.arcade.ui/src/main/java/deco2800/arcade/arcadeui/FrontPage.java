package deco2800.arcade.arcadeui; 

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;

public class FrontPage {
	
	private class FrontPage extends Stage {}
	
	private Skin skin;
    private Stage stage;
    private menuBar sprite;
    
    public FrontPage() {
    	
    	skin = new Skin(Gdx.files.internal("loginSkin.json")); //<-- dafuq is this
        skin.add("background", new Texture("homescreen_bg.png")); 

        stage = new LoginScreenStage();
        
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("background"));
        stage.addActor(table);
        
        
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
        Table.drawDebug(stage);  // Shows table debug lines
	}
	
	@Override
	public void dispose() {
        stage.dispose();
        skin.dispose();
        ArcadeInputMux.getInstance().removeProcessor(stage);
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
	
	@Override
	public void resize(int arg0, int arg1) {
	}

}
