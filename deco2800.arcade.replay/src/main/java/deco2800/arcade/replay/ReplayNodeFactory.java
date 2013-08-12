package deco2800.arcade.replay;

import java.util.HashMap;
import java.util.Map;

import deco2800.arcade.replay.exception.ReplayItemDataInvalidException;

public class ReplayNodeFactory {
    
    private static Map<String, String[]> lookupEvents = new HashMap<String, String[]>();

    /**
     * Create a replay node from a registered event name.
     * @param eName
     * @param eData An array of data corresponding to registered keys, or use varargs.
     * @return
     * @throws Exception
     */
    public static ReplayNode createReplayNode(String eName, Object... eData)
            throws ReplayItemDataInvalidException
    {
        HashMap<String, ReplayItem> data = new HashMap<String, ReplayItem>();
        ReplayItem riTemp;
        
        if (lookupEvents.containsKey(eName))
        {
            for (int i = 0; i < eData.length; i++)
            {
                riTemp = new ReplayItem(eData[i]);
                data.put(lookupEvents.get(eName)[i], riTemp);
            }
            
            return new ReplayNode(eName, data);
            
        } else {
            throw new ReplayItemDataInvalidException("Event Name does not exist.");
        }
    }
    
    /**
     * Register an event type with the Node Factory.
     * @param eName The Event Name
     * @param strs List of Parameters (as array or as varargs).
     */
    public static void registerEvent(String eName, String... strs)
    {   
        lookupEvents.put(eName, strs);
    }
}
