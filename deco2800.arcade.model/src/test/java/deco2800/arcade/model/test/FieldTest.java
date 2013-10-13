package deco2800.arcade.model.test;

import org.junit.Assert;
import org.junit.Test;

import deco2800.arcade.model.Field;

public class FieldTest {
	
	@Test
	public void FieldTest1(){
		Field f = new Field(1, "Cat");
		Assert.assertTrue(f.getID() == 1);
		Assert.assertTrue(f.getValue() == "Cat");
		
		f.setValue("Dog");
		Assert.assertTrue(f.getValue() == "Dog");
		
		
	}

}
