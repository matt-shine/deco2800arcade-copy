package deco2800.arcade.protocol.replay;

import deco2800.arcade.protocol.UserRequest;

/**
 * Sent when a game is ending the recording session.
 * @author Ben Follington
 *
 */
public class EndSessionRequest extends UserRequest {

    public Integer sessionId;

}
