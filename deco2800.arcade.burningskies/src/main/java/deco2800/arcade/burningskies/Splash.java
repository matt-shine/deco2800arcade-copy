package deco2800.arcade.burningskies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 
public class Splash implements Screen
{
    private Texture logo;
    private SpriteBatch spriteBatch;
    private BurningSkies game;
    private Music music;
 
    public Splash( BurningSkies game )
    {
    	this.game = game;
    }
 
    @Override
    public void show()
    {
        logo = new Texture(Gdx.files.internal("images/splash.png"));
        spriteBatch = new SpriteBatch();
        music = Gdx.audio.newMusic(Gdx.files.internal("music/intro.ogg"));
        music.setLooping(true);
        music.play();
    }
 
    @Override
    public void render(float delta)
    {
        handleInput();
 
        spriteBatch.begin();
        spriteBatch.draw(logo, 0, 0, BurningSkies.SCREENWIDTH, BurningSkies.SCREENHEIGHT);
        spriteBatch.end();
    }
 
    private void handleInput()
    {
        /* TODO: TIMER? TOUCH? */
    }
 
    @Override
    public void resize(int width, int height) {
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
    public void dispose() {	
    	music.dispose();
    }
}
