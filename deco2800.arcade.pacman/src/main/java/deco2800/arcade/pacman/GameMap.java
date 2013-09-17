package deco2800.arcade.pacman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/*
 * Probably just replace this with a method in Pacman.java later, but for now
 * this is just a thinking space :).
 */

public class GameMap {

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
	 * Generate and draw walls as specified by the map ArrayList.
	 */
	public void drawMap(ArrayList<char[]> map, ShapeRenderer shaper) {
		// using a unit length for the walls as 25 pixels. This means by the
		// standard grid we are working at
		// at the moment, the txt files must be 24x24.
		for (int i = 0; i < map.size(); i++) {
			char[] s = map.get(i);
			for (int j = 0; j < s.length; j++) {
				Wall wall = new Wall(Character.getNumericValue(s[j]),
						(900 - 24 * i), (650 - 24 * j), 25);
				wall.render(shaper);
			}
		}
	}
}
