package deco2800.arcade.guesstheword.gameplay;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
/**
 * This is the class that will "add" all the images required for the game. 
 * */
public class Pictures {
	
	/**
	 * This hashmap is used for storing the level1 pictures.  
	 * String is categories they are in.
	 * */
	private HashMap<String, HashMap<String, Texture>> level1Cat;
//	private HashMap<String, Texture> level1pict;
	/**
	 * This hashmap is used for storing the level2 pictures.  
	 * String is Name of the picture
	 * */
	private HashMap<String, HashMap<String, Texture>> level2Cat;
//	private HashMap<String, Texture> level2pict;
	
	/**
	 * This hashmap is used for storing the level3 pictures.  
	 * String is Name of the picture
	 * */
	private HashMap<String, HashMap<String, Texture>> level3Cat;
//	private HashMap<String, Texture> level3pict;
	
	public void loadPictures(){
		// Declarations for HashMap for the different levels 
		// DO NOT AMEND!
//		level1pict = new HashMap<String, Texture>();
//		level2pict = new HashMap<String, Texture>();
//		level3pict = new HashMap<String, Texture>();
		
		level1Cat =  new HashMap<String, HashMap<String, Texture>>();
		level2Cat =  new HashMap<String, HashMap<String, Texture>>();
		level3Cat =  new HashMap<String, HashMap<String, Texture>>();
		// Creating of the new Texture, in other words each picture 
		// has their own texture. 
		
		//animals textures
		Texture bear1 = new Texture("Images/level1/animals/bear/bear_1.png");
		Texture bull1 = new Texture("Images/level1/animals/bull/bull_1.png");
		Texture fish1 = new Texture("Images/level1/animals/fish/fish_1.png");
		Texture hare1 = new Texture("Images/level1/animals/hare/hare_2.png");
		HashMap<String, Texture> animalsMap = new HashMap<String, Texture>();
		animalsMap.put("BEAR", bear1 );
		animalsMap.put("BULL", bull1 );
		animalsMap.put("FISH", fish1 );
		animalsMap.put("HARE", hare1 );
		
		System.out.println(animalsMap.keySet());
		
//		System.out.println(bear1.getTextureData());
//		Texture bear2 = new Texture("Images/level1/animals/bear/bear_2.png");
//		Texture bear3 = new Texture("Images/level1/animals/bear/bear_3.png");
		
//		Texture bull1 = new Texture("bull1.png");
//		Texture bull2 = new Texture("Images/level1/animals/bull/bull_2.png");
//		Texture bull3 = new Texture("Images/level1/animals/bull/bull_3.png");
		
//		Texture fish1 = new Texture("fish1.png");
//		Texture fish2 = new Texture("Images/level1/animals/fish/fish_2.png");
//		Texture fish3 = new Texture("Images/level1/animals/fish/fish_3.png");
		
//		Texture hare1 = new Texture("hare1.png");
//		Texture hare2 = new Texture("Images/level1/animals/hare/hare_2.png");
//		Texture hare3 = new Texture("Images/level1/animals/hare/hare_3.png");
		
		//brands textures
		Texture dell1 = new Texture("Images/level1/brands/dell/dell_1.png");
		Texture ikea1 = new Texture("Images/level1/brands/ikea/ikea_1.png");	
		Texture nike1 = new Texture("Images/level1/brands/nike/nike_1.png");
		Texture sony1 = new Texture("Images/level1/brands/sony/sony_1.png");		
		HashMap<String, Texture> brandsMap = new HashMap<String, Texture>();
		brandsMap.put("DELL", dell1);
		brandsMap.put("IKEA", ikea1);
		brandsMap.put("NIKE", nike1);
		brandsMap.put("SONY", sony1);
//		System.out.println(brandsMap.keySet());
		
		
//		Texture dell2 = new Texture("Images/level1/brands/dell/dell2.png");
//		Texture dell3 = new Texture("Images/level1/brands/dell/dell3.png");
//		Texture ikea2 = new Texture("Images/level1/brands/ikea/ikea2.png");
//		Texture ikea3 = new Texture("Images/level1/brands/ikea/ikea3.png");
//		Texture nike2 = new Texture("Images/level1/brands/nike/nike2.png");
//		Texture nike3 = new Texture("Images/level1/brands/nike/nike3.png");
//		Texture sony2 = new Texture("Images/level1/brands/sony/sony2.png");
//		Texture sony3 = new Texture("Images/level1/brands/sony/sony3.png");

		//countries textures
		Texture cuba1 = new Texture("Images/level1/countries/cuba/cuba_2.png");
		Texture iran1 = new Texture("Images/level1/countries/iran/iran_3.png");
		Texture iraq1 = new Texture("Images/level1/countries/iraq/iraq_3.png");
		Texture laos1 = new Texture("Images/level1/countries/laos/laos_3.png");
		Texture peru1 = new Texture("Images/level1/countries/peru/peru_3.png");
		HashMap<String, Texture> countriesMap = new HashMap<String, Texture>();
		countriesMap.put("CUBA", cuba1);
		countriesMap.put("IRAN", iran1);
		countriesMap.put("IRAQ", iraq1);
		countriesMap.put("PERU", peru1);
		countriesMap.put("LAOS", laos1);
//		System.out.println(countriesMap.keySet());
		
//		Texture cuba2 = new Texture("Images/level1/countries/cuba/cuba2.png");
//		Texture cuba3 = new Texture("Images/level1/countries/cuba/cuba3.png");
//		Texture iran2 = new Texture("Images/level1/countries/iran/iran2.png");
//		Texture iran3 = new Texture("Images/level1/countries/iran/iran3.png");
//		Texture iraq2 = new Texture("Images/level1/countries/iraq/iraq2.png");
//		Texture iraq3 = new Texture("Images/level1/countries/iraq/iraq3.png");		
//		Texture laos2 = new Texture("Images/level1/countries/laos/laos2.png");
//		Texture laos3 = new Texture("Images/level1/countries/laos/laos3.png");				
//		Texture peru2 = new Texture("Images/level1/countries/peru/peru2.png");
//		Texture peru3 = new Texture("Images/level1/countries/peru/peru3.png");

		//Sports textures
		Texture judo1 = new Texture("Images/level1/sports/judo/judo_3.png");
		Texture polo1 = new Texture("Images/level1/sports/polo/polo_1.png");
		HashMap<String, Texture> sportsMap = new HashMap<String, Texture>();
		sportsMap.put("JUDO" , judo1);
		sportsMap.put("POLO" , polo1); 
//		System.out.println(sportsMap.keySet());
		
//		Texture judo2 = new Texture("Images/level1/sports/judo/judo2.jpg");
//		Texture judo3 = new Texture("Images/level1/sports/judo/judo3.png");	
//		Texture polo2 = new Texture("Images/level1/sports/polo/polo2.png");
//		Texture polo3 = new Texture("Images/level1/sports/polo/polo3.png");
//		Texture silat1 = new Texture("Images/level1/sports/silat/silat1.png");
//		Texture silat2 = new Texture("Images/level1/sports/silat/silat2.png");
//		Texture silat3 = new Texture("Images/level1/sports/silat/silat3.png");
		
		level1Cat.put("ANIMALS", animalsMap);
		level1Cat.put("BRANDS", brandsMap);
		level1Cat.put("COUNTRIES", countriesMap);
		level1Cat.put("SPORTS", sportsMap);

	}
	
	public HashMap<String, HashMap<String, Texture>> getLevel1(){
		return level1Cat;
	}
}
