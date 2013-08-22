package deco2800.arcade.client.replay;

import com.google.gson.*;
import java.lang.reflect.*;
import java.util.*;

public class ReplayNodeDeserializer implements JsonDeserializer<ReplayNode> {
	/**
	 * This method will be called whenever a registered Gson object tries to 
	 * convert a JSON formatted string into a ReplayNode object.
	 */
	@Override
	public ReplayNode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		// Convert the element to an object, which can be parsed
	    JsonObject jsonObject = json.getAsJsonObject();
	    
	    // Grab the top level values from the object 
	    String nodeType = jsonObject.get( "type" ).getAsString();
	    Long nodeTime = jsonObject.get( "nodeTime" ).getAsLong();
	    
	    // Create the node, to have items pushed to it
	    ReplayNode node = new ReplayNode( nodeType, nodeTime );
	    
	    // Get the items as a JSON object
	    JsonElement items = jsonObject.get("items");
	    JsonObject elems = items.getAsJsonObject();
	    
	    // Add each item in elems to the node
	    for ( Map.Entry<String,JsonElement> entry : elems.entrySet()) {
	    	ReplayItem item;
	    	String itemType = entry.getKey();
	    	
	    	JsonObject entryObject = entry.getValue().getAsJsonObject();
	    	/* Would like to find a better solution to this, but the constants
	    	 * declared in ReplayItem aren't known at compile time (somehow) so 
	    	 * the compiler complains. Not sure if we can just suppress, or if
	    	 * thats a massive no-no.
	    	 */
	    	switch( entryObject.get( "type" ).getAsInt() ) {
	    	case ReplayItem.TYPE_INTEGER:
	    		item = new ReplayItem( entryObject.get( "data" ).getAsInt() );
	    		break;
	    	case ReplayItem.TYPE_FLOAT:
	    		item = new ReplayItem( entryObject.get( "data" ).getAsFloat() );
	    		break;
	    	case ReplayItem.TYPE_STRING:
	    		item = new ReplayItem( entryObject.get( "data" ).getAsString() );
	    		break;
	    	default:
	    		// TODO: make our own exception
	    		throw new ClassCastException( "Could not cast JSON string to ReplayNode" );
	    	} 
	    	
	    	node.addItem( itemType, item );
	    }
	    
	    return node;
	}
	
}