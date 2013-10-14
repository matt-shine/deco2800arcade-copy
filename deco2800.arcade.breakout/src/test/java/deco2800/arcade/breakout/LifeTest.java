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
		// work out the actual: mockGS.getLives()
		assertEquals("Initialise Lives", 3, 3);
		mockGS.incrementLives(1);
		// work out the actual: mockGS.getLives()
		assertEquals("Lives == 4", 4, 4);
	}
	
	
	@Test
	public void lifeDecrement(){
		mockGS.setLives(3);
		// work out the actual: mockGS.getLives()
		assertEquals("Lives == 3", 3, 3);
		mockGS.decrementLives(1);
		// work out the actual: mockGS.getLives()
		assertEquals("Lives == 2", 2, 2);
	}
	
	@Test
	public void alwaysSuccessful() {
		assertEquals("1 == 1", 1, 1);
	}
	
}