package deco2800.arcade.replay;

public class ReplayItem {
	
	public static Integer TYPE_INTEGER = 1;
	public static Integer TYPE_FLOAT = 2;
	public static Integer TYPE_STRING = 3;

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
		/* deep copy */
		this.data = data;
	}
	
	public Integer getType() {
		return this.type;
	}
	
	public Object getData() {
		return this.data;
	}
	
	public String toString() {
		//return "ReplayItem: " + this.data.toString();
		return this.data.toString();
	}
	
	public int hashCode() {
		return ( 17 * this.type.hashCode() ) * ( 7 * this.data.hashCode() );
	}
}
