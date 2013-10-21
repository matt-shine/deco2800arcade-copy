//Shelved for now
/*package deco2800.arcade.breakout;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.junit.*;
import com.badlogic.gdx.Gdx;

public class LevelTest1 {

	Level mockLevel = mock(Level.class);
	Level level = new Level();
	GameScreen context = mock(GameScreen.class);

	String[] fileArray = {"level1.txt", "level2.txt", "level3.txt",
			"level4.txt", "level5.txt", "level6.txt", "level7.txt",
			"level8.txt", "level9.txt", "level10.txt" };
	Brick bricks[];
	
	@Before
	public void setUp() throws Exception {
		doCallRealMethod().when(context).getLevel();
		String fileName = "levels/level" + context.getLevel() + ".txt";
		doCallRealMethod().when(mockLevel).readFile(fileName + ".txt",bricks, context);
		
	}
	
	@Test
	public void brickCount() throws IOException {
		
		String fileName = "level" + context.getLevel() + ".txt";
		bricks = mockLevel.readFile(fileName,
				bricks, context);
		BufferedReader input = new BufferedReader(new FileReader(fileName));
		// Read the number of brick's from the file (first line).
		int numBricks = 0;
		try {
			numBricks = Integer.valueOf(input.readLine().trim());
		} catch (Exception e) {
			input.close();
			// file not correct format
		}
		assertEquals(numBricks, bricks.length);

	}
}
*/