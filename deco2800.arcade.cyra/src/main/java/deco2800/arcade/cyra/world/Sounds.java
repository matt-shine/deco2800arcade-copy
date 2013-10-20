package deco2800.arcade.cyra.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public class Sounds {

		public static Sound jump;
		
		private static Sound coin0, coin1, explosion0,explosion1,explosion2,shoot0, new5, hit3, new3, laser, laser3; 
		private static Music boss1bgm, victory, level1bgm, menuBgm;
		private static boolean soundEnabled = false;
		private static boolean soundLoaded = false;
		
		public static final float BOSS1BGM_VOLUME = 0.4f;
		public static final float VICTORY_VOLUME = 0.6f;
		public static final float LEVEL1BGM_VOLUME = 0.5f;
		public static final float MENUBGM_VOLUME = 0.5f;
		
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
			new5 = loadSound("new05.wav");
			new3 = loadSound("new03.wav");
			hit3 = loadSound("hit03.wav");
			laser = loadSound("laserbeam.wav");
			laser3 = loadSound("laserbeam3.wav");
			boss1bgm = Gdx.audio.newMusic(Gdx.files.internal("sounds/boss1music_prototype.mp3"));
			victory = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameVictory.mp3"));
			level1bgm = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameLevel.mp3"));
			menuBgm = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameMenu.mp3"));
			soundLoaded = true;
			
			
			
			
		}
		
		public static boolean areSoundsLoadedYet() {
			return soundLoaded;
		}
		
		public static void setSoundEnabled(boolean enabled) {
			soundEnabled = enabled;
		}
		
		private static Sound loadSound (String filename) {
			return Gdx.audio.newSound(Gdx.files.internal("sounds/"+filename));
		}
		
		public static void stopPlay (Sound sound, float pan) {
			sound.stop();
			sound.play(1);
		}
		
		public static void play(Sound sound, float pan) {
			sound.play(1, 1, (pan-0.5f)*2);
		}
		
		
		public static void play (Sound sound, float volume, float pitch, float pan) {
			sound.play(volume, pitch, (pan-0.5f)*2);
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
			if (soundEnabled) {
				pauseMusic();
				boss1bgm.play();
				boss1bgm.setVolume(BOSS1BGM_VOLUME);
				boss1bgm.setLooping(true);
			}
		}
		
		public static void playVictoryMusic() {
			if (soundEnabled) {
				pauseMusic();
				victory.play();
				victory.setVolume(VICTORY_VOLUME);
				victory.setLooping(false);
			}
		}
		
		public static void playMenuMusic() {
			if (soundEnabled) {
				stopMusic();
				menuBgm.play();
				menuBgm.setVolume(MENUBGM_VOLUME);
				menuBgm.setLooping(true);
			}
		}
		
		public static void playLevelMusic() {
			pauseMusic();
			level1bgm.play();
			level1bgm.setVolume(LEVEL1BGM_VOLUME);
			level1bgm.setLooping(true);
		}
		
		public static void stopMusic() {
			menuBgm.stop();
			boss1bgm.stop();
			victory.stop();
			level1bgm.stop();
		}
		
		public static void pauseMusic() {
			menuBgm.pause();
			boss1bgm.pause();
			victory.pause();
			level1bgm.pause();
		}
		
		public static void setMusicVolume(float volume) {
			menuBgm.setVolume(volume);
			boss1bgm.setVolume(volume);
			victory.setVolume(volume);
			level1bgm.setVolume(volume);
		}
		
		
		public static void playWarningSound(float pan) {
			if (soundEnabled) {
				new5.stop();
				play(new5, pan);
			}
		}
		
		public static void playHurtSound(float pan) {
			if (soundEnabled) {
				play(hit3,pan);
			}
		}
		
		public static void playJumpSound(float pan) {
			if (soundEnabled) {
				new3.stop();
				
				play(new3, 0.1f, 1, pan);
				
			}
		}
		
		public static void playLaserSound(float pan) {
			if (soundEnabled) {
				laser.loop(1, 1, (pan-0.5f)*2f);
			}
		}
		
		public static void stopLaserSound(){
			laser.stop();
		}
		
		public static void playLaserChargeSound(float pan) {
			if (soundEnabled) {
				laser3.loop(1,1, (pan-0.5f)*2f);
			}
		}
		
		public static void stopLaserChargeSound() {
			laser3.stop();
		}
		
		public static void stopAll() {
			laser3.stop();
			laser.stop();
		}
		
		
}
