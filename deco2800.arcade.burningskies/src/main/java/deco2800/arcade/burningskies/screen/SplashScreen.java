package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.arcade.burningskies.BurningSkies;
 
public class SplashScreen implements Screen
{
    private Texture logo;
    private SpriteBatch spriteBatch;
    private BurningSkies game;
    private Music music;
    private OrthographicCamera camera;
 
    public SplashScreen( BurningSkies game )
    {
    	this.game = game;
    }
 
    @Override
    public void show()
    {
        logo = new Texture(Gdx.files.internal("images/splash.png"));
        spriteBatch = new SpriteBatch();
        //TODO: reenable this when we need it
        //music = Gdx.audio.newMusic(Gdx.files.internal("sound/intro.ogg"));
        //music.setLooping(true);
        //music.play();
    }
    @Override
    public void hide() {
    } 
    
    @Override
    public void render(float delta)
    {
    	if (Gdx.input.justTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) {
    		game.setScreen(game.menuScreen);
    	}
 
        spriteBatch.begin();
        spriteBatch.draw(logo, 0, 0, BurningSkies.SCREENWIDTH, BurningSkies.SCREENHEIGHT);
        spriteBatch.end();
    }
    
    @Override
    public void resize(int width, int height) {
    }
    
    @Override
    public void pause() {
    }
    
    @Override
    public void resume() {
    }
    
    @Override
    public void dispose() {	
    	//music.dispose();
    }
}
