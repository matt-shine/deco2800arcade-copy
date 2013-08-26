package deco2800.arcade.protocol.replay;

import deco2800.arcade.protocol.UserRequest;

/**
 * The request sent to transmit a new event to the server.
 * @author Ben Follington
 *
 */
public class PushEventRequest extends UserRequest {

    public String sessionId;
    public String nodeString;

}
