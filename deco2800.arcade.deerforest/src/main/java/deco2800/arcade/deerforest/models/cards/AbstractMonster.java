package deco2800.arcade.deerforest.models.cards;

import java.util.List;

import deco2800.arcade.deerforest.models.effects.Attack;
import deco2800.arcade.deerforest.models.effects.MonsterEffect;

public abstract class AbstractMonster extends AbstractCard {

	private String type;
	private int health;
	private int attack;
    private int currentHealth;

	//Variables for current effects affecting the monster

	//Initialise the card, note attacks map damage to effect
	public AbstractMonster(String type, int health, int attack, String cardFilePath) {
		this.type = type;
		this.health = health;
        this.currentHealth = health;
		this.attack = attack;
	}

	//Get attacks (make sure to not return part of private class)
	public int getAttack() {
		return attack;
	}

	//Get highest atk (not taking current effects into consideration)
	public int getHighestAttack() {
		return attack;
	}

	//Get total Health (not taking current effects into consideration)
	public int getTotalHealth() {
		return health;
	}

    public int getCurrentHealth() {
        return currentHealth;
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

        double damageMultiplier = 1;

        if(this.type.equals(typeOfAttack)) {
            damageMultiplier = 0.5;
        } else if(this.type.equals("Fire") && typeOfAttack.equals("Water")) {
            damageMultiplier = 2;
        } else if(this.type.equals("Water") && typeOfAttack.equals("Nature")) {
            damageMultiplier = 2;
        } else if(this.type.equals("Nature") && typeOfAttack.equals("Fire")) {
            damageMultiplier = 2;
        } else if(this.type.equals("Dark") && typeOfAttack.equals("Light")) {
            damageMultiplier = 2;
        } else if(this.type.equals("Light") && typeOfAttack.equals("Dark")) {
            damageMultiplier = 2;
        }

        this.currentHealth -= damage*damageMultiplier;

        if(this.currentHealth <= 0) {
            return true;
        }

		return false;
	}
	
	public String toString() {
		String s;
		s = "Type: " + getType() + ", Health: " + getCurrentHealth()
				+ ", Attack: " + getAttack();
		return s;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + attack;
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
        if (attack != other.attack)
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
		
}
