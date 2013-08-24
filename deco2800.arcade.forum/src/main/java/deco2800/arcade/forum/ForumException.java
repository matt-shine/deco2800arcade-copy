package deco2800.arcade.forum;

/**
 * ForumException models the exception handler for forum package. 
 * This should not be thrown from replacements of other exceptions, run-time exceptions and errors.
 * @author Junya
 *
 */
public class ForumException extends Exception {
	private static final long serialVersionUID = -8025837404065239227L;

	public ForumException(String e) {
		super(e);
	}
}
