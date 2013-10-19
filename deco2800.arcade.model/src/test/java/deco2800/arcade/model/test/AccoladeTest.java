package deco2800.arcade.model.test;
import java.io.Console;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.model.AccoladeContainer;
import deco2800.arcade.model.Accolade;

//TODO Add in extra tests to see if an error is thrown when trying to get an optional field when no set (id, gameid etc)
public class AccoladeTest {
	private Accolade a1;
	private Accolade a2;
	private Accolade a3;
	private Accolade a4;
	private Accolade a5;
	private AccoladeContainer ac1;
	private AccoladeContainer ac2;
	private AccoladeContainer ac3;
	private AccoladeContainer ac4;
	private AccoladeContainer ac5;
	private AccoladeContainer ac6;
	
	
	@Before
	public void initialise(){
		/** Accolade(String name, String message, int popup, String popupMessage, 
		* float modifier, String unit, String tag, String imagePath) **/
		a1 = new Accolade("Name1", "String of Accolade1: %VALUE %UNIT", 10, 
				"PopUp Message of Accolade1: %VALUE %UNIT", 1, "Unit1", "Tag1", 
				"Path1").setID(1).setValue(100);
		a2 = new Accolade("Name2", "String of Accolade2: %VALUE %UNIT", 20, 
				"PopUp Message of Accolade2: %VALUE %UNIT", 2, "Unit2", "Tag2", 
				"Path2").setID(2).setValue(200);
		a3 = new Accolade("Name3", "String of Accolade3: %VALUE %UNIT", 30, 
				"PopUp Message of Accolade3: %VALUE %UNIT", 3, "Unit3", "Tag3", 
				"Path3").setID(3).setValue(300);
		a4 = new Accolade("Name4", "String of Accolade4: %VALUE %UNIT", 40, 
				"PopUp Message of Accolade4: %VALUE %UNIT", 4, "Unit4", "Tag4", 
				"Path4").setID(4).setValue(400);
		a5 = new Accolade("Name5", "String of Accolade5: %VALUE %UNIT", 50, 
				"PopUp Message of Accolade5: %VALUE %UNIT", 5, "Unit5", "Tag5", 
				"Path5").setID(5).setValue(500);
	}
	
	@Test
	/**
	 * Test 
	 * 
	 * setGameID(),getGameID() 
	 * 
	 * for Accolade
	 */
	public void AccoladeTest1(){
		//CHANGED THIS TO A STRING AS PER THE NEW GETGAMEID FUNCTIONS
		a1.setGameID("game1");
		Assert.assertTrue("Accolade a1.gameID was set to \"game1\", "
				+ "a1.getGameID() returned " + a1.getGameID(),
				a1.getGameID().compareTo("game1")==0);
	}
	
	@Test
	/**
	 * Test
	 * 
	 * getID(),getValue(),getName(),getUnit(), 
	 * getModifier(),getTag(),getImagePath() 
	 * 
	 * for Accolade
	 * 
	 */
	public void AccoladeTest2() {
		Assert.assertTrue(a2.getID() == 2);
		Assert.assertTrue(a2.getValue() == 200);
		Assert.assertTrue(a2.getName().equals("Name2"));
		Assert.assertTrue("The test failed for accolade.getUnit(). "
				+ "Instead of \"Unit2\" a2.getUnit() returned " + a2.getUnit(), 
				a2.getUnit().equals("Unit2"));
		Assert.assertTrue("The test failed for accolade.getModifier(). " +
				"Instead of \"2\" a2.getModifier() returned " + a2.getModifier(),
				a2.getModifier() == 2);
		Assert.assertTrue(a2.getTag().equals("Tag2"));
		Assert.assertTrue(a2.getImagePath().equals("Path2"));
	}
	
	@Test
	/**
	 * Test 
	 * 
	 * getString(),toString() 
	 * 
	 * for Accolade
	 */
	public void AccoladeTest3(){
		String rawString = "String of Accolade3: %VALUE %UNIT";
		Assert.assertTrue(a3.getRawString().equals(rawString));
		String s = "String of Accolade3: 900 Unit3";
		Assert.assertTrue(a3.toString().equals(s));
	}
	
	@Test
	/**
	 * Test 
	 * 
	 * populateAccoladesPlayer(int playerID), populateAccoladesGame(int gameID),
	 * getGameID(),getPlayerID();
	 * 
	 * for AccoladeContainer
	 */
	public void AccoladeTest4(){
		ac1 = new AccoladeContainer();
		ac2 = new AccoladeContainer();
		ac1.populateAccoladesGame(999);
		ac2.populateAccoladesPlayer(101);
		Assert.assertTrue(ac1.getGameID() == 999);
		Assert.assertTrue(ac2.getPlayerID() == 101);
	}
	
	@Test
	/**
	 * Test 
	 * 
	 *Constructor 
	 * 
	 * for AccoladeContainer
	 */
	public void AccoladeTest5(){
		ac3 = new AccoladeContainer();
		Assert.assertTrue(ac3.isEmpty());
		Assert.assertTrue(!ac3.hasHead());
		Assert.assertTrue(!ac3.hasTail());
	}
	
	@Test
	/**
	 * Test 
	 * 
	 *addAccolade(params),add(params),size(),clearAccolades()
	 * 
	 * for AccoladeContainer
	 */
	public void AccoladeTest6(){
		ac4 = new AccoladeContainer();
		ac4.addAccolade("Name4", "String of Accolade4: %VALUE %UNIT", 40, 
				"PopUp Message of Accolade4: %VALUE %UNIT", 4, "Unit4", "Tag4", 
				"Path4");
				//.setID(4).setValue(400);
		ac4.add(a4);
		ac4.add(a5);
		Assert.assertTrue(ac4.size() == 3);
		ac4.clearAccolades();
		Assert.assertTrue(ac4.size() == 0);
	}
	
	@Test
	/**
	 * Test 
	 * 
	 *check all accolades in accolade container
	 * 
	 * for AccoladeContainer
	 */
	public void AccoladeTest7(){
		ac5 = new AccoladeContainer();
		ac5.add(a1);
		ac5.add(a2);
		ac5.add(a3);
		ac5.add(a4);
		ac5.add(a5);
		int count = 0;
		for(Accolade a : ac5){
			count++;
			Assert.assertTrue(a.getID() == count);
			Assert.assertTrue(a.getTag().equals("Tag" + count));
			
		}
	
	}

}
