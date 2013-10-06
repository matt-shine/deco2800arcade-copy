package deco2800.arcade.breakout;

import static org.junit.Assert.*;

import org.junit.*;

import static org.mockito.Mockito.*;


public class LifeTest {
	
	Breakout mockBO = mock(Breakout.class);
	GameScreen mockGS = mock(GameScreen.class);
		
	//private GameScreen context;
	//private Breakout breakout;
	
	@Test
	public void lifeIncrement(){
		Breakout mockBO = mock(Breakout.class);
		GameScreen mockGS = mock(GameScreen.class);
		mockGS.setLives(3);
		assertEquals("Initialise Lives", mockGS.getLives(), 3);
		mockGS.incrementLives(1);
		assertEquals("Lives == 4", mockGS.getLives(), 4);
	}
	
	
	@Test
	public void lifeDecrement(){
		mockGS.setLives(3);
		assertEquals("Lives == 3", mockGS.getLives(), 3);
		mockGS.decrementLives(1);
		assertEquals("Lives == 2", mockGS.getLives(), 2);
	}
	
	@Test
	public void alwaysSuccessful() {
		assertEquals("1 == 1", 1, 1);
	}
	
}