package deco2800.arcade.deerforest.models.cards;

/**
 * Provides an abstraction for the monster cards
 */
public abstract class AbstractMonster extends AbstractCard {

	private String type;
	private int health;
	private int attack;
    private int originalAttack;
    private int currentHealth;

	/**
	 * Initialises the card, note attacks map damage to effect
	 */
	public AbstractMonster(String type, int health, int attack, String cardFilePath) {
		this.type = type;
		this.health = health;
        this.currentHealth = health;
		this.attack = attack;
        this.originalAttack = attack;
	}

	/**
	 * Get attacks (make sure to not return part of private class)
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * Get highest attack (not taking current effects into consideration)
	 */
	public int getHighestAttack() {
		return attack;
	}

	/**
	 * Get total Health (not taking current effects into consideration)
	 */
	public int getTotalHealth() {
		return health;
	}

	/**
	 * Returns the current health fo the monster
	 */
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Resets the health and attack of the monster
     */
    public void resetStats() {
        this.attack = this.originalAttack;
        this.currentHealth = this.health;
    }

    /**
     * Increases the attack of the monster by the given amount
     */
    public void buffAttack(int amount) {
        this.attack = this.attack + amount;
    }

    /**
     * Increases the health of the monster by the current amount
     * @param amount
     */
    public void buffHealth(int amount) {
        this.currentHealth = this.currentHealth + amount;
    }

    /**
     * Increases both the health and attack of the monster by the given amount
     */
    public void buff(int amount) {
        this.currentHealth = this.currentHealth + amount;
        this.attack = this.attack + amount;
    }

	/**
	 * Returns the weakness of the monster
	 */
	public String getWeakness() {
		return null;
	}

	/**
	 * Returns the element type of the card
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Returns the resistance of the card
	 */
	public String getResistance() {
		return null;
	}

	/**
	 * Get damaged (taking into account effects affecting the monster currently)
	 * returning true if dead.
	 */
	public boolean takeDamage(int damage, String typeOfAttack) {

        this.currentHealth -= modifiedDamage(damage, typeOfAttack);

        if(this.currentHealth <= 0) {
            return true;
        }

		return false;
	}

	/**
	 * Gets the damage of the monster using the given modifiers
	 */
    public int modifiedDamage(int damage, String typeOfAttack) {

        int modifiedDamage = damage;
        double damageMultiplier = 1;

        if(this.type.equals(typeOfAttack)) {
            damageMultiplier = 0.75;
        } else if(this.type.equals("Fire") && typeOfAttack.equals("Water")) {
            damageMultiplier = 1.5;
        } else if(this.type.equals("Water") && typeOfAttack.equals("Nature")) {
            damageMultiplier = 1.5;
        } else if(this.type.equals("Nature") && typeOfAttack.equals("Fire")) {
            damageMultiplier = 1.5;
        } else if(this.type.equals("Dark") && typeOfAttack.equals("Light")) {
            damageMultiplier = 1.5;
        } else if(this.type.equals("Light") && typeOfAttack.equals("Dark")) {
            damageMultiplier = 1.5;
        }

        return (int)(damageMultiplier*modifiedDamage);
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
