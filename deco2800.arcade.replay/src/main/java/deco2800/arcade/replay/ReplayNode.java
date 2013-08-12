package deco2800.arcade.replay;

import java.util.*;

public class ReplayNode {

	private long nodeTime;
	private String type;
	//private List<ReplayItem> items;
	private Map<String, ReplayItem> items;
	
	/* most constructors aren't used */
	public ReplayNode( String type, Map<String, ReplayItem> items, long timeOffset ) {
		this.nodeTime = timeOffset;
		this.type = type;
		this.items = items;
	}
	
	/**
	 * Create Replay Node from a set of data.
	 * @param type The Event Type
	 * @param items An Item List <DataName, DataValue>
	 */
	public ReplayNode( String type, Map<String, ReplayItem> items)
	{
	    this.nodeTime = 0;
	    this.type = type;
	    this.items = items;
	}
	
	/*
	 * TODO: create from flattened string
	 */
	
	/* get rid of this, but we still need the functionality. */
	public ReplayNode( String type ) {
		this.nodeTime = 0;
		this.type = type;
		
		items = new HashMap<String, ReplayItem>();
	}
	
	/*
	 * TODO: flatten method
	 * Converts to string
	 * 		node: {
	 * 			time: long,
	 * 			type: String,
	 * 			items: {
	 * 				key: String
	 * 				value: ReplayItem { (flattened version of replayItem)
	 * 						type: Integer,
	 * 						data: Integer/Float/String etc...
	 * 					},
	 * 				...
	 * 			}
	 * 		}
	 */
	
	public ReplayNode( ReplayNode toCopy ) {
		this.nodeTime = toCopy.getTime();
		this.type = toCopy.getType();
		this.items = toCopy.getItems();
	}
	
	public void setTime( long newTime ) {
		this.nodeTime = newTime;
	}
	
	public void addItem( String name, ReplayItem item ) {
		this.items.put( name, item );
	}
	
	public long getTime() {
		return this.nodeTime;
	}
	
	public String getType() {
		return this.type;
	}
	
	public Map<String, ReplayItem> getItems() {
		/* This copy is too shallow, needs to copy the values by value not reference */
		return new HashMap<String, ReplayItem>( this.items );
	}
	
	public String toString() {
		String repr = "ReplayNode at " + this.nodeTime + "\n";
		return repr;
	}
}
