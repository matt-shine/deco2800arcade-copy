package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public final class Sounds {
	private enum SoundType {
		None, Menu, Action
	}

	private static Sounds INSTANCE;
	private Music menuLoop;
	private Music actionLoop;
	private SoundType playing;

	private Sounds() {
		menuLoop = Gdx.audio.newMusic(Gdx.files.internal("lls_surge.mp3"));
		menuLoop.setLooping(true);
		menuLoop.setVolume(0.4f);

		actionLoop = Gdx.audio.newMusic(Gdx.files
				.internal("lls_aegis_sprint.mp3"));
		actionLoop.setLooping(true);
		actionLoop.setVolume(0.5f);
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
}
