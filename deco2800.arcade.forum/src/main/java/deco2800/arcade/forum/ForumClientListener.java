package deco2800.arcade.forum;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.forum.ForumTestResponse;

/**
 * This implements kryonet.Listener for client-side listener for Forum
 * @author Junya
 *
 */
public class ForumClientListener extends Listener {
	@Override
	public void received(Connection con, Object object) {
		super.received(con, object);
		
		/* Do action based on object type */
		if (object instanceof ForumTestResponse) {
			ForumTestResponse response = (ForumTestResponse) object;
			System.out.println(response.result);
		}
	}
}
