package deco2800.arcade.protocol.forum;

/** 
 * Request for inserting a new parent thread to DB 
 */
public class InsertParentThreadRequest {
	public String topic;
	public String message;
	public int createdBy;
	public String category;
	public String tags;
}
