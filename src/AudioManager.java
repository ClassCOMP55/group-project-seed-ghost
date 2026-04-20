import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {

	// crossfade settings
	private static final int CROSSFADE_MS = 1000;
	private static final int FADE_STEPS = 18;

	// current BGM
	private static Clip currentMusicClip;
	private static String currentMusicPath;

	// cache so we don't reload files every time
	private static Map<String, Clip> clipCache = new HashMap<String, Clip>();
	private static Map<String, Long> resumePositions = new HashMap<String, Long>();

	// volume settings
	private static int musicVolumePercent = 80;
	private static int sfxVolumePercent = 80;
	private static boolean musicMuted = false;

	// campfire / ambient loop - kept separate so playSfxOnce doesn't stomp it
	private static Clip ambientSfxClip;

	public static synchronized void playMusicLoop(String path) {
		if (path == null || path.trim().isEmpty()) {
			stopMusic();
			return;
		}

		String fixedPath = path.trim();

		// don't restart if already playing this track
		if (fixedPath.equals(currentMusicPath) && currentMusicClip != null && currentMusicClip.isRunning()) {
			return;
		}

		try {
			Clip newClip = loadClip(fixedPath);
			if (newClip == null) {
				System.out.println("AudioManager: couldn't load " + fixedPath);
				currentMusicPath = null;
				return;
			}

			long startMicros = getResumePos(fixedPath, newClip);
			fadeToClip(newClip, startMicros);

			currentMusicClip = newClip;
			currentMusicPath = fixedPath;
			// safety net in case fade math drifted slightly
			applyMusicVolumeToCurrentClip();

		} catch (Exception e) {
			System.out.println("AudioManager: failed to play " + fixedPath);
			currentMusicPath = null;
		}
	}

	public static synchronized void preloadMusic(String... paths) {
		if (paths == null) return;
		for (String p : paths) {
			if (p == null || p.trim().isEmpty()) continue;
			try {
				loadClip(p.trim());
			} catch (Exception e) {
				System.out.println("AudioManager: preload failed for " + p);
			}
		}
	}

	public static synchronized void preloadSfx(String... paths) {
		if (paths == null) return;
		for (String p : paths) {
			if (p == null || p.trim().isEmpty()) continue;
			try {
				loadClip(p.trim());
			} catch (Exception e) {
				System.out.println("AudioManager: preload failed for " + p);
			}
		}
	}

	public static synchronized void stopMusic() {
		stopCurrentClip();
		currentMusicPath = null;
	}

	// --- volume controls used by SettingsPane ---

	public static synchronized void setMusicVolumePercent(int value) {
		if (value < 0) value = 0;
		if (value > 100) value = 100;
		musicVolumePercent = value;
		applyMusicVolumeToCurrentClip();
	}

	public static synchronized int getMusicVolumePercent() {
		return musicVolumePercent;
	}

	public static synchronized void setSfxVolumePercent(int value) {
		if (value < 0) value = 0;
		if (value > 100) value = 100;
		sfxVolumePercent = value;
		applySfxVolumeToClip(ambientSfxClip);
	}

	public static synchronized int getSfxVolumePercent() {
		return sfxVolumePercent;
	}

	public static synchronized void setMusicMuted(boolean muted) {
		musicMuted = muted;
		applyMusicVolumeToCurrentClip();
	}

	public static synchronized boolean isMusicMuted() {
		return musicMuted;
	}

	// --- SFX ---

	public static synchronized void playSfxOnce(String path) {
		if (path == null || path.trim().isEmpty()) return;
		try {
			Clip clip = loadClip(path.trim());
			if (clip == null) return;
			clip.stop();
			clip.setFramePosition(0);
			applySfxVolumeToClip(clip);
			clip.start();
		} catch (Exception e) {
			System.out.println("AudioManager: failed to play sfx " + path);
		}
	}

	public static synchronized void playAmbientSfxLoop(String path) {
		if (path == null || path.trim().isEmpty()) {
			stopAmbientSfx();
			return;
		}

		stopAmbientSfx();

		try {
			File audioFile = resolveAudioFile(path.trim());
			if (audioFile == null || !audioFile.exists()) {
				System.out.println("AudioManager: ambient file not found: " + path);
				return;
			}

			AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioFile);
			Clip clip = AudioSystem.getClip();
			clip.open(inputStream);

			ambientSfxClip = clip;
			applySfxVolumeToClip(clip);
			clip.setFramePosition(0);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();

		} catch (Exception e) {
			System.out.println("AudioManager: failed to play ambient sfx " + path);
			ambientSfxClip = null;
		}
	}

	public static synchronized void stopAmbientSfx() {
		if (ambientSfxClip != null) {
			try {
				ambientSfxClip.stop();
				ambientSfxClip.flush();
				ambientSfxClip.close();
			} catch (Exception ignored) {}
			ambientSfxClip = null;
		}
	}

	// --- internal helpers ---

	private static void stopCurrentClip() {
		if (currentMusicClip != null) {
			saveResumePos(currentMusicPath, currentMusicClip);
			currentMusicClip.stop();
		}
	}

	private static void fadeToClip(Clip newClip, long startMicros) throws InterruptedException {
		Clip oldClip = currentMusicClip;

		// nothing playing yet, just start
		if (oldClip == null || !oldClip.isRunning()) {
			newClip.stop();
			newClip.setMicrosecondPosition(startMicros);
			newClip.loop(Clip.LOOP_CONTINUOUSLY);
			newClip.start();
			return;
		}

		FloatControl oldGain = getGain(oldClip);
		FloatControl newGain = getGain(newClip);

		// use the actual current volume so we don't spike up to 0db before fading
		float oldStart = (oldGain != null) ? oldGain.getValue() : 0f;
		// fade new clip to the correct target, not hardware max
		float newTarget = computeTargetMusicDb(newGain);

		float oldMin = getMinDb(oldGain);
		float newMin = getMinDb(newGain);

		// start new clip silently
		newClip.stop();
		newClip.setMicrosecondPosition(startMicros);
		setDb(newGain, newMin);
		newClip.loop(Clip.LOOP_CONTINUOUSLY);
		newClip.start();

		int delay = Math.max(1, CROSSFADE_MS / FADE_STEPS);

		for (int i = 1; i <= FADE_STEPS; i++) {
			float r = i / (float) FADE_STEPS;
			// new clip fades in, old clip fades out
			setDb(newGain, newMin + (newTarget - newMin) * r);
			setDb(oldGain, oldStart + (oldMin - oldStart) * r);
			Thread.sleep(delay);
		}

		saveResumePos(currentMusicPath, oldClip);
		oldClip.stop();
	}

	// figures out what dB the current volume% + mute state maps to
	private static float computeTargetMusicDb(FloatControl gain) {
		if (gain == null) return 0f;
		if (musicMuted) return gain.getMinimum();
		float min = gain.getMinimum();
		float max = getDefaultDb(gain);
		return min + (max - min) * (musicVolumePercent / 100f);
	}

	private static void applyMusicVolumeToCurrentClip() {
		if (currentMusicClip == null || !currentMusicClip.isOpen()) return;
		FloatControl gain = getGain(currentMusicClip);
		if (gain == null) return;
		setDb(gain, computeTargetMusicDb(gain));
	}

	private static void applySfxVolumeToClip(Clip clip) {
		if (clip == null || !clip.isOpen()) return;
		FloatControl gain = getGain(clip);
		if (gain == null) return;
		float min = gain.getMinimum();
		float max = getDefaultDb(gain);
		float finalDb = min + (max - min) * (sfxVolumePercent / 100f);
		setDb(gain, finalDb);
	}

	private static void saveResumePos(String path, Clip clip) {
		if (path == null || clip == null) return;
		long len = clip.getMicrosecondLength();
		if (len <= 0) {
			resumePositions.put(path, 0L);
			return;
		}
		resumePositions.put(path, clip.getMicrosecondPosition() % len);
	}

	private static long getResumePos(String path, Clip clip) {
		Long saved = resumePositions.get(path);
		if (saved == null || clip == null) return 0L;
		long len = clip.getMicrosecondLength();
		if (len <= 0) return 0L;
		return saved % len;
	}

	private static FloatControl getGain(Clip clip) {
		if (clip == null) return null;
		if (!clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) return null;
		return (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	}

	private static float getMinDb(FloatControl c) {
		if (c == null) return 0f;
		return c.getMinimum();
	}

	private static float getDefaultDb(FloatControl c) {
		if (c == null) return 0f;
		return Math.min(0f, c.getMaximum());
	}

	private static void setDb(FloatControl c, float value) {
		if (c == null) return;
		float bounded = Math.max(c.getMinimum(), Math.min(c.getMaximum(), value));
		c.setValue(bounded);
	}

	private static Clip loadClip(String path) throws Exception {
		Clip cached = clipCache.get(path);
		if (cached != null) return cached;

		File file = resolveAudioFile(path);
		if (file == null || !file.exists()) return null;

		AudioInputStream input = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(input);

		clipCache.put(path, clip);
		return clip;
	}

	// tries a bunch of common paths since the working directory changes depending on how you run it
	private static File resolveAudioFile(String path) {
		File direct = new File(path);
		if (direct.exists()) return direct;

		File[] places = new File[] {
			new File("Music", path),
			new File("Audio", path),
			new File("Media", path),

			new File("../Music", path),
			new File("../Audio", path),
			new File("../Media", path),

			new File("resources/Music", path),
			new File("resources/Audio", path),
			new File("resources/Media", path),
			new File("src/resources/Music", path),
			new File("src/resources/Audio", path),
			new File("src/resources/Media", path)
		};

		for (File f : places) {
			if (f.exists()) return f;
		}
		return null;
	}
}