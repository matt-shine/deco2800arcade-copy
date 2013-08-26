package deco2800.arcade.client.network.listener;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.client.replay.ReplayHandler;
import deco2800.arcade.client.replay.exception.NoReplayHandlerException;
import deco2800.arcade.protocol.replay.EndSessionRequest;
import deco2800.arcade.protocol.replay.EndSessionResponse;
import deco2800.arcade.protocol.replay.GetEventsRequest;
import deco2800.arcade.protocol.replay.ListSessionsRequest;
import deco2800.arcade.protocol.replay.PushEventRequest;
import deco2800.arcade.protocol.replay.StartSessionResponse;
import deco2800.arcade.protocol.replay.demo.ReplayResponse;

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
		
		if (object instanceof ReplayResponse) {
		    ReplayResponse replayResponse = (ReplayResponse) object;
		    
		    if (replayHandler == null) throw new NoReplayHandlerException();
		    
		    replayHandler.printOutServerResponse(replayResponse);
		} else if (object instanceof StartSessionResponse)
		{
		    StartSessionResponse ssr = (StartSessionResponse) object;
		    
		    replayHandler.setSessionId(ssr.sessionId);
		} else if (object instanceof EndSessionResponse)
		{
		    EndSessionResponse esr = (EndSessionResponse) object;
		    
		    //TODO this is probably bad
		    replayHandler.setSessionId(null);
		    
		} else if (object instanceof ListSessionsRequest)
		{
		    ListSessionsRequest lsr = (ListSessionsRequest) object;
		} else if (object instanceof PushEventRequest)
		{
		    PushEventRequest per = (PushEventRequest) object;
		} else if (object instanceof GetEventsRequest)
		{
		    GetEventsRequest ger = (GetEventsRequest) object;
		}
	}

	
}
