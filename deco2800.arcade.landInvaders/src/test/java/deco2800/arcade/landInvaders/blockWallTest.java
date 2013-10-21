package deco2800.arcade.landInvaders;

import static org.junit.Assert.*;

import org.mockito.*;
import org.junit.*;



public class blockWallTest {


	@Before
	public void Initialize() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void wallInitialize() {
		blockWall bw = new blockWall(0, 0, 50, 50, null);
		assertTrue(bw.checkWallCondition());
	}
	
	
	@Test
	public void constructWall() {
		
		blockWall bw = new blockWall(0, 0, 50, 50, null);
		assertNotNull(bw.checkList());
	}

	@Test
	public void playerHitWall() {
		blockWall bw = new blockWall(20, 20, 50, 50, null);
		tankshot shot = new tankshot(20, 20);
		assertFalse(bw.checkHit(shot));
	}
	
	@Test
	public void enemyHitWall() {
		blockWall bw = new blockWall(0, 0, 50, 50, null);
		enemyShot shot = new enemyShot(20, 20);
		assertTrue(bw.checkEnemyHit(shot));
	}
}
