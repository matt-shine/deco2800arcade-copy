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
	
	private boolean recording;
	
	
	public ReplayRecorder(ReplayHandler handler, NetworkClient client, Integer sessionId) { 
		this.startTime = -1;
		this.client = client;
		this.sessionId = sessionId;
		this.handler = handler;
		
		this.recording = false;
	    
	    serializer = new Gson();
	}
	
	public boolean isRecording() {
		return this.recording;
	}
	
	public void startRecording() {
		if ( this.recording ) {
			throw new RuntimeException( "Already recording" );
		}
		
		this.startTime = -1;
		this.replayIndex = 0;
		this.recording = true;
	    
		this.startTime = System.currentTimeMillis();
	}
	
	/**
	 * End recording
	 */
	public void finishRecording() {
		if ( !this.recording ) {
			throw new RuntimeException( "Already recording" );
		}
		
		this.startTime = -1;
		this.recording = false;
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
		if ( !recording ) {
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
