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
		
		//animals textures level 1
		Texture bear1 = new Texture("Images/level1/animals/bear/bear_1.png");
		Texture bull1 = new Texture("Images/level1/animals/bull/bull_1.png");
		Texture fish1 = new Texture("Images/level1/animals/fish/fish_1.png");
		Texture hare1 = new Texture("Images/level1/animals/hare/hare_1.png");
		Texture frog1 = new Texture("Images/level1/animals/frog/frog_1.png");
		HashMap<String, Texture> animalsMap = new HashMap<String, Texture>();
		animalsMap.put("BEAR", bear1 );
		animalsMap.put("BULL", bull1 );
		animalsMap.put("FISH", fish1 );
		animalsMap.put("FROG", frog1 );
		animalsMap.put("HARE", hare1 );
		
		System.out.println(animalsMap.keySet());
		
		//animals textures level 2
		Texture birds1 = new Texture("Images/level2/animals/birds/birds_1.png");
		Texture lions1 = new Texture("Images/level2/animals/lions/lions_1.png");
		Texture sheep1 = new Texture("Images/level2/animals/sheep/sheep_1.png");
		Texture wolfs1 = new Texture("Images/level2/animals/wolfs/wolfs_1.png");
		Texture tiger1 = new Texture("Images/level2/animals/tiger/tiger_1.png");
		HashMap<String, Texture> animalsMap2 = new HashMap<String, Texture>();
		animalsMap2.put("BIRDS", birds1 );
		animalsMap2.put("LIONS", lions1 );
		animalsMap2.put("SHEEP", sheep1 );
		animalsMap2.put("WOLFS", wolfs1 );
		animalsMap2.put("TIGER", tiger1 );
		
		System.out.println(animalsMap2.keySet());
		
		//animals textures level 3
		Texture baboon1 = new Texture("Images/level3/animals/baboon/baboon_1.png");
		Texture donkey1 = new Texture("Images/level3/animals/donkey/donkey_1.png");
		Texture bulldog1 = new Texture("Images/level3/animals/bulldog/bulldog_1.png");
		Texture catfish1 = new Texture("Images/level3/animals/catfish/catfish_1.png");
		Texture turkey1 = new Texture("Images/level3/animals/turkey/turkey_1.png");
		HashMap<String, Texture> animalsMap3 = new HashMap<String, Texture>();
		animalsMap3.put("BABOON", baboon1 );
		animalsMap3.put("DONKEY", donkey1 );
		animalsMap3.put("BULLDOG", bulldog1 );
		animalsMap3.put("CATFISH", catfish1 );
		animalsMap3.put("TURKEY", turkey1 );
				
		System.out.println(animalsMap3.keySet());
		
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
		
		//brands textures level 1
		Texture dell1 = new Texture("Images/level1/brands/dell/dell_1.png");
		Texture ikea1 = new Texture("Images/level1/brands/ikea/ikea_1.png");	
		Texture nike1 = new Texture("Images/level1/brands/nike/nike_1.png");
		Texture sony1 = new Texture("Images/level1/brands/sony/sony_1.png");	
		Texture ford1 = new Texture("Images/level1/brands/ford/ford_1.png");
		HashMap<String, Texture> brandsMap = new HashMap<String, Texture>();
		brandsMap.put("DELL", dell1);
		brandsMap.put("IKEA", ikea1);
		brandsMap.put("NIKE", nike1);
		brandsMap.put("SONY", sony1);
		brandsMap.put("FORD", ford1);
//		System.out.println(brandsMap.keySet());
		
		
		//brands textures level 2
		Texture pepsi1 = new Texture("Images/level2/brands/pepsi/pepsi_1.png");
		Texture pixar1 = new Texture("Images/level2/brands/pixar/pixar_1.jpg");	
		Texture intel1 = new Texture("Images/level2/brands/intel/intel_1.png");
		Texture honda1 = new Texture("Images/level2/brands/honda/honda_1.png");		
		Texture kraft1 = new Texture("Images/level2/brands/kraft/kraft_1.png");	
		HashMap<String, Texture> brandsMap2 = new HashMap<String, Texture>();
		brandsMap2.put("PEPSI", pepsi1);
		brandsMap2.put("PIXAR", pixar1);
		brandsMap2.put("INTEL", intel1);
		brandsMap2.put("HONDA", honda1);
		brandsMap2.put("KRAFT", kraft1);
