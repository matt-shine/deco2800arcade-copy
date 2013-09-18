package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.forum.*;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.ForumStorage;
import deco2800.arcade.forum.*;

/**
 * ForumListener models listener for forum's server service
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
		} else if (object instanceof ParentThreadRequest) {
			/* Reply ParentThread instance from given pid 
			 * Set error for no parent thread is found or DatabaseException
			 */
			ParentThreadRequest request = (ParentThreadRequest) object;
			ParentThreadResponse response = new ParentThreadResponse();
			try {
				response.parentThread = ArcadeServer.instance().getForumStorage().getParentThread(request.pid);
				if (response.parentThread == null) {
					response.error = "No result";
				}
			} catch (DatabaseException e) {
				e.printStackTrace();
				response.error = "DatabaseException occured: " + e.getMessage();
			} finally {
				connection.sendTCP(response);
			}
		}
	}
}
