package deco2800.arcade.client.replay.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import deco2800.arcade.client.replay.ReplayItem;
import deco2800.arcade.client.replay.ReplayNode;

public class ReplayNodeTest {
    
    private ReplayItem r1 = new ReplayItem(123);
    private ReplayItem r2 = new ReplayItem(45.345f);
    private ReplayItem r3 = new ReplayItem("test");
    private HashMap<String, ReplayItem> items;

    @Test
    public void testGetType()
    {
        items = new HashMap<String, ReplayItem>();
        items.put("x", r1);
        items.put("y", r2);
        items.put("name", r3);
        
        ReplayNode rn = new ReplayNode("test_event", items);
        
        assertEquals("test_event", rn.getType());
    }
    
    @Test
    public void testGetItems()
    {
        items = new HashMap<String, ReplayItem>();
        items.put("x", r1);
        items.put("y", r2);
        items.put("name", r3);
        
        ReplayNode rn = new ReplayNode("test_event", items);
        
        int r1 = rn.getItemForString("x").intVal();
        float r2 = rn.getItemForString("y").floatVal();
        
        assertEquals(123, r1);
        assertEquals(45.345f, r2, 0);
        assertEquals("test", rn.getItemForString("name").stringVal());
    }
    
    @Test
    public void testGetTime()
    {
        items = new HashMap<String, ReplayItem>();
        items.put("x", r1);
        items.put("y", r2);
        items.put("name", r3);
        
        ReplayNode rn = new ReplayNode("test_event", items);
        
        assertEquals(0, rn.getTime());
    }
    
    @Test
    public void testSetTime()
    {
        items = new HashMap<String, ReplayItem>();
        items.put("x", r1);
        items.put("y", r2);
        items.put("name", r3);
        
        ReplayNode rn = new ReplayNode("test_event", items);
        
        rn.setTime(123);
        assertEquals(123, rn.getTime());
    }
    
    @Test
    public void testItems()
    {
        items = new HashMap<String, ReplayItem>();
        items.put("x", r1);
        items.put("y", r2);
        items.put("name", r3);
        
        ReplayNode rn = new ReplayNode("test_event", items);
        
        assertEquals(rn.getItems(), items);
    }
    
    @Test
    public void testSmallConstructor()
    {        
        ReplayNode rn = new ReplayNode("test_event");
        
        assertEquals(rn.getItems(), new HashMap<String, ReplayItem>());
        assertEquals(rn.getTime(), 0);
    }
    
    @Test
    public void testAlternateConstructor()
    {
        ReplayNode rn = new ReplayNode("test_event", 999);
        
        assertEquals(rn.getTime(), 999);
    }
    
    @Test
    public void testFinalConstructor()
    {
        items = new HashMap<String, ReplayItem>();
        items.put("x", r1);
        items.put("y", r2);
        items.put("name", r3);
        
        ReplayNode rn = new ReplayNode("test_event", items, 999);
        
        assertEquals(rn.getTime(), 999);
    }
    

}
