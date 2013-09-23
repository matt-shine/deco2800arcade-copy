package deco2800.arcade.client.replay;

import java.util.List;

import com.google.gson.*;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.protocol.replay.GetEventsRequest;
import deco2800.arcade.protocol.replay.GetEventsResponse;

public class ReplayPlayback {
	
	private long playbackStartTime = -1;
	private long nextReplayTime = -1;
	private int nextReplayIndex = -1;
	
	private NetworkClient client;
	
	private ReplayHandler handler;

	private List<String> replayHistory;
	
	private Gson deserializer;
	
	public ReplayPlayback( ReplayHandler handler, NetworkClient client ) {
		this.client = client;
		this.handler = handler;
		

	    GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		gsonBuilder.registerTypeAdapter(ReplayNode.class, new ReplayNodeDeserializer());
		deserializer = gsonBuilder.create();
	}

	/**
	 * Request all the events for a given session.
	 * @param sessionId
	 */
	public void requestEventsForSession(Integer sessionId)
	{
		this.replayHistory = null;
		
	    GetEventsRequest ger = new GetEventsRequest();
	    ger.sessionId = sessionId;
	    client.sendNetworkObject(ger);
	}
	
	/**
	 * Lists all events received in a session
	 * @param ger The list
	 */
	public void eventsForSessionReceived(GetEventsResponse ger)
	{
		replayHistory = ger.nodes;
		startPlayback();
	}
	
	public void playbackSession( Integer sessionId ) {
		requestEventsForSession( sessionId );
	}
	
	/**
	 * Starts playback of game
	 */
	private void startPlayback() {
		if ( this.replayHistory == null ) {
			throw new RuntimeException( "Started replay with no data" );
		}
		playbackStartTime = System.currentTimeMillis();
		this.nextReplayIndex = 0;
		
		ReplayNode next = deserializer.fromJson(
				replayHistory.get( this.nextReplayIndex ),
				ReplayNode.class );
		
		this.nextReplayTime = playbackStartTime + next.getTime();
	}

	public void runLoop() {
		if ( System.currentTimeMillis() > this.nextReplayTime ) {
			if ( this.nextReplayIndex < 0 ) {
				// NOPE
				return;
			}
			ReplayNode node = deserializer.fromJson(
					replayHistory.get( this.nextReplayIndex  ),
					ReplayNode.class );
			
			handler.playbackItem( node );
			
			this.nextReplayIndex ++;
			if ( this.nextReplayIndex >= replayHistory.size() ) {
				this.nextReplayIndex = -1;
				this.nextReplayTime = -1;
				
				handler.dispatchReplayEvent( "playback_complete", null );
			} else {
				ReplayNode next = this.deserializer.fromJson(
						replayHistory.get( this.nextReplayIndex ),
						ReplayNode.class );
				
				this.nextReplayTime = playbackStartTime + next.getTime();
			}
		}
	}
	
}
