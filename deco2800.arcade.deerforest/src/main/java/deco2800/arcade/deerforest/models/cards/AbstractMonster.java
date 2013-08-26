package deco2800.arcade.deerforest.models.cards;

import java.util.List;

import deco2800.arcade.deerforest.models.effects.Attack;
import deco2800.arcade.deerforest.models.effects.MonsterEffect;

public abstract class AbstractMonster extends AbstractCard {
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attackList == null) ? 0 : attackList.hashCode());
		result = prime * result + health;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractMonster other = (AbstractMonster) obj;
		if (attackList == null) {
			if (other.attackList != null)
				return false;
		} else if (!attackList.equals(other.attackList))
			return false;
		if (health != other.health)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	String type;
	int health;
	List<Attack> attackList;
	//Variables for current effects affecting the monster

	//Initialise the card, note attacks map damage to effect
	public AbstractMonster(String type, int health, List<Attack> attacks) {
		this.type = type;
		this.health = health;
		this.attackList = attacks;
	}

	//Get attacks (make sure to not return part of private class)
	public List<Attack> getAttacks() {
		return attackList;
	}

	//Get highest atk (not taking current effects into consideration)
	public Attack getHighestAttack() {
		return null;
	}
	
	//Get lowest atk (not taking current effects into consideration)
	public Attack getLowestAttack() {
		return null;
	}

	//Get total Health (not taking current effects into consideration)
	public int getTotalHealth() {
		return health;
	}

	//Get current Health (taking current effects into consideration)
	public int getCurrentHealth() {
		return 0;
	}
	
	//get weakness
	public String getWeakness() {
		return null;
	}

	// Returns the element type of the card
	public String getType() {
		return type;
	}
	
	//get resistance
	public String getResistance() {
		return null;
	}

	//Get damaged (taking into account effects affecting the monster currently), true if dead
	public boolean takeDamage(int damage, String typeOfAttack) {
		return false;
	}

	//add buffing / weakening effect to monster, true if succeeded
	public boolean addEffect(MonsterEffect effect) {
		return false;
	}

	//remove effect from monster, true if succeeded
	public boolean removeEffect(MonsterEffect effect) {
		return false;
	}
	
	public String toString() {
		String s;
		s = "Type: " + getType() + ", Health: " + getTotalHealth() 
				+ ", Attacks: " + getAttacks(); 
		return s;
	}
		
}
