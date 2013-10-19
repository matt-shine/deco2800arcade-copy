package deco2800.arcade.deerforest.GUI;

import com.badlogic.gdx.Gdx;
import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cardContainers.Field;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles all the phase logic (mostly comprised of the battle logic)
 */
public class PhaseLogic {

    /**
     * Given current selection, takes in a point and tries to do battle between
     * a sprite at that location
     *
     * @param x the x point to battle against
     * @param y the y point to battle against
     */
    public static void battlePhaseSelection(int x, int y) {

        ExtendedSprite currentSelection = DeerForestSingletonGetter.getDeerForest().inputProcessor.getCurrentSelection();
        MainGame game = DeerForestSingletonGetter.getDeerForest().mainGame;

        //Check if card has already battled
        if(currentSelection.hasAttacked()) {
            DeerForest.logger.info("Already Battled");
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

    /**
     * Battles two sprites against each other, subtracting the attacking cards
     * attack from the others health. Removes any card that dies due to the battling
     *
     * @param player the player who initiated the battling
     * @param s1 the attacking card
     * @param s2 the defending card
     */
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
                DeerForest.logger.info("Didn't Move, cardToMove was: " + cardToMove + " defendingPlayer: " + defendingPlayer);
            } else {
                DeerForest.logger.info("Did Move, cardToMove was: " + cardToMove + " defendingPlayer: " + defendingPlayer);
            }

            //Remove card from view
            view.removeSprite(s2);
            view.getArena().removeSprite(s2);

            //if field is empty in view, remove all model stuff
            String area;
            if(defendingPlayer==1) {
                area = "P1MonsterZone";
            } else {
                area = "P2MonsterZone";
            }
            if(view.getSpriteMap().get(area).isEmpty()) {
                DeerForest.logger.info("Wiped monsters");
                ((Field) game.getCardCollection(defendingPlayer, "Field")).destroyAllMonsters();
            }
        }

        //Show battle
        view.setBattleSprites(s1, s2, m1.getAttack());

        DeerForest.logger.info("Card health: " + m2.getCurrentHealth());

        s1.setHasAttacked(true);
    }

    /**
     * Does a direct battle against the opposing player, subtracting the cards
     * attack from their lifepoints
     *
     * @param player the player who is attacking
     * @param s1 the card that is attacking
     */
    public static void doDirectBattle(int player, ExtendedSprite s1) {

        DeerForest.logger.info("Direct attack Player: " + player + " s1: " + s1);

        MainGame game = DeerForestSingletonGetter.getDeerForest().mainGame;
        MainGameScreen view = DeerForestSingletonGetter.getDeerForest().view;

        DeerForest.logger.info("Card attached to sprite: " + s1.getCard());

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
