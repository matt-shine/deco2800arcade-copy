package deco2800.arcade.deerforest.GUI;

import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.AbstractMonster;
import deco2800.arcade.deerforest.models.gameControl.GameSystem;

//This class functions as sort of a higher level game system controller
//As well as (most importantly) being an instance of a game (according to Gdx)
//to run
public class MainGame extends Game {

	SpriteBatch batch;
	BitmapFont font;
	final private GameSystem model;
    private Music bgLoop;
    private Sound battleSoundEffect;
    @SuppressWarnings("unused")
	private Sound phaseSoundEffect;
    private boolean effectsMuted;
    private boolean musicMuted;
    private final boolean muted = false;

	
	public MainGame(GameSystem model) {
		this.model = model;
	}
	
	@Override
	public void create() {
		batch = new SpriteBatch();

		//Use LibGDX's default Arial font
        Texture texture = new Texture(Gdx.files.internal("DeerForestAssets/Deco_0.tga"), true); // true enables mipmaps
        texture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        font = new BitmapFont(Gdx.files.internal("DeerForestAssets/Deco.fnt"), true);
        font.setScale(0.5f);

		this.setScreen(new MainGameScreen(this));	
		model.startgame(true);

        //Play music
        FileHandle audioPath = new FileHandle("DeerForestAssets/QuickSilver.wav");
        bgLoop = Gdx.audio.newMusic(audioPath);
        bgLoop.setLooping(true);
        bgLoop.setVolume(0.5f);
        if(!muted) {
            bgLoop.play();
        }
        battleSoundEffect = null;
        phaseSoundEffect = null;
	}
	
	public void render() {
        super.render();
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
        bgLoop.dispose();
	}
	
	public GameSystem getModel() {
		return this.model;
	}
	
	public void changeTurns() {
		
		//check to see if we are on start phase already
		if(model.getPhase().equals("StartPhase")) model.nextPhase();
		
		//Go to next phases till end of turn
		while(!model.getPhase().equals("StartPhase")) {
			model.nextPhase();
		}
	}
	
	public void nextPhase() {
        playPhaseSound();
		model.nextPhase();
	}
	
	public String getPhase() {
		return model.getPhase();
	}
	
	public int getCurrentPlayer() {
		return model.currentPlayer()==model.player1()?1:2;
	}
	
	public boolean getSummoned() {
		return model.getSummoned();
	}
	
	public void setSummoned(boolean b) {
		model.setSummoned(b);
	}

	public int getPlayerLP(int player) {
		return model.playerLP(player);
	}
	
	public CardCollection getCardCollection(int player, String area) {
		return model.getCardCollection(player, area);
	}
	
	public AbstractCard draw(int player) {
		return model.draw(player);
	}
	
	public boolean moveCards(int player, List<AbstractCard> cards, String oldLocation, String newLocation) {
		
		if(cards == null || oldLocation == null || newLocation == null) return false;
		
		CardCollection srcCards = null;
		CardCollection destCards = null;
		
		if(oldLocation.contains("Hand")) {
			srcCards = model.getCardCollection(player, "Hand");
		} else if(oldLocation.contains("Deck")) {
			srcCards = model.getCardCollection(player, "Deck");
		} else if(oldLocation.contains("Field") || oldLocation.contains("Monster") 
				|| oldLocation.contains("Spell")) {
			srcCards = model.getCardCollection(player, "Field");
            resetMonsters(cards);
		} else if(oldLocation.contains("Graveyard")) {
			srcCards = model.getCardCollection(player, "Graveyard");
		}
		
		if(newLocation.contains("Hand")) {
			destCards = model.getCardCollection(player, "Hand");
		} else if(newLocation.contains("Deck")) {
			destCards = model.getCardCollection(player, "Deck");
		} else if(newLocation.contains("Field") || newLocation.contains("Monster") 
				|| newLocation.contains("Spell")) {
			destCards = model.getCardCollection(player, "Field");
		} else if(newLocation.contains("Graveyard")) {
			destCards = model.getCardCollection(player, "Graveyard");
		}

        boolean b = model.moveCards(player, cards, srcCards, destCards);
        if(!b) {
            System.out.println("Error moving with cards: " + cards + " srcCards: " + srcCards + " destCards: " + destCards);
        } else {
            System.out.println("No error moving with cards: " + cards + " srcCards: " + srcCards + " destCards: " + destCards);
        }
		return b;
	}

    public void toggleMuted() {

        this.effectsMuted = !effectsMuted;
        if(bgLoop.isPlaying()) {
            bgLoop.pause();
        } else {
            bgLoop.play();
        }

        this.musicMuted = !musicMuted;
    }

    public void playBattleSound() {
        //dispose previous sound
        if(battleSoundEffect != null) {
            battleSoundEffect.dispose();
        }
        //Play sound
        if(!muted && !effectsMuted) {
            battleSoundEffect = Gdx.audio.newSound(new FileHandle("DeerForestAssets/Battle.mp3"));
            long id = battleSoundEffect.play();
            battleSoundEffect.setVolume(id, 1.0f);
        }
    }

    public void playPhaseSound() {
//        //dispose previous sound
//        if(phaseSoundEffect != null) {
//            phaseSoundEffect.dispose();
//        }
//        //Play sound
//        if(!effectsMuted) {
//            phaseSoundEffect = Gdx.audio.newSound(new FileHandle("DeerForestAssets/PhaseChange.wav"));
//            long id = phaseSoundEffect.play();
//            phaseSoundEffect.setVolume(id, 1.0f);
//        }
    }

    public void inflictDamage(int player, int amount) {
        model.inflictDamage(player, amount);
    }

    private void resetMonsters(List<AbstractCard> cards) {
        for(AbstractCard c : cards) {
            if(c instanceof AbstractMonster) {
                ((AbstractMonster)c).resetStats();
            }
        }
    }

    public void setCurrentPlayer(int player) {
        if(player == 1 || player == 2) {
            this.model.setCurrentPlayer(player);
        }
    }

    public void setFirstTurn(boolean b) {
        model.setFirstTurn(b);
    }
}
