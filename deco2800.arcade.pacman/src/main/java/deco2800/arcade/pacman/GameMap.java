package deco2800.arcade.pacman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	
	
	ArrayList<char[]> readMap(String file) throws IOException {

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

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
	 * Generate and draw walls as specified by the map ArrayList.
	 */
	void drawMap(List<Object> colList, ArrayList<char[]> map, ShapeRenderer shaper){
		// using a unit length for the walls as 25 pixels. This means by the standard grid we are working at
		// at the moment, the txt files must be 24x24.
		for (int i = 0; i < map.size(); i++){
			char[] s = map.get(i);
			for (int j = 0; j < s.length; j++){
				//Wall wall = new Wall(Character.getNumericValue(s[j]), (900-24*i), (650-24*j), 25);
				//wall.render(shaper); 
			}
		}
	}
}
