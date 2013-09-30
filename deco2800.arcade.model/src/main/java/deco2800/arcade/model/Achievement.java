package deco2800.arcade.model;

import deco2800.arcade.model.Icon;

/**
 * An immutable struct-like class used for storing information about an
 * achievement. Note that while the fields are public, they're also final
 * (to enforce immutability) which removes any invariant issues.
 */
public class Achievement {
    
    public final String id;
    public final String name;
	public final String description;
    public final int awardThreshold;
	public final Icon icon;
    
    /**
     * Constructs an Achievement from the supplied arguments.
     * 
     * @param id             The achievement's id.
     * @param name           The achievement's name.
     * @param description    The achievement's description.
     * @param awardThreshold The achievement's awardThreshold.
     * @param icon           The achievement's icon.
     */
	public Achievement(String id, 
            String name, 
            String description,
            int awardThreshold, 
            Icon icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.awardThreshold = awardThreshold;
        this.icon = icon;
    }
}
