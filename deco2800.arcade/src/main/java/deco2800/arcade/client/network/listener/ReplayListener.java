package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.client.replay.ReplayHandler;
import deco2800.arcade.protocol.replay.EndSessionResponse;
import deco2800.arcade.protocol.replay.GetEventsResponse;
import deco2800.arcade.protocol.replay.ListSessionsResponse;
import deco2800.arcade.protocol.replay.PushEventResponse;
import deco2800.arcade.protocol.replay.StartSessionResponse;

public class ReplayListener extends NetworkListener {
    
    private ReplayHandler replayHandler = null;
    
    public ReplayListener (ReplayHandler handler)
    {
        this.replayHandler = handler;
    }
    
    public ReplayListener ()
    {
        
    }
    
    public void setHandler(ReplayHandler rh)
    {
        replayHandler = rh;
    }
    
	@Override
	public void connected(Connection connection) {
		super.connected(connection);
	}

	@Override
	public void disconnected(Connection connection) {
		super.disconnected(connection);
	}

	@Override
	public void idle(Connection connection) {
		super.idle(connection);
	}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		
		if (object instanceof StartSessionResponse)
		{
		    StartSessionResponse ssr = (StartSessionResponse) object;
		    
		    replayHandler.sessionStarted(ssr);
		} else if (object instanceof EndSessionResponse)
		{
		    EndSessionResponse esr = (EndSessionResponse) object;

		    replayHandler.sessionEnded(esr);
		    
		} else if (object instanceof ListSessionsResponse)
		{
		    ListSessionsResponse lsr = (ListSessionsResponse) object;
		    
		    replayHandler.sessionListReceived(lsr);
		} else if (object instanceof PushEventResponse)
		{
		    PushEventResponse per = (PushEventResponse) object;
		    
		    replayHandler.eventPushed(per);
		} else if (object instanceof GetEventsResponse)
		{
		    GetEventsResponse ger = (GetEventsResponse) object;
		    
		    replayHandler.eventsForSessionReceived(ger);
		}
	}

	
}
