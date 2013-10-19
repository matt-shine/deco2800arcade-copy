package deco2800.arcade.deerforest.models.gameControl;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.deerforest.GUI.ExtendedSprite;
import deco2800.arcade.deerforest.GUI.SpriteLogic;
import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.Deck;
import deco2800.arcade.deerforest.models.cardContainers.Field;
import deco2800.arcade.deerforest.models.cardContainers.Graveyard;
import deco2800.arcade.deerforest.models.cardContainers.Hand;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.FireMonster;
import deco2800.arcade.deerforest.models.cards.WaterMonster;

public class DeckSystem {
	
	public Deck deck;
	private Deck cards;
	
	public DeckSystem(Deck playerDeck, Deck cards) {
		this.deck = playerDeck;
		this.cards = cards;
	}

    /**
     * Returns the card collection of the specified area
     * @param area the key for the area to return
     * @return a CardCollection of the area
     */
	public CardCollection getCardCollection(String area) {
		
		if(area.contains("Card")) {
			return cards;
		} else if(area.contains("Deck")) {
			return deck;
		}
		
		return null;
	}
	
	
	
}
