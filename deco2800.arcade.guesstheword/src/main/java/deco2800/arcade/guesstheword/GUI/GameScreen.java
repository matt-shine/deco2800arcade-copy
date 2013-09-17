package deco2800.arcade.guesstheword.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameScreen implements Screen {
	
	@SuppressWarnings("unused")
	private final GuessTheWord game;
//	private final Skin skin;
	private final SpriteBatch batch;

	
	private Texture level1;
	
	GameScreen(final GuessTheWord game){
		this.game = game;
//		this.skin = game.skin;
		batch = new SpriteBatch();
		
		level1 = new Texture("level1.png");
		
//		sprite = new Sprite(texture, 20, 20, 50, 50);
//		sprite.setPosition(10, 10);
//		sprite.setRotation(45);
	}
	
	@Override
	public void render(float arg0) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0f, 1f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(level1, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		batch.end();
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		level1.dispose();
		batch.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

}