//		System.out.println(brandsMap2.keySet());
		
		//brands textures level 3
		Texture adidas1 = new Texture("Images/level3/brands/adidas/adidas_1.png");
		Texture qantas1 = new Texture("Images/level3/brands/qantas/qantas_1.png");	
		Texture target1 = new Texture("Images/level3/brands/targer/target_1.png");
		Texture hermes1 = new Texture("Images/level3/brands/hermes/hermes_1.png");	
		Texture kitkat1 = new Texture("Images/level3/brands/kitkat/kitkat_1.png");	
		HashMap<String, Texture> brandsMap3 = new HashMap<String, Texture>();
		brandsMap3.put("ADIDAS", adidas1);
		brandsMap3.put("QANTAS", qantas1);
		brandsMap3.put("TARGET", target1);
		brandsMap3.put("HERMES", hermes1);
		brandsMap3.put("KITKAT", kitkat1);
		
//		System.out.println(brandsMap3.keySet());
		
//		Texture dell2 = new Texture("Images/level1/brands/dell/dell2.png");
//		Texture dell3 = new Texture("Images/level1/brands/dell/dell3.png");
//		Texture ikea2 = new Texture("Images/level1/brands/ikea/ikea2.png");
//		Texture ikea3 = new Texture("Images/level1/brands/ikea/ikea3.png");
//		Texture nike2 = new Texture("Images/level1/brands/nike/nike2.png");
//		Texture nike3 = new Texture("Images/level1/brands/nike/nike3.png");
//		Texture sony2 = new Texture("Images/level1/brands/sony/sony2.png");
//		Texture sony3 = new Texture("Images/level1/brands/sony/sony3.png");

		//countries textures level 1
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
		
		//countries textures level 2
		Texture india1 = new Texture("Images/level2/countries/india/india_1.png");
		Texture italy1 = new Texture("Images/level2/countries/italy/italy_1.png");
		Texture kenya1 = new Texture("Images/level2/countries/kenya/kenya_1.png");
		Texture korea1 = new Texture("Images/level2/countries/korea/korea_1.png");
		Texture nepal1 = new Texture("Images/level2/countries/nepal/nepal_1.png");
		HashMap<String, Texture> countriesMap2 = new HashMap<String, Texture>();
		countriesMap2.put("INDIA", india1);
		countriesMap2.put("ITALY", italy1);
		countriesMap2.put("KENYA", kenya1);
		countriesMap2.put("KOREA", korea1);
		countriesMap2.put("NEPAL", nepal1);
//		System.out.println(countriesMap2.keySet());
		
		//countries textures level 3
		Texture africa1 = new Texture("Images/level3/countries/africa/africa_1.png");
		Texture mexico1 = new Texture("Images/level3/countries/mexico/mexico_1.png");
		Texture russia1 = new Texture("Images/level3/countries/russia/russian_1.png");
		Texture turkey2 = new Texture("Images/level3/countries/turkey/turkeyflag_1.png");
		Texture zambia1 = new Texture("Images/level3/countries/zambia/zambia_1.png");
		HashMap<String, Texture> countriesMap3 = new HashMap<String, Texture>();
		countriesMap3.put("AFRICA", africa1);
		countriesMap3.put("MEXICO", mexico1);
		countriesMap3.put("RUSSIA", russia1);
		countriesMap3.put("TURKEY", turkey2);
		countriesMap3.put("ZAMBIA", zambia1);
//		System.out.println(countriesMap3.keySet());
		
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

		//Sports textures level 1
		Texture judo1 = new Texture("Images/level1/sports/judo/judo_3.png");
		Texture polo1 = new Texture("Images/level1/sports/polo/polo_1.png");
		Texture epee1 = new Texture("Images/level1/sports/epee/epee_1.png");
		Texture golf1 = new Texture("Images/level1/sports/golf/golf_1.png");
		Texture dart1 = new Texture("Images/level1/sports/dart/dart_1.png");
		HashMap<String, Texture> sportsMap = new HashMap<String, Texture>();
		sportsMap.put("JUDO" , judo1);
		sportsMap.put("POLO" , polo1); 
		sportsMap.put("EPEE" , epee1); 
		sportsMap.put("GOLF" , golf1); 
		sportsMap.put("DART" , dart1); 
//		System.out.println(sportsMap.keySet());
		
		//Sports textures level 2
		Texture canoe1 = new Texture("Images/level2/sports/canoe/canoe_1.png");
		Texture chess1 = new Texture("Images/level2/sports/chess/chess_1.png");
		Texture silat1 = new Texture("Images/level2/sports/silat/silat_1.png");
		Texture rugby1 = new Texture("Images/level2/sports/rugby/rugby_1.png");
		Texture rodeo1 = new Texture("Images/level2/sports/rodeo/rodeo_1.png");
		HashMap<String, Texture> sportsMap2 = new HashMap<String, Texture>();
		sportsMap2.put("CANOE" , canoe1);
		sportsMap2.put("CHESS" , chess1);
		sportsMap2.put("SILAT" , silat1);
		sportsMap2.put("RUGBY" , rugby1);
		sportsMap2.put("RODEO" , rodeo1);
