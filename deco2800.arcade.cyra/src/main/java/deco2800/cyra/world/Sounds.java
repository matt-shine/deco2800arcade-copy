package deco2800.cyra.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public class Sounds {

		public static Sound jump;
		
		private static Sound explosion0,explosion1,explosion2; 
		
		public static void load() {
			jump = loadSound("jump.wav");
		}
		
		public static void loadAll() {
			explosion0 = loadSound("explosion00.wav");
			explosion1 = loadSound("explosion01.wav");
			explosion2 = loadSound("explosion02.wav");
			
		}
		
		private static Sound loadSound (String filename) {
			return Gdx.audio.newSound(Gdx.files.internal("data/sounds/"+filename));
		}
		
		public static void stopPlay (Sound sound, float pan) {
			sound.stop();
			sound.play(1);
		}
		
		public static void play (Sound sound, float pan) {
			sound.play(1);
		}
		
		public static void playtest () {
			jump.play(1);
		}
		
		public static void playExplosionShort(float pan) {
			explosion0.stop();
			//explosion1.stop();
			//explosion2.stop();
			//int rand = MathUtils.random(1);
			//if (rand == 0) {
				play(explosion0, pan);
			//} else {
				play(explosion2, pan);
			//}
		}
		
		public static void playExplosionLong(float pan) {
			explosion0.stop();
			explosion1.stop();
			//explosion2.stop();
			//int rand = MathUtils.random(1);
			//if (rand == 0) {
				play(explosion0, pan);
			//} else {
				play(explosion1, pan);
			//}
		}
		
		
}
