package net.minecraft.src;

import java.io.File;
import java.util.Random;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager {
	private static SoundSystem soundSystem;
	private SoundPool soundPoolSounds;
	private SoundPool soundPoolStreaming;
	private SoundPool soundPoolMusic;
	private int latestSoundID;
	private GameSettings options;
	private static boolean loaded = false;
	private Random rand;
	private int ticksBeforeMusic;

	public SoundManager() {
		soundPoolSounds = new SoundPool();
		soundPoolStreaming = new SoundPool();
		soundPoolMusic = new SoundPool();
		latestSoundID = 0;
		rand = new Random();
		ticksBeforeMusic = rand.nextInt(12000);
	}

	public void loadSoundSettings(GameSettings gamesettings) {
		soundPoolStreaming.isGetRandomSound = false;
		options = gamesettings;
		if(!loaded && (gamesettings == null || gamesettings.soundVolume != 0.0F || gamesettings.musicVolume != 0.0F)) {
			tryToSetLibraryAndCodecs();
		}
	}

	private void tryToSetLibraryAndCodecs() {
		try {
			float f = options.soundVolume;
			float f1 = options.musicVolume;
			options.soundVolume = 0.0F;
			options.musicVolume = 0.0F;
			options.saveOptions();
			SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
			SoundSystemConfig.setCodec("mus", CodecMus.class);
			SoundSystemConfig.setCodec("wav", CodecWav.class);
			soundSystem = new SoundSystem();
			options.soundVolume = f;
			options.musicVolume = f1;
			options.saveOptions();
		} catch(Throwable throwable) {
			throwable.printStackTrace();
			System.err.println("error linking with the LibraryJavaSound plug-in");
		}
		loaded = true;
	}

	public void onSoundOptionsChanged() {
		if(!loaded && (options.soundVolume != 0.0F || options.musicVolume != 0.0F)) {
			tryToSetLibraryAndCodecs();
		}
		if(options.musicVolume == 0.0F) {
			soundSystem.stop("BgMusic");
		} else {
			soundSystem.setVolume("BgMusic", options.musicVolume);
		}
	}

	public void closeMinecraft() {
		if(loaded) {
			soundSystem.cleanup();
		}
	}

	public void addSound(String s, File file) {
		soundPoolSounds.addSound(s, file);
	}

	public void addStreaming(String s, File file) {
		soundPoolStreaming.addSound(s, file);
	}

	public void addMusic(String s, File file) {
		soundPoolMusic.addSound(s, file);
	}

	public void playRandomMusicIfReady() {
		if(!loaded || options.musicVolume == 0.0F) {
			return;
		}
		if(!soundSystem.playing("BgMusic") && !soundSystem.playing("streaming")) {
			if(ticksBeforeMusic > 0) {
				ticksBeforeMusic--;
				return;
			}
			SoundPoolEntry soundpoolentry = soundPoolMusic.getRandomSound();
			if(soundpoolentry != null) {
				ticksBeforeMusic = rand.nextInt(12000) + 12000;
				soundSystem.backgroundMusic("BgMusic", soundpoolentry.soundUrl, soundpoolentry.soundName, false);
				soundSystem.setVolume("BgMusic", options.musicVolume);
				soundSystem.play("BgMusic");
			}
		}
	}

	public void setListener(EntityLiving entityliving, float f) {
		if(!loaded || options.soundVolume == 0.0F) {
			return;
		}
		if(entityliving == null) {
			return;
		} else {
			float f1 = entityliving.prevRotationYaw + (entityliving.rotationYaw - entityliving.prevRotationYaw) * f;
			double d = entityliving.prevPosX + (entityliving.posX - entityliving.prevPosX) * (double)f;
			double d1 = entityliving.prevPosY + (entityliving.posY - entityliving.prevPosY) * (double)f;
			double d2 = entityliving.prevPosZ + (entityliving.posZ - entityliving.prevPosZ) * (double)f;
			float f2 = MathHelper.cos(-f1 * 0.01745329F - 3.141593F);
			float f3 = MathHelper.sin(-f1 * 0.01745329F - 3.141593F);
			float f4 = -f3;
			float f5 = 0.0F;
			float f6 = -f2;
			float f7 = 0.0F;
			float f8 = 1.0F;
			float f9 = 0.0F;
			soundSystem.setListenerPosition((float)d, (float)d1, (float)d2);
			soundSystem.setListenerOrientation(f4, f5, f6, f7, f8, f9);
			return;
		}
	}

	public void playStreaming(String disc, float x, float y, float z, float vol) {
		if(!loaded || options.soundVolume == 0.0F) {
			return;
		}
		String sourceName = "streaming";
		if(soundSystem.playing("streaming")) {
			soundSystem.stop("streaming");
		}
		if(disc == null) {
			return;
		}
		SoundPoolEntry soundpoolentry = soundPoolStreaming.getRandomSoundFromSoundPool(disc);
		if(soundpoolentry != null && vol > 0.0F) {
			if(soundSystem.playing("BgMusic")) {
				soundSystem.stop("BgMusic");
			}
			float dist = 16F;
			soundSystem.newStreamingSource(true, sourceName, soundpoolentry.soundUrl, soundpoolentry.soundName, false, x, y, z, 2, dist * 4F);
			soundSystem.setVolume(sourceName, 0.5F * options.soundVolume);
			soundSystem.play(sourceName);
		}
	}

	public void playSound(String s, float x, float y, float z, float f3, float f4) {
		if(!loaded || options.soundVolume == 0.0F) {
			return;
		}
		SoundPoolEntry soundpoolentry = soundPoolSounds.getRandomSoundFromSoundPool(s);
		if(soundpoolentry != null && f3 > 0.0F) {
			latestSoundID = (latestSoundID + 1) % 256;
			String s1 = (new StringBuilder()).append("sound_").append(latestSoundID).toString();
			float dist = 16F;
			if(f3 > 1.0F) {
				dist *= f3;
			}
			soundSystem.newSource(f3 > 1.0F, s1, soundpoolentry.soundUrl, soundpoolentry.soundName, false, x, y, z, 2, dist);
			soundSystem.setPitch(s1, f4);
			if(f3 > 1.0F) {
				f3 = 1.0F;
			}
			soundSystem.setVolume(s1, f3 * options.soundVolume);
			soundSystem.play(s1);
		}
	}

	public void playSoundFX(String s, float f, float f1) {
		if(!loaded || options.soundVolume == 0.0F) {
			return;
		}
		SoundPoolEntry soundpoolentry = soundPoolSounds.getRandomSoundFromSoundPool(s);
		if(soundpoolentry != null) {
			latestSoundID = (latestSoundID + 1) % 256;
			String s1 = (new StringBuilder()).append("sound_").append(latestSoundID).toString();
			soundSystem.newSource(false, s1, soundpoolentry.soundUrl, soundpoolentry.soundName, false, 0.0F, 0.0F, 0.0F, 0, 0.0F);
			if(f > 1.0F) {
				f = 1.0F;
			}
			f *= 0.25F;
			soundSystem.setPitch(s1, f1);
			soundSystem.setVolume(s1, f * options.soundVolume);
			soundSystem.play(s1);
		}
	}



}
