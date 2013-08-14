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
	

	public String gameId; //A machine-processable identifier for the game
	
	public String name; //A human-readable name for the game
	
	public Set<Achievement> availableAchievements;
	
}
