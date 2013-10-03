package deco2800.arcade.mixmaze.domain;

import org.junit.Test;

import static deco2800.arcade.mixmaze.domain.MixMazeModel.Difficulty.*;
import static org.junit.Assert.*;

public class MixMazeModelTest {
	@Test(expected=IllegalArgumentException.class)
	public void mixMazeSizeOutOfRange() {
		new MixMazeModel(-1, BEGINNER, 60);
	}

	@Test(expected=IllegalArgumentException.class)
	public void mixMazeTimeOutOfRange() {
		new MixMazeModel(5, BEGINNER, -1);
	}

	@Test
	public void mixMazeInitialization() {
		MixMazeModel mixMaze = new MixMazeModel(5, BEGINNER, 60);
		assertEquals(5, mixMaze.getBoardSize());
		for(int row = 0; row < mixMaze.getBoardSize(); ++row) {
			for(int column = 0; column < mixMaze.getBoardSize(); ++column) {
				TileModel tile = mixMaze.getBoardTile(column, row);
				assertNotNull(tile);
				assertEquals(column, tile.getX());
				assertEquals(row, tile.getY());
			}
		}
		assertEquals(BEGINNER, mixMaze.getGameDifficulty());
		assertEquals(60, mixMaze.getGameMaxTime());
		assertEquals(null, mixMaze.getGameStartTime());
		assertEquals(null, mixMaze.getGameEndTime());
		assertNotNull(mixMaze.getPlayer(1));
		assertNotNull(mixMaze.getPlayer(2));
	}
}
