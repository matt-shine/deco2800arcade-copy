package deco2800.arcade.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Set;

import com.badlogic.gdx.ApplicationListener;

public abstract class Game implements ApplicationListener {

	//TODO shared between server & client?

	/**
	 * @author uqjstee8
	 *
	 */
	@Target(ElementType.TYPE)
	public @interface ArcadeGame {
		 String id();
	}

	public String gameId; //A machine-processable identifier for the game
	
	public String name; //A human-readable name for the game
	
	public Set<Achievement> availableAchievements;
}
