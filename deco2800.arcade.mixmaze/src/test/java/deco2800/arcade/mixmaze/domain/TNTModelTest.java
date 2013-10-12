package deco2800.arcade.mixmaze.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class TNTModelTest {
	@Test
	public void init() {
		TNTModel m = new TNTModel();

		assertEquals(ItemModel.Type.TNT, m.getType());
	}
}
