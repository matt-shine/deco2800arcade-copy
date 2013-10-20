package deco2800.arcade.cyra.game;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SplashScreen extends AbstractScreen {
	private Texture splashTex;
	private TextureRegion splashTexRegion;
	private Sprite splashSpr;
	private TweenManager manager;
	
	public SplashScreen( Cyra game) {
		super (game);
	}
	
	@Override
	public void show() {
		super.show();
		splashTex = new Texture(Gdx.files.internal("title.png"));
		splashTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		splashTexRegion = new TextureRegion (splashTex, 0, 0, 512, 512);
		
		//Version for use with Tween
		splashSpr = new Sprite(splashTexRegion);
		splashSpr.setOrigin(splashSpr.getWidth()/2, splashSpr.getHeight()/2);
		splashSpr.setPosition((Gdx.graphics.getWidth()-splashSpr.getWidth())/2, (Gdx.graphics.getHeight()-splashSpr.getHeight())/2);
		splashSpr.setColor(1,1,1,0);
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		manager = new TweenManager();
		TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				tweenCompleted();
			}

			
		};
		Tween.to(splashSpr, SpriteTween.ALPHA, 0.6f).target(1).ease(TweenEquations.easeInQuad).repeatYoyo(1, 1.6f).
				setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
		
	}
	
	private void tweenCompleted() {
		game.setScreen(new MainMenu(game));
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		manager.update(delta);
		batch.begin();
		splashSpr.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		splashTex.dispose();
	}
}
