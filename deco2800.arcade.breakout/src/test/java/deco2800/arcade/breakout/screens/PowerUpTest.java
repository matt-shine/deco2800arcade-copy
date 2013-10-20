/*package deco2800.arcade.breakout.screens;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.junit.*;
import org.lwjgl.util.vector.Vector;
import org.mockito.Mockito;
import org.mockito.internal.configuration.ClassPathLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.breakout.Paddle;
import deco2800.arcade.breakout.powerup.DecreasePaddle;
import deco2800.arcade.breakout.powerup.PowerupManager;

public class PowerUpTest {
	
	private static final int SCREENWIDTH = 1280;
	GameScreen context = mock(GameScreen.class);
	PowerupManager pum = mock(PowerupManager.class);
	DecreasePaddle dp = new DecreasePaddle(context);
	//Classpath cp = mock(Gdx.files.classpath(deco2800.arcade.breakout.));
	Vector2 v2 = mock(com.badlogic.gdx.math.Vector2.class);
	Paddle testPaddle = mock(Paddle.class);
	Texture txt = mock(com.badlogic.gdx.graphics.Texture.class);
	Sprite spt = mock(com.badlogic.gdx.graphics.g2d.Sprite.class);
	private final String img = "decreasepaddle.png";

	

	@Before
	public void setUp() throws Exception {
		Mockito.doCallRealMethod().when(context).getPaddle();
		Mockito.doCallRealMethod().when(context).setPaddle(testPaddle);
		Mockito.doCallRealMethod().when(context).getPaddle().getPaddleShapeWidth();
		Mockito.doCallRealMethod().when(context).getPaddle().getStandardWidth();
		Mockito.doCallRealMethod().when(dp).applyPowerup();
		spt = new Sprite((new Texture(Gdx.files.classpath("imgs/" + img))));
		testPaddle = new Paddle(v2);
	}

	@Test
	public void test() {
		dp = new DecreasePaddle(context);
		dp.applyPowerup();
		float padWidth = context.getPaddle().getPaddleShapeWidth();
		float stdWidth = context.getPaddle().getStandardWidth();
		stdWidth = stdWidth/2;
		assertEquals(stdWidth, padWidth, 0.0001);
		
		
	}

}*/
