package deco2800.arcade.pacman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/*
 * Probably just replace this with a method in Pacman.java later, but for now
 * this is just a thinking space :).
 */

public class GameMap {

	/*
	 * The idea is to read in a text file representing the game 'grid'
	 * and append lines to an ArrayList. Maybe arrays of chars/strings will work
	 * more nicely but for now it's just convenient because ArrayLists are dynamically
	 * sized so there's no need to pre-compute the size of the map beforehand.
	 * 
	 * I'm thinking that we just have a specific character representing 
	 * each type of wall.
	 * 
	 * Later on we can just have a loop to generate all our walls. 
	 * 
	 * 
	 */
	ArrayList<String> readMap(File file) throws IOException{
		
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		ArrayList<String> resultArray = new ArrayList<String>();
		try{
		String str;
		while((str = br.readLine())!=null){
			resultArray.add(str);
		 }
		}catch (IOException e){
			e.printStackTrace();
		} finally{
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return resultArray;
		
	}
}
