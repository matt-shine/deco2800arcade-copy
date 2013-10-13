package deco2800.arcade.mixmaze.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class BrickModelTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private BrickModel brick;

	@Before
	public void init() {
		brick = new BrickModel(5);
	}

	@Test(expected=IllegalArgumentException.class)
	public void initWithNegativeAmount() {
		new BrickModel(-1);
	}

	@Test
	public void initWithTooMany() throws IllegalArgumentException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("no more than max bricks");
		new BrickModel(BrickModel.getMaxBricks() + 1);
	}

	@Test
	public void setInvalidMaxBricks() throws IllegalArgumentException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("max must be greater than or equal to 1");
		BrickModel.setMaxBricks(0);
	}

	@Test
	public void changeMaxBricks() {
		int old = BrickModel.getMaxBricks();

		BrickModel.setMaxBricks(50);
		assertEquals(50, BrickModel.getMaxBricks());
		BrickModel.setMaxBricks(old);
	}

	@Test
	public void initializeBrickModel() {
		assertEquals(ItemModel.Type.BRICK, brick.getType());
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
	public void setInvalidAmount2() throws IllegalArgumentException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("no more than max bricks");
		brick.setAmount(BrickModel.getMaxBricks() + 1);
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
	public void addInvalidAmount2() throws IllegalArgumentException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("no less than 0");
		brick.addAmount(-(brick.getAmount() + 1));
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
	public void removeInvalidAmount2() throws IllegalArgumentException {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("no less than 0");
		brick.removeAmount(brick.getAmount() + 1);
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

	@Test
	public void mergeBricks() {
		BrickModel other = new BrickModel(2);

		brick.mergeBricks(other);
		assertEquals(7, brick.getAmount());
		assertEquals(0, other.getAmount());
	}

	@Test
	public void mergeBricks2() {
		BrickModel other = new BrickModel(7);
		int old = brick.getAmount();

		brick.mergeBricks(other);
		assertEquals(BrickModel.getMaxBricks(), brick.getAmount());
		assertEquals(7 + old - BrickModel.getMaxBricks(),
				other.getAmount());
	}
}