//		System.out.println(sportsMap2.keySet());
		
		//Sports textures level 3
		Texture boxing1 = new Texture("Images/level3/sports/boxing/boxing_1.png");
		Texture diving1 = new Texture("Images/level3/sports/diving/diving_1.png");
		Texture karate1 = new Texture("Images/level3/sports/karate/karate_1.png");
		Texture squash1 = new Texture("Images/level3/sports/squash/squash_1.png");
		Texture skiing1 = new Texture("Images/level3/sports/skiing/skiing_1.png");
		HashMap<String, Texture> sportsMap3 = new HashMap<String, Texture>();
		sportsMap.put("BOXING" , boxing1);
		sportsMap.put("DIVING" , diving1); 
		sportsMap.put("KARATE" , karate1);
		sportsMap.put("SQUASH" , squash1); 
		sportsMap.put("SKIING" , skiing1); 
//		System.out.println(sportsMap3.keySet());
		
//		Texture judo2 = new Texture("Images/level1/sports/judo/judo2.jpg");
//		Texture judo3 = new Texture("Images/level1/sports/judo/judo3.png");	
//		Texture polo2 = new Texture("Images/level1/sports/polo/polo2.png");
//		Texture polo3 = new Texture("Images/level1/sports/polo/polo3.png");
//		Texture silat1 = new Texture("Images/level1/sports/silat/silat1.png");
//		Texture silat2 = new Texture("Images/level1/sports/silat/silat2.png");
//		Texture silat3 = new Texture("Images/level1/sports/silat/silat3.png");
		
		//Transport textures level 1
		Texture bike1 = new Texture("Images/level1/transports/bike/bike_1.png");
		Texture luge1 = new Texture("Images/level1/transports/luge/luge_1.png");
		Texture taxi1 = new Texture("Images/level1/transports/taxi/taxi_1.png");
		Texture tram1 = new Texture("Images/level1/transports/tram/tram_1.png");
		Texture boat1 = new Texture("Images/level1/transports/boat/boat_1.png");
		HashMap<String, Texture> transportsMap = new HashMap<String, Texture>();
		transportsMap.put("BIKE" , bike1);
		transportsMap.put("LUGE" , luge1); 
		transportsMap.put("TAXI" , taxi1);
		transportsMap.put("TRAM" , tram1); 
		transportsMap.put("BOAT" , boat1); 
//		System.out.println(transportsMap.keySet());
		
		//Transport textures level 2
		Texture ferry1 = new Texture("Images/level2/transports/ferry/ferry_1.png");
		Texture liner1 = new Texture("Images/level2/transports/liner/liner_1.png");
		Texture train1 = new Texture("Images/level2/transports/train/train_1.png");
		Texture wagon1 = new Texture("Images/level2/transports/wagon/wagon_1.png");
		Texture yacht1 = new Texture("Images/level2/transports/yacht/yacht_1.png");
		HashMap<String, Texture> transportsMap2 = new HashMap<String, Texture>();
		transportsMap2.put("FERRY" , ferry1);
		transportsMap2.put("LINER" , liner1); 
		transportsMap2.put("TRAIN" , train1);
		transportsMap2.put("WAGON" , wagon1); 
		transportsMap2.put("YACHT" , yacht1); 
//		System.out.println(transportsMap2.keySet());
		
		//Transport textures level 3
		Texture subway1 = new Texture("Images/level3/transports/subway/subway_1.png");
		HashMap<String, Texture> transportsMap3 = new HashMap<String, Texture>();
		transportsMap3.put("SUBWAY" , subway1);
//		System.out.println(transportsMap3.keySet());
				
		level1Cat.put("ANIMALS", animalsMap);
		level1Cat.put("BRANDS", brandsMap);
		level1Cat.put("COUNTRIES", countriesMap);
		level1Cat.put("SPORTS", sportsMap);
		level1Cat.put("TRANSPORTS", transportsMap);
		
		level2Cat.put("ANIMALS", animalsMap2);
		level2Cat.put("BRANDS", brandsMap2);
		level2Cat.put("COUNTRIES", countriesMap2);
		level2Cat.put("SPORTS", sportsMap2);
		level2Cat.put("TRANSPORTS", transportsMap2);
		
		level3Cat.put("ANIMALS", animalsMap3);
		level3Cat.put("BRANDS", brandsMap3);
		level3Cat.put("COUNTRIES", countriesMap3);
		level3Cat.put("SPORTS", sportsMap3);
		level3Cat.put("TRANSPORTS", transportsMap3);

	}
	
	public HashMap<String, HashMap<String, Texture>> getLevel1(){
		return level1Cat;
	}
	
	public HashMap<String, HashMap<String, Texture>> getLevel2(){
		return level2Cat;
	}
	public HashMap<String, HashMap<String, Texture>> getLevel3(){
		return level3Cat;
	}
}
