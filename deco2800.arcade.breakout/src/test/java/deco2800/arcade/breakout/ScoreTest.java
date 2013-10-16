package deco2800.arcade.breakout;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import org.mockito.Mockito;

public class ScoreTest {

	GameScreen context = mock(GameScreen.class);
	
	@Before
	public void setUp() throws Exception {
		Mockito.doCallRealMethod().when(context).getScore();
		Mockito.doCallRealMethod().when(context).getLevel();
		context.resetScore();
		context.setLives(3);
		context.setLevel(1);
	}
		
	@Test
	public void scoreInitialise(){
		assertEquals("Initialise Score", 0, context.getScore());
	}
	
	@Test
	public void scoreIncrement(){
		Mockito.doCallRealMethod().when(context).incrementScore(1);
		context.incrementScore(1);
		assertEquals("Increase Score", 1, context.getScore());
	}
	
	@Test
	public void scoreDecrement(){
		Mockito.doCallRealMethod().when(context).incrementScore(10);
		Mockito.doCallRealMethod().when(context).decrementScore(5);
		context.incrementScore(10);
		context.decrementScore(5);
		assertEquals("Decrease Score", 5, context.getScore());
	}
	
	@Test
	public void roundOver(){
		Mockito.doCallRealMethod().when(context).incrementScore(10);
		Mockito.doCallRealMethod().when(context).decrementScore(5);
		context.incrementScore(10);
		context.decrementScore(5);
		context.roundOver();
		verify(context).roundOver();
		assertEquals("Round Over Score", 5, context.getScore());
	}
	
	/*
	@Test
	public void scoreLevel1(){
		for(int i = 0; i<10; i++){
			Mockito.doCallRealMethod().when(context).incrementScore(context.getLevel()*2);
			context.incrementScore(context.getLevel()*2);
		}
		assertEquals("Score == 20", 20, context.getScore());
	}
	

	@Test
	public void scoreLevel2(){
		context.setLevel(2);
		for(int i = 0; i<10; i++){
			context.incrementScore(context.getLevel()*2);
		}
		assertEquals("Score == 60", context.getScore(), 60);
	}
	
	@Test
	public void scoreLevel1and2(){
		for(int i = 0; i<10; i++){
			context.incrementScore(context.getLevel()*2);
		}
		assertEquals("Score == 20", context.getScore(), 20);
		context.setLevel(2);
		for(int i = 0; i<10; i++){
			context.incrementScore(context.getLevel()*2);
		}
		assertEquals("Score == 60", context.getScore(), 60);
	}
	
	@Test
	public void alwaysSuccessful() {
		assertEquals("1 == 1", 1, 1);
	}*/
		
}
