import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
	private static Clip currentMusicClip;
	private static String currentMusicPath;
	private static final Map<String, Clip> clipCache = new HashMap<String, Clip>();

	public static void playMusicLoop(String path) {
		if (path == null || path.trim().isEmpty()) {
			stopMusic();
			return;
		}

		String normalizedPath = path.trim();
		if (normalizedPath.equals(currentMusicPath) && currentMusicClip != null && currentMusicClip.isRunning()) {
			return;
		}

		stopCurrentClipOnly();

		try {
			Clip clip = loadClip(normalizedPath);
			if (clip == null) {
				System.out.println("AudioManager: Could not load music track " + normalizedPath);
				currentMusicPath = null;
				return;
			}

			currentMusicClip = clip;
			currentMusicPath = normalizedPath;
			currentMusicClip.setFramePosition(0);
			currentMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
			currentMusicClip.start();
		} catch (Exception e) {
			System.out.println("AudioManager: Failed to play music: " + normalizedPath);
			currentMusicPath = null;
		}
	}

	public static void stopMusic() {
		stopCurrentClipOnly();
		currentMusicPath = null;
	}

	private static void stopCurrentClipOnly() {
		if (currentMusicClip != null) {
			currentMusicClip.stop();
		}
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
