package deco2800.arcade.landInvaders;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class TankTest {

	@Before
	public void Initialize() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void wallInitialize() {
		tank t = new tank(null);
		assertTrue(t.checkTankState());
	}
	
	
	@Test
	public void tankMoveTest() {
		
		tank t = new tank(null);
		t.tankMove();
		assertEquals(t.PositionX(), 370);
	}

}
