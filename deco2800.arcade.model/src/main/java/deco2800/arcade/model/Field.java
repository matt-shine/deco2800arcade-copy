package deco2800.arcade.model;

/**
 * Field is an abstract encapsulation of the String class, providing an ID
 * identifier.
 * 
 * @author Leggy
 * 
 */
public class Field {

	private int id;
	private String value;

	/**
	 * Creates a new field given an ID and value.
	 * 
	 * @param id
	 *            The Field's ID.
	 * @param value
	 *            The Field's value.
	 */
	public Field(int id, String value) {
		this.id = id;
		this.value = value;
	}

	/**
	 * Access method for the Field's ID.
	 * 
	 * @return Returns the Field's ID.
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Access method for the Field's value.
	 * 
	 * @return Returns the Field's value.
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Sets the Field's value to that provided.
	 * 
	 * @param v
	 *            The Field's new value
	 * @require v != null
	 */
	public void setValue(String v) {
		this.value = v;
	}

}
