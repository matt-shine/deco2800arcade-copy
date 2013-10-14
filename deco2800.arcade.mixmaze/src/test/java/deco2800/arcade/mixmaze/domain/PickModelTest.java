package deco2800.arcade.mixmaze.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class PickModelTest {
	@Test
	public void init() {
		PickModel m = new PickModel();

		assertEquals(ItemModel.Type.PICK, m.getType());
	}
}
