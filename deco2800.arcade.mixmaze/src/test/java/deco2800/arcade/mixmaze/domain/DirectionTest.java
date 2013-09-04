package deco2800.arcade.mixmaze.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class DirectionTest {
	@Test
	public void west() {
		assertTrue(Direction.isWest(Direction.WEST));
	}
	
	@Test
	public void north() {
		assertTrue(Direction.isNorth(Direction.NORTH));
	}
	
	@Test
	public void east() {
		assertTrue(Direction.isEast(Direction.EAST));
	}
	
	@Test
	public void south() {
		assertTrue(Direction.isSouth(Direction.SOUTH));
	}
	
	@Test
	public void isDirection() {
		assertFalse(Direction.isDirection(-1));
		assertTrue(Direction.isDirection(Direction.WEST));
		assertTrue(Direction.isDirection(Direction.NORTH));
		assertTrue(Direction.isDirection(Direction.EAST));
		assertTrue(Direction.isDirection(Direction.SOUTH));
	}
	
	@Test
	public void getPolarDirection() {
		assertEquals(Direction.WEST, Direction.getPolarDirection(Direction.EAST));
		assertEquals(Direction.NORTH, Direction.getPolarDirection(Direction.SOUTH));
		assertEquals(Direction.EAST, Direction.getPolarDirection(Direction.WEST));
		assertEquals(Direction.SOUTH, Direction.getPolarDirection(Direction.NORTH));
	}
	
	@Test
	public void isXDirection() {
		assertTrue(Direction.isXDirection(Direction.WEST));
		assertTrue(Direction.isXDirection(Direction.EAST));
		assertFalse(Direction.isXDirection(Direction.NORTH));
		assertFalse(Direction.isXDirection(Direction.SOUTH));
	}
	
	@Test
	public void isYDirection() {
		assertTrue(Direction.isYDirection(Direction.NORTH));
		assertTrue(Direction.isYDirection(Direction.SOUTH));
		assertFalse(Direction.isYDirection(Direction.WEST));
		assertFalse(Direction.isYDirection(Direction.EAST));
	}
	
	@Test
	public void isPositiveDirection() {
		assertTrue(Direction.isPositiveDirection(Direction.EAST));
		assertTrue(Direction.isPositiveDirection(Direction.SOUTH));
		assertFalse(Direction.isPositiveDirection(Direction.WEST));
		assertFalse(Direction.isPositiveDirection(Direction.NORTH));
	}
	
	@Test
	public void isNegativeDirection() {
		assertFalse(Direction.isNegativeDirection(Direction.EAST));
		assertFalse(Direction.isNegativeDirection(Direction.SOUTH));
		assertTrue(Direction.isNegativeDirection(Direction.WEST));
		assertTrue(Direction.isNegativeDirection(Direction.NORTH));
	}
}
