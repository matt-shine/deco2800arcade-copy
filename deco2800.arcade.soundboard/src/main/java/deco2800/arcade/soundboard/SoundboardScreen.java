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

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.client.network.listener.ReplayListener;
import deco2800.arcade.client.replay.ReplayHandler;
import deco2800.arcade.model.Player;
import deco2800.arcade.soundboard.actions.SoundButtonChangeListener;
import deco2800.arcade.soundboard.model.SoundFileHolder;

import java.util.List;

/**
 * Screen for soundboard game
 * @author Aaron Hayes
 */
public class SoundboardScreen implements Screen {

    private final Player player;
    private List<SoundFileHolder> loops;
    private List<SoundFileHolder> samples;
    private ReplayHandler replayHandler;
    private ReplayListener replayListener;
    private boolean recording = false;
    private boolean playback;
    private int session;

    public static final int LOOPS = 0;
    public static final int SAMPLES = 1;

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
    private static final int LABEL_HEIGHT = 40;
    private static final int TITLE_Y = 650;
    private static final int TITLE_LABEL_X = 450;
    private static final int RECORD_BTN_X = 750;
    private static final int PLAY_BTN_X = 1000;

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
    private Label titleLabel;
    private TextButton recordButton;
    private TextButton playbackButton;



    /**
     * Basic Constructor
     */
    public SoundboardScreen(List<SoundFileHolder> loops, List<SoundFileHolder> samples,
                            ReplayHandler replayHandler, ReplayListener replayListener,
                            Player player) {
        this.loops = loops;
        this.samples = samples;
        this.replayHandler = replayHandler;
        this.replayListener = replayListener;
        this.player = player;

        session = -1;
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

        setupTitleBar();
        setupLoopButtons();
        setupSampleButtons();

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Setup Interface elements in title bar
     */
    private void setupTitleBar() {
        titleLabel = new Label("UQ Soundboard!", libSkin, "heading");
        titleLabel.setWidth(SAMPLE_BUTTON_WIDTH);
        titleLabel.setHeight(LABEL_HEIGHT);
        titleLabel.setX(TITLE_LABEL_X);
        titleLabel.setY(TITLE_Y);
        stage.addActor(titleLabel);

        recordButton = new TextButton("Start Recording", libSkin);
        recordButton.setWidth(SAMPLE_BUTTON_WIDTH);
        recordButton.setHeight(LABEL_HEIGHT);
        recordButton.setX(RECORD_BTN_X);
        recordButton.setY(TITLE_Y);
        recordButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (recording) {
                    recordButton.setText("Start Recording");
                    session = replayHandler.getSessionId();
                    replayHandler.endSession(session);
                } else {
                    reset();
                    recordButton.setText("Stop Recording");
                    replayHandler.startSession(1, player.getUsername());
                    replayHandler.startRecording();
                }
                recordButton.remove();
                stage.addActor(recordButton);
                recording = !recording;
            }
        });

        stage.addActor(recordButton);

        playbackButton = new TextButton("Playback Recording", libSkin);
        playbackButton.setWidth(SAMPLE_BUTTON_WIDTH);
        playbackButton.setHeight(LABEL_HEIGHT);
        playbackButton.setX(PLAY_BTN_X);
        playbackButton.setY(TITLE_Y);
        playbackButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (session != -1 && !recording) {
                    reset();
                    replayHandler.requestEventsForSession(session);
                    playback = true;
                }
            }
        });

        stage.addActor(playbackButton);
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

        int index = 0;
        /* Make buttons for samples */
        for (SoundFileHolder sound : samples) {
            button = new TextButton(sound.getLabel(), libSkin, "red");
            button.addListener(new SoundButtonChangeListener(sound, replayHandler, index++, this));
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

        int index = 0;
        /* Make buttons for loops */
        for (SoundFileHolder sound : loops) {
            button = new TextButton(sound.getLabel(), libSkin, "green");
            button.addListener(new SoundButtonChangeListener(sound, replayHandler, index++, this));
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

        if (playback) {
            replayHandler.runLoop();
        }
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

    /**
     * Check if sounds should be recorded
     * @return recoding
     */
    public boolean isRecording() {
        return this.recording;
    }

    /**
     * Set recording
     * @param recording boolean
     */
    public void setRecording(boolean recording) {
        this.recording = recording;
    }

    /**
     * Set if the screen is playing back recording
     * @param playback boolean
     */
    public void setPlayback(boolean playback) {
        this.playback = playback;
    }

    /**
     * Check if screen is currently playing back recording
     * @return playback
     */
    public boolean isPlayback() {
        return this.playback;
    }

    /**
     * Reset screen
     *  - Stop all sounds playing
     */
    public void reset() {
        for (SoundFileHolder sound : loops) {
            sound.stop();
        }

        for (SoundFileHolder sound : samples) {
            sound.stop();
        }

    }

    /**
     * Play a sound
     * @param sound_name Name of the sound
     * @param loop_type Type of sound
     * @param index index in array
     */
    public void playSound(String sound_name, int loop_type, Integer index) {
        if (loop_type == LOOPS) {
            loops.get(index).play();
        } else if (loop_type == SAMPLES) {
            samples.get(index).play();
        }
    }
}
