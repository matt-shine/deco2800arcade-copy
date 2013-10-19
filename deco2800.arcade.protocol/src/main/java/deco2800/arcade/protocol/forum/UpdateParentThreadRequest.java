package deco2800.arcade.protocol.forum;

/**
 * Request for updateting parent thread in parent_thread table. <br>
 * pid is not updatable (it will be used to search a thread subject to change.)
 * 
 * @author Junya, TeamForum
 *
 */
public class UpdateParentThreadRequest {
	public int pid;
	public String newTopic;
	public String newMessage;
	public String newCategory;
	public String newTags;
}
