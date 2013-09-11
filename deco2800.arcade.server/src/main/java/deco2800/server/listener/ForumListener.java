package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.forum.ForumTestRequest;
import deco2800.arcade.protocol.forum.ForumTestResponse;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;

/**
 * ForumListener models listener for forum's server service
 *
 */
public class ForumListener extends Listener {
	/**
	 * Execute process in response to client request
	 */
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof ForumTestRequest){
			ForumTestRequest request = (ForumTestRequest) object;
			ForumTestResponse response = new ForumTestResponse();
			int num = request.num;
			
			response.result = "Received integer is " + String.valueOf(num);
			
			connection.sendTCP(response);
		}
	}
}
