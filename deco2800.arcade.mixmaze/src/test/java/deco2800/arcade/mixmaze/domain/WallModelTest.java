package deco2800.arcade.mixmaze.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class WallModelTest {

	@Mock
	private PlayerModel mockedPlayer;

	@Before
	public void Initialize() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void buildWall() {
		WallModel wall = new WallModel(false);

		wall.build(mockedPlayer);
		assertTrue(wall.isBuilt());
		assertEquals(mockedPlayer, wall.getBuilder());
	}

	@Test
	public void destroyWall() {
		WallModel wall = new WallModel(false);

		wall.build(mockedPlayer);
		wall.destroy(mockedPlayer);
		assertFalse(wall.isBuilt());
		assertNull(wall.getBuilder());
	}

}
