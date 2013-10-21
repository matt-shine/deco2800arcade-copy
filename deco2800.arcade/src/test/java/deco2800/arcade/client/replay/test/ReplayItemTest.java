package deco2800.arcade.client.replay.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import deco2800.arcade.client.replay.ReplayItem;
import deco2800.arcade.client.replay.exception.ReplayItemDataInvalidException;

public class ReplayItemTest {

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
    
    @Test (expected = ReplayItemDataInvalidException.class)
    public void testInvalidType()
    {
        ReplayItem ri = new ReplayItem(true);
    }
    
    @Test (expected = RuntimeException.class)
    public void testBadIntCast()
    {
        ReplayItem ri = new ReplayItem(123);
        ri.floatVal();
    }
    
    @Test (expected = RuntimeException.class)
    public void testBadFloatCast()
    {
        ReplayItem ri = new ReplayItem(123.345f);
        ri.stringVal();
    }
    
    @Test (expected = RuntimeException.class)
    public void testBadStringCast()
    {
        ReplayItem ri = new ReplayItem("Hello world!");
        ri.intVal();
    }
    
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
    
    @Test
    public void testHashCode()
    {
        ReplayItem r1 = new ReplayItem(123);
        ReplayItem r2 = new ReplayItem(123);
        
        assertEquals(true, r1.equals(r2));
        assertEquals(r1.hashCode(), r1.hashCode());
        
    }

}
