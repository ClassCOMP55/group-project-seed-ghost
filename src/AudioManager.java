import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {

	// Crossfade 
	private static final int CROSSFADE_MS = 1000;
	private static final int FADE_STEPS = 18;

	// Current music
	private static Clip currentMusicClip;
	private static String currentMusicPath;

	// Cache + resume positions
	private static Map<String, Clip> clipCache = new HashMap<String, Clip>();
	private static Map<String, Long> resumePositions = new HashMap<String, Long>();

	// Settings values (used by SettingsPane)
	private static int musicVolumePercent = 80;
	private static int sfxVolumePercent = 80; // kept for settings compatibility
	private static boolean musicMuted = false;

	public static synchronized void playMusicLoop(String path) {
		if (path == null || path.trim().isEmpty()) {
			stopMusic();
			return;
		}

		String fixedPath = path.trim();

		// same song already playing
		if (fixedPath.equals(currentMusicPath) && currentMusicClip != null && currentMusicClip.isRunning()) {
			return;
		}

		try {
			Clip newClip = loadClip(fixedPath);
			if (newClip == null) {
				System.out.println("AudioManager: Could not load music track " + fixedPath);
				currentMusicPath = null;
				return;
			}

			long startMicros = getResumePos(fixedPath, newClip);
			fadeToClip(newClip, startMicros);

			currentMusicClip = newClip;
			currentMusicPath = fixedPath;
			applyMusicVolumeToCurrentClip();

		} catch (Exception e) {
			System.out.println("AudioManager: Failed to play music: " + fixedPath);
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
				System.out.println("AudioManager: Failed to preload music: " + p);
			}
		}
	}

	public static synchronized void stopMusic() {
		stopCurrentClip();
		currentMusicPath = null;
	}
//setting

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
		// no SFX engine here yet, but SettingsPane needs this stored
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
//internal helpers

	private static void stopCurrentClip() {
		if (currentMusicClip != null) {
			saveResumePos(currentMusicPath, currentMusicClip);
			currentMusicClip.stop();
		}
	}

	private static void fadeToClip(Clip newClip, long startMicros) throws InterruptedException {
		Clip oldClip = currentMusicClip;

		// If nothing is currently playing, start right away
		if (oldClip == null || !oldClip.isRunning()) {
			newClip.stop();
			newClip.setMicrosecondPosition(startMicros);
			newClip.loop(Clip.LOOP_CONTINUOUSLY);
			newClip.start();
			return;
		}

		FloatControl oldGain = getGain(oldClip);
		FloatControl newGain = getGain(newClip);

		float oldDefault = getDefaultDb(oldGain);
		float newDefault = getDefaultDb(newGain);
		float oldMin = getMinDb(oldGain);
		float newMin = getMinDb(newGain);

		newClip.stop();
		newClip.setMicrosecondPosition(startMicros);
		setDb(newGain, newMin);
		newClip.loop(Clip.LOOP_CONTINUOUSLY);
		newClip.start();

		int delay = Math.max(1, CROSSFADE_MS / FADE_STEPS);

		for (int i = 1; i <= FADE_STEPS; i++) {
			float r = i / (float) FADE_STEPS;

			// fade in new
			float newDb = newMin + (newDefault - newMin) * r;
			setDb(newGain, newDb);

			// fade out old
			float oldDb = oldDefault + (oldMin - oldDefault) * r;
			setDb(oldGain, oldDb);

			Thread.sleep(delay);
		}

		saveResumePos(currentMusicPath, oldClip);
		oldClip.stop();
	}

	private static void applyMusicVolumeToCurrentClip() {
		if (currentMusicClip == null || !currentMusicClip.isOpen()) return;

		FloatControl gain = getGain(currentMusicClip);
		if (gain == null) return;

		if (musicMuted) {
			gain.setValue(gain.getMinimum());
			return;
		}

		float min = gain.getMinimum();
		float target = getDefaultDb(gain);
		float ratio = musicVolumePercent / 100f;

		float finalDb = min + (target - min) * ratio;
		setDb(gain, finalDb);
	}

	private static void saveResumePos(String path, Clip clip) {
		if (path == null || clip == null) return;

		long len = clip.getMicrosecondLength();
		if (len <= 0) {
			resumePositions.put(path, 0L);
			return;
		}

		long pos = clip.getMicrosecondPosition() % len;
		resumePositions.put(path, pos);
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

	private static File resolveAudioFile(String path) {
		File direct = new File(path);
		if (direct.exists()) return direct;

		File[] places = new File[] {
			new File("Music", path),
			new File("Audio", path),
			new File("Media", path),
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