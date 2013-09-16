package deco2800.arcade.protocol.achievement;

import deco2800.arcade.protocol.UserRequest;

public class IncrementProgressResponse extends UserRequest {
    public int newProgress;
    public String achievementID;
}
