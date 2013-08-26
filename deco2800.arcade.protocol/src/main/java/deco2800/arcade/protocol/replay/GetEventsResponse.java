package deco2800.arcade.protocol.replay;

import java.util.ArrayList;

/**
 * The list of all events in a session, returned upon request.
 * @author Ben Follington
 *
 */
public class GetEventsResponse {

    public ArrayList<String> nodes;
    public Integer serverOffset;
    
}
