package deco2800.arcade.pacman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/*
 * Probably just replace this with a method in Pacman.java later, but for now
 * this is just a thinking space :).
 */

public class GameMap {

	// created these to try to specify a space in which the gamemap should go
	// doesn't do anything yet, need to think more about how that might work
	private int width;
	private int height;
	
	public GameMap(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/*
	 * The idea is to read in a text file representing the game 'grid' and
	 * append lines to an ArrayList. Maybe arrays of chars/strings will work
	 * more nicely but for now it's just convenient because ArrayLists are
	 * dynamically sized so there's no need to pre-compute the size of the map
	 * beforehand.
	 */

	public ArrayList<char[]> readMap(String file) throws IOException {

		// Pre-process 'file' to get it to point to the correct directory
		file = "src\\main\\resources\\" + file;		
		String absolutePath = Gdx.files.internal(file).file().getAbsolutePath();
		absolutePath = absolutePath.replace("arcade", "arcade.pacman");

		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(absolutePath);
			br = new BufferedReader(fr);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		ArrayList<char[]> resultArray = new ArrayList<char[]>();
		try {
			String str;
			while ((str = br.readLine()) != null) {
				resultArray.add(str.toCharArray());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return resultArray;

	}

	/*
	 * Alternate implementation of readMap. Given that map files
	 * have relatively small dimensions, it shouldn't be an issue to simply
	 * read the entire text file into memory all at once. So for this purpose,
	 * we can just use libgdx's functions to read our file.
	 */
	public ArrayList<char[]> readMap2(String file) {
//		FileHandle filePath = Gdx.files.internal(file);
		
		file = "src\\main\\resources\\" + file;
		FileHandle filePath = Gdx.files.internal(file);
		String mapstr = filePath.readString();
		
		ArrayList<char[]> mapp = new ArrayList<char[]>();	
		String[] stringarray = mapstr.split(System.getProperty("line.separator"));
		for (int i = 0; i < stringarray.length; i++) {
			mapp.add(stringarray[i].toCharArray());
		}
		return mapp;
	}

	/*
	 * OLD METHOD: Generate and draw walls as specified by the map ArrayList.
	 */
	public void drawMap2(List<Collideable> colList, ArrayList<char[]> map, ShapeRenderer shaper) {
		// using a unit length for the walls as 25 pixels. This means by the
		// standard grid we are working at
		// at the moment, the txt files must be 24x24.
		for (int i = 0; i < map.size(); i++) {
			char[] s = map.get(i);
			for (int j = 0; j < s.length; j++) {
				Wall wall = new Wall(colList, Character.getNumericValue(s[j]),
						(900 - 8 * i), (650 - 8 * j), 8);
				wall.render(shaper);
			}
		}
	}
	
	/*
	 * Generate and draw walls as specified by the map ArrayList. THIS IS THE CURRENT METHOD BEING USED
	 */
	public void drawMap(ArrayList<char[]> map, SpriteBatch batch) {
		//should do some fancy stuff to position pacman grid in middle of screen horizontally
		//instead of what I've done here with just setting them to a value I picked
		int hOffset = 300;
		int vOffset = 100;
		int side = Tile.getSideLength();
		// this is making what looks like a square, not a rectangle... whoever wrote this
		// an explanation of what exactly is happening would be nice, I think I misunderstood
		for (int i = 0; i < map.size(); i++) {
			char[] s = map.get(i);
			for (int j = 0; j < s.length; j++) {
				//character.getnumericvalue needs to be changed to just be able to
				// take any char
				WallTile wall = new WallTile(Character.getNumericValue(s[j]));
				wall.render(batch, hOffset + i*side, vOffset + j*side);
			}
		}
	}
}
