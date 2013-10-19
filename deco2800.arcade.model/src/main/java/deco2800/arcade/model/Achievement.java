package deco2800.arcade.model;

import deco2800.arcade.model.Icon;

/**
 * An immutable struct-like class used for storing information about an
 * achievement. Note that while the fields are public, they're also final
 * (to enforce immutability) which removes any invariant issues.
 *
 * It's invalid for two Achievements to have equal ids without the
 * rest of the fields being equal as well.
 */

public class Achievement {
    
	public String id;
	public String name;
	public String description;
    public int awardThreshold;
	public String icon; //Temporarily using string for debugging
    
    // USED BY KRYONET - don't use it yourself
    public Achievement() {}

        /**
	 * Constructs an Achievement from the supplied arguments.
	 * 
	 * @param id	         The achievement's id.
	 * @param name	         The achievement's name.
	 * @param description    The achievement's description.
	 * @param awardThreshold The achievement's awardThreshold.
	 * @param icon	         The achievement's icon.
	 */
	public Achievement(String id, 
			String name, 
	    		String description,
	    		int awardThreshold, 
	    		String icon) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.awardThreshold = awardThreshold;
		this.icon = icon;
	}
	
	/**
	 * Returns the name of an Achievement
	 * @author PeterHsieh 
	 * 
	 * @return String
	 * 
	 */
	
	// Just used for debugging purposes
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Achievement)) {
			return false;
		}
		
		Achievement ach = (Achievement)obj;
		// equality is based on id - we don't need to check the rest
		return this.id.equals(ach.id);
	}

	@Override
	public int hashCode() {
		// because equality's only based on the id we don't actually
		// need to hash the rest
		return id.hashCode();
	}

    @Override
    public String toString() {
        return "(" + id + ", " + name + ", " + description + ", " + awardThreshold + ")";
    }

    // Static helper stuff
    public static String idForComponentID(String componentID) {
	if (!isComponentID(componentID)) // don't bother if it's not a component id already
	    return componentID;

        String[] parts = componentID.split("\\.");
        return parts[0] + "." + parts[1];
    }

    public static boolean isComponentID(String componentID) {
        return (componentID.split("\\.").length == 3);
    }
}
