package deco2800.arcade.towerdefence.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A wrapper to maintain a model of all the sprites that should be sent to render.
 * @author hadronn
 *
 */
public class AnimationsList {
	// Fields
	// The animations to be sent to render
	private List<List<Sprite>> animationsList;
	// Constructor
	/**
	 * Constructs an empty list of sprite lists
	 */
	public AnimationsList() {
		this.animationsList = new ArrayList<List<Sprite>>();
	}
	
	// Getters
	/**
	 * Return the list
	 * @return
	 */
	public List<List<Sprite>> animationsList() {
		return animationsList;
	}
	
	// Setters
	/**
	 * Set the list to a new list.
	 * @param list
	 */
	public void animationsList(List<List<Sprite>> list) {
		this.animationsList = list;
	}
	
	// Methods
	/**
	 * Allows you to add a list of sprites to the list for rendering
	 * @param sprites
	 */
	public void add(List<Sprite> sprites) {
		this.animationsList.add(sprites);
	}
	/**
	 * Allows you to remove a list of sprites from the list for rendering
	 * @param sprites
	 */
	public void remove(List<Sprite> sprites) {
		this.animationsList.remove(sprites);
	}

	public int size() {
		return animationsList.size();
	}

	public Sprite get(int i) {
		return (Sprite) animationsList.get(i);
	}
	
}
