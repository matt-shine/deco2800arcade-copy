package deco2800.arcade.deerforest.GUI;


import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cards.*;
import deco2800.arcade.deerforest.models.effects.SpellEffect;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Segmelsian
 * Date: 30/09/13
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpellLogic {

    private boolean needsSelection;
    private MainGame game;
    private MainGameScreen view;
    private MainInputProcessor processor;
    private Map<String, LinkedHashMap<String, List<Integer>>> continuousEffects;
    private LinkedHashMap<String, List<Integer>> effectQueue;
    private Random random;
    private ExtendedSprite currentSprite;
    private AbstractSpell currentSpell;
    private int currentPlayer;

    //Parameters for what has been selected
    private boolean hasDrawn;

    //Destroy cards parameters
    private String destroyLoc;
    private int destroyPlayer;
    private AbstractCard destroyCard;
    private Set<String> destroyType;

    //Monster buff parameters
    private int monsterPlayer;
    private int monsterStat;
    private AbstractCard monsterCard;
    private Set<String> monsterType;

    //TODO Make way of destroying continuous effects

    /**
     * Constructs a new representation of a SpellLogic class
     *
     * @param game The game SpellLogic uses to tell the state of the game
     * @param view The view of the game, used for interpreting interactions
     */
    public SpellLogic(MainGame game, MainGameScreen view, MainInputProcessor processor) {
        this.needsSelection = false;
        this.game = game;
        this.view = view;
        this.processor = processor;
        this.effectQueue = new LinkedHashMap<String, List<Integer>>();

        this.continuousEffects = new HashMap<String, LinkedHashMap<String, List<Integer>>>();
        LinkedHashMap<String, List<Integer>> P1EndPhase = new LinkedHashMap<String, List<Integer>>();
        LinkedHashMap<String, List<Integer>> P2EndPhase = new LinkedHashMap<String, List<Integer>>();
        this.continuousEffects.put("EndPhase1", P1EndPhase);
        this.continuousEffects.put("EndPhase2", P2EndPhase);

        this.random = new Random();

        this.hasDrawn = false;
    }

    /**
     * Activates a spell card, by updating the effectQueue accordingly,
     * then performing an effect
     *
     * @param player
     * @param spell
     */
    public void activateSpell(int player, AbstractSpell spell, ExtendedSprite sprite) {

        System.out.println("ACTIVATED SPELL");

        SpellEffect effects = spell.getSpellEffect();
        this.currentSpell = spell;
        this.currentSprite = sprite;
        this.currentPlayer = player;

        for(int i = 0; i < effects.effectCategories().size(); i++) {
            //Update the effectQueue if effect is instant or Continuous effects
            switch(effects.effectParameter().get(i).get(4)) {
                case 0:
                    this.effectQueue.put(effects.effectCategories().get(i) + (new Integer(i)).toString(),
                            effects.effectParameter().get(i));
                    break;
                case 1:
                    if(player == 1) {
                        this.continuousEffects.get("EndPhase1").put(effects.effectCategories().get(i) +
                                (new Integer(random.nextInt())).toString(), effects.effectParameter().get(i));
                    } else {
                        this.continuousEffects.get("EndPhase2").put(effects.effectCategories().get(i) +
                                (new Integer(random.nextInt())).toString(), effects.effectParameter().get(i));
                    }
                    break;
                case 2:
                    //Swap who the card effects as it happens on the opponents turn
                    if(effects.effectParameter().get(i).get(3) == 1) effects.effectParameter().get(i).set(3, 2);
                    else if(effects.effectParameter().get(i).get(3) == 2) effects.effectParameter().get(i).set(3, 1);
                    if(player == 1) {
                        this.continuousEffects.get("EndPhase2").put(effects.effectCategories().get(i) +
                                (new Integer(random.nextInt())).toString(), effects.effectParameter().get(i));
                    } else {
                        this.continuousEffects.get("EndPhase1").put(effects.effectCategories().get(i) +
                                (new Integer(random.nextInt())).toString(), effects.effectParameter().get(i));
                    }
                    break;
                case 3:
                    this.continuousEffects.get("EndPhase1").put(effects.effectCategories().get(i) +
                            (new Integer(random.nextInt())).toString(), effects.effectParameter().get(i));
                    this.continuousEffects.get("EndPhase2").put(effects.effectCategories().get(i) +
                            (new Integer(random.nextInt())).toString(), effects.effectParameter().get(i));
                    break;
            }
        }

        doEffect();

        //Move general spell to graveyard if empty
        if(effectQueue.isEmpty() && spell instanceof GeneralSpell) {
            view.getArena().removeSprite(sprite);
            view.removeSprite(sprite);
            List<AbstractCard> cards = new ArrayList<AbstractCard>();
            cards.add(spell);
            game.moveCards(player, cards, "Spell", "Graveyard");
        }

    }

    /**
     * Handles a click, always called when spellLogic is waiting for some user
     * input, after click is handled it tries to continue going through the
     * effectQueue, stopping only when another selection is required
     *
     * @param x
     * @param y
     */
    public void handleClick(int x, int y) {
        //If there is nothing at click do nothing
        ExtendedSprite s = SpriteLogic.checkIntersection(1, x, y);
        if(s == null) {
            s = SpriteLogic.checkIntersection(2, x, y);
            if(s == null) {
                return;
            }
        }

        String area = view.getArena().getAreaAtPoint(x, y);
        AbstractCard card = s.getCard();

        List<String> keysToRemove = new ArrayList<String>();

        Set<String> keys = effectQueue.keySet();
        for(String effectType : keys) {
            //Check what effect we are trying to activate
            if(effectType.contains("Destroy")) {
                if(!keys.iterator().next().equals(effectType)) {
                    if(!doDestroy(effectQueue.get(effectType))) {
                        break;
                    } else {
                        keysToRemove.add(effectType);
                        continue;
                    }
                }
                //Check card is in right zone
                CardCollection possibleDestroy;
                //Check what the actual number of possible cards to destroy are
                if(this.destroyPlayer != 3) {
                    possibleDestroy = game.getCardCollection(this.destroyPlayer, this.destroyLoc);
                } else {
                    possibleDestroy = game.getCardCollection(1, this.destroyLoc);
                    possibleDestroy.addAll(game.getCardCollection(2, this.destroyLoc));
                }
                //Check if selected card is in right area
                if(!possibleDestroy.contains(card)) {
                    System.out.println("Wrong area");
                    break;
                }
                //See if the selected card is valid to be destroyed
                boolean valid = false;
                if(this.destroyType.isEmpty()) {
                    valid = true;
                }
                //if can destroy any monster:
                if(this.destroyType.contains("Monster")) {
                    if(card instanceof AbstractMonster) {
                        valid = true;
                    }
                }
                //See if card is of same destroy type
                if(this.destroyCard != null && card.getClass() == this.destroyCard.getClass()) {
                    valid = true;
                }
                //The card was valid
                if(valid) {
                    effectQueue.get(effectType).set(0, effectQueue.get(effectType).get(0) - 1);
                    List<AbstractCard> cards = new ArrayList<AbstractCard>();
                    cards.add(s.getCard());
                    game.moveCards(game.getCurrentPlayer(), cards, area, "Graveyard");
                    view.removeSprite(s);
                    view.getArena().removeSprite(s);
                } else {
                    System.out.println("Not valid");
                    System.out.println("Selection was: " + s);
                    System.out.println("Selection was: " + s.getCard());
                }
                //If effect requires no interaction then keep going
                if(!doDestroy(effectQueue.get(effectType))) {
                    break;
                } else {
                    keysToRemove.add(effectType);
                }
            } else if(effectType.contains("Draw")) {
                if(!area.contains(game.getCurrentPlayer()+"Hand")) break; //Break if card isn't in hand
                //Do discard if on the first loop then continue the effect
                if(keys.iterator().next().equals(effectType)) {
                    if(effectQueue.get(effectType).get(1) > 0) {
                        effectQueue.get(effectType).set(1, effectQueue.get(effectType).get(1) - 1);
                    } else {
                        effectQueue.get(effectType).set(2, effectQueue.get(effectType).get(2) - 1);
                    }
                }
                List<AbstractCard> cards = new ArrayList<AbstractCard>();
                cards.add(s.getCard());
                game.moveCards(game.getCurrentPlayer(), cards, area, "Graveyard");
                view.removeSprite(s);
                view.getArena().removeSprite(s);
                if(!doDraw(effectQueue.get(effectType))) {
                    break;
                } else {
                    keysToRemove.add(effectType);
                }
            } else if(effectType.contains("Monster")) {
                //If not first in loop then try normal monster
                if(!keys.iterator().next().equals(effectType)) {
                    if(!doMonster(effectQueue.get(effectType))) {
                        break;
                    } else {
                        keysToRemove.add(effectType);
                        continue;
                    }
                }
                //Check card is in right zone
                CardCollection possibleBuff;
                //Check what the actual number of possible cards to buff are
                if(this.monsterPlayer != 3) {
                    possibleBuff = game.getCardCollection(this.monsterPlayer, "Field");
                } else {
                    possibleBuff = game.getCardCollection(1, "Field");
                    possibleBuff.addAll(game.getCardCollection(2, "Field"));
                }
                //Check if selected card is in right area
                if(!possibleBuff.contains(card)) {
                    System.out.println("Wrong area");
                    break;
                }
                //See if the selected card is valid to be destroyed
                boolean valid = false;
                if(this.monsterType.isEmpty()) {
                    valid = true;
                }
                //if can destroy any monster:
                if(this.monsterType.contains("Monster")) {
                    if(card instanceof AbstractMonster) {
                        valid = true;
                    }
                }
                //See if card is of same destroy type
                if(this.monsterCard != null && card.getClass() == this.monsterCard.getClass()) {
                    valid = true;
                }
                //The card was valid, apply buff
                if(valid) {
                    AbstractMonster monster = (AbstractMonster) card;
                    if(effectQueue.get(effectType).get(2) == 0) {
                        monster.buffAttack(effectQueue.get(effectType).get(3));
                    } else if(effectQueue.get(effectType).get(2) == 0) {
                        monster.buffHealth(effectQueue.get(effectType).get(3));
                    } else {
                        monster.buff(effectQueue.get(effectType).get(3));
                    }
                    //Decrease the amount of monsters left
                    effectQueue.get(effectType).set(0, effectQueue.get(effectType).get(0) - 1);
                    //Remove monster if health is 0
                    if(monster.getCurrentHealth() <= 0) {
                        List<AbstractCard> cards = new ArrayList<AbstractCard>();
                        cards.add(s.getCard());
                        game.moveCards(game.getCurrentPlayer(), cards, area, "Graveyard");
                        view.removeSprite(s);
                        view.getArena().removeSprite(s);
                    }
                } else {
                    System.out.println("Not valid");
                    System.out.println("Selection was: " + s);
                    System.out.println("Selection was: " + s.getCard());
                }
                //If effect requires no interaction then keep going
                if(!doMonster(effectQueue.get(effectType))) {
                    break;
                } else {
                    keysToRemove.add(effectType);
                }
            } else if(effectType.contains("Search")) {
                //If effect requires no interaction then keep going
                if(!doSearch(effectQueue.get(effectType))) {
                    break;
                }
            } else if(effectType.contains("Player")) {
                //If effect requires no interaction then keep going
                if(!doPlayer(effectQueue.get(effectType))) {
                    break;
                } else {
                    keysToRemove.add(effectType);
                }
            }
        }

        for(String key : keysToRemove) {
            effectQueue.remove(key);
        }

        //If complete move general spell to graveyard and don't require selection
        if(effectQueue.isEmpty()) {
            this.needsSelection = false;
            if(currentSpell instanceof GeneralSpell) {
                view.getArena().removeSprite(currentSprite);
                view.removeSprite(currentSprite);
                List<AbstractCard> cards = new ArrayList<AbstractCard>();
                cards.add(currentSpell);
                game.moveCards(currentPlayer, cards, "Spell", "Graveyard");
            }
        }
    }

    public boolean needsSelection() {
        return this.needsSelection;
    }

    /**
     * On each change of phase (though only works for ends currently) activate
     * all the continuous effects that exist on the field currently
     *
     * @param player
     * @param phase
     */
    public void activateContinuousEffects(int player, String phase) {

        if(this.continuousEffects.get(phase + (new Integer(player)).toString()) == null) {
            this.effectQueue = new LinkedHashMap<String, List<Integer>>();
            return;
        }

        this.effectQueue = new LinkedHashMap<String, List<Integer>>();

        LinkedHashMap<String, List<Integer>> contEff =
                this.continuousEffects.get(phase + (new Integer(player)).toString());

        for(String key : contEff.keySet()) {
            List<Integer> params = contEff.get(key);
            this.effectQueue.put(key, new ArrayList<Integer>(params));
        }

        doEffect();

    }

    /**
     * Starts up the effectQueue, stops when selection is required or if all
     * effects are completed
     */
    private void doEffect() {

        List<String> keysToRemove = new ArrayList<String>();

        Set<String> keys = effectQueue.keySet();
        for(String effectType : keys) {
            //Check what effect we are trying to activate
            if(effectType.contains("Destroy")) {
                //If effect requires no interaction then keep going
                if(!doDestroy(effectQueue.get(effectType))) {
                    break;
                } else {
                    keysToRemove.add(effectType);
                }
            } else if(effectType.contains("Draw")) {
                //Try to do draw
                if(!doDraw(effectQueue.get(effectType))) {
                    break;
                } else {
                    keysToRemove.add(effectType);
                }
            } else if(effectType.contains("Monster")) {
                //If effect requires no interaction then keep going
                if(!doMonster(effectQueue.get(effectType))) {
                    break;
                } else {
                    keysToRemove.add(effectType);
                }
            } else if(effectType.contains("Search")) {
                //If effect requires no interaction then keep going
                if(!doSearch(effectQueue.get(effectType))) {
                    break;
                }
            } else if(effectType.contains("Player")) {
                //If effect requires no interaction then keep going
                if(!doPlayer(effectQueue.get(effectType))) {
                    break;
                } else {
                    keysToRemove.add(effectType);
                }
            }
        }

        for(String key : keysToRemove) {
            effectQueue.remove(key);
        }

    }

    private boolean doDestroy(List<Integer> effectParameters) {
        System.out.println("DID DESTROY");

        //Set up parameters for what is allowed to be destroyed
        switch(effectParameters.get(1)) { //Set up location
            case 0:
                this.destroyLoc = "Hand";
                break;
            case 1:
                this.destroyLoc = "Field";
        }

        switch (effectParameters.get(2)) { //Set up player
            case 0:
                this.destroyPlayer = game.getCurrentPlayer();
                break;
            case 1:
                this.destroyPlayer = game.getCurrentPlayer()==1?2:1;
                break;
            case 2:
                this.destroyPlayer = 3; //3 is either
                break;
        }

        this.destroyType = new HashSet<String>();

        //Set up the type of cards that can be destroyed
        if(effectParameters.get(3) == 0 && effectParameters.get(5) == 0) {
            this.destroyType.add("Monster");
        } else if(effectParameters.get(3) == 0 && effectParameters.get(5) == 1) {
            this.destroyCard = new FireMonster(10, 10, "NA"); //Fire monster
            this.destroyType.add("Fire");
        } else if(effectParameters.get(3) == 0 && effectParameters.get(5) == 2) {
            this.destroyCard = new NatureMonster(10, 10, "NA"); //Nature monster
            this.destroyType.add("Nature");
        } else if(effectParameters.get(3) == 0 && effectParameters.get(5) == 3) {
            this.destroyCard = new WaterMonster(10, 10, "NA"); //Water monster
            this.destroyType.add("Water");
        } else if(effectParameters.get(3) == 0 && effectParameters.get(5) == 4) {
            this.destroyCard = new DarkMonster(10, 10, "NA"); //Dark monster
            this.destroyType.add("Dark");
        } else if(effectParameters.get(3) == 0 && effectParameters.get(5) == 5) {
            this.destroyCard = new LightMonster(10, 10, "NA"); //Light monster
            this.destroyType.add("Light");
        }  else if(effectParameters.get(3) == 1 && effectParameters.get(5) == 1) {
            this.destroyCard = new FieldSpell(null, "NA"); //Field spell
            this.destroyType.add("Spell");
        }

        CardCollection possibleDestroy;
        //Check what the actual number of possible cards to destroy are
        if(this.destroyPlayer != 3) {
            possibleDestroy = game.getCardCollection(this.destroyPlayer, this.destroyLoc);
        } else {
            possibleDestroy = game.getCardCollection(1, this.destroyLoc);
            possibleDestroy.addAll(game.getCardCollection(2, this.destroyLoc));
        }

        //Iterate over all the cards in the right areas and see how many are
        //able to be destroyed
        int canDestroy = 0;
        for(AbstractCard card : new ArrayList<AbstractCard>(possibleDestroy)) {
            //If can destroy any then increase counter
            System.out.println("Card: " + card);
            if(this.destroyType.isEmpty()) {
                canDestroy++;
                continue;
            }
            //if can destroy any monster:
            if(this.destroyType.contains("Monster")) {
                if(card instanceof AbstractMonster) {
                    canDestroy++;
                    continue;
                }
            }
            //See if card is of same destroy type
            if(this.destroyCard != null && card.getClass() == this.destroyCard.getClass()) {
                canDestroy++;
            }
        }

        //If the amount to destroy is greater than the possible targets, lower
        //the targets
        if(effectParameters.get(0) > canDestroy) {
            effectParameters.set(0, canDestroy);
        }

        System.out.println("Before setting stuff");
        if(this.destroyType == null || this.destroyType.isEmpty()) {
            view.setEffectMessage("Currently performing a destroy effect" + System.getProperty("line.separator") +
                    "You must destroy " + effectParameters.get(0) + " cards" + System.getProperty("line.separator") +
                    "They can be of any type" + System.getProperty("line.separator") +
                    "They must be from " + this.destroyLoc);
        } else {
            view.setEffectMessage("Currently performing a destroy effect" + System.getProperty("line.separator") +
                    "You must destroy "+ effectParameters.get(0) + " cards" + System.getProperty("line.separator") +
                    "They must be of type: " + this.destroyType.iterator().next() + System.getProperty("line.separator") +
                    "They must be from " + this.destroyLoc);
        }

        //Needs selecting if there are valid cards to destroy, check them
        if(effectParameters.get(0) <= 0) {
            this.needsSelection = false;
            view.setEffectMessage(null);
            return true;
        } else {
            this.needsSelection = true;
            return false;
        }
    }

    /**
     * Performs the 'draw' effect, waiting for selection if required
     *
     * @param effectParameters
     * @return
     */
    private boolean doDraw(List<Integer> effectParameters) {
        System.out.println("DID DRAW");

        System.out.println("Before setting stuff");
        view.setEffectMessage("Currently performing a draw effect" + System.getProperty("line.separator") +
                "You must discard " + effectParameters.get(1) + " cards before" + System.getProperty("line.separator") +
                "You must discard " +  effectParameters.get(2) + " cards after");

        //Check if cards need to be discarded before draw
        if(effectParameters.get(1) > 0) {
            //Wait for user to discard cards
            needsSelection = true;
            return false;
        }

        //Draw cards
        if(effectParameters.get(1) == 0 && !hasDrawn) {
            switch(effectParameters.get(3)) {
                case 0:
                    for(int i = 0; i < effectParameters.get(0); i++) {
                        processor.doDraw();
                    }
                    break;

                case 1:
                    game.changeTurns();
                    for(int i = 0; i < effectParameters.get(0); i++) {
                        processor.doDraw();
                    }
                    game.changeTurns();
                    break;

                case 2:
                    game.changeTurns();
                    for(int i = 0; i < effectParameters.get(0); i++) {
                        processor.doDraw();
                    }
                    game.changeTurns();
                    for(int i = 0; i < effectParameters.get(0); i++) {
                        processor.doDraw();
                    }
                    break;
            }
        }

        //Check if cards need to be discarded after
        if(effectParameters.get(2) > 0) {
            //Wait for user to discard cards
            needsSelection = true;
            this.hasDrawn = true;
            return false;
        }

        this.hasDrawn = false;

        view.setEffectMessage(null);
        return true;
    }

    private boolean doMonster(List<Integer> effectParameters) {
        System.out.println("DID MONSTER");

        switch (effectParameters.get(1)) { //Set up player
            case 0:
                this.monsterPlayer = game.getCurrentPlayer();
                break;
            case 1:
                this.monsterPlayer = game.getCurrentPlayer()==1?2:1;
                break;
            case 2:
                this.monsterPlayer = 3; //3 is either
                break;
        }

        this.monsterStat = effectParameters.get(2);

        this.monsterType = new HashSet<String>();

        //Set up the type of cards that can be buffed
        if(effectParameters.get(5) == 0) {
            this.monsterType.add("Monster");
        } else if(effectParameters.get(5) == 1) {
            this.monsterCard = new FireMonster(10, 10, "NA"); //Fire monster
            this.monsterType.add("Fire");
        } else if(effectParameters.get(5) == 2) {
            this.monsterCard = new NatureMonster(10, 10, "NA"); //Nature monster
            this.monsterType.add("Nature");
        } else if(effectParameters.get(5) == 3) {
            this.monsterCard = new WaterMonster(10, 10, "NA"); //Water monster
            this.monsterType.add("Water");
        } else if(effectParameters.get(5) == 4) {
            this.monsterCard = new DarkMonster(10, 10, "NA"); //Dark monster
            this.monsterType.add("Dark");
        } else if(effectParameters.get(5) == 5) {
            this.monsterCard = new LightMonster(10, 10, "NA"); //Light monster
            this.monsterType.add("Light");
        }

        CardCollection possibleMonsters;
        //Check what the actual number of possible cards to buff are
        if(this.monsterPlayer != 3) {
            possibleMonsters = game.getCardCollection(this.monsterPlayer, "Field");
        } else {
            possibleMonsters = game.getCardCollection(1, "Field");
            possibleMonsters.addAll(game.getCardCollection(2, "Field"));
        }

        //Iterate over all the cards in the right areas and see how many are
        //able to be buffed
        int canBuff = 0;
        for(AbstractCard card : new ArrayList<AbstractCard>(possibleMonsters)) {
            //If can destroy any then increase counter
            System.out.println("Card: " + card);
            if(this.monsterType.isEmpty()) {
                canBuff++;
                continue;
            }
            //if can destroy any monster:
            if(this.monsterType.contains("Monster")) {
                if(card instanceof AbstractMonster) {
                    canBuff++;
                    continue;
                }
            }
            //See if card is of same destroy type
            if(this.monsterCard != null && card.getClass() == this.monsterCard.getClass()) {
                canBuff++;
            }
        }

        //If the amount to destroy is greater than the possible targets, lower
        //the targets
        if(effectParameters.get(0) > canBuff) {
            effectParameters.set(0, canBuff);
        }

        System.out.println("Before setting stuff");
        if(this.monsterType == null || this.monsterType.isEmpty()) {
            view.setEffectMessage("Currently performing a monster alter effect" + System.getProperty("line.separator") +
                    "You must alter " + effectParameters.get(0) + " monsters" + System.getProperty("line.separator") +
                    "They can be of any type");
        } else {
            view.setEffectMessage("Currently performing a monster alter effect" + System.getProperty("line.separator") +
                    "You must alter " + effectParameters.get(0) + " monsters" + System.getProperty("line.separator") +
                    "They must be of type: " + this.monsterType.iterator().next());
        }

        //Needs selecting if there are valid cards to buff
        if(effectParameters.get(0) <= 0) {
            this.needsSelection = false;
            view.setEffectMessage(null);
            return true;
        } else {
            this.needsSelection = true;
            return false;
        }
    }

    private boolean doSearch(List<Integer> effectParameters) {
        System.out.println("DID SEARCH");
        return false;
    }

    private boolean doPlayer(List<Integer> effectParameters) {
        System.out.println("DID PLAYER");

        switch (effectParameters.get(0)) { //Set up player
            case 0:
                game.inflictDamage(game.getCurrentPlayer(),
                        effectParameters.get(2));
                break;
            case 1:
                int player = game.getCurrentPlayer()==1?2:1;
                game.inflictDamage(player, effectParameters.get(2));
                break;
            case 2:
                game.inflictDamage(1, effectParameters.get(2));
                game.inflictDamage(2, effectParameters.get(2));
                break;
        }


        return false;
    }
}

