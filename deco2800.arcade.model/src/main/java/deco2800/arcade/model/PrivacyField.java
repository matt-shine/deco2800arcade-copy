package deco2800.arcade.model;

/**
 * PrivacyField is an abstract encapsulation of a boolean, providing an ID
 * identifier.
 * 
 * @author Leggy
 * 
 */
public class PrivacyField {

	private int id;
	private boolean value;

	/**
	 * Creates a new PrivacyField given an ID and value.
	 * 
	 * @param id
	 *            The PrivacyField's ID.
	 * @param value
	 *            The PrivacyField's value.
	 */
	public PrivacyField(int id, boolean value) {
		this.id = id;
		this.value = value;
	}

	/**
	 * Access method for the PrivacyField's ID.
	 * 
	 * @return Returns the PrivacyField's ID.
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Access method for the PrivacyField's value.
	 * 
	 * @return Returns the PrivacyField's value.
	 */
	public boolean getValue() {
		return this.value;
	}

	/**
	 * Sets the PrivacyField's value to that provided.
	 * 
	 * @param v
	 *            The PrivacyField's new value.
	 */
	public void setValue(boolean v) {
		this.value = v;
	}

}
