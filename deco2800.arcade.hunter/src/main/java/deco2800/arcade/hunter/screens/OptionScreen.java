package deco2800.arcade.hunter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.hunter.Hunter;

public class OptionScreen implements Screen {

    private final Hunter game;
    private final Stage stage;
    private boolean music;
    private boolean sound;
    private float volume;
    private final Texture background;
    private final SpriteBatch batch;

    public OptionScreen(Hunter p) {
        game = p;
        stage = new Stage();
        ArcadeInputMux.getInstance().addProcessor(stage);

        Texture.setEnforcePotImages(false);
        background = new Texture("textures/mainmenu.png");
        batch = new SpriteBatch();

        getPreferences();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table(skin);
        table.setFillParent(true);
        table.padRight(400f);
        table.padTop(400f);
        stage.addActor(table);

        //set table defaults
        table.defaults().spaceBottom(20);
        table.columnDefaults(0).colspan(3);
        table.add("Options Menu").colspan(3);
        table.row();

        //Add a music checkbox to the table
        final CheckBox musicCheckBox = new CheckBox("", skin);
        musicCheckBox.setChecked(music);
        musicCheckBox.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music = musicCheckBox.isChecked();
                game.getPreferencesManager().setMusicEnabled(music);
            }

        });
        table.add("Music");
        table.add(musicCheckBox).colspan(2);
        table.row();

        //adds a sound checkbox to the table
        final CheckBox soundCheckBox = new CheckBox("", skin);
        soundCheckBox.setChecked(sound);
        soundCheckBox.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sound = soundCheckBox.isChecked();
                game.getPreferencesManager().setSoundEnabled(sound);
            }
        });

        table.add("Sound");
        table.add(soundCheckBox).colspan(2);
        table.row();

        //adds a slider for volume
        Slider volumeSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeSlider.setValue(volume);
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(
                    ChangeEvent event,
                    Actor actor) {
                volume = ((Slider) actor).getValue();
                game.getPreferencesManager().setVolume(volume);
            }
        });

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
            }
        });

        table.add(backButton).size(300, 70);
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

        batch.begin();
        drawBackground();
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(width, height, true);
    }

    @Override
    public void show() {
        ArcadeInputMux.getInstance().addProcessor(stage);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        ArcadeInputMux.getInstance().removeProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void drawBackground() {
        batch.draw(background, 0f, 0f, Hunter.Config.SCREEN_WIDTH, Hunter.Config.SCREEN_HEIGHT, 0, 0, background.getWidth(), background.getHeight(), false, false);
    }
}
