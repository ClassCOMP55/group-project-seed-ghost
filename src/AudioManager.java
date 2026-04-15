import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
	private static final int CROSSFADE_MS = 900;
	private static final int FADE_STEPS = 18;
	private static Clip currentMusicClip;
	private static String currentMusicPath;
	private static final Map<String, Clip> clipCache = new HashMap<String, Clip>();

	public static synchronized void playMusicLoop(String path) {
		if (path == null || path.trim().isEmpty()) {
			stopMusic();
			return;
		}

		String normalizedPath = path.trim();
		if (normalizedPath.equals(currentMusicPath) && currentMusicClip != null && currentMusicClip.isRunning()) {
			return;
		}

		try {
			Clip nextClip = loadClip(normalizedPath);
			if (nextClip == null) {
				System.out.println("AudioManager: Could not load music track " + normalizedPath);
				currentMusicPath = null;
				return;
			}

			crossfadeTo(nextClip);
			currentMusicClip = nextClip;
			currentMusicPath = normalizedPath;
		} catch (Exception e) {
			System.out.println("AudioManager: Failed to play music: " + normalizedPath);
			currentMusicPath = null;
		}
	}

	public static synchronized void stopMusic() {
		stopCurrentClipOnly();
		currentMusicPath = null;
	}

	private static void stopCurrentClipOnly() {
		if (currentMusicClip != null) {
			currentMusicClip.stop();
		}
	}
	private static void crossfadeTo(Clip nextClip) throws InterruptedException {
		Clip oldClip = currentMusicClip;
		FloatControl oldGain = getGainControl(oldClip);
		FloatControl newGain = getGainControl(nextClip);
		float oldTargetDb = getDefaultDb(oldGain);
		float newTargetDb = getDefaultDb(newGain);
		float minOldDb = getMinDb(oldGain);
		float minNewDb = getMinDb(newGain);

		nextClip.stop();
		nextClip.setFramePosition(0);
		setGain(newGain, minNewDb);
		nextClip.loop(Clip.LOOP_CONTINUOUSLY);
		nextClip.start();

		int sleepMs = Math.max(1, CROSSFADE_MS / FADE_STEPS);
		for (int i = 1; i <= FADE_STEPS; i++) {
			float ratio = i / (float) FADE_STEPS;
			setGain(newGain, minNewDb + ((newTargetDb - minNewDb) * ratio));
			setGain(oldGain, oldTargetDb + ((minOldDb - oldTargetDb) * ratio));
			Thread.sleep(sleepMs);
		}

		if (oldClip != null) {
			oldClip.stop();
		}
		setGain(newGain, newTargetDb);
	}

	private static FloatControl getGainControl(Clip clip) {
		if (clip == null) {
			return null;
		}
		if (!clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
			return null;
		}
		return (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	}

	private static float getMinDb(FloatControl gainControl) {
		if (gainControl == null) {
			return 0f;
		}
		return gainControl.getMinimum();
	}

	private static float getDefaultDb(FloatControl gainControl) {
		if (gainControl == null) {
			return 0f;
		}
		return Math.min(0f, gainControl.getMaximum());
	}

	private static void setGain(FloatControl gainControl, float value) {
		if (gainControl == null) {
			return;
		}
		float bounded = Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), value));
		gainControl.setValue(bounded);
	}

	private static Clip loadClip(String path) throws Exception {
		Clip cachedClip = clipCache.get(path);
		if (cachedClip != null) {
			return cachedClip;
		}

		File audioFile = resolveAudioFile(path);
		if (audioFile == null || !audioFile.exists()) {
			return null;
		}

		AudioInputStream inputStream = AudioSystem.getAudioInputStream(audioFile);
		Clip clip = AudioSystem.getClip();
		clip.open(inputStream);
		clipCache.put(path, clip);
		return clip;
	}

	private static File resolveAudioFile(String path) {
		File directFile = new File(path);
		if (directFile.exists()) {
			return directFile;
		}

		File[] possibleLocations = new File[] {
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

		for (File candidate : possibleLocations) {
			if (candidate.exists()) {
				return candidate;
			}
		}

		return null;
	}
}
