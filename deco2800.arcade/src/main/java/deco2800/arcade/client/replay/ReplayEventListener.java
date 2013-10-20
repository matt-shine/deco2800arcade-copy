package deco2800.arcade.client.replay;

import java.util.EventListener;

public interface ReplayEventListener extends EventListener {
	void replayEventReceived( String eType, ReplayNode eData );
}