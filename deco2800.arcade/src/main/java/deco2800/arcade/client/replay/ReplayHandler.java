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
	private Integer lastSessionId;
	
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
	    System.out.println( "NULL 1" );
		this.lastSessionId = null;
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
	    this.playback = new ReplayPlayback( this, this.client );
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
	 * Ends the session currently being played
	 */
	public void endCurrentSession() {
		endSession( this.sessionId );
	}
	
	/**
	 * Terminate the current recording session.
	 * @param sessionId ID for the session
	 */
	public void endSession(Integer sessionId)
	{
		System.out.println( "ENDING SESSION" );
		this.lastSessionId = sessionId;
		
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
	    System.out.println("Session ended");
	    setSessionId(null); //bad
	}
	
	/**
	 * Request the list of sessions available to replay from the server.
	 * @param gameId
	 */
	public void requestSessionList(String gameId)
	{
		System.out.println( "Asked for sessions..." );
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
	    System.out.println( "Got sessions!" );
		ArrayList<Session> sessions = lsr.sessions;
	    
	    if (sessions.size() == 0)
	    {
	        System.out.println("No sessions for game.");
	        return;
	    }
	    
	    //System.out.println("Sessions for game: " + sessions.get(0).gameId + "\n {");
	    
	    StringBuilder sb = new StringBuilder();
	    
	    for (Session s : sessions)
	    {
	        //System.out.println(s.sessionId);
	        sb.append(s.sessionId);
	        sb.append(",");
	    }
	    
	    String s = sb.subSequence(0, sb.length() - 1).toString();
	    
	    ReplayNodeFactory.registerEvent("session_list", "sessions");
	    ReplayNode rn = ReplayNodeFactory.createReplayNode("session_list", s);
	    this.dispatchReplayEvent(rn.getType(), rn);
	    
	    //System.out.println("}");
	    
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
	 * 			the response sent from the server
	 */
	public void printOutServerResponse(ReplayResponse rr)
	{
		System.out.println( "Response:" );
        System.out.println(rr);
	}
	
	/**
	 * Signals the start of a recording. Note that all time offsets will be
	 * taken from the time this method is called.
	 */
	public void startRecording() {
		this.recorder.startRecording();
	}
	
	/**
	 * Signals the end of a recording, and should be called at the end of 
	 * every recording.
	 */
	public void finishRecording() {
		this.recorder.finishRecording();
	}
	
	/**
	 * Pushes a single event to the recording. Note that it will store this 
	 * data on the server, not locally, when possible. Delegates the logic
	 * to the recorder object.
	 * @param eData
	 */
	public void pushEvent( ReplayNode eData ) {
		this.recorder.pushEvent( eData );
	}
	
	/**
	 * This method should be called once during the run loop of any
	 * implementing game during playback, as it does the checking to see
	 * whether an event should be broadcast. Delegates the logic to the
	 * playback object.
	 */
	public void runLoop() {
		playback.runLoop();
	}
	
	/**
     * Set the handler to constant-time playback. Constant time refers
     * to the spacing between the events, so constant time will send one
     * event per interval ms. This is useful when, for example, it takes
     * a player a long time to think about a move and the viewer doesn't
     * want to (or shouldn't have to) wait that amount of time to see
     * the move.
     * 
     * @param interval
     * 				The spacing between events, in milliseconds
     */
    public void enableConstantTimePlayback( long interval )
    {
        playback.enableConstantTimePlayback(interval);
    }
    
    /**
     * Set the handler to real-time playback. This is the default, and
     * returns the events in the order they were added at the same 
     * relative times they were added.
     */
    public void enableRealTimePlayback()
    {
        playback.enableRealTimePlayback();
    }
	
	/**
	 * Play back a specific replay node item, by broadcasting it to all
	 * of the observers
	 * @param node
	 * 			the node to dispatch to the observers
	 */
	public void playbackItem( ReplayNode node ) {
		dispatchReplayEvent( node.getType(), node );
	}
	
	/**
	 * Play back the current session, used as a convenient helper method 
	 * for games that require immediate playback.
	 */
	public void playbackCurrentSession() {
		this.playback.playbackSession( sessionId );
	}
	
	/**
	 * Plays back the last session that was played, used as a convenient helper method 
	 * for games that require immediate playback.
	 */
	public void playbackLastSession() {
		this.playback.playbackSession( this.lastSessionId );
	}
	
	/**
	 * Start playback for a session with the given ID
	 * 
	 * @param sessionId, the ID of the session to play back
	 */
	public void playbackSession( int sessionId ) {
		this.playback.playbackSession( sessionId );
	}
	
	/**
	 * No longer used, as the game ID is now a string
	 * 
	 * @deprecated
	 * @param gameId, the ID of the game to start a session for
	 * @param username, the username of the client starting the game
	 */
	public void startSession(int gameId, String username) {
		throw new RuntimeException( "This method is no longer supported." );
	}
	
	/**
	 * No longer used, see playbackSession
	 * 
	 * @deprecated
	 * @param sessionId, the id of the session to request
	 */
	public void requestEventsForSession( int sessionId ) {
		throw new RuntimeException( "This method is no longer supported, see the playbackSession method" );
	}
	
	/*
	 * 
	 * Methods to handle the Observers (ReplayEventListeners)
	 * 
	 */
	
	/**
	 * Adds an observer
	 * @param listener
	 * 			the observer to add
	 */
	public void addReplayEventListener( ReplayEventListener listener ) {
		listenerList.add( ReplayEventListener.class, listener );
	}
	
	/**
	 * Removes an observer
	 * @param listener
	 * 			the observer to remove
	 */
	public void removeReplayEventListener( ReplayEventListener listener ) {
		listenerList.remove( ReplayEventListener.class, listener );
	}
	
	/**
	 * Sends the replay event to every ReplayEventListener registered to 
	 * receive the events. In practice, there should only be one of these, 
	 * but this allows multiple to connect as per the pattern.
	 * 
	 * @param eType
	 *  		the type of the event (eg. "piece_move")
	 * @param eData
	 * 			the ReplayNode storing the data for the event
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
