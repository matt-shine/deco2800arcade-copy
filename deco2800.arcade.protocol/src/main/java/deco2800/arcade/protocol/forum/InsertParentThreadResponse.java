package deco2800.arcade.protocol.forum;

/**
 * Response of InsertParentThreadRequest
 * Result contains a pid of new parent thread (-1 for failure)
 * Error contains the error message (empty for no error)
 *
 */
public class InsertParentThreadResponse {
	public int result;
	public String error;
}
