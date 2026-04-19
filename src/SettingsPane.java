import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class SettingsPane extends GraphicsPane {

	GRect backButton, muteButton, fullscreenButton;
	GLabel backLabel, muteLabel, fullscreenLabel;

	GRect volumeTrack, volumeFill;
	GLabel volumePercentLabel;
	int volumePercent = 80;

	boolean musicMuted = false;
	boolean fullscreen = false;
	boolean draggingVolume = false;

	private final Color BACKGROUND = new Color(8, 10, 26);
	private final Color PANEL = new Color(20, 25, 50);
	private final Color PANEL_HOVER = new Color(30, 38, 75);
	private final Color TEAL = new Color(0, 210, 180);
	private final Color PINK = new Color(200, 40, 100);
	private final Color WHITE = new Color(240, 240, 255);
	private final Color DIVIDER = new Color(40, 50, 80);

	private double trackX;
	private double trackWidth = 400;

	public SettingsPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		volumePercent = AudioManager.getSfxVolumePercent();
		musicMuted = AudioManager.isMusicMuted();
		createBackground();
		addTitle();
		addSoundSection();
		addDivider(370);
		addDisplaySection();
		addDivider(510);
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
		
		java.util.Random rand = new java.util.Random(42);
		for (int i = 0; i < 120; i++) {
		    int size = rand.nextInt(3) + 1;
		    GRect star = new GRect(size, size);
		    star.setLocation(rand.nextInt(MainApplication.WINDOW_WIDTH), rand.nextInt(MainApplication.WINDOW_HEIGHT));
		    star.setFilled(true);
		    int brightness = rand.nextInt(100) + 155;
		    star.setFillColor(new Color(brightness, brightness, brightness));
		    star.setColor(new Color(brightness, brightness, brightness));
		    contents.add(star);
		    mainScreen.add(star);
		}

		GRect panel = new GRect(700, 580);
		panel.setLocation((mainScreen.getWidth() - 700) / 2, 80);
		panel.setFilled(true);
		panel.setFillColor(PANEL);
		panel.setColor(TEAL);
		contents.add(panel);
		mainScreen.add(panel);

		GRect topBar = new GRect(700, 5);
		topBar.setLocation((mainScreen.getWidth() - 700) / 2, 80);
		topBar.setFilled(true);
		topBar.setFillColor(TEAL);
		topBar.setColor(TEAL);
		contents.add(topBar);
		mainScreen.add(topBar);
	}

	private void addTitle() {
		GLabel title = new GLabel("Game Settings");
		title.setFont("DialogInput-BOLD-38");
		title.setColor(WHITE);
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 145);
		contents.add(title);
		mainScreen.add(title);

		GRect line = new GRect(660, 2);
		line.setLocation((mainScreen.getWidth() - 660) / 2, 158);
		line.setFilled(true);
		line.setFillColor(DIVIDER);
		line.setColor(DIVIDER);
		contents.add(line);
		mainScreen.add(line);
	}

	private void addSoundSection() {
		GLabel sectionLabel = new GLabel("Sound Settings");
		sectionLabel.setFont("DialogInput-BOLD-18");
		sectionLabel.setColor(WHITE);
		sectionLabel.setLocation((mainScreen.getWidth() - 700) / 2 + 20, 200);
		contents.add(sectionLabel);
		mainScreen.add(sectionLabel);

		GLabel volLabel = new GLabel("Volume");
		volLabel.setFont("DialogInput-PLAIN-16");
		volLabel.setColor(new Color(160, 170, 200));
		volLabel.setLocation((mainScreen.getWidth() - 700) / 2 + 20, 245);
		contents.add(volLabel);
		mainScreen.add(volLabel);

		trackX = (mainScreen.getWidth() - trackWidth) / 2 + 20;
		double trackY = 228;

		GRect trackBg = new GRect(trackWidth, 8);
		trackBg.setLocation(trackX, trackY);
		trackBg.setFilled(true);
		trackBg.setFillColor(new Color(40, 50, 80));
		trackBg.setColor(new Color(40, 50, 80));
		contents.add(trackBg);
		mainScreen.add(trackBg);

		volumeFill = new GRect(trackWidth * volumePercent / 100, 8);
		volumeFill.setLocation(trackX, trackY);
		volumeFill.setFilled(true);
		volumeFill.setFillColor(TEAL);
		volumeFill.setColor(TEAL);
		contents.add(volumeFill);
		mainScreen.add(volumeFill);

		volumeTrack = new GRect(trackWidth, 8);
		volumeTrack.setLocation(trackX, trackY);
		volumeTrack.setFilled(false);
		volumeTrack.setColor(new Color(40, 50, 80));
		contents.add(volumeTrack);
		mainScreen.add(volumeTrack);

		volumePercentLabel = new GLabel(volumePercent + "%");
		volumePercentLabel.setFont("DialogInput-PLAIN-16");
		volumePercentLabel.setColor(new Color(160, 170, 200));
		volumePercentLabel.setLocation(trackX + trackWidth + 15, 240);
		contents.add(volumePercentLabel);
		mainScreen.add(volumePercentLabel);

		GLabel musicLabel = new GLabel("Music");
		musicLabel.setFont("DialogInput-PLAIN-16");
		musicLabel.setColor(new Color(160, 170, 200));
		musicLabel.setLocation((mainScreen.getWidth() - 700) / 2 + 20, 310);
		contents.add(musicLabel);
		mainScreen.add(musicLabel);

		muteButton = new GRect(120, 38);
		muteButton.setLocation((mainScreen.getWidth() - 700) / 2 + 120, 290);
		muteButton.setFilled(true);
		muteButton.setFillColor(PANEL);
		muteButton.setColor(TEAL);
		contents.add(muteButton);
		mainScreen.add(muteButton);

		muteLabel = new GLabel(musicMuted ? "OFF" : "ON");
		muteLabel.setFont("DialogInput-BOLD-16");
		muteLabel.setColor(TEAL);
		muteLabel.setLocation(
				muteButton.getX() + (muteButton.getWidth() - muteLabel.getWidth()) / 2,
				muteButton.getY() + (muteButton.getHeight() + muteLabel.getAscent()) / 2);
		contents.add(muteLabel);
		mainScreen.add(muteLabel);
	}

	private void addDisplaySection() {
		GLabel sectionLabel = new GLabel("Display");
		sectionLabel.setFont("DialogInput-BOLD-18");
		sectionLabel.setColor(WHITE);
		sectionLabel.setLocation((mainScreen.getWidth() - 700) / 2 + 20, 400);
		contents.add(sectionLabel);
		mainScreen.add(sectionLabel);

		GLabel fsLabel = new GLabel("Fullscreen");
		fsLabel.setFont("DialogInput-PLAIN-16");
		fsLabel.setColor(new Color(160, 170, 200));
		fsLabel.setLocation((mainScreen.getWidth() - 700) / 2 + 20, 455);
		contents.add(fsLabel);
		mainScreen.add(fsLabel);

		fullscreenButton = new GRect(120, 38);
		fullscreenButton.setLocation((mainScreen.getWidth() - 700) / 2 + 120, 435);
		fullscreenButton.setFilled(true);
		fullscreenButton.setFillColor(PANEL);
		fullscreenButton.setColor(TEAL);
		contents.add(fullscreenButton);
		mainScreen.add(fullscreenButton);

		fullscreenLabel = new GLabel("OFF");
		fullscreenLabel.setFont("DialogInput-BOLD-16");
		fullscreenLabel.setColor(TEAL);
		fullscreenLabel.setLocation(
				fullscreenButton.getX() + (fullscreenButton.getWidth() - fullscreenLabel.getWidth()) / 2,
				fullscreenButton.getY() + (fullscreenButton.getHeight() + fullscreenLabel.getAscent()) / 2);
		contents.add(fullscreenLabel);
		mainScreen.add(fullscreenLabel);
	}

	private void addDivider(double y) {
		GRect line = new GRect(660, 1);
		line.setLocation((mainScreen.getWidth() - 660) / 2, y);
		line.setFilled(true);
		line.setFillColor(DIVIDER);
		line.setColor(DIVIDER);
		contents.add(line);
		mainScreen.add(line);
	}

	private void addBackButton() {
		backButton = new GRect(200, 45);
		backButton.setLocation((mainScreen.getWidth() - 200) / 2, 540);
		backButton.setFilled(true);
		backButton.setFillColor(PANEL);
		backButton.setColor(PINK);
		contents.add(backButton);
		mainScreen.add(backButton);

		backLabel = new GLabel("Back to Menu");
		backLabel.setFont("DialogInput-BOLD-16");
		backLabel.setColor(PINK);
		backLabel.setLocation(
				backButton.getX() + (backButton.getWidth() - backLabel.getWidth()) / 2,
				backButton.getY() + (backButton.getHeight() + backLabel.getAscent()) / 2);
		contents.add(backLabel);
		mainScreen.add(backLabel);
	}

	private void updateVolume(int mouseX) {
		double clickX = mouseX - trackX;
		volumePercent = (int) Math.round((clickX / trackWidth) * 100);
		if (volumePercent < 0) volumePercent = 0;
		if (volumePercent > 100) volumePercent = 100;
		volumeFill.setSize(trackWidth * volumePercent / 100, volumeFill.getHeight());
		volumePercentLabel.setLabel(volumePercent + "%");
		AudioManager.setSfxVolumePercent(volumePercent);
		AudioManager.setMusicVolumePercent(volumePercent);
	}

	private void toggleMute() {
		musicMuted = !musicMuted;
		AudioManager.setMusicMuted(musicMuted);
		muteLabel.setLabel(musicMuted ? "OFF" : "ON");
		recenterLabel(muteLabel, muteButton);
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
		fullscreenLabel.setLabel(fullscreen ? "ON" : "OFF");
		recenterLabel(fullscreenLabel, fullscreenButton);
	}

	private boolean isInside(GRect rect, double x, double y) {
		if (rect == null) return false;
		return x >= rect.getX() && x <= rect.getX() + rect.getWidth()
				&& y >= rect.getY() && y <= rect.getY() + rect.getHeight();
	}

	private boolean isOnTrack(double x, double y) {
		return x >= trackX - 10 && x <= trackX + trackWidth + 10
				&& y >= volumeTrack.getY() - 10 && y <= volumeTrack.getY() + 28;
	}

	private void recenterLabel(GLabel label, GRect button) {
		label.setLocation(
				button.getX() + (button.getWidth() - label.getWidth()) / 2,
				button.getY() + (button.getHeight() + label.getAscent()) / 2);
	}

	private void resetButtonColors() {
		muteButton.setFillColor(PANEL);
		fullscreenButton.setFillColor(PANEL);
		backButton.setFillColor(PANEL);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		if (isOnTrack(x, y)) { updateVolume((int) x); return; }
		if (isInside(muteButton, x, y)) { toggleMute(); return; }
		if (isInside(fullscreenButton, x, y)) { toggleFullscreen(); return; }
		if (isInside(backButton, x, y)) { mainScreen.switchToMenuPane(); }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		draggingVolume = isOnTrack(x, y);
		if (draggingVolume) updateVolume((int) x);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (draggingVolume) updateVolume(e.getX());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		draggingVolume = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		resetButtonColors();
		if (isInside(muteButton, x, y)) muteButton.setFillColor(PANEL_HOVER);
		else if (isInside(fullscreenButton, x, y)) fullscreenButton.setFillColor(PANEL_HOVER);
		else if (isInside(backButton, x, y)) backButton.setFillColor(PANEL_HOVER);
	}
}