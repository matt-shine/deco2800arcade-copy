package deco2800.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to track {@link Session}'s and associate them with a session id that is
 * unique to the connected client.
 * 
 * @author Team Mashup
 * @see Session
 * 
 */
public class SessionManager {
	private Map<Integer, Session> connectedSessions = new HashMap<Integer, Session>();

	// FIXME will hit MAX_INT and die
	private Integer nextSessionId = 0;

	/**
	 * Initialise the session manager
	 */
	public SessionManager() {
		// stub
	}

	/**
	 * Adds a {@link Session} to the session manager
	 * @param session
	 * @return Next session id
	 */
	public Integer add(Session session) {
		this.connectedSessions.put(nextSessionId, session);

		// Return session ID
		return nextSessionId++;
	}

	/**
	 * @param sessionId
	 * @return {@link Session} with the given session ID
	 */
	public Session getSession(Integer sessionId) {
		return connectedSessions.get(sessionId);
	}

	/**
	 * 
	 * @param sessionId
	 * @return true if the session manager contains a session with the 
	 * given session id
	 */
	public Boolean hasSession(Integer sessionId) {
		return connectedSessions.containsKey(sessionId);
	}
}
