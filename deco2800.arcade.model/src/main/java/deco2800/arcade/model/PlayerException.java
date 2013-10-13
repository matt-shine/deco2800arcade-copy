package deco2800.arcade.model;

/*An exception for handing invalid player data */

public class PlayerException extends Exception {

	private static final long serialVersionUID = 1L;

	public PlayerException() {
		super();
	}

	public PlayerException(String s) {
		super(s);
	}
}
