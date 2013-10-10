package deco2800.arcade.client.highscores.test;

import org.junit.*;
import deco2800.arcade.client.highscores.*;

public class HighscoreClientTest {
	
	/*It's actually really hard to test this class, as HighscoreClient must be 
	 * instantiated with a non-null NetworkClient object. And, this object 
	 * can't be created without the server running. So, I can really only test 
	 * that the initiliser throws the correct exception for null valued 
	 * paramaters.*/
	
	@Test (expected=NullPointerException.class)
	public void initTest1() {
		HighscoreClient hsc = new HighscoreClient(0, null, null);
	}
	
	@Test (expected=NullPointerException.class)
	public void initTest2() {
		HighscoreClient hsc = new HighscoreClient(0, "TestGame", null);
	}
}
