package deco2800.arcade.mixmaze.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import deco2800.arcade.mixmaze.domain.MixMazeModel.MixMazeDifficulty;

public class MixMazeModelTest {
	@Test(expected=IllegalArgumentException.class)
	public void mixMazeSizeOutOfRange() {
		new MixMazeModel(-1, MixMazeDifficulty.Beginner, 60);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void mixMazeTimeOutOfRange() {
		new MixMazeModel(5, MixMazeDifficulty.Beginner, -1);
	}
	
	@Test
	public void mixMazeInitialization() {
		MixMazeModel mixMaze = new MixMazeModel(5, MixMazeDifficulty.Beginner, 60);
		assertEquals(5, mixMaze.getBoardSize());
		for(int row = 0; row < mixMaze.getBoardSize(); ++row) {
			for(int column = 0; column < mixMaze.getBoardSize(); ++column) {
				TileModel tile = mixMaze.getBoardTile(column, row);
				assertNotNull(tile);
				assertEquals(column, tile.getX());
				assertEquals(row, tile.getY());
			}
		}
		assertEquals(MixMazeDifficulty.Beginner, mixMaze.getGameDifficulty());
		assertEquals(60, mixMaze.getGameMaxTime());
		assertEquals(-1, mixMaze.getGameStartTime());
		assertEquals(-1, mixMaze.getGameEndTime());
		assertNotNull(mixMaze.getPlayer1());
		assertNotNull(mixMaze.getPlayer2());
	}
}
