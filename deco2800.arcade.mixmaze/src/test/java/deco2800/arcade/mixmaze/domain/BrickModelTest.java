package deco2800.arcade.mixmaze.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import deco2800.arcade.mixmaze.domain.view.IItemModel.ItemType;

public class BrickModelTest {

	private BrickModel brick;

	@Before
	public void init() {
		brick = new BrickModel(5);
	}

	@Test(expected=IllegalArgumentException.class)
	public void brickAmountOutOfRange() {
		new BrickModel(-1);
	}

	@Test
	public void initializeBrickModel() {
		assertEquals(ItemType.BRICK, brick.getType());
		assertEquals(5, brick.getAmount());
	}

	@Test
	public void setAmount() {
		brick.setAmount(1);
		assertEquals(1, brick.getAmount());
	}

	@Test(expected=IllegalArgumentException.class)
	public void setInvalidAmount() {
		brick.setAmount(-1);
	}

	@Test
	public void addAmount() {
		brick.addAmount(1);
		assertEquals(6, brick.getAmount());
	}

	@Test(expected=IllegalArgumentException.class)
	public void addInvalidAmount() {
		brick.addAmount(BrickModel.getMaxBricks() + 1);
	}

	@Test
	public void removeAmount() {
		brick.removeAmount(1);
		assertEquals(4, brick.getAmount());
	}

	@Test(expected=IllegalArgumentException.class)
	public void removeInvalidAmount() {
		brick.removeAmount(BrickModel.getMaxBricks() + 1);
	}

	@Test
	public void removeOne() {
		brick.removeOne();
		assertEquals(4, brick.getAmount());
	}

	@Test(expected=IllegalArgumentException.class)
	public void removeTooMany() {
		BrickModel brick = new BrickModel(1);

		brick.removeOne();
		brick.removeOne();
	}

}
