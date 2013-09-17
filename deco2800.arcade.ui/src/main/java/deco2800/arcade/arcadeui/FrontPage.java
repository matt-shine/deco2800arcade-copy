
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
	
	private class LoginScreenStage extends Stage {}
	
	private Skin skin;
    private LoginScreenStage stage;
    
    public FrontPage() {
    	
    	skin = new Skin(Gdx.files.internal("loginSkin.json")); //<-- dafuq is this
        skin.add("background", new Texture("background.png"));

        stage = new LoginScreenStage();
        
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("background"));
        stage.addActor(table);
    }

}
