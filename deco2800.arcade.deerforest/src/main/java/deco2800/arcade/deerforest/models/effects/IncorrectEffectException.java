package deco2800.arcade.deerforest.models.effects;

/**
 * Exception to throw when dealing with an incorrect effect
 */
public class IncorrectEffectException extends Exception {

	/**
	 * Here only to suppress warnings
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param errorMessage string to display on error
	 */
	public IncorrectEffectException(String errorMessage) {
		super(errorMessage);
	}
	
}
