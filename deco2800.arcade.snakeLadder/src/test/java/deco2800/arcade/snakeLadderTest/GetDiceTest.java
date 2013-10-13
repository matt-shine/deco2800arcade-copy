package deco2800.arcade.snakeLadderTest;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderModel.Dice;

public class GetDiceTest {
	SnakeLadder snakeLadder;
	
	@Before
	public void setup(){
		snakeLadder = new SnakeLadder(null, null);
	}
	
	@Test
	public void getCorrectNumber(){
		Dice dice = snakeLadder.getDice(6);
		assertEquals("Didn't Return Correct Dice Number", 6, dice.getDiceNumber());
	}
}
