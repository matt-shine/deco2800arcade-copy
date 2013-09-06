package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.Field;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

public class PhaseLogic {

    public static void battlePhaseSelection(int x, int y) {

        ExtendedSprite currentSelection = DeerForestSingletonGetter.getDeerForest().inputProcessor.getCurrentSelection();
        MainGame game = DeerForestSingletonGetter.getDeerForest().mainGame;

        //Check if card has already battled
        if(currentSelection.hasAttacked()) {
            System.out.println("Already Battled");
            return;
        }

        //Check if battling a card
        int defendingPlayer = game.getCurrentPlayer()==1?2:1;
        ExtendedSprite defendingCard = SpriteLogic.checkIntersection(defendingPlayer,x,y);

        //Do battle against another card
        if(defendingCard != null) {
            //PLay battle sound
            game.playBattleSound();
            PhaseLogic.doBattle(game.getCurrentPlayer(), currentSelection, defendingCard);
            return;
        }

        //Check opponents field is empty
        CardCollection cardCollection = game.getCardCollection(defendingPlayer,"Field");
        if(cardCollection instanceof Field) {
            Field defendingField = (Field) cardCollection;
            if (defendingField.sizeMonsters() != 0) {
                return;
            }
        }

        //Do a direct battle
        float middle = Gdx.graphics.getHeight()/2;
        if((y > middle && defendingPlayer == 1) || (y < middle && defendingPlayer == 2)) {
            //PLay battle sound
            game.playBattleSound();
            PhaseLogic.doDirectBattle(game.getCurrentPlayer(), currentSelection);
        }
    }

    public static void doBattle(int player, ExtendedSprite s1, ExtendedSprite s2) {

        AbstractMonster m1 = (AbstractMonster) s1.getCard();
        AbstractMonster m2 = (AbstractMonster) s2.getCard();

        MainGame game = DeerForestSingletonGetter.getDeerForest().mainGame;
        MainGameScreen view = DeerForestSingletonGetter.getDeerForest().view;

        if(m2.takeDamage(m1.getAttack(), m1.getType())) {
            //Move card in the model
            List<AbstractCard> cardToMove = new ArrayList<AbstractCard>();
            cardToMove.add(s2.getCard());
            int defendingPlayer = game.getCurrentPlayer()==1?2:1;

            if(!game.moveCards(defendingPlayer, cardToMove, "Field", "Graveyard")) {
                System.out.println("Didn't Move, cardToMove was: " + cardToMove + " defendingPlayer: " + defendingPlayer);
            } else {
                System.out.println("Did Move, cardToMove was: " + cardToMove + " defendingPlayer: " + defendingPlayer);
            }

            //Remove card from view
            view.removeSprite(s2);
            view.getArena().removeSprite(s2);
        }

        //Show battle
        view.setBattleSprites(s1, s2, m1.getAttack());

        System.out.println("Card health: " + m2.getCurrentHealth());

        s1.setHasAttacked(true);
    }

    public static void doDirectBattle(int player, ExtendedSprite s1) {

        System.out.println("Direct attack Player: " + player + " s1: " + s1);

        MainGame game = DeerForestSingletonGetter.getDeerForest().mainGame;
        MainGameScreen view = DeerForestSingletonGetter.getDeerForest().view;

        System.out.println("Card attached to sprite: " + s1.getCard());

        AbstractMonster m = null;

        if(s1.getCard() instanceof AbstractMonster) {
            m = (AbstractMonster) s1.getCard();
            int defendingPlayer = game.getCurrentPlayer()==1?2:1;
            game.inflictDamage(defendingPlayer, m.getAttack());
            //Show attack
            view.setBattleSprites(s1, null, m.getAttack());
        }

        s1.setHasAttacked(true);
    }
    
}
