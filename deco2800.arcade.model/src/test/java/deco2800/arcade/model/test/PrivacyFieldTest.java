package deco2800.arcade.model.test;

import org.junit.Assert;
import org.junit.Test;

import deco2800.arcade.model.PrivacyField;

public class PrivacyFieldTest {
	
	@Test
	public void FieldTest1(){
		PrivacyField f = new PrivacyField(1, true);
		Assert.assertTrue(f.getID() == 1);
		Assert.assertTrue(f.getValue());
		
		f.setValue(false);
		Assert.assertTrue(!f.getValue());
		
		
	}

}
