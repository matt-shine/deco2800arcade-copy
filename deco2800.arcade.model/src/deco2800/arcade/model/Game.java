package deco2800.arcade.model;

import java.util.Set;

import com.badlogic.gdx.ApplicationListener;

public abstract class Game implements ApplicationListener {

	//TODO shared between server & client?
	
	public String gameId;
	
	public Set<Achievement> availableAchievements;
}
