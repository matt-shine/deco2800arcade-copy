package deco2800.arcade.client.replay;

import javax.swing.event.EventListenerList;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.protocol.replay.ReplayRequest;
import deco2800.arcade.protocol.replay.ReplayResponse;

import java.util.*;

public class ReplayHandler {

	protected EventListenerList listenerList = new EventListenerList();
	
	private long startTime;
	private List<ReplayNode> replayHistory;
	private NetworkClient client;
	
	public ReplayHandler(NetworkClient client)
	{
	    this.client = client;
	    init();
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
	    this.replayHistory = new ArrayList<ReplayNode>();
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
        System.out.println(rr.test);
	}
	
	public void startRecording() {
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
		replayHistory.add( toAdd );
		dispatchReplayEvent( "event_pushed", toAdd );
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
		this.replayHistory = new ArrayList<ReplayNode>();
		dispatchReplayEvent( "replay_reset", null );
	}
	
	
	public void startPlayback() {
		playbackItem( 0, 0 );
	}
	
	private void playbackItem( final int index, long lastNodeTime ) {
		final ReplayNode node = replayHistory.get( index );
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
		            	dispatchReplayEvent( node.getType(), node.getItems() );
		            	
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
	
	private void dispatchReplayEvent( String eType, Object eData  ) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++ ) {
			if ( listeners[i] == ReplayEventListener.class ) {
				( ( ReplayEventListener ) listeners[i+1] ).replayEventReceived( eType, eData );
			}
		}
	}
}
