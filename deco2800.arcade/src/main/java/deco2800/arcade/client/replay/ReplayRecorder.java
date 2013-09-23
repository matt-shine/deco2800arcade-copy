package deco2800.arcade.client.replay;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.protocol.replay.PushEventRequest;
import deco2800.arcade.protocol.replay.PushEventResponse;

public class ReplayRecorder {
	
	ReplayHandler handler;

	private NetworkClient client;
	private Gson serializer;
	
	private long startTime;
	private int replayIndex;

	private Integer sessionId;
	
	
	public ReplayRecorder(ReplayHandler handler, NetworkClient client, Integer sessionId) { 
		this.startTime = -1;
		this.client = client;
		this.sessionId = sessionId;
		this.handler = handler;
	    
	    serializer = new Gson();
	    
	    GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		gsonBuilder.registerTypeAdapter(ReplayNode.class, new ReplayNodeDeserializer());
	}
	
	public void startRecording() {
		this.startTime = -1;
		this.replayIndex = 0;
	    
		this.startTime = System.currentTimeMillis();
	}
	
	/**
	 * End recording
	 */
	public void finishRecording() {
		this.startTime = -1;
		// Do something with the captured data, then reset it (or possibly allow playback etc.)
	}
	

	
	/**
	 * Send a node as a string to the server for storage
	 * @param node
	 */
	private void pushEventToServer(Integer eventIndex, String node, Integer sessionId)
	{
	    PushEventRequest per = new PushEventRequest();
	    per.eventIndex = eventIndex;
	    per.nodeString = node;
	    per.sessionId = sessionId;
	    client.sendNetworkObject(per);
	}
	
	/**
	 * Sends new event to server
	 * @param per Confirms
	 */
	public void eventPushed(PushEventResponse per)
	{
		if ( !per.success ) {
			// well then...
		}
	  //TODO Implement
	}
	
	/**
	 * Pushes nodes to server
	 * @param eData The nodes
	 */
	public void pushEvent( ReplayNode eData ) {
		ReplayNode toAdd = new ReplayNode( eData );
		if ( startTime < 0 ) {
			System.err.println( "Didn't start first" );
		}
		long timeOffset = System.currentTimeMillis() - startTime;
		toAdd.setTime( timeOffset );
		String nodeString = serializer.toJson( toAdd );
		
		System.out.println( nodeString );
		pushEventToServer( replayIndex, nodeString, sessionId );
		replayIndex ++;
		
		// probably get rid of this, kind of useless
		handler.dispatchReplayEvent( "event_pushed", null );
	}
	
}
