package deco2800.arcade.model;

import java.io.IOException;

public class Achievement {

	// TODO shared between server & client?

	private String description;

	private final Icon icon;

	/**
	 * Creates a new Achievement given the description.
	 * 
	 * @param description
	 *            The Achievement's description.
	 */
	public Achievement(String description) {
		this.description = description;
		this.icon = null;
	}

	/**
	 * Creates a new Achievement given the description and icon.
	 * 
	 * @param description
	 *            The Achievement's description.
	 * @param filepath
	 *            The filepath to the Achievement's icon image file.
	 * @throws IOException
	 *             Throws exception when the image cannot be found at the
	 *             designated filepath.
	 */
	public Achievement(String description, String filepath) throws IOException {
		/*
		 * Note that exception handling could be done in-method, however if it
		 * cannot be loaded there is no way (other than changing the return type
		 * to boolean/int and specifying error range) to communicate this.
		 */
		this.description = description;
		icon = new Icon(filepath);

	}

	/**
	 * Creates an achievement identical to another.
	 * 
	 * @param achievement
	 *            The original Achievement to be copied.
	 */
	public Achievement(Achievement achievement) {
		/*
		 * Preserving immutability.
		 */
		this.description = achievement.getDescription();
		this.icon = achievement.getIcon().clone();

	}

	/**
	 * Access method for Achievement's description.
	 * 
	 * @return The Achievement's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Access method for the Achievement's icon.
	 * 
	 * @return The Achievement's icon.
	 */
	public Icon getIcon() {
		return icon.clone();
	}

}
