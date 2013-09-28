package deco2800.arcade.hunter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.hunter.Hunter;

public class OptionScreen implements Screen {

	private Hunter game;
	private Stage stage;
	private boolean music;
	private boolean sound;
	private float volume;
	private Skin skin;
	
	public OptionScreen(Hunter p){
		game = p;
		stage = new Stage();
		ArcadeInputMux.getInstance().addProcessor(stage);
		
		getPreferences();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		
		Table table = new Table(skin);
		table.setFillParent(true);
		stage.addActor(table);
		
		//set table defaults
		table.defaults().spaceBottom(20);
		table.columnDefaults(0).colspan(3);
		table.add("Options Menu").colspan(3);
		table.row();
		
		//Add a music checkbox to the table		
		final CheckBox musicCheckBox = new CheckBox("", skin);
		musicCheckBox.setChecked(music);
		musicCheckBox.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				music = musicCheckBox.isChecked();
				game.getPreferencesManager().setMusicEnabled(music);
				System.out.println("Change music preference!");
			}
			
		});
		table.add("Music");
		table.add(musicCheckBox).colspan(2);
		table.row();
		
		//adds a sound checkbox to the table
		final CheckBox soundCheckBox = new CheckBox("",skin);
		soundCheckBox.setChecked(sound);
		soundCheckBox.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sound = soundCheckBox.isChecked();
				game.getPreferencesManager().setSoundEnabled(sound);
				System.out.println("Changed sound preference!");
			}
		});
		
		table.add("Sound");
		table.add(soundCheckBox).colspan(2);
		table.row();
		
		//adds a slider for volume
		Slider volumeSlider = new Slider( 0f, 1f, 0.1f, false, skin);
		volumeSlider.setValue(volume);
        volumeSlider.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
                volume = ( (Slider) actor ).getValue();
                game.getPreferencesManager().setVolume(volume);
                System.out.println("Volume is changed to"  + volume);
            }
        } );
        
        table.add("Volume");
        table.add(volumeSlider).colspan(2);
        table.row();
        
        //Add a back to main menu button
        TextButton backButton = new TextButton("Back to main menu", skin);
        backButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
        		System.out.println("Going back to the main menu!");
        		game.setScreen(new MenuScreen(game));
        		stage.clear();
        	}
        });
        
        table.add(backButton).size(300,70);
        table.row();
	}

	private void getPreferences() {
		/* Loads the preferences from a file */
		music = game.getPreferencesManager().isMusicEnabled();
		System.out.println(music);
		sound = game.getPreferencesManager().isSoundEnabled();
		System.out.println(sound);
		volume = game.getPreferencesManager().getVolume();
		System.out.println(volume);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		Gdx.input.setInputProcessor(stage);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
		ArcadeInputMux.getInstance().removeProcessor(stage);
	}

}
