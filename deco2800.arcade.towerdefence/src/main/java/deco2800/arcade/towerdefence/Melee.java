package deco2800.arcade.towerdefence;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * The interface for GridObjects that can attack another GridObject on it's
 * facing.
 * 
 * @author hadronn
 * 
 */
public interface Melee {
	/**
	 * Returns the number of attacks per second it is capable of
	 * 
	 * @return
	 */
	public float attackRate();

	/**
	 * Returns the damage of an attack
	 * 
	 * @return
	 */
	public int damage();

	/**
	 * Returns the penetration of the attack
	 * 
	 * @return
	 */
	public int penetration();

	/**
	 * Returns the target the GridObject is currently focused on.
	 * 
	 * @return
	 */
	public GridObject target();
	
	/**
	 * Returns the array of sprites to animate melee attacking.
	 * @return
	 */
	public List<Sprite> meleeSprites();

}
