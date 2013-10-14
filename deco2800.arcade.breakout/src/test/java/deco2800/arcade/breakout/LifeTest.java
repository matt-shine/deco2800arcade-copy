package deco2800.arcade.breakout;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import org.mockito.Mockito;

public class LifeTest {
	
	GameScreen mockGS = mock(GameScreen.class);

	@Test
	public void lifeIncrement(){
		Mockito.doCallRealMethod().when(mockGS).getLives();
		Mockito.doCallRealMethod().when(mockGS).getLevel();
		Mockito.doCallRealMethod().when(mockGS).setLives(3);
		Mockito.doCallRealMethod().when(mockGS).incrementLives(1);
		mockGS.setLives(3);
		// work out the actual: mockGS.getLives()
		assertEquals("Initialise Lives", 3, mockGS.getLives());
		mockGS.incrementLives(1);
		// work out the actual: mockGS.getLives()
		assertEquals("Lives == 4", 4, mockGS.getLives());
	}
	
	@Test
	public void lifeDecrement(){
		Mockito.doCallRealMethod().when(mockGS).setLives(3);
		mockGS.setLives(3);
		// work out the actual: mockGS.getLives()
		assertEquals("Lives == 3", 3, 3);
		Mockito.doCallRealMethod().when(mockGS).decrementLives(1);
		mockGS.decrementLives(1);
		// work out the actual: mockGS.getLives()
		assertEquals("Lives == 2", 2, 2);
	}
}