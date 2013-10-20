package deco2800.arcade.userui.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import deco2800.arcade.userui.Model;

public class StatusTest {
	
	private Model theModel;
	
	String awaystatus;
	String busystatus;
	String onlinestatus;
	String offlinestatus;

	
	@Before
	public void initialise() {
		
		theModel = new Model();
		
	}
	
	@Test
	public void useronline() {
		
		theModel.setStatus("online");
		onlinestatus = "online";
		assertEquals(onlinestatus, theModel.getStatus());		
		
	}
	
	@Test
	public void useroffline() {
		
		theModel.setStatus("offline");
		offlinestatus = "offline";
		assertEquals(offlinestatus, theModel.getStatus());		
		
	}
	
	@Test
	public void useraway() {
		
		theModel.setStatus("away");
		awaystatus = "away";
		assertEquals(awaystatus, theModel.getStatus());		
		
	}
	
	@Test
	public void userbusy() {
		
		theModel.setStatus("busy");
		busystatus = "busy";
		assertEquals(busystatus, theModel.getStatus());		
		
	}

}
