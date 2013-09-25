package deco2800.arcade.guesstheword.gameplay;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class Pictures {
	/**
	 * This hashmap is used for storing the level1 pictures.  String is the catergories*/
	private HashMap<String, Texture> level1pict;
	private HashMap<String, Texture> level2pict;
	private HashMap<String, Texture> level3pict;
	
	private ArrayList<HashMap<String, Texture>> categories;
	
	public void addPictures(){
		level1pict = new HashMap<String, Texture>();
		level2pict = new HashMap<String, Texture>();
		level3pict = new HashMap<String, Texture>();
		
		Texture rain = new Texture("Images/level1/rain/rain1.jpg");
		Texture cold = new Texture("Images/level1/cold/cold1.jpg");
		
		level1pict.put("rain" , rain);
		level1pict.put("cold" , cold);
		

	}

}
