package deco2800.arcade.breakout;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import org.mockito.Mockito;

import deco2800.arcade.breakout.screens.GameScreen;

public class LifeTest {
	
	GameScreen mockGS = mock(GameScreen.class);

	@Test
	public void lifeIncrement(){
		Mockito.doCallRealMethod().when(mockGS).getLives();
		Mockito.doCallRealMethod().when(mockGS).getLevel();
		Mockito.doCallRealMethod().when(mockGS).setLives(3);
		Mockito.doCallRealMethod().when(mockGS).incrementLives(1);
		mockGS.setLives(3);
		assertEquals("Initialise Lives", 3, mockGS.getLives());
		mockGS.incrementLives(1);
		assertEquals("Lives == 4", 4, mockGS.getLives());
	}
	
	@Test
	public void lifeDecrement(){
		Mockito.doCallRealMethod().when(mockGS).getLives();
		Mockito.doCallRealMethod().when(mockGS).getLevel();
		Mockito.doCallRealMethod().when(mockGS).setLives(3);
		Mockito.doCallRealMethod().when(mockGS).decrementLives(1);
		mockGS.setLives(3);
		assertEquals("Lives == 3", 3, mockGS.getLives());
		Mockito.doCallRealMethod().when(mockGS).decrementLives(1);
		mockGS.decrementLives(1);
		assertEquals("Lives == 2", 2, mockGS.getLives());
	}
}