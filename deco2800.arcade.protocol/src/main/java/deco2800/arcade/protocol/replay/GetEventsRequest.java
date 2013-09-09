package deco2800.arcade.protocol.replay;

import deco2800.arcade.protocol.UserRequest;

/**
 * Request all the events that occured in a particular session.
 * @author Ben Follington
 *
 */
public class GetEventsRequest extends UserRequest {

    public Integer sessionId;
    
}
