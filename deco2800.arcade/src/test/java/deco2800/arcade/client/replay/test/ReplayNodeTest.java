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
    private HashMap<String, ReplayItem> items = new HashMap<String, ReplayItem>();

    @Test
    public void testGetType()
    {
        items.put("x", r1);
        items.put("y", r2);
        items.put("name", r3);
        
        ReplayNode rn = new ReplayNode("test_event", items);
        
        assertEquals("test_event", rn.getType());
    }
    
    @Test
    public void testGetItems()
    {
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

}
