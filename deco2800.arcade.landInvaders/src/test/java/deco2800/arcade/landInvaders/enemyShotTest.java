package deco2800.arcade.landInvaders;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class enemyShotTest {

	@Before
	public void Initialize() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void enemyInitialize() {
		enemyShot es = new enemyShot(10, 10);
		assertTrue(es.getEShotState());
	}


}
