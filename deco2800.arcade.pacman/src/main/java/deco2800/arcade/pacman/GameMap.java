package deco2800.arcade.pacman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
	private boolean vsym;
	
	public GameMap(int width, int height) {
		this.width = width;
		this.height = height;
		vsym = false;
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
		
		//resultArray contains a number of char arrays equal to the number of lines
		// each array contains all the characters from that line
		ArrayList<char[]> resultArray = new ArrayList<char[]>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(absolutePath));			
			String line;
			while ((line = br.readLine()) != null) {
				if (line.contains("VSYM")) {
					vsym = true;
					continue;
				}
				if (vsym) {
					line = useVSymmetry(line);
				}
				resultArray.add(line.toCharArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null)  {
				br.close();
			}			
		}		
		//reverse as libgdx draws from bottom left
		Collections.reverse(resultArray);
//		for (int i=0; i<resultArray.size(); i++) {
//			System.out.println(resultArray.get(i));
//		}
		return resultArray;
	}

	private String useVSymmetry(String line) {
		StringBuffer reverse = new StringBuffer(line).reverse();
		for (int i = 0; i < reverse.length(); i++) {
			char letter = reverse.charAt(i);
			String replacer = null;
			switch(letter) {
			case 'A': replacer = "C"; break;
			case 'C': replacer = "A"; break;
			case 'D': replacer = "E"; break;
			case 'E': replacer = "D"; break;
			case 'F': replacer = "H"; break;
			case 'H': replacer = "F"; break;
			case 'a': replacer = "c"; break;
			case 'c': replacer = "a"; break;
			case 'd': replacer = "e"; break;
			case 'e': replacer = "d"; break;
			case 'f': replacer = "h"; break;
			case 'h': replacer = "f"; break;
			case '1': replacer = "3"; break;
			case '3': replacer = "1"; break;
			case '4': replacer = "5"; break;
			case '5': replacer = "4"; break;
			case '6': replacer = "8"; break;
			case '8': replacer = "6"; break;
			case '9': replacer = "0"; break;
			case '0': replacer = "9"; break;
			case 'R': replacer = "S"; break;
			case 'S': replacer = "R"; break;
			case 'X': replacer = "Z"; break;
			case 'Z': replacer = "X"; break;
			case 'W': replacer = "Y"; break;
			case 'Y': replacer = "W"; break;
			case 'x': replacer = "z"; break;
			case 'z': replacer = "x"; break;
			case 'w': replacer = "y"; break;
			case 'y': replacer = "w"; break;
			case 'J': replacer = "L"; break;
			case 'L': replacer = "J"; break;
			case 'K': replacer = "M"; break;
			case 'M': replacer = "K"; break;
			}
			if (replacer !=  null) {
				reverse.replace(i, i+1, replacer);
				//System.out.println("New string: " + reverse);
			}
		}
		return line + reverse.toString();
	}

//	public static void main(String[] args) {
//		System.out.println(useVSymmetry(new String("ABBBBBBBBBBBBR")));
//		System.out.println(useVSymmetry(new String("Dppppppppppppd")));
//		System.out.println(useVSymmetry(new String("Dpabbcpabbbcpd")));
//		System.out.println(useVSymmetry(new String("DPd  epd   epd")));
//	}
	
	/*
	 * Alternate implementation of readMap. Given that map files
	 * have relatively small dimensions, it shouldn't be an issue to simply
	 * read the entire text file into memory all at once. So for this purpose,
	 * we can just use libgdx's functions to read our file.
	 */
	public ArrayList<char[]> readMap2(String file) {
		// Pre-process 'file' to get it to point to the correct directory
		file = "src\\main\\resources\\" + file;		
		String absolutePath = Gdx.files.internal(file).file().getAbsolutePath();
		absolutePath = absolutePath.replace("arcade", "arcade.pacman");
		FileHandle filePath = Gdx.files.absolute(absolutePath);
		String contents = filePath.readString();		
		ArrayList<char[]> levelMap = new ArrayList<char[]>();	
		String[] lineArray = contents.split(System.getProperty("line.separator"));
		for (int i = 0; i < lineArray.length; i++) {
			levelMap.add(lineArray[i].toCharArray());
		}
		return levelMap;
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
		for (int lineNum = 0; lineNum < map.size(); lineNum++) {
			char[] line = map.get(lineNum);
			for (int i = 0; i < line.length; i++) {
				WallTile wall = new WallTile(line[i]);
				wall.render(batch, hOffset + i*side, vOffset + lineNum*side);
			}
		}
	}
}
