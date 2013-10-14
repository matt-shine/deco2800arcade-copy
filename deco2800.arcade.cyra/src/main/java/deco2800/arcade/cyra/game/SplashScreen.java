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
		splashTex = new Texture("data/title.png");
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
		Tween.to(splashSpr, SpriteTween.ALPHA, 1.5f).target(1).ease(TweenEquations.easeInQuad).repeatYoyo(1, 2.5f).
				setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
		
		//Version for use with Scene2d
		/*Image splashImage = new Image((Drawable) splashTexRegion, Scaling.stretch, Align.bottom | Align.left);
		splashImage.setColor(0, 0, 0, 0f);
		SequenceAction actions = SequenceAction.$(FadeIn.$(0.75f), Delay.$(FadeOut.$(0.75f), 1.75f));*/
		
		
	}
	
	private void tweenCompleted() {
		game.setScreen(new MainMenu(game));
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		manager.update(delta);
		batch.begin();
		//batch.draw(splashTexRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
