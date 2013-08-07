package deco2800.arcade.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.util.Set;

public class Game {

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

	public String gameId; //A machine-processable identifier for the game
	
	public String name; //A human-readable name for the game
	
	public Set<Achievement> availableAchievements;
	
	public int pricePerPlay = 1;
	
}
