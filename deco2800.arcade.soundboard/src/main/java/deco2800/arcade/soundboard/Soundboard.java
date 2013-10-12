package deco2800.arcade.soundboard;

import com.badlogic.gdx.Screen;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.listener.ReplayListener;
import deco2800.arcade.client.replay.ReplayEventListener;
import deco2800.arcade.client.replay.ReplayHandler;
import deco2800.arcade.client.replay.ReplayNode;
import deco2800.arcade.client.replay.ReplayNodeFactory;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.soundboard.model.SoundFileHolder;

import java.util.*;

/**
 * UQ DECO2800 Soundboard game
 * @author Aaron Hayes
 */
@Game.ArcadeGame(id="soundboard")
public class Soundboard extends GameClient {

    private Player player;
    private NetworkClient networkClient;
    private SoundboardScreen screen;
    private ReplayHandler replayHandler;
    private ReplayListener replayListener;

    private List<SoundFileHolder> loops;
    private List<SoundFileHolder> samples;

    /**
     * Basic Constructor for Soundboard game
     * @param player1 Current Player
     * @param networkClient1 Network Client
     */
    public Soundboard(Player player1, NetworkClient networkClient1) {
        super(player1, networkClient1);
        player = player1;
        networkClient = networkClient1;

        replayHandler = new ReplayHandler(networkClient);
        replayListener = new ReplayListener(replayHandler);
        networkClient.addListener(replayListener);

        replayHandler.addReplayEventListener(initReplayEventListener());

        ReplayNodeFactory.registerEvent("sound_pushed", new String[]{"sound_name", "loop_type", "index"});

        loops = new ArrayList<SoundFileHolder>();
        samples = new ArrayList<SoundFileHolder>();
        fetchLoops();
        fetchSamples();
    }

    /**
     * Create SoundFileHandlers for each loop preset
     */
    private void fetchLoops() {
        HashMap<String, String> loopsSetup = new HashMap<String, String>();
        loopsSetup.put("creepy_loop.wav", "Creepy");
        loopsSetup.put("dub_loop.wav", "Dubstep");
        loopsSetup.put("funk_loop.wav", "Funk");
        loopsSetup.put("house_loop.wav", "House");
        loopsSetup.put("minimal_loop.wav", "Minimal Electro");
        loopsSetup.put("techno_loop.wav", "Techno");

        for (Map.Entry<String, String> file : loopsSetup.entrySet()) {
            this.loops.add(new SoundFileHolder("SoundboardAssets/loops/" + file.getKey(), file.getValue(), true));
        }
        Collections.sort(this.loops);
    }

    /**
     * Create SoundFileHandlers for each sample
     */
    private void fetchSamples() {
        HashMap<String, String> samplesSetup = new HashMap<String, String>();
        samplesSetup.put("hat.wav", "Hat");

        for (Map.Entry<String, String> file : samplesSetup.entrySet()) {
            SoundFileHolder soundFileHolder = new SoundFileHolder("SoundboardAssets/samples/" + file.getKey(), file.getValue(), false);
            soundFileHolder.setVolume(SoundFileHolder.SAMPLE_VOLUME);
            this.samples.add(soundFileHolder);
        }
        Collections.sort(this.samples);
    }

    /**
     * Get list of loops
     * @return Loops
     */
    public List<SoundFileHolder> getLoops() {
        return loops;
    }

    /**
     * Get list of samples
     * @return Samples
     */
    public List<SoundFileHolder> getSamples() {
        return samples;
    }

    @Override
    public void create() {
        ArcadeSystem.openConnection();
        screen = new SoundboardScreen(loops, samples, replayHandler, player);
        setScreen(screen);
        super.create();


        this.getOverlay().setListeners(new Screen() {
            @Override
            public void hide() {}
            @Override
            public void show() {}
            @Override
            public void pause() {}
            @Override
            public void render(float arg0) {}
            @Override
            public void resize(int arg0, int arg1) {}
            @Override
            public void resume() {}
            @Override
            public void dispose() {}
        });

    }

    @Override
    public void dispose() {
        super.dispose();
        if (screen != null) {
            screen.dispose();
        }
    }


    /* Game Information */
    private static final Game game;
    static {
        game = new Game();
        game.id = "soundboard";
        game.name = "UQ Soundboard";
        game.description = "The epic DECO2800 Soundboard!! Enjoy the master sounds of UQ!";
    }

    @Override
    public Game getGame() {
        return game;
    }

    /**
     * Initialise replay event listener
     */
    private ReplayEventListener initReplayEventListener() {
        return new ReplayEventListener() {
            @Override
            public void replayEventReceived(String eType, ReplayNode eData) {
                if (eType.equals("playback_complete")) {
                    screen.setPlayback(false);
                    screen.reset();
                }

                if (eType.equals("sound_pushed") && screen.isPlayback()) {
                    screen.playSound(eData.getItemForString("sound_name").toString(),
                            eData.getItemForString("loop_type").intVal(),
                            eData.getItemForString("index").intVal());
                }
            }
        };
    }
}
