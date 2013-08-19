package deco2800.arcade.client.replay;

import java.util.EventListener;

interface ReplayEventListener extends EventListener {
	public void replayEventReceived( String eType, Object eData );
}