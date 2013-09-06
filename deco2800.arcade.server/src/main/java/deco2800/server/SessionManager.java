package deco2800.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to track session objects and associate them with a session id that is
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

	public SessionManager() {
		// stub
	}

	public Integer add(Session session) {
		this.connectedSessions.put(nextSessionId, session);

		// Return session ID
		return nextSessionId++;
	}

	public Session getSession(Integer sessionId) {
		return connectedSessions.get(sessionId);
	}

	public Boolean hasSession(Integer sessionId) {
		return connectedSessions.containsKey(sessionId);
	}
}
