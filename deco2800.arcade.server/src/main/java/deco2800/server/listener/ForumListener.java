package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.model.forum.*;
import deco2800.arcade.protocol.forum.*;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.ForumStorage;


/**
 * ForumListener models listener for forum's server service
 */
public class ForumListener extends Listener {
	/**
	 * TODO Add more listener actions
	 */
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
		} else if (object instanceof LoginRequest) {
			/* Try to login and send back the result */
			//LoginRequest request = (LoginRequest) object;
			LoginResponse response = new LoginResponse();
			try {
				response.result = false;
				response.error = "Not implemented";
			} catch (Exception e) {
				response.error = "Exception :" + e.getMessage();
			} finally {
				connection.sendTCP(response);
			}
		} else if (object instanceof TagsRequest) {
			/* Get String[] of tags */
			TagsRequest request = (TagsRequest) object;
			TagsResponse response = new TagsResponse();
			try {
				response.result = ArcadeServer.instance().getForumStorage().getAllTags(request.limit);
			} catch (DatabaseException e) {
				response.error = e.getMessage();
			} finally {
				connection.sendTCP(response);
			}
		} else if (object instanceof ParentThreadRequest) {
			/* Reply ParentThread instance from given pid 
			 * Set error for no parent thread is found or DatabaseException
			 */
			ParentThreadRequest request = (ParentThreadRequest) object;
			ParentThreadResponse response = new ParentThreadResponse();
			try {
				/* Retrieve parent thread */
				ParentThread pThread = ArcadeServer.instance().getForumStorage().getParentThread(request.pid);

				if (pThread == null) {
					response.error = "No result";
				} else {
					response.pThread = pThread;
				}
			} catch (DatabaseException e) {
				e.printStackTrace();
				response.error = "DatabaseException occured: " + e.getMessage();
			} finally {
				connection.sendTCP(response);
			}
		} else if (object instanceof DeleteRequest) {
			/* Delete parent_thread record for given pid, and sends error if occur */
			DeleteRequest request = (DeleteRequest) object;
			DeleteResponse response = new DeleteResponse();
			response.error = "";
			try {
				ArcadeServer.instance().getForumStorage().deleteParentThread(request.pid);
			} catch (DatabaseException e) {
				response.error = e.getMessage();
			} finally {
				connection.sendTCP(response);
			}
		}
	}
}
