package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.bullets.BossPattern;
import deco2800.arcade.burningskies.screen.PlayScreen;

//TODO abstract?
public class Boss extends Enemy {
	
	private BossPattern pattern;
	private static Texture tex = new Texture(Gdx.files.internal("images/ships/boss1.png"));
	private boolean intro = true;
	private float pauseTimer = 1f;
	
	public Boss(PlayScreen screen, PlayerShip player) {
		super(100000, tex, 
		new Vector2(640, 880), new Vector2(0,0), screen, player, 10000000000L);

		pattern = new BossPattern(this, player, screen);
	}
	
	@Override
	protected void move(float delta) {
		if(intro) {
			position.add(0, delta*-50);
			if(position.y <= 540) {
				position.y = 540;
				intro = false;
			}
		} else if(pauseTimer > 0) {
			pauseTimer -= delta;
		} else {
			if(!pattern.isFiring()) {
				pattern.start();
			}
			//TODO: MAKE HIM SHOOT AND MOVE AND SHIT
		}
		setPosition(position.x-getWidth()/2, position.y-getHeight()/2);
	}
	
	@Override
	protected void fire(float delta) {
		pattern.onRender(delta);
	}
}
