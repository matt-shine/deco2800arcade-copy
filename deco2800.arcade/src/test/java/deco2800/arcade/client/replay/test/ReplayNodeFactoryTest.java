package deco2800.arcade.client.replay.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import deco2800.arcade.client.replay.ReplayItem;
import deco2800.arcade.client.replay.ReplayNode;
import deco2800.arcade.client.replay.ReplayNodeFactory;
import deco2800.arcade.client.replay.exception.ReplayItemDataInvalidException;

public class ReplayNodeFactoryTest {
    
    private ReplayItem r1 = new ReplayItem(123);
    private ReplayItem r2 = new ReplayItem(45.345f);
    private ReplayItem r3 = new ReplayItem("test");
    private HashMap<String, ReplayItem> items;

    /**
     * Test that ReplayNodeFactory returns an identical ReplayNode to the
     * traditional syntax.
     */
    @Test
    public void testFactory()
    {
        items = new HashMap<String, ReplayItem>();
        items.put("x", r1);
        items.put("y", r2);
        items.put("name", r3);
        
        ReplayNode rn = new ReplayNode("test_event", items);
        
        ReplayNodeFactory.registerEvent("test_event", "x", "y", "name");
        ReplayNode factoryCreated = ReplayNodeFactory.createReplayNode(
                                                                "test_event",
                                                                123,
                                                                45.345f,
                                                                "test");
        
        assertEquals(rn.toString(), factoryCreated.toString());
    
    }
    
    /**
     * Ensure that attempting to create a node type that has not been registered
     * throws the correct exception.
     */
    @Test (expected = ReplayItemDataInvalidException.class)
    public void testUnregisteredName()
    {
        ReplayNodeFactory.createReplayNode("invalid", 123, 45.345f, "test");
    }
    
    
    

}
