package deco2800.arcade.landInvaders;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class enemyGroupTest {

	@Before
	public void Initialize() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void enemyInitialize() {
		enemyGroup eg = new enemyGroup(10, 10, 30, 30, null);
		assertTrue(eg.getEnemyGroupState());
	}
	
	@Test
	public void constructEnemyGroup() {
		
		enemyGroup eg = new enemyGroup(10, 10, 30, 30, null);
		assertNotNull(eg.checkList());
	}

	@Test
	public void enemyShotCount() {
		
		enemyGroup eg = new enemyGroup(10, 10, 30, 30, null);
		assertNotNull(eg.enemyShot(250));
	}
	
	@Test
	public void checkShotRate() {
		
		enemyGroup eg = new enemyGroup(10, 10, 30, 30, null);
		assertNotNull(eg.enemyShot(250));
	}
	
	@Test
	public void checkEmptyGroup() {
		
		enemyGroup eg = new enemyGroup(0, 0, 30, 30, null);
		assertTrue(eg.isEmpty());
		enemyGroup eg3 = new enemyGroup(10, 10, 30, 30, null);
		assertFalse(eg3.isEmpty());
	}
}
