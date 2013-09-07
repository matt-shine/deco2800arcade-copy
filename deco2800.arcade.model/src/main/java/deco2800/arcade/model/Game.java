package deco2800.arcade.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

public class Game implements Comparable<Game> {

    //TODO shared between server & client?

	/**
	 * @author uqjstee8
	 *
	 */
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ArcadeGame {
		 String id();
	}
	
	/**
	 * This annotation is for ArcadeGames that should not be listed in the games list
	 * in release builds
	 * @author Simon
	 *
	 */
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface InternalGame {
	}
	
	public String id; //A machine-processable identifier for the game
	
	public String name; //A human-readable name for the game
	
	public String description; //The description for the game

	public Icon icon; //A game Icon

	public int pricePerPlay = 1;
	
	/**
	 * Sets the description for a game
	 * @param description Game description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * returns the description of a game as a string
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the name of a game
	 * @param name Game name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of a game as a string
	 * @return name
	 */
	public String getName() {
		return name;
	}

    /**
     * Set Icon
     * @param icon Game Icon
     */
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    /**
     * Get Icon
     * @return icon
     */
    public Icon getIcon() {
        return this.icon;
    }

    /**
     *  Compare Games based on names
     */
    @Override
    public int compareTo(Game g) {
        return name.compareTo(g.name);
    }
}
