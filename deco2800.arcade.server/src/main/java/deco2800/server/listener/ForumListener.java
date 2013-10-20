package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.model.forum.*;
import deco2800.arcade.protocol.forum.*;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.ForumStorage;

/**
 * ForumListener models listener for forum's server service.
 * <p>
 * Spec:
 * <ul>
 * 	<li>If error, it is set on response.error.</li>
 * 	<li>response.errro generally contains result of request.</li>
 *  <li>Catch DatabaseException and append it to response.error.</li>
 *  <li>For sending ParentThread, ChildThread and ForumUser instances, convert 
 *  them to ParentThreadProtocol, ChildThreadProtocol and ForumUserProtocol 
 *  instead. It is to allow Kryo's field serializer. </li>
 * </ul>
 * 
 * @author Junya, Team Forum
 * @see deco2800.arcade.protocol.forum.*
 * @see deco2800.arcade.server.ForumStorage
 */
public class ForumListener extends Listener {
	/**
	 * Execute process in response to client request
	 */
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);
		//System.out.println("Some request is received (debug purpose)");
		if (object instanceof ForumTestRequest){
			/* For test */
			ForumTestRequest request = (ForumTestRequest) object;
			ForumTestResponse response = new ForumTestResponse();
			int num = request.num;
			response.result = "Received integer is " + String.valueOf(num);
			response.error = "";
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
		} else if (object instanceof CountVoteRequest){
			/* Count total vote having specific condition. See CountVoteRequest for detail */
			CountVoteRequest request = (CountVoteRequest) object;
			CountVoteResponse response = new CountVoteResponse();
			response.error = "";
			response.result = -1;
			try {
				if (request.countType == "all") {
					response.result = ArcadeServer.instance().getForumStorage()
							.countAllVotes();
				} else if (request.countType == "parent") {
					response.result = ArcadeServer.instance().getForumStorage()
							.countParentThreadVotes(request.id);
				} else if (request.countType == "user") {
					response.result = ArcadeServer.instance().getForumStorage()
							.countUserVote(request.id);
				} else {
					response.error = "Invalid count type (whether all, parent or user)";
				}
			} catch (DatabaseException e) {
				response.error = "Fail CountVoteRequest, " + e.getMessage();
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
					response.pThread = ParentThreadProtocol.getParentThreadProtocol(pThread);
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
				ParentThread[] result;
				if (request.userId > 0) {
					result = ArcadeServer.instance().getForumStorage().getTaggedParentThreads(
							request.tag, request.userId);
				} else if (request.start != 0 || request.end != 0 || request.limit != 0) {
					result = ArcadeServer.instance().getForumStorage().getTaggedParentThreads(
							request.tag, request.start, request.end, request.limit);
				} else {
					result = ArcadeServer.instance().getForumStorage().getTaggedParentThreads(request.tag);
				}
				if (result == null) {
					response.error = "No result found";
				} else {
					response.result = ParentThreadProtocol.getParentThreadProtocols(result);
				}
			} catch (DatabaseException e) {
				response.error = e.getMessage();
				response.result = null;
			} finally {
				connection.sendTCP(response);
			}
		} else if (object instanceof GetParentThreadsRequest){
			/* Get parent threads by specifying pid range and category or userId */
			GetParentThreadsRequest request = (GetParentThreadsRequest) object;
			GetParentThreadsResponse response = new GetParentThreadsResponse();
			response.error = "";
			response.result = null;
			try {
				ParentThread[] threads;
				if (request.userId > 0) {
					threads = ArcadeServer.instance().getForumStorage().getParentThreads(request.start
							, request.end, request.limit, request.userId);
				} else if (request.category != "") {
					threads = ArcadeServer.instance().getForumStorage().getParentThreads(request.start
							, request.end, request.limit, request.category);
				} else {
					threads = ArcadeServer.instance().getForumStorage().getParentThreads(request.start
							, request.end, request.limit);
				}
				if (threads == null) {
					response.result = null;
				} else {
					response.result = ParentThreadProtocol.getParentThreadProtocols(threads);
				}
			} catch (DatabaseException e) {
				response.error = e.getMessage();
				response.result = null;
			} finally {
				connection.sendTCP(response);
			}
		} else if (object instanceof GetChildThreadRequest) {
			/* Get a child thread having a particular cid */
			GetChildThreadRequest request = (GetChildThreadRequest) object;
			GetChildThreadResponse response = new GetChildThreadResponse();
			response.result = null;
			response.error = "";
			try {
				ChildThread thread = ArcadeServer.instance().getForumStorage()
						.getChildThread(request.cid);
				if (thread == null) {
					response.error = "No result found";
				} else {
					response.result = ChildThreadProtocol.getChildThreadProtocol(thread);
				}
			} catch (DatabaseException e) {
				response.error = "Fail GetChildThreadRequest, " + e.getMessage();
			} finally {
				connection.sendTCP(response);
			}
		} else if (object instanceof GetChildThreadsRequest) {
			/* Get child threads with passing conditions */
			GetChildThreadsRequest request = (GetChildThreadsRequest) object;
			GetChildThreadsResponse response = new GetChildThreadsResponse();
			response.error = "";
			try {
				ChildThread[] threads;
				if (request.userId > 0) {
					threads = ArcadeServer.instance().getForumStorage().getChildThreads(
							request.pid, request.start, request.end, request.limit, request.userId);
				} else if (request.start != 0 || request.end != 0 || request.limit != 0) {
					threads = ArcadeServer.instance().getForumStorage().getChildThreads(
							request.pid, request.start, request.end, request.limit);
				} else {
					threads = ArcadeServer.instance().getForumStorage().getChildThreads(
							request.pid);
				}
				response.result = ChildThreadProtocol.getChildThreadProtocols(threads);
			} catch (DatabaseException e) {
				response.error = e.getMessage();
				response.result = null;
			} finally {
				connection.sendTCP(response);
			}
		
		} else if (object instanceof DeleteRequest) {
			/* Delete thread record for given id and thread type, and sends error if occur */
			DeleteRequest request = (DeleteRequest) object;
			DeleteResponse response = new DeleteResponse();
			response.error = "";
			try {
				if (request.threadType == "parent") {
					ArcadeServer.instance().getForumStorage().deleteParentThread(request.id);
				} else if (request.threadType == "child") {
					ArcadeServer.instance().getForumStorage().deleteChildThread(request.id);
				} else {
					response.error = "Invalid threadType (only parent or child)";
				}
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
				response.result = 0;
			} finally {
				connection.sendTCP(response);
			}
		} else if (object instanceof InsertChildThreadRequest) {
			/* Insert a new child thread */
			InsertChildThreadRequest request = (InsertChildThreadRequest) object;
			InsertChildThreadResponse response = new InsertChildThreadResponse();
			response.error = "";
			try {
				ArcadeServer.instance().getForumStorage()
						.insertChildThread(request.message, request.createdBy, request.pThread);
			} catch (DatabaseException e) {
				response.error = "Fail InsertChildTheradRequest, " + e.getMessage();
			} finally {
				connection.sendTCP(response);
			}
		} else if (object instanceof UpdateParentThreadRequest) {
			/* Update parent thread, pid is not updatable */
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
		} else if (object instanceof UpdateChildThreadRequest) {
			/* Update a child thread having a cid. Only message attr is updatable */
			UpdateChildThreadRequest request = (UpdateChildThreadRequest) object;
			UpdateChildThreadResponse response = new UpdateChildThreadResponse();
			response.error = "";
			try {
				ArcadeServer.instance().getForumStorage().updateChildThread(
						request.cid, request.newMessage);
			} catch (DatabaseException e) {
				response.error = "Fail UpdateChildThreadRequest, " + e.getMessage();
			} finally {
				connection.sendTCP(response);
			}
		} else if (object instanceof GetForumUserRequest) {
			/* For getting ForumUser */
			System.out.println("GetForumUserRequest is received (debug purpose)");
			GetForumUserRequest request = (GetForumUserRequest) object;
			GetForumUserResponse response = new GetForumUserResponse();
			response.error = "";
			response.result = null;
			try {
				if (request.userId == 0 && request.userName != "") {
					response.result = ForumUserProtocol.getForumUserProtocol(
							ArcadeServer.instance().getForumStorage().getForumUser(request.userName));
				} else if (request.userId != 0 && request.userName == "") {
					response.result = ForumUserProtocol.getForumUserProtocol(
							ArcadeServer.instance().getForumStorage().getForumUser(request.userId));
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
