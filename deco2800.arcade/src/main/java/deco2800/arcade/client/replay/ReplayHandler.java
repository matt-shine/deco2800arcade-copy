package deco2800.arcade.client.replay;

import javax.swing.event.EventListenerList;

import com.google.gson.*;
//import com.sun.tools.example.debug.bdi.SessionListener;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.protocol.replay.EndSessionRequest;
import deco2800.arcade.protocol.replay.EndSessionResponse;
import deco2800.arcade.protocol.replay.GetEventsRequest;
import deco2800.arcade.protocol.replay.GetEventsResponse;
import deco2800.arcade.protocol.replay.ListSessionsRequest;
import deco2800.arcade.protocol.replay.ListSessionsResponse;
import deco2800.arcade.protocol.replay.PushEventRequest;
import deco2800.arcade.protocol.replay.PushEventResponse;
import deco2800.arcade.protocol.replay.StartSessionRequest;
import deco2800.arcade.protocol.replay.StartSessionResponse;
import deco2800.arcade.protocol.replay.demo.ReplayRequest;
import deco2800.arcade.protocol.replay.demo.ReplayResponse;

import java.util.*;

public class ReplayHandler {

	protected EventListenerList listenerList = new EventListenerList();
	
	private long startTime;
	private List<String> replayHistory;
	private NetworkClient client;
	private Gson serializer;
	private Gson deserializer;
	private Integer sessionId;
	
	private long playbackStartTime = -1;
	private long nextReplayTime = -1;
	private int nextReplayIndex = -1;
	
	
	public ReplayHandler(NetworkClient client)
	{
	    setClient(client);
	    init();
	}
	
	public void setClient(NetworkClient client)
	{
	    this.client = client;
	}
	
	public ReplayHandler() {
	    init();
	}
	
	/**
	 * Set up the default variables.
	 */
	private void init()
	{
	    this.startTime = -1;
	    this.replayHistory = new ArrayList<String>();
	    
	    serializer = new Gson();
	    
	    GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		gsonBuilder.registerTypeAdapter(ReplayNode.class, new ReplayNodeDeserializer());
		deserializer = gsonBuilder.create();
	}
	
	/**
	 * Start a recording session with the server.
	 * @param gameId
	 * @param username
	 */
	public void startSession(Integer gameId, String username)
	{
	    StartSessionRequest ssr = new StartSessionRequest();
	    ssr.gameId = gameId;
	    ssr.username = username;
	    client.sendNetworkObject(ssr);
	}
	
	public void sessionStarted(StartSessionResponse ssr)
	{
	    //TODO Implement
	    setSessionId(ssr.sessionId);
	}
	
	/**
	 * Terminate the current recording session.
	 * @param sessionId
	 */
	public void endSession(Integer sessionId)
	{
	    EndSessionRequest esr = new EndSessionRequest();
	    esr.sessionId = sessionId;
	    client.sendNetworkObject(esr);
	}
	
	public void sessionEnded(EndSessionResponse esr)
	{
	    //TODO Implement
	    setSessionId(null); //bad
	}
	
	/**
	 * Request the list of sessions available to replay from the server.
	 * @param gameId
	 */
	public void requestSessionList(Integer gameId)
	{
	    ListSessionsRequest lsr = new ListSessionsRequest();
	    lsr.gameId = gameId;
	    client.sendNetworkObject(lsr);
	}
	
	public void sessionListReceived(ListSessionsResponse lsr)
	{
	  //TODO Implement
	}
	
	/**
	 * Send a node as a string to the server for storage
	 * @param node
	 */
	public void pushEventToServer(String node, Integer sessionId)
	{
	    PushEventRequest per = new PushEventRequest();
	    per.nodeString = node;
	    per.sessionId = sessionId;
	    client.sendNetworkObject(per);
	}
	
	public void eventPushed(PushEventResponse per)
	{
	  //TODO Implement
	}
	
	/**
	 * Request all the events for a given session.
	 * @param sessionId
	 */
	public void requestEventsForSession(Integer sessionId)
	{
	    GetEventsRequest ger = new GetEventsRequest();
	    ger.sessionId = sessionId;
	    client.sendNetworkObject(ger);
	}
	
	public void eventsForSessionReceived(GetEventsResponse ger)
	{
	  //TODO Implement
	}
	
	/**
	 * Set the handler's session id.
	 * @param id
	 */
	public void setSessionId(Integer id)
	{
	    sessionId = id;
	    System.out.println("Session Id: " + sessionId);
	}
	
	public Integer getSessionId()
	{
	    return sessionId;
	}
	
	/**
	 * Send a simple ping message to the server.
	 */
	public void sendSimpleMessageToServer()
	{
	    ReplayRequest rr = new ReplayRequest();
	    rr.random = Math.random();
	    client.sendNetworkObject(rr);
	}
	
	/**
	 * A simple method for demonstrating the networking flow.
	 * @param rr
	 */
	public void printOutServerResponse(ReplayResponse rr)
	{
		System.out.println( "Response:" );
        System.out.println(rr);
	}
	
