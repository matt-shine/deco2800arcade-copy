package deco2800.arcade.soundboard;

import com.badlogic.gdx.Screen;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
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

    /* Game Information */
    private static final Game game;
    static {
        game = new Game();
        game.id = "soundboard";
        game.name = "UQ Soundboard";
        game.description = "The epic DECO2800 Soundboard!! Enjoy the master sounds of UQ!";
    }

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

        ReplayNodeFactory.registerEvent("sound_pushed", new String[]{"loop_type", "index"});

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
        loopsSetup.put("creepy_loop.mp3", "Creepy");
        loopsSetup.put("dub_loop.mp3", "Dubstep");
        loopsSetup.put("funk_loop.mp3", "Funk");
        loopsSetup.put("house_loop.mp3", "House");
        loopsSetup.put("minimal_loop.mp3", "Minimal Electro");
        loopsSetup.put("techno_loop.mp3", "Techno");
        loopsSetup.put("dial_up_loop.mp3", "Dial Up");
        loopsSetup.put("click_loop.mp3", "Clicks");


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
        samplesSetup.put("hat.mp3", "Hat");
        samplesSetup.put("huge.mp3", "HUGE!");
        samplesSetup.put("beta.mp3", "Beta");
        samplesSetup.put("arcade.mp3", "Arcade");
        samplesSetup.put("pluck.mp3", "Pluck");
        samplesSetup.put("drum.mp3", "Low Drum");
        samplesSetup.put("bat.mp3", "Bat");
        samplesSetup.put("haha.mp3", "Laugh");
        samplesSetup.put("okay.mp3", "Okay");
        samplesSetup.put("sneeze.mp3", "Sneeze");
        samplesSetup.put("deco2800.mp3", "DECO2800");
        samplesSetup.put("umso.mp3", "Umm.. So");
        samplesSetup.put("down.mp3", "Down");
        samplesSetup.put("up.mp3", "Up");

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
        screen = new SoundboardScreen(loops, samples, replayHandler, player, game.id);
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
                    screen.playSound(eData.getItemForString("loop_type").intVal(),
                            eData.getItemForString("index").intVal());
                }
            }
        };
    }
}
