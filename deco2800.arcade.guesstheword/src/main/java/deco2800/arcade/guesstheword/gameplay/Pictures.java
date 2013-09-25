package deco2800.arcade.guesstheword.gameplay;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
/**
 * This is the class that will "add" all the images required for the game. 
 * */
public class Pictures {
	/**
	 * This hashmap is used for storing the level1 pictures.  
	 * String is Name of the picture
	 * */
	private HashMap<String, Texture> level1pict;
	
	/**
	 * This hashmap is used for storing the level2 pictures.  
	 * String is Name of the picture
	 * */
	private HashMap<String, Texture> level2pict;
	
	/**
	 * This hashmap is used for storing the level3 pictures.  
	 * String is Name of the picture
	 * */
	private HashMap<String, Texture> level3pict;
	
	private ArrayList<HashMap<String, Texture>> categories;
	
	public void addPictures(){
		// Declarations for HashMap for the different levels 
		// DO NOT AMEND!
		level1pict = new HashMap<String, Texture>();
		level2pict = new HashMap<String, Texture>();
		level3pict = new HashMap<String, Texture>();
		
		// Creating of the new Texture, in other words each picture 
		// has their own texture. 
		Texture rain = new Texture("Images/level1/rain/rain1.jpg");
		Texture cold = new Texture("Images/level1/cold/cold1.jpg");
		
		// Adding of the word and "image" into the hashmap.
		level1pict.put("rain" , rain);
		level1pict.put("cold" , cold);
		

	}

}
