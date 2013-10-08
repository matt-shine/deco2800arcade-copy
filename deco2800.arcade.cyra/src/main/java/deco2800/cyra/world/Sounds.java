package deco2800.cyra.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public class Sounds {

		public static Sound jump;
		
		private static Sound coin0, coin1, explosion0,explosion1,explosion2,shoot0; 
		private static Music boss1bgm;
		
		public static void load() {
			jump = loadSound("jump.wav");
		}
		
		public static void loadAll() {
			coin0 = loadSound("coin01.wav");
			coin1 = loadSound("shoot01.wav");
			explosion0 = loadSound("explosion00.wav");
			explosion1 = loadSound("explosion01.wav");
			explosion2 = loadSound("explosion02.wav");
			shoot0 = loadSound("shoot03.wav");
			boss1bgm = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/boss1music_prototype.mp3"));
			
			
			
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
		
		public static void playShootSound(float pan) {
			play(shoot0, pan);
		}
		
		public static void playCoinSound(float pan) {
			coin0.stop();
			coin1.stop();
			play(coin0, pan);
			play(coin1, pan);
		}
		
		public static void playBossMusic() {
			boss1bgm.stop();
			boss1bgm.play();
			boss1bgm.setVolume(0.4f);
			boss1bgm.setLooping(true);
		}
		
		
}
