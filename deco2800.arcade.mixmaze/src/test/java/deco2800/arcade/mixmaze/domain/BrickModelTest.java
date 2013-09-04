package deco2800.arcade.mixmaze.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import deco2800.arcade.mixmaze.domain.ItemModel.ItemType;

public class BrickModelTest {
	@Mock
	private TileModel mockedTile;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void brickNullTile() {
		new BrickModel(null, 5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void brickAmountOutOfRange() {
		new BrickModel(mockedTile, -1);
	}
	
	@Test
	public void initializeBrickModel() {
		BrickModel brick = new BrickModel(mockedTile, 5);
		assertEquals(ItemType.BRICK, brick.getType());
		assertEquals(5, brick.getAmount());
	}
	
	@Test
	public void setAmount() {
		BrickModel brick = new BrickModel(mockedTile, 5);
		brick.setAmount(1);
		assertEquals(1, brick.getAmount());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setAmountInvalid() {
		BrickModel brick = new BrickModel(mockedTile, 5);
		brick.setAmount(-1);
	}
	
	@Test
	public void addAmount() {
		BrickModel brick = new BrickModel(mockedTile, 5);
		brick.addAmount(1);
		assertEquals(6, brick.getAmount());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addAmountInvalid() {
		BrickModel brick = new BrickModel(mockedTile, 5);
		brick.addAmount(BrickModel.MAX_BRICKS + 1);
	}
	
	@Test
	public void removeAmount() {
		BrickModel brick = new BrickModel(mockedTile, 5);
		brick.removeAmount(1);
		assertEquals(4, brick.getAmount());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void removeAmountInvalid() {
		BrickModel brick = new BrickModel(mockedTile, 5);
		brick.removeAmount(BrickModel.MAX_BRICKS - 1);
	}
	
	@Test
	public void removeOne() {
		BrickModel brick = new BrickModel(mockedTile, 5);
		brick.removeOne();
		assertEquals(4, brick.getAmount());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void removeOneInvalid() {
		BrickModel brick = new BrickModel(mockedTile, 1);
		brick.removeOne();
		brick.removeOne();
	}
}
