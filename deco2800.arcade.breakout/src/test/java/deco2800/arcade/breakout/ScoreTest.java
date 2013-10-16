//package deco2800.arcade.breakout;
//
//import static org.junit.Assert.*;
//
//import org.junit.*;
//
//public class ScoreTest {
//	
//	private GameScreen context;
//	private Breakout breakout;
//
//	@Before
//	public void setUp() throws Exception {
//		breakout = new Breakout();
//		GameScreen context = new GameScreen();
//		context.setScore();
//		context.setLives(3);
//		context.setLevel(1);
//	}
//		
//		
//	@Test
//	public void scoreInitialise(){
//		assertEquals("Initialise Lives", context.getScore(), 0);
//	}
//	
//	
//	@Test
//	public void scoreIncrement(){
//		context.incrementScore(1);
//		assertEquals("Score == 1", context.getScore(), 1);
//		context.incrementLives(1);
//		assertEquals("Score == 2", context.getScore(), 2);
//	}
//	
//	@Test
//	public void scoreDecrement(){
//		context.incrementScore(10);
//		context.decrementScore(5);
//		assertEquals("Score == 5", context.getScore(), 5);
//		context.decrementLives(10);
//		assertEquals("Score == -5", context.getScore(), -5);
//	}
//	
//	@Test
//	public void scoreAfterRound(){
//		context.roundOver();
//		assertEquals("Score == -5", context.getScore(), -5);
//	}
//	
//	@Test
//	public void scoreLevel1(){
//		for(int i = 0; i<10; i++){
//			context.incrementScore(context.getLevel()*2);
//		}
//		assertEquals("Score == 20", context.getScore(), 20);
//	}
//	
//	@Test
//	public void scoreLevel2(){
//		context.setLevel(2);
//		for(int i = 0; i<10; i++){
//			context.incrementScore(context.getLevel()*2);
//		}
//		assertEquals("Score == 60", context.getScore(), 60);
//	}
//	
//	@Test
//	public void scoreLevel1and2(){
//		for(int i = 0; i<10; i++){
//			context.incrementScore(context.getLevel()*2);
//		}
//		assertEquals("Score == 20", context.getScore(), 20);
//		context.setLevel(2);
//		for(int i = 0; i<10; i++){
//			context.incrementScore(context.getLevel()*2);
//		}
//		assertEquals("Score == 60", context.getScore(), 60);
//	}
//	
//	@Test
//	public void alwaysSuccessful() {
//		assertEquals("1 == 1", 1, 1);
//	}
//		
//}
