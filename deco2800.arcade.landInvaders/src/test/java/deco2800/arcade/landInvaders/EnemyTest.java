package deco2800.arcade.landInvaders;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class EnemyTest {

	@Before
	public void Initialize() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void enemyInitialize() {
		enemy e = new enemy(0, 0, 20, 20, null);
		assertTrue(e.checkEnemyState());
	}
	
	/**
	 * Test if enemy y-coordinate updates successfully
	 */
	@Test
	public void moveUpdateYTest() {
		enemy e = new enemy(20, 20, 20, 20, null);
		e.moveUpdate(10, true);
		assertEquals(e.positionY(), 30);
	}
	
	/**
	 * Test if enemy x-coordinate updates successfully
	 */
	@Test
	public void moveUpdateXTest() {
		enemy e = new enemy(20, 20, 20, 20, null);
		e.moveUpdate(15, false);
		assertEquals(e.positionX(), 35);
	}

}
