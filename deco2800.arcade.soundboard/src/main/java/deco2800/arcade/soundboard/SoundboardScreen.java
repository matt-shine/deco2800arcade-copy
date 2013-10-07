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

import java.util.List;

/**
 * Screen for soundboard game
 * @author Aaron Hayes
 */
public class SoundboardScreen implements Screen {

    private List<SoundFileHolder> loops;
    private List<SoundFileHolder> samples;

    private static final int LOOP_BUTTON_WIDTH = 275;
    private static final int SAMPLE_BUTTON_WIDTH = 200;
    private static final int SAMPLE_X = 300;
    private static final int BUTTON_HEIGHT = 33;
    private static final int INIT_Y = 580;
    private static final int BUTTON_OFFSET_Y = 35;
    private static final int BUTTON_OFFSET_X = 200;
    private static final int BUTTON_SAMPLE_OFFSET_Y = 50;
    private static final int SAMPLE_BUTTON_ROW_COUNT = 4;
    private static final int WIDTH = 1024;

    /**
     * UI Objects
     */
    private SpriteBatch batch;
    private Stage stage;
    private Texture background;
    private Actor image;
    private Skin libSkin;
    private int x = 0;
    private int y = INIT_Y;
    private TextButton button;
    private Label loopLabel;
    private Label sampleLabel;

    /**
     * Basic Constructor
     */
    public SoundboardScreen(List<SoundFileHolder> loops, List<SoundFileHolder> samples) {
        this.loops = loops;
        this.samples = samples;
        libSkin = new Skin(Gdx.files.internal("libSkin.json"));
        setupUI();
    }

    /**
     * Create the user interface
     */
    private void setupUI() {
        stage = new Stage();
        batch = new SpriteBatch();
        background = new Texture("Assets/splashscreen.jpg");
        image = new Image(background);
        stage.addActor(image);

        setupLoopButtons();
        setupSampleButtons();

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Create and show buttons for samples
     */
    private void setupSampleButtons() {
        x += SAMPLE_X;
        y = INIT_Y;
        int count = 0;

        sampleLabel = new Label("Samples", libSkin);
        sampleLabel.setAlignment(2, 2);
        sampleLabel.setWidth(WIDTH - LOOP_BUTTON_WIDTH);
        sampleLabel.setHeight(BUTTON_HEIGHT);
        sampleLabel.setY(y);
        sampleLabel.setX(x);
        y -= BUTTON_SAMPLE_OFFSET_Y;
        stage.addActor(sampleLabel);

        /* Make buttons for samples */
        for (SoundFileHolder sound : samples) {
            button = new TextButton(sound.getLabel(), libSkin, "red");
            button.addListener(new SoundButtonChangeListener(sound));
            button.setWidth(SAMPLE_BUTTON_WIDTH);
            button.setHeight(BUTTON_HEIGHT);
            button.setX(x);
            button.setY(y);

            stage.addActor(button);

            if (++count % SAMPLE_BUTTON_ROW_COUNT == 0) {
                x = SAMPLE_X;
                y -= BUTTON_SAMPLE_OFFSET_Y;
            } else {
                x += (BUTTON_OFFSET_X);
            }
        }
    }


    /**
     * Create and show buttons for loops
     */
    private void setupLoopButtons() {
        loopLabel = new Label("Loops", libSkin);
        loopLabel.setAlignment(2, 2);
        loopLabel.setWidth(LOOP_BUTTON_WIDTH);
        loopLabel.setHeight(BUTTON_HEIGHT);
        loopLabel.setY(y);
        loopLabel.setX(x);
        y -= BUTTON_OFFSET_Y;
        stage.addActor(loopLabel);


        /* Make buttons for loops */
        for (SoundFileHolder sound : loops) {
            button = new TextButton(sound.getLabel(), libSkin, "green");
            button.addListener(new SoundButtonChangeListener(sound));
            button.setWidth(LOOP_BUTTON_WIDTH);
            button.setHeight(BUTTON_HEIGHT);
            button.setX(x);
            button.setY(y);
            y-= BUTTON_OFFSET_Y;

            stage.addActor(button);
        }
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
