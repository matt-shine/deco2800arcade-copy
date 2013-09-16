package deco2800.arcade.pacman;

import java.io.BufferedReader;
import java.io.File;
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
	 * 
	 * I'm thinking that we just have a specific character representing each
	 * type of wall.
	 * 
	 * Later on we can just have a loop to generate all our walls.
	 */

	public static void main(String[] args) {
		System.out.println("first");
		FileHandle fh = Gdx.files.internal("testmap");
		System.out.println("before 2string");
		String file = fh.toString();
		System.out.println("test!");
		System.out.println(file);

	}

	public ArrayList<char[]> readMap(String file) throws IOException {

		file = "src\\main\\resources\\testmap.png";
		FileHandle fh = Gdx.files.internal(file);
		file = fh.toString();

		File absoluteFile = fh.file();
		String absolutePath = absoluteFile.getAbsolutePath();
		absolutePath = absolutePath.replace("arcade", "arcade.pacman");

		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(absolutePath);
			br = new BufferedReader(fr);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
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

	public ArrayList<char[]> readMap2(String file) {
		FileHandle filePath = Gdx.files.internal(file);
		String mapstr = filePath.readString();
		String[] stringarray;
		ArrayList<char[]> mapp = new ArrayList<char[]>();
		// Split file by newlines.
		stringarray = mapstr.split(System.getProperty("line.separator"));

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
