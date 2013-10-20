package deco2800.arcade.client.replay;

import java.util.*;

public class ReplayNode {

	private long nodeTime;
	private String type;
	private Map<String, ReplayItem> items;
	
	/**
	 * @param type
	 * 			string type of the node (eg. 'piece_move')
	 * @param items
	 * 			map of item names (String) to replay items (ReplayItem)
	 * @param timeOffset
	 * 			ms since the replay started
	 */
	public ReplayNode( String type, Map<String, ReplayItem> items, long timeOffset ) {
		this.nodeTime = timeOffset;
		this.type = type;
		this.items = items;
	}

	/**
	 * Sets default time offset of 0
	 * 
	 * @param type
	 * 			string type of the node (eg. 'piece_move')
	 * @param items
	 * 			map of item names (String) to replay items (ReplayItem)
	 */
	public ReplayNode( String type, Map<String, ReplayItem> items)
	{
	    this.nodeTime = 0;
	    this.type = type;
	    this.items = items;
	}
	
	/**
	 * Creates a replay node with no items
	 * 
	 * @param type
	 * 			string type of the node (eg. 'piece_move')
	 * @param timeOffset
	 * 			ms since the replay started
	 */
	public ReplayNode( String type, long timeOffset ) {
		this.nodeTime = timeOffset;
		this.type = type;
		
		items = new HashMap<String, ReplayItem>();
	}
	
	/**
	 * Creates a replay node with no items and a time of 0
	 * 
	 * @param type
	 * 			string type of the node (eg. 'piece_move')
	 */
	public ReplayNode( String type ) {
		this.nodeTime = 0;
		this.type = type;
		
		items = new HashMap<String, ReplayItem>();
	}
	
	/**
	 * Creates a replay node as a copy of an existing node (deep copy)
	 * 
	 * @param toCopy
	 */
	public ReplayNode( ReplayNode toCopy ) {
		this.nodeTime = toCopy.getTime();
		this.type = toCopy.getType();
		this.items = new HashMap<String, ReplayItem>( toCopy.getItems() );
	}
	
	/**
	 * Set the time offset of the node
	 * 
	 * @param newTime
	 * 			the new time offset for the node
	 */
	public void setTime( long newTime ) {
		this.nodeTime = newTime;
	}
	
	/**
	 * Push a replay item to the list
	 * 
	 * @param name
	 * 			The name of the item, eg. 'piece_move'
	 * @param item
	 * 			The item to add to the list
	 */
	public void addItem( String name, ReplayItem item ) {
		this.items.put( name, item );
	}
	
	/**
	 * Get the offset of the node
	 * 
	 * @return
	 * 			time (in ms) since the replay was started
	 */
	public long getTime() {
		return this.nodeTime;
	}
	
	/**
	 * Gets the type of the node
	 * 
	 * @return
	 * 			the string type of the item
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Gets the items stored in the node, deep copying to protect class variables
	 * 
	 * @return 	a deep copy of the items
	 */
	public Map<String, ReplayItem> getItems() {
		/* This copy is too shallow, needs to copy the values by value not reference */
		return new HashMap<String, ReplayItem>( this.items );
	}
	
	/**
	 * Gets a replay item for a specified string key
	 * 
	 * @param key
	 * 			the item to get from the node
	 * @return
	 * 			the replay item for the given key
	 */
	public ReplayItem getItemForString( String key ) {
		return this.items.get( key );
	}
	
	/**
	 * Simple toString function
	 */
	public String toString() {
		return "ReplayNode at " + this.nodeTime + ": " + this.items;
	}
}
