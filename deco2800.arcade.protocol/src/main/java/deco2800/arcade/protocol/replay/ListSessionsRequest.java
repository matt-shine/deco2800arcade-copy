package deco2800.arcade.protocol.replay;

import deco2800.arcade.protocol.UserRequest;

/**
 * Request a list of all replays available, optional: provide gameId.
 * @author Ben Follington
 *
 */
public class ListSessionsRequest extends UserRequest {

    public String gameId;

}
