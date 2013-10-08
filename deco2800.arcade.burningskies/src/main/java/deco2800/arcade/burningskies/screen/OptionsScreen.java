package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.burningskies.Configuration;
import deco2800.arcade.client.ArcadeInputMux;

public class OptionsScreen implements Screen {
	
	private int masterVolume;
	private int effectsVolume;
	private int backgroundVolume;
	private int difficulty;
	
	private BurningSkies game;
    private Stage stage;
    private BitmapFont black;
    private BitmapFont white;
    private Skin skin;
    private SpriteBatch batch;
    private TextButton backButton;
    private Label label;
    private Image background;
	private MenuInputProcessor processor;
	private Slider masterVolumeSlider;
	private Slider effectsVolumeSlider;
	private Slider backgroundVolumeSlider;
	private Slider difficultySlider;
	private Label masterVolumeLabel;
	private Label effectsVolumeLabel;
	private Label backgroundVolumeLabel;
	private Label difficultyLabel;
    private int width = BurningSkies.SCREENWIDTH;
    private int height = BurningSkies.SCREENHEIGHT;
    
	
	public OptionsScreen(BurningSkies game) {
		this.game = game;
	}

	@Override
	public void dispose() {
		batch.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
        stage.dispose();
	}

	@Override
	public void hide() {
		game.stopSong();
		ArcadeInputMux.getInstance().removeProcessor(stage);
		ArcadeInputMux.getInstance().removeProcessor(processor);
		this.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        stage.act(delta);

        batch.begin();
        stage.draw();
        batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("images/menu/uiskin32.json"));
        white = new BitmapFont(Gdx.files.internal("images/menu/whitefont.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("images/menu/font.fnt"), false);
        background = new Image(new Texture(Gdx.files.internal("images/menu/menu_background.png")));
    
        stage = new Stage(width, height, true);
        
        masterVolume = Configuration.getMasterVolumeInt();
        effectsVolume = Configuration.getEffectsVolumeInt();
        backgroundVolume = Configuration.getBackgroundVolumeInt();
        difficulty = Configuration.getDifficulty();
	
        ArcadeInputMux.getInstance().addProcessor(stage);
        processor = new MenuInputProcessor(game);
    	ArcadeInputMux.getInstance().addProcessor(processor);

	    backButton = new TextButton("Back", skin);
	    backButton.setWidth(200);
	    backButton.setHeight(50);
	    backButton.setX((float)(width*0.02));
	    backButton.setY((float)(height*0.02));
	    
	    createSliders();
	    createListeners();
	    createLabels();
	    
	    stage.addActor(backButton);
	    stage.addActor(label);
	    stage.addActor(masterVolumeLabel);
	    stage.addActor(effectsVolumeLabel);
	    stage.addActor(backgroundVolumeLabel);
	    stage.addActor(difficultyLabel);
	    stage.addActor(background);
	    stage.addActor(masterVolumeSlider);
	    stage.addActor(effectsVolumeSlider);
	    stage.addActor(backgroundVolumeSlider);
	    stage.addActor(difficultySlider);
	    background.toBack();
	}
	public void createLabels() {
		LabelStyle ls = new LabelStyle(white, Color.WHITE);
	    label = new Label("Options", ls);
	    label.setX(0);
	    label.setY((float)(height*0.95));
	    label.setWidth(width);
	    label.setAlignment(Align.center);
	    
	    masterVolumeLabel = new Label("Master Volume", ls);
	    masterVolumeLabel.setX(0);
	    masterVolumeLabel.setY(height/2 + 130);
	    masterVolumeLabel.setWidth(width);
	    masterVolumeLabel.setAlignment(Align.center);
	    
	    effectsVolumeLabel = new Label("Effects Volume", ls);
	    effectsVolumeLabel.setX(0);
	    effectsVolumeLabel.setY(height/2 + 80);
	    effectsVolumeLabel.setWidth(width);
	    effectsVolumeLabel.setAlignment(Align.center);
	    
	    backgroundVolumeLabel = new Label("Background Volume", ls);
	    backgroundVolumeLabel.setX(0);
	    backgroundVolumeLabel.setY(height/2 + 30);
	    backgroundVolumeLabel.setWidth(width);
	    backgroundVolumeLabel.setAlignment(Align.center);
	    
	    difficultyLabel = new Label("Difficulty", ls);
	    difficultyLabel.setX(0);
	    difficultyLabel.setY(height/2 - 20);
	    difficultyLabel.setWidth(width);
	    difficultyLabel.setAlignment(Align.center);
	}
	
	public void createListeners() {
		backButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            	Configuration.writeConfig();
                game.setScreen(game.menuScreen);
            }
	    });
	    
	    masterVolumeSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Slider slider = (Slider) actor;

				float value = slider.getValue();
				if (value == 0) {
					Configuration.setMasterVolume(0);
				} else {
					Configuration.setMasterVolume((int) value);
				}
			}
		});
	    
	    effectsVolumeSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Slider slider = (Slider) actor;

				float value = slider.getValue();
				if (value == 0) {
					Configuration.setEffectsVolume(0);
				} else {
					Configuration.setEffectsVolume((int) value);
				}
			}
		});
	    
	    backgroundVolumeSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Slider slider = (Slider) actor;

				float value = slider.getValue();
				if (value == 0) {
					Configuration.setBackgroundVolume(0);
				} else {
					Configuration.setBackgroundVolume((int) value);
				}
			}
		});
	    
	    difficultySlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Slider slider = (Slider) actor;

				float value = slider.getValue();
				if (value == 0) {
					Configuration.setDifficulty(0);
				} else {
					Configuration.setDifficulty((int) value);
				}
			}
		});
	}
	
	public void createSliders() {
		masterVolumeSlider = new Slider(0, 100, 1, false, skin);
    	masterVolumeSlider.setBounds(width/2 - 320, height/2 + 100, 640, 50);
    	masterVolumeSlider.setValue(masterVolume);
    	
    	effectsVolumeSlider = new Slider(0, 100, 1, false, skin);
    	effectsVolumeSlider.setBounds(width/2 - 320, height/2 + 50, 640, 50);
    	effectsVolumeSlider.setValue(effectsVolume);
    	
    	backgroundVolumeSlider = new Slider(0, 100, 1, false, skin);
    	backgroundVolumeSlider.setBounds(width/2 - 320, height/2, 640, 50);
    	backgroundVolumeSlider.setValue(backgroundVolume);
    	
    	difficultySlider = new Slider(0, 4, 1, false, skin);
    	difficultySlider.setBounds(width/2 - 320, height/2 - 50, 640, 50);
    	difficultySlider.setValue(difficulty);
	}
}
