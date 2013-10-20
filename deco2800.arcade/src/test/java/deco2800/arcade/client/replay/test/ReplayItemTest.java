package deco2800.arcade.client.replay.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import deco2800.arcade.client.replay.ReplayItem;
import deco2800.arcade.client.replay.exception.ReplayItemDataInvalidException;

public class ReplayItemTest {

	/**
	 * Test type is correctly assigned and retrieved
	 */
    @Test
    public void testTypes()
    {
        ReplayItem ri = new ReplayItem(123);
        assertEquals(new Integer(123), ri.intVal());
        assertEquals(new Integer(ReplayItem.TYPE_INTEGER), ri.getType());
        
        ri = new ReplayItem(5.0234f);
        assertEquals(new Float(5.0234f), ri.floatVal());
        assertEquals(new Integer(ReplayItem.TYPE_FLOAT), ri.getType());
        
        ri = new ReplayItem("This is a string");
        assertEquals("This is a string", ri.stringVal());
        assertEquals(new Integer(ReplayItem.TYPE_STRING), ri.getType());
    }
    
    /**
     * Test providing the default constructor with an invalid type (boolean)
     */
    @Test (expected = ReplayItemDataInvalidException.class)
    public void testInvalidType()
    {
        ReplayItem ri = new ReplayItem(true);
    }
    
    /**
     * Test retrieving the value of an item with the
     * wrong getter, when the item is of type int
     */
    @Test (expected = RuntimeException.class)
    public void testBadIntCast()
    {
        ReplayItem ri = new ReplayItem(123);
        ri.floatVal();
    }
    
    /**
     * Another test for retrieving the wrong type, 
     * this time for a float ReplayItem
     */
    @Test (expected = RuntimeException.class)
    public void testBadFloatCast()
    {
        ReplayItem ri = new ReplayItem(123.345f);
        ri.stringVal();
    }
    
    /**
     * Another test for retrieving the wrong type, 
     * this time for a string ReplayItem
     */
    @Test (expected = RuntimeException.class)
    public void testBadStringCast()
    {
        ReplayItem ri = new ReplayItem("Hello world!");
        ri.intVal();
    }
    
    /**
     * Test the toString method of a ReplayNode with each
     * possible type
     */
    @Test
    public void testToString()
    {
        ReplayItem ri = new ReplayItem(123);
        assertEquals("123", ri.toString());
        
        ri = new ReplayItem(5.0234f);
        assertEquals("5.0234", ri.toString());
        
        ri = new ReplayItem("This is a string");
        assertEquals("This is a string", ri.toString());   
    }
    
    /**
     * Test that two equal ReplayItems have equal hashcodes
     */
    @Test
    public void testHashCode()
    {
        ReplayItem r1 = new ReplayItem(123);
        ReplayItem r2 = new ReplayItem(123);
        
        assertEquals(true, r1.equals(r2));
        assertEquals(r1.hashCode(), r1.hashCode());
    }
    
    /**
     * Test that the equals method returns true for two 
     * equal nodes of all possible types
     */
    @Test
    public void testEquals()
    {
        ReplayItem r1 = new ReplayItem(123);
        ReplayItem r2 = new ReplayItem(123);
        
        assertEquals(true, r1.equals(r2));
        
        r1 = new ReplayItem(123);
        r2 = new ReplayItem(456);
        
        assertEquals(false, r1.equals(r2));
        
        r1 = new ReplayItem(123.0f);
        r2 = new ReplayItem(123.0f);
        
        assertEquals(true, r1.equals(r2));
        
        r1 = new ReplayItem(123.0f);
        r2 = new ReplayItem(456.0f);
        
        assertEquals(false, r1.equals(r2));

        r1 = new ReplayItem("123");
        r2 = new ReplayItem("123");
        
        assertEquals(true, r1.equals(r2));
        
        r1 = new ReplayItem("456");
        r2 = new ReplayItem("123");
        
        assertEquals(false, r1.equals(r2));
        
        r1 = new ReplayItem(123);
        r2 = new ReplayItem("123");
        
        assertEquals(false, r1.equals(r2));
        
        r1 = new ReplayItem(123);
        r2 = new ReplayItem(123.0f);
        
        assertEquals(false, r1.equals(r2));
    }
    
    /**
     * Test that calling equals between a ReplayItem and
     * the raw value stored in it returns false
     */
    @Test
    public void testInvalidEquals() 
    {
        ReplayItem r2 = new ReplayItem(123);
    	assertEquals( false, r2.equals( new Integer( 123 ) ) );
    }

}
