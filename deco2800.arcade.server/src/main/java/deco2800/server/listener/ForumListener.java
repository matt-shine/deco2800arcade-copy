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
		System.out.println("Some request is received (debug purpose)");
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
				response.result = false;
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
		} else if (object instanceof AddVoteRequest) {
			/* For both parent and child thread */
			AddVoteRequest request = (AddVoteRequest) object;
			AddVoteResponse response = new AddVoteResponse();
			response.error = "";
			try {
				ArcadeServer.instance().getForumStorage().addVote(request.value, request.threadType, request.id);
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
		} else if (object instanceof GetTaggedParentThreadsRequest) {
			/* Retrieve parent threads which have a specific tag */
			GetTaggedParentThreadsRequest request = (GetTaggedParentThreadsRequest) object;
			GetTaggedParentThreadsResponse response = new GetTaggedParentThreadsResponse();
			response.result = null;
			response.error = "";
			try {
				response.result = ArcadeServer.instance().getForumStorage().getTaggedParentThreads(request.tag);
				if (response.result == null) {
					response.error = "No result found";
				}
			} catch (DatabaseException e) {
				response.error = e.getMessage();
				response.result = null;
			} finally {
				connection.sendTCP(response);
			}
		} else if (object instanceof GetParentThreadsRequest){
			GetParentThreadsRequest request = (GetParentThreadsRequest) object;
			GetParentThreadsResponse response = new GetParentThreadsResponse();
			response.error = "";
			try {
				response.result = ArcadeServer.instance().getForumStorage().getParentThreads(request.start, request.end, request.limit);
			} catch (DatabaseException e) {
				response.error = e.getMessage();
				response.result = null;
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
		} else if (object instanceof InsertParentThreadRequest) {
			/* Insert a new parent thread */
			InsertParentThreadRequest request = (InsertParentThreadRequest) object;
			InsertParentThreadResponse response = new InsertParentThreadResponse();
			response.result = 0;
			response.error = "";
			try {
				response.result = ArcadeServer.instance().getForumStorage()
						.insertParentThread(request.topic, request.message, request.createdBy, request.category, request.tags);
			} catch (DatabaseException e) {
				response.error = e.getMessage();
			} finally {
				connection.sendTCP(response);
			}
		} else if (object instanceof UpdateParentThreadRequest) {
			UpdateParentThreadRequest request = (UpdateParentThreadRequest) object;
			UpdateParentThreadResponse response = new UpdateParentThreadResponse();
			response.error = "";
			try {
				ArcadeServer.instance().getForumStorage().updateParentThread(
						request.pid, request.newTopic, request.newMessage, request.newCategory, request.newTags);
			} catch (DatabaseException e) {
				response.error = e.getMessage();
			} finally {
				connection.sendTCP(response);
			}
		} 
		
		if (object instanceof GetForumUserRequest) {
			System.out.println("GetForumUserRequest is received (debug purpose)");
			GetForumUserRequest request = (GetForumUserRequest) object;
			GetForumUserResponse response = new GetForumUserResponse();
			response.error = "";
			response.result = null;
			try {
				if (request.userId == 0 && request.userName != "") {
					response.result = ArcadeServer.instance().getForumStorage().getForumUser(request.userName);
				} else if (request.userId != 0 && request.userName == "") {
					response.result = ArcadeServer.instance().getForumStorage().getForumUser(request.userId);
				} else {
					response.error = "Invalid request";
				}
			} catch (DatabaseException e) {
				e.printStackTrace();
				response.error = e.getMessage();
			} finally {
				connection.sendTCP(response);
				System.out.println("GetForumUserResponse is sent (debug purpose)");
			}
		}
	}
}
