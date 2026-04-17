import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class SettingsPane extends GraphicsPane {

	GRect backButton, mapButton, muteButton, fullscreenButton;
	GLabel backLabel, mapLabel, muteLabel, fullscreenLabel;

	GRect sfxVolumeTrackBox, sfxVolumeTrack, sfxVolumeFill;
	GLabel sfxVolumePercentLabel;
	int sfxVolumePercent = 80;

	boolean musicMuted = false;
	boolean fullscreen = false;
	boolean draggingSfxVolume = false;

	private final Color BACKGROUND = new Color(10, 10, 18);
	private final Color PANEL_DARK = new Color(26, 26, 38);
	private final Color PANEL_DARK_HOVER = new Color(44, 44, 70);
	private final Color LED = new Color(255, 45, 200);
	private final Color LED_SOFT = new Color(120, 255, 255);
	private final Color LED_TEXT = new Color(240, 240, 255);

	public SettingsPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		sfxVolumePercent = AudioManager.getSfxVolumePercent();
		musicMuted = AudioManager.isMusicMuted();
		createBackground();
		addTitle();
		addSfxVolumeSection();
		addMuteButton();
		addFullscreenButton();
		addMapButton();
		addBackButton();
	}

	@Override
	public void hideContent() {
		for (GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}

	private void createBackground() {
		GRect bg = new GRect(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		bg.setFilled(true);
		bg.setFillColor(BACKGROUND);
		bg.setColor(BACKGROUND);
		bg.setLocation(0, 0);
		contents.add(bg);
		mainScreen.add(bg);
	}

	private void addTitle() {
		GLabel title = new GLabel("Settings");
		title.setFont("DialogInput-BOLD-70");
		title.setColor(LED_SOFT);
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 95);
		contents.add(title);
		mainScreen.add(title);
	}

	private void addSfxVolumeSection() {
		GLabel volumeTitle = new GLabel("Sound Volume");
		volumeTitle.setFont("DialogInput-BOLD-16");
		volumeTitle.setColor(LED_TEXT);
		volumeTitle.setLocation(150, 165);
		contents.add(volumeTitle);
		mainScreen.add(volumeTitle);

		sfxVolumeTrackBox = new GRect(420, 42);
		sfxVolumeTrackBox.setLocation(140, 173);
		sfxVolumeTrackBox.setFilled(true);
		sfxVolumeTrackBox.setFillColor(PANEL_DARK);
		sfxVolumeTrackBox.setColor(LED_SOFT);
		contents.add(sfxVolumeTrackBox);
		mainScreen.add(sfxVolumeTrackBox);
		addLedFrame(sfxVolumeTrackBox.getX(), sfxVolumeTrackBox.getY(), sfxVolumeTrackBox.getWidth(), sfxVolumeTrackBox.getHeight());

		sfxVolumeTrack = new GRect(400, 22);
		sfxVolumeTrack.setLocation(150, 183);
		sfxVolumeTrack.setFilled(true);
		sfxVolumeTrack.setFillColor(new Color(45, 55, 64));
		sfxVolumeTrack.setColor(new Color(45, 55, 64));
		contents.add(sfxVolumeTrack);
		mainScreen.add(sfxVolumeTrack);

		sfxVolumeFill = new GRect(400 * sfxVolumePercent / 100, 22);
		sfxVolumeFill.setLocation(150, 183);
		sfxVolumeFill.setFilled(true);
		sfxVolumeFill.setFillColor(LED_SOFT);
		sfxVolumeFill.setColor(LED_SOFT);
		contents.add(sfxVolumeFill);
		mainScreen.add(sfxVolumeFill);

		sfxVolumePercentLabel = new GLabel(sfxVolumePercent + "%");
		sfxVolumePercentLabel.setFont("DialogInput-BOLD-16");
		sfxVolumePercentLabel.setColor(LED_TEXT);
		sfxVolumePercentLabel.setLocation(565, 200);
		contents.add(sfxVolumePercentLabel);
		mainScreen.add(sfxVolumePercentLabel);
	}

	private void updateSfxVolume(int mouseX) {
		double clickX = mouseX - sfxVolumeTrack.getX();
		sfxVolumePercent = (int) Math.round((clickX / sfxVolumeTrack.getWidth()) * 100);
		if (sfxVolumePercent < 0) sfxVolumePercent = 0;
		if (sfxVolumePercent > 100) sfxVolumePercent = 100;
		sfxVolumeFill.setSize(sfxVolumeTrack.getWidth() * sfxVolumePercent / 100, sfxVolumeFill.getHeight());
		sfxVolumePercentLabel.setLabel(sfxVolumePercent + "%");
		AudioManager.setSfxVolumePercent(sfxVolumePercent);
	}

	private void addMuteButton() {
		muteButton = new GRect(300, 50);
		muteButton.setLocation((mainScreen.getWidth() - muteButton.getWidth()) / 2, 260);
		muteButton.setFilled(true);
		muteButton.setFillColor(PANEL_DARK);
		muteButton.setColor(LED);
		contents.add(muteButton);
		mainScreen.add(muteButton);
		addLedFrame(muteButton.getX(), muteButton.getY(), muteButton.getWidth(), muteButton.getHeight());

		muteLabel = new GLabel(musicMuted ? "Music: OFF" : "Music: ON");
		muteLabel.setFont("DialogInput-BOLD-16");
		muteLabel.setColor(LED_TEXT);
		muteLabel.setLocation(
				muteButton.getX() + (muteButton.getWidth() - muteLabel.getWidth()) / 2,
				muteButton.getY() + (muteButton.getHeight() + muteLabel.getAscent()) / 2);
		contents.add(muteLabel);
		mainScreen.add(muteLabel);
	}

	private void toggleMute() {
		musicMuted = !musicMuted;
		AudioManager.setMusicMuted(musicMuted);
		muteLabel.setLabel(musicMuted ? "Music: OFF" : "Music: ON");
		recenterLabel(muteLabel, muteButton);
	}

	private void addFullscreenButton() {
		fullscreenButton = new GRect(300, 50);
		fullscreenButton.setLocation((mainScreen.getWidth() - fullscreenButton.getWidth()) / 2, 330);
		fullscreenButton.setFilled(true);
		fullscreenButton.setFillColor(PANEL_DARK);
		fullscreenButton.setColor(LED);
		contents.add(fullscreenButton);
		mainScreen.add(fullscreenButton);
		addLedFrame(fullscreenButton.getX(), fullscreenButton.getY(), fullscreenButton.getWidth(), fullscreenButton.getHeight());

		fullscreenLabel = new GLabel("Fullscreen: OFF");
		fullscreenLabel.setFont("DialogInput-BOLD-16");
		fullscreenLabel.setColor(LED_TEXT);
		fullscreenLabel.setLocation(
				fullscreenButton.getX() + (fullscreenButton.getWidth() - fullscreenLabel.getWidth()) / 2,
				fullscreenButton.getY() + (fullscreenButton.getHeight() + fullscreenLabel.getAscent()) / 2);
		contents.add(fullscreenLabel);
		mainScreen.add(fullscreenLabel);
	}

	private void toggleFullscreen() {
		fullscreen = !fullscreen;

		java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(mainScreen.getGCanvas());
		if (window instanceof java.awt.Frame) {
			java.awt.Frame frame = (java.awt.Frame) window;

			if (fullscreen) {
				frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			} else {
				frame.setExtendedState(java.awt.Frame.NORMAL);
			}
		}

		fullscreenLabel.setLabel(fullscreen ? "Fullscreen: ON" : "Fullscreen: OFF");
		recenterLabel(fullscreenLabel, fullscreenButton);
	}

	private void addMapButton() {
		mapButton = new GRect(300, 50);
		mapButton.setLocation((mainScreen.getWidth() - mapButton.getWidth()) / 2, 400);
		mapButton.setFilled(true);
		mapButton.setFillColor(PANEL_DARK);
		mapButton.setColor(LED_SOFT);
		contents.add(mapButton);
		mainScreen.add(mapButton);
		addLedFrame(mapButton.getX(), mapButton.getY(), mapButton.getWidth(), mapButton.getHeight());

		mapLabel = new GLabel("Back to Map");
		mapLabel.setFont("DialogInput-BOLD-16");
		mapLabel.setColor(LED_TEXT);
		mapLabel.setLocation(
				mapButton.getX() + (mapButton.getWidth() - mapLabel.getWidth()) / 2,
				mapButton.getY() + (mapButton.getHeight() + mapLabel.getAscent()) / 2);
		contents.add(mapLabel);
		mainScreen.add(mapLabel);
	}

	private void addBackButton() {
		backButton = new GRect(300, 50);
		backButton.setLocation((mainScreen.getWidth() - backButton.getWidth()) / 2, 470);
		backButton.setFilled(true);
		backButton.setFillColor(PANEL_DARK);
		backButton.setColor(LED);
		contents.add(backButton);
		mainScreen.add(backButton);
		addLedFrame(backButton.getX(), backButton.getY(), backButton.getWidth(), backButton.getHeight());

		backLabel = new GLabel("Back to Menu");
		backLabel.setFont("DialogInput-BOLD-16");
		backLabel.setColor(LED_TEXT);
		backLabel.setLocation(
				backButton.getX() + (backButton.getWidth() - backLabel.getWidth()) / 2,
				backButton.getY() + (backButton.getHeight() + backLabel.getAscent()) / 2);
		contents.add(backLabel);
		mainScreen.add(backLabel);
	}

	private void addLedFrame(double x, double y, double width, double height) {
		GRect outerGlow = new GRect(width + 8, height + 8);
		outerGlow.setLocation(x - 4, y - 4);
		outerGlow.setFilled(false);
		outerGlow.setColor(new Color(0, 130, 110));
		contents.add(outerGlow);
		mainScreen.add(outerGlow);

		GRect innerGlow = new GRect(width + 2, height + 2);
		innerGlow.setLocation(x - 1, y - 1);
		innerGlow.setFilled(false);
		innerGlow.setColor(LED_SOFT);
		contents.add(innerGlow);
		mainScreen.add(innerGlow);
	}

	private boolean isInside(GRect rect, double x, double y) {
		if (rect == null) return false;
		return x >= rect.getX() && x <= rect.getX() + rect.getWidth()
				&& y >= rect.getY() && y <= rect.getY() + rect.getHeight();
	}

	private void recenterLabel(GLabel label, GRect button) {
		label.setLocation(
				button.getX() + (button.getWidth() - label.getWidth()) / 2,
				button.getY() + (button.getHeight() + label.getAscent()) / 2);
	}

	private void resetButtonColors() {
		muteButton.setFillColor(PANEL_DARK);
		fullscreenButton.setFillColor(PANEL_DARK);
		mapButton.setFillColor(PANEL_DARK);
		backButton.setFillColor(PANEL_DARK);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();

		if (isInside(sfxVolumeTrackBox, x, y) || isInside(sfxVolumeTrack, x, y) || isInside(sfxVolumeFill, x, y)) {
			updateSfxVolume((int) x);
			return;
		}
		if (isInside(muteButton, x, y)) {
			toggleMute();
			return;
		}
		if (isInside(fullscreenButton, x, y)) {
			toggleFullscreen();
			return;
		}
		if (isInside(mapButton, x, y)) {
			mainScreen.switchToMapPane();
			return;
		}
		if (isInside(backButton, x, y)) {
			mainScreen.switchToMenuPane();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		draggingSfxVolume = isInside(sfxVolumeTrackBox, x, y) || isInside(sfxVolumeTrack, x, y) || isInside(sfxVolumeFill, x, y);
		if (draggingSfxVolume) {
			updateSfxVolume((int) x);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (draggingSfxVolume) {
			updateSfxVolume(e.getX());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		draggingSfxVolume = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		resetButtonColors();

		if (isInside(muteButton, x, y)) muteButton.setFillColor(PANEL_DARK_HOVER);
		else if (isInside(fullscreenButton, x, y)) fullscreenButton.setFillColor(PANEL_DARK_HOVER);
		else if (isInside(mapButton, x, y)) mapButton.setFillColor(PANEL_DARK_HOVER);
		else if (isInside(backButton, x, y)) backButton.setFillColor(PANEL_DARK_HOVER);
	}
}