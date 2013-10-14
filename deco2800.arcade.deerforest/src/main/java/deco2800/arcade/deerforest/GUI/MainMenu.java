package deco2800.arcade.deerforest.GUI;

import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.arcade.deerforest.models.cardContainers.CardCollection;
import deco2800.arcade.deerforest.models.cards.AbstractCard;
import deco2800.arcade.deerforest.models.cards.AbstractMonster;
import deco2800.arcade.deerforest.models.gameControl.GameSystem;

//This class functions as sort of a higher level game system controller
//As well as (most importantly) being an instance of a game (according to Gdx)
//to run
public class MainMenu extends Game {

	SpriteBatch batch;
	BitmapFont font;
	final private GameSystem model;
    private Music bgLoop;
    private Sound battleSoundEffect;
    private Sound phaseSoundEffect;
    private boolean effectsMuted;
    private boolean musicMuted;
    private final boolean muted = false;

	
	public MainMenu(GameSystem model) {
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

		this.setScreen(new MainMenuScreen(this));

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
	
}
