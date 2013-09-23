package deco2800.arcade.client.replay;

import javax.swing.event.EventListenerList;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.protocol.replay.EndSessionRequest;
import deco2800.arcade.protocol.replay.EndSessionResponse;
import deco2800.arcade.protocol.replay.ListSessionsRequest;
import deco2800.arcade.protocol.replay.ListSessionsResponse;
import deco2800.arcade.protocol.replay.StartSessionRequest;
import deco2800.arcade.protocol.replay.StartSessionResponse;
import deco2800.arcade.protocol.replay.demo.ReplayRequest;
import deco2800.arcade.protocol.replay.demo.ReplayResponse;
import deco2800.arcade.protocol.replay.types.Session;

import java.util.*;

/**
 * An extension for the arcade client for recording and replaying games.
 * 
 */
public class ReplayHandler {
	protected EventListenerList listenerList = new EventListenerList();
	
	private NetworkClient client;
	
	private Integer sessionId;
	
	ReplayRecorder recorder;
	ReplayPlayback playback;
	
	/**
	 * Basic constructor for the ReplayHandler
	 * 
	 * @param client The network client for sending/receiving messages to/from the server
	 */
	public ReplayHandler(NetworkClient client)
	{
	    setClient(client);
	    init();
	}
	
	/**
	 * Sets the instance variables for the client
	 * @param client
	 */
	private void setClient(NetworkClient client)
	{
	    this.client = client;
	}
	
	/**
	 * Set up the default variables.
	 */
	private void init()
	{	
	    playback = new ReplayPlayback( this, this.client );
	}
	
	public ReplayRecorder getRecorder() {
		return this.recorder;
	}
	
	public ReplayPlayback getPlayback() {
		return this.playback;
	}
	
	/**
	 * Start a recording session with the server.
	 * @param gameId The id for the recorded game
	 * @param username The username of the user recording the game
	 */
	public void startSession(String gameId, String username)
	{
		System.out.println( "Trying to start session" );
	    StartSessionRequest ssr = new StartSessionRequest();
	    ssr.gameId = gameId;
	    ssr.username = username;
	    client.sendNetworkObject(ssr);
	}
	
	/**
	 * Sets the session ID
	 * @param ssr The session
	 */
	public void sessionStarted(StartSessionResponse ssr)
	{
	    //TODO Implement
	    setSessionId(ssr.sessionId);

		System.out.println( "Session started" );
	    
		recorder = new ReplayRecorder( this, this.client, this.sessionId );
	}
	
	/**
	 * Terminate the current recording session.
	 * @param sessionId ID for the session
	 */
	public void endSession(Integer sessionId)
	{
	    EndSessionRequest esr = new EndSessionRequest();
	    esr.sessionId = sessionId;
	    client.sendNetworkObject(esr);
	}
	
	/**
	 * Finish the session
	 * @param esr Confirm session ended
	 */
	public void sessionEnded(EndSessionResponse esr)
	{
	    //TODO Implement
	    System.out.println("Session ended");
	    setSessionId(null); //bad
	}
	
	/**
	 * Request the list of sessions available to replay from the server.
	 * @param gameId
	 */
	public void requestSessionList(String gameId)
	{
	    ListSessionsRequest lsr = new ListSessionsRequest();
	    lsr.gameId = gameId;
	    client.sendNetworkObject(lsr);
	}
	
	/**
	 * Requests list for sessions
	 * @param lsr List of session responses
	 */
	public void sessionListReceived(ListSessionsResponse lsr)
	{
	    ArrayList<Session> sessions = lsr.sessions;
	    
	    if (sessions.size() == 0)
	    {
	        System.out.println("No sessions for game.");
	        return;
	    }
	    
	    System.out.println("Sessions for game: " + sessions.get(0).gameId + "\n {");
	    
	    for (Session s : sessions)
	    {
	        System.out.println(s.sessionId);
	    }
	    
	    System.out.println("}");
	    
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
	
	/**
	 * Retrieves the session ID
	 * @return The session ID
	 */
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
	
	/**
	 * Start recording game
	 */
	public void startRecording() {
		
		this.recorder.startRecording();
	}
	
	/**
	 * End recording
	 */
	public void finishRecording() {
		this.recorder.finishRecording();
	}
	
	public void pushEvent( ReplayNode eData ) {
		this.recorder.pushEvent( eData );
	}
	
	/**
	 * Runs through the nodes and plays them
	 */
	public void runLoop() {
		playback.runLoop();
	}
	
	/**
	 * Deserializes the node
	 * @param index Particular history
	 */
	public void playbackItem( ReplayNode node ) {
//		ReplayNode node = deserializer.fromJson(
//				replayHistory.get( index ),
//				ReplayNode.class );
		
		dispatchReplayEvent( node.getType(), node );
	}
	
	/*
	 * This is temporary, will probably remove it later. Just for the demo.
	 */
	public void playbackCurrentSession() {
		this.playback.playbackSession( sessionId );
		//requestEventsForSession( sessionId );
	}
	
	/**
	 * Waits for replay to be added to a list
	 * @param listener
	 */
	public void addReplayEventListener( ReplayEventListener listener ) {
		listenerList.add( ReplayEventListener.class, listener );
	}
	
	/**
	 * Waits for replay to be removed from the list
	 * @param listener
	 */
	public void removeReplayEventListener( ReplayEventListener listener ) {
		listenerList.remove( ReplayEventListener.class, listener );
	}
	
	/**
	 * Sends the information 
	 * @param eType
	 * @param eData
	 */
	void dispatchReplayEvent( String eType, ReplayNode eData  ) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i++ ) {
			if ( listeners[i] == ReplayEventListener.class ) {
				( ( ReplayEventListener ) listeners[i+1] ).replayEventReceived( eType, eData );
			}
		}
	}
}
