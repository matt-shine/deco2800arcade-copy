package deco2800.arcade.protocol.replay;

import java.util.List;

/**
 * The list of all events in a session, returned upon request.
 * @author Ben Follington
 *
 */
public class GetEventsResponse {

    public List<String> nodes;
    public Integer serverOffset;
    
}
