package deco2800.arcade.cyra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Basic screen class for other screens to inherit from. 
 * 
 * @author Game Over
 *
 */
public abstract class AbstractScreen implements Screen {

	protected final Cyra game;
	protected final BitmapFont font;
	protected final SpriteBatch batch;
	
	public AbstractScreen(Cyra game) {
		this.game = game;
		this.font = new BitmapFont();
		this.batch = new SpriteBatch();
	}
	@Override
	public void render(float delta) {
		// the following code clears the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL10.GL_COLOR_BUFFER_BIT );
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {

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
		
	}

}
