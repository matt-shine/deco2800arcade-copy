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
		//animals textures
		Texture bear1 = new Texture("Images/level1/animals/bear/bear1.png");
		Texture bear2 = new Texture("Images/level1/animals/bear/bear2.png");
		Texture bear3 = new Texture("Images/level1/animals/bear/bear3.png");
		
		Texture bull1 = new Texture("Images/level1/animals/bull/bull1.png");
		Texture bull2 = new Texture("Images/level1/animals/bull/bull2.png");
		Texture bull3 = new Texture("Images/level1/animals/bull/bull3.png");
		
		Texture fish1 = new Texture("Images/level1/animals/fish/fish1.png");
		Texture fish2 = new Texture("Images/level1/animals/fish/fish2.png");
		Texture fish3 = new Texture("Images/level1/animals/fish/fish3.png");
		
		Texture hare1 = new Texture("Images/level1/animals/hare/hare1.png");
		Texture hare2 = new Texture("Images/level1/animals/hare/hare2.png");
		Texture hare3 = new Texture("Images/level1/animals/hare/hare3.png");
		
		//brands textures
		Texture dell1 = new Texture("Images/level1/brands/dell/dell1.png");
		Texture dell2 = new Texture("Images/level1/brands/dell/dell2.png");
		Texture dell3 = new Texture("Images/level1/brands/dell/dell3.png");
				
		Texture ikea1 = new Texture("Images/level1/brands/ikea/ikea1.png");
		Texture ikea2 = new Texture("Images/level1/brands/ikea/ikea2.png");
		Texture ikea3 = new Texture("Images/level1/brands/ikea/ikea3.png");
				
		Texture nike1 = new Texture("Images/level1/brands/nike/nike1.png");
		Texture nike2 = new Texture("Images/level1/brands/nike/nike2.png");
		Texture nike3 = new Texture("Images/level1/brands/nike/nike3.png");
				
		Texture sony1 = new Texture("Images/level1/brands/sony/sony1.png");
		Texture sony2 = new Texture("Images/level1/brands/sony/sony2.png");
		Texture sony3 = new Texture("Images/level1/brands/sony/sony3.png");
				
				//countries textures
		Texture cuba1 = new Texture("Images/level1/countries/cuba/cuba1.png");
		Texture cuba2 = new Texture("Images/level1/countries/cuba/cuba2.png");
		Texture cuba3 = new Texture("Images/level1/countries/cuba/cuba3.png");
				
		Texture iran1 = new Texture("Images/level1/countries/iran/iran1.png");
		Texture iran2 = new Texture("Images/level1/countries/iran/iran2.png");
		Texture iran3 = new Texture("Images/level1/countries/iran/iran3.png");
				
		Texture iraq1 = new Texture("Images/level1/countries/iraq/iraq1.png");
		Texture iraq2 = new Texture("Images/level1/countries/iraq/iraq2.png");
		Texture iraq3 = new Texture("Images/level1/countries/iraq/iraq3.png");
				
		Texture laos1 = new Texture("Images/level1/countries/laos/laos1.png");
		Texture laos2 = new Texture("Images/level1/countries/laos/laos2.png");
		Texture laos3 = new Texture("Images/level1/countries/laos/laos3.png");
				
		Texture peru1 = new Texture("Images/level1/countries/peru/peru1.png");
		Texture peru2 = new Texture("Images/level1/countries/peru/peru2.png");
		Texture peru3 = new Texture("Images/level1/countries/peru/peru3.png");
		
		//countries textures
		Texture judo1 = new Texture("Images/level1/sports/judo/cuba1.jpg");
		Texture judo2 = new Texture("Images/level1/sports/judo/cuba2.jpg");
		Texture judo3 = new Texture("Images/level1/sports/judo/cuba3.png");
		
		Texture polo1 = new Texture("Images/level1/countries/polo/iran1.png");
		Texture polo2 = new Texture("Images/level1/countries/polo/iran2.png");
		Texture polo3 = new Texture("Images/level1/countries/polo/iran3.png");
		
		Texture silat1 = new Texture("Images/level1/countries/silat/iraq1.png");
		Texture silat2 = new Texture("Images/level1/countries/silat/iraq2.png");
		Texture silat3 = new Texture("Images/level1/countries/silat/iraq3.png");
		


		
		// Adding of the word and "image" into the hashmap.
		level1pict.put("rain" , rain);
		level1pict.put("cold" , cold);
		//level 1 (animals)
		level1pict.put("bear",bear1);
		level1pict.put("bear",bear2);
		level1pict.put("bear",bear3);		
		level1pict.put("bull",bull1);
		level1pict.put("bull",bull2);
		level1pict.put("bull",bull3);		
		level1pict.put("fish",fish1);
		level1pict.put("fish",fish2);
		level1pict.put("fish",fish3);		
		level1pict.put("hare",hare1);
		level1pict.put("hare",hare2);
		level1pict.put("hare", hare3);
		//level 1 (Brands)
		level1pict.put("ikea", ikea1);
		level1pict.put("ikea", ikea2);
		level1pict.put("ikea", ikea3);
		level1pict.put("sony", sony1);
		level1pict.put("sony", sony2);
		level1pict.put("sony", sony3);
		level1pict.put("nike", nike1);
		level1pict.put("nike", nike2);
		level1pict.put("nike", nike3);
		level1pict.put("dell", dell1);
		level1pict.put("dell", dell2);
		level1pict.put("dell", dell3);
		//level 1 (Countries)
		level1pict.put("cuba", cuba1);
		level1pict.put("cuba", cuba2);
		level1pict.put("cuba", cuba3);
		level1pict.put("iran", iran1);
		level1pict.put("iran", iran2);
		level1pict.put("iran", iran3);
		level1pict.put("iraq", iraq1);
		level1pict.put("iraq", iraq2);
		level1pict.put("iraq", iraq3);
		level1pict.put("laos", laos1);
		level1pict.put("laos", laos2);
		level1pict.put("laos", laos3);
		level1pict.put("peru", peru1);
		level1pict.put("peru", peru2);
		level1pict.put("peru", peru3);
				
		//level 1 (Sports)
		level1pict.put("judo", judo1);
		level1pict.put("judo", judo2);
		level1pict.put("judo", judo3);
		level1pict.put("polo", polo1);
		level1pict.put("polo", polo2);
		level1pict.put("polo", polo3);
		level1pict.put("silat", silat1);
		level1pict.put("silat", silat2);
		level1pict.put("silat", silat3);
	}

}
