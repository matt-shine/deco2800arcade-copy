package deco2800.arcade.guesstheword.test;

import java.io.File;
import java.util.HashMap;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class PictureTest {
	
	@Test
	public void testAnimalPictureExist(){
		// Level 1 Animals Pictures
		assertTrue(new File("src/main/resources/Images/level1/animals/bear/bear_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/animals/bull/bull_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/animals/fish/fish_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/animals/hare/hare_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/animals/frog/frog_1.png").exists());
		
		// Level 2 Animal Pictures
		assertTrue(new File("src/main/resources/Images/level2/animals/birds/birds_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/animals/lions/lions_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/animals/sheep/sheep_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/animals/wolfs/wolf_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/animals/tiger/tiger_1.png").exists());
		
		// Level 3 Animal Pictures
		assertTrue(new File("src/main/resources/Images/level3/animals/baboon/baboon_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/animals/donkey/donkey_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/animals/bulldog/bulldog_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/animals/catfish/catfish_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/animals/turkey/turkey_1.png").exists());
	}
	
	@Test
	public void testBrandPictureExists(){
		
		assertTrue(new File("src/main/resources/Images/level1/brands/dell/dell_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/brands/ikea/ikea_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/brands/nike/nike_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/brands/sony/sony_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/brands/ford/ford_1.png").exists());
		
		assertTrue(new File("src/main/resources/Images/level2/brands/pepsi/pepsi_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/brands/pixar/pixar_1.jpg").exists());
		assertTrue(new File("src/main/resources/Images/level2/brands/intel/intel_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/brands/honda/honda_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/brands/kraft/kraft_1.png").exists());

		assertTrue(new File("src/main/resources/Images/level3/brands/adidas/adidas_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/brands/qantas/qantas_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/brands/target/target_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/brands/hermes/hermes.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/brands/kitkat/kitkat_1.png").exists());
		
	}
	
	@Test
	public void testSportPictureExists(){
		assertTrue(new File("src/main/resources/Images/level1/sports/judo/judo_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/sports/polo/polo_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/sports/epee/epee_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/sports/golf/golf_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/sports/dart/dart_1.png").exists());

		assertTrue(new File("src/main/resources/Images/level2/sports/canoe/canoe_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/sports/chess/chess_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/sports/silat/silat_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/sports/rugby/rugby_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/sports/rugby/rugby_1.png").exists());
		
		assertTrue(new File("src/main/resources/Images/level3/sports/boxing/boxing_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/sports/diving/diving_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/sports/karate/karate_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/sports/squash/squash_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/sports/skiing/skiing_1.png").exists());

	}
	
	@Test
	public void testCountriesPictureExists(){
		assertTrue(new File("src/main/resources/Images/level1/countries/cuba/cuba_2.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/countries/iran/iran_3.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/countries/iraq/iraq_3.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/countries/laos/laos_3.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/countries/peru/peru_3.png").exists());

		assertTrue(new File("src/main/resources/Images/level2/countries/india/india_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/countries/italy/italy_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/countries/kenya/kenya_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/countries/korea/korea_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/countries/nepal/nepal_1.png").exists());

		assertTrue(new File("src/main/resources/Images/level3/countries/africa/africa_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/countries/mexico/mexico_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/countries/russia/russia_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/countries/turkey/turkeyflag_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/countries/zambia/zambia_1.png").exists());
		
	}
	
	@Test
	public void testTransportsPictureExists(){
		assertTrue(new File("src/main/resources/Images/level1/transports/bike/bike_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/transports/luge/luge_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/transports/taxi/taxi_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/transports/tram/tram_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level1/transports/boat/boat_1.png").exists());

		assertTrue(new File("src/main/resources/Images/level2/transports/ferry/ferry_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/transports/liner/liner_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/transports/train/train_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/transports/wagon/wagon_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level2/transports/yacht/yacht_1.png").exists());
		
		assertTrue(new File("src/main/resources/Images/level3/transports/subway/subway_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/transports/barrow/barrow_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/transports/convoy/Convoy_1.jpg").exists());
		assertTrue(new File("src/main/resources/Images/level3/transports/boxcar/boxcar_1.png").exists());
		assertTrue(new File("src/main/resources/Images/level3/transports/glider/glider_1.png").exists());
		
	}
	

}
