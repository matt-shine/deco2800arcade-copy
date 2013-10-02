package deco2800.arcade.deerforest.GUI;


import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.AbstractSpell;
import deco2800.arcade.deerforest.models.cards.GeneralSpell;
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

    boolean needsSelection;
    MainGame game;
    MainGameScreen view;
    MainInputProcessor processor;
    Map<String, LinkedHashMap<String, List<Integer>>> continuousEffects;
    LinkedHashMap<String, List<Integer>> effectQueue;
    Random random;
    ExtendedSprite currentSprite;
    AbstractSpell currentSpell;
    int currentPlayer;

    //Parameters for what has been selected
    boolean hasDrawn;

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
        System.out.println();
        System.out.println("Map is: " + effectQueue);
        System.out.println("HANDLED CLICK, swapped needs selection");
        System.out.println();

        //If there is nothing at click do nothing
        ExtendedSprite s = SpriteLogic.checkIntersection(game.getCurrentPlayer(), x, y);
        if(s == null) {
            return;
        }
        List<String> keysToRemove = new ArrayList<String>();

        Set<String> keys = effectQueue.keySet();
        for(String effectType : keys) {
            //Check what effect we are trying to activate
            if(effectType.equals("Destroy")) {
                //If effect requires no interaction then keep going
                if(!doDestroy(effectQueue.get(effectType))) {
                    break;
                }
            } else if(effectType.contains("Draw")) {
                String area = view.getArena().getAreaAtPoint(x, y);
                if(!area.contains("Hand")) break; //Break if card isn't in hand
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
            } else if(effectType.equals("Monster")) {
                //If effect requires no interaction then keep going
                if(!doMonster(effectQueue.get(effectType))) {
                    break;
                }
            } else if(effectType.equals("Search")) {
                //If effect requires no interaction then keep going
                if(!doSearch(effectQueue.get(effectType))) {
                    break;
                }
            } else if(effectType.equals("Player")) {
                //If effect requires no interaction then keep going
                if(!doPlayer(effectQueue.get(effectType))) {
                    break;
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
            if(effectType.equals("Destroy")) {
                //If effect requires no interaction then keep going
                if(!doDestroy(effectQueue.get(effectType))) {
                    break;
                }
            } else if(effectType.contains("Draw")) {
                //Try to do draw
                if(!doDraw(effectQueue.get(effectType))) {
                    break;
                } else {
                    keysToRemove.add(effectType);
                }
            } else if(effectType.equals("Monster")) {
                //If effect requires no interaction then keep going
                if(!doMonster(effectQueue.get(effectType))) {
                    break;
                }
            } else if(effectType.equals("Search")) {
                //If effect requires no interaction then keep going
                if(!doSearch(effectQueue.get(effectType))) {
                    break;
                }
            } else if(effectType.equals("Player")) {
                //If effect requires no interaction then keep going
                if(!doPlayer(effectQueue.get(effectType))) {
                    break;
                }
            }
        }

        for(String key : keysToRemove) {
            effectQueue.remove(key);
        }

    }

    private boolean doDestroy(List<Integer> effectParameters) {
        System.out.println("DID DESTROY");
        return false;
    }

    /**
     * Performs the 'draw' effect, waiting for selection if required
     *
     * @param effectParameters
     * @return
     */
    private boolean doDraw(List<Integer> effectParameters) {
        System.out.println("DID DRAW");

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

        return true;
    }

    private boolean doMonster(List<Integer> effectParameters) {
        System.out.println("DID MONSTER");
        return false;
    }

    private boolean doSearch(List<Integer> effectParameters) {
        System.out.println("DID SEARCH");
        return false;
    }

    private boolean doPlayer(List<Integer> effectParameters) {
        System.out.println("DID PLAYER");
        return false;
    }
}

