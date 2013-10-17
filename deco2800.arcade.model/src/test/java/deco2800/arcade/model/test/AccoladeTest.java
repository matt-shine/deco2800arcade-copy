package deco2800.arcade.model.test;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.model.AccoladeContainer;
import deco2800.arcade.model.Accolade;


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
		a1 = new Accolade(1,100,"Name1", "String of Accolade1", "Unit1",5,"Tag1","Path1");
		a2 = new Accolade(2,200,"Name2", "String of Accolade2", "Unit2",5,"Tag2","Path2");
		a3 = new Accolade(3,300,"Name3", "String of Accolade3", "Unit3",5,"Tag3","Path3");
		a4 = new Accolade(4,400,"Name4", "String of Accolade4", "Unit4",5,"Tag4","Path4");
		a5 = new Accolade(5,500,"Name5", "String of Accolade5", "Unit5",5,"Tag5","Path5");
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
		a1.setGameID(999);
		Assert.assertTrue(a1.getGameID() == 999);
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
		Assert.assertTrue(a2.getUnit().equals("Unit2"));
		Assert.assertTrue(a2.getModifier() == 5);
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
		String s = "";
		s = String.format(a3.getString(), a3.getModifier(), a3.getUnit());
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
		ac4.addAccolade(6, 600, "Name6", "String of Accolade6", "Unit6", 5, "Tag6", "Path6");
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