	public void startRecording() {
		this.startTime = -1;
	    this.replayHistory = new ArrayList<String>();
	    
		this.startTime = System.currentTimeMillis();
	}
	
	public void finishRecording() {
		this.startTime = -1;
		// Do something with the captured data, then reset it (or possibly allow playback etc.)
	}
	
	public void pushEvent( ReplayNode eData ) {
		ReplayNode toAdd = new ReplayNode( eData );
		if ( startTime < 0 ) {
			System.err.println( "Didn't start first" );
		}
		long timeOffset = System.currentTimeMillis() - startTime;
		toAdd.setTime( timeOffset );
		replayHistory.add( serializer.toJson( toAdd ) );
		// probably get rid of this, kind of useless
		dispatchReplayEvent( "event_pushed", null );
	}
	
	/*
	public void pushEvent( String eType, List<ReplayItem> eData ) {
		List<ReplayItem> items = new ArrayList<ReplayItem>();
		for ( ReplayItem item: eData ) {
			items.add( new ReplayItem( item.getName(), item.getData() ) );
		}
		if ( startTime < 0 ) {
			System.out.println( "Didn't start first" );
		}
		long timeOffset = System.currentTimeMillis() - startTime;
		ReplayNode node = new ReplayNode( eType, items, timeOffset );
		
		replayHistory.add( node );
		dispatchReplayEvent( "event_pushed", node );
	}
	*/
	
	/*
	 * Problems will come from people adding mutable eData
	 * Only way I can think of at the moment is to make different
	 * functions taking the common data types for people to use, 
	 * eg String, List, Map etc (and for the List and Map perform
	 * a one level deep copy, throwing an error if the type stored
	 * within the List or Map is mutable [can check for inheritance
	 * from Object?])
	 */
	/*
	public void pushEvent( String eType, String fieldName, Object eData ) {
		List<ReplayItem> items = new ArrayList<ReplayItem>();
		items.add( new ReplayItem( fieldName, eData ) );
		pushEvent( eType, items );
	}
	*/
	
	public void resetReplayHistory() {
		this.replayHistory = new ArrayList<String>();
		dispatchReplayEvent( "replay_reset", null );
	}
	
	public void runLoop() {
		if ( System.currentTimeMillis() > this.nextReplayTime ) {
			if ( this.nextReplayIndex < 0 ) {
				// NOPE
				return;
			}
			playbackItem( this.nextReplayIndex );
			
			this.nextReplayIndex ++;
			if ( this.nextReplayIndex >= this.replayHistory.size() ) {
				this.nextReplayIndex = -1;
				this.nextReplayTime = -1;
				
				dispatchReplayEvent( "playback_complete", null );
			} else {
				ReplayNode next = deserializer.fromJson(
						replayHistory.get( this.nextReplayIndex ),
						ReplayNode.class );
				
				this.nextReplayTime = playbackStartTime + next.getTime();
			}
		}
	}
	
	private void playbackItem( final int index ) {
		ReplayNode node = deserializer.fromJson(
				replayHistory.get( index ),
				ReplayNode.class );
		
		dispatchReplayEvent( node.getType(), node );
	}
	
	
	public void startPlayback() {
		playbackStartTime = System.currentTimeMillis();
		this.nextReplayIndex = 0;
		
		ReplayNode next = deserializer.fromJson(
				replayHistory.get( this.nextReplayIndex ),
				ReplayNode.class );
		
		this.nextReplayTime = playbackStartTime + next.getTime();
		
		//playbackItem( 0, 0 );
		//playbackItem();
	}
	
	private void playbackItem( final int index, long lastNodeTime ) {
		final ReplayNode node = deserializer.fromJson(
				replayHistory.get( index ),
				ReplayNode.class );
		long nodeTimeVal = node.getTime();
		if ( nodeTimeVal == 0 ) {
			System.err.println( "what" );
			nodeTimeVal = lastNodeTime;
		} 
		final long nodeTime = nodeTimeVal;
		
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	dispatchReplayEvent( node.getType(), node );
		            	if ( index < replayHistory.size()-1 ) {
		            		playbackItem( index+1, nodeTime );
		            	} else {
		            		dispatchReplayEvent( "playback_finished", null );
		            	}
		            }
		        }, 
		        nodeTime - lastNodeTime
		);
	}

	public void addReplayEventListener( ReplayEventListener listener ) {
		listenerList.add( ReplayEventListener.class, listener );
	}
	public void removeReplayEventListener( ReplayEventListener listener ) {
		listenerList.remove( ReplayEventListener.class, listener );
	}
	
	private void dispatchReplayEvent( String eType, ReplayNode eData  ) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++ ) {
			if ( listeners[i] == ReplayEventListener.class ) {
				( ( ReplayEventListener ) listeners[i+1] ).replayEventReceived( eType, eData );
			}
		}
	}
}
