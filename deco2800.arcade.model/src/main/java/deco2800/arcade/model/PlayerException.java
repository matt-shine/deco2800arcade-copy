package deco2800.arcade.model;

/*An exception for handing invalid player data */

@SuppressWarnings("serial")
public class PlayerException extends Exception {

	  public PlayerException() {
		super();
	    }

	    public PlayerException(String s) {
		super(s);
	    }
}
