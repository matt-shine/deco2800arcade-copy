package deco2800.arcade.client.model;

import java.util.Set;

import com.badlogic.gdx.ApplicationListener;

public abstract class Game implements ApplicationListener {

	//TODO shared between server & client?
	
	private String gameId;
	
	private Set<Achievement> availableAchievements;
}
