package deco2800.arcade.model;

public class Achievement {

	// TODO shared between server & client?

	// TODO icon?

	private String description;

	/**
	 * Creates a new Achievement given the description.
	 * 
	 * @param description
	 *            The Achievement's description.
	 */
	public Achievement(String description) {
		this.description = description;
	}

	/**
	 * Access method for Achievement's description.
	 * 
	 * @return The Achievement's description.
	 */
	public String getDescription() {
		return description;
	}

}
