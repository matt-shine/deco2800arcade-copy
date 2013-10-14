package deco2800.arcade.protocol.replay;

import deco2800.arcade.protocol.UserRequest;

/**
 * Sent when the client is attempting to start a recording session.
 * @author Ben Follington
 *
 */
public class StartSessionRequest extends UserRequest {

    public String gameId;
    public String username;

}
