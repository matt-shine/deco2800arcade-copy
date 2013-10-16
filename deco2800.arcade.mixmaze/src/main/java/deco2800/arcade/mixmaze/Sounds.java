package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public final class Sounds {
	private enum SoundType {
		None, Menu, Action
	}

	private static Sounds INSTANCE;
	
	// Looping music
	private Music menuLoop;
	private Music actionLoop;
	private SoundType playing;
	
	// Effect Sounds
	private Sound buildSound;
	private Sound destroySound;
	private Sound tntSound;

	private Sounds() {
		// Menu Music
		menuLoop = Gdx.audio.newMusic(Gdx.files.internal("lls_surge.mp3"));
		menuLoop.setLooping(true);
		menuLoop.setVolume(0.5f);

		// Game music
		actionLoop = Gdx.audio.newMusic(Gdx.files
				.internal("lls_aegis_sprint.mp3"));
		actionLoop.setLooping(true);
		actionLoop.setVolume(0.4f);
		
		// Effect Sounds
		buildSound = Gdx.audio.newSound(Gdx.files.internal("build.wav"));
		destroySound = Gdx.audio.newSound(Gdx.files.internal("destroy.mp3"));
		tntSound = Gdx.audio.newSound(Gdx.files.internal("tnt.mp3"));
	}

	private static Sounds getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Sounds();
		}
		return INSTANCE;
	}

	private void _playMenu() {
		if (playing != SoundType.Menu) {
			_stop();
			playing = SoundType.Menu;
			menuLoop.play();
		}
	}

	public static void playMenu() {
		getInstance()._playMenu();
	}

	private void _playAction() {
		if (playing != SoundType.Action) {
			_stop();
			playing = SoundType.Action;
			actionLoop.play();
		}
	}

	public static void playAction() {
		getInstance()._playAction();
	}

	private void _stop() {
		if (playing == SoundType.Menu) {
			menuLoop.stop();
		} else if (playing == SoundType.Action) {
			actionLoop.stop();
		}
		playing = SoundType.None;
	}

	public static void stop() {
		getInstance()._stop();
	}
	
	private void _playBuild() {
		long id = buildSound.play();
		buildSound.setVolume(id, 1.0f);
	}
	
	public static void playBuild() {
		getInstance()._playBuild();
	}
	
	private void _playDestroy() {
		long id = destroySound.play();
		destroySound.setVolume(id, 1.0f);
	}
	
	public static void playDestroy() {
		getInstance()._playDestroy();
	}
	
	private void _playTNT() {
		long id = tntSound.play();
		tntSound.setVolume(id, 1.0f);
	}
	
	public static void playTNT() {
		getInstance()._playTNT();
	}
}
