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

	private void instancePlayMenu() {
		if (playing != SoundType.Menu) {
			instanceStop();
			playing = SoundType.Menu;
			menuLoop.play();
		}
	}

	public static void playMenu() {
		getInstance().instancePlayMenu();
	}

	private void instancePlayAction() {
		if (playing != SoundType.Action) {
			instanceStop();
			playing = SoundType.Action;
			actionLoop.play();
		}
	}

	public static void playAction() {
		getInstance().instancePlayAction();
	}

	private void instanceStop() {
		if (playing == SoundType.Menu) {
			menuLoop.stop();
		} else if (playing == SoundType.Action) {
			actionLoop.stop();
		}
		playing = SoundType.None;
	}

	public static void stop() {
		getInstance().instanceStop();
	}
	
	private void instancePlayBuild() {
		long id = buildSound.play();
		buildSound.setVolume(id, 1.0f);
	}
	
	public static void playBuild() {
		getInstance().instancePlayBuild();
	}
	
	private void instancePlayDestroy() {
		long id = destroySound.play();
		destroySound.setVolume(id, 1.0f);
	}
	
	public static void playDestroy() {
		getInstance().instancePlayDestroy();
	}
	
	private void instancePlayTNT() {
		long id = tntSound.play();
		tntSound.setVolume(id, 1.0f);
	}
	
	public static void playTNT() {
		getInstance().instancePlayTNT();
	}
}
