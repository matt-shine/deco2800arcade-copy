package deco2800.arcade.client.replay;

import deco2800.arcade.client.replay.exception.ReplayItemDataInvalidException;

public class ReplayItem {
	
	public static final int TYPE_INTEGER = 1;
	public static final int TYPE_FLOAT = 2;
	public static final int TYPE_STRING = 3;

	private Integer type;
	private Object data;
	
	public ReplayItem( Object data ) {
		if ( data instanceof Integer ) {
			this.type = TYPE_INTEGER;
		} else if ( data instanceof Float ) {
			this.type = TYPE_FLOAT;
		} else if ( data instanceof String ) {
			this.type = TYPE_STRING;
		} else {
			throw new ReplayItemDataInvalidException( "Type invalid, only Integer, Float or String allowed" );
		}
		
		this.data = data;
	}
	
	/**
	 * Gets the type of the replay item.
	 * 
	 * @return 	the type of the object, as a constant defined statically on
	 * 			ReplayItem
	 */
	public Integer getType() {
		return this.type;
	}
	
	/**
	 * Gets the value stored in the replay item.
	 * 
	 * @return 	value stored, type corresponds to item.getType()
	 */
	public Object getData() {
		return this.data;
	}
	
	/**
	 * Gets the integer value of the replay item, iff the value stored is
	 * an integer.
	 * 
	 * @return	the integer value stored in the item
	 */
	public Integer intVal() {
		if ( this.type == TYPE_INTEGER ) {
			return (Integer) getData();
		} else {
			throw new RuntimeException( "Invalid cast" );
		}
	}
	
	/**
	 * Gets the float value of the replay item, iff the value stored is
	 * a float.
	 * 
	 * @return	the float value stored in the item
	 */
	public Float floatVal() {
		if ( this.type == TYPE_FLOAT ) {
			return (Float) getData();
		} else {
			throw new RuntimeException( "Invalid cast" );
		}
	}

	/**
	 * Gets the string value of the replay item, iff the value stored is
	 * an string.
	 * 
	 * @return	the string value stored in the item
	 */
	public String stringVal() {
		if ( this.type == TYPE_STRING ) {
			return (String) getData();
		} else {
			throw new RuntimeException( "Invalid cast" );
		}
	}
	
	/**
	 * Gets a string representation of the data stored in the item.
	 * 
	 * @return 	string representation of the item
	 */
	public String toString() {
		return this.data.toString();
	}
	
	/**
	 * Basic hashcode function
	 * 
	 * @return	hash of the item
	 */
	public int hashCode() {
		return ( 17 * this.type.hashCode() ) * ( 7 * this.data.hashCode() );
	}
}
