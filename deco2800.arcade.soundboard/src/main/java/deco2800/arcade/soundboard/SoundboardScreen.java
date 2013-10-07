package deco2800.arcade.soundboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import deco2800.arcade.soundboard.actions.SoundButtonChangeListener;
import deco2800.arcade.soundboard.model.SoundFileHolder;

import java.util.ArrayList;

/**
 * Screen for soundboard game
 * @author Aaron Hayes
 */
public class SoundboardScreen implements Screen {

    private ArrayList<SoundFileHolder> loops;
    private ArrayList<SoundFileHolder> samples;

    /**
     * UI Objects
     */
    private SpriteBatch batch;
    private Stage stage;
    private Texture background;
    private Actor image;
    private Skin libSkin;
    private int x = 0;
    private int y = 580;
    private TextButton button;
    private Label loopLabel;


    /**
     * Basic Constructor
     */
    public SoundboardScreen(ArrayList<SoundFileHolder> loops, ArrayList<SoundFileHolder> samples) {
        this.loops = loops;
        this.samples = samples;
        libSkin = new Skin(Gdx.files.internal("libSkin.json"));
        setupUI();
    }

    private void setupUI() {
        stage = new Stage();
        batch = new SpriteBatch();
        background = new Texture("Assets/splashscreen.jpg");
        image = new Image(background);
        stage.addActor(image);

        loopLabel = new Label("Loops", libSkin);
        loopLabel.setAlignment(2, 2);
        loopLabel.setWidth(275);
        loopLabel.setHeight(33);
        loopLabel.setY(y);
        loopLabel.setX(x);
        y -= 35;
        stage.addActor(loopLabel);




        for (SoundFileHolder sound : loops) {
            button = new TextButton(sound.getLabel(), libSkin, "green");
            button.addListener(new SoundButtonChangeListener(sound));
            button.setWidth(275);
            button.setHeight(33);
            button.setX(x);
            button.setY(y);
            y-= 35;

            stage.addActor(button);
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.2f, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int i, int i2) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}
