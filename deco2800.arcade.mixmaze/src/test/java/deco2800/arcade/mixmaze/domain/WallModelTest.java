package deco2800.arcade.mixmaze.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WallModelTest {
	private WallModel wall;

	@Mock
	private PlayerModel mockedPlayer;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void initialize() {
		MockitoAnnotations.initMocks(this);
		wall = new WallModel();
	}

	@Test
	public void buildWall() {
		wall.build(mockedPlayer);
		assertTrue(wall.isBuilt());
		assertEquals(mockedPlayer, wall.getBuilder());
	}

	@Test
	public void destroyWall() {
		wall.build(mockedPlayer);
		wall.destroy(mockedPlayer);
		assertFalse(wall.isBuilt());
		assertNull(wall.getBuilder());
	}

	@Test
	public void updateObserver() {
		WallModelObserver mockedObserver = mock(WallModelObserver.class);

		wall.addObserver(mockedObserver);
		wall.build(mockedPlayer);
		verify(mockedObserver).updateWall(wall.isBuilt());
		wall.destroy(mockedPlayer);
		verify(mockedObserver).updateWall(wall.isBuilt());
	}

	@Test
	public void getTiles() {
		TileModel mockedLeft = mock(TileModel.class);
		TileModel mockedRight = mock(TileModel.class);

		wall.setLeftTile(mockedLeft);
		assertEquals(mockedLeft, wall.getLeftTile());
		wall.setRightTile(mockedRight);
		assertEquals(mockedRight, wall.getRightTile());
	}

	@Test
	public void buildAgain() throws IllegalStateException {
		wall.build(mockedPlayer);

		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("already built");
		wall.build(mockedPlayer);
	}

	@Test
	public void buildWithNull() throws IllegalArgumentException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("player cannot be null");
		wall.build(null);
	}

	@Test
	public void destroyUnbuiltWall() throws IllegalStateException {
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("wall not built");
		wall.destroy(mockedPlayer);
	}

	@Test
	public void updateTiles() {
		TileModel mockedLeft = mock(TileModel.class);
		TileModel mockedRight = mock(TileModel.class);

		wall.setLeftTile(mockedLeft);
		wall.setRightTile(mockedRight);
		wall.build(mockedPlayer);
		verify(mockedLeft).validateBox(mockedPlayer);
		verify(mockedRight).validateBox(mockedPlayer);
	}

	@Test
	public void testToString() {
		assertEquals("<WallModel: not built>", wall.toString());
		wall.build(mockedPlayer);
		assertEquals("<WallModel: built>", wall.toString());
	}
}
