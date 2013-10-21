package deco2800.arcade.landInvaders;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class tankshotTest {

	@Before
	public void Initialize() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void enemyInitialize() {
		tankshot t = new tankshot(0, 0);
		assertTrue(t.getTankShotState());
	}

}
