package deco2800.arcade.client.replay;

import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.*;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.replay.exception.PlaybackException;
import deco2800.arcade.protocol.replay.GetEventsRequest;
import deco2800.arcade.protocol.replay.GetEventsResponse;

public class ReplayPlayback {
	
	private long playbackStartTime = -1;
	private long nextReplayTime = -1;
	private int nextReplayIndex = -1;
	
	private final long CONSTANT_PLAYBACK_DEFAULT_INTERVAL = 1000;
	
	private long constantTimePlaybackInterval =
	                                         CONSTANT_PLAYBACK_DEFAULT_INTERVAL;
	private long lastPlaybackTime;
	private boolean constantTimePlayback = false;
	
	private NetworkClient client;
	
	private ReplayHandler handler;

	private List<String> replayHistory;
	
	private Gson deserializer;

    private Logger logger = LoggerFactory.getLogger( ReplayHandler.class );
    
	public ReplayPlayback( ReplayHandler handler, NetworkClient client ) {
		this.client = client;
		this.handler = handler;

        PropertyConfigurator.configure( "src/main/resources/replay_log4j.properties" );

	    GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		gsonBuilder.registerTypeAdapter(ReplayNode.class, new ReplayNodeDeserializer());
		deserializer = gsonBuilder.create();
	}
	
	/**
	 * Set the handler to constant-time playback
	 * @param interval The spacing between events
	 */
	public void enableConstantTimePlayback(long interval)
	{
	    constantTimePlayback = true;
	    constantTimePlaybackInterval = interval;
	}
	
	/**
	 * Set the handler to real-time playback.
	 */
	public void enableRealTimePlayback()
	{
	    constantTimePlayback = false;
	    constantTimePlaybackInterval = CONSTANT_PLAYBACK_DEFAULT_INTERVAL;
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
		logger.info( "Playing back session " + sessionId + "." );
		requestEventsForSession( sessionId );
	}
	
	/**
	 * Starts playback of game
	 */
	private void startPlayback() {
		if ( this.replayHistory == null ) {
			throw new PlaybackException( "Started replay with no data" );
		}
		playbackStartTime = System.currentTimeMillis();
		lastPlaybackTime = playbackStartTime;
		this.nextReplayIndex = 0;
		
		ReplayNode next = deserializer.fromJson(
				replayHistory.get( this.nextReplayIndex ),
				ReplayNode.class );
		
		this.nextReplayTime = playbackStartTime + next.getTime();
		
	}

	private void runLoopRealTime()
	{   
	    if ( System.currentTimeMillis() > this.nextReplayTime ) {
	        playNextNode();
        }   
	}
	
	private void runLoopConstantTime()
	{
       if ( System.currentTimeMillis() > lastPlaybackTime + 
                                         constantTimePlaybackInterval ) {
            playNextNode();
            lastPlaybackTime = lastPlaybackTime + 
                               constantTimePlaybackInterval;
        }      
	}
	
	/**
	 * Handle the dispatch of the next event.
	 */
	private void playNextNode()
	{
        if ( this.nextReplayIndex < 0 ) {
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
	
	/**
	 * Called once per frame in a game, handles updating and dispatching of 
	 * events.
	 */
	public void runLoop() {
	
	    if (constantTimePlayback)
	    {
	        runLoopConstantTime();
	    } else {
	        runLoopRealTime();
	    }
		
	}
	
}
