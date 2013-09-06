package deco2800.teamgameover.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

		public static Sound jump;
		
		public static void load() {
			jump = loadSound("jump.wav");
		}
		
		private static Sound loadSound (String filename) {
			return Gdx.audio.newSound(Gdx.files.internal("data/sounds/"+filename));
		}
		
		public static void play (Sound sound) {
			sound.play(1);
		}
		
		public static void playtest () {
			jump.play(1);
		}
}
