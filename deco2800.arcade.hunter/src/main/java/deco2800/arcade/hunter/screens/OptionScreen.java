package deco2800.arcade.hunter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
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

import deco2800.arcade.hunter.Hunter;

public class OptionScreen implements Screen {

	final private Hunter game;
	private Stage stage;

	private boolean music;
	private boolean sound;
	private int volume;

	public OptionScreen( Hunter game){
		this.game = game;
		stage = new Stage();
		getPreferences();
		
		
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		//set table defaults
		table.defaults().spaceBottom(20);
		table.columnDefaults(0).colspan(3);
		table.add("Options Menu").colspan(3);
		
		//Add a music checkbox to the table		
		CheckBox musicCheckBox = new CheckBox("", new Skin());
		musicCheckBox.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Change music preference!");
			}
			
		});
		table.add("Music");
		table.add(musicCheckBox).colspan(2);
		table.row();
		
		//adds a sound checkbox to the table
		CheckBox soundCheckBox = new CheckBox("",new Skin());
		soundCheckBox.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Changed sound preference!");
			}
		});
		
		table.add("Sound");
		table.add(soundCheckBox).colspan(2);
		table.row();
		
		//adds a slider for volume
		Slider volumeSlider = new Slider( 0f, 1f, 0.1f, false, new Skin());
        volumeSlider.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
                float value = ( (Slider) actor ).getValue();
                System.out.println("Volums is changed to"  + value);
            }
        } );
        
        table.add("Volume");
        table.add(volumeSlider).colspan(2);
        table.row();
        
        //Add a back to mainm menu button
        TextButton backButton = new TextButton("Back to main menu", new Skin());
        backButton.addListener(new ClickListener(){
        	@Override
            public void touchUp(InputEvent event,float x,float y,int pointer,int button ){
        		System.out.println("Going back to the main menu!");
        	}
        });
        
        table.add("backButton").size(300,70);
        table.row();
	}

	private void getPreferences() {
		/* Loads the preferences from a file */
		music = false;
		sound = false;
		volume = 100;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

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
		game.dispose();
	}

}
