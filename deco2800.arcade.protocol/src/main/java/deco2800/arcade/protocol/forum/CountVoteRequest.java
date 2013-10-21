package deco2800.arcade.protocol.forum;

/**
 * Request for counting total votes having a specific condition.
 * <p>
 * If countType = "all", count vote of all threads (countAllVotes). <br>
 * If countType = "parent", count vote of parent thread and its underlying
 *  child threads.<br>
 * If countType = "user", count vote of threads created by a specific user.<br>
 * For countType = "parent" & "user", set id for pid and userId.
 * 
 * @author Junya, Team Forum
 *
 */
public class CountVoteRequest {
	public int id;
	public String countType;
}
